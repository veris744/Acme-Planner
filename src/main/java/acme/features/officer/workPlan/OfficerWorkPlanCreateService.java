
package acme.features.officer.workPlan;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.roles.Officer;
import acme.entities.workPlans.WorkPlan;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.services.AbstractCreateService;

@Service
public class OfficerWorkPlanCreateService implements AbstractCreateService<Officer, WorkPlan> {

	@Autowired
	protected OfficerWorkPlanRepository repository;


	@Override
	public boolean authorise(final Request<WorkPlan> request) {
		assert request != null;
		return true;
	}

	@Override
	public void bind(final Request<WorkPlan> request, final WorkPlan entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		request.bind(entity, errors);
	}

	@Override
	public void unbind(final Request<WorkPlan> request, final WorkPlan entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "title", "startPeriod", "endPeriod", "isPublic");
	}

	@Override
	public WorkPlan instantiate(final Request<WorkPlan> request) {
		assert request != null;

		WorkPlan result;
		Officer officer;

		officer = this.repository.getOfficerById(request.getPrincipal().getActiveRoleId());

		result = new WorkPlan();
		result.setOfficer(officer);
		result.setIsPublic(true);

		return result;
	}

	@Override
	public void validate(final Request<WorkPlan> request, final WorkPlan entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		//Fecha de inicio anterior a la de fin
		if(!errors.hasErrors("startPeriod") && !errors.hasErrors("endPeriod")) {
			
			errors.state(request, entity.getStartPeriod().before(entity.getEndPeriod()), "startPeriod", "officer.workplan.form.error.startPeriodBefore");
		}
		
		//Fecha de inicio anterior a la actual
		if(!errors.hasErrors("startPeriod" )) {
			
			errors.state(request, entity.getStartPeriod().after(java.util.Calendar.getInstance().getTime()), "startPeriod", "officer.workplan.form.error.startPeriodCurrent");
		}


	}

	@Override
	public void create(final Request<WorkPlan> request, final WorkPlan entity) {
		assert request != null;
		assert entity != null;

		Officer officer;

		officer = this.repository.getOfficerById(request.getPrincipal().getActiveRoleId());

		entity.setOfficer(officer);
		this.repository.save(entity);
	}
}
