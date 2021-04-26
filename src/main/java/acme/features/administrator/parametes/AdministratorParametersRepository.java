package acme.features.administrator.parametes;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.parameters.Parameters;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AdministratorParametersRepository extends AbstractRepository {


	@Query("select p from Parameters p")
	Parameters find();
	
	@Query("select p.threshold from Parameters p")
	Double findThreshold();
	
	
}
