package acme.features.officer.duty;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.roles.Officer;
import acme.entities.duties.Duty;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Principal;
import acme.framework.services.AbstractUpdateService;

@Service
public class OfficerDutyUpdateService implements AbstractUpdateService<Officer, Duty>  {

	@Autowired
	protected OfficerDutyRepository repository;

	
	@Override
	public boolean authorise(final Request<Duty> request) {
		assert request != null;
		
		int dutyId;
		Duty duty;
		Officer officer;
		Principal principal;
		
		dutyId = request.getModel().getInteger("id");
		duty = this.repository.findOneDutyById(dutyId);
		officer = duty.getOfficer();
		principal = request.getPrincipal();
		
		return officer.getId() == principal.getActiveRoleId();
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
		
		request.unbind(entity, model, "title", "startPeriod", "endPeriod", "workload",
			"description", "link", "isPublic");
		
	}

	@Override
	public Duty findOne(final Request<Duty> request) {
		assert request != null;

		Duty result;
		int id;

		id = request.getModel().getInteger("id");
		result = this.repository.findOneDutyById(id);

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
			final Double minutos = workload - horas;
			errors.state(request, minutos<0.6, "workload", "officer.duty.form.error.workloadminutes");
	
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
		
	}

	@Override
	public void update(final Request<Duty> request, final Duty entity) {
		assert request != null;
		assert entity != null;

		this.repository.save(entity);
		
	}

}
