package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.errors.AppError;
import com.qa.opencart.utils.CSVUtil;
import com.qa.opencart.utils.ExcelUtil;
import com.qa.opencart.utils.StringUtils;

public class RegistrationPageTest extends BaseTest {
	
	@BeforeClass
	public void regSetup() {
		registrationPage = loginPage.navigateToRegisterPage();
	}
	
	
	@DataProvider
	public Object[][] userRegisterTestData() {
	return new Object [][] {
		{"Khushi","Sharma", "9876543212","khushi@123","yes"},
		{"Rajiv","Kumar", "8876543212","rajiv@123","no"},
		{"Mohan","Chawla", "7776543212","mohan@123","yes"},
		{"Vimla","Sharma","6676543212","vimla@123","yes"}
		
		
	};
	}
	
	@DataProvider
	public Object[][] userRegistrationTestDataFromSheet() {
		return ExcelUtil.getTestData(AppConstants.REGISTER_SHEET_NAME);
	}
	
	@DataProvider
	public Object[][] userRegistrationTestDataFromCSV() {
		return CSVUtil.csvData(AppConstants.REGISTER_SHEET_NAME);
	}
	
	
	
	@Test(dataProvider = "userRegistrationTestDataFromCSV")
	public void userRegistrationTest(String firstName, String lastName, 
			String telephone,String password, String subscribe) {
		Assert.assertTrue
		(registrationPage.userRegister(firstName, lastName, StringUtils.getRandomEmailId(), telephone, password, subscribe), 
				AppError.USER_REG_NOT_DONE);
	}
	
}
