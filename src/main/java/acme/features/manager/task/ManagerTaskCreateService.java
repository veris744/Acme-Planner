package acme.features.manager.task;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.tasks.Task;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Manager;
import acme.framework.services.AbstractCreateService;

@Service
public class ManagerTaskCreateService implements AbstractCreateService<Manager, Task> {
	
	@Autowired
	protected ManagerTaskRepository repository;
	
	@Override
	public boolean authorise (final Request<Task> request) {
		assert request != null;
		return true;
	}
	
	@Override
	public void bind(final Request<Task> request, final Task entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		request.bind(entity, errors);
	}
	
	@Override
	public void unbind(final Request<Task> request, final Task entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "title", "startPeriod", 
			"endPeriod", "workload", "description", "link");
	}
	
	@Override
	public Task instantiate(final Request<Task> request) {
		assert request != null;
		
		Task result;
		Date startdate;
		Date enddate;
		
		startdate = new Date(System.currentTimeMillis()-2);
		enddate = new Date(System.currentTimeMillis()-1);
		
		result = new Task();
		result.setTitle("Create a Causes functionality");
		result.setStartPeriod(startdate);
		result.setEndPeriod(enddate);
		result.setWorkload(1.0);
		result.setDescription("As a client, we can create a cause that contains a name and description (string), a budget target (numeric), and an acti-ve non profit organization (string) that will use the budget for the cause.");
		result.setLink("http://www.spring.com");
		result.setIsPublic(true);
		return result;
	}
	
	@Override
	public void validate(final Request<Task> request, final Task entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
		
	}
	
	@Override
	public void create(final Request<Task> request, final Task entity) {
		assert request != null;
		assert entity != null;
		
		this.repository.save(entity);
	}
}