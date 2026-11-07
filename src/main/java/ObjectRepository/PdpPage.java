package ObjectRepository;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class PdpPage {

	@FindBy(xpath = "//h1[contains(@class,'pdp-title')]")
	private WebElement productTitle;

	@FindBy(xpath = "//span[.='Now']/following-sibling::span[contains(.,'₹')]")
	private WebElement productPrice;

	@FindBy(xpath = "//button[@class='btn-add-bag']")
	private WebElement AddToCartButton;
	
	@FindBy(xpath="(//div[@class='cart-footer']/descendant::button[.='Checkout'])[last()]")
	private WebElement CheckOutButton;
	
	@FindBy(xpath="//h1[.='Login or Signup']")
	private WebElement loginPopup;
	
	@FindBy(xpath="//strong[.='Fossil Fast Delivery']")
	private WebElement FastDeliveryBtn;

	@FindBy(xpath="(//div[@class='item-title']/descendant::a/span)[last()]")
	private WebElement ProdTtleInCheckoutPopUp;
	
	@FindBy(xpath="(//h4[.='Your shopping bag']/following-sibling::div/img[@alt=\"Close\"])[last()]")
	private WebElement CloseCheckoutPopup;
	
	@FindBy(xpath="//a[.='Go To Cart']")
	private WebElement GoToCartBtn;
	
	public WebElement getCloseCheckoutPopup() {
		return CloseCheckoutPopup;
	}


	public WebElement getGoToCartBtn() {
		return GoToCartBtn;
	}


	public WebElement getProdTtleInCheckoutPopUp() {
		return ProdTtleInCheckoutPopUp;
	}

	

	public WebElement getFastDeliveryBtn() {
		return FastDeliveryBtn;
	}
	
	public WebElement getLoginPopup() {
		return loginPopup;
	}
	public WebElement getProductTitle() {
		return productTitle;
	}

	public WebElement getProductPrice() {
		return productPrice;
	}
	
	
	
	public WebElement getAddToCartButton() {
		return AddToCartButton;
	}



	public WebElement getCheckoutButton() {
		return CheckOutButton;
	}

	
	public PdpPage(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}

	

	// Business Library
	public String getTitleOfProduct() {
		String value = productTitle.getText();
		return value;
	}

	public String getpriceOfProd() {
		String price = productPrice.getText();
		return price;

	}

}
