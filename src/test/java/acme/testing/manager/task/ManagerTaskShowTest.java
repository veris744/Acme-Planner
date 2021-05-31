package acme.testing.manager.task;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.AcmePlannerTest;

public class ManagerTaskShowTest extends AcmePlannerTest {
	
	//En este test probamos que se muestra una tarea correctamente al hacer click en ella.
	@ParameterizedTest
	@CsvFileSource(resources = "/manager/task/show-positive.csv", encoding="utf-8", numLinesToSkip = 1)
	@Order(10)
	public void list(final int recordIndex, final String title, final String startPeriod, final String endPeriod, 
		final String workload, final String description, final String link, final String isPublic) {
		
		super.signIn("manager", "manager");
		super.clickOnMenu("Manager", "List tasks");
		
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
	
	//En este test comprobamos que se produce un error al intentar mostrar las tareas con las siguientes ids:
	//5000: No existe, 747 y 749: Tareas de otro manager.
	@ParameterizedTest
	@CsvFileSource(resources = "/manager/task/show-negative.csv", encoding="utf-8", numLinesToSkip = 1)
	@Order(20)
	public void showNegative(final int id) {
		
		super.signIn("manager", "manager");

		super.navigate("/manager/task/show", "id=" + id);
		super.checkErrorsExist();
		
		super.signOut();
	}
}
