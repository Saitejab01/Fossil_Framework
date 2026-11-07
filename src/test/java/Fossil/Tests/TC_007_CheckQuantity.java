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
import ObjectRepository.CartPage;
import ObjectRepository.HomePage;
import ObjectRepository.PdpPage;
import ObjectRepository.PlpPage;

public class TC_007_CheckQuantity extends BaseClass {

	@Test
	public void CheckQuantity() throws InterruptedException {
		// Retrieving Data From Excel
		Map<String, String> map = new LinkedHashMap<String, String>();
		map = ExcelUtil.getTestData("Sheet1", "TC_007");

		String product_Name = map.get("Product_Name");

		wdUtil.ImplicitlyWaits(driver);

		HomePage Hp = new HomePage(driver);

		String PageTitle = driver.getTitle();

		Assert.assertEquals(PageTitle, "Fossil India - The Official Site for Fossil Watches, Bags, Jewelry and More");

		// Searching the Product
		Hp.SearchProduct(product_Name, driver);

		PlpPage plp = new PlpPage(driver);

		// Navigating from PLP to PDP

		 By productLocator = By.xpath(
	                "//a[normalize-space(.)='" + product_Name + "']"
	        );
	        
	        WebDriverWait wait=new WebDriverWait(driver,Duration.ofSeconds(10));

	        // Wait until product is visible
	        WebElement product_link = wait.until(
	                ExpectedConditions.visibilityOfElementLocated(productLocator)
	        );

		wdUtil.scrollByAmountAction(driver, 0, 300);
		wdUtil.scrollToElementAction(driver, product_link);

		// Getting Product Price From PLP
		By priceLocator = By.xpath("//a[.='" + product_Name
				+ "']/ancestor::div[contains(@class,\"plp-cell plp-\")]/following-sibling::div/descendant::span[contains(@class,\"currentPriceValue\")]");

		// Clicking Product
		wdUtil.ExplicitWaitForElementToBeClickable(driver, product_link);
		// Getting Product Price From PLP
		String priceInPLP = driver.findElement(priceLocator).getText();

		// Clicking Product
		wdUtil.ExplicitWaitForElementToBeClickable(driver, product_link);
		product_link.click();

		// Performing Validations In PDP
		PdpPage pdp = new PdpPage(driver);

		wdUtil.ExplicitWaitForElementThatIsVisible(driver, pdp.getProductTitle());

		String pdpProdName = pdp.getTitleOfProduct();

		Assert.assertTrue(pdpProdName.contains(product_Name));

		Assert.assertEquals(priceInPLP, pdp.getpriceOfProd());

		// Scrolling To Add To Cart
		wdUtil.scrollToElementAction(driver, pdp.getAddToCartButton());
		wdUtil.ExplicitWaitForElementToBeClickable(driver, pdp.getAddToCartButton());
		pdp.getAddToCartButton().click();

		// Validate Checkout Popup
		wdUtil.ExplicitWaitForElementThatIsVisible(driver, pdp.getProdTtleInCheckoutPopUp());

		String title = pdp.getProdTtleInCheckoutPopUp().getText();
		Assert.assertTrue(product_Name.equalsIgnoreCase(title));

		pdp.getCloseCheckoutPopup().click();

		// Go to Cart

		wdUtil.ExplicitWaitForElementToBeClickable(driver, pdp.getGoToCartBtn());

		pdp.getGoToCartBtn().click();

		CartPage cart = new CartPage(driver);
		String cartTitle = cart.getCartTitle().getText();

		Assert.assertTrue(cartTitle.contains("Shopping Cart"));


String expected = cart.getProductTitle().getText();

Assert.assertTrue(product_Name.equalsIgnoreCase(expected),
        "Product name mismatch. Expected: " + product_Name
        + ", Actual: " + expected);

System.out.println("Product Name validated in Cart");

String priceBefore = cart.getProductPrice().getText().trim();

System.out.println("Price Before: " + priceBefore);

Assert.assertEquals(priceBefore, priceInPLP,
        "Product price mismatch between PLP and Cart");

int before = Integer.parseInt(priceBefore.replaceAll("[^0-9]", ""));

cart.getIncreaseQtyBtn().click();

Thread.sleep(2000);

String priceAfter = cart.getTotalPrice().getText().trim();

System.out.println("Price After: " + priceAfter);

int after = Integer.parseInt(priceAfter.replaceAll("[^0-9]", ""));

Assert.assertTrue(after > before,
        "Total price did not increase. Before: " + before
        + ", After: " + after);


	}

}
