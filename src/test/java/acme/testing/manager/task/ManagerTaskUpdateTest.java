package acme.testing.manager.task;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.AcmePlannerTest;

public class ManagerTaskUpdateTest extends AcmePlannerTest {

	//En este test se hace una actualizacion de una tarea y se comprueba que la actualizacion se ha realizado correctamente
	@ParameterizedTest
	@CsvFileSource(resources = "/manager/task/update-positive.csv", encoding="utf-8", numLinesToSkip = 1)
	@Order(10)
	public void updatePositive(final int recordIndex, final String title, final String startPeriod, final String endPeriod, 
		final String workload, final String description, final String link, final String isPublic) {
		
		super.signIn("manager", "manager");
		super.clickOnMenu("Manager", "List tasks");
		
		super.clickOnListingRecord(recordIndex);
		
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("startPeriod", startPeriod);
		super.fillInputBoxIn("endPeriod", endPeriod);
		super.fillInputBoxIn("workload", workload);
		super.fillInputBoxIn("description", description);
		super.fillInputBoxIn("link", link);
		super.fillInputBoxIn("isPublic", isPublic);
		
		super.clickOnSubmitButton("Update");
		
		
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

	/* En este test probaremos que al actualizar las tareas se cumple que:
	*  El título no contiene spam.
	*  El título no puede contener más de 50 caracteres.
	*  La fecha de inicio es anterior a la actual.
	*  El workload es un número negativo.
	*  La descripción contiene spam.
	*  Tanto el periodo de inicio como el de fin deben ser válidos.
	*  El link no tiene el formato correcto.
	*  El workload no es un número.
	*  El titulo no está vacio.
	*  La fecha de inicio no está vacía.
	*  La fecha de fin no está vacía.
	*  El workload no está vacío.
	*  El workload no se ajusta al periodo.
	*/
	@ParameterizedTest
	@CsvFileSource(resources = "/manager/task/update-negative.csv", encoding="utf-8", numLinesToSkip = 1)
	@Order(20)
	public void updateNegative(final int recordIndex, final String title, final String startPeriod, final String endPeriod, 
		final String workload, final String description, final String link, final String isPublic) {
		
		super.signIn("manager", "manager");
		super.clickOnMenu("Manager", "List tasks");
		
		super.clickOnListingRecord(recordIndex);
		
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("startPeriod", startPeriod);
		super.fillInputBoxIn("endPeriod", endPeriod);
		super.fillInputBoxIn("workload", workload);
		super.fillInputBoxIn("description", description);
		super.fillInputBoxIn("link", link);
		super.fillInputBoxIn("isPublic", isPublic);
		
		super.clickOnSubmitButton("Update");

		super.checkErrorsExist();
		super.signOut();
	}
}
