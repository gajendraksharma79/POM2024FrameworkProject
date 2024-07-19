package com.qa.opencart.factory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.safari.SafariDriver;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.errors.AppError;
import com.qa.opencart.exceptions.BrowserException;
import com.qa.opencart.exceptions.FrameworkException;

public class DriverFactory {

	WebDriver driver;
	Properties prop;
	OptionsManager optionsmanager;
	
	public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<WebDriver>();
	public static String highlight;
	

	/**
	 * This method is used to initialize the driver on the basis of given browser
	 * name
	 * 
	 * @param browserName
	 */
	public WebDriver initDriver(Properties prop) {
		// cross browser logic

		String browserName = prop.getProperty("browser");
		//following code is written to read the browserName from configured variable in Jenkins job
		//String browserName = System.getProperty("browserName");
		String url = prop.getProperty("url");
		optionsmanager = new OptionsManager(prop);
		System.out.println("browser name is :" + browserName);
		highlight = prop.getProperty("highlight");
		switch (browserName.toLowerCase().trim()) {
		case "chrome":
			//driver = new ChromeDriver();
			tlDriver.set(new ChromeDriver(optionsmanager.getChromeOptions()));
			break;

		case "firefox":
			//driver = new FirefoxDriver();
			tlDriver.set(new FirefoxDriver(optionsmanager.getFirefoxOptions()));
			break;
		case "edge":
			//driver = new EdgeDriver();
			tlDriver.set(new EdgeDriver(optionsmanager.getEdgeOptions()));
			break;
		case "safari":
			//driver = new SafariDriver();
			tlDriver.set(new SafariDriver());
			break;

		default:
			System.out.println("please pass the riht browser name :" + browserName);
			throw new BrowserException(AppError.BROWSER_NOT_FOUND);

		}
		
		getDriver().manage().deleteAllCookies();
		getDriver().manage().window().maximize();
		getDriver().get(url);
		return getDriver();
	}

	/**
	 * get the local thread copy of the driver
	 * @return
	 */
	public static WebDriver getDriver() {
		return tlDriver.get();
	}
	
	
	/**
	 * This method is used to initialize the properties from the .properties file
	 * 
	 * @return : this returns properties (prop)
	 */
	public Properties initProp() {
		// mvn clean install -Denv="qa'
		prop = new Properties();
		FileInputStream fis = null;
		String envName = System.getProperty("env");
		System.out.println("running test suite on env ---> " + envName);
		if (envName == null) {
			System.out.println("env name is null, hence running it on QA environment");
			try {
				fis = new FileInputStream(AppConstants.CONFIG_QA_FILE_PATH);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}

		} else {

			try {
				switch (envName.toLowerCase().trim()) {
				case "qa":
					fis = new FileInputStream(AppConstants.CONFIG_QA_FILE_PATH);
					break;

				case "dev":
					fis = new FileInputStream(AppConstants.CONFIG_DEV_FILE_PATH);
					break;

				case "uat":
					fis = new FileInputStream(AppConstants.CONFIG_UAT_FILE_PATH);
					break;

				case "stage":
					fis = new FileInputStream(AppConstants.CONFIG_STAGE_FILE_PATH);
					break;
				case "prod":
					fis = new FileInputStream(AppConstants.CONFIG_FILE_PATH);
					break;
				default:
					System.out.println("pleas pass the right env name..." + envName);
					throw new FrameworkException("====WRONGENVPASSED====");
				}

			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		try {
			prop.load(fis);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return prop;
	}
	
	/**
	 * take screenshot
	 */
			
	public static String getScreenshot(String methodName) {
		File srcFile = ((TakesScreenshot)getDriver()).getScreenshotAs(OutputType.FILE); //temp location
		String path = System.getProperty("user.dir")+"/screenshots/"+methodName+"_"
						+System.currentTimeMillis()+".png";
		File destFile = new File(path);
		try {
			FileHandler.copy(srcFile, destFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return path;
	}
}
