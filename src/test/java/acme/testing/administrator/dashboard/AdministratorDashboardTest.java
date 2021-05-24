package acme.testing.administrator.dashboard;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.openqa.selenium.By;

import acme.testing.AcmePlannerTest;

public class AdministratorDashboardTest extends AcmePlannerTest{
	
	@ParameterizedTest
	@CsvFileSource(resources = "/administrator/dashboard/list.csv", encoding="utf-8", numLinesToSkip = 1)
	@Order(10)
	public void dashboardValuesExist() {
	
		super.signIn("administrator", "administrator");
		super.clickOnMenu("Administrator", "Dashboard");
		
		
		super.checkExists(By.xpath(String.format("//table[@id='dashboard']//tr[1]/td")));
		super.checkExists(By.xpath(String.format("//table[@id='dashboard']//tr[2]/td")));
		super.checkExists(By.xpath(String.format("//table[@id='dashboard']//tr[3]/td")));
		super.checkExists(By.xpath(String.format("//table[@id='dashboard']//tr[4]/td")));
		super.checkExists(By.xpath(String.format("//table[@id='dashboard']//tr[5]/td")));
		super.checkExists(By.xpath(String.format("//table[@id='dashboard']//tr[6]/td")));
		super.checkExists(By.xpath(String.format("//table[@id='dashboard']//tr[7]/td")));
		super.checkExists(By.xpath(String.format("//table[@id='dashboard']//tr[8]/td")));
		super.checkExists(By.xpath(String.format("//table[@id='dashboard']//tr[9]/td")));
		super.checkExists(By.xpath(String.format("//table[@id='dashboard']//tr[10]/td")));
		super.checkExists(By.xpath(String.format("//table[@id='dashboard']//tr[11]/td")));
		super.checkExists(By.xpath(String.format("//table[@id='dashboard']//tr[12]/td")));
		
		super.checkExists(By.xpath(String.format("//table[@id='dashboard']//tr[15]/td")));
		super.checkExists(By.xpath(String.format("//table[@id='dashboard']//tr[16]/td")));
		super.checkExists(By.xpath(String.format("//table[@id='dashboard']//tr[17]/td")));
		super.checkExists(By.xpath(String.format("//table[@id='dashboard']//tr[18]/td")));
		super.checkExists(By.xpath(String.format("//table[@id='dashboard']//tr[19]/td")));
		super.checkExists(By.xpath(String.format("//table[@id='dashboard']//tr[20]/td")));
		super.checkExists(By.xpath(String.format("//table[@id='dashboard']//tr[21]/td")));
		super.checkExists(By.xpath(String.format("//table[@id='dashboard']//tr[22]/td")));
		super.checkExists(By.xpath(String.format("//table[@id='dashboard']//tr[23]/td")));
		super.checkExists(By.xpath(String.format("//table[@id='dashboard']//tr[24]/td")));
		super.checkExists(By.xpath(String.format("//table[@id='dashboard']//tr[25]/td")));
		super.checkExists(By.xpath(String.format("//table[@id='dashboard']//tr[26]/td")));
		
		super.signOut();
		
	}

}
