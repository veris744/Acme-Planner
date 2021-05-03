package acme.features.authenticated.manager;

import org.springframework.data.jpa.repository.Query;

import acme.framework.entities.Manager;
import acme.framework.entities.UserAccount;
import acme.framework.repositories.AbstractRepository;

public interface AuthenticatedManagerRepository extends AbstractRepository {
	
	@Query("select m from Manager m where m.userAccount.id = ?1")
	Manager findOneManagerByUserAccountId(int id);
	
	@Query("select ua from UserAccount ua where ua.id = ?1")
	UserAccount findOneUserAccountById(int i);

}
