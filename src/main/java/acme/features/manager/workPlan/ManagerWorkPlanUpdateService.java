
package acme.features.manager.workPlan;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.CheckForNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.roles.Manager;
import acme.entities.tasks.Task;
import acme.entities.workPlans.WorkPlan;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Principal;
import acme.framework.services.AbstractUpdateService;

@Service
public class ManagerWorkPlanUpdateService implements AbstractUpdateService<Manager, WorkPlan> {

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
	public void bind(final Request<WorkPlan> request, final WorkPlan entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		final Collection<Task> tasks = entity.getTasks();
		final Collection<Task> enabledTask = this.repository.findManyTask(request.getPrincipal().getActiveRoleId()).stream().filter(entity::taskFitsOnPeriod).filter(x -> !tasks.contains(x)).filter(x -> !entity.getIsPublic() || x.getIsPublic())
			.collect(Collectors.toList());

		request.getModel().setAttribute("taskList", tasks);
		request.getModel().setAttribute("enabledTask", enabledTask);
			
		//Recomendación fechas
		if(!tasks.isEmpty()) {
			final Optional<Date> ini = tasks.stream().min(Comparator.comparing(Task::getStartPeriod)).map(Task::getStartPeriod);
			final Optional<Date> end = tasks.stream().max(Comparator.comparing(Task::getEndPeriod)).map(Task::getEndPeriod);
							
			if(ini.isPresent() && end.isPresent()) {
				final LocalDateTime inicio = ini.get().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
				final LocalDateTime fin = end.get().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
									
				LocalDateTime inicioRecomendado = inicio.minusDays(1);
				inicioRecomendado = LocalDateTime.of(inicioRecomendado.getYear(), inicioRecomendado.getMonth(), inicioRecomendado.getDayOfMonth(), 8, 0);
						
				LocalDateTime finRecomendado = fin.plusDays(1);
				finRecomendado = LocalDateTime.of(finRecomendado.getYear(), finRecomendado.getMonth(), finRecomendado.getDayOfMonth(), 17, 0);
												
				final Date iniRec = Date.from(inicioRecomendado.atZone(ZoneId.systemDefault()).toInstant());
				final Date finRec = Date.from(finRecomendado.atZone(ZoneId.systemDefault()).toInstant());
												
				request.getModel().setAttribute("inirec", iniRec);
				request.getModel().setAttribute("finrec", finRec);
			}
		}
			
		request.bind(entity, errors);
	}

