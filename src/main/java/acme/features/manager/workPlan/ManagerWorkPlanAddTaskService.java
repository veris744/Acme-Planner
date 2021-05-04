
//package acme.features.manager.workPlan;
//
//import java.util.Collection;
//import java.util.List;
//import java.util.stream.Collectors;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import acme.entities.tasks.Task;
//import acme.entities.workPlans.WorkPlan;
//import acme.features.manager.task.ManagerTaskRepository;
//import acme.framework.components.Errors;
//import acme.framework.components.Model;
//import acme.framework.components.Request;
//import acme.framework.entities.Manager;
//import acme.framework.entities.Principal;
//import acme.framework.services.AbstractUpdateService;
//
//@Service
//public class ManagerWorkPlanAddTaskService implements AbstractUpdateService<Manager, WorkPlan> {
//
//	@Autowired
//	private ManagerWorkPlanRepository	repository;
//
//	@Autowired
//	ManagerTaskRepository				Taskrepository;
//
//
//	@Override
//	public boolean authorise(final Request<WorkPlan> request) {
//		assert request != null;
//		final boolean result;
//		WorkPlan workplan;
//		int workplanId;
//		Manager manager;
//		Principal principal;
//
//		workplanId = request.getModel().getInteger("id");
//		workplan = this.repository.findOneWorkPlanById(workplanId);
//		manager = workplan.getManager();
//		principal = request.getPrincipal();
//		result = (manager.getUserAccount().getId() == principal.getAccountId());
//		return result;
//	}
//
//	@Override
//	public void bind(final Request<WorkPlan> request, final WorkPlan entity, final Errors errors) {
//		assert request != null;
//		assert entity != null;
//		assert errors != null;
//		request.bind(entity, errors);
//	}
//
//	@Override
//	public void unbind(final Request<WorkPlan> request, final WorkPlan entity, final Model model) {
//		assert request != null;
//		assert entity != null;
//		assert model != null;
//		//Lista con las Task Avaiable
//
//		request.unbind(entity, model, "isPublic", "begin", "end", "tasks", "title", "executionPeriod", "workload");
//	}
//
//	@Override
//	public WorkPlan findOne(final Request<WorkPlan> request) {
//		final int id = request.getModel().getInteger("id");
//		return this.repository.findOneWorkPlanById(id);
//	}
//
//	@Override
//	public void validate(final Request<WorkPlan> request, final WorkPlan entity, final Errors errors) {
//		assert request != null;
//		assert entity != null;
//		assert errors != null;
//		
//		if(request.getModel().hasAttribute("taskSelected")) {
//			final WorkPlan workplan=this.repository.findOneWorkPlanById(entity.getId());
//			final Task task= (Task) this.Taskrepository.findById(request.getModel().getInteger("taskSelected")).orElse(null);
//			final Collection<Task> ts = workplan.getTasks();
//			
//			if(Boolean.TRUE.equals(workplan.getIsPublic()))
//				errors.state(request, task!=null && Boolean.TRUE.equals(task.getIsPublic()), "taskSelected", "manager.workplan.form.addTask.error.public");
//				
//			errors.state(request, task!=null && task.getStartPeriod().after(workplan.getStartPeriod()) && task.getEndPeriod().before(workplan.getEndPeriod()) && workplan.getExecutionPeriod() >= 
//				(ts.stream().mapToDouble(Task::getPeriod).sum() + task.getPeriod()), "taskSelected", 
//				"manager.workplan.form.addTask.error.executionPeriod");
//			
//			final Manager manager = workplan.getManager();
//			final Principal principal = request.getPrincipal();
//			final Boolean Mywp = manager.getUserAccount().getId() == principal.getAccountId();
//			final Boolean valid = Mywp && workplan.getTasks().stream().filter(x-> x.getIsPublic().equals(false)).count()==0 && !workplan.getIsPublic();
//			
//			List<Task> tl=this.repository.findAvailableTasks(manager.getId(), workplan.getId()).stream().filter(x->!workplan.getTasks().contains(x)).collect(Collectors.toList());
//			tl=tl.stream().filter(x->x.getIsPublic()).collect(Collectors.toList());
//			
//			request.getModel().setAttribute("Mywp", Mywp);
//			request.getModel().setAttribute("valid", valid);
//			request.getModel().setAttribute("task", workplan.getTasks());
//			request.getModel().setAttribute("tasksEnabled", tl);
//			
//		}else {
//			errors.state(request, false, "taskSelected", "manager.workplan.form.addTask.error.task");
//		}
//		request.unbind(entity, request.getModel(), "isPublic","begin","end","task","title","executionPeriod","workload");
//		
//		if(errors.hasErrors()) {
//			request.getModel().setAttribute("Adderrors", true);
//		}
//	}
//	
//	
//	@Override
//	public void update(final Request<WorkPlan> request, final WorkPlan entity) {
//		final WorkPlan workplan = this.repository.findOneWorkPlanById(entity.getId());
//		final Task task = (Task) this.Taskrepository.findById(request.getModel().getInteger("taskSelected")).orElse(null);
//		final Collection<Task> ct=workplan.getTasks();
//		ct.add(task);
//		workplan.setTasks(ct);
//		workplan.setWorkload();
//		
//		this.repository.save(workplan);
//	}
//
//}
