package acme.testing.authenticated.manager;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.AcmePlannerTest;

public class AuthenticatedManagerCreateTest extends AcmePlannerTest{

	// En este test probamos a registrar una nueva cuenta y registrarla como manager.
	@ParameterizedTest
	@CsvFileSource(resources = "/authenticated/manager/create.csv", encoding="utf-8", numLinesToSkip = 1)
	@Order(10)
	public void create(final int recordIndex, final String username, final String pass, final String name, 
		final String surname, final String email) {
		
		super.signUp(username, pass, name, surname, email);
		super.signIn(username, pass);
		
		super.clickOnMenu("Account", "Become a manager");
		
		super.clickOnSubmitButton("Register");
		
		super.checkLinkExists("Manager");
		
		super.signOut();
	}
}
