package Fossil.Tests;

import java.util.LinkedHashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import GenericUtilities.BaseClass;
import ObjectRepository.HomePage;
import ObjectRepository.PlpPage;

public class NG002_InvalidProductSearchTest extends BaseClass {
	@Test
	public void InvalidProductSearchTest() {
		
		//Retrieving Data From Excel
		Map<String, String> map = new LinkedHashMap<String, String>();
		map = ExcelUtil.getTestData("Sheet1", "NG002");
		String product_Name = map.get("Product_Name");
		
		HomePage hp=new HomePage(driver);
		hp.SearchProduct(product_Name, driver);
		
		PlpPage plp=new  PlpPage(driver);
		wdUtil.ExplicitWaitForElementToBeClickable(driver, plp.getInvalidSearchMsg());
		String Invalid_Msg=plp.getInvalidSearchMsg().getText();
	
		
		Assert.assertTrue(Invalid_Msg.contains("Sorry, we couldn't find any results"));
		
	}

}
