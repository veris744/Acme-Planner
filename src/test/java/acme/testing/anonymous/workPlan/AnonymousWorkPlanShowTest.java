package acme.testing.anonymous.workPlan;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.AcmePlannerTest;

public class AnonymousWorkPlanShowTest extends AcmePlannerTest {
	
	//En este test comprobamos que se muestran correctamente los work plans para un usuario anonimo
	@ParameterizedTest
	@CsvFileSource(resources = "/anonymous/work-plan/show-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void list(final int recordIndex, final String title, final String startPeriod, final String endPeriod, 
		final String workload) {
		
		super.clickOnMenu("Anonymous", "List non-finished public work plans");
		
		super.clickOnListingRecord(recordIndex);
		
		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("startPeriod", startPeriod);
		super.checkInputBoxHasValue("endPeriod", endPeriod);
		super.checkInputBoxHasValue("workload", workload);
		
	}
	
	// En este test comprobamos que se produce un error al intentar mostrar los work plans con las siguientes ids:
	// 5000: No existe, 722: Work plan privado, 745: Work plan ya terminado
	@ParameterizedTest
	@CsvFileSource(resources = "/anonymous/work-plan/show-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(20)
	public void showNegative(final int id) {
		super.navigate("/anonymous/work-plan/show", "id=" + id); 
		super.checkErrorsExist();
	}

}
