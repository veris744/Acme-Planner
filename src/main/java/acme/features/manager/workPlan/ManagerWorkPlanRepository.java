package acme.features.manager.workPlan;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.roles.Manager;
import acme.entities.tasks.Task;
import acme.entities.workPlans.WorkPlan;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface ManagerWorkPlanRepository extends AbstractRepository {
	@Query("select w from WorkPlan w where w.id = ?1")
	WorkPlan findOneWorkPlanById(int id);

	@Query("select w from WorkPlan w where w.manager.id = ?1")
	Collection<WorkPlan> findManyWorkPlansByManagerId(int id);

	@Query("select m from Manager m where m.id = ?1")
	Manager getManagerById(int id);
	
	@Query("select t from Task t where t.manager.id = ?1 order by t.workload desc")
	List<Task> findManyTask(int id);
	
	@Query("select t from Task t")
	Collection<Task> findAvailableTasks();
	
	@Query("select t from Task t where t.id = ?1")
	Task findOneTaskById(int id);
	
//	@Query("select t.startPeriod from WorkPlan w join w.tasks t where w.id=?1 order by t.begin asc" )
//	public List<Date> findStartDateFirstTask(int id);
//	
//	@Query("select t.endPeriod from WorkPlan w join w.tasks t where w.id=?1 order by t.end desc" )
//	public List<Date> findEndDateLastTask(int id);
	
	
	
}
