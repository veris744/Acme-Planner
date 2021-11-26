
package acme.features.officer.workPlan;

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

import acme.entities.roles.Officer;
import acme.entities.duties.Duty;
import acme.entities.workPlans.WorkPlan;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Principal;
import acme.framework.services.AbstractUpdateService;

@Service
public class OfficerWorkPlanUpdateService implements AbstractUpdateService<Officer, WorkPlan> {

	@Autowired
	protected OfficerWorkPlanRepository repository;


	@Override
	public boolean authorise(final Request<WorkPlan> request) {
		assert request != null;

		int workPlanId;
		WorkPlan workPlan;
		Officer officer;
		Principal principal;

		workPlanId = request.getModel().getInteger("id");
		workPlan = this.repository.findOneWorkPlanById(workPlanId);
		officer = workPlan.getOfficer();
		principal = request.getPrincipal();

		return officer.getId() == principal.getActiveRoleId();
	}

	@Override
	public void bind(final Request<WorkPlan> request, final WorkPlan entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		final Collection<Duty> duties = entity.getDuties();
		final Collection<Duty> enabledDuty = this.repository.findManyDuty(request.getPrincipal().getActiveRoleId()).stream().filter(entity::dutyFitsOnPeriod).filter(x -> !duties.contains(x)).filter(x -> !entity.getIsPublic() || x.getIsPublic())
			.collect(Collectors.toList());

		request.getModel().setAttribute("dutyList", duties);
		request.getModel().setAttribute("enabledDuty", enabledDuty);
			
		//Recomendación fechas
		if(!duties.isEmpty()) {
			final Optional<Date> ini = duties.stream().min(Comparator.comparing(Duty::getStartPeriod)).map(Duty::getStartPeriod);
			final Optional<Date> end = duties.stream().max(Comparator.comparing(Duty::getEndPeriod)).map(Duty::getEndPeriod);
							
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

		final Collection<Duty> duties = entity.getDuties();
		final Collection<Duty> enabledDuty = this.repository.findManyDuty(request.getPrincipal().getActiveRoleId()).stream().filter(entity::dutyFitsOnPeriod).filter(x -> !duties.contains(x)).filter(x -> !entity.getIsPublic() || x.getIsPublic())
			.collect(Collectors.toList());																								

		request.unbind(entity, model, "title", "startPeriod", "endPeriod", "isPublic");

		model.setAttribute("dutyList", duties);
		model.setAttribute("enabledDuty", enabledDuty);
		
		//Recomendación fechas
		if(!duties.isEmpty()) {
			final Optional<Date> ini = duties.stream().min(Comparator.comparing(Duty::getStartPeriod)).map(Duty::getStartPeriod);
			final Optional<Date> end = duties.stream().max(Comparator.comparing(Duty::getEndPeriod)).map(Duty::getEndPeriod);
									
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

			errors.state(request, entity.getStartPeriod().before(entity.getEndPeriod()), "startPeriod", "officer.workplan.form.error.startPeriodBefore");
		}

		// Recomendación del periodo de ejecución del workplan 
		if (!errors.hasErrors("startPeriod") && !errors.hasErrors("endPeriod") && entity.getDuties() != null && !entity.getDuties().isEmpty()) {
			final Optional<Date> ini = entity.getDuties().stream().min(Comparator.comparing(Duty::getStartPeriod)).map(Duty::getStartPeriod);
			final Optional<Date> end = entity.getDuties().stream().max(Comparator.comparing(Duty::getEndPeriod)).map(Duty::getEndPeriod);
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

		final Integer dutyId = request.getModel().getInteger("dutySelected");

		if (dutyId != null && !dutyId.equals(-1) && !errors.hasErrors("startPeriod") && !errors.hasErrors("endPeriod")) {
			final Duty duty = this.repository.findOneDutyById(dutyId);
			if (Boolean.FALSE.equals(duty.getIsPublic()) && Boolean.TRUE.equals(entity.getIsPublic())) {
				errors.state(request, false, "dutySelected", "acme.validation.duty-is-private");
			}
			if (!entity.dutyFitsOnPeriod(duty)) {
				errors.state(request, false, "dutySelected", "acme.validation.duty-not-in-period");
			}
			if (entity.getDuties() != null && entity.getDuties().contains(duty)) {
				errors.state(request, false, "dutySelected", "acme.validation.duty-is-present");
			}
		}

	}

	@Override
	public void update(final Request<WorkPlan> request, final WorkPlan entity) {
		assert request != null;
		assert entity != null;

		final Integer dutyId = request.getModel().getInteger("dutySelected");
		final Integer dutyDeleteId = request.getModel().getInteger("dutyDeleteSelected");
		
		if (dutyId != null && !dutyId.equals(-1)) {
			final Duty duty = this.repository.findOneDutyById(dutyId);

			assert duty.getOfficer().getId() == request.getPrincipal().getActiveRoleId();

			final Collection<Duty> ct = entity.getDuties();
			ct.add(duty);
			entity.setDuties(ct);
		}
		
		if (dutyDeleteId != null && !dutyDeleteId.equals(-1)) {
			final Duty duty = this.repository.findOneDutyById(dutyDeleteId);
			
			final Collection<Duty> ct = entity.getDuties();
			
			assert ct.contains(duty);
			
			ct.remove(duty);
			entity.setDuties(ct);
		}

		this.repository.save(entity);
	}
}
