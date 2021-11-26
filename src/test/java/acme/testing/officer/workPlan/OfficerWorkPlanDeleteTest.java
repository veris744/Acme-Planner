package acme.testing.officer.workPlan;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.AcmePlannerTest;

public class OfficerWorkPlanDeleteTest extends AcmePlannerTest{
	// En este test probaremos que se borran correctamente los planes de trabajo.
	@ParameterizedTest
	@CsvFileSource(resources = "/officer/work-plan/delete-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void deletePositive(final int recordIndex, final int id) {
		super.signIn("officer", "officer");
		super.clickOnMenu("Officer", "List my work plans");

		super.clickOnListingRecord(recordIndex);
		super.clickOnSubmitButton("Delete");
		super.navigate("/officer/work-plan/show", "id=" + id);
		super.checkErrorsExist();
	}
	// En este test probaremos que el sistema no permite borrar planes de trabajo de otros usuarios.
	@ParameterizedTest
	@CsvFileSource(resources = "/officer/work-plan/delete-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(20)
	public void deleteNegative(final int id) {
		super.signIn("officer", "officer");
		super.navigate("/officer/work-plan/delete", "id=" + id);
		super.checkErrorsExist();
		
		super.signOut();
	}

}
