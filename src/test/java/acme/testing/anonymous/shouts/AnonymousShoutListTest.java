
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
	final String moment2, final String date, final String money, final String bool) {

		super.clickOnMenu("Anonymous", "List recent shouts");

		super.checkColumnHasValue(recordIndex, 0, moment);
		super.checkColumnHasValue(recordIndex, 1, author);
		super.checkColumnHasValue(recordIndex, 2, text);
		super.checkColumnHasValue(recordIndex, 3, info);
		super.checkColumnHasValue(recordIndex, 4, moment2);
		super.checkColumnHasValue(recordIndex, 5, date);
		super.checkColumnHasValue(recordIndex, 6, money);
		super.checkColumnHasValue(recordIndex, 7, bool);

	}

}
