package com.tatcha.jscripts.navigation;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.tatcha.jscripts.commons.ReportGenerator;
import com.tatcha.jscripts.commons.TestMethods;
import com.tatcha.jscripts.dao.TestCase;
import com.xceptance.xlt.api.engine.scripting.AbstractWebDriverScriptTestCase;

public class TestFooter extends AbstractWebDriverScriptTestCase {

	private final static Logger logger = Logger.getLogger(TestFooter.class);

	private static WebDriver driver;
	private String baseUrl;
	private boolean acceptNextAlert = true;

	private StringBuffer verificationErrors = new StringBuffer();

	private static TestMethods tmethods;
	private TestCase testCase;
	private List<TestCase> tcList;
	private final String MODULE = "Global Footer Navigation";
	private int TESTNO = 1;

	@BeforeClass
	public static void initClass() {
		tmethods = TestMethods.getInstance();
		driver = tmethods.getWebDriver();
	}

	public TestFooter() {
		super(driver);
	}

	@Test
	public void testFooter() throws Exception {
		logger.info("inside TestFooter ");
		tcList = new ArrayList<TestCase>();
		baseUrl = tmethods.getBaseURL();
		driver.get(baseUrl);
		driver.manage().window().maximize();

		/** Closing News Letter Popup */
		tmethods.testNewsLetterPopupModal(driver);

		Properties prop = new Properties();
		prop = tmethods.getInstance().getEnvPropertyFile();

		/** Checking 5 Footer Links */
		int totalNoOfCategories = 4;
		for (int MENUNUM = 1; MENUNUM <= totalNoOfCategories; MENUNUM++) {
			String[] footerLinks = prop.get("footer.links." + MENUNUM).toString().split("#");
			callFooterMenus(driver, footerLinks, MENUNUM);
		}

//		testSocialMedia(driver, prop);
		// social media next page titles needs to be checked
//		testPrivacyAndTerms(driver, prop);
		// Footer account Info and Order Status is automated in MyLogin.java

		// Report Generation for PDP Module
		ReportGenerator.getInstance().generateReport(MODULE, tcList);
	}

