
package Fossil.Tests;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import GenericUtilities.BaseClass;
import ObjectRepository.PlpPage;

public class TC_003_NavigateToCategories extends BaseClass {

    @Test(dataProvider = "getData")
    public void NavigateToSectionsTest(String CategoryName) {

        wdUtil.ImplicitlyWaits(driver);

        WebDriverWait wait =
                new WebDriverWait(driver, Duration.ofSeconds(20));

        // Navigate to category
        By categoryLinks = By.xpath(
                "//a[normalize-space(.)='" + CategoryName
                + "' and @class='navLink']"
        );

        WebElement category = wait.until(
                ExpectedConditions.elementToBeClickable(categoryLinks)
        );

        category.click();

        // Validate category after page update
        PlpPage plp = new PlpPage(driver);

        wait.until(d -> {
            String actual = plp.getSearchResult().getText().trim();

            System.out.println(
                    "Expected Category: " + CategoryName
                    + " | Actual Result: " + actual
            );

            return actual.equalsIgnoreCase(CategoryName);
        });

        String actualResult = plp.getSearchResult().getText().trim();

        Assert.assertEquals(
                actualResult.toLowerCase(),
                CategoryName.toLowerCase(),
                "Category verification failed!"
        );

        System.out.println(CategoryName + " - PASS");
    }

    @DataProvider
    public Object[][] getData() {
        return new Object[][] {
            {"Jewelry"},
            {"Mens"},
            {"Bags"},
            {"Womens"}
        };
    }
}
