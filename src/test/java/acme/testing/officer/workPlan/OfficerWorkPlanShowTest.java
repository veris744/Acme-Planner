
package acme.testing.officer.workPlan;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.AcmePlannerTest;

public class OfficerWorkPlanShowTest extends AcmePlannerTest {
	// En este test probaremos que al pulsar sobre un plan de trabajo, todos los datos son correctos.
	@ParameterizedTest
	@CsvFileSource(resources = "/officer/work-plan/show-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void showPositive(final int recordIndex, final String title, final String startPeriod, final String endPeriod, final String workload, final String isPublic) {
		super.signIn("officer", "officer");
		super.clickOnMenu("Officer", "List my work plans");

		super.clickOnListingRecord(recordIndex);

		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("startPeriod", startPeriod);
		super.checkInputBoxHasValue("endPeriod", endPeriod);
		super.checkInputBoxHasValue("isPublic", isPublic);

		super.signOut();
	}
	
	// En este test probamos que el sistema no permite mostrar un plan de trabajo que no exsiste, o que pertenece a otro usuario.
	@ParameterizedTest
	@CsvFileSource(resources = "/officer/work-plan/show-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(20)
	public void showNegative(final int id) {
		super.signIn("officer", "officer");

		super.navigate("/officer/work-plan/show", "id=" + id); // el id 80 no existe y el 580 no pertenece al usuario officer
		super.checkErrorsExist();

		super.signOut();

	}

}
