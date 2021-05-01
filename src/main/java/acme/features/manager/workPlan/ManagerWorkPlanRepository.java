package acme.features.manager.workPlan;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.workPlans.WorkPlan;
import acme.framework.entities.Manager;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface ManagerWorkPlanRepository extends AbstractRepository {
	@Query("select w from WorkPlan w where w.id = ?1")
	WorkPlan findOneWorkPlanById(int id);

	@Query("select w from WorkPlan w where w.manager.id = ?1")
	Collection<WorkPlan> findManyWorkPlansByManagerId(int id);

	@Query("select m from Manager m where m.id = ?1")
	Manager getManagerById(int id);
	
}
