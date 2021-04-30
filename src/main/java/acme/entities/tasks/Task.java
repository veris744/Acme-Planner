package acme.entities.tasks;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.entities.spamWords.SpamWordsConstraint;
import acme.framework.entities.DomainEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Task extends DomainEntity{
	
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
	@Min(0)
	protected Double			workload;
	
	@NotEmpty
	@Length(min = 1, max = 500)
	@SpamWordsConstraint
	protected String			description;
	
	@URL
	protected String			link;
	
	@NotNull
	protected Boolean			isPublic; //Si lo pongo como boolean da error al popular la DB

	public Double getPeriod() {
		return ((double) this.endPeriod.getTime() - (double) this.startPeriod.getTime())/3600000;
	}
	

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------
}
