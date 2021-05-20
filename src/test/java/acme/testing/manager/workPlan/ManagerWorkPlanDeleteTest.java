package acme.testing.manager.workPlan;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.AcmePlannerTest;

public class ManagerWorkPlanDeleteTest extends AcmePlannerTest{
	
	@ParameterizedTest
	@CsvFileSource(resources = "/manager/work-plan/delete.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void delete(final int recordIndex, final int id) {
		super.signIn("manager", "manager");
		super.clickOnMenu("Manager", "List my work plans");

		super.clickOnListingRecord(recordIndex);
		super.clickOnSubmitButton("Delete");
		super.navigate("/manager/work-plan/show", "id=" + id);
		super.checkErrorsExist();
	}

}
