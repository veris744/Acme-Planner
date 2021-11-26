package acme.features.authenticated.duty;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.duties.Duty;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Authenticated;
import acme.framework.services.AbstractShowService;

@Service
public class AuthenticatedDutyShowService implements AbstractShowService<Authenticated, Duty> {

	@Autowired
	protected AuthenticatedDutyRepository repository;
	
	@Override
	public boolean authorise(final Request<Duty> request) {
		assert request != null;
		return true;
	}

	@Override
	public void unbind(final Request<Duty> request, final Duty entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "title", "startPeriod", "endPeriod", "workload", "description", "link", "isPublic"); 
	}

	@Override
	public Duty findOne(final Request<Duty> request) {
		assert request != null;
		
		final Duty result;
		int id;
		
		id = request.getModel().getInteger("id");
		result = this.repository.findOneDutyById(id);

		final Date current = java.util.Calendar.getInstance().getTime();
		
		assert result.getEndPeriod().before(current);
		assert result.getIsPublic();
		
		return result;
	}
}
