package acme.features.anonymous.shout;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.deras.Dera;
import acme.entities.shouts.Shout;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Anonymous;
import acme.framework.services.AbstractCreateService;

@Service
public class AnonymousShoutCreateService implements AbstractCreateService<Anonymous, Shout> {
	
	
	
	@Autowired
	protected AnonymousShoutRepository repository;
	
	
	@Override
	public boolean authorise (final Request<Shout> request) {
		assert request != null;
		return true;
	}
	
	@Override
	public void bind(final Request<Shout> request, final Shout entity, final Errors errors) {

		assert request != null;
		assert entity != null;
		assert errors != null;

		request.bind(entity, errors);
	}
	
	@Override
	public void unbind(final Request<Shout> request, final Shout entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "author", "text", "info", 
			"dera.deadline", "dera.budget", "dera.important");
	}
	
	@Override
	public Shout instantiate(final Request<Shout> request) {
		assert request != null;
		
		Shout result;
		result = new Shout();
		
		Dera dera;
		dera = new Dera();
		
		Date moment;
		moment = new Date(System.currentTimeMillis());
		

		result.setAuthor("");
		result.setInfo("");
		result.setText("");
		result.setMoment(moment);
		
		LocalDate localDate;
		localDate = moment.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		
		String year;
		year = String.valueOf(localDate.getYear()).substring(2);
		
		String month;
		month = String.valueOf(localDate.getMonthValue());
		if (month.length() == 1)
			month = "0"+month;
		

		String day;
		day = String.valueOf(localDate.getDayOfMonth());
		if (day.length() == 1)
			day = "0"+day;
		
		dera.setTicket("aaa-"+ year + month + day+ "-aa");
		dera.setImportant(false);
		
		result.setDera(dera);

		return result;
	}
	
	@Override
	public void validate(final Request<Shout> request, final Shout entity, final Errors errors) {
		
		assert request != null;
		assert entity != null;
		assert errors != null;

		boolean isAcceptedCurrency, isOneWeekAhead;
		
		
		isAcceptedCurrency = entity.getDera().getBudget().getCurrency().equals("EUR") ||
			entity.getDera().getBudget().getCurrency().equals("USD");
		errors.state(request, isAcceptedCurrency, "dera.budget", "Must be EUR or USD");
		

		LocalDate localDate;
		localDate = entity.getDera().getDeadline().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		isOneWeekAhead = localDate.isAfter(LocalDate.now().plusDays(7)) || localDate.equals(LocalDate.now().plusDays(7));
		errors.state(request, isOneWeekAhead, "dera.deadline", "Must be at least 7 deays ahead");
	}
	
	@Override
	public void create(final Request<Shout> request, final Shout entity) {
		assert request != null;
		assert entity != null;

		Date moment;
		
		moment = new Date(System.currentTimeMillis()-1);
		entity.setMoment(moment);
		
		Dera dera;
		dera = entity.getDera();
		
		LocalDate localDate;
		localDate = moment.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

		String year;
		year = String.valueOf(localDate.getYear()).substring(2);

		dera.setTicket("aaa-"+ year + localDate.getMonthValue() + localDate.getDayOfMonth() + "-aa");
		
		entity.setDera(dera);;

		this.repository.save(dera);
		this.repository.save(entity);
	}
}