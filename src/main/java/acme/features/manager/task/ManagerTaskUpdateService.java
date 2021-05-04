package acme.features.manager.task;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.tasks.Task;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Manager;
import acme.framework.entities.Principal;
import acme.framework.services.AbstractUpdateService;

@Service
public class ManagerTaskUpdateService implements AbstractUpdateService<Manager, Task>  {

	@Autowired
	protected ManagerTaskRepository repository;

	
	@Override
	public boolean authorise(final Request<Task> request) {
		assert request != null;
		
		int taskId;
		Task task;
		Manager manager;
		Principal principal;
		
		taskId = request.getModel().getInteger("id");
		task = this.repository.findOneTaskById(taskId);
		manager = task.getManager();
		principal = request.getPrincipal();
		
		return manager.getId() == principal.getActiveRoleId();
	}

	@Override
	public void bind(final Request<Task> request, final Task entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		request.bind(entity, errors);
		
	}

	@Override
	public void unbind(final Request<Task> request, final Task entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;
		
		request.unbind(entity, model, "title", "startPeriod", "endPeriod", "workload",
			"description", "link", "isPublic");
		
	}

	@Override
	public Task findOne(final Request<Task> request) {
		assert request != null;

		Task result;
		int id;

		id = request.getModel().getInteger("id");
		result = this.repository.findOneTaskById(id);

		return result;
	}

	@Override
	public void validate(final Request<Task> request, final Task entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
		
		final Double workload = entity.getWorkload();
		final Integer horas = workload.intValue();
		final Double minutos = workload - horas;
		final Date startPeriod = entity.getStartPeriod();
		final Date endPeriod = entity.getEndPeriod();
		final Long workloadMax = endPeriod.getTime()-startPeriod.getTime();
		final Integer worklodMinutosMax = (int) (workloadMax/(60000));
		final Integer workloadMinutos = (int) (horas*60+minutos*100);
		
		if(!errors.hasErrors("startPeriod") && !errors.hasErrors("endPeriod")) {
			errors.state(request, entity.getStartPeriod().before(entity.getEndPeriod()), "startPeriod", "manager.task.form.error.startPeriodBefore");
		}
		
		if(!errors.hasErrors("workload")) {
			
			errors.state(request, minutos<0.6, "workload", "manager.task.form.error.workloadminutes");
	
		}
		
		if(!errors.hasErrors("startPeriod") && !errors.hasErrors("endPeriod") && !errors.hasErrors("workload")) {

			errors.state(request, worklodMinutosMax>=workloadMinutos, "workload", "manager.task.form.error.workloadmax");
		}
		
	}

	@Override
	public void update(final Request<Task> request, final Task entity) {
		assert request != null;
		assert entity != null;

		this.repository.save(entity);
		
	}

}
