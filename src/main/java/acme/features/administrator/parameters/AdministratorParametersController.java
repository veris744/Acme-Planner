package acme.features.administrator.parameters;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import acme.entities.parameters.Parameters;
import acme.framework.components.BasicCommand;
import acme.framework.controllers.AbstractController;
import acme.framework.entities.Administrator;

@Controller
@RequestMapping("/administrator/parameters/")
public class AdministratorParametersController extends AbstractController<Administrator, Parameters> {
	
	@Autowired
	private AdministratorParametersShowService showService;

	@Autowired
	private AdministratorParametersUpdateService updateService;
	
	
	@PostConstruct
	private void initialise() {
		super.addBasicCommand(BasicCommand.SHOW, this.showService);
		super.addBasicCommand(BasicCommand.UPDATE, this.updateService);
	}
}