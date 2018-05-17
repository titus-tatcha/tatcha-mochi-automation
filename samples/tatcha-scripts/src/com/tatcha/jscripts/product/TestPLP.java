package com.tatcha.jscripts.product;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.tatcha.jscripts.TatchaConstants;
import com.tatcha.jscripts.commons.ReportGenerator;
import com.tatcha.jscripts.commons.TestMethods;
import com.tatcha.jscripts.dao.Product;
import com.tatcha.jscripts.dao.TestCase;
import com.tatcha.jscripts.exception.TatchaException;
import com.xceptance.xlt.api.engine.scripting.AbstractWebDriverScriptTestCase;

/**
 * Test Product Listing Page as per MODULE set as system property in pom.xml.
 * ie. <work.module>
 * 
 * @author titus
 *
 */
public class TestPLP extends AbstractWebDriverScriptTestCase {
	private final static Logger logger = Logger.getLogger(TestPLP.class);

	private static WebDriver driver;
	private String baseUrl;
	private boolean acceptNextAlert = true;
	private StringBuffer verificationErrors = new StringBuffer();
	private static TestMethods tmethods;

	private TestCase testCase;
	private List<TestCase> tcList;
	private final String MODULE = TatchaConstants.PLP;
	private int TESTNO = 1;

	@BeforeClass
	public static void initClass() {
		tmethods = TestMethods.getInstance();
		driver = tmethods.getWebDriver();
	}

	public TestPLP() {
		super(driver);
	}

	// DDL Skin Type 6
	// DDL Solutions For 8,6,6
	// DDL Size 2
	// DDL Sort By 3

