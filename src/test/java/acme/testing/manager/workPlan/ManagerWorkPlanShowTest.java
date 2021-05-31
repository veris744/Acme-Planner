
package acme.testing.manager.workPlan;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.AcmePlannerTest;

public class ManagerWorkPlanShowTest extends AcmePlannerTest {
	// En este test probaremos que al pulsar sobre un plan de trabajo, todos los datos son correctos.
	@ParameterizedTest
	@CsvFileSource(resources = "/manager/work-plan/show-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void showPositive(final int recordIndex, final String title, final String startPeriod, final String endPeriod, final String workload, final String isPublic) {
		super.signIn("manager", "manager");
		super.clickOnMenu("Manager", "List my work plans");

		super.clickOnListingRecord(recordIndex);

		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("startPeriod", startPeriod);
		super.checkInputBoxHasValue("endPeriod", endPeriod);
		super.checkInputBoxHasValue("isPublic", isPublic);

		super.signOut();
	}
	
	// En este test probamos que el sistema no permite mostrar un plan de trabajo que no exsiste, o que pertenece a otro usuario.
	@ParameterizedTest
	@CsvFileSource(resources = "/manager/work-plan/show-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(20)
	public void showNegative(final int id) {
		super.signIn("manager", "manager");

		super.navigate("/manager/work-plan/show", "id=" + id); // el id 80 no existe y el 580 no pertenece al usuario manager
		super.checkErrorsExist();

		super.signOut();

	}

}