	private void testPrivacyAndTerms(WebDriver driver, Properties prop) {

		logger.info("inside assertPrivacyAndTerms ");

		String FUNCTIONALITY = "Privacy Policy";
		testCase = new TestCase();
		testCase.setTestNo("TC-" + TESTNO++);
		testCase.setFunctionality(FUNCTIONALITY);
		testCase.setMocNo("MOC-39");

		String STATUS = "FAIL";
		String REMARKS = "";
		StringBuilder REMARKS_STRING = new StringBuilder();

		boolean ppFLAG = false;
		boolean ppFLAG2 = false;

		String[] privacyPolicy = prop.get("footer.links.privacyPolicy").toString().split("@");

		String XPATH_PrivacyPolicy = "//body/footer/div/div[4]/div/p/small/a[1]";
		String XPATH_TermsUse = "//body/footer/div/div[4]/div/p/small/a[2]";

		WebElement privacypolicyEle = tmethods.getWE(driver, XPATH_PrivacyPolicy);
		WebElement termsUseEle = tmethods.getWE(driver, XPATH_TermsUse);

		String ppLabel = privacyPolicy[0];
		String ppUrl = privacyPolicy[1];

		pause(1000);

		if (null != privacypolicyEle && null != privacypolicyEle.getText())
			if (tmethods.assertionChecker(ppLabel, privacypolicyEle.getText(), "EQUALS")) {
				ppFLAG = true;
			} else {
				REMARKS = "Privacy Policy Label Mismatch.";
				REMARKS_STRING.append(REMARKS);
			}

		if (tmethods.assertionChecker(ppUrl, privacypolicyEle.getAttribute("href"), "EQUALS")) {
			ppFLAG2 = true;
		} else {
			REMARKS_STRING.append("<BR/>");
			REMARKS = "Privacy Policy Href Mismatch.";
			REMARKS_STRING.append(REMARKS);
		}

		if (ppFLAG && ppFLAG2)
			STATUS = "PASS";
		else
			STATUS = "FAIL";

		testCase.setStatus(STATUS);
		testCase.setRemarks(REMARKS_STRING.toString());
		tcList.add(testCase);

		// assertEquals(privacyPolicy[0], privacyPolicyElement.getText());
		// assertEquals(privacyPolicy[1],
		// privacyPolicyElement.getAttribute("href"));

		FUNCTIONALITY = "Terms Of Use";
		STATUS = "FAIL";
		testCase = new TestCase();
		testCase.setTestNo("TC-" + TESTNO++);
		testCase.setFunctionality(FUNCTIONALITY);
		testCase.setMocNo("MOC-39");

		STATUS = "FAIL";
		REMARKS = "";
		REMARKS_STRING = new StringBuilder();

		boolean termsFLAG = false;
		boolean termsFLAG2 = false;

		String[] termsOfUse = prop.get("footer.links.termsOfUse").toString().split("@");

		String termsLabel = termsOfUse[0];
		String termsUrl = termsOfUse[1];

		pause(1000);

		if (null != termsUseEle && null != termsUseEle.getText())
			if (tmethods.assertionChecker(termsLabel, termsUseEle.getText(), "EQUALS")) {
				termsFLAG = true;
			} else {
				REMARKS = "Tems Of Use Label Mismatch.";
				REMARKS_STRING.append(REMARKS);
			}

		if (tmethods.assertionChecker(termsUrl, termsUseEle.getAttribute("href"), "EQUALS")) {
			termsFLAG2 = true;
		} else {
			REMARKS_STRING.append("<BR/>");
			REMARKS = "Terms Of Use Href Mismatch.";
			REMARKS_STRING.append(REMARKS);
		}

		// WebElement termsOfUseElement = wait
		// .until(ExpectedConditions.visibilityOfElementLocated(By.linkText(termsOfUse[0])));
		// assertEquals(termsOfUse[0], termsOfUseElement.getText());
		// assertEquals(termsOfUse[1], termsOfUseElement.getAttribute("href"));

		if (termsFLAG && termsFLAG2)
			STATUS = "PASS";
		else
			STATUS = "FAIL";

		testCase.setStatus(STATUS);
		testCase.setRemarks(REMARKS_STRING.toString());
		tcList.add(testCase);
	}

