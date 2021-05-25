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

import acme.entities.roles.Manager;
import acme.entities.spamWords.SpamWordsConstraint;
import acme.entities.tasks.Task;
import acme.framework.entities.DomainEntity;
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
		@SpamWordsConstraint
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
		
		public Double getPeriod() {
			return ((double) this.endPeriod.getTime() - (double) this.startPeriod.getTime())/3600000;
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
		
		// Other methods ----------------------------------------------------------
		
		public boolean taskFitsOnPeriod(final Task task) {

			return (this.startPeriod.before(task.getStartPeriod()) || this.startPeriod.equals(task.getStartPeriod()))
				&& (this.endPeriod.after(task.getEndPeriod()) || this.endPeriod.equals(task.getEndPeriod()));
		}
	
}
