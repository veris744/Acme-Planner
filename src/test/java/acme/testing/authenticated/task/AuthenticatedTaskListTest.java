package acme.testing.authenticated.task;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.AcmePlannerTest;

public class AuthenticatedTaskListTest  extends AcmePlannerTest {
	
	// En este test probaremos listar las tareas publicas finalizadas para un usuario identificado (List) y mostrar estas tareas (show)
	// En el caso positivo no debería haber ningun problema al listar o mostrar las tareas
	// En esta feature no hemos añadido un listado negativo porque no es posible hacer un test negativo de un listado
	@ParameterizedTest
	@CsvFileSource(resources = "/authenticated/task/list.csv", encoding="utf-8", numLinesToSkip = 1)
	@Order(10)
	public void list(final int recordIndex, final String title, final String startPeriod, final String endPeriod, 
		final String workload, final String description, final String link) {
		
		super.signIn("administrator", "administrator");
		super.clickOnMenu("Authenticated", "List finished public tasks");
		
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
