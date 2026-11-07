
package ObjectRepository;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class PlpPage {

    private WebDriver driver;

    @FindBy(xpath = "//h1[.='Search' and contains(@class,'plp-page-title')]")
    private WebElement SearchTxt;

    @FindBy(xpath = "//p[@class='results-for-text']/span")
    private WebElement InvalidSearchMsg;

    @FindBy(xpath = "//ol[contains(@class,'plp-breadcrumb')]/descendant::a")
    private WebElement SearchResult;

    @FindBy(xpath = "//span[.='Category']")
    private WebElement CategoryBtn;

    @FindBy(xpath = "//span[.='Strap Color']")
    private WebElement ColorFilter;

    @FindBy(xpath = "//button[.='Price']")
    private WebElement PriceText;

    @FindBy(xpath = "//div[@class='plp-price-inputs']/descendant::input")
    private WebElement MinPriceInput;

    @FindBy(xpath = "//span[.='to']/following-sibling::label/input")
    private WebElement MaxPriceInput;


    // Constructor
    public PlpPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }



public By getFilterOption(String filterName) {
    return By.xpath(
        "//label[contains(@class,'plp-filter-item')]" +
        "[contains(normalize-space(.),'" + filterName + "')]"
    );
}

public By getSelectedFilter(String filterName) {
    return By.xpath(
        "//span[contains(@class,'reval-filter-chip')]" +
        "[contains(normalize-space(.),'" + filterName + "')]"
    );
}



    // Getters
    public WebElement getSearchTxt() {
        return SearchTxt;
    }

    public WebElement getInvalidSearchMsg() {
        return InvalidSearchMsg;
    }

    public WebElement getSearchResult() {
        return SearchResult;
    }

    public WebElement getCategoryBtn() {
        return CategoryBtn;
    }

    public WebElement getColorFilter() {
        return ColorFilter;
    }

    public WebElement getPriceText() {
        return PriceText;
    }

    public WebElement getMinPriceInput() {
        return MinPriceInput;
    }

    public WebElement getMaxPriceInput() {
        return MaxPriceInput;
    }
}
