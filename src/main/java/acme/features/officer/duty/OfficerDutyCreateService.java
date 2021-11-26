package acme.features.officer.duty;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.roles.Officer;
import acme.entities.duties.Duty;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.services.AbstractCreateService;

@Service
public class OfficerDutyCreateService implements AbstractCreateService<Officer, Duty> {
	
	@Autowired
	protected OfficerDutyRepository repository;
	
	@Override
	public boolean authorise (final Request<Duty> request) {
		assert request != null;
		return true;
	}
	
	@Override
	public void bind(final Request<Duty> request, final Duty entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		request.bind(entity, errors);
	}
	
	@Override
	public void unbind(final Request<Duty> request, final Duty entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "title", "startPeriod", 
			"endPeriod", "workload", "description", "link","isPublic");
	}
	
	@Override
	public Duty instantiate(final Request<Duty> request) {
		assert request != null;
		
		Duty result;
		Officer officer;
		
		officer = this.repository.getOfficerById(request.getPrincipal().getActiveRoleId());
		
		
		result = new Duty();
		result.setOfficer(officer);
		result.setIsPublic(true);
		return result;
	}
	
	@Override
	public void validate(final Request<Duty> request, final Duty entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
		
		final Double workload = entity.getWorkload();
		
		
		if(!errors.hasErrors("startPeriod") && !errors.hasErrors("endPeriod")) {
			errors.state(request, entity.getStartPeriod().before(entity.getEndPeriod()), "startPeriod", "officer.duty.form.error.startPeriodBefore");
		}
		
		if(!errors.hasErrors("workload")) {
			final Integer horas = workload.intValue();
			try{
				final Integer minutos = BigDecimal.valueOf(workload).subtract(BigDecimal.valueOf(horas)).movePointRight(2).intValueExact(); //Usamos BigDecimal porque double no calcula correctamente la parte decimal
				errors.state(request, minutos<=59, "workload", "officer.duty.form.error.workloadminutes");
			}catch (final Exception e) {
				final boolean error=false;
				errors.state(request, error, "workload", "officer.duty.form.error.workloadminutes2");
			}
			errors.state(request, horas<=99, "workload", "officer.duty.form.error.workloadhours");
	
		}
		
		if(!errors.hasErrors("startPeriod") && !errors.hasErrors("endPeriod") && !errors.hasErrors("workload")) {
			final Integer horas = workload.intValue();
			final Double minutos = workload - horas;
			final Date startPeriod = entity.getStartPeriod();
			final Date endPeriod = entity.getEndPeriod();
			final Long workloadMax = endPeriod.getTime()-startPeriod.getTime();
			final Integer worklodMinutosMax = (int) (workloadMax/(60000));
			final Integer workloadMinutos = (int) (horas*60+minutos*100);
			errors.state(request, worklodMinutosMax>=workloadMinutos, "workload", "officer.duty.form.error.workloadmax");
		}
		
		
		if(!errors.hasErrors("startPeriod")) {
			
			errors.state(request, entity.getStartPeriod().after(java.util.Calendar.getInstance().getTime()), "startPeriod", "officer.duty.form.error.startPeriodCurrent");
		}
		

	
		
		
	}
	
	@Override
	public void create(final Request<Duty> request, final Duty entity) {
		assert request != null;
		assert entity != null;
		
		Officer officer;
		
		officer = this.repository.getOfficerById(request.getPrincipal().getActiveRoleId());
		
		entity.setOfficer(officer);
		
		this.repository.save(entity);
	}
}