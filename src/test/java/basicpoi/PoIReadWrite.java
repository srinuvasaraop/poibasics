package basicpoi;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class PoIReadWrite {
	WebDriver driver;
	public ExcelUtility utility;
	String xlpath = "F:\\APACHE POI\\testdata.xlsx";

	@BeforeTest
	public void launchAUT() {
		utility = new ExcelUtility(xlpath);
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.get("https://login.salesforce.com/?locale=in");
	}

	@Test
	public void loginWithInvalidData() throws InterruptedException {
		// String username1 = utility.getCellData()
		String username1 = utility.getCellData("loginData", "username", 2);
		String password1 = utility.getCellData("loginData", "password", 2);
		Thread.sleep(1000);

		driver.findElement(By.id("username")).sendKeys(username1);
		Thread.sleep(1000);
		System.out.println("username");
		driver.findElement(By.id("password")).sendKeys(password1);
		System.out.println("password");
		Thread.sleep(1000);
		driver.findElement(By.id("Login")).click();

		// validting error message
		driver.getPageSource().contains(
				"Please check your username and password. If you still can't log in, contact your Salesforce administrator.");
	   //update excel data 
		utility.setCellData("loginData", "status", 2, "PASSED");
	}

	@AfterTest
	public void closeAUT() {
		driver.close();
	}
}
