
package acme.testing.manager.workPlan;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.AcmePlannerTest;

public class ManagerWorkPlanCreateTest extends AcmePlannerTest {

	@ParameterizedTest
	@CsvFileSource(resources = "/manager/work-plan/create-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void createPositive(final int recordIndex, final String title, final String startPeriod, final String endPeriod, final String workload, final String isPublic) {

		super.signIn("manager", "manager");
		super.clickOnMenu("Manager", "Create a work plan");

		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("startPeriod", startPeriod);
		super.fillInputBoxIn("endPeriod", endPeriod);
		if (!Boolean.parseBoolean(isPublic)) {
			super.fillInputBoxIn("isPublic", null);
		}
		super.clickOnSubmitButton("Create");

		super.clickOnMenu("Manager", "List my work plans");

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

	@ParameterizedTest
	@CsvFileSource(resources = "/manager/work-plan/create-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(20)
	public void createNegative(final int recordIndex, final String title, final String startPeriod, final String endPeriod, final String isPublic) {

		super.signIn("manager", "manager");
		super.clickOnMenu("Manager", "Create a work plan");

		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("startPeriod", startPeriod);
		super.fillInputBoxIn("endPeriod", endPeriod);
		if (!Boolean.parseBoolean(isPublic)) {
			super.fillInputBoxIn("isPublic", null);
		}
		super.clickOnSubmitButton("Create");

		super.checkErrorsExist();
		super.signOut();
	}
}
