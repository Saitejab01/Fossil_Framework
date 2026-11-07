package GenericUtilities;


import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.Set;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.WheelInput.ScrollOrigin;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 *  This class consists of generic  methods related to web driver operations 
 * @author Vemula Harika
 */
public class WebDriverUtility {
	//maximize,minimize,full screen
	
	/**
	 * this method maximize the window
	 * @param driver
	 */
	
	public void maximizeWindow(WebDriver driver) {
		
		driver.manage().window().maximize();
		
	}

	/**
	 * This method will minimize the window
	 * @param driver
	 */
	public void minimizeWindow(WebDriver driver) {
		
		driver.manage().window().minimize();
		
	}
	
	public void closingBrowser(WebDriver driver) {
		driver.close();
	}
	//implicit wait,explicitly wait -visible,clikable
	
	/**
	 * This method  is for implicit wait
	 * @param driver
	 */
	
	public void ImplicitlyWaits(WebDriver driver) {
		
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
		
		
	}
	/**
	 * This method is for explicit wait on element that is clikable
	 * @param driver
	 * @param ele
	 */
	
	public void ExplicitWaitForElementToBeClickable(WebDriver driver,WebElement ele) {
		
		WebDriverWait wt=new WebDriverWait(driver, Duration.ofSeconds(20));
		
		wt.until(ExpectedConditions.elementToBeClickable(ele));
	
		
	}
	
	/**
	 * This method is for explicit wait on element that is visible
	 * @param driver
	 * @param ele
	 */
	public void ExplicitWaitForElementThatIsVisible(WebDriver driver,WebElement ele) {
		
		WebDriverWait wt=new WebDriverWait(driver, Duration.ofSeconds(10));
		wt.until(ExpectedConditions.visibilityOfAllElements(ele));
	}
	/**
	 * This is method will select the element by index
	 * @param ele
	 * @param index
	 */

	public void HandleDropDown(WebElement ele,int index) {
		Select s=new Select(ele);
		s.selectByIndex(index);
		
	}
    /**
     * This is method will select the element by value
     * @param ele
     * @param value
     */
	
	
	public void HandleDropDown(WebElement ele,String value) {
		Select s=new Select(ele);
		s.selectByValue(value);
		
	}
	/**
     * This is method will select the element by visible text
     * @param ele
     * @param value
     */
	
	
	public void HandleDropDown(String text,WebElement ele) {
		Select s=new Select(ele);
		s.selectByVisibleText(text);
		
		
	}
	/**
	 * This method performs mouse hovering action
	 * @param driver
	 * @param ele
	 */
	
	public void MouseOveringAction(WebDriver driver,WebElement ele) {
		Actions act=new Actions(driver);
		act.moveToElement(ele).perform();
				
		
	}
	/**
	 * This method performsmoving action by offset action
	 * @param driver
	 * @param x
	 * @param y
	 */
	public void MoveByOffsetAction(WebDriver driver,int x,int y) {
		Actions act=new Actions(driver);
		act.moveByOffset(x, y).perform();
		
		
	}
	/**
	 * This method performs drag and drop action
	 * @param driver
	 * @param src
	 * @param trgt
	 * @param x
	 * @param y
	 */
	
	
	public void  dragAndDropAction(WebDriver driver,WebElement src,WebElement trgt,int x,int y) {
		Actions act=new Actions(driver);
		act.dragAndDrop(src, trgt).perform();
		act.dragAndDropBy(src, x, y).perform();
		
	}
	/**
	 * This method performs scroll to element action
	 * @param driver
	 * @param ele
	 */
	
	public void scrollToElementAction(WebDriver driver,WebElement ele) {
		Actions act=new Actions(driver);
		act.scrollToElement(ele).perform();
		
	}
	/**
	 * This method performs scroll by amount action
	 * @param driver
	 * @param x
	 * @param y
	 */
	
	public void scrollByAmountAction(WebDriver driver,int x,int y) {
		Actions act=new Actions(driver);
		act.scrollByAmount(x, y).perform();
		
		
		
	}
	/**
	 * This method performs scrolls to element action
	 * @param driver
	 * @param ele
	 * @param x
	 * @param y
	 */
	public void scrollFromOrigin(WebDriver driver,WebElement ele,int x,int y) {
		Actions act=new Actions(driver);
		act.scrollFromOrigin(ScrollOrigin.fromElement(ele), x, y).perform();
		
	}
	/**
	 * This method performs release action
	 * @param driver
	 * @param ele
	 */
	
	public void ReleaseAction(WebDriver driver,WebElement ele) { 
		Actions act=new Actions(driver);
		act.clickAndHold().release(ele).perform();
		
	}
	/**
	 * This method performs double click action
	 * @param driver
	 * @param ele
	 */
	
	public void doubleClickAction(WebDriver driver,WebElement ele) {
		Actions act=new Actions(driver);
		
		act.doubleClick(ele).perform();
	}
	/**
	 * This method performs right click action
	 * @param driver
	 * @param ele
	 */
	
