/*
 * AdministratorDashboardShowService.java
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
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.duties.Duty;
import acme.entities.workPlans.WorkPlan;
import acme.forms.Dashboard;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Administrator;
import acme.framework.services.AbstractShowService;

@Service
public class AdministratorDashboardShowService implements AbstractShowService<Administrator, Dashboard> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AdministratorDashboardRepository repository;

	// AbstractShowService<Administrator, Dashboard> interface ----------------


	@Override
	public boolean authorise(final Request<Dashboard> request) {
		assert request != null;

		return true;
	}

	@Override
	public void unbind(final Request<Dashboard> request, final Dashboard entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, //
			"numberOfPublicDuties", "numberOfPrivateDuties", "numberOfFinishedDuties", "numberOfNonFinishedDuties", 
			"minimumWorkload", "maximumWorkload", "averageWorkload", "deviationWorkload", 
			"minimumPeriod", "maximumPeriod", "averagePeriod", "deviationPeriod",
			"numberOfPublicWorkPlans","numberOfPrivateWorkPlans","numberOfFinishedWorkPlans",
			"numberOfNonFinishedWorkPlans","minimumWorkloadWorkPlans","maximumWorkloadWorkPlans",
			"averageWorkloadWorkPlans","deviationWorkloadWorkPlans","minimumPeriodWorkPlans",
			"maximumPeriodWorkPlans","averagePeriodWorkPlans","deviationPeriodWorkPlans");
	}

	@Override
	public Dashboard findOne(final Request<Dashboard> request) {
		assert request != null;

		
		Date current;
		current = java.util.Calendar.getInstance().getTime();
		
		Dashboard result;
		Integer numberOfPublicDuties;
		Integer numberOfPrivateDuties;
		Integer numberOfFinishedDuties;
		Integer numberOfNonFinishedDuties;
		Double minimumWorkload;
		Double maximumWorkload;
		Double averageWorkload;
		Double deviationWorkload;
		Double minimumPeriod;
		Double maximumPeriod;
		Double averagePeriod;
		Double deviationPeriod;
		
		Integer numberOfPublicWorkPlans;
		Integer numberOfPrivateWorkPlans;
		Integer numberOfFinishedWorkPlans;
		Integer numberOfNonFinishedWorkPlans;
		Double minimumWorkloadWorkPlans;
		Double maximumWorkloadWorkPlans;
		Double averageWorkloadWorkPlans;
		Double deviationWorkloadWorkPlans;
		Double minimumPeriodWorkPlans;
		Double maximumPeriodWorkPlans;
		Double averagePeriodWorkPlans;
		Double deviationPeriodWorkPlans;



		final Collection<Duty> duties = this.repository.getDuties();
		minimumPeriod = duties.stream().collect(Collectors.toList()).get(0).getPeriod();
		maximumPeriod = duties.stream().collect(Collectors.toList()).get(0).getPeriod();
		averagePeriod = 0.0;
		for (final Duty t:duties) {
			if(minimumPeriod > t.getPeriod()) {
				minimumPeriod = t.getPeriod();
			}
			if(maximumPeriod < t.getPeriod()) {
				maximumPeriod = t.getPeriod();
			}
			averagePeriod += t.getPeriod();
		}
		averagePeriod = averagePeriod/duties.size();
		deviationPeriod = 0.0;
		for (final Duty t:duties) {
			deviationPeriod += Math.pow(t.getPeriod()-averagePeriod, 2);
		}
		deviationPeriod = Math.sqrt(deviationPeriod/duties.size());
		
		
		
		final Collection<WorkPlan> workPlans = this.repository.getWorkPlans();
		minimumPeriodWorkPlans = workPlans.stream().collect(Collectors.toList()).get(0).getPeriod();
		maximumPeriodWorkPlans = workPlans.stream().collect(Collectors.toList()).get(0).getPeriod();
		averagePeriodWorkPlans = 0.0;
		for (final WorkPlan w:workPlans) {
			if(minimumPeriodWorkPlans > w.getPeriod()) {
				minimumPeriodWorkPlans = w.getPeriod();
			}
			if(maximumPeriodWorkPlans < w.getPeriod()) {
				maximumPeriodWorkPlans = w.getPeriod();
			}
			averagePeriodWorkPlans += w.getPeriod();
		}
		averagePeriodWorkPlans = averagePeriodWorkPlans/workPlans.size();
		deviationPeriodWorkPlans = 0.0;
		for (final WorkPlan w:workPlans) {
			deviationPeriodWorkPlans += Math.pow(w.getPeriod()-averagePeriodWorkPlans, 2);
		}
		deviationPeriodWorkPlans = Math.sqrt(deviationPeriodWorkPlans/workPlans.size());
		

		numberOfPublicDuties = this.repository.numberOfPublicDuties();
		numberOfPrivateDuties = this.repository.numberOfPrivateDuties();
		numberOfFinishedDuties = this.repository.numberOfFinishedDuties(current);
		numberOfNonFinishedDuties = this.repository.numberOfNonFinishedDuties(current);
		minimumWorkload = this.repository.minimumWorkload();
		maximumWorkload = this.repository.maximumWorkload();
		averageWorkload = this.repository.averageWorkload();
		deviationWorkload = Math.sqrt(this.repository.deviationWorkload(averageWorkload)/this.repository.getDuties().size());
		
		
		numberOfPublicWorkPlans = this.repository.numberOfPublicWorkPlans();
		numberOfPrivateWorkPlans = this.repository.numberOfPrivateWorkPlans();
		numberOfFinishedWorkPlans = this.repository.numberOfFinishedWorkPlans(current);
		numberOfNonFinishedWorkPlans = this.repository.numberOfNonFinishedWorkPlans(current);
		minimumWorkloadWorkPlans = this.repository.getWorkPlans().stream().mapToDouble(WorkPlan::getWorkload).min().orElse(0);
		maximumWorkloadWorkPlans = this.repository.getWorkPlans().stream().mapToDouble(WorkPlan::getWorkload).max().orElse(0);
		averageWorkloadWorkPlans = this.repository.getWorkPlans().stream().mapToDouble(WorkPlan::getWorkload).average().orElse(0);
		deviationWorkloadWorkPlans = Math.sqrt(Math.pow((this.repository.getWorkPlans().stream().
			mapToDouble(WorkPlan::getWorkload).iterator().next()-this.repository.getWorkPlans().stream().
			mapToDouble(WorkPlan::getWorkload).average().orElse(0)),2)/this.repository.getWorkPlans().size());

		
		result = new Dashboard();
		result.setNumberOfPublicDuties(numberOfPublicDuties);
		result.setNumberOfPrivateDuties(numberOfPrivateDuties);
		result.setNumberOfFinishedDuties(numberOfFinishedDuties);
		result.setNumberOfNonFinishedDuties(numberOfNonFinishedDuties);
		result.setMinimumWorkload(minimumWorkload);
		result.setMaximumWorkload(maximumWorkload);
		result.setAverageWorkload(averageWorkload);
		result.setMinimumPeriod(minimumPeriod);
		result.setMaximumPeriod(maximumPeriod);
		result.setAveragePeriod(averagePeriod);
		result.setDeviationPeriod(deviationPeriod);
		result.setDeviationWorkload(deviationWorkload);
		
		result.setNumberOfPublicWorkPlans(numberOfPublicWorkPlans);
		result.setNumberOfPrivateWorkPlans(numberOfPrivateWorkPlans);
		result.setNumberOfFinishedWorkPlans(numberOfFinishedWorkPlans);
		result.setNumberOfNonFinishedWorkPlans(numberOfNonFinishedWorkPlans);
		result.setMinimumWorkloadWorkPlans(minimumWorkloadWorkPlans);
		result.setMaximumWorkloadWorkPlans(maximumWorkloadWorkPlans);
		result.setAverageWorkloadWorkPlans(averageWorkloadWorkPlans);
		result.setMinimumPeriodWorkPlans(minimumPeriodWorkPlans);
		result.setMaximumPeriodWorkPlans(maximumPeriodWorkPlans);
		result.setAveragePeriodWorkPlans(averagePeriodWorkPlans);
		result.setDeviationPeriodWorkPlans(deviationPeriodWorkPlans);
		result.setDeviationWorkloadWorkPlans(deviationWorkloadWorkPlans);

		return result;
	}

	
}
