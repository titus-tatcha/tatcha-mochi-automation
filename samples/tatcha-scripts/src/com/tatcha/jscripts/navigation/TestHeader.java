package com.tatcha.jscripts.navigation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.tatcha.jscripts.commons.ReportGenerator;
import com.tatcha.jscripts.commons.TestMethods;
import com.tatcha.jscripts.dao.TestCase;
import com.tatcha.jscripts.exception.TatchaException;
import com.xceptance.xlt.api.engine.scripting.AbstractWebDriverScriptTestCase;

/**
 * 
 * Tests Header Menu Links wrt DEV_HeaderElement.properties MOC 33
 * 
 * @author titus
 *
 */
public class TestHeader extends AbstractWebDriverScriptTestCase {

	private final static Logger logger = Logger.getLogger(TestHeader.class);

	// private static WebDriver driver = BrowserDriver.getChromeWebDriver();
	// private static WebDriver driver = new XltDriver();

	private static WebDriver driver;
	private boolean acceptNextAlert = true;
	private StringBuffer verificationErrors = new StringBuffer();

	private String baseUrl;

	private Properties locator = new Properties();
	private Properties prop = new Properties();

	private static TestMethods tmethods;
	private TestCase testCase;
	private List<TestCase> tcList;
	private final String MODULE = "Global Header Navigation";
	private int TESTNO = 1;

	@BeforeClass
	public static void initClass() {
		tmethods = TestMethods.getInstance();
		driver = tmethods.getWebDriver();
	}

	/**
	 * Constructor.
	 */
	public TestHeader() {
		super(driver);
	}

	/**
	 * Method to test Header Menu Category Links
	 * 
	 * @throws Exception
	 */
	@Test
	public void testHeader() throws Exception {

		tcList = new ArrayList<TestCase>();
		try {
			baseUrl = tmethods.getBaseURL();
			driver.get(baseUrl);
			driver.manage().window().maximize();

			/** Closing News Letter Popup */
			tmethods.testNewsLetterPopupModal(driver);

			Properties prop = new Properties();
			prop = tmethods.getInstance().getEnvPropertyFile();

			testPromoBanner(driver, prop);
			testLogo(driver, prop);
			int totalNoOfCategories = 5;
			for (int MENUNUM = 1; MENUNUM <= totalNoOfCategories; MENUNUM++) {
				String[] categories = prop.get("header.links." + MENUNUM).toString().split("#");
				callHeaderMenus(driver, categories, MENUNUM);
			}
		} catch (Exception exp) {
			try {
				throw new TatchaException(exp, tcList);
			} catch (Exception e) {
				logger.error("Handling Tatcha Exception " + e.toString());
			}
		}
		// Report Generation for PDP Module
		ReportGenerator.getInstance().generateReport(MODULE, tcList);

	}

