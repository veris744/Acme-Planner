package acme.features.officer.duty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.roles.Officer;
import acme.entities.duties.Duty;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Principal;
import acme.framework.services.AbstractDeleteService;

@Service
public class OfficerDutyDeleteService implements AbstractDeleteService<Officer, Duty>{

	@Autowired
	protected OfficerDutyRepository repository;
	
	@Override
	public boolean authorise(final Request<Duty> request) {
		assert request != null;
		
		int dutyId;
		Duty duty;
		Officer officer;
		Principal principal;
		
		dutyId = request.getModel().getInteger("id");
		duty = this.repository.findOneDutyById(dutyId);
		officer = duty.getOfficer();
		principal = request.getPrincipal();
		
		return officer.getId() == principal.getActiveRoleId();
	}

	@Override
	public void bind(final Request<Duty> request, final Duty entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		request.bind(entity, errors);
		
	}

	@Override
	public void unbind(final Request<Duty> request, final Duty entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "title", "startExecutionPeriod",
			"endExecutionPeriod", "workload", "description", "link", "isPublic");	
		
	}

	@Override
	public Duty findOne(final Request<Duty> request) {
		assert request != null;
		
		final int dutyId = request.getModel().getInteger("id");
		return this.repository.findOneDutyById(dutyId);
	}

	@Override
	public void validate(final Request<Duty> request, final Duty entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
		
	}

	@Override
	public void delete(final Request<Duty> request, final Duty entity) {
		assert request != null;
		assert entity !=null;
		
		
		
		this.repository.delete(entity);
		
	}

}
