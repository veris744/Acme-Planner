package acme.testing.administrator.parameters;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.AcmePlannerTest;

//En este test probaremos actualizar el threshold (Update)
//En el caso positivo no debería haber ningun problema al actualizarlo
//En el caso negativo se prueba que no se acepten valores invalidos (el threshold debe ser un número positivo inferior a 100)

public class AdministratorParametersUpdateTest extends AcmePlannerTest {
	
	@ParameterizedTest
	@CsvFileSource(resources = "/administrator/parameters/update-positive.csv", encoding="utf-8", numLinesToSkip = 1)
	@Order(10)
	public void updatePositive(final String threshold) {
		
		super.signIn("administrator", "administrator");
		super.clickOnMenu("Administrator", "See customisation parameters");
		
		super.clickOnReturnButton("Update parameters");
		super.fillInputBoxIn("threshold", threshold);
		super.clickOnSubmitButton("Update");
		
		super.clickOnMenu("Administrator", "See customisation parameters");
		super.checkInputBoxHasValue("threshold", threshold);
		
		super.signOut();
	}
	
	
	@ParameterizedTest
	@CsvFileSource(resources = "/administrator/parameters/update-negative.csv", encoding="utf-8", numLinesToSkip = 1)
	@Order(20)
	public void updateNegative(final String threshold) {
		
		super.signIn("administrator", "administrator");
		super.clickOnMenu("Administrator", "See customisation parameters");
		
		super.clickOnReturnButton("Update parameters");
		super.fillInputBoxIn("threshold", threshold);
		super.clickOnSubmitButton("Update");
		
		super.checkErrorsExist();
		
		super.signOut();
	}
}
