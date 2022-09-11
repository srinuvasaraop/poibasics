package basicpoi;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class PoiExp1 {
	WebDriver driver;
	public ExcelUtility utility;
	String xlpath="F:\\APACHE POI\\testdata.xlsx";
	@BeforeTest
	public void launchAUT() {
	     utility=new ExcelUtility(xlpath);
		driver=new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.get("https://login.salesforce.com/?locale=in");
	}
	
	@Test(enabled = false)
	public void readExcelData() throws IOException {
		String fileName = "testdata.xlsx";
		String filePath = "F:\\APACHE POI";
		File file = new File(filePath + "\\" + fileName);
		// Create an object of File class to open xlsx file

		FileInputStream fs = new FileInputStream(file);
		// Creating a workbook
		Workbook workbook = null;
		// Find the file extension by splitting file name in substring and getting only
		// extension name

		String fileExtensionName = fileName.substring(fileName.indexOf("."));

		// Check condition if the file is xlsx file

		if (fileExtensionName.equals(".xlsx")) {

			// If it is xlsx file then create object of XSSFWorkbook class

			workbook = new XSSFWorkbook(fs);

		}

		// Check condition if the file is xls file

		else if (fileExtensionName.equals(".xls")) {

			// If it is xls file then create object of HSSFWorkbook class

			workbook = new HSSFWorkbook(fs);

		}
		Sheet sheet = workbook.getSheet("loginData");
		int totalrows = sheet.getPhysicalNumberOfRows();

		System.out.println("The number of rows in sheet are----> " + totalrows);
		// Create a loop over all the rows of excel file to read it

		for (int i = 0; i < totalrows; i++) {

			Row row = sheet.getRow(i);

			// Create a loop to print cell values in a row

			for (int j = 0; j < row.getLastCellNum(); j++) {

				// Print Excel data in console

				System.out.print(row.getCell(j).getStringCellValue() + "|| ");
			}
			System.out.println();

		}
		workbook.close();
	}

	@Test(enabled = false)
	public void loginSalesforce() throws IOException
	{
		String fileName = "testdata.xlsx";
		String filePath = "F:\\APACHE POI";
		File file = new File(filePath + "\\" + fileName);
		// Create an object of File class to open xlsx file

		FileInputStream fs = new FileInputStream(file);
		// Creating a workbook
		Workbook workbook = new XSSFWorkbook(fs);
		Sheet sheet = workbook.getSheet("loginData");
		String username=sheet.getRow(1).getCell(0).getStringCellValue();
		String password=sheet.getRow(1).getCell(1).getStringCellValue();
		
		//saleforece webelements
		driver.findElement(By.id("username")).sendKeys(username);
		driver.findElement(By.id("password")).sendKeys(password);
		driver.findElement(By.id("Login")).click();
		workbook.close();
	}
		@AfterTest
	public void closeAUT() {
		driver.close();
	}
}
