
package Fossil.Tests;

import java.time.Duration;
import java.util.LinkedHashMap;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import GenericUtilities.BaseClass;
import GenericUtilities.ListenersImplementation;
import ObjectRepository.HomePage;
import ObjectRepository.PdpPage;

@Listeners(ListenersImplementation.class)

public class TC_001_AddProductToCartTest extends BaseClass {

    @Test
    public void AddToCartTest() {

        // Retrieve test data from Excel
        Map<String, String> map =
                new LinkedHashMap<String, String>();

        map = ExcelUtil.getTestData("Sheet1", "TC_001");

        String product_Name = map.get("Product_Name");

        WebDriverWait wait =
                new WebDriverWait(driver, Duration.ofSeconds(20));

        HomePage Hp = new HomePage(driver);

        // Validate Home Page
        String pageTitle = driver.getTitle();

        Assert.assertEquals(pageTitle,
                "Fossil India - The Official Site for Fossil Watches, Bags, Jewelry and More");

        // Search Product
        Hp.SearchProduct(product_Name, driver);

        // Product locator
        By productLocator = By.xpath(
                "//a[normalize-space(.)='" + product_Name + "']"
        );

        // Wait until product is visible
        WebElement productLink = wait.until(
                ExpectedConditions.visibilityOfElementLocated(productLocator)
        );

        // Scroll to product
        wdUtil.scrollByAmountAction(driver, 0, 300);

        productLink = wait.until(
                ExpectedConditions.visibilityOfElementLocated(productLocator)
        );

        wdUtil.scrollToElementAction(driver, productLink);

        // Product price locator
        By priceLocator = By.xpath(
                "//a[normalize-space(.)='" + product_Name
                + "']/ancestor::div[contains(@class,'plp-cell plp-')]"
                + "/following-sibling::div"
                + "//span[contains(@class,'currentPriceValue')]"
        );

        // Get product price from PLP
        String priceInPLP = wait.until(
                ExpectedConditions.visibilityOfElementLocated(priceLocator)
        ).getText().trim();

        System.out.println("Product Price in PLP: " + priceInPLP);

        // Click product using locator-based wait
        wait.until(
                ExpectedConditions.elementToBeClickable(productLocator)
        ).click();

        // PDP validation
        PdpPage pdp = new PdpPage(driver);

        wait.until(
                ExpectedConditions.visibilityOf(pdp.getProductTitle())
        );

        String pdpProdName = pdp.getTitleOfProduct();

        Assert.assertTrue(
                pdpProdName.contains(product_Name),
                "Product name mismatch in PDP"
        );

        String priceInPDP = pdp.getpriceOfProd().trim();

        Assert.assertEquals(
                priceInPDP,
                priceInPLP,
                "Product price mismatch between PLP and PDP"
        );

        System.out.println("Product Name and Price validated in PDP");

        // Scroll to Add To Cart
        wdUtil.scrollToElementAction(driver, pdp.getAddToCartButton());

        wait.until(
                ExpectedConditions.elementToBeClickable(pdp.getAddToCartButton())
        ).click();

        // Validate Checkout Popup
        wait.until(
                ExpectedConditions.visibilityOf(pdp.getProdTtleInCheckoutPopUp())
        );

        String popupTitle =
                pdp.getProdTtleInCheckoutPopUp().getText().trim();

        Assert.assertTrue(
                product_Name.equalsIgnoreCase(popupTitle),
                "Product name mismatch in checkout popup"
        );

        System.out.println("Product validated in checkout popup");

        // Click Checkout
        wait.until(
                ExpectedConditions.elementToBeClickable(pdp.getCheckoutButton())
        ).click();

        // Validate Login Popup
        wait.until(
                ExpectedConditions.visibilityOf(pdp.getLoginPopup())
        );

        String loginText = pdp.getLoginPopup().getText();

        Assert.assertTrue(
                loginText.contains("Login"),
                "Login popup is not displayed"
        );

        System.out.println("Login popup validated successfully");
    }
}
