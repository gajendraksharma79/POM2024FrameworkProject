package com.qa.opencart.pages;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.utils.ElementUtil;
import com.qa.opencart.utils.TimeUtil;

public class ProductInfoPage {
	private WebDriver driver;
	private By productHeader = By.cssSelector("div#content h1");
	private By productImgsCount = By.cssSelector("div#content img");
	private By productMetaData = By.xpath("(//div[@id='content']//ul[@class='list-unstyled'])[1]/li");
	private By productPriceData = By.xpath("(//div[@id='content']//ul[@class='list-unstyled'])[2]/li");
	private Map<String, String> productMap;
	ElementUtil eleuUtil;
	
	public ProductInfoPage(WebDriver driver){
		this.driver = driver;
		eleuUtil = new ElementUtil(driver);
	}
	
	public String getProductHeader() {
		String header = eleuUtil.doGetText(productHeader);
		System.out.println("Product header is : "+header);
		return header;
	}
	
	public int getProductImagesCount() {
		int imagesCount = eleuUtil
				.waitForVisibilityOfElementsLocated(productImgsCount, TimeUtil.DEFAULT_MEDIUM_TIME).size();
		System.out.println("total images == "+imagesCount);
		return imagesCount;
	}
	
	public Map<String, String> getProductInfoMap() {
		productMap = new HashMap<String, String>();
//		productMap = new LinkedHashMap<String, String>();
//		productMap = new TreeMap<String, String>();
		productMap.put("productname", getProductHeader());
		productMap.put("productimagescount", String.valueOf(getProductImagesCount()));
		getProductMetaData();
		getProductPriceData();
		return productMap;
	}
	
	private void getProductMetaData() {
		List<WebElement> metaList = eleuUtil.getElements(productMetaData);
		
		for(WebElement e : metaList) {
			String metaData = e.getText();
			String[] meta = metaData.split(":");
			String metaKey = meta[0].trim();
			String metaValue = meta[1].trim();
			productMap.put(metaKey, metaValue);
		}

	}
	
//	$2,000.00
//	Ex Tax: $2,000.00
	
	private void getProductPriceData() {
		List<WebElement> priceList = eleuUtil.getElements(productPriceData);
		String productPrice = priceList.get(0).getText();
		String extaxPrice = priceList.get(1).getText().split(":")[1].trim();
		productMap.put("productprice",productPrice);
		productMap.put("extaxprice", extaxPrice);

	}

	

}
