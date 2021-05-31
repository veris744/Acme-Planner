package acme.testing.manager.task;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.AcmePlannerTest;


//En este test probaremos eliminar las tareas de un manager (Delete)
//En el caso positivo no debería haber ningun problema al eliminar las tareas del manager
//En el caso negativo se prueba que un manager no pueda eliminar tareas de otros manager


public class ManagerTaskDeleteTest extends AcmePlannerTest {

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
