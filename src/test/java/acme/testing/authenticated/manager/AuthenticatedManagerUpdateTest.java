package acme.testing.authenticated.manager;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.AcmePlannerTest;

public class AuthenticatedManagerUpdateTest extends AcmePlannerTest{

	// En este test comprobamos que se actualizan correctamente los datos de un manager.
	@ParameterizedTest
	@CsvFileSource(resources = "/authenticated/manager/update-positive.csv", encoding="utf-8", numLinesToSkip = 1)
	@Order(10)
	public void createPositive(final int recordIndex, final String company, final String sector) {
		
		super.signIn("manager", "manager");
		
		super.clickOnMenu("Account", "Manager data");

		super.fillInputBoxIn("company", company);
		super.fillInputBoxIn("sector", sector);
		
		super.clickOnSubmitButton("Update");
		
		super.clickOnMenu("Account", "Manager data");
		
		super.checkInputBoxHasValue("company", company);
		super.checkInputBoxHasValue("sector", sector);
		
		super.signOut();
	}
	
	// En este test comprobamos que se produce un error al intentar actualizar los datos de un manager en los siguientes casos:
	// El campo "company" esta vacio, el campo "sector" esta vacio, o ambos campos estan vacios.
	@ParameterizedTest
	@CsvFileSource(resources = "/authenticated/manager/update-negative.csv", encoding="utf-8", numLinesToSkip = 1)
	@Order(20)
	public void createNegative(final int recordIndex, final String company, final String sector) {
		
		super.signIn("manager", "manager");
		
		super.clickOnMenu("Account", "Manager data");
		
		super.fillInputBoxIn("company", company);
		super.fillInputBoxIn("sector", sector);
		
		super.clickOnSubmitButton("Update");
		
		super.checkErrorsExist();
		
		super.signOut();
	}
}
