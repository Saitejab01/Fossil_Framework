package GenericUtilities;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import ObjectRepository.HomePage;
import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseClass {

	public String propertyFilePath = "./src/test/resources/CommonData.properties";

	//public String ExcelFilePath = System.getProperty("user.dir") + "/src/test/resources/Fossil_TestData.xlsx";
	
	public String ExcelFilePath =  "./src/test/resources/Fossil_TestData.xlsx";

	public WebDriverUtility wdUtil = new WebDriverUtility();
	public JavaUtility jUtil = new JavaUtility();
	public FileUtility fUtil = new FileUtility();
	public ExcelUtility ExcelUtil = new ExcelUtility(ExcelFilePath);
	public WebDriver driver;

	public static WebDriver sdriver;

	@BeforeSuite(alwaysRun = true)
	public void bsConfig() {
		System.out.println("======== DB CONNECTION SUCCESSFULL==========");
	}

	// @Parameters("browser")
//	@BeforeTest(alwaysRun =true)

	@BeforeClass(alwaysRun = true)
	public void bcConfig(/* String BROWSER */) throws IOException {
		String BROWSER = fUtil.readDataFromPropertyFile("browser", propertyFilePath);
		String URL = fUtil.readDataFromPropertyFile("Url", propertyFilePath);
		// WebDriver driver;

		if (BROWSER.equals("chrome")) {
			ChromeOptions options = new ChromeOptions();

			Map<String, Object> prefs = new HashMap<>();

			// Block location permission
			prefs.put("profile.default_content_setting_values.geolocation", 2);

			// Block notifications
			prefs.put("profile.default_content_setting_values.notifications", 2);

			options.setExperimentalOption("prefs", prefs);

			driver = new ChromeDriver(options);

		} else if (BROWSER.equals("edge")) {

			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver();
		} else if (BROWSER.equals("firefox")) {
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
		} else {
			driver = new EdgeDriver();
		}
		// maximize window

		sdriver = driver;
		// WebDriverUtility wd=new WebDriverUtility();
		wdUtil.maximizeWindow(driver);
		driver.get(URL);

		System.out.println("===========SUCCESSFULLY LAUNCHED " + BROWSER + " browser============");

	}


@BeforeMethod
public void bmConfig() throws IOException {

    WebDriverUtility wdUtil = new WebDriverUtility();
    HomePage hp = new HomePage(driver);

    List<WebElement> popup = driver.findElements(By.xpath("//button[normalize-space()='Ok']"));

    if (!popup.isEmpty() && popup.get(0).isDisplayed() && popup.get(0).isEnabled()) {
        popup.get(0).click();
        System.out.println("OK popup closed successfully");
    } else {
        System.out.println("OK popup is not displayed. Continuing...");
    }
}


	@AfterMethod(alwaysRun = true)
	public void amConfig() {

	}

	// @AfterTest(alwaysRun =true)
	@AfterClass(alwaysRun = true)
	public void acConfig() {
		driver.quit();
		System.out.println("==========SUCCESSFULLLY CLOSED  BROWSER============");
	}

	@AfterSuite(alwaysRun = true)
	public void asConfig() {
		System.out.println("=========DB CONNECTION CLOSED==========");
	}

}
