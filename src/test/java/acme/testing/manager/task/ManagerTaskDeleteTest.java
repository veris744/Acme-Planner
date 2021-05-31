package acme.testing.manager.task;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.AcmePlannerTest;



public class ManagerTaskDeleteTest extends AcmePlannerTest {

	//En este test probaremos eliminar las tareas de un manager (Delete)
	@ParameterizedTest
	@CsvFileSource(resources = "/manager/task/delete-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void deletePositive(final int recordIndex, final int id) {
		super.signIn("manager", "manager");
		super.clickOnMenu("Manager", "List tasks");

		super.clickOnListingRecord(recordIndex);
		super.clickOnSubmitButton("Delete");
		super.navigate("/manager/task/show", "id=" + id);
		super.checkErrorsExist();
		
		super.signOut();
	}
	
	//En este test comprobamos que se produce un error al intentar eliminar las tareas con las siguientes ids:
	//5000: No existe, 747 y 749: Tareas de otro manager.
	@ParameterizedTest
	@CsvFileSource(resources = "/manager/task/delete-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(20)
	public void deleteNegative(final int id) {
		super.signIn("manager", "manager");
		super.navigate("/manager/task/delete", "id=" + id);
		super.checkErrorsExist();
		
		super.signOut();
	}
}
