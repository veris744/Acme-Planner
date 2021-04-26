/*
 * AdministratorParametersUpdateService.java
 *
 * Copyright (c) 2012-2021 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.administrator.parametes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.parameters.Parameters;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Administrator;
import acme.framework.services.AbstractUpdateService;

@Service
public class AdministratorParametersUpdateService implements AbstractUpdateService<Administrator, Parameters> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AdministratorParametersRepository repository;

	// AbstractListService<Administrator, Parameters> -------------------------------------


	@Override
	public boolean authorise(final Request<Parameters> request) {
		assert request != null;
		return true;
	}

	@Override
	public void validate(final Request<Parameters> request, final Parameters entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;	
	}

	@Override
	public void bind(final Request<Parameters> request, final Parameters entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		request.bind(entity, errors);
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

		Parameters result;
		result = this.repository.find();

		return result;
	}

	@Override
	public void update(final Request<Parameters> request, final Parameters entity) {
		assert request != null;
		assert entity != null;

		this.repository.save(entity);
	}

}
