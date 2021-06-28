
package acme.testing.anonymous.shouts;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.core.annotation.Order;

import acme.testing.AcmePlannerTest;

//En este test comprobamos el listado de los shouts 
public class AnonymousShoutListTest extends AcmePlannerTest {

	@ParameterizedTest
	@CsvFileSource(resources = "/anonymous/shouts/list.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void list(final int recordIndex, final String moment,
	final String author, final String text, final String info,
	final String ticket, final String deadline, final String budget, final String important) {

		super.clickOnMenu("Anonymous", "List recent shouts");

		super.checkColumnHasValue(recordIndex, 0, moment);
		super.checkColumnHasValue(recordIndex, 1, author);
		super.checkColumnHasValue(recordIndex, 2, text);
		super.checkColumnHasValue(recordIndex, 3, info);
		super.checkColumnHasValue(recordIndex, 4, ticket);
		super.checkColumnHasValue(recordIndex, 5, deadline);
		super.checkColumnHasValue(recordIndex, 6, budget);
		super.checkColumnHasValue(recordIndex, 7, important);

	}

}
