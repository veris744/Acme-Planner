package acme.features.anonymous.workPlan;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.workPlans.WorkPlan;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AnonymousWorkPlanRepository extends AbstractRepository {
	
	@Query("select w from WorkPlan w where w.isPublic = true AND w.endPeriod > :current")
	Collection<WorkPlan> findMany(Date current);

	@Query("select w from WorkPlan w where w.id = ?1")
	WorkPlan findOneWorkPlanById(int id);
}
