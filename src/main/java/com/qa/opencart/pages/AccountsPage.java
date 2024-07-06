package com.qa.opencart.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;
import com.qa.opencart.utils.TimeUtil;

public class AccountsPage {
	private WebDriver driver;
	private ElementUtil eleUtil;
	
	public AccountsPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}
	
	private By logOutLink = By.linkText("Logout");
	private By headers = By.cssSelector("div#content H2");
	private By search = By.name("search");
	private By searchBtn = By.cssSelector("div#search button");
	
	public String getAccountPageTitle() {
		String title = eleUtil.waitForTitleToBe(AppConstants.ACCOUNT_PAGE_TITLE,TimeUtil.DEFAULT_TIME);
		System.out.println("Account Page Title is : "+title);
		return title;
	}
	
	public String getAccountPageURL() {
		
		String url = eleUtil.waitForURLContains(AppConstants.ACCOUNT_PAGE_FRACTION_URL, TimeUtil.DEFAULT_TIME);
		System.out.println("Account Page URL is : " +url);
		return url;
		
	} 
	
	public boolean isLogOutLinkExist() {
		return eleUtil.doIsDisplayed(logOutLink);
	}
	
	public List<String> getAccPageHeaders() {
		
		List<WebElement> headersList = eleUtil.waitForVisibilityOfElementsLocated(headers, 
				TimeUtil.DEFAULT_MEDIUM_TIME);	
		
		List<String> headersValList = new ArrayList<String>();
		for(WebElement e : headersList) {
			String text = e.getText();
			headersValList.add(text);
		}
		return headersValList;
	}
	
	public boolean isSearchExist() {
		return eleUtil.doIsDisplayed(search);
	}
	
	public SearchResultsPage doSearch (String searchKey) {
		System.out.println("searching for product : "+searchKey);
		if(isSearchExist()) {
			eleUtil.doSendKeys(search, searchKey);
			eleUtil.doClick(searchBtn);
			return new SearchResultsPage(driver);
		}
		else {
			System.out.println("search field is not present on the page");
			return null;
		}
	}

}
