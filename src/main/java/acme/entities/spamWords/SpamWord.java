package acme.entities.spamWords;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;

import acme.framework.entities.DomainEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class SpamWord extends DomainEntity {

	private static final long serialVersionUID = 1L;

	@NotBlank
	protected String word;
	
}