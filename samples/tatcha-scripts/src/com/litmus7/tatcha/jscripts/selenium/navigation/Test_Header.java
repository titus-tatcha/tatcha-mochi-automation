package com.litmus7.tatcha.jscripts.selenium.navigation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import java.util.Properties;
import org.apache.log4j.Logger;
import org.jfree.util.Log;
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

import com.litmus7.tatcha.jscripts.commons.TestMethods;
import com.litmus7.tatcha.utils.BrowserDriver;
import com.xceptance.xlt.api.engine.scripting.AbstractWebDriverScriptTestCase;

/**
 * 
 * Tests Header Menu Links wrt DEV_HeaderElement.properties
 * MOC 33
 * @author titus.kurian
 *
 */

public class Test_Header extends AbstractWebDriverScriptTestCase {

	private final static Logger logger = Logger.getLogger(Test_Header.class);

	// private static WebDriver driver = BrowserDriver.getChromeWebDriver();
	// private static WebDriver driver = new XltDriver();

	private static WebDriver driver;
	private static String baseurl = System.getProperty("base.url");
	private static String ENV = System.getProperty("work.env");
	private boolean acceptNextAlert = true;
	private StringBuffer verificationErrors = new StringBuffer();
	private TestMethods testMethods;
	
	@BeforeClass
	public static void initSetup() {
		if (null != System.getProperty("test.type")) {
			if (System.getProperty("test.type").equals("load.xlt")) {
				logger.info("Load Testing : XLT");
				driver = BrowserDriver.getXLTChromeWebDriver();
			} else if (System.getProperty("test.type").equals("browser.chrome")) {
				logger.info("Browser Automation : Google Chrome");
//				driver = BrowserDriver.getChromeWebDriver();
				driver = BrowserDriver.getXLTChromeWebDriver();
			}
		} else {
			logger.info("System Property : NULL");
			driver = BrowserDriver.getXLTChromeWebDriver();
//			driver = BrowserDriver.getChromeWebDriver();
			if(null == baseurl)
				baseurl = BrowserDriver.DEV_URL;
			System.setProperty("work.env", "DEV");
			System.setProperty("work.module", "HEADER");
		}
	}

	/**
	 * Constructor.
	 */
	public Test_Header() {
		super(driver);
	}


	@Test
	public void testHeader() throws Exception {
		try{
			driver.get(baseurl);
			TestMethods tmethods = testMethods.getInstance();
			Properties prop = new Properties();
			prop=tmethods.getInstance().getEnvPropertyFile();
			tmethods.testNewsLetterPopupModal(driver);
			
			/** Checking Sub-Categories of each Header Category */
			testPromoBanner(driver, prop);
			testLogo(driver, prop);
			int totalNoOfCategories = 5;
			for (int MENUNUM = 1; MENUNUM <= totalNoOfCategories; MENUNUM++) {
				String[] categories = prop.get("header.links." + MENUNUM).toString().split("#");
				callHeaderMenus(driver, categories, MENUNUM);
			}
		}catch(Exception e){
    		verificationErrors.append(e.getMessage());
    		logger.error("EXCEPTIONS: "+verificationErrors);
    	}
	}

	private void testPromoBanner(WebDriver driver2, Properties prop) {
		String[] promoMessageDetails1 = prop.get("header.promo.message1").toString().split("@");

		String[] promoMessageDetails2 = prop.get("header.promo.message2").toString().split("@");

		try {

			String bannerButtonXPATH = "//*[@id=\"ext-gen44\"]/body/div/div[1]/div/button";

			String contentAssetXPATH = "//div[@class='content-asset']";
			String bannerFlag1XPATH = "//div[@class='prenav-marketing']/a[1]";
			String bannerFlag2XPATH = "//div[@class='prenav-marketing']/a[2]";

			// Closing the promo banner
			click(bannerButtonXPATH);

			Actions actions = new Actions(driver);

			WebElement contentAssetXpathELE = driver.findElement(By.className("content-asset"));
			WebElement contentBodyXpathELE = driver.findElement(By.className("content-asset-body"));
			actions.moveToElement(contentAssetXpathELE);
			actions.moveToElement(contentBodyXpathELE);
			actions.build().perform();

			assertPromoMessage(promoMessageDetails1, bannerFlag1XPATH);
			assertPromoMessage(promoMessageDetails2, bannerFlag2XPATH);

		} catch (NoSuchElementException ne) {
			logger.error("BANNER : ELEMENT NOT FOUND " + ne.toString());
		} catch (ElementNotVisibleException nv) {
			logger.error("BANNER : ELEMENT NOT VISIBLE " + nv.toString());
		} catch (TimeoutException te) {
			logger.error("BANNER : TIMEOUT " + te.toString());
		} catch (StaleElementReferenceException elementHasDisappeared) {
			logger.error("BANNER : STALE ELE REF " + elementHasDisappeared.toString());
		} catch (WebDriverException we) {
			logger.error("BANNER : WEBDRIVER ISSUE " + we.toString());
		}
	}

