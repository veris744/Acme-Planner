package acme.testing.administrator.dashboard;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.openqa.selenium.By;

import acme.testing.AcmePlannerTest;

public class AdministratorDashboardTest extends AcmePlannerTest{
	
	//En este test se comprueba que los valores del dashboard se muestran correctamente.
	@ParameterizedTest
	@CsvFileSource(resources = "/administrator/dashboard/list.csv", encoding="utf-8", numLinesToSkip = 1)
	@Order(10)
	public void dashboardShow(final String publicDutys, final String privateDutys, final String finishedDutys,
		final String nonFinishedDutys, final String minWorkloadDutys, final String maxWorkloadDutys,
		final String avWorkloadDutys, final String devWorkloadDutys, final String minPeriodDutys, 
		final String maxPeriodDutys, final String avPeriodDutys, final String devPeriodDutys,
		final String publicWP, final String privateWP, final String finishedWP,
		final String nonFinishedWP, final String minWorkloadWP, final String maxWorkloadWP,
		final String avWorkloadWP, final String devWorkloadWP, final String minPeriodWP, 
		final String maxPeriodWP, final String avPeriodWP, final String devPeriodWP) {
	
		super.signIn("administrator", "administrator");
		super.clickOnMenu("Administrator", "Dashboard");
		
		
		final String xpath1 = "//table[@id='dashboard']//tr[1]/td";
		final String xpath2 = "//table[@id='dashboard']//tr[2]/td";
		final String xpath3 = "//table[@id='dashboard']//tr[3]/td";
		final String xpath4 = "//table[@id='dashboard']//tr[4]/td";
		final String xpath5 = "//table[@id='dashboard']//tr[5]/td";
		final String xpath6 = "//table[@id='dashboard']//tr[6]/td";
		final String xpath7 = "//table[@id='dashboard']//tr[7]/td";
		final String xpath8 = "//table[@id='dashboard']//tr[8]/td";
		final String xpath9 = "//table[@id='dashboard']//tr[9]/td";
		final String xpath10 = "//table[@id='dashboard']//tr[10]/td";
		final String xpath11 = "//table[@id='dashboard']//tr[11]/td";
		final String xpath12 = "//table[@id='dashboard']//tr[12]/td";
		
		final String xpath15 = "//table[@id='dashboard']//tr[15]/td";
		final String xpath16 = "//table[@id='dashboard']//tr[16]/td";
		final String xpath17 = "//table[@id='dashboard']//tr[17]/td";
		final String xpath18 = "//table[@id='dashboard']//tr[18]/td";
		final String xpath19 = "//table[@id='dashboard']//tr[19]/td";
		final String xpath20 = "//table[@id='dashboard']//tr[20]/td";
		final String xpath21 = "//table[@id='dashboard']//tr[21]/td";
		final String xpath22 = "//table[@id='dashboard']//tr[22]/td";
		final String xpath23 = "//table[@id='dashboard']//tr[23]/td";
		final String xpath24 = "//table[@id='dashboard']//tr[24]/td";
		final String xpath25 = "//table[@id='dashboard']//tr[25]/td";
		final String xpath26 = "//table[@id='dashboard']//tr[26]/td";
		
		
		assert super.locateOne(By.xpath(xpath1)).getText().equals(publicDutys);
		assert super.locateOne(By.xpath(xpath2)).getText().equals(privateDutys);
		assert super.locateOne(By.xpath(xpath3)).getText().equals(finishedDutys);
		assert super.locateOne(By.xpath(xpath4)).getText().equals(nonFinishedDutys);
		assert super.locateOne(By.xpath(xpath5)).getText().equals(minWorkloadDutys);
		assert super.locateOne(By.xpath(xpath6)).getText().equals(maxWorkloadDutys);
		assert super.locateOne(By.xpath(xpath7)).getText().equals(avWorkloadDutys);
		assert super.locateOne(By.xpath(xpath8)).getText().equals(devWorkloadDutys);
		assert super.locateOne(By.xpath(xpath9)).getText().equals(minPeriodDutys);
		assert super.locateOne(By.xpath(xpath10)).getText().equals(maxPeriodDutys);
		assert super.locateOne(By.xpath(xpath11)).getText().equals(avPeriodDutys);
		assert super.locateOne(By.xpath(xpath12)).getText().equals(devPeriodDutys);
		
		assert super.locateOne(By.xpath(xpath15)).getText().equals(publicWP);
		assert super.locateOne(By.xpath(xpath16)).getText().equals(privateWP);
		assert super.locateOne(By.xpath(xpath17)).getText().equals(finishedWP);
		assert super.locateOne(By.xpath(xpath18)).getText().equals(nonFinishedWP);
		assert super.locateOne(By.xpath(xpath19)).getText().equals(minWorkloadWP);
		assert super.locateOne(By.xpath(xpath20)).getText().equals(maxWorkloadWP);
		assert super.locateOne(By.xpath(xpath21)).getText().equals(avWorkloadWP);
		assert super.locateOne(By.xpath(xpath22)).getText().equals(devWorkloadWP);
		assert super.locateOne(By.xpath(xpath23)).getText().equals(minPeriodWP);
		assert super.locateOne(By.xpath(xpath24)).getText().equals(maxPeriodWP);
		assert super.locateOne(By.xpath(xpath25)).getText().equals(avPeriodWP);
		assert super.locateOne(By.xpath(xpath26)).getText().equals(devPeriodWP);
		
		super.signOut();
		
	}

}
