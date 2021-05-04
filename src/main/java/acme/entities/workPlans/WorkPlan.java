package acme.entities.workPlans;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import acme.entities.tasks.Task;
import acme.framework.entities.DomainEntity;
import acme.framework.entities.Manager;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class WorkPlan extends DomainEntity{

	// Serialisation identifier -----------------------------------------------

		protected static final long	serialVersionUID	= 1L;

		// Attributes -------------------------------------------------------------

		@NotEmpty
		@Length(min = 1, max = 80)
		protected String			title;

		@Temporal(TemporalType.TIMESTAMP)
		@NotNull
		protected Date				startPeriod;
		
		@Temporal(TemporalType.TIMESTAMP)
		@NotNull
		protected Date				endPeriod;
		
		@NotNull
		protected Boolean			isPublic;

		// Derived attributes -----------------------------------------------------

		public Double getWorkload() {
			return this.tasks.stream().mapToDouble(Task::getWorkload).sum();
		}
		
		// Relationships ----------------------------------------------------------
		
		@ManyToMany(fetch = FetchType.EAGER)
		@JoinTable(name = "task_work_plan", 
        joinColumns = @JoinColumn(name = "work_plan_id"), 
        inverseJoinColumns = @JoinColumn(name = "task_id"))
		protected Collection<@Valid Task> 		tasks;
		
		@NotNull
		@Valid
		@ManyToOne(optional = false)
		protected Manager			manager;
		
		//Prueba Iván
		protected double workload;
		protected double executionPeriod;
		
		@Temporal(TemporalType.TIMESTAMP)
		@NotNull
		protected Date begin;
		
		@Temporal(TemporalType.TIMESTAMP)
		@NotNull
		protected Date end;
		
		public void setWorkload() {
			this.workload = this.tasks.stream().mapToDouble(Task::getWorkload).sum();
		}
		public void setExecutionPeriod() {
			this.executionPeriod = (double) (this.end.getTime() - this.begin.getTime()) / (1000 * 3600);
		}
}
