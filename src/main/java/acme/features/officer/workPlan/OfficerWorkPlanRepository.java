package acme.features.officer.workPlan;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.roles.Officer;
import acme.entities.duties.Duty;
import acme.entities.workPlans.WorkPlan;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface OfficerWorkPlanRepository extends AbstractRepository {
	@Query("select w from WorkPlan w where w.id = ?1")
	WorkPlan findOneWorkPlanById(int id);

	@Query("select w from WorkPlan w where w.officer.id = ?1")
	Collection<WorkPlan> findManyWorkPlansByOfficerId(int id);

	@Query("select m from Officer m where m.id = ?1")
	Officer getOfficerById(int id);
	
	@Query("select t from Duty t where t.officer.id = ?1 order by t.workload desc")
	List<Duty> findManyDuty(int id);
	
	@Query("select t from Duty t")
	Collection<Duty> findAvailableDuties();
	
	@Query("select t from Duty t where t.id = ?1")
	Duty findOneDutyById(int id);
	
//	@Query("select t.startPeriod from WorkPlan w join w.duties t where w.id=?1 order by t.begin asc" )
//	public List<Date> findStartDateFirstDuty(int id);
//	
//	@Query("select t.endPeriod from WorkPlan w join w.duties t where w.id=?1 order by t.end desc" )
//	public List<Date> findEndDateLastDuty(int id);
	
	
	
}
