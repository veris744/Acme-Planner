package acme.features.officer.workPlan;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import acme.entities.roles.Officer;
import acme.entities.workPlans.WorkPlan;
import acme.framework.components.BasicCommand;
import acme.framework.controllers.AbstractController;



@Controller
@RequestMapping("/officer/work-plan/")
public class OfficerWorkPlanController extends AbstractController<Officer, WorkPlan>{
	
	@Autowired
	private OfficerWorkPlanListService listService;

	@Autowired
	private OfficerWorkPlanShowService showService;
	
	@Autowired
	private OfficerWorkPlanCreateService createService;
	
	@Autowired
	protected OfficerWorkPlanUpdateService	updateService;

	@Autowired
	protected OfficerWorkPlanDeleteService	deleteService;
	
	@PostConstruct
	private void initialise() {
		super.addBasicCommand(BasicCommand.LIST, this.listService);
		super.addBasicCommand(BasicCommand.SHOW, this.showService);
		super.addBasicCommand(BasicCommand.CREATE, this.createService);
		super.addBasicCommand(BasicCommand.UPDATE, this.updateService);
		super.addBasicCommand(BasicCommand.DELETE, this.deleteService);
	}

}
