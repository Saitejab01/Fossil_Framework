package ObjectRepository;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CartPage {
	
	@FindBy(xpath="//h1[@class='cart-title']")
	private WebElement CartTitle;
	
	@FindBy(xpath="//h5[contains(@class,'product-title')]/a[@class='title-hover']")
	private WebElement productTitle;
	
	@FindBy(xpath="//span[contains(@class,'price-current')]")
	private WebElement productPrice;
	
	@FindBy(xpath="//img[@alt='Plus']/..")
	private WebElement increaseQtyBtn;
	
	@FindBy(xpath="//img[@alt='Minus']/..")
	private WebElement decreaseQtyBtn;
	
	@FindBy(xpath="//p[contains(.,'Total:')]/span")
	private WebElement TotalPrice;
	
	public WebElement getTotalPrice() {
		return TotalPrice;
	}



	public CartPage(WebDriver driver) {
		PageFactory.initElements(driver, this);
		
	}

	public WebElement getCartTitle() {
		return CartTitle;
	}



	public WebElement getProductTitle() {
		return productTitle;
	}


	public WebElement getProductPrice() {
		return productPrice;
	}



	public WebElement getIncreaseQtyBtn() {
		return increaseQtyBtn;
	}

	

	public WebElement getDecreaseQtyBtn() {
		return decreaseQtyBtn;
	}

	
	
	

}
