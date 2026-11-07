package ObjectRepository;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.mysql.cj.protocol.x.Ok;

import GenericUtilities.WebDriverUtility;

public class HomePage {
	
	@FindBy(xpath="//button[@aria-label='Open search' and contains(@class,'search-form')]")
	private WebElement searchIcon;
	
	@FindBy(xpath="//input[@class='header-search-input']")
	private WebElement searchTextField;
	
	@FindBy(xpath="//button[.='Search']")
	private WebElement SearchButton;
	
	@FindBy(xpath="//button[.='Ok']")
	private WebElement OkPopup;
	
	public WebElement getOkPopup() {
		return OkPopup;
	}

	

	public HomePage(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}

	public WebElement getSearchIcon() {
		return searchIcon;
	}

	public WebElement getSearchTextField() {
		return searchTextField;
	}

	public WebElement getSearchButton() {
		return SearchButton;
	}
	
	//Business Library
	public void SearchProduct(String productName,WebDriver driver) {
		WebDriverUtility wdUtil=new WebDriverUtility();
		
		wdUtil.ExplicitWaitForElementToBeClickable(driver, searchIcon);
		searchIcon.click();
		wdUtil.ExplicitWaitForElementToBeClickable(driver, searchTextField);
		searchTextField.sendKeys(productName);
		SearchButton.click();
	}

}
