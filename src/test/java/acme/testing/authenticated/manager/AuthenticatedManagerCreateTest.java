package acme.testing.authenticated.manager;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.AcmePlannerTest;

public class AuthenticatedManagerCreateTest extends AcmePlannerTest{

	@ParameterizedTest
	@CsvFileSource(resources = "/authenticated/manager/create-positive.csv", encoding="utf-8", numLinesToSkip = 1)
	@Order(10)
	public void createPositive(final int recordIndex, final String username, final String pass, final String name, 
		final String surname, final String email, final String company, final String sector) {
		
		super.signUp(username, pass, name, surname, email);
		super.signIn(username, pass);
		
		super.clickOnMenu("Account", "Become a manager");
		
		super.fillInputBoxIn("company", company);
		super.fillInputBoxIn("sector", sector);
		
		super.clickOnSubmitButton("Register");
		
		super.clickOnMenu("Account", "Manager data");
		
		super.checkInputBoxHasValue("company", company);
		super.checkInputBoxHasValue("sector", sector);
		
		super.signOut();
	}
	
	@ParameterizedTest
	@CsvFileSource(resources = "/authenticated/manager/create-negative.csv", encoding="utf-8", numLinesToSkip = 1)
	@Order(20)
	public void createNegative(final int recordIndex, final String username, final String pass, final String name, 
		final String surname, final String email, final String company, final String sector) {
		
		super.signUp(username, pass, name, surname, email);
		super.signIn(username, pass);
		
		super.clickOnMenu("Account", "Become a manager");
		
		super.fillInputBoxIn("company", company);
		super.fillInputBoxIn("sector", sector);
		
		super.clickOnSubmitButton("Register");
		
		super.checkErrorsExist();
		
		super.signOut();
	}
}
