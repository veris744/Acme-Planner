package acme.testing.manager.task;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.AcmePlannerTest;

//En este test probaremos crea las tareas para un manager (Create) y mostrar estas tareas (show)
//En el caso positivo no debería haber ningun problema al crear o mostrar las tareas
//En el caso negativo se prueba que las fechas sean correctas (inicio antes que fin y en el pasado), que el workload sea un número positivo,
//que no se considere spam, que ni el título ni la descripción superen el limite de caracteres, que el link sea una URL)

public class ManagerTaskCreateTest extends AcmePlannerTest {

	@ParameterizedTest
	@CsvFileSource(resources = "/manager/task/create-positive.csv", encoding="utf-8", numLinesToSkip = 1)
	@Order(10)
	public void createPositive(final int recordIndex, final String title, final String startPeriod, final String endPeriod, 
		final String workload, final String description, final String link, final String isPublic) {
		
		super.signIn("manager", "manager");
		super.clickOnMenu("Manager", "Create task");
		
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("startPeriod", startPeriod);
		super.fillInputBoxIn("endPeriod", endPeriod);
		super.fillInputBoxIn("workload", workload);
		super.fillInputBoxIn("description", description);
		super.fillInputBoxIn("link", link);
		super.fillInputBoxIn("isPublic", isPublic);
		
		super.clickOnSubmitButton("Create");
		
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
	@CsvFileSource(resources = "/manager/task/create-negative.csv", encoding="utf-8", numLinesToSkip = 1)
	@Order(20)
	public void createNegative(final int recordIndex, final String title, final String startPeriod, final String endPeriod, 
		final String workload, final String description, final String link, final String isPublic) {
		
		super.signIn("manager", "manager");
		super.clickOnMenu("Manager", "Create task");
		
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("startPeriod", startPeriod);
		super.fillInputBoxIn("endPeriod", endPeriod);
		super.fillInputBoxIn("workload", workload);
		super.fillInputBoxIn("description", description);
		super.fillInputBoxIn("link", link);
		super.fillInputBoxIn("isPublic", isPublic);
		
		super.clickOnSubmitButton("Create");
		
		super.checkErrorsExist();
		super.signOut();
	}
}
