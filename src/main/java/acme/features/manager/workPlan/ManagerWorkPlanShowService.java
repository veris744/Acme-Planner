package acme.features.manager.workPlan;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.roles.Manager;
import acme.entities.tasks.Task;
import acme.entities.workPlans.WorkPlan;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Principal;
import acme.framework.services.AbstractShowService;

@Service 
public class ManagerWorkPlanShowService implements AbstractShowService<Manager, WorkPlan> {

	@Autowired
	protected ManagerWorkPlanRepository repository;
	
	@Override
	public boolean authorise(final Request<WorkPlan> request) {
		assert request != null;
		
		int workPlanId;
		WorkPlan workPlan;
		Manager manager;
		Principal principal;
		
		workPlanId = request.getModel().getInteger("id");
		workPlan = this.repository.findOneWorkPlanById(workPlanId);
		manager = workPlan.getManager();
		principal = request.getPrincipal();
		
		return manager.getId() == principal.getActiveRoleId();
	}

	@Override
	public void unbind(final Request<WorkPlan> request, final WorkPlan entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;
		
		
		
		final Collection<Task> tasks = entity.getTasks();
		final Collection<Task> enabledTask = this.repository.findManyTask(request.getPrincipal().getActiveRoleId()).stream()
			.filter(entity::taskFitsOnPeriod)
			.filter(x->!tasks.contains(x))
			.filter(x->!entity.getIsPublic() || x.getIsPublic())
			.collect(Collectors.toList());
		
		request.unbind(entity, model, "title", "startPeriod", "endPeriod", "isPublic"); 
		
		model.setAttribute("taskList", tasks);
		model.setAttribute("enabledTask", enabledTask);
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
