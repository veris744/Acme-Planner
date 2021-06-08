
package acme.testing.anonymous.shouts;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.AcmePlannerTest;

// En este test se crean shouts correctamente y se comprueba que estan en el listado
public class ShoutCreateTest extends AcmePlannerTest {

	@ParameterizedTest
	@CsvFileSource(resources = "/anonymous/shouts/create-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void createPositive(final int recordIndex, final String author, final String text, final String info,
		final String date, final String money, final String bool) {

		super.clickOnMenu("Anonymous", "Shout!");
		super.fillInputBoxIn("author", author);
		super.fillInputBoxIn("text", text);
		super.fillInputBoxIn("info", info);
		super.clickOnSubmitButton("Shout!");

		super.clickOnMenu("Anonymous", "List recent shouts");

		super.checkColumnHasValue(recordIndex, 1, author);
		super.checkColumnHasValue(recordIndex, 2, text);
		super.checkColumnHasValue(recordIndex, 3, info);
	}
// En este test se intenta crear test rellenando mal el formulario, usando palabras de spam o fechas con formato incorrecto 
	
	@ParameterizedTest
	@CsvFileSource(resources = "/anonymous/shouts/create-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void createNegative(final String author, final String text, final String info,
		final String date, final String money, final String bool) {

		super.clickOnMenu("Anonymous", "Shout!");
		super.fillInputBoxIn("author", author);
		super.fillInputBoxIn("text", text);
		super.fillInputBoxIn("info", info);
		super.clickOnSubmitButton("Shout!");
		super.checkErrorsExist();

	}

}
