package acme.testing.authenticated.task;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.AcmePlannerTest;

public class AuthenticatedTaskShowTest extends AcmePlannerTest {
	
	@ParameterizedTest
	@CsvFileSource(resources = "/authenticated/task/show-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void list(final int recordIndex, final String title, final String startPeriod, final String endPeriod, 
		final String workload, final String description, final String link) {
		
		super.signIn("administrator", "administrator");
		super.clickOnMenu("Authenticated", "List finished public tasks");
		
		super.clickOnListingRecord(recordIndex);
		
		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("startPeriod", startPeriod);
		super.checkInputBoxHasValue("endPeriod", endPeriod);
		super.checkInputBoxHasValue("workload", workload);
		super.checkInputBoxHasValue("description", description);
		super.checkInputBoxHasValue("link", link);
		
	}
	
	@ParameterizedTest
	@CsvFileSource(resources = "/authenticated/task/show-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(20)
	public void showNegative(final int id) {
		super.navigate("/authenticated/task/show", "id=" + id); // el id 5000 no existe y el id 721 pertenece a una task no finalizada
		super.checkErrorsExist();
	}

}
