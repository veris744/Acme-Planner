package acme.testing.manager.workPlan;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.AcmePlannerTest;

public class ManagerWorkPlanDeleteTest extends AcmePlannerTest{
	// En este test probaremos que se borran correctamente los planes de trabajo.
	@ParameterizedTest
	@CsvFileSource(resources = "/manager/work-plan/delete-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void deletePositive(final int recordIndex, final int id) {
		super.signIn("manager", "manager");
		super.clickOnMenu("Manager", "List my work plans");

		super.clickOnListingRecord(recordIndex);
		super.clickOnSubmitButton("Delete");
		super.navigate("/manager/work-plan/show", "id=" + id);
		super.checkErrorsExist();
	}
	// En este test probaremos que el sistema no permite borrar planes de trabajo de otros usuarios.
	@ParameterizedTest
	@CsvFileSource(resources = "/manager/work-plan/delete-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(20)
	public void deleteNegative(final int id) {
		super.signIn("manager", "manager");
		super.navigate("/manager/work-plan/delete", "id=" + id);
		super.checkErrorsExist();
		
		super.signOut();
	}

}
