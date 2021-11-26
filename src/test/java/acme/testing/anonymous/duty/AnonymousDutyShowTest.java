package acme.testing.anonymous.duty;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.AcmePlannerTest;

public class AnonymousDutyShowTest extends AcmePlannerTest {
	
	//En este test probaremos a mostrar estas tareas publicas y no terminadas (show) desde un usuario anonimo
	//En el caso positivo no debería haber ningun problema para mostrar las tareas
	
	@ParameterizedTest
	@CsvFileSource(resources = "/anonymous/duty/show-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void list(final int recordIndex, final String title, final String startPeriod, final String endPeriod, 
		final String workload, final String description, final String link) {
		
		super.clickOnMenu("Anonymous", "List non-finished public duties");
		
		super.clickOnListingRecord(recordIndex);
		
		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("startPeriod", startPeriod);
		super.checkInputBoxHasValue("endPeriod", endPeriod);
		super.checkInputBoxHasValue("workload", workload);
		super.checkInputBoxHasValue("description", description);
		super.checkInputBoxHasValue("link", link);
		
	}
	
	//En este test probaremos a mostrar estas tareas publicas y no terminadas (show) desde un usuario anonimo
	//En el caso negativo se prueba que no se puede mostrar una tarea que no existe y una tarea privada
	
	@ParameterizedTest
	@CsvFileSource(resources = "/anonymous/duty/show-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(20)
	public void showNegative(final int id) {
		super.navigate("/anonymous/duty/show", "id=" + id); 
		super.checkErrorsExist();
	}

}