	/**
	 * Method to test Promo Banner
	 * 
	 * @param driver2
	 * @param prop
	 */
	private void testPromoBanner(WebDriver driver2, Properties prop) {
		logger.info("inside testPromoBanner");

		String FUNCTIONALITY = "Promotional Banner";
		logger.info(FUNCTIONALITY);
		testCase = new TestCase();
		testCase.setTestNo("TC-" + TESTNO++);
		testCase.setFunctionality(FUNCTIONALITY);
		testCase.setMocNo("MOC-33");

		String STATUS = "FAIL";
		String REMARKS = "";
		StringBuilder REMARKS_STRING = new StringBuilder();
		boolean FLAG1 = false;
		boolean FLAG2 = false;
		boolean FLAG3 = false;

		try {

			String[] promoMessageDetails1 = prop.get("header.promo.message1").toString().split("@");
			String[] promoMessageDetails2 = prop.get("header.promo.message2").toString().split("@");

			String bannerButtonXPATH = "//body/div/div[1]/div/button";

			// Closing the promo banner
			try {

				Actions actions = new Actions(driver);
				WebElement contentAssetXpathELE = driver.findElement(By.className("content-asset"));
				WebElement contentBodyXpathELE = driver.findElement(By.className("content-asset-body"));
				if (null != contentAssetXpathELE)
					actions.moveToElement(contentAssetXpathELE);
				if (null != contentBodyXpathELE)
					actions.moveToElement(contentBodyXpathELE);
				actions.build().perform();

				click(bannerButtonXPATH);
				FLAG1 = true;
			} catch (NoSuchElementException ne) {
				REMARKS = "Promotion Banner not Found";
				REMARKS_STRING.append(REMARKS);
				logger.info(REMARKS + ne.toString());
			}

			// assertPromoMessage(promoMessageDetails1, bannerFlag1XPATH);

			String flagName = promoMessageDetails1[0];
			String flagHref = promoMessageDetails1[1];
			String flagTitle = promoMessageDetails1[2];

			logger.info("NAME : " + flagName + "\nURL : " + flagHref + "\nTITLE : " + flagTitle);

			// String contentAssetXPATH = "//div[@class='content-asset']";
			String bannerFlag1XPATH = "//div[@class='prenav-marketing']/a[1]";
			String bannerFlag2XPATH = "//div[@class='prenav-marketing']/a[2]";

			WebElement bannerMessage = tmethods.getWE(driver, bannerFlag1XPATH);
			if (null != bannerMessage) {
				// Test Promo message
				if (null != bannerMessage.getText())
					tmethods.assertionChecker(flagName, bannerMessage.getText());
				// Test Url
				if (null != bannerMessage.getAttribute("href"))
					tmethods.assertionChecker(flagHref, bannerMessage.getAttribute("href"));
				bannerMessage.click();
				// Test clicked page Title
				tmethods.assertionChecker(flagTitle, driver.getTitle());
				driver.navigate().back();
				FLAG2 = true;
			} else {
				REMARKS = "Free Shipping Banner not Found";
				REMARKS_STRING.append(REMARKS);
			}
			// assertPromoMessage(promoMessageDetails2, bannerFlag2XPATH);

			String flagName2 = promoMessageDetails2[0];
			String flagHref2 = promoMessageDetails2[1];
			String flagTitle2 = promoMessageDetails2[2];

			logger.info("NAME : " + flagName2 + "\nURL : " + flagHref2 + "\nTITLE : " + flagTitle2);

			WebElement bannerMessage2 = tmethods.getWE(driver, bannerFlag2XPATH);
			if (null != bannerMessage2) {
				// Test Promo message
				if (null != bannerMessage2.getText())
					tmethods.assertionChecker(flagName, bannerMessage2.getText());
				// Test Url
				if (null != bannerMessage2.getAttribute("href"))
					tmethods.assertionChecker(flagHref2, bannerMessage2.getAttribute("href"));
				bannerMessage2.click();
				// Test clicked page Title
				tmethods.assertionChecker(flagTitle2, driver.getTitle());
				driver.navigate().back();
				FLAG3 = true;
			} else {
				REMARKS = "Complementary Samples not Found";
				REMARKS_STRING.append(REMARKS);
			}

			if (FLAG1 || FLAG2 || FLAG3)
				STATUS = "PASS";
			else
				STATUS = "FAIL";

		} catch (NoSuchElementException ne) {
			logger.error("BANNER : ELEMENT NOT FOUND " + ne.toString());
			STATUS = "FAIL";
		} catch (ElementNotVisibleException nv) {
			logger.error("BANNER : ELEMENT NOT VISIBLE " + nv.toString());
			STATUS = "FAIL";
		} catch (TimeoutException te) {
			logger.error("BANNER : TIMEOUT " + te.toString());
			STATUS = "FAIL";
		} catch (StaleElementReferenceException elementHasDisappeared) {
			logger.error("BANNER : STALE ELE REF " + elementHasDisappeared.toString());
			STATUS = "FAIL";
		} catch (WebDriverException we) {
			logger.error("BANNER : WEBDRIVER ISSUE " + we.toString());
			STATUS = "FAIL";
		}

		testCase.setStatus(STATUS);
		testCase.setRemarks(REMARKS);
		tcList.add(testCase);

	}

