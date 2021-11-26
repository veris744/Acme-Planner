package acme.testing.authenticated.duty;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.AcmePlannerTest;

public class AuthenticatedDutyShowTest extends AcmePlannerTest {
	
	//En este test probaremos mostrar tareas publicas finalizadas (show) para un usuario identificado
	//En el caso positivo no debería haber ningun problema mostrar las tareas
	
	@ParameterizedTest
	@CsvFileSource(resources = "/authenticated/duty/show-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void list(final int recordIndex, final String title, final String startPeriod, final String endPeriod, 
		final String workload, final String description, final String link) {
		
		super.signIn("administrator", "administrator");
		super.clickOnMenu("Authenticated", "List finished public duties");
		
		super.clickOnListingRecord(recordIndex);
		
		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("startPeriod", startPeriod);
		super.checkInputBoxHasValue("endPeriod", endPeriod);
		super.checkInputBoxHasValue("workload", workload);
		super.checkInputBoxHasValue("description", description);
		super.checkInputBoxHasValue("link", link);
		
	}
	
	//En este test probaremos mostrar tareas publicas finalizadas (show) para un usuario identificado
	//En el caso negativo se prueba que hay errores al mostrar tareas no finalizadas y tareas que no existen
	
	@ParameterizedTest
	@CsvFileSource(resources = "/authenticated/duty/show-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(20)
	public void showNegative(final int id) {
		super.navigate("/authenticated/duty/show", "id=" + id); // el id 5000 no existe y el id 721 pertenece a una duty no finalizada
		super.checkErrorsExist();
	}

}
