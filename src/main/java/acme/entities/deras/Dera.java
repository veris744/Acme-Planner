package acme.entities.deras;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import acme.framework.datatypes.Money;
import acme.framework.entities.DomainEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class Dera extends DomainEntity {

	@Pattern(regexp="^\\w{3}-\\d{6}-\\w{2}$")
	@NotNull
	protected String				ticket;
	
	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	protected Date				deadline;
	
	@NotNull
	@Valid
	protected Money				budget;

	@NotNull
	protected Boolean			important;

}
