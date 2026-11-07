
package Fossil.Tests;

import java.time.Duration;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import GenericUtilities.BaseClass;
import ObjectRepository.PlpPage;

public class TC_004_Apply_FiltersTest extends BaseClass {

    @Test
    public void CheckFiltersTest() throws InterruptedException {

        // 1. Retrieve test data from Excel
        Map<String, String> map =
                ExcelUtil.getTestData("Sheet1", "TC_004");

        System.out.println("Excel Data: " + map);

        String SectionName = map.get("Category_Name");
        String Filter_Name = map.get("Filter_Name");

        Assert.assertNotNull(
                SectionName,
                "Category_Name is missing in Excel"
        );

        Assert.assertNotNull(
                Filter_Name,
                "Filter_Name is missing in Excel"
        );

        Assert.assertFalse(
                SectionName.trim().isEmpty(),
                "Category_Name is empty"
        );

        Assert.assertFalse(
                Filter_Name.trim().isEmpty(),
                "Filter_Name is empty"
        );


        // 2. Initialize wait and POM
        WebDriverWait wait =
                new WebDriverWait(driver, Duration.ofSeconds(20));

        PlpPage plp = new PlpPage(driver);


        // 3. Select category
        By SectionLinks = By.xpath(
                "//a[normalize-space(.)='" +
                SectionName + "' and @class='navLink']"
        );

        WebElement section = wait.until(
                ExpectedConditions.elementToBeClickable(SectionLinks)
        );

        section.click();

        System.out.println("Category selected: " + SectionName);


        // 4. Verify category
        wait.until(
                ExpectedConditions.visibilityOf(plp.getSearchResult())
        );

        String ExpectedResult = plp.getSearchResult().getText();

        Assert.assertTrue(
                ExpectedResult.contains(SectionName),
                "Category verification failed"
        );

        System.out.println("Category verified successfully");


        // 5. Scroll to Category filter section
        wdUtil.scrollToElementAction(
                driver,
                plp.getCategoryBtn()
        );



     // Locate the complete filter label
     By filterLocator = plp.getFilterOption(Filter_Name);

     WebElement filter = wait.until(
         ExpectedConditions.visibilityOfElementLocated(filterLocator)
     );

     // Scroll to the filter
     JavascriptExecutor js = (JavascriptExecutor) driver;

     js.executeScript(
         "arguments[0].scrollIntoView({block:'center'});",
         filter
     );

     // Click the complete label
     wait.until(ExpectedConditions.elementToBeClickable(filter));

     try {
         filter.click();
     } catch (ElementClickInterceptedException e) {
         js.executeScript("arguments[0].click();", filter);
     }

     System.out.println("Filter click attempted: " + Filter_Name);


     // Wait briefly for the page to update
     By selectedFilterLocator = plp.getSelectedFilter(Filter_Name);

     boolean chipDisplayed = false;

     try {
         wait.until(
             ExpectedConditions.visibilityOfElementLocated(
                 selectedFilterLocator
             )
         );
         chipDisplayed = true;

     } catch (org.openqa.selenium.TimeoutException e) {
         System.out.println("Selected filter chip was not displayed.");
     }


     // Debug: Print all available selected filter chips
     java.util.List<WebElement> chips = driver.findElements(
         By.cssSelector("span.plp-chip.reval-filter-chip")
     );

     System.out.println("Total selected chips: " + chips.size());

     for (WebElement chip : chips) {
         System.out.println("Available chip: " + chip.getText());
     }


     // Verify the filter
     Assert.assertTrue(
         chipDisplayed,
         "Filter was not applied. Selected filter chip is missing: " + Filter_Name
     );

     System.out.println("Filter applied successfully: " + Filter_Name);

    }
}
