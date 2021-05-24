package acme.features.administrator.parameters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.parameters.Parameters;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Administrator;
import acme.framework.services.AbstractShowService;

@Service
public class AdministratorParametersShowService implements AbstractShowService<Administrator, Parameters> {

	@Autowired
	protected AdministratorParametersRepository repository;
	
	@Override
	public boolean authorise(final Request<Parameters> request) {
		assert request != null;
		return true;
	}

	@Override
	public void unbind(final Request<Parameters> request, final Parameters entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "threshold"); 
	}

	@Override
	public Parameters findOne(final Request<Parameters> request) {
		assert request != null;
		
		final Parameters result;
		result = this.repository.find();

		return result;
	}
}