	@Override
	public void unbind(final Request<WorkPlan> request, final WorkPlan entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		final Collection<Task> tasks = entity.getTasks();
		final Collection<Task> enabledTask = this.repository.findManyTask(request.getPrincipal().getActiveRoleId()).stream().filter(entity::taskFitsOnPeriod).filter(x -> !tasks.contains(x)).filter(x -> !entity.getIsPublic() || x.getIsPublic())
			.collect(Collectors.toList());																								

		request.unbind(entity, model, "title", "startPeriod", "endPeriod", "isPublic");

		model.setAttribute("taskList", tasks);
		model.setAttribute("enabledTask", enabledTask);
		
		//Recomendación fechas
		if(!tasks.isEmpty()) {
			final Optional<Date> ini = tasks.stream().min(Comparator.comparing(Task::getStartPeriod)).map(Task::getStartPeriod);
			final Optional<Date> end = tasks.stream().max(Comparator.comparing(Task::getEndPeriod)).map(Task::getEndPeriod);
									
			if(ini.isPresent() && end.isPresent()) {
				final LocalDateTime inicio = ini.get().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
				final LocalDateTime fin = end.get().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
											
				LocalDateTime inicioRecomendado = inicio.minusDays(1);
				inicioRecomendado = LocalDateTime.of(inicioRecomendado.getYear(), inicioRecomendado.getMonth(), inicioRecomendado.getDayOfMonth(), 8, 0);
								
				LocalDateTime finRecomendado = fin.plusDays(1);
				finRecomendado = LocalDateTime.of(finRecomendado.getYear(), finRecomendado.getMonth(), finRecomendado.getDayOfMonth(), 17, 0);
														
				final Date iniRec = Date.from(inicioRecomendado.atZone(ZoneId.systemDefault()).toInstant());
				final Date finRec = Date.from(finRecomendado.atZone(ZoneId.systemDefault()).toInstant());
														
				model.setAttribute("inirec", iniRec);
				model.setAttribute("finrec", finRec);
			}
		}
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
	@CheckForNull
	public void validate(final Request<WorkPlan> request, final WorkPlan entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
		//Fecha de inicio anterior a la de fin
		if (!errors.hasErrors("startPeriod") && !errors.hasErrors("endPeriod")) {

			errors.state(request, entity.getStartPeriod().before(entity.getEndPeriod()), "startPeriod", "manager.workplan.form.error.startPeriodBefore");
		}

		// Recomendación del periodo de ejecución del workplan 
		if (!errors.hasErrors("startPeriod") && !errors.hasErrors("endPeriod") && entity.getTasks() != null && !entity.getTasks().isEmpty()) {
			final Optional<Date> ini = entity.getTasks().stream().min(Comparator.comparing(Task::getStartPeriod)).map(Task::getStartPeriod);
			final Optional<Date> end = entity.getTasks().stream().max(Comparator.comparing(Task::getEndPeriod)).map(Task::getEndPeriod);
			LocalDateTime inicio = null;
			LocalDateTime fin = null;
			
			if (ini.isPresent() && end.isPresent()) {
				inicio = ini.get().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
				fin = end.get().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

				final LocalDateTime inicioEntity = entity.getStartPeriod().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
				final LocalDateTime finEntity = entity.getEndPeriod().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

				if (!inicioEntity.isBefore(inicio)) {
					LocalDateTime inicioRecomendado = inicio.minusDays(1);
					inicioRecomendado = LocalDateTime.of(inicioRecomendado.getYear(), inicioRecomendado.getMonth(), inicioRecomendado.getDayOfMonth(), 8, 0);
					final Date iniRec = Date.from(inicioRecomendado.atZone(ZoneId.systemDefault()).toInstant());
					errors.state(request, false, "startPeriod", "acme.validation.start-period");
					request.getModel().setAttribute("fechainirec", iniRec);
				}
				if (!finEntity.isAfter(fin)) {
					LocalDateTime finRecomendado = fin.plusDays(1);
					finRecomendado = LocalDateTime.of(finRecomendado.getYear(), finRecomendado.getMonth(), finRecomendado.getDayOfMonth(), 17, 0);
					final Date finRec = Date.from(finRecomendado.atZone(ZoneId.systemDefault()).toInstant());
					
					errors.state(request, false, "endPeriod", "acme.validation.end-period");
					request.getModel().setAttribute("fechafinrec", finRec);
				}
			}
		}

		// Validacion de nueva tarea a añadir

		final Integer taskId = request.getModel().getInteger("taskSelected");

		if (taskId != null && !taskId.equals(-1) && !errors.hasErrors("startPeriod") && !errors.hasErrors("endPeriod")) {
			final Task task = this.repository.findOneTaskById(taskId);
			if (Boolean.FALSE.equals(task.getIsPublic()) && Boolean.TRUE.equals(entity.getIsPublic())) {
				errors.state(request, false, "taskSelected", "acme.validation.task-is-private");
			}
			if (!entity.taskFitsOnPeriod(task)) {
				errors.state(request, false, "taskSelected", "acme.validation.task-not-in-period");
			}
			if (entity.getTasks() != null && entity.getTasks().contains(task)) {
				errors.state(request, false, "taskSelected", "acme.validation.task-is-present");
			}
		}

	}

	@Override
	public void update(final Request<WorkPlan> request, final WorkPlan entity) {
		assert request != null;
		assert entity != null;

		final Integer taskId = request.getModel().getInteger("taskSelected");
		
		if (taskId != null && !taskId.equals(-1)) {
			final Task task = this.repository.findOneTaskById(taskId);

			assert task.getManager().getId() == request.getPrincipal().getActiveRoleId();

			final Collection<Task> ct = entity.getTasks();
			ct.add(task);
			entity.setTasks(ct);
		}

		this.repository.save(entity);
	}
}
