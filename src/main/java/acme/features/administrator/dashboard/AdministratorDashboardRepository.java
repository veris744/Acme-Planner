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

import acme.entities.duties.Duty;
import acme.entities.workPlans.WorkPlan;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AdministratorDashboardRepository extends AbstractRepository {



	@Query("select count(t) from Duty t where t.isPublic = true")
	Integer numberOfPublicDuties();

	@Query("select count(t) from Duty t where t.isPublic = false")
	Integer numberOfPrivateDuties();

	@Query("select count(t) from Duty t where t.endPeriod < :current")
	Integer numberOfFinishedDuties(Date current);

	@Query("select count(t) from Duty t where t.endPeriod > :current")
	Integer numberOfNonFinishedDuties(Date current);
	
	@Query("select min(t.workload) from Duty t")
	Double minimumWorkload();

	@Query("select max(t.workload) from Duty t")
	Double maximumWorkload();
	
	@Query("select avg(t.workload) from Duty t")
	Double averageWorkload();

	@Query("select sum((t.workload - :average)*(t.workload - :average)) from Duty t")
	Double deviationWorkload(Double average);

	@Query("select t from Duty t")
	Collection<Duty> getDuties();
	
	
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
	

}