	private void testSocialMedia(WebDriver driver, Properties prop) {
		logger.info("inside assertSocialMedia ");

		String FUNCTIONALITY = "Follow Us & Social Media Links";
		testCase = new TestCase();
		testCase.setTestNo("TC-" + TESTNO++);
		testCase.setFunctionality(FUNCTIONALITY);
		testCase.setMocNo("MOC-39");
		String STATUS = "FAIL";
		String REMARKS = "";
		StringBuilder REMARKS_STRING = new StringBuilder();
		boolean[] FLAGS = new boolean[5];

		String[] socialMedia = prop.get("footer.links.followUs").toString().split("#");
		String xpathFollowDiv = "//body/footer/div/div[3]/div[5]/div/div";

		// Checking Follow Us heading and 4 Social Media Links as of now
		if (null != socialMedia && socialMedia.length == 5) {

			StringBuilder xpathFollow = new StringBuilder();
			xpathFollow.append(xpathFollowDiv);
			xpathFollow.append("/h5");

			WebElement followHeadEle = tmethods.getWE(driver, xpathFollow.toString());
			if (null != followHeadEle && null != followHeadEle.getText()) {
				if (tmethods.assertionChecker(socialMedia[0], followHeadEle.getText(), "EQUALS")) {
					logger.info("follow us header success");
					FLAGS[0] = true;
				} else {
					logger.info("follow us header error");
					REMARKS = "Follow Us label missing";
					REMARKS_STRING.append(REMARKS);
				}
			} else {
				logger.info("follow us header empty");
			}

			for (int index = 1; index < 5; index++) {
				xpathFollow.setLength(0);
				xpathFollow.append(xpathFollowDiv);

				String XPATH = "/ul/li[" + index + "]/a";
				xpathFollow.append(XPATH);
				WebElement websiteEle = tmethods.getWE(driver, xpathFollow.toString());
				if (null != websiteEle) {

					String[] siteAddres = null;
					String websiteName = null;
					String websiteLink = null;

					if (socialMedia[index].contains("@")) {
						siteAddres = socialMedia[index].split("@");
						websiteName = siteAddres[0];
						websiteLink = siteAddres[1];
					}

					/*
					 * if (null != websiteName && null != websiteEle.getText()
					 * && tmethods.assertionChecker(websiteName,
					 * websiteEle.getText(), "EQUALS")) { logger.info(index +
					 * "website label success"); } else { logger.info(index +
					 * "website label error"); }
					 */

					if (null != websiteLink && null != websiteEle.getAttribute("href")
							&& tmethods.assertionChecker(websiteLink, websiteEle.getAttribute("href"), "EQUALS")) {
						logger.info(index + "website url success");
						FLAGS[index] = true;
					} else {
						logger.info(index + "website url error");
						REMARKS_STRING.append("<BR/>");
						if (index == 1) {
							REMARKS = "Facebook URL is missing";
						} else if (index == 2) {
							REMARKS = "Instagram URL is missing";
						} else if (index == 3) {
							REMARKS = "Twitter URL is missing";
						} else {
							REMARKS = "Snapchat URL is missing";
						}
						REMARKS_STRING.append(REMARKS);
					}

				} else {
					logger.info(index + ": website header empty");
					REMARKS_STRING.append("<BR/>");
					if (index == 1) {
						REMARKS = "Facebook is missing";
					} else if (index == 2) {
						REMARKS = "Instagram is missing";
					} else if (index == 3) {
						REMARKS = "Twitter is missing";
					} else {
						REMARKS = "Snapchat is missing";
					}
					REMARKS_STRING.append(REMARKS);
				}
			}

			if (FLAGS[0] && FLAGS[1] && FLAGS[2] && FLAGS[3] & FLAGS[4])
				STATUS = "PASS";
			else
				STATUS = "FAIL";

			testCase.setStatus(STATUS);
			testCase.setRemarks(REMARKS_STRING.toString());
			tcList.add(testCase);
		}

	}

