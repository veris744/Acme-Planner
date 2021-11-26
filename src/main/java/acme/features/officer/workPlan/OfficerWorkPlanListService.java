package acme.features.officer.workPlan;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.roles.Officer;
import acme.entities.workPlans.WorkPlan;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.services.AbstractListService;

@Service
public class OfficerWorkPlanListService implements AbstractListService<Officer, WorkPlan> {
	
	@Autowired
	private OfficerWorkPlanRepository repository;
	
	@Override
	public boolean authorise(final Request<WorkPlan> request) {
		assert request != null;
		
		return true;
	}

	@Override
	public void unbind(final Request<WorkPlan> request, final WorkPlan entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;
		
		request.unbind(entity, model, "title", "startPeriod", 
			"endPeriod", "workload");
		
	}

	@Override
	public Collection<WorkPlan> findMany(final Request<WorkPlan> request) {
		assert request != null;
		
		Collection<WorkPlan> result;
		int officerId;
		
		officerId = request.getPrincipal().getActiveRoleId();
		
		result = this.repository.findManyWorkPlansByOfficerId(officerId);
		
		return result;
	}
}