	/**
	 * Executes the test.
	 *
	 * @throws Throwable
	 *             if anything went wrong
	 */
	@Test
	public void testPLP() throws TatchaException {
		logger.info("inside testPLP");
		tcList = new ArrayList<TestCase>();
		try {
			baseUrl = tmethods.getBaseURL();
			driver.get(baseUrl);
			driver.manage().window().maximize();

			/** Closing News Letter Popup */
			tmethods.testNewsLetterPopupModal(driver);

			startAction("Shop All");

			// Shop All Filter DDLs
			/*
			 * startAction("Shop All Filter DDLs:"); try{ String DDL_NAME =
			 * "SKIN TYPE All Skin Types"; String DDL_ID = "filterskinType";
			 * String DDL_XPATH = "//*[@id='"+DDL_ID+
			 * "']/button[contains(@class,'view-option-btn')][1]";
			 * tmethods.assertionChecker(DDL_XPATH, DDL_NAME);
			 * 
			 * int SKIN_TYPE_ITEMS = 6; // 1-6 for(int CB = 1; CB <=
			 * SKIN_TYPE_ITEMS; CB++ ){ String CB_XPATH =
			 * "//div[@id='filterskinType']/ul/li["+CB+"]/a/div/label/input";
			 * click(CB_XPATH); // click(CB_XPATH); }
			 * 
			 * }catch(NoSuchElementException e){ logger.error("NO ELEMENT: "
			 * +e.toString()); }catch (StaleElementReferenceException e) {
			 * logger.error("STALE ELEMENT: "+e.toString()); }
			 * 
			 * try{
			 * 
			 * String DDL_NAME = "SOLUTIONS FOR All Solutions"; // Multiple...
			 * // DDL_NAME = "SOLUTIONS FOR Multiple..."; String DDL_ID =
			 * "filterskinConcerns"; String DDL_XPATH = "//*[@id='"+DDL_ID+
			 * "']/button[contains(@class,'view-option-btn')][1]";
			 * tmethods.assertionChecker(DDL_XPATH, DDL_NAME); //
			 * assertText(DDL_XPATH, DDL_NAME);
			 * 
			 * int SOL_TAB = 3; // 3 : 8,6,6 for(int TAB = 1 ;TAB <= SOL_TAB;
			 * TAB++){ int SOL_ITEM = 6; if(TAB == 1) SOL_ITEM = 8; for(int
			 * INDEX = 1 ;INDEX <= SOL_ITEM; INDEX++){ String CB_XPATH =
			 * "//div[@id='"+DDL_ID+"']/ul/li/ul["+TAB+"]/li["+INDEX+
			 * "]/a/div/label/input"; click(CB_XPATH); // click(CB_XPATH); } }
			 * }catch(NoSuchElementException e){ logger.error("NO ELEMENT: "
			 * +e.toString()); }catch (StaleElementReferenceException e) {
			 * logger.error("STALE ELEMENT: "+e.toString()); }
			 * 
			 * try{ String DDL_NAME = "SIZE All Sizes"; String DDL_ID =
			 * "filterisTravelSize"; String DDL_XPATH = "//*[@id='"+DDL_ID+
			 * "']/button[contains(@class,'view-option-btn')][1]";
			 * tmethods.assertionChecker(DDL_XPATH, DDL_NAME); //
			 * assertText(DDL_XPATH, DDL_NAME);
			 * 
			 * int SIZE_ITEM = 2; for(int INDEX = 1 ;INDEX <= SIZE_ITEM;
			 * INDEX++){ String CB_XPATH =
			 * "//div[@id='"+DDL_ID+"']/ul/li["+INDEX+"]/a/div/label/input";
			 * click(CB_XPATH); // click(CB_XPATH); }
			 * }catch(NoSuchElementException e){ logger.error("NO ELEMENT: "
			 * +e.toString()); }catch (StaleElementReferenceException e) {
			 * logger.error("STALE ELEMENT: " +e.toString()); }
			 * 
			 * try{ String DDL_NAME = "Sort By"; String DDL_ID = "sort"; String
			 * DDL_XPATH = "//*[@id='"+DDL_ID+
			 * "']/button[contains(@class,'view-option-btn')][1]";
			 * //*[@id="sort"]/div[1] tmethods.assertionChecker(DDL_XPATH,
			 * DDL_NAME); // assertText(DDL_XPATH, DDL_NAME);
			 * 
			 * int SORT_ITEM = 3; for(int INDEX = 1 ;INDEX <= SORT_ITEM;
			 * INDEX++){ String CB_XPATH =
			 * "//*[@id='primary']/div[4]/div/div[2]/div/ul/li["+INDEX+"]/a";
			 * click(CB_XPATH); // click(CB_XPATH); } // click("link=Featured");
			 * // click("link=Price (low to high)"); // click(
			 * "link=Product name (A to Z)"); }catch(NoSuchElementException e){
			 * logger.error("NO ELEMENT: "+e.toString()); }catch
			 * (StaleElementReferenceException e) { logger.error(
			 * "STALE ELEMENT: " +e.toString()); }
			 */

			// Iterating Product Items

			String SUBFORM_XPATH = "//*[@id='SubscribeForm']/label";
			WebElement subformEle = tmethods.getWE(driver, SUBFORM_XPATH);

			JavascriptExecutor jse = (JavascriptExecutor) driver;
			String bodyHeight = jse.executeScript("return document.body.scrollHeight").toString();
			int scrollHeight = Integer.parseInt(bodyHeight);
			logger.info("Scroll Height " + bodyHeight);

			// jse.executeScript("window.scrollTo(0,
			// document.body.scrollHeight);");

			for (int height = 0; height < scrollHeight; height += 10) {
				jse.executeScript("window.scrollTo(0, " + height + ");");
				bodyHeight = jse.executeScript("return document.body.scrollHeight").toString();
				scrollHeight = Integer.parseInt(bodyHeight);
			}

			jse.executeScript("window.scrollTo(" + scrollHeight + ", 0);");

			int RINDEX = 1;
			boolean NO_ROW = false;

			List<Product> productlist = new ArrayList<Product>();

			int PROD_ID = 0;

			while (!NO_ROW) {
				try {
					if (RINDEX == 4)
						NO_ROW = true;

					if (RINDEX % 5 != 0) {
						// row product-list-row
						// //*[@id="primary"]/div[5]/div[19]
						for (int CINDEX = 1; CINDEX <= 3; CINDEX++) {
							String PROW_XPATH = "//div[contains(@class,'search-result-content')]/div[" + RINDEX + "]";
							String PCOL_XPATH = PROW_XPATH + "/div[" + CINDEX + "]";

							WebElement prodEle = tmethods.getWE(driver, PCOL_XPATH);
							// WebElement prodEle =
							// driver.findElement(By.xpath(PCOL_XPATH));

							if (null != prodEle && null != prodEle.getText() && !prodEle.getText().trim().isEmpty()
									&& prodEle.getAttribute("class").toString().contains("product-list-col")) {

								String ptext = prodEle.getText();
								// logger.info("\nProduct:" + (++PCOUNT) + "\n"
								// +
								// ptext);

								String PLIST_XPATH = "//div[contains(@class,'product-list-unit')]";
								String PIMG_XPATH = "//div[contains(@class,'product-img-block')]/a";
								String PMFLAG_XPATH = "//div[contains(@class,'product-marketing-flag-block')]";
								String PNAME_XPATH = "//h2[contains(@class,'product-name')]/a";
								// String PLINKS_XPATH =
								// "//div[contains(@class,'product-price-variant-block')]";
								String PPRICE_XPATH = "//span[contains(@class,'product-price')]";
								String PVAR_XPATH = "//span[contains(@class,'product-variants')]";
								String PRATING_XPATH = "//a[contains(@class,'product-view-link')]";
								String PCTA_XPATH = "//div[contains(@class,'product-cta')]/a";

								Product product = new Product();
								PROD_ID++;
								product.setPid(PROD_ID + "");
								product.setProductNo(PROD_ID);

								WebElement wbEle = null;

								/** PRODUCT MARKETING FLAGS */
								wbEle = tmethods.getWE(driver, PCOL_XPATH + PMFLAG_XPATH);
								if (null != wbEle) {
									logger.info("PMFLAG_XPATH: " + wbEle.getText());
									product.setFlag1(wbEle.getText());
									// product.setFlag2(flag1);

								}
								/** PRODUCT NAME */
								wbEle = tmethods.getWE(driver, PCOL_XPATH + PNAME_XPATH);
								if (null != wbEle) {
									logger.info("PNAME_XPATH: " + wbEle.getText());
									product.setName(wbEle.getText());
								}

								// wbEle = tmethods.getWE(driver, PCOL_XPATH +
								// PLINKS_XPATH);
								// if (null != wbEle)
								// logger.info("PLINKS_XPATH: " +
								// wbEle.getText());

								/** PRODUCT PRICE */
								wbEle = tmethods.getWE(driver, PCOL_XPATH + PPRICE_XPATH);
								if (null != wbEle) {
									logger.info("PPRICE_XPATH: " + wbEle.getText());
									product.setPrice(wbEle.getText());
								}

								/** PRODUCT VARIANTS */
								wbEle = tmethods.getWE(driver, PCOL_XPATH + PVAR_XPATH);
								if (null != wbEle) {
									logger.info("PVAR_XPATH: " + wbEle.getText());
									if (wbEle.getText().contains("Sizes")) {
										String sizes = wbEle.getText();
										int sizeNo = Integer
												.parseInt(sizes.substring(0, sizes.indexOf("Sizes")).trim());
										product.setSize(sizeNo);
									}

									// product.setVariant1(variant1);
									// product.setVariant2(variant2);

								}

								/** PRODUCT RATINGS */
								wbEle = tmethods.getWE(driver, PCOL_XPATH + PRATING_XPATH);
								if (null != wbEle) {
									logger.info("PRATING_XPATH: " + wbEle.getText());
									String ratings = wbEle.getText();
									if (ratings.contains("rating")) {
										int ratingNo = Integer
												.parseInt(ratings.substring(0, ratings.indexOf("ratings")).trim());
										product.setSize(ratingNo);
									}

								}

								/** PRODUCT STATUS */
								wbEle = tmethods.getWE(driver, PCOL_XPATH + PCTA_XPATH);
								if (null != wbEle) {
									logger.info("PCTA_XPATH: " + wbEle.getText());
									product.setStatus(wbEle.getText());
								}

								productlist.add(product);

							}

						}
					}
					RINDEX++;

				} catch (NoSuchElementException e) {
					NO_ROW = true;
					RINDEX--;
				}
			} // EOWHILE

			createTestCaseForPLP(productlist);

			if (tmethods.writeToPropertiesFile(productlist))
				logger.info("PROPERTIES FILE CREATED... ");

		} catch (Exception exp) {
			try {
				throw new TatchaException(exp, tcList);
			} catch (Exception e) {
				logger.error("Handling Tatcha Exception " + e.toString());
			}
		}
		// Report Generation for PDP Module
		if (ReportGenerator.getInstance().generateReport(MODULE, tcList))
			logger.info("Report Generation Succeeded for: "+MODULE);
		else
			logger.info("Report Generation Failed for: "+MODULE);

	}