	public void contextClickAction(WebDriver driver,WebElement ele) {
		Actions act=new Actions(driver);
		act.contextClick(ele).perform();
		
	}
	/**
	 * This method switchs to frame
	 * @param driver
	 * @param index
	 */
	
	public void switchingToFrame(WebDriver driver,int index) {
		driver.switchTo().frame(index);
		
	}
	/**
	 * This method switchs to  frame by frame name
	 * @param driver
	 * @param name
	 */
	public void switchingToFrameByString(WebDriver driver,String name) {
		driver.switchTo().frame(name);
		
	}
	/**
	 * This method switchs to  frame by element
	 * @param driver
	 * @param ele
	 */
	public void switchingToFrameByString(WebDriver driver,WebElement ele) {
		driver.switchTo().frame(ele);
	}
	/**
	 *  This method switchs to parent frame
	 * @param driver
	 */
	public void SwitchingToParentFrame(WebDriver driver) {
		driver.switchTo().parentFrame();
	}
	/**
	 *  This method switchs to  main frame
	 * @param driver
	 */
	public void switchingToDefaultContext(WebDriver driver)
	{
		driver.switchTo().defaultContent();
	}
	/**
	 * This method accepts the alert
	 * @param driver
	 */
	
	
	public void AcceptingAlert(WebDriver driver) {
		driver.switchTo().alert().accept();
		
	}
	/**
	 *  This method cancels the alert
	 * @param driver
	 */
	
	public void DismissAlert(WebDriver driver) {
		driver.switchTo().alert().dismiss();
		
	}
	/**
	 *  This method gets the text of the alert
	 * @param driver
	 */
	public void getTextOfAlert(WebDriver driver) {
		driver.switchTo().alert().getText();
	}
	/**
	 *  This method sends keys to the alert
	 * @param driver
	 * @param name
	 */
	public void sendKeysToAlert(WebDriver driver,String name) {
		driver.switchTo().alert().sendKeys(name);
		
	}
	/**
	 * This method is for switching to child windows
	 * @param driver
	 */
	 
	public void SwitchingToWindow(WebDriver driver) {
		String mainHand = driver.getWindowHandle();
		Set<String> handles = driver.getWindowHandles();
		for(String hh:handles) {
			if(!hh.equals(mainHand)) {
				driver.switchTo().window(hh);
			}
		}
		
	}
	/**
	 * This method is for switching to window if it matches the title
	 * @param driver
	 * @param reqTitle
	 */
	
	public void switchingToWindowByTitle(WebDriver driver,String reqTitle) {
		
		Set<String> handles = driver.getWindowHandles();
		for(String hh:handles) 
		{
			driver.switchTo().window(hh);
			String title=driver.getTitle();
			if(title.contains(reqTitle)) {
				//driver.switchTo().window(title);
				break;
			}
			
		}
		}
	
	/**
	 * This method takes screenshot and returns the path to the caller
	 * @param driver
	 * @param screenshotname
	 * @return
	 * @throws IOException
	 */
	
	public String captureScreenShot(WebDriver driver,String screenshotname) throws IOException {
		
		TakesScreenshot ts=( TakesScreenshot)driver;
		
		File src=ts.getScreenshotAs(OutputType.FILE);
		File dest=new File(".\\ScreenShots\\"+screenshotname+".png");
		FileHandler.copy(src, dest);
		
		return dest.getAbsolutePath();  //for extent reports
		
		
	}
	
	public void scrollUsingJs(WebDriver driver,int Y) {
		JavascriptExecutor js=(JavascriptExecutor)driver;
		js.executeScript("window.scrollTo(0,'"+Y+"')");
	}
		
	
	public void scrollToElement(WebDriver driver,WebElement ele) {
		JavascriptExecutor js = (JavascriptExecutor) driver;

		js.executeScript("arguments[0].scrollIntoView(true)", ele);
	}
	
	public void ClickOnElementUsingJs(WebDriver driver,WebElement ele) {
		JavascriptExecutor js = (JavascriptExecutor) driver;

		js.executeScript("arguments[0].click()", ele);
	}
	
	
	public void MoveOnElement(WebDriver driver,WebElement ele) {
		Actions act=new Actions(driver);
		act.moveToElement(ele).perform();
	}
	
	public void Click(WebDriver driver) {
		Actions act=new Actions(driver);
		act.click();
	}
	
	public void RobotPressEnterkey() throws AWTException {
		Robot r=new Robot();
		r.keyPress(KeyEvent.VK_ENTER);
	}
	
	public void RobotReleaseEnterkey() throws AWTException {
		Robot r=new Robot();
		r.keyRelease(KeyEvent.VK_ENTER);
	}
	
	public void ExplicitWait(WebDriver driver,int sec) {
		WebDriverWait wait=new WebDriverWait(driver,Duration.ofSeconds(sec) );
	}
	
	
	

	

}
