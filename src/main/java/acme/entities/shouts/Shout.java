
package acme.entities.shouts;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.URL;

import acme.entities.spamWords.SpamWordsConstraint;
import acme.framework.entities.DomainEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class Shout extends DomainEntity {

	protected static final long	serialVersionUID	= 1L;

	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	@Past
	protected Date				moment;

	@NotBlank
	@Size(min = 5, max = 25)
	protected String			author;

	@SpamWordsConstraint
	@NotBlank
	@Size(max = 100)
	protected String			text;

	@URL
	protected String			info;
}
