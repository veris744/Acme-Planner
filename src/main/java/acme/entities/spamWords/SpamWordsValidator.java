package acme.entities.spamWords;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import acme.features.administrator.spamWord.AdministratorSpamWordRepository;

public class SpamWordsValidator implements ConstraintValidator<SpamWordsConstraint, String> {


	@Autowired
	protected AdministratorSpamWordRepository spamWordRepository;
	
	
	//private List<String> spamWords2;

	private List<String> spamWords;
	
	@Override
    public void initialize(final SpamWordsConstraint Spam) {
		this.spamWords = this.spamWordRepository.findWords().stream().collect(Collectors.toList());
    }

    @Override
    public boolean isValid(final String field, final ConstraintValidatorContext cxt) {

    	for (int i = 0; i< this.spamWords.size(); i++) {
			if(field.toLowerCase().contains(this.spamWords.get(i)) || 
				field.toLowerCase().contains(this.spamWords.get(i).replace(" ", ""))) {
				return false;
			}
		}
    	return true;
    }
}
