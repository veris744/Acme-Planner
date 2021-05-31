package acme.testing.administrator.spamWord;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.AcmePlannerTest;

public class AdministratorSpamWordListTest extends AcmePlannerTest {

	//En este test se comprueba el listado de las palabras de spam
	@ParameterizedTest
	@CsvFileSource(resources = "/administrator/spamWord/list.csv", encoding="utf-8", numLinesToSkip = 1)
	@Order(10)
	public void list(final int recordIndex, final String word) {
		
		super.signIn("administrator", "administrator");
		
		super.clickOnMenu("Administrator", "See spam words");
		super.checkColumnHasValue(recordIndex, 0, word);
		
		super.signOut();
		
	}

}
