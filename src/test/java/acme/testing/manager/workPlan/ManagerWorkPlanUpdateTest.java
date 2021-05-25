
package acme.testing.manager.workPlan;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.AcmePlannerTest;

public class ManagerWorkPlanUpdateTest extends AcmePlannerTest {

	@ParameterizedTest
	@CsvFileSource(resources = "/manager/work-plan/update-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void updatePositive(final int recordIndex, final String title, final String startPeriod, final String endPeriod, final String workload, final String isPublic, final String taskSelected) {
		super.signIn("manager", "manager");
		super.clickOnMenu("Manager", "List my work plans");

		super.clickOnListingRecord(recordIndex);

		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("startPeriod", startPeriod);
		super.fillInputBoxIn("endPeriod", endPeriod);
		super.fillInputBoxIn("isPublic", isPublic);
		super.fillInputBoxIn("taskSelected", taskSelected);
		super.clickOnSubmitButton("Update");
		
		super.clickOnListingRecord(recordIndex);
		
		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("startPeriod", startPeriod);
		super.checkInputBoxHasValue("endPeriod", endPeriod);
		super.checkInputBoxHasValue("isPublic", isPublic);
	}
	
	@ParameterizedTest
	@CsvFileSource(resources = "/manager/work-plan/update-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(20)
	public void updateNegative(final int recordIndex, final String title, final String startPeriod, final String endPeriod, final String workload, final String isPublic, final String taskSelected) {
		super.signIn("manager", "manager");
		super.clickOnMenu("Manager", "List my work plans");

		super.clickOnListingRecord(recordIndex);
		
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("startPeriod", startPeriod);
		super.fillInputBoxIn("endPeriod", endPeriod);
		super.fillInputBoxIn("isPublic", isPublic);
		super.fillInputBoxIn("taskSelected", taskSelected);
		super.clickOnSubmitButton("Update");
		
		super.checkErrorsExist();
		
	}
	
}
