
package acme.features.manager.workPlan;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.tasks.Task;
import acme.entities.workPlans.WorkPlan;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Manager;
import acme.framework.entities.Principal;
import acme.framework.services.AbstractUpdateService;

@Service
public class ManagerWorkPlanUpdateService implements AbstractUpdateService<Manager, WorkPlan> {

	@Autowired
	protected ManagerWorkPlanRepository	repository;


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

		final Collection<Task> tasks = entity.getTasks();
		final Collection<Task> enabledTask = this.repository.findAvailableTasks();
		//Nueva Query con Tareas disponibles

		request.unbind(entity, model, "title", "startPeriod", "endPeriod", "isPublic");

		model.setAttribute("taskList", tasks);
		model.setAttribute("enabledTask", enabledTask);
	}

	@Override
	public WorkPlan findOne(final Request<WorkPlan> request) {
		assert request != null;

		WorkPlan result;
		int id;

		id = request.getModel().getInteger("id");
		result = this.repository.findOneWorkPlanById(id);

		return result;
	}

	@Override
	public void validate(final Request<WorkPlan> request, final WorkPlan entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
	}

	@Override
	public void update(final Request<WorkPlan> request, final WorkPlan entity) {
		assert request != null;
		assert entity != null;
		//Conseguir selectedTask ponersela a la entidad en la lista de tareas
		final Integer taskId = request.getModel().getInteger("taskSelected");
		if (taskId != null) {
			final Task task = this.repository.findOneTaskById(taskId);
			final Collection<Task> ct = entity.getTasks();
			ct.add(task);
			entity.setTasks(ct);
		}
		this.repository.save(entity);
	}
}