	/**
	 * Method to test Tatcha Logo
	 * 
	 * @param driver
	 * @param properties
	 */
	private void testLogo(WebDriver driver, Properties properties) {

		logger.info("inside testLogo");
		String FUNCTIONALITY = "Tatcha Logo";
		logger.info(FUNCTIONALITY);
		testCase = new TestCase();
		testCase.setTestNo("TC-" + TESTNO++);
		testCase.setFunctionality(FUNCTIONALITY);
		testCase.setMocNo("MOC-33");

		String STATUS = "FAIL";
		String REMARKS = "";
		StringBuilder REMARKS_STRING = new StringBuilder();

		boolean FLAG1 = false;
		boolean FLAG2 = false;
		boolean FLAG3 = false;

		String logoClass = null;
		String logoHref = null;
		String logoTitle = null;
		String[] logoDetails = null;
		String logoAlt = "Tatcha Official Site";
		String logoSrc = "tatcha-logo-nav.png";
		try {

			String logoProps = properties.get("header.tatcha.logo").toString();
			if (logoProps.contains("@"))
				logoDetails = logoProps.toString().split("@");
			else
				logoClass = logoProps.toString();

			if (null != logoDetails && logoDetails.length > 1) {
				logoClass = logoDetails[0];
				logoHref = logoDetails[1];
			}

			if (null != logoDetails && logoDetails.length > 2)
				logoTitle = logoDetails[2];

			String selector = properties.get("header.logo.selector").toString();

			WebElement webEle = tmethods.getWEbyCSS(driver, selector);

			if (null != webEle && null != webEle.getAttribute("href")) {
				String hrefName = webEle.getAttribute("href");

				if (tmethods.assertionChecker(logoHref, hrefName, "EQUALS"))
					FLAG1 = true;
				else {
					REMARKS = "Logo Href link mismatch.";
					REMARKS_STRING.append(REMARKS);
				}
			}

			WebElement webEle2 = tmethods.getWE(driver, "//img");
			Actions actions = new Actions(driver);
			actions.moveToElement(webEle);
			actions.moveToElement(webEle2).build().perform();
			if (null != webEle2) {

				if (null != webEle2.getAttribute("alt")) {
					String altName = webEle2.getAttribute("alt");
					tmethods.assertionChecker(logoAlt, altName);
				}

				if (null != webEle2.getAttribute("src")) {
					String srcName = webEle2.getAttribute("src");
					tmethods.assertionChecker(logoSrc, srcName);
				}

				if (null != webEle2.getAttribute("class")) {
					String className = webEle2.getAttribute("class");
					tmethods.assertionChecker(logoClass, className);
				}
				FLAG2 = true;
			} else {
				REMARKS = "Logo Image not Found.";
				REMARKS_STRING.append(REMARKS);
			}

			webEle.click();
			if (tmethods.assertionChecker(logoTitle, driver.getTitle(), "EQUALS"))
				FLAG3 = true;
			else {
				REMARKS = "Logo clicking page title mismatch.";
				REMARKS_STRING.append(REMARKS);
			}
			driver.navigate().back();

			if (FLAG1 && FLAG2 && FLAG3)
				STATUS = "PASS";
			else
				STATUS = "FAIL";
		} catch (NoSuchElementException ne) {
			logger.error("LOGO : ELEMENT NOT FOUND " + ne.toString());
			STATUS = "FAIL";
		} catch (ElementNotVisibleException nv) {
			logger.error("LOGO : ELEMENT NOT VISIBLE " + nv.toString());
			STATUS = "FAIL";
		} catch (TimeoutException te) {
			logger.error("LOGO : TIMEOUT " + te.toString());
			STATUS = "FAIL";
		} catch (StaleElementReferenceException elementHasDisappeared) {
			logger.error("LOGO : STALE ELE REF " + elementHasDisappeared.toString());
			STATUS = "FAIL";
		} catch (WebDriverException we) {
			logger.error("LOGO : WEBDRIVER ISSUE " + we.toString());
			STATUS = "FAIL";
		}

		testCase.setStatus(STATUS);
		testCase.setRemarks(REMARKS);
		tcList.add(testCase);
	}

