package com.litmus7.tatcha.jscripts.commons;

import static org.junit.Assert.assertEquals;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TestMethods {
	private final static Logger logger = Logger.getLogger(TestMethods.class);

	private static TestMethods instance = null;
	
	public static final TestMethods getInstance(){
		if(null == instance)
			instance = new TestMethods();
		return instance;
	}
	
	private TestMethods(){
		
	}
	
	public void testNewsLetterPopupModal(WebDriver driver2) {
		// driver2.manage().timeouts().implicitlyWait(3,TimeUnit.SECONDS);
		// JavascriptExecutor js = (JavascriptExecutor)driver2;
		// js.executeAsyncScript("window.setTimeout(arguments[arguments.length -
		// 1], 5000);");
		// new WebDriverWait(driver2, pageLoadTimeout).until(webDriver -> ((js)
		// webDriver).executeScript("return
		// document.readyState").equals("complete"));
		// waitForLoad(driver2);
		Properties propNews = new Properties();
		try {
			propNews.load(new FileInputStream(getClass().getResource("/news_letter.properties").getFile()));
		} catch (IOException ie) {
			logger.error("NEWS LETTER PROPERTIES FILE ERROR: "+ie.toString());
		}

		waitForPageLoaded(driver2);
		assertionChecker(propNews.getProperty("newsletter.popup.heading.text").toString(),
				getWE(driver2, propNews, "newsletter.popup.heading").getText());
		WebElement we = getWE(driver2, propNews, "newsletter.popup.close");
		we.click();
	}
	
	public void waitForLoad(WebDriver driver) {
		ExpectedCondition<Boolean> pageLoadCondition = new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
			}
		};
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(pageLoadCondition);
	}

	public void waitForPageLoaded(WebDriver driver2) {
		ExpectedCondition<Boolean> expectation = new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				return ((JavascriptExecutor) driver).executeScript("return document.readyState").toString()
						.equals("complete");
			}
		};
		try {
			Thread.sleep(1000);
			WebDriverWait wait = new WebDriverWait(driver2, 30);
			wait.until(expectation);
		} catch (Throwable error) {
			Assert.fail("Timeout waiting for Page Load Request to complete.");
		}
	}	
	

	public WebElement getWE(WebDriver driver, Properties prop, String propKey) {
		WebElement we = null;
		try {
			we = driver.findElement(By.xpath(prop.getProperty(propKey).toString()));
		} catch (NoSuchElementException ne) {
			logger.error("ELEMENT NOT FOUND VIA PROPERTY KEY: " + ne.toString());
		}
		return we;
	}

	public WebElement getWE(WebDriver driver, String expression) {
		WebElement we = null;
		try {
			we = driver.findElement(By.xpath(expression));
		} catch (NoSuchElementException ne) {
			logger.error("ELEMENT NOT FOUND VIA XPATH: " + ne.toString());
		}
		return we;
	}

	public void assertionChecker(String PARAM1, String PARAM2) {
		try {
			if (null != PARAM1 && null != PARAM2)
				assertEquals(PARAM1, PARAM2);
			else {
				logger.error("ASSERTION FAILS as PARAMS NULL");
			}
		} catch (AssertionError ae) {
			logger.error("ASSERTION-EQUALS ERROR b/w " + PARAM1 + " & " + PARAM2);
		}
	}
	
	public Properties getEnvPropertyFile(){
		Properties prop = new Properties();
		if (null != System.getProperty("work.env")) {
			String ENV = System.getProperty("work.env").toString();
			String MODULE = System.getProperty("work.module").toString();
			if (ENV.equalsIgnoreCase("PRODUCTION")) {

			} else if (ENV.equalsIgnoreCase("PREVIEW")) {

			} else if (ENV.equalsIgnoreCase("STAGING")) {

			} else if (ENV.equalsIgnoreCase("DEMO")) {

			} else if (ENV.equalsIgnoreCase("DEV")) {
				if(MODULE.equalsIgnoreCase("HEADER")){
					try {
						prop.load(new FileInputStream(getClass().getResource("/DEV_Header.properties").getFile()));
					} catch (IOException ie) {
						logger.error("PROPERTY FILE MISSING: DEV_HeaderElement"+ie.toString());
					}
				}
				
				if(MODULE.equalsIgnoreCase("FOOTER")){
					try {
						prop.load(new FileInputStream(getClass().getResource("/DEV_Footer.properties").getFile()));
					} catch (IOException ie) {
						logger.error("PROPERTY FILE MISSING: DEV_HeaderElement"+ie.toString());
					}
				}
			}else{
				logger.error("ENVIRONMENT NOT SPECIFIED");
			}
		}
		return prop;
	}

}