	/**
	 * Method to create test case for each product stored in product list
	 * 
	 * @param productlist
	 */
	private void createTestCaseForPLP(List<Product> productlist) {
		logger.info("BEGIN TestPLP.createTestCaseForPLP");

		for (Product product : productlist) {

			// String FUNCTIONALITY = "Product No:";
			// logger.info(FUNCTIONALITY+" "+TESTNO);
			testCase = new TestCase();
			testCase.setTestNo("TC-" + TESTNO++);
			// testCase.setFunctionality(FUNCTIONALITY);
			testCase.setMocNo("MOC-551");
			String STATUS = "FAIL";
			String REMARKS = "";
			StringBuilder REMARKS_STRING = new StringBuilder();
			boolean FLAG1 = false;
			boolean FLAG2 = false;
			boolean FLAG3 = false;
			boolean FLAG4 = false;
			boolean FLAG5 = false;
			boolean FLAG6 = false;

			if (null != product.getName() && !product.getName().trim().isEmpty()) {
				testCase.setFunctionality(product.getName());
				FLAG1 = true;
			} else {

				REMARKS = "Product name Emtpy";
				REMARKS_STRING.append(REMARKS + "<BR/>");
				testCase.setFunctionality(product.getName());
			}

			if ((null != product.getFlag1() && !(product.getFlag1().trim().isEmpty())
					|| (null != product.getFlag2() && !(product.getFlag2().trim().isEmpty())))) {
				FLAG2 = true;
			} else {
				REMARKS = "Marketing flags Emtpy";
				REMARKS_STRING.append(REMARKS + "<BR/>");
			}

			if ((null != product.getPrice() && !(product.getPrice().trim().isEmpty()))
					|| (null != product.getHighPrice() && !(product.getHighPrice().trim().isEmpty()))
					|| (null != product.getLowPrice() && !(product.getLowPrice().trim().isEmpty()))) {
				FLAG3 = true;
			} else {
				REMARKS = "Product price Emtpy";
				REMARKS_STRING.append(REMARKS + "<BR/>");
			}

			if ((null != product.getVariant1() && !(product.getVariant1().trim().isEmpty())
					|| (null != product.getVariant2() && !(product.getVariant2().trim().isEmpty())))) {
				FLAG4 = true;
			} else {
				REMARKS = "Product variants Emtpy";
				REMARKS_STRING.append(REMARKS + "<BR/>");
			}

			if ((product.getRatings() != 0) || (product.getReviews() != 0)) {
				FLAG5 = true;
			} else {
				REMARKS = "Product ratings Emtpy";
				REMARKS_STRING.append(REMARKS + "<BR/>");

			}

			if (null != product.getStatus() && !(product.getStatus().trim().isEmpty())) {
				FLAG6 = true;
			} else {
				REMARKS = "Product status Emtpy";
				REMARKS_STRING.append(REMARKS + "<BR/>");
			}

			if (FLAG1 || FLAG2 || FLAG3 || FLAG4 || FLAG5 || FLAG6) {
				STATUS = "PASS";
				REMARKS_STRING.append(product.getName() + "<BR/>");
				if (null != product.getFlag1() && !product.getFlag1().isEmpty())
					REMARKS_STRING.append(product.getFlag1() + "<BR/>");
				if (null != product.getFlag2() && !product.getFlag2().isEmpty())
					REMARKS_STRING.append(product.getFlag2() + "<BR/>");
				if (null != product.getPrice() && !product.getPrice().isEmpty())
					REMARKS_STRING.append(product.getPrice() + "<BR/>");
				if (null != product.getHighPrice() && !product.getHighPrice().isEmpty())
					REMARKS_STRING.append(product.getHighPrice() + "<BR/>");
				if (null != product.getLowPrice() && !product.getLowPrice().isEmpty())
					REMARKS_STRING.append(product.getLowPrice() + "<BR/>");
				if (null != product.getVariant1() && !product.getVariant1().isEmpty())
					REMARKS_STRING.append(product.getVariant1() + "<BR/>");
				if (null != product.getVariant2() && !product.getVariant2().isEmpty())
					REMARKS_STRING.append(product.getVariant2() + "<BR/>");
				if (product.getReviews() != 0)
					REMARKS_STRING.append(product.getReviews() + "<BR/>");
				if (product.getRatings() != 0)
					REMARKS_STRING.append(product.getRatings());

			} else
				STATUS = "FAIL";

			testCase.setStatus(STATUS);
			testCase.setRemarks(REMARKS_STRING.toString());
			tcList.add(testCase);
		} // EO FOR

		logger.info("END TestPLP.createTestCaseForPLP");
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