package com.qa.opencart.pages;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;
import com.qa.opencart.utils.TimeUtil;

import io.qameta.allure.Step;

public class LoginPage {
	
	private WebDriver driver;
	private ElementUtil eleUtil;
	//1. Page objects: By locators
	//
	
	private By emailID = By.id("input-email");
	private By password = By.id("input-password");
	private By login = By.xpath("//input[@value='Login']");
	private By forgotPwdLink = By.linkText("Forgotten Password");
	private By registerLink = By.linkText("Register");
	//2. Public constructor of the page
	
	public LoginPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}
	
	//3. public page actions/methods/features
	//No Assertion, all methods should return something
	@Step("getting login page title")
	public String getLoginPageTitle() {
		String title = eleUtil.waitForTitleToBe(AppConstants.LOGIN_PAGE_TITLE,TimeUtil.DEFAULT_TIME);
		System.out.println("Login Page Title is : "+title);
		return title;
	}
	
	@Step("getting login page URL")
	public String getLoginPageURL() {
		
		String url = eleUtil.waitForURLContains(AppConstants.LOGIN_PAGE_FRACTION_URL, TimeUtil.DEFAULT_TIME);
		System.out.println("Login Page URL is : " +url);
		return url;
	}
	
	@Step("getting the state of forgot pwd link exist...")
	public boolean checkForgotPwdLinkExist() {
		return eleUtil.doIsDisplayed(forgotPwdLink);	
	}
	
	@Step("Login to application with username: {0} and password: {1}")
	public AccountsPage doLogin(String userName, String pwd) {
		eleUtil.doSendKeys(emailID, userName, TimeUtil.DEFAULT_MEDIUM_TIME);
		eleUtil.doSendKeys(password, pwd);
		eleUtil.doClick(login);
		return new AccountsPage(driver);
		
		
//		String title = driver.getTitle();
//		System.out.println("Account Page Title : "+title);
//		return title;
	}
	
	@Step("navigating to register page...")
	public RegistrationPage navigateToRegisterPage() {
		eleUtil.doClick(registerLink, TimeUtil.DEFAULT_TIME);
		return new RegistrationPage(driver);
	}
	
	
	
	

}
