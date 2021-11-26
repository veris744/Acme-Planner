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

import acme.entities.roles.Officer;
import acme.entities.spamWords.SpamWordsConstraint;
import acme.entities.duties.Duty;
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
			return this.duties.stream().mapToDouble(Duty::getWorkload).sum();
		}
		
		public Double getPeriod() {
			return ((double) this.endPeriod.getTime() - (double) this.startPeriod.getTime())/3600000;
		}
		
		// Relationships ----------------------------------------------------------
		
		@ManyToMany(fetch = FetchType.EAGER)
		@JoinTable(name = "duty_work_plan", 
        joinColumns = @JoinColumn(name = "work_plan_id"), 
        inverseJoinColumns = @JoinColumn(name = "duty_id"))
		protected Collection<@Valid Duty> 		duties;
		
		@NotNull
		@Valid
		@ManyToOne(optional = false)
		protected Officer			officer;
		
		// Other methods ----------------------------------------------------------
		
		public boolean dutyFitsOnPeriod(final Duty duty) {

			return (this.startPeriod.before(duty.getStartPeriod()) || this.startPeriod.equals(duty.getStartPeriod()))
				&& (this.endPeriod.after(duty.getEndPeriod()) || this.endPeriod.equals(duty.getEndPeriod()));
		}
	
}
