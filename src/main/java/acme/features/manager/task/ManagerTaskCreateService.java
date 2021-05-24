package acme.features.manager.task;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.tasks.Task;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Manager;
import acme.framework.services.AbstractCreateService;

@Service
public class ManagerTaskCreateService implements AbstractCreateService<Manager, Task> {
	
	@Autowired
	protected ManagerTaskRepository repository;
	
	@Override
	public boolean authorise (final Request<Task> request) {
		assert request != null;
		return true;
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

		request.unbind(entity, model, "title", "startPeriod", 
			"endPeriod", "workload", "description", "link","isPublic");
	}
	
	@Override
	public Task instantiate(final Request<Task> request) {
		assert request != null;
		
		Task result;
		Manager manager;
		
		manager = this.repository.getManagerById(request.getPrincipal().getActiveRoleId());
		
		
		result = new Task();
		result.setManager(manager);
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
		
		
		if(!errors.hasErrors("startPeriod")) {
			
			errors.state(request, entity.getStartPeriod().after(java.util.Calendar.getInstance().getTime()), "startPeriod", "manager.task.form.error.startPeriodCurrent");
		}
		
	}
	
	@Override
	public void create(final Request<Task> request, final Task entity) {
		assert request != null;
		assert entity != null;
		
		Manager manager;
		
		manager = this.repository.getManagerById(request.getPrincipal().getActiveRoleId());
		
		entity.setManager(manager);
		
		this.repository.save(entity);
	}
}