	private void callFooterMenus(WebDriver driver2, String[] categories, int MENUNUM) {
		logger.info("inside callFooterMenus ");
		// String SHOP_TAB_ID = "";
		int LINECOUNT = 0;
		int FOOTER_TABLINK_NO = 1;

		if ((MENUNUM == 1) || (MENUNUM == 2)) {
			LINECOUNT = 5;
		} else if (MENUNUM == 4) {
			LINECOUNT = 3;
		} else {
			LINECOUNT = 1;
		}

		for (int LISTITEM = 0; LISTITEM < LINECOUNT; LISTITEM++) {

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

			if (null != subcategories) {
				if (subcategories.length > 1) {
					HREF_NAME = subcategories[1];
					FLAG2 = true;
				}

				if (subcategories.length > 2) {
					NEXT_TITLE = subcategories[2];
					FLAG3 = true;
				}

			}

			logger.info("PROP ELEMENT_NAME :" + ELEMENT_NAME);
			logger.info("PROP HREF_NAME :" + HREF_NAME);
			logger.info("PROP NEXT_TITLE :" + NEXT_TITLE);

			if ((MENUNUM == 3) || ELEMENT_NAME.equalsIgnoreCase("PURITY PROMISE")
					|| ELEMENT_NAME.equalsIgnoreCase("SERVICE") || ELEMENT_NAME.equalsIgnoreCase("CONSULTATION")
					|| ELEMENT_NAME.equalsIgnoreCase("COMPANY")) {
				NOT_A_LINK = true;
			}

			if (!NOT_A_LINK) {
				if (MENUNUM == 1) {
					XPATH_EXPR = "//*[@id='collapsePromise']/li[" + (FOOTER_TABLINK_NO++) + "]/a";
				} else if (MENUNUM == 2) {
					XPATH_EXPR = "//*[@id='collapseService']/li[" + (FOOTER_TABLINK_NO++) + "]/a";
				} else {
					XPATH_EXPR = "//*[@id='collapseCompany']/li[" + (FOOTER_TABLINK_NO++) + "]/a";
				}

				testCase = new TestCase();
				testCase.setTestNo("TC-" + TESTNO++);
				testCase.setMocNo("MOC-39");
				testCase.setFunctionality(ELEMENT_NAME);

				try {

					WebElement webEle = tmethods.getWE(driver2, XPATH_EXPR);
					if (null != webEle) {
						if (tmethods.assertionChecker(ELEMENT_NAME, webEle.getText(), "EQUALS")) {
							FLAG1 = true;
						}
						if (tmethods.assertionChecker(HREF_NAME, webEle.getAttribute("href"), "EQUALS")) {
							FLAG2 = true;
						}
						try {
							Actions actions = new Actions(driver2);
							WebElement menuEle = driver2.findElement(By.xpath(XPATH_EXPR));
							actions.moveToElement(menuEle).perform();
							webEle.click();
						} catch (ElementNotVisibleException ee) {
							logger.error("ELEMENT NOT VISIBLE: " + ELEMENT_NAME + ee.toString());

						} catch (NoSuchElementException ne) {
							logger.error("NO SUCH ELEMENT: " + ELEMENT_NAME + ne.toString());
						}

						if (tmethods.assertionChecker(NEXT_TITLE, driver2.getTitle(), "EQUALS")) {
							FLAG3 = true;
						}
						driver2.navigate().back();
					}

				} catch (NoSuchElementException ne) {
					REMARKS_STRING.append("<BR/>");
					REMARKS = ELEMENT_NAME + " not found. ";
					REMARKS_STRING.append(REMARKS);
					logger.error(REMARKS + ne.toString());
				} catch (ElementNotVisibleException ne) {
					REMARKS_STRING.append("<BR/>");
					REMARKS = ELEMENT_NAME + " not visible. ";
					REMARKS_STRING.append(REMARKS);
					logger.error(REMARKS + ne.toString());
				} catch (TimeoutException ne) {
					REMARKS_STRING.append("<BR/>");
					REMARKS = ELEMENT_NAME + " is TimeOut. ";
					REMARKS_STRING.append(REMARKS);
					logger.error(REMARKS + ne.toString());
				} catch (StaleElementReferenceException ne) {
					REMARKS_STRING.append("<BR/>");
					REMARKS = ELEMENT_NAME + " is STALE. ";
					REMARKS_STRING.append(REMARKS);
					logger.error(REMARKS + ne.toString());
				} catch (WebDriverException ne) {
					REMARKS_STRING.append("<BR/>");
					REMARKS = ELEMENT_NAME + " has webdriver exception.";
					REMARKS_STRING.append(REMARKS);
					logger.error(REMARKS + ne.toString());
				}

				if (FLAG1 && FLAG2 && FLAG3) {
					STATUS = "PASS";
					REMARKS_STRING.append("URL = " + HREF_NAME);
					REMARKS_STRING.append("<BR/>");
					REMARKS_STRING.append("Next Page Title = " + NEXT_TITLE);
				} else {
					STATUS = "FAIL";
				}

				testCase.setStatus(STATUS);
				testCase.setRemarks(REMARKS_STRING.toString());
				tcList.add(testCase);
			} // EO IF XPATH NOT EMTPY
		} // EO FOR
	}

	@After
	public void tearDown() {
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
