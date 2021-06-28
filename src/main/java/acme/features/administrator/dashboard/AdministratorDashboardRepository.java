/*
 * AdministratorDashboardRepository.java
 *
 * Copyright (c) 2012-2021 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.administrator.dashboard;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.shouts.Shout;
import acme.entities.tasks.Task;
import acme.entities.workPlans.WorkPlan;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AdministratorDashboardRepository extends AbstractRepository {



	@Query("select count(t) from Task t where t.isPublic = true")
	Integer numberOfPublicTasks();

	@Query("select count(t) from Task t where t.isPublic = false")
	Integer numberOfPrivateTasks();

	@Query("select count(t) from Task t where t.endPeriod < :current")
	Integer numberOfFinishedTasks(Date current);

	@Query("select count(t) from Task t where t.endPeriod > :current")
	Integer numberOfNonFinishedTasks(Date current);
	
	@Query("select min(t.workload) from Task t")
	Double minimumWorkload();

	@Query("select max(t.workload) from Task t")
	Double maximumWorkload();
	
	@Query("select avg(t.workload) from Task t")
	Double averageWorkload();

	@Query("select sum((t.workload - :average)*(t.workload - :average)) from Task t")
	Double deviationWorkload(Double average);

	@Query("select t from Task t")
	Collection<Task> getTasks();
	
	
	//Work plans
	@Query("select count(w) from WorkPlan w where w.isPublic = true")
	Integer numberOfPublicWorkPlans();

	@Query("select count(w) from WorkPlan w where w.isPublic = false")
	Integer numberOfPrivateWorkPlans();

	@Query("select count(w) from WorkPlan w where w.endPeriod < :current")
	Integer numberOfFinishedWorkPlans(Date current);

	@Query("select count(w) from WorkPlan w where w.endPeriod > :current")
	Integer numberOfNonFinishedWorkPlans(Date current);
	
	@Query("select w from WorkPlan w")
	Double minimumWorkloadWorkPlans();

	@Query("select w from WorkPlan w")
	Double maximumWorkloadWorkPlans();
	
	@Query("select w from WorkPlan w")
	Double averageWorkloadWorkPlans();

	@Query("select w from WorkPlan w")
	Double deviationWorkloadWorkPlans(Double average);

	@Query("select w from WorkPlan w")
	Collection<WorkPlan> getWorkPlans();
	
	@Query("select s from Shout s where s.dera.important = true")
	Collection<Shout> getImportantDeras();

	@Query("select s from Shout s where s.dera.important = false")
	Collection<Shout> getNonImportantDeras();

	@Query("select s from Shout s where s.dera.budget.amount = 0.00")
	Collection<Shout> getNumberShouts0Budget();

	@Query("select s from Shout s")
	Collection<Shout> getNumberShouts();
	
	@Query("select avg(s.dera.budget.amount) from Shout s where s.dera.budget.currency = 'EUR'")
	Double averageBudgetEUR();

	@Query("select sum((s.dera.budget.amount - :average)*(s.dera.budget.amount - :average)) from Shout s where s.dera.budget.currency = 'EUR'")
	Double deviationBudgetEUR(Double average);
	

	@Query("select avg(s.dera.budget.amount) from Shout s where s.dera.budget.currency = 'USD'")
	Double averageBudgetUSD();

	@Query("select sum((s.dera.budget.amount - :average)*(s.dera.budget.amount - :average)) from Shout s where s.dera.budget.currency = 'USD'")
	Double deviationBudgetUSD(Double average);
}
