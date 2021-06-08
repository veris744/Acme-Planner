package acme.features.anonymous.shout;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.datatypes.ShoutInfo;
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
			"shoutInfo.date", "shoutInfo.money", "shoutInfo.bool");
	}
	
	@Override
	public Shout instantiate(final Request<Shout> request) {
		assert request != null;
		
		Shout result;
		result = new Shout();
		
		ShoutInfo info;
		info = new ShoutInfo();
		
		Date moment;
		moment = new Date(System.currentTimeMillis()-1);
		

		result.setAuthor("");
		result.setInfo("");
		result.setText("");
		result.setMoment(moment);
		
		info.setMoment2(moment);
		info.setBool(false);
		
		result.setShoutInfo(info);

		return result;
	}
	
	@Override
	public void validate(final Request<Shout> request, final Shout entity, final Errors errors) {
		
		assert request != null;
		assert entity != null;
		assert errors != null;

		boolean isAcceptedCurrency;
		
		
		isAcceptedCurrency = entity.getShoutInfo().getMoney().getCurrency().equals("EUR") ||
			entity.getShoutInfo().getMoney().getCurrency().equals("DOL");
		errors.state(request, isAcceptedCurrency, "shoutInfo.money", "Must be EUR or DOL");
		
	}
	
	@Override
	public void create(final Request<Shout> request, final Shout entity) {
		assert request != null;
		assert entity != null;

		Date moment;
		
		moment = new Date(System.currentTimeMillis()-1);
		entity.setMoment(moment);
		final ShoutInfo info = entity.getShoutInfo();
		info.setMoment2(moment);
		entity.setShoutInfo(info);

		this.repository.save(entity);
	}
}