package acme.testing.anonymous.duty;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.AcmePlannerTest;

public class AnonymousDutyListTest extends AcmePlannerTest {
	
	//En este test se incia sesion como usuario anonimo, pulsa en el boton del menu Anonymous/List non-finished public duties
	//donde comprueba que todas las tareas se listan correctamente
	
	@ParameterizedTest
	@CsvFileSource(resources = "/anonymous/duty/list.csv", encoding="utf-8", numLinesToSkip = 1)
	@Order(10)
	public void list(final int recordIndex, final String title, final String startPeriod, final String endPeriod, 
		final String workload, final String description, final String link) {
		
		super.clickOnMenu("Anonymous", "List non-finished public duties");
		
		super.checkColumnHasValue(recordIndex, 0, title);
		super.checkColumnHasValue(recordIndex, 1, startPeriod);
		super.checkColumnHasValue(recordIndex, 2, endPeriod);
		super.checkColumnHasValue(recordIndex, 3, workload);
		
		super.clickOnListingRecord(recordIndex);
		
		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("startPeriod", startPeriod);
		super.checkInputBoxHasValue("endPeriod", endPeriod);
		super.checkInputBoxHasValue("workload", workload);
		super.checkInputBoxHasValue("description", description);
		super.checkInputBoxHasValue("link", link);
		
	}

}
