package acme.features.authenticated.task;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.tasks.Task;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Authenticated;
import acme.framework.services.AbstractListService;

@Service
public class AuthenticatedTaskListService implements AbstractListService<Authenticated, Task> {
	
	@Autowired
	private AuthenticatedTaskRepository repository;
	
	@Override
	public boolean authorise(final Request<Task> request) {
		assert request != null;
		
		return true;
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
	public Collection<Task> findMany(final Request<Task> request) {
		assert request != null;
		
		Collection<Task> result;
		Date current;
		
		current = java.util.Calendar.getInstance().getTime();
		
		result = this.repository.findMany(current);
		
		return result;
	}
}
