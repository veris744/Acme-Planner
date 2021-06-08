
package acme.testing.manager.workPlan;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.AcmePlannerTest;

public class ManagerWorkPlanUpdateTest extends AcmePlannerTest {

	// En este test probaremos que se actualizan correctamente los planes de trabajo
	@ParameterizedTest
	@CsvFileSource(resources = "/manager/work-plan/update-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void updatePositive(final int recordIndex, final String title, final String startPeriod, final String endPeriod, final String workload, final String isPublic, final String taskSelected, final String taskDeleteSelected) {
		super.signIn("manager", "manager");
		super.clickOnMenu("Manager", "List my work plans");

		super.clickOnListingRecord(recordIndex);

		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("startPeriod", startPeriod);
		super.fillInputBoxIn("endPeriod", endPeriod);
		super.fillInputBoxIn("isPublic", isPublic);
		super.fillInputBoxIn("taskSelected", taskSelected);
		super.fillInputBoxIn("taskDeleteSelected", taskDeleteSelected);
		super.clickOnSubmitButton("Update");

		super.clickOnListingRecord(recordIndex);

		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("startPeriod", startPeriod);
		super.checkInputBoxHasValue("endPeriod", endPeriod);
		super.checkInputBoxHasValue("isPublic", isPublic);
	}

	/*
	 * En este test probaremos que al actualizar los planes de trabajo, se cumple que:
	 * El título no debe estar vacío.
	 * El título no puede contener spam.
	 * El periodo de inicio debe ser anterior al periodo de inicio de las tareas que contiene.
	 * El periodo de fin debe ser posterior al periodo de fin de las tareas que contiene.
	 * El título debe tener entre 1 y 80 caracteres.
	 * Tanto el periodo de inicio como el de fin no deben estar vacíos.
	 * Tanto el periodo de inicio como el de fin deben ser válidos.
	 * El periodo de inicio debe ser posterior a la fecha actual.
	 * El periodo de fin debe ser posterior al periodo de inicio.
	 */
	@ParameterizedTest
	@CsvFileSource(resources = "/manager/work-plan/update-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(20)
	public void updateNegative(final int recordIndex, final String title, final String startPeriod, final String endPeriod, final String workload, final String isPublic, final String taskSelected, final String taskDeleteSelected) {
		
		super.signIn("manager", "manager");
		super.clickOnMenu("Manager", "List my work plans");

		super.clickOnListingRecord(recordIndex);

		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("startPeriod", startPeriod);
		super.fillInputBoxIn("endPeriod", endPeriod);
		super.fillInputBoxIn("isPublic", isPublic);
		super.fillInputBoxIn("taskSelected", taskSelected);
		super.fillInputBoxIn("taskDeleteSelected", taskDeleteSelected);
		super.clickOnSubmitButton("Update");

		super.checkErrorsExist();

	}

}
