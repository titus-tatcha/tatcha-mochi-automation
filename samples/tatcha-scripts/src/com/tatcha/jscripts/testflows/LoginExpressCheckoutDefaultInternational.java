package com.tatcha.jscripts.testflows;

import static org.junit.Assert.fail;

import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.tatcha.jscripts.bag.TestAddToCart;
import com.tatcha.jscripts.commons.*;
import com.tatcha.jscripts.dao.TestCase;
import com.tatcha.jscripts.dao.User;
import com.tatcha.jscripts.exception.TatchaException;
import com.tatcha.jscripts.helper.TatchaTestHelper;
import com.tatcha.jscripts.login.TestLogin;
import com.tatcha.jscripts.review.ReviewOrder;
import com.tatcha.utils.BrowserDriver;

/**
 * Flow-2 : Add to cart - Login in Checkout page - Order Review(With International
 * address) - Place order
 * 
 * @author reshma
 *
 */
public class LoginExpressCheckoutDefaultInternational {

	private WebDriver driver = BrowserDriver.getChromeWebDriver();
	private boolean acceptNextAlert = true;
	private StringBuffer verificationErrors = new StringBuffer();
	private Properties prop = new Properties();
	private Properties locator = new Properties();
	private Properties data = new Properties();

	private TatchaTestHelper testHelper = new TatchaTestHelper();
	private final static Logger logger = Logger.getLogger(LoginExpressCheckoutDefaultInternational.class);

	private static TestMethods tmethods;
	private TestCase testCase;
	private List<TestCase> tcList = new ArrayList<TestCase>();
	private final String MODULE = "Flow-2 : LoginExpressCheckoutDefaultInternational";

	@Before
	public void setUp() throws Exception {
		prop.load(new FileInputStream(getClass().getResource("/tatcha.properties").getFile()));
		locator.load(new FileInputStream(getClass().getResource("/checkoutElementLocator.properties").getFile()));
		data.load(new FileInputStream(
				getClass().getResource("/LoginExpressCheckoutDefaultInternational.properties").getFile()));

		boolean testInLocal = Boolean.parseBoolean(prop.getProperty("testInLocal").toString());
		if (testInLocal) {
			String url = data.getProperty("url").toString();
			driver.get(url);
		} else {
			tmethods = TestMethods.getInstance();
			String baseUrl = tmethods.getBaseURL();
			driver.get(baseUrl);
		}
	}

	/**
	 * Test the checkout flow of logged in user. Pre-requisite : The user should
	 * have default shipping and payment details, and also the default address
	 * should be International address
	 * 
	 * @throws Exception
	 */
	@Test
	public void testExpressCheckoutLoginInternational() throws TatchaException {

		String FUNCTIONALITY = "Express checkout with default international address and credit card";
		testCase = new TestCase("Flow-2", "MOC-NIL", FUNCTIONALITY, "FAIL", "");

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy_HH:mm:ss");
		String timeStamp = sdf.format(Calendar.getInstance().getTime());

		logger.info("VERIFYING EXPRESS CHECKOUT FLOW FOR LOGGED IN USER WITH INTERNATIONAL ADDRESS : " + getClass()
				+ timeStamp);
		ReviewOrder reviewOrder = new ReviewOrder();
		User user = new User();
		Map<String, Boolean> map = new HashMap<String, Boolean>();

		TestAddToCart addToCart = new TestAddToCart();
		TestLogin testLogin = new TestLogin();

		map.put("isLogged", true);
		map.put("isUSAddress", false);
		map.put("isGiftCard", false);
		map.put("isCreditCard", true);
		map.put("isRegister", false);

		WebDriverWait wait = (WebDriverWait) new WebDriverWait(driver, 10);

		try {
			addToCart.addSpecificProductToCart(driver, prop, locator, user, tcList);

			wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h2.panel-title")));
			wait.until(ExpectedConditions
					.elementToBeClickable(By.xpath("//*[@id='cart-table']/div[2]/div/div[2]/button")));

			// Click checkout button in shopping bag
			Actions actions = new Actions(driver);
			WebElement checkoutButtonElement = driver
					.findElement(By.xpath("//*[@id='cart-table']/div[2]/div/div[2]/button"));
			actions.moveToElement(checkoutButtonElement).click(checkoutButtonElement);
			actions.perform();

			// Login as a registered user at the checkout
			testLogin.checkoutLogin(driver, data, user, tcList);

			// Verify Review Order for express checkout
			reviewOrder.verifyReviewOrder1(driver, prop, locator, user, map, tcList);

			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='main']/main/div[1]/div/h1")));
			getTestHelper().logAssertion(getClass().getSimpleName(), "THANK YOU FOR YOUR ORDER",
					driver.findElement(By.xpath("//*[@id='main']/main/div[1]/div/h1")).getText());
			timeStamp = sdf.format(Calendar.getInstance().getTime());
			logger.info("ORDER ID : " + driver
					.findElement(By.xpath(locator.getProperty("confirmOrder.orderId.label").toString())).getText()
					+ " TIMESTAMP : " + timeStamp);

			testCase.setStatus("PASS");
			tcList.add(testCase);

		} catch (Exception exp) {
			try {
				throw new TatchaException(exp, tcList);
			} catch (Exception e) {
				logger.error("Handling Tatcha Exception " + e.toString());
			}
		}
		// Report Generation for Flow-8
		if (ReportGenerator.getInstance().generateReport(MODULE, tcList))
			logger.info("Report Generation Succeeded for: " + MODULE);
		else
			logger.info("Report Generation Failed for: " + MODULE);

		logger.info("END testExpressCheckoutLoginInternational");

	}

	/**
	 * @return the testHelper
	 */
	public TatchaTestHelper getTestHelper() {
		return testHelper;
	}

	/**
	 * @param testHelper
	 *            the testHelper to set
	 */
	public void setTestHelper(TatchaTestHelper testHelper) {
		this.testHelper = testHelper;
	}

	@After
	public void tearDown() throws Exception {
		driver.quit();
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			fail(verificationErrorString);
		}
	}

	private boolean isElementPresent(By by) {
		try {
			driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	private boolean isAlertPresent() {
		try {
			driver.switchTo().alert();
			return true;
		} catch (NoAlertPresentException e) {
			return false;
		}
	}

	private String closeAlertAndGetItsText() {
		try {
			Alert alert = driver.switchTo().alert();
			String alertText = alert.getText();
			if (acceptNextAlert) {
				alert.accept();
			} else {
				alert.dismiss();
			}
			return alertText;
		} finally {
			acceptNextAlert = true;
		}
	}
}
