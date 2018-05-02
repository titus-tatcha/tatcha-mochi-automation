package com.litmus7.tatcha.jscripts.selenium.sprint5;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.fasterxml.jackson.databind.deser.Deserializers.Base;
import com.litmus7.tatcha.jscripts.commons.TestMethods;
import com.litmus7.tatcha.jscripts.dob.Product;
import com.litmus7.tatcha.jscripts.selenium.navigation.Test_Footer;
import com.litmus7.tatcha.utils.BrowserDriver;
import com.xceptance.xlt.api.engine.scripting.AbstractWebDriverScriptTestCase;

/**
 * TODO: Add class description
 */
public class PLP extends AbstractWebDriverScriptTestCase {
	private final static Logger logger = Logger.getLogger(Test_Footer.class);

	private static WebDriver driver;
	private String baseUrl;
	private boolean acceptNextAlert = true;
	private StringBuffer verificationErrors = new StringBuffer();
	private static TestMethods tmethods;


	@BeforeClass
	public static void initClass() {
		tmethods = TestMethods.getInstance();

		if (null != System.getProperty("test.type")) {
			if (System.getProperty("test.type").equals("load.xlt")) {
				logger.info("Load Testing : XLT");
				driver = BrowserDriver.getXLTChromeWebDriver();
			} else if (System.getProperty("test.type").equals("browser.chrome")) {
				logger.info("Browser Automation : Google Chrome");
				driver = BrowserDriver.getChromeWebDriver();
			}
		} else {
			driver = BrowserDriver.getChromeWebDriver();
			/** Customly needed for JUnit Exec */
			if (null == System.getProperty("work.env")) {
				System.setProperty("work.env", "DEV_SEC");
			}
			if (null == System.getProperty("work.module")) {
				System.setProperty("work.module", "BESTSELLERS");
			}
		}
	}

