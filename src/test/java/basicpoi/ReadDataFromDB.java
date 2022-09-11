package basicpoi;

import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class ReadDataFromDB {
	WebDriver driver = null;
	Connection connection = null;
	ResultSet resultSet = null;
	Statement statement = null;

	@BeforeClass
	public void LoadAUT() {
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.get("https://login.salesforce.com/?locale=in");
	}

	@BeforeTest
	public void createDBConnection() {

		String dbConnectionUrl = "jdbc:mysql://localhost:3306/srinuvas";
		String dbUserName = "root";
		String dbPassword = "root";
		// load and registering driver.
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		// establishing connection
		try {
			connection = DriverManager.getConnection(dbConnectionUrl, dbUserName, dbPassword);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	@Test
	public void geDBData() {
		int id;
		String name, password;
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery("SELECT * FROM users");
			while (resultSet.next()) {
           	id = resultSet.getInt(1);
					name = resultSet.getString("user_name");
					if(name.equals("kuyy"))
					{
						password = resultSet.getString("user_password");
						// System.out.println("user id-->" + id + " user name-->" + name + " user
						// password-->" + password);
						driver.findElement(By.id("username")).sendKeys(name);
						Thread.sleep(1000);
						
						driver.findElement(By.id("password")).sendKeys(password);
					
						Thread.sleep(1000);
						

						driver.findElement(By.id("Login")).click();
					}
					
				//}
				
			}
			resultSet.close();
			statement.close();
			connection.close();
		} catch (SQLException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@AfterTest
	public void cloaseDBConnection() {
		// close the connection.
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@AfterClass
	public void cloaseAUT() {
	//	driver.close();
	}
}
