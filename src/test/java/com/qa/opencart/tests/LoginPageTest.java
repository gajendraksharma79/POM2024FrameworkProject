package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.errors.AppError;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Issue;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

@Epic("Epic 100: Design Open Cart Application with Shopping Workflow")
@Story("User Story 101: Design Login Page for Open Cart Application")

//@Listeners({ExtentReportListener.class,TestAllureListener.class,AnnotationTransformer.class }) ---only report will work
// retry is not working here...it will working from testng.xml
public class LoginPageTest extends BaseTest{
	
	
	
	@Description("Checking the Title of Login Page TESTGJ----")
	@Severity(SeverityLevel.MINOR)
	@Owner("Gajendra")
	@Feature("Login Title feature")
	@Issue("UGMVPDEV-1131-GJ")
	@Test(priority = 1)
	public void loginPageTitleTest() {
		String actualTitle = loginPage.getLoginPageTitle();
		Assert.assertEquals(actualTitle, AppConstants.LOGIN_PAGE_TITLE, AppError.TITLE_NOT_FOUND);
				
	}
	
	@Description("Checking the URL of Login Page----")
	@Severity(SeverityLevel.NORMAL)
	@Owner("Gajendra")
	@Feature("Login URL feature")
	@Test(priority = 2)
	public void loginPageURLTest() {
		String actUrl = loginPage.getLoginPageURL();
		Assert.assertEquals(true, actUrl.contains(AppConstants.LOGIN_PAGE_FRACTION_URL), AppError.URL_NOT_FOUND);
	}
	
	@Description("Checking the Existence of Forgot Password Link----")
	@Severity(SeverityLevel.CRITICAL)
	@Owner("Gajendra")
	@Test(priority = 3)
	public void forgotPwdLinkExistTest() {
		Assert.assertTrue(loginPage.checkForgotPwdLinkExist(), AppError.ELEMENT_NOT_FOUND);
	}
	
	@Description("Checking the Login Functionality----")
	@Severity(SeverityLevel.BLOCKER)
	@Owner("Gajendra")
	@Test(priority = 4)
	public void loginTest() {
		//accPage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
		accPage = loginPage.doLogin(prop.getProperty("username"), System.getProperty("password"));
		Assert.assertEquals(accPage.getAccountPageTitle(),AppConstants.ACCOUNT_PAGE_TITLE);
	}
	
	

}
