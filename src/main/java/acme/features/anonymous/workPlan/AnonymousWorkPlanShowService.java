package acme.features.anonymous.workPlan;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.workPlans.WorkPlan;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Anonymous;
import acme.framework.services.AbstractShowService;

@Service
public class AnonymousWorkPlanShowService implements AbstractShowService<Anonymous, WorkPlan> {

	@Autowired
	protected AnonymousWorkPlanRepository repository;
	
	@Override
	public boolean authorise(final Request<WorkPlan> request) {
		assert request != null;
		
		int workPlanId;
		WorkPlan workPlan;
		Date current;
		
		workPlanId = request.getModel().getInteger("id");
		workPlan = this.repository.findOneWorkPlanById(workPlanId);
		current = java.util.Calendar.getInstance().getTime();
		
		return workPlan.getEndPeriod().after(current) && workPlan.getIsPublic();
	}

	@Override
	public void unbind(final Request<WorkPlan> request, final WorkPlan entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "title", "startPeriod", "endPeriod", "isPublic", "workload"); 
	}

	@Override
	public WorkPlan findOne(final Request<WorkPlan> request) {
		assert request != null;
		
		final WorkPlan result;
		int id;
		
		id = request.getModel().getInteger("id");
		result = this.repository.findOneWorkPlanById(id);
		
		return result;
	}
}