	public PLP() {
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
	public void testPLPs() throws Throwable {

		baseUrl = tmethods.getBaseURL();
		
		// tmethods.basicAuth(baseUrl);
		driver.get(baseUrl);
		driver.manage().window().maximize();

		/** Closing News Letter Popup */
		TestMethods.getInstance().testNewsLetterPopupModal(driver);
    	
		logger.info("Current URL " + driver.getCurrentUrl());
		startAction("Shop All");

		// Shop All Filter DDLs
		/*
		 * startAction("Shop All Filter DDLs:"); try{ String DDL_NAME =
		 * "SKIN TYPE All Skin Types"; String DDL_ID = "filterskinType"; String
		 * DDL_XPATH = "//*[@id='"+DDL_ID+
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
		 * String DDL_NAME = "SOLUTIONS FOR All Solutions"; // Multiple... //
		 * DDL_NAME = "SOLUTIONS FOR Multiple..."; String DDL_ID =
		 * "filterskinConcerns"; String DDL_XPATH = "//*[@id='"+DDL_ID+
		 * "']/button[contains(@class,'view-option-btn')][1]";
		 * tmethods.assertionChecker(DDL_XPATH, DDL_NAME); //
		 * assertText(DDL_XPATH, DDL_NAME);
		 * 
		 * int SOL_TAB = 3; // 3 : 8,6,6 for(int TAB = 1 ;TAB <= SOL_TAB;
		 * TAB++){ int SOL_ITEM = 6; if(TAB == 1) SOL_ITEM = 8; for(int INDEX =
		 * 1 ;INDEX <= SOL_ITEM; INDEX++){ String CB_XPATH =
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
		 * int SIZE_ITEM = 2; for(int INDEX = 1 ;INDEX <= SIZE_ITEM; INDEX++){
		 * String CB_XPATH =
		 * "//div[@id='"+DDL_ID+"']/ul/li["+INDEX+"]/a/div/label/input";
		 * click(CB_XPATH); // click(CB_XPATH); } }catch(NoSuchElementException
		 * e){ logger.error("NO ELEMENT: "+e.toString()); }catch
		 * (StaleElementReferenceException e) { logger.error("STALE ELEMENT: "
		 * +e.toString()); }
		 * 
		 * try{ String DDL_NAME = "Sort By"; String DDL_ID = "sort"; String
		 * DDL_XPATH = "//*[@id='"+DDL_ID+
		 * "']/button[contains(@class,'view-option-btn')][1]";
		 * //*[@id="sort"]/div[1] tmethods.assertionChecker(DDL_XPATH,
		 * DDL_NAME); // assertText(DDL_XPATH, DDL_NAME);
		 * 
		 * int SORT_ITEM = 3; for(int INDEX = 1 ;INDEX <= SORT_ITEM; INDEX++){
		 * String CB_XPATH =
		 * "//*[@id='primary']/div[4]/div/div[2]/div/ul/li["+INDEX+"]/a";
		 * click(CB_XPATH); // click(CB_XPATH); } // click("link=Featured"); //
		 * click("link=Price (low to high)"); // click(
		 * "link=Product name (A to Z)"); }catch(NoSuchElementException e){
		 * logger.error("NO ELEMENT: "+e.toString()); }catch
		 * (StaleElementReferenceException e) { logger.error("STALE ELEMENT: "
		 * +e.toString()); }
		 */

		// Iterating Product Items
		// String PCONTAINER_XPATH =
		// "//div[contains(@class,'search-result-content')]";
		// String PRINDEX_XPATH = "//div["+RINDEX+"]";
		// String CINDEX_XPATH = "//div["+RINDEX+"]";

		// ((JavascriptExecutor)driver).executeScript("window.scrollTo(0,document.body.scrollHeight)");
		// ((JavascriptExecutor)driver).executeScript("window.scrollTo(document.body.scrollHeight,0)");
		// ((JavascriptExecutor)driver).executeScript("window.scrollTo(0,document.body.scrollHeight)");

		String SUBFORM_XPATH = "//*[@id='SubscribeForm']/label";
		WebElement subformEle = tmethods.getWE(driver, SUBFORM_XPATH);

		JavascriptExecutor jse = (JavascriptExecutor) driver;
		String bodyHeight = jse.executeScript("return document.body.scrollHeight").toString();
		int scrollHeight = Integer.parseInt(bodyHeight);
		logger.info("Scroll Height " + bodyHeight);

		// jse.executeScript("window.scrollTo(0, document.body.scrollHeight);");

		for (int height = 0; height < scrollHeight; height += 10) {
			jse.executeScript("window.scrollTo(0, " + height + ");");
			bodyHeight = jse.executeScript("return document.body.scrollHeight").toString();
			scrollHeight = Integer.parseInt(bodyHeight);
		}

		jse.executeScript("window.scrollTo(" + scrollHeight + ", 0);");

		int RINDEX = 1;
		int PCOUNT = 0;
		boolean NO_ROW = false;
		// int PAGE_TOP = 0;
		// int PAGE_END = 0;

		
		List<Product> productlist = new ArrayList<Product>();
		
		int PROD_ID = 0;

		while (!NO_ROW) {
			try {
					if(RINDEX==4)
						NO_ROW = true;
				
				if (RINDEX % 5 != 0) {
					// row product-list-row //*[@id="primary"]/div[5]/div[19]
					for (int CINDEX = 1; CINDEX <= 3; CINDEX++) {
						String PROW_XPATH = "//div[contains(@class,'search-result-content')]/div[" + RINDEX + "]";
						String PCOL_XPATH = PROW_XPATH + "/div[" + CINDEX + "]";

						WebElement prodEle = tmethods.getWE(driver, PCOL_XPATH);
						// WebElement prodEle =
						// driver.findElement(By.xpath(PCOL_XPATH));

						if ( null != prodEle && prodEle.getAttribute("class").toString().contains("product-list-col")) {

							String ptext = prodEle.getText();
							logger.info("\nProduct:" + (++PCOUNT) + "\n" + ptext);

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
								// product.setFlag1(flag1);
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
							// logger.info("PLINKS_XPATH: " + wbEle.getText());

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
								// product.setVariant1(variant1);
								// product.setVariant2(variant2);

							}

							/** PRODUCT RATINGS */
							wbEle = tmethods.getWE(driver, PCOL_XPATH + PRATING_XPATH);
							if (null != wbEle) {
								logger.info("PRATING_XPATH: " + wbEle.getText());
								// product.setRatings(ratings);
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
		}		// EOWHILE
		
		if(tmethods.writeToPropertiesFile(productlist))
			logger.info("PROPERTIES FILE CREATED... ");
	
		logger.info("ROW COUNT: " + (RINDEX));
		logger.info("PRODUCT COUNT: " + PCOUNT);

		/*
		 * 
		 * 
		 * Actions actions = new Actions(driver); //
		 * actions.moveToElement(tmethods.getWE(driver, PCONTAINER_XPATH));
		 * actions.moveToElement(tmethods.getWE(driver, PROW_XPATH));
		 * actions.moveToElement(tmethods.getWE(driver, PCOL_XPATH));
		 * actions.moveToElement(tmethods.getWE(driver, PLIST_XPATH));
		 * 
		 * actions.moveToElement(tmethods.getWE(driver, PIMG_XPATH));
		 * actions.moveToElement(tmethods.getWE(driver, PMFLAG_XPATH));
		 * actions.moveToElement(tmethods.getWE(driver, PNAME_XPATH));
		 * actions.moveToElement(tmethods.getWE(driver, PLINKS_XPATH));
		 * actions.moveToElement(tmethods.getWE(driver, PPRICE_XPATH));
		 * actions.moveToElement(tmethods.getWE(driver, PVAR_XPATH));
		 * actions.moveToElement(tmethods.getWE(driver, PRATING_XPATH));
		 * actions.moveToElement(tmethods.getWE(driver, PCTA_XPATH));
		 * 
		 * actions.click(); actions.build().perform(); driver.navigate().back();
		 */
		// First Item another xpath
		// pimg, ptitle, cost, vlinks, ratings, addCTA

		/*
		 * String PIMAGE_XPATH =
		 * "//img[contains(@class,'img-responsive') and contains(@class,'product-img')]"
		 * ; // String PIMAGE_XPATH =
		 * "//div[contains(@class,'product-img-block') and contains(@class,'quickview-enabled')][1]/a[contains(@class,'thumb-link') and contains(@class,'product-view-link')]/img[contains(@class,'img-responsive') and contains(@class,'product-img')]"
		 * ; String PNAME_XPATH =
		 * "//h2[contains(@class,'product-name') and contains(@class,'name-link')][1]/a[contains(@class,'product-view-link')]"
		 * ; String PCOST_XPATH =
		 * "//div[contains(@class,'product-price-variant-block')][1]/span[contains(@class,'product-price')][1]/span[contains(@class,'product-sales-price')][1]";
		 * String PVARIENT_XPATH =
		 * "//div[contains(@class,'product-price-variant-block')][1]/span[contains(@class,'product-variants')][1]/a[contains(@class,'plp-list-quick-view')]";
		 * String RATINGS_XPATH =
		 * "//*[@id='BVRRInlineRating-renewal-cream-master']//span[contains(@class,'bv-rating-stars-on') and contains(@class,'bv-rating-stars')][1]"
		 * ; // String ADDBAG_XPATH =
		 * "//[starts-with(@id,'dwfrm_product_addtocart_')]/div[contains(@class,'product-cta')][1]/a[contains(@class,'btn') and contains(@class,'btn-default') and contains(@class,'btn-sm') and contains(@class,'plp-list-quick-view')]"
		 * ; String ADDBAG_XPATH =
		 * "//[starts-with(@id,'dwfrm_product_addtocart_')]/div[contains(@class,'product-cta')][1]";
		 * 
		 * WebElement pimage = TestMethods.getInstance().getWE(driver,
		 * PIMAGE_XPATH);
		 * 
		 * int height = pimage.getSize().getHeight(); int width =
		 * pimage.getSize().getWidth(); logger.info("Pimage Height "+height);
		 * logger.info("Pimage Width "+width);
		 * 
		 * WebElement pname = TestMethods.getInstance().getWE(driver,
		 * PNAME_XPATH); pname.getText();
		 * 
		 * // "$185.00" WebElement pcost =
		 * TestMethods.getInstance().getWE(driver, PCOST_XPATH);
		 * pcost.getText();
		 * 
		 * // "2 Sizes" WebElement pvarient =
		 * TestMethods.getInstance().getWE(driver, PVARIENT_XPATH);
		 * pvarient.getText();
		 * 
		 * // "★★★★★" WebElement pratings =
		 * TestMethods.getInstance().getWE(driver, RATINGS_XPATH);
		 * pratings.getText();
		 * 
		 * // "ADD TO BAG" WebElement paddBag=
		 * TestMethods.getInstance().getWE(driver, ADDBAG_XPATH);
		 * paddBag.getText();
		 * 
		 */

	}
}