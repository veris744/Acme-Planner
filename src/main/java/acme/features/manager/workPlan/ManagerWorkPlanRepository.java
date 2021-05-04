package acme.features.manager.workPlan;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.tasks.Task;
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
	
	@Query("select t from Task t where t.manager.id = ?1 order by t.workload desc")
	List<Task> findManyTask(int id);
	
	@Query("select t from Task t where (t.isPublic=1 or t.manager.id =?1)")
	public Collection<Task> findAvailableTasks(int id, int wpid);
	
	@Query("select t.begin from WorkPlan w join w.tasks t where w.id=?1 order by t.begin asc" )
	public List<Date> findStartDateFirstTask(int id);
	
	@Query("select t.end from WorkPlan w join w.tasks t where w.id=?1 order by t.end desc" )
	public List<Date> findEndDateLastTask(int id);
	
	
	
}
