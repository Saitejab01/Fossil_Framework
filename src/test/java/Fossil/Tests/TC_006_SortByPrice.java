
package Fossil.Tests;

import java.awt.AWTException;
import java.time.Duration;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import GenericUtilities.BaseClass;
import ObjectRepository.PlpPage;

public class TC_006_SortByPrice extends BaseClass {

	@Test
	public void SortProductsTest() throws AWTException, InterruptedException {

		// Retrieve test data from Excel
		Map<String, String> map = ExcelUtil.getTestData("Sheet1", "TC_006");

		String sectionName = map.get("Category_Name");

		int minPrice = Integer.parseInt(map.get("Min_Price"));
	

		Assert.assertNotNull(sectionName, "Category name is missing");

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

		// Navigate to category
		By sectionLinks = By.xpath("//a[normalize-space(.)='" + sectionName + "' and @class='navLink']");

		WebElement section = wait.until(ExpectedConditions.elementToBeClickable(sectionLinks));

		section.click();

		// Verify category
		PlpPage plp = new PlpPage(driver);

		wait.until(ExpectedConditions.visibilityOf(plp.getSearchResult()));

		String expectedResult = plp.getSearchResult().getText();

		Assert.assertTrue(expectedResult.contains(sectionName), "Category verification failed");

		// Enter minimum and maximum price
		wdUtil.scrollToElement(driver, plp.getMinPriceInput());

		plp.getMinPriceInput().click();

		plp.getMinPriceInput().sendKeys(Keys.CONTROL + "a");
		plp.getMinPriceInput().sendKeys(Keys.BACK_SPACE);

		wait = new WebDriverWait(driver, Duration.ofSeconds(15));
		plp.getMinPriceInput().sendKeys(String.valueOf(minPrice));

		// Enter minimum price
		WebElement minInput = wait.until(ExpectedConditions.elementToBeClickable(plp.getMinPriceInput()));

		wdUtil.scrollToElement(driver, minInput);

		minInput.click();
		minInput.sendKeys(Keys.CONTROL + "a");
		minInput.sendKeys(Keys.BACK_SPACE);
		minInput.sendKeys(String.valueOf(minPrice));

		// Verify minimum value
		System.out.println("Min value: " + minInput.getAttribute("value"));
		
		wdUtil.scrollToElement(driver, plp.getMaxPriceInput());

		WebElement maxInput = plp.getMaxPriceInput();

		String maxPriceText = maxInput.getAttribute("value");

		System.out.println("Maximum Price: " + maxPriceText);

		int maxPrice = Integer.parseInt(maxPriceText);


       // Apply filter
		wdUtil.RobotPressEnterkey();
		// Wait for product prices
		By priceLocator = By.xpath("//span[contains(@class,'regularPriceValue')]");

		wait.until(ExpectedConditions.visibilityOfElementLocated(priceLocator));

		// Get product prices
		List<WebElement> list = driver.findElements(priceLocator);

		Assert.assertTrue(list.size() > 0, "No products found");

		// Verify every product price
		for (WebElement product : list) {

			String price = product.getText().trim();

			// Remove currency symbols and decimal values
			String numericPrice = price.replaceAll("[^0-9.]", "").split("\\.")[0];

			int amount = Integer.parseInt(numericPrice);

			System.out.println("Product Price: " + amount);

			Assert.assertTrue(amount >= minPrice && amount <= maxPrice,
					"Price out of range: " + amount + " | Expected range: " + minPrice + " - " + maxPrice);

			System.out.println(amount + " - PASS");
		}

		System.out.println("All product prices are within the given range");
	}
}
