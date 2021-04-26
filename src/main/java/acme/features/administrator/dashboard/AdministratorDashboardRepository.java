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

import acme.entities.tasks.Task;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AdministratorDashboardRepository extends AbstractRepository {

	@Query("select avg(select count(j) from Job j where j.employer.id = e.id) from Employer e")
	Double averageNumberOfJobsPerEmployer();

	@Query("select avg(select count(a) from Application a where a.worker.id = w.id) from Worker w")
	Double averageNumberOfApplicationsPerWorker();

	@Query("select avg(select count(a) from Application a where exists(select j from Job j where j.employer.id = e.id and a.job.id = j.id)) from Employer e")
	Double averageNumberOfApplicationsPerEmployer();

	@Query("select 1.0 * count(a) / (select count(b) from Application b) from Application a where a.status = acme.entities.jobs.ApplicationStatus.PENDING")
	Double ratioOfPendingApplications();

	@Query("select 1.0 * count(a) / (select count(b) from Application b) from Application a where a.status = acme.entities.jobs.ApplicationStatus.ACCEPTED")
	Double ratioOfAcceptedApplications();

	@Query("select 1.0 * count(a) / (select count(b) from Application b) from Application a where a.status = acme.entities.jobs.ApplicationStatus.REJECTED")
	Double ratioOfRejectedApplications();

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

}
