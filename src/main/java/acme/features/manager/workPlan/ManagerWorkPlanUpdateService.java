
package acme.features.manager.workPlan;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collection;
import java.util.Comparator;
import java.util.stream.Collectors;

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
		final Collection<Task> enabledTask = this.repository.findManyTask(request.getPrincipal().getActiveRoleId()).stream().filter(x -> entity.taskFitsOnPeriod(x)).filter(x -> !tasks.contains(x)).filter(x -> !entity.getIsPublic() || x.getIsPublic())
			.collect(Collectors.toList());

		request.getModel().setAttribute("taskList", tasks);
		request.getModel().setAttribute("enabledTask", enabledTask);

		request.bind(entity, errors);
	}

	@Override
	public void unbind(final Request<WorkPlan> request, final WorkPlan entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		final Collection<Task> tasks = entity.getTasks();
		final Collection<Task> enabledTask = this.repository.findManyTask(request.getPrincipal().getActiveRoleId()).stream().filter(x -> entity.taskFitsOnPeriod(x)).filter(x -> !tasks.contains(x)).filter(x -> !entity.getIsPublic() || x.getIsPublic())
			.collect(Collectors.toList());

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
		//Fecha de inicio anterior a la de fin
		if (!errors.hasErrors("startPeriod") && !errors.hasErrors("endPeriod")) {

			errors.state(request, entity.getStartPeriod().before(entity.getEndPeriod()), "startPeriod", "manager.workplan.form.error.startPeriodBefore");
		}

		// Recomendación del periodo de ejecución del workplan 
		if (!errors.hasErrors("startPeriod") && !errors.hasErrors("endPeriod") && entity.getTasks()!= null && !entity.getTasks().isEmpty()) {
			final LocalDateTime inicio = entity.getTasks().stream().min(Comparator.comparing(Task::getStartPeriod)).map(Task::getStartPeriod).orElse(null).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
			final LocalDateTime fin = entity.getTasks().stream().max(Comparator.comparing(Task::getEndPeriod)).map(Task::getEndPeriod).orElse(null).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
			final LocalDateTime inicioEntity = entity.getStartPeriod().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
			final LocalDateTime finEntity = entity.getEndPeriod().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

			if (!inicioEntity.isBefore(inicio)) {
				LocalDateTime inicioRecomendado = inicio.minusDays(1);
				inicioRecomendado = LocalDateTime.of(inicioRecomendado.getYear(), inicioRecomendado.getMonth(), inicioRecomendado.getDayOfMonth(), 8, 0);

				final int dia = inicioRecomendado.getDayOfMonth();
				final int mes = inicioRecomendado.getMonthValue();
				final int anyo = inicioRecomendado.getYear();
				final int hora = inicioRecomendado.getHour();
				final int min = inicioRecomendado.getMinute();

				errors.add("startPeriod", dia + "/" + mes + "/" + anyo + " " + hora + ":" + (min == 0 ? "00" : min));
			}
			if (!finEntity.isAfter(fin)) {
				LocalDateTime finRecomendado = fin.plusDays(1);
				finRecomendado = LocalDateTime.of(finRecomendado.getYear(), finRecomendado.getMonth(), finRecomendado.getDayOfMonth(), 17, 0);

				final int dia = finRecomendado.getDayOfMonth();
				final int mes = finRecomendado.getMonthValue();
				final int anyo = finRecomendado.getYear();
				final int hora = finRecomendado.getHour();
				final int min = finRecomendado.getMinute();

				errors.add("endPeriod", dia + "/" + mes + "/" + anyo + " " + hora + ":" + (min == 0 ? "00" : min));
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
