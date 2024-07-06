package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;
import com.qa.opencart.utils.TimeUtil;

public class RegistrationPage {
	
	private WebDriver driver;
	ElementUtil eleUtil;

	public RegistrationPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}
	//first Name
	private By firstName = By.id("input-firstname");
	//Last Name
	private By lastName = By.name("lastname");
	//Email
	private By email = By.id("input-email");
	//telephone
	private By telephone = By.name("telephone");
	//Password
	private By password = By.id("input-password");
	//Confirm Password
	private By confirmpassword = By.name("confirm");
	//Privacy Policy
	private By agreeCheckBox = By.name("agree");
	//Continue button
	private By continueButton = By.xpath("//input[@value='Continue']");
	private By subscribeYes = By.xpath("(//label[@class='radio-inline'])[position()=1]/input[@type='radio']");
	private By subscribeNo = By.xpath("(//label[@class='radio-inline'])[position()=2]/input[@type='radio']");
	//Account created Header
	private By SuccessMsg = By.tagName("h1");
	private By logOutLink = By.linkText("Logout");
	private By registerLink = By.linkText("Register");
	

	
	public boolean userRegister(String firstName,String lastName, String email, String telephone,
			String password, String subscribe) {
		eleUtil.doSendKeys(this.firstName, firstName,TimeUtil.DEFAULT_MEDIUM_TIME);
		eleUtil.doSendKeys(this.lastName,lastName);
		eleUtil.doSendKeys(this.email, email);
		eleUtil.doSendKeys(this.telephone, telephone);
		eleUtil.doSendKeys(this.password, password);
		eleUtil.doSendKeys(this.confirmpassword, password);
		if(subscribe.equalsIgnoreCase("yes")) {
			eleUtil.doClick(this.subscribeYes);
		}
		else {
			eleUtil.doClick(this.subscribeNo);
		}
		eleUtil.doClick(this.agreeCheckBox);
		eleUtil.doClick(this.continueButton);
//		String expectedMsg = "Your Account Has Been Created!";
		String successMsg = eleUtil.waitForElementVisible(SuccessMsg,TimeUtil.DEFAULT_MEDIUM_TIME).getText();
		System.out.println(successMsg);
		if(successMsg.contains(AppConstants.USER_REGISTER_SUCCESS_MESSAGE)) {
			eleUtil.doClick(this.logOutLink);
			eleUtil.doClick(registerLink);
			return true;
		}else {
			return false;
		}
		
	}

}
