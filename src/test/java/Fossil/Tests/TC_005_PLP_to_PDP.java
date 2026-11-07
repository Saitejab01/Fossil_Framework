package Fossil.Tests;

import java.time.Duration;
import java.util.LinkedHashMap;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import GenericUtilities.BaseClass;
import ObjectRepository.HomePage;
import ObjectRepository.PdpPage;
import ObjectRepository.PlpPage;

public class TC_005_PLP_to_PDP extends BaseClass {
	
	@Test
	public void NavigateFromPlpToPDPTest() {
		
		// Retrieving Data From Excel
				Map<String, String> map = new LinkedHashMap<String, String>();
				map = ExcelUtil.getTestData("Sheet1", "TC_005");

				String product_Name = map.get("Product_Name");

				wdUtil.ImplicitlyWaits(driver);

				

				HomePage Hp = new HomePage(driver);

				String PageTitle = driver.getTitle();

				Assert.assertEquals(PageTitle, "Fossil India - The Official Site for Fossil Watches, Bags, Jewelry and More");

				// Searching the Product
				Hp.SearchProduct(product_Name, driver);

				PlpPage plp = new PlpPage(driver);

				// Navigating from PLP to PDP

				   // Product locator
		        By productLocator = By.xpath(
		                "//a[normalize-space(.)='" + product_Name + "']"
		        );
		        
		        WebDriverWait wait=new WebDriverWait(driver,Duration.ofSeconds(10));

		        // Wait until product is visible
		        WebElement productLink = wait.until(
		                ExpectedConditions.visibilityOfElementLocated(productLocator)
		        );

				
				wdUtil.scrollByAmountAction(driver, 0, 300);
				wdUtil.scrollToElementAction(driver,productLink );

				// Getting Product Price From PLP
				By priceLocator = By.xpath("//a[.='"+product_Name+"']/ancestor::div[contains(@class,\"plp-cell plp-\")]/following-sibling::div/descendant::span[contains(@class,\"currentPriceValue\")]");
				

				
				

				// Clicking Product
				wdUtil.ExplicitWaitForElementToBeClickable(driver, productLink);
				// Getting Product Price From PLP
				String priceInPLP = driver.findElement(priceLocator).getText();

				// Clicking Product
				wdUtil.ExplicitWaitForElementToBeClickable(driver, productLink);
				productLink.click();

				// Performing Validations In PDP
				PdpPage pdp = new PdpPage(driver);

				wdUtil.ExplicitWaitForElementThatIsVisible(driver, pdp.getProductTitle());

				String pdpProdName = pdp.getTitleOfProduct();

				Assert.assertTrue(pdpProdName.contains(product_Name));

				Assert.assertEquals(priceInPLP, pdp.getpriceOfProd());

	}

}
