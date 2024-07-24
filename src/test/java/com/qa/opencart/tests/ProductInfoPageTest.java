package com.qa.opencart.tests;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.errors.AppError;
import com.qa.opencart.utils.ExcelUtil;

public class ProductInfoPageTest extends BaseTest{
	
	@BeforeClass
	public void productInfoPageSetup() {
		accPage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
	}
	
	@DataProvider
	public Object[][] getProductData() {
		return new Object[][] {
			{"macbook","MacBook Pro"},
			{"iMac","iMac"},
			{"samsung","Samsung Galaxy Tab 10.1"}
			
		};
	}
	
	@DataProvider
	public Object[][] getProductImageData() {
		return new Object[][] {
			{"macbook","MacBook Pro", 4},
			{"iMac","iMac",4},
			{"samsung","Samsung Galaxy Tab 10.1", 7}
			
		};
	}
	
	@DataProvider
	public Object[][] getProductImageSheetData() {
		return ExcelUtil.getTestData(AppConstants.PRODUCT_IMAGES_SHEET_NAME); 
			
	}
	
	
	@Test(dataProvider = "getProductData")
	public void productHeaderTest(String searchKey, String productName) {
		searchResultsPage = accPage.doSearch(searchKey);
		productInfoPage = searchResultsPage.selectProduct(productName);
		Assert.assertEquals(productInfoPage.getProductHeader(),productName, AppError.HEADER_NOT_FOUND);
	}
	
	
	
	//Test with single Assertion (use hard Assertion i.e. Assert)
	@Test(dataProvider = "getProductImageSheetData")
	public void productImagesCountTest(String searchKey, String productName, String imagesCount) {
		searchResultsPage = accPage.doSearch(searchKey);
		productInfoPage = searchResultsPage.selectProduct(productName);
		Assert.assertEquals(productInfoPage.getProductImagesCount(),Integer.parseInt(imagesCount), AppError.IMAGES_COUNT_MISMATCHED);
	}
	
//	@DataProvider
//	public Object[][] expectedProductInofData() {
//		return new Object[][] {
//			{"macbook","MacBook Pro","Apple","Product 18","800","In Stock","$2,000.00","$2,000.00"},
//			{"imac","iMac","Apple","Product 14","800","In Stock","$2,000.00","$2,000.00"
//			
//		};
//	}
	//Test with Multiple Assertions (use SoftAssert)
	@Test
	public void productInfoTest() {
		searchResultsPage = accPage.doSearch("macbook");
		productInfoPage = searchResultsPage.selectProduct("MacBook Pro");
		Map<String, String> productInfoMap = productInfoPage.getProductInfoMap();
		System.out.println("========Product Information=======");
		System.out.println(productInfoMap);
		softAssert.assertEquals(productInfoMap.get("productname"), "MacBook Pro");//Pass
		softAssert.assertEquals(productInfoMap.get("Brand"), "Apple"); //Fail
		softAssert.assertEquals(productInfoMap.get("Product Code"), "Product 18");//Pass
		softAssert.assertEquals(productInfoMap.get("Reward Points"), "800"); //Pass
		softAssert.assertEquals(productInfoMap.get("Availability"), "In Stock"); //Pass
		softAssert.assertEquals(productInfoMap.get("productprice"), "$2,000.00"); //Pass
		softAssert.assertEquals(productInfoMap.get("extaxprice"), "$2,000.00"); //Pass
		softAssert.assertAll(); //will Assert the result of all the softAsserts 
								//and give Failure Info (1) that how many assertions got failed
		
	}
	
	//hard assert (Assert class) Vs Soft assert (verify - SoftAssert class)
	//Assert ---> all methods are static
	//SoftAssert ---> all methods are non-static
	
	
	
}
