package acme.features.manager.workPlan;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.workPlans.WorkPlan;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Manager;
import acme.framework.services.AbstractCreateService;

@Service
public class ManagerWorkPlanCreateService implements AbstractCreateService<Manager, WorkPlan> {
	
	@Autowired
	protected ManagerWorkPlanRepository repository;
	
	@Override
	public boolean authorise (final Request<WorkPlan> request) {
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

		request.unbind(entity, model, "title", "startPeriod", "endPeriod","isPublic");
	}
	
	@Override
	public WorkPlan instantiate(final Request<WorkPlan> request) {
		assert request != null;
		
		WorkPlan result;
		Manager manager;
		
		manager = this.repository.getManagerById(request.getPrincipal().getActiveRoleId());
		
		result = new WorkPlan();
		result.setManager(manager);
		result.setIsPublic(true);
		
		return result;
	}
	
	@Override
	public void validate(final Request<WorkPlan> request, final WorkPlan entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
		
	}
	
	@Override
	public void create(final Request<WorkPlan> request, final WorkPlan entity) {
		assert request != null;
		assert entity != null;
		
		Manager manager;
		
		manager = this.repository.getManagerById(request.getPrincipal().getActiveRoleId());
		
		entity.setManager(manager);
		this.repository.save(entity);
	}
}