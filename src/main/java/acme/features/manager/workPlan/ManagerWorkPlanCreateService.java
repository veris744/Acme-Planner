
package acme.features.manager.workPlan;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Comparator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.tasks.Task;
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

		// Recomendación del periodo de ejecución del workplan 

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

			errors.add("startPeriod", "La fecha de inicio debería ser: " + dia + "/" + mes + "/" + anyo + " " + hora + ":" + (min == 0 ? "00" : min));
		}
		if (!finEntity.isAfter(fin)) {
			LocalDateTime finRecomendado = fin.plusDays(1);
			finRecomendado = LocalDateTime.of(finRecomendado.getYear(), finRecomendado.getMonth(), finRecomendado.getDayOfMonth(), 17, 0);

			final int dia = finRecomendado.getDayOfMonth();
			final int mes = finRecomendado.getMonthValue();
			final int anyo = finRecomendado.getYear();
			final int hora = finRecomendado.getHour();
			final int min = finRecomendado.getMinute();

			errors.add("endPeriod", "La fecha de fin debería ser: " + dia + "/" + mes + "/" + anyo + " " + hora + ":" + (min == 0 ? "00" : min));
		}

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
