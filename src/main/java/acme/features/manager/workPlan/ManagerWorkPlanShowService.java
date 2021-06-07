package acme.features.manager.workPlan;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.roles.Manager;
import acme.entities.tasks.Task;
import acme.entities.workPlans.WorkPlan;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Principal;
import acme.framework.services.AbstractShowService;

@Service 
public class ManagerWorkPlanShowService implements AbstractShowService<Manager, WorkPlan> {

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
	public void unbind(final Request<WorkPlan> request, final WorkPlan entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;
		
		
		
		final Collection<Task> tasks = entity.getTasks();
		final Collection<Task> enabledTask = this.repository.findManyTask(request.getPrincipal().getActiveRoleId()).stream()
			.filter(entity::taskFitsOnPeriod)
			.filter(x->!tasks.contains(x))
			.filter(x->!entity.getIsPublic() || x.getIsPublic())
			.collect(Collectors.toList());
		
		request.unbind(entity, model, "title", "startPeriod", "endPeriod", "isPublic"); 
		
		model.setAttribute("taskList", tasks);
		model.setAttribute("enabledTask", enabledTask);
		
		//Recomendación fechas
		if(!tasks.isEmpty()) {
			final Optional<Date> ini = tasks.stream().min(Comparator.comparing(Task::getStartPeriod)).map(Task::getStartPeriod);
			final Optional<Date> end = tasks.stream().max(Comparator.comparing(Task::getEndPeriod)).map(Task::getEndPeriod);
						
			System.out.println("AAA");
					
			if(ini.isPresent() && end.isPresent()) {
				final LocalDateTime inicio = ini.get().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
				final LocalDateTime fin = end.get().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
									
				System.out.println(inicio);
				System.out.println(fin);
							
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
