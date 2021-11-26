package acme.testing.officer.duty;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.AcmePlannerTest;



public class OfficerDutyDeleteTest extends AcmePlannerTest {

	//En este test probaremos eliminar las tareas de un officer (Delete)
	@ParameterizedTest
	@CsvFileSource(resources = "/officer/duty/delete-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void deletePositive(final int recordIndex, final int id) {
		super.signIn("officer", "officer");
		super.clickOnMenu("Officer", "List duties");

		super.clickOnListingRecord(recordIndex);
		super.clickOnSubmitButton("Delete");
		super.navigate("/officer/duty/show", "id=" + id);
		super.checkErrorsExist();
		
		super.signOut();
	}
	
	//En este test comprobamos que se produce un error al intentar eliminar las tareas con las siguientes ids:
	//5000: No existe, 747 y 749: Tareas de otro officer.
	@ParameterizedTest
	@CsvFileSource(resources = "/officer/duty/delete-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(20)
	public void deleteNegative(final int id) {
		super.signIn("officer", "officer");
		super.navigate("/officer/duty/delete", "id=" + id);
		super.checkErrorsExist();
		
		super.signOut();
	}
}