	private void callHeaderMenus(WebDriver driver2, String[] categories, int MENUNUM) {

		logger.info("inside callHeaderMenus");

		String FUNCTIONALITY = "HEADER LINK - ";

		// String SHOP_TAB_ID = "";
		int LINECOUNT = 0;
		int SHOP_TAB = 0;
		int SHOP_TAB_NO = 1;
		String MENU_HOVER = "";

		if (MENUNUM == 1) {
			LINECOUNT = 24;
		} else if (MENUNUM == 4) {
			LINECOUNT = 4;
		} else if (MENUNUM == 5) {
			LINECOUNT = 5;
		} else {
			LINECOUNT = 1;
		}

		for (int LISTITEM = 0; LISTITEM < LINECOUNT; LISTITEM++) {

			testCase = new TestCase();
			testCase.setTestNo("TC-" + TESTNO++);
			testCase.setMocNo("MOC-33");

			String STATUS = "FAIL";
			String REMARKS = "";
			StringBuilder REMARKS_STRING = new StringBuilder();

			boolean FLAG1 = false;
			boolean FLAG2 = false;
			boolean FLAG3 = false;

			String[] subcategories = null;
			String ELEMENT_NAME = null;
			String HREF_NAME = null;
			String NEXT_TITLE = null;
			String XPATH_EXPR = "";
			boolean NOT_A_LINK = false;

			if (categories[LISTITEM].contains("@")) {
				subcategories = categories[LISTITEM].split("@");
				ELEMENT_NAME = subcategories[0];
			} else {
				ELEMENT_NAME = categories[LISTITEM];
			}

			testCase.setFunctionality(FUNCTIONALITY + ELEMENT_NAME);

			if (null != subcategories) {
				if (subcategories.length > 1)
					HREF_NAME = subcategories[1];
				if (subcategories.length > 2)
					NEXT_TITLE = subcategories[2];
			}

			logger.info("PROP ELEMENT_NAME :" + ELEMENT_NAME);
			logger.info("PROP HREF_NAME :" + HREF_NAME);
			logger.info("PROP NEXT_TITLE :" + NEXT_TITLE);

			MENU_HOVER = "//*[@id='navigation']/div/ul/li[" + MENUNUM + "]/a";

			if (MENUNUM == 1) {

				if (ELEMENT_NAME.equalsIgnoreCase("Shop All")) {
					SHOP_TAB = 1;
				} else if (ELEMENT_NAME.equalsIgnoreCase("Shop by Category")) {
					SHOP_TAB = 2;
					SHOP_TAB_NO = 1;
				} else if (ELEMENT_NAME.equalsIgnoreCase("Shop by Skin Type")) {
					SHOP_TAB = 3;
					SHOP_TAB_NO = 1;
				}

				if ((ELEMENT_NAME.equalsIgnoreCase("SHOP BY CATEGORY"))
						|| (ELEMENT_NAME.equalsIgnoreCase("SHOP BY SKIN TYPE"))) {
					NOT_A_LINK = true;
				}

				if (!NOT_A_LINK) {
					if (SHOP_TAB == 1) {
						XPATH_EXPR = "//*[@id='navigation']/div/ul/li[1]/div/div/div/div[1]/ul/li[" + (SHOP_TAB_NO++)
								+ "]/a";
					} else if (SHOP_TAB == 2) {
						XPATH_EXPR = "//*[@id='navCollapseCategory']/li[" + (SHOP_TAB_NO++) + "]/a";
					} else if (SHOP_TAB == 3) {
						XPATH_EXPR = "//*[@id='navCollapseSkinType']/li[" + (SHOP_TAB_NO++) + "]/a";
					} else {
						XPATH_EXPR = "//*[@id='navigation']/div/ul/li[" + MENUNUM + "]/a";
					}
				}

			} else {
				XPATH_EXPR = "//*[@id='navigation']/div/ul/li[" + MENUNUM + "]/a";
			}

			TestMethods tmethods = TestMethods.getInstance();
			WebElement webEle = tmethods.getWE(driver2, XPATH_EXPR);
			if (null != webEle) {
				if (tmethods.assertionChecker(ELEMENT_NAME, webEle.getText(), "EQUALS"))
					FLAG1 = true;
				else {
					REMARKS = ELEMENT_NAME + " mismatch";
					REMARKS_STRING.append(REMARKS);
				}
				if (tmethods.assertionChecker(HREF_NAME, webEle.getAttribute("href"), "EQUALS"))
					FLAG2 = true;
				else {
					REMARKS = ELEMENT_NAME + " url link mismatch";
					REMARKS_STRING.append(REMARKS);
				}
				try {
					Actions actions = new Actions(driver2);
					WebElement menuEle = driver2.findElement(By.xpath(MENU_HOVER));
					actions.moveToElement(menuEle).perform();
					webEle.click();
				} catch (ElementNotVisibleException ee) {
					logger.error("ELEMENT NOT VISIBLE: " + ELEMENT_NAME + ee.toString());

				} catch (NoSuchElementException ne) {
					logger.error("NO SUCH ELEMENT: " + ELEMENT_NAME + ne.toString());
				}

				if (tmethods.assertionChecker(NEXT_TITLE, driver2.getTitle(), "EQUALS"))
					FLAG3 = true;
				else {
					REMARKS = ELEMENT_NAME + " navigation page mismatch";
					REMARKS_STRING.append(REMARKS);
				}
				driver2.navigate().back();
			}
			if (FLAG1 && FLAG2 && FLAG3)
				STATUS = "PASS";
			else
				STATUS = "FAIL";

			testCase.setStatus(STATUS);
			testCase.setRemarks(REMARKS);
			tcList.add(testCase);
		}
	}

	@After
	public void tearDown() {
		driver.quit();
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			fail(verificationErrorString);
		}
	}

}
