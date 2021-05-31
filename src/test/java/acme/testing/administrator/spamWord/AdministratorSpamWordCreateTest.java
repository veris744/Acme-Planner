
package acme.testing.administrator.spamWord;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.AcmePlannerTest;

public class AdministratorSpamWordCreateTest extends AcmePlannerTest {

	//En este test se crea una nueva palabra de spam y comprueba que este en la lista
	@ParameterizedTest
	@CsvFileSource(resources = "/administrator/spamWord/create-positive.csv", encoding="utf-8", numLinesToSkip = 1)
	@Order(10)
	public void addPositive(final int recordIndex, final String word) {
		
		super.signIn("administrator", "administrator");
		
		super.clickOnMenu("Administrator", "See spam words");
		super.clickOnReturnButton("Add word");
		
		super.fillInputBoxIn("word", word);
		super.clickOnSubmitButton("Add");

		super.clickOnMenu("Administrator", "See spam words");
		super.checkColumnHasValue(recordIndex, 0, word);
		
		super.signOut();
		
	}

	//En este test se crea una nueva palabra de spam sin rellenar el campo y comprueba que haya un error	
	@ParameterizedTest
	@CsvFileSource(resources = "/administrator/spamWord/create-negative.csv", encoding="utf-8", numLinesToSkip = 1)
	@Order(20)
	public void addNegative(final int recordIndex, final String word) {
		
		super.signIn("administrator", "administrator");
		
		super.clickOnMenu("Administrator", "See spam words");
		super.clickOnReturnButton("Add word");
		
		super.fillInputBoxIn("word", word);
		super.clickOnSubmitButton("Add");

		super.checkErrorsExist();
		
		super.signOut();
		
	}

}
