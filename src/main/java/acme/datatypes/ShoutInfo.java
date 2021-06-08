package acme.datatypes;

import java.util.Date;

import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import acme.framework.datatypes.Money;
import acme.framework.entities.DomainEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Embeddable
@Getter
@Setter
@ToString
public class ShoutInfo extends DomainEntity {

	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	@Past
	protected Date				moment2;
	
	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	@Past
	protected Date				date;
	
	@NotNull
	@Valid
	protected Money				money;

	@NotNull
	protected Boolean			bool;

}
