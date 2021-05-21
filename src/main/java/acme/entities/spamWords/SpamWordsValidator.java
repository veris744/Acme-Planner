package acme.entities.spamWords;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import acme.features.administrator.parameters.AdministratorParametersRepository;
import acme.features.administrator.spamWord.AdministratorSpamWordRepository;

public class SpamWordsValidator implements ConstraintValidator<SpamWordsConstraint, String> {


	@Autowired
	protected AdministratorSpamWordRepository spamWordRepository;
	
	@Autowired
	protected AdministratorParametersRepository parameters;
	

	private List<String> spamWords;
	private Double threshold;
	
	@Override
    public void initialize(final SpamWordsConstraint Spam) {
		this.spamWords = this.spamWordRepository.findWords().stream().collect(Collectors.toList());
		this.threshold = this.parameters.findThreshold();
    }

    @Override
    public boolean isValid(final String field, final ConstraintValidatorContext cxt) {
    	
    	int n;
    	n = 0;
    	
    	for (int i = 0; i< this.spamWords.size(); i++) {
			if(field.toLowerCase().contains(this.spamWords.get(i)) || 
				field.toLowerCase().contains(this.spamWords.get(i).replace(" ", ""))) {
				n += 1;
			}
		}
    	if (n*100/field.split(" ").length > this.threshold) 
    		return false;
    	return true;
    }
}