	private void assertPromoMessage(String[] promoMessageDetails, String xPath) {
		try {
			WebElement bannerMessage = driver.findElement(By.xpath(xPath));
			logger.info("NAME : " + promoMessageDetails[0] + "\nURL : " + promoMessageDetails[1] + "\nTITLE : "
					+ promoMessageDetails[2]);
			// Test Promo message
			assertEquals((null != promoMessageDetails[0]) ? promoMessageDetails[0].trim() : "",
					bannerMessage.getText());
			// Test Url
			assertEquals((null != promoMessageDetails[1]) ? promoMessageDetails[1].trim() : "",
					bannerMessage.getAttribute("href"));
			bannerMessage.click();
			// Test landing page Title
			assertEquals((null != promoMessageDetails[2]) ? promoMessageDetails[2].trim() : "", driver.getTitle());

			driver.navigate().back();
		} catch (AssertionError ae) {
			Log.error("ASSERTION ERROR " + ae.toString());
		}

	}

	private void testLogo(WebDriver driver, Properties properties) {
		String[] logoDetails = properties.get("header.tatcha.logo").toString().split("@");

		driver.navigate().to(baseurl);
		// *[@id="ext-gen44"]/body/header/nav/div[1]/div[1]/a/img

		assertLogo(logoDetails, "//*[@id=\"ext-gen44\"]/body/header/nav/div[1]/div[1]/a/img");

		try {

		} catch (NoSuchElementException ne) {
			logger.error("LOGO : ELEMENT NOT FOUND " + ne.toString());
		} catch (ElementNotVisibleException nv) {
			logger.error("LOGO : ELEMENT NOT VISIBLE " + nv.toString());
		} catch (TimeoutException te) {
			logger.error("LOGO : TIMEOUT " + te.toString());
		} catch (StaleElementReferenceException elementHasDisappeared) {
			logger.error("LOGO : STALE ELE REF " + elementHasDisappeared.toString());
		} catch (WebDriverException we) {
			logger.error("LOGO : WEBDRIVER ISSUE " + we.toString());
		}

	}

	private void assertLogo(String[] logoDetails, String string) {
		String logoClass = (null != logoDetails[0]) ? logoDetails[0].trim() : "";
		WebElement webElement = driver
				.findElement(By.xpath("//*[@id=\"ext-gen44\"]/body/header/nav/div[1]/div[1]/a/img"));
		logger.info("CLASS : " + logoClass + "\nURL : " + logoDetails[1] + "\nTITLE : " + logoDetails[2]);
		if (logoClass.equals(webElement.getAttribute("class"))) {
			// String anchorURL = webElement.getAttribute("href");
			String anchorURL = driver.findElement(By.xpath("//*[@id=\"ext-gen44\"]/body/header/nav/div[1]/div[1]/a"))
					.getAttribute("href");
			assertEquals((null != logoDetails[1]) ? logoDetails[1].trim() : "", anchorURL);
			webElement.click();
		}
		assertEquals((null != logoDetails[2]) ? logoDetails[2].trim() : "", driver.getTitle());
	}


	private void callHeaderMenus(WebDriver driver2, String[] categories, int MENUNUM) {

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
				tmethods.assertionChecker(ELEMENT_NAME, webEle.getText());
				tmethods.assertionChecker(HREF_NAME, webEle.getAttribute("href"));
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

				tmethods.assertionChecker(NEXT_TITLE, driver2.getTitle());
				driver2.navigate().back();
			}
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
