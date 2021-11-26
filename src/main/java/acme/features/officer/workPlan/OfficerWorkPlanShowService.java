package acme.features.officer.workPlan;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.roles.Officer;
import acme.entities.duties.Duty;
import acme.entities.workPlans.WorkPlan;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Principal;
import acme.framework.services.AbstractShowService;

@Service 
public class OfficerWorkPlanShowService implements AbstractShowService<Officer, WorkPlan> {

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
	public void unbind(final Request<WorkPlan> request, final WorkPlan entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;
		
		
		
		final Collection<Duty> duties = entity.getDuties();
		final Collection<Duty> enabledDuty = this.repository.findManyDuty(request.getPrincipal().getActiveRoleId()).stream()
			.filter(entity::dutyFitsOnPeriod)
			.filter(x->!duties.contains(x))
			.filter(x->!entity.getIsPublic() || x.getIsPublic())
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
		
		final WorkPlan result;
		int id;
		
		id = request.getModel().getInteger("id");	
		result = this.repository.findOneWorkPlanById(id);
		
		return result;
	}
}
