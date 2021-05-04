package acme.features.manager.workPlan;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import acme.entities.workPlans.WorkPlan;
import acme.framework.components.BasicCommand;
import acme.framework.controllers.AbstractController;
import acme.framework.entities.Manager;



@Controller
@RequestMapping("/manager/work-plan/")
public class ManagerWorkPlanController extends AbstractController<Manager, WorkPlan>{
	
	@Autowired
	private ManagerWorkPlanListService listService;

	@Autowired
	private ManagerWorkPlanShowService showService;
	
	@Autowired
	private ManagerWorkPlanCreateService createService;
	
	@Autowired
	protected ManagerWorkPlanUpdateService	updateService;

	@Autowired
	protected ManagerWorkPlanDeleteService	deleteService;
	
//	@Autowired
//	protected ManagerWorkPlanAddTaskService addTaskService;
	
	@PostConstruct
	private void initialise() {
		super.addBasicCommand(BasicCommand.LIST, this.listService);
		super.addBasicCommand(BasicCommand.SHOW, this.showService);
		super.addBasicCommand(BasicCommand.CREATE, this.createService);
		super.addBasicCommand(BasicCommand.UPDATE, this.updateService);
		super.addBasicCommand(BasicCommand.DELETE, this.deleteService);
//		super.addCustomCommand(CustomCommand.ADD_TASK, BasicCommand.UPDATE,this.addTaskService);
	}

}
