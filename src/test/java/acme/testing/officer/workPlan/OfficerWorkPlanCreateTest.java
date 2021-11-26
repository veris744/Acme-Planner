
package acme.testing.officer.workPlan;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.AcmePlannerTest;

public class OfficerWorkPlanCreateTest extends AcmePlannerTest {
	// En este test probaremos que se crean correctamente los planes de trabajo.
	@ParameterizedTest
	@CsvFileSource(resources = "/officer/work-plan/create-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void createPositive(final int recordIndex, final String title, final String startPeriod, final String endPeriod, final String workload, final String isPublic) {

		super.signIn("officer", "officer");
		super.clickOnMenu("Officer", "Create a work plan");

		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("startPeriod", startPeriod);
		super.fillInputBoxIn("endPeriod", endPeriod);
		super.fillInputBoxIn("isPublic", isPublic);
		super.clickOnSubmitButton("Create");

		super.clickOnMenu("Officer", "List my work plans");

		super.checkColumnHasValue(recordIndex, 0, title);
		super.checkColumnHasValue(recordIndex, 1, startPeriod);
		super.checkColumnHasValue(recordIndex, 2, endPeriod);
		super.checkColumnHasValue(recordIndex, 3, workload);

		super.clickOnListingRecord(recordIndex);

		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("startPeriod", startPeriod);
		super.checkInputBoxHasValue("endPeriod", endPeriod);
		super.checkInputBoxHasValue("isPublic", isPublic);

		super.signOut();
	}

	/* En este test probaremos que al crear los planes de trabajo se cumple que:
	*  El título no debe estar vacío.
	*  El título no puede contener spam.
	*  El periodo de inicio debe ser anterior al periodo de inicio de las tareas que contiene.
	*  El periodo de fin debe ser posterior al periodo de fin de las tareas que contiene.
	*  El título debe tener entre 1 y 80 caracteres.
	*  Tanto el periodo de inicio como el de fin no deben estar vacíos.
	*  Tanto el periodo de inicio como el de fin deben ser válidos.
	*  El periodo de inicio debe ser posterior a la fecha actual.
	*  El periodo de fin debe ser posterior al periodo de inicio.
	*/
	
	@ParameterizedTest
	@CsvFileSource(resources = "/officer/work-plan/create-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(20)
	public void createNegative(final int recordIndex, final String title, final String startPeriod, final String endPeriod, final String isPublic) {

		super.signIn("officer", "officer");
		super.clickOnMenu("Officer", "Create a work plan");

		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("startPeriod", startPeriod);
		super.fillInputBoxIn("endPeriod", endPeriod);
		super.fillInputBoxIn("isPublic", isPublic);
		super.clickOnSubmitButton("Create");

		super.checkErrorsExist();
		super.signOut();
	}
}
