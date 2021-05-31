package acme.testing.manager.task;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.AcmePlannerTest;

//En este test probaremos listar las tareas de un manager (List) y mostrar estas tareas (show)
//En el caso positivo no debería haber ningun problema al listar o mostrar las tareas
//En el caso negativo se prueba que un manager no pueda ver tareas de otros manager

public class ManagerTaskListTest extends AcmePlannerTest {

	@ParameterizedTest
	@CsvFileSource(resources = "/manager/task/list.csv", encoding="utf-8", numLinesToSkip = 1)
	@Order(10)
	public void list(final int recordIndex, final String title, final String startPeriod, final String endPeriod, 
		final String workload, final String description, final String link, final String isPublic) {
		
		super.signIn("manager", "manager");
		super.clickOnMenu("Manager", "List tasks");
		
		
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
		super.checkInputBoxHasValue("isPublic", isPublic);
		
		super.signOut();
	}
	
	@ParameterizedTest
	@CsvFileSource(resources = "/manager/task/list-negative.csv", encoding="utf-8", numLinesToSkip = 1)
	@Order(20)
	public void listNegative(final int id) {
		
		super.signIn("manager", "manager");

		super.navigate("/manager/task/show", "id=" + id);
		super.checkErrorsExist();
		
		super.signOut();
	}
}
