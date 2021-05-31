package acme.testing.administrator.parameters;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.AcmePlannerTest;


//En este test probaremos mostrar el parámetro theshold (show)


public class AdministratorParametersShowTest extends AcmePlannerTest {
	
	@ParameterizedTest
	@CsvFileSource(resources = "/administrator/parameters/show.csv", encoding="utf-8", numLinesToSkip = 1)
	@Order(10)
	public void show(final String threshold) {
		
		super.signIn("administrator", "administrator");
		
		super.clickOnMenu("Administrator", "See customisation parameters");
		super.checkInputBoxHasValue("threshold", threshold);
		

		super.signOut();
	}
}
