package acme.entities.parameters;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import acme.framework.entities.DomainEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class Parameters extends DomainEntity {

	private static final long serialVersionUID = 1L;

	@NotNull
	protected Double threshold;

}
