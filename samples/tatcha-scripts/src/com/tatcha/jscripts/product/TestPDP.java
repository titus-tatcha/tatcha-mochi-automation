package com.tatcha.jscripts.product;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.tatcha.jscripts.TcConstants;
import com.tatcha.jscripts.commons.ReportGenerator;
import com.tatcha.jscripts.commons.TestMethods;
import com.tatcha.jscripts.dao.Product;
import com.tatcha.jscripts.dao.TestCase;
import com.tatcha.utils.ClassUtils;
import com.xceptance.xlt.api.engine.scripting.AbstractWebDriverScriptTestCase;

/**
 * Test Product Details Page as PRODUCT which is set as system
 * properties in pom.xml. ie. <product.name>
 * 
 * <!--Modules need to be set either one of the following : SHOPALL MOIST CLEANS
 * FACE MASKS ESSENCE EYE LIP MAKEUP PRIMING BODY BLOTTING NORMALDRY NORMALOILY
 * DRY SENSITIVE BESTSELLERS NEW GIFTSETS GIFTPURCHASE GIFTPOUCH OURSTORY
 * NATURAL TIPS GIVING -->
 * 
 * <!-- Products need to be set either one of the following : SILKCANVAS PCOIL
 * PCOIL.TRAVEL PINKSTARTER.NORMDRY REDLIP DPCLEANSE WATERCREAM -->
 * 
 * @author titus
 *
 */
public class TestPDP extends AbstractWebDriverScriptTestCase {

	private static WebDriver driver;
	private String baseUrl;
	private boolean acceptNextAlert = true;
	private StringBuffer verificationErrors = new StringBuffer();
	private ArrayList<Product> prodList = new ArrayList<Product>();
	private ArrayList<Product> prodEqualList = new ArrayList<Product>();
	private double subTotal = 0;
	private int productId = 10001;

	private final static Logger logger = Logger.getLogger(TestPDP.class);
	private Properties locator = new Properties();
	// private Properties prop = new Properties();

	private static TestMethods tmethods;
	private TestCase testCase;
	private List<TestCase> tcList;
	private final String MODULE = "Product Details Page";
	// private int TESTNO = 1;

	public TestPDP() {
		super(driver);
	}

	@BeforeClass
	public static void initClass() {
		tmethods = TestMethods.getInstance();
		driver = tmethods.getWebDriver();
	}

	/**
	 * Method to test Product Details Page on the basis of Module / Product set
	 * as system property
	 * 
	 * @throws Exception
	 */

	@Test
	public void testPDP() {

		tcList = new ArrayList<TestCase>();
		try {
			baseUrl = tmethods.getBaseURL();
			driver.get(baseUrl);
			driver.manage().window().maximize();

			/** Closing News Letter Popup */
			tmethods.testNewsLetterPopupModal(driver);
			// testMavenDynamicProperties();

			// locator.load(new
			// FileInputStream(getClass().getResource("/elementLocator.properties").getFile()));
			String resourceFile = getClass().getResource("/PDP.properties").getFile();
			logger.info("resourceFile: " + resourceFile);

			locator.load(new FileInputStream(resourceFile));

			// prop.load(new
			// FileInputStream(getClass().getResource("/tatcha.properties").getFile()));

			WebElement webEleDiv1 = tmethods.getWE(driver, locator, "pname.div1");
			WebElement webEleDiv2 = tmethods.getWE(driver, locator, "pname.div2");
			WebElement webEleDiv3 = tmethods.getWE(driver, locator, "pname.div3");
			// WebElement webEleH1 = tmethods.getWE(driver, locator,
			// "pname.h1");

			Actions actions = new Actions(driver);
			actions.moveToElement(webEleDiv1);
			actions.moveToElement(webEleDiv2);
			actions.moveToElement(webEleDiv3);
			actions.build().perform();

			String productName = webEleDiv3.getText();
			Product product = createProduct(productName.trim());

			// Need to check all PDP sections

//			testMarketingFlags(driver, locator);
			testProductTitles(driver, product);
			testProductPrice(driver, product, locator);

			testSkinVariant(driver, locator);
			testSizeVariant(driver, locator);

			testQuantityNotification(driver, locator);
			testQuantityDDL(driver, locator);
			testQuantityLimitMessage(driver, locator);

			testReviews(driver, locator); // Reviews need to be modified
			testProductImages(driver, locator);

			/** Testing Sprint 5 stories - MOC 203,204,205,206,207,208,211 */
			testRecommendedFor(driver, product, locator);
			testWhatItis(driver, product, locator);
			testWhyItWorks(driver, product, locator);
			testIngredient(driver, product, locator);
			testHadasei(driver, product, locator);
			testHowToUse(driver, product, locator);

			testPurityPromise(driver, product, locator);

			// addItemAndCheckout(driver, locator);
			// checkoutLogin(driver, product);

			// Report Generation for PDP Module
		} catch (FileNotFoundException e) {
			String FUNCTIONALITY_ERROR = "Testing PDP";
			String TC_NO = "TC-ERR";
			String MOC_NO = "MOC-PDP";
			String REMARKS = "File Not Found";
			TestCase tc = new TestCase(TC_NO, MOC_NO, FUNCTIONALITY_ERROR, TcConstants.FAIL, REMARKS);
			tcList.add(tc);
		} catch (IOException e) {
			String FUNCTIONALITY_ERROR = "Testing PDP";
			String TC_NO = "TC-ERR";
			String MOC_NO = "MOC-PDP";
			String REMARKS = "IO Exception";
			TestCase tc = new TestCase(TC_NO, MOC_NO, FUNCTIONALITY_ERROR, TcConstants.FAIL, REMARKS);
			tcList.add(tc);
		}catch (NoSuchElementException e) {
			String FUNCTIONALITY_ERROR = "Testing PDP";
			String TC_NO = "TC-ERR";
			String MOC_NO = "MOC-PDP";
			String REMARKS = "Element Location Not Found";
			TestCase tc = new TestCase(TC_NO, MOC_NO, FUNCTIONALITY_ERROR, TcConstants.FAIL, REMARKS);
			tcList.add(tc);
		} catch (IllegalArgumentException e) {
			String FUNCTIONALITY_ERROR = "Testing PDP";
			String TC_NO = "TC-ERR";
			String MOC_NO = "MOC-PDP";
			String REMARKS = "Illegal Argument - Needs a Product to test";
			TestCase tc = new TestCase(TC_NO, MOC_NO, FUNCTIONALITY_ERROR, TcConstants.FAIL, REMARKS);
			tcList.add(tc);
		} catch (TimeoutException e) {
			String FUNCTIONALITY_ERROR = "Testing PDP";
			String TC_NO = "TC-ERR";
			String MOC_NO = "MOC-PDP";
			String REMARKS = "Time out Exception";
			TestCase tc = new TestCase(TC_NO, MOC_NO, FUNCTIONALITY_ERROR, TcConstants.FAIL, REMARKS);
			tcList.add(tc);
		} /*
			 * catch (Exception e) { String FUNCTIONALITY_ERROR = "Testing PDP";
			 * String TC_NO = "TC-ERR"; String MOC_NO = "MOC-PDP"; String
			 * REMARKS = "Exception"; TestCase tc = new TestCase(TC_NO, MOC_NO,
			 * FUNCTIONALITY_ERROR, TcConstants.FAIL, REMARKS); tcList.add(tc);
			 * }
			 */
		ReportGenerator.getInstance().generateReport(MODULE, tcList);

	}

	/**
	 * Method to create product object
	 * 
	 * @param productName
	 * @return
	 */
	private Product createProduct(String productName) {
		logger.info("INSIDE createProduct");
		// Create Product object
		Product product = new Product();
		String pkidStart;
		int PKBEGINLIMIT = 4;
		if (null != productName && productName.trim().length() >= PKBEGINLIMIT) {
			pkidStart = productName.substring(0, 4);
		} else {
			pkidStart = "PROD";
		}
		product.setPid(pkidStart + (ClassUtils.getInstance().getPK()));
		// product.setPid("P" + (productId++));
		product.setName(productName);
		return product;
	}

	/**
	 * Method to test Marketing flags of PDP
	 * 
	 * @param driver
	 * @param locator
	 */
	private void testMarketingFlags(WebDriver driver, Properties locator) {
		// Marketing Flags
		logger.info("INSIDE testMarketingFlags");
		String FUNCTIONALITY = TcConstants.PDP_FUN_101;
		logger.info(FUNCTIONALITY);
		testCase = new TestCase();
		// testCase.setTestNo("TC-"+TESTNO++);
		testCase.setTestNo(TcConstants.PDP_TC_101);
		testCase.setFunctionality(FUNCTIONALITY);
		testCase.setMocNo(TcConstants.PDP_MOC_101);

		String STATUS = TcConstants.FAIL;
		String REMARKS = "";

		try {
			String[] marketingFlags = { "New", "Bestseller", "Limited Edition", "Exclusive" };
			WebElement flag1Ele1 = driver.findElement(By.cssSelector(
					"div.product-summary-desktop > div.product-marketing-flag-block > span.product-marketing-flag"));
			String marketFlag1 = flag1Ele1.getText().trim();
			// *[@id="pdpMain"]/div[1]/div[1]/div/span[1]
			// *[@id="pdpMain"]/div[1]/div[1]/div/span[3]

			WebElement flag1Ele2 = driver.findElement(By.xpath(locator.getProperty("marketingFlag.two").toString()));
			String marketFlag2 = flag1Ele2.getText().trim();

			// assertEquals("New", marketFlag1);
			// assertEquals("Bestseller", marketFlag2);

			/**
			 * Checking whether Marketing flag if Present is listing any of the
			 * Marketing Message
			 */

			boolean flag1Validity = false;
			boolean flag2Validity = false;

			if (null != marketFlag1 && !marketFlag1.isEmpty()) {
				for (String flagName : marketingFlags) {
					if (flagName.equalsIgnoreCase(marketFlag1)) {
						flag1Validity = true;
						break;
					}
				}
			} else {
				flag1Validity = true;
			}

			if (null != marketFlag1 && !marketFlag1.isEmpty()) {
				for (String flagName : marketingFlags) {
					if (flagName.equalsIgnoreCase(marketFlag2)) {
						flag2Validity = true;
						break;
					}
				}
			} else {
				flag2Validity = true;
			}

			/** Raising Invalid Market Flags as Exceptions */
			if (!flag1Validity) {
				STATUS = TcConstants.FAIL;
				REMARKS = "Marketing Flag 1 is invalid";
			}

			if (!flag2Validity) {
				STATUS = TcConstants.FAIL;
				REMARKS = "Marketing Flag 2 is invalid";
			}

			if (flag1Validity && flag2Validity)
				STATUS = TcConstants.PASS;

		} catch (NoSuchElementException ee) {
			/** No marketing banner */
			STATUS = TcConstants.FAIL;
			REMARKS = "Marketing Banner not found";
			logger.error(REMARKS + ee.toString());
		} catch (Exception ee) {
			STATUS = TcConstants.FAIL;
			REMARKS = "Exception in Marketing Banner";
			logger.error(REMARKS + ee.toString());
		}

		testCase.setStatus(STATUS);
		testCase.setRemarks(REMARKS);
		tcList.add(testCase);
	}

	/**
	 * Method to test Product Titles of PDP
	 * 
	 * @param driver
	 * @param product
	 */
	private void testProductTitles(WebDriver driver, Product product) {
		// Product Titles
		logger.info("INSIDE testProductTitles");
		String FUNCTIONALITY = TcConstants.PDP_FUN_102;
		logger.info(FUNCTIONALITY);
		testCase = new TestCase();
		// testCase.setTestNo("TC-"+TESTNO++);
		testCase.setTestNo(TcConstants.PDP_TC_102);
		testCase.setFunctionality(FUNCTIONALITY);
		testCase.setMocNo(TcConstants.PDP_MOC_102);

		String STATUS = TcConstants.FAIL;
		String REMARKS = "";

		// Product Title
		try {
			WebElement prodTitleEle = driver
					.findElement(By.cssSelector("div.product-summary-desktop > h1.product-name"));
			String prodTitle = prodTitleEle.getText().toLowerCase();
			// assert (prodTitle.contains(product.getName().toLowerCase()));
			tmethods.assertionChecker(prodTitle, product.getName().toLowerCase(), "CONTAINS");

			// Subtitle
			try {
				WebElement prodsubtitleEle = driver.findElement(
						By.cssSelector("div.product-summary-desktop > h1.product-name > span.product-subtitle"));
				product.setSubtitle(prodsubtitleEle.getText());
				STATUS = TcConstants.PASS;
			} catch (NoSuchElementException ne) {
				STATUS = TcConstants.FAIL;
				REMARKS = "No Subtitle";
				logger.error(REMARKS + ne.toString());
			}

		} catch (NoSuchElementException ne) {
			STATUS = TcConstants.FAIL;
			REMARKS = "No Title";
			logger.error(REMARKS + ne.toString());
		}

		testCase.setStatus(STATUS);
		testCase.setRemarks(REMARKS);
		tcList.add(testCase);
	}

	/**
	 * Method to test Product Price of PDP
	 * 
	 * @param driver
	 * @param product
	 * @param locator
	 */
	private void testProductPrice(WebDriver driver, Product product, Properties locator) {
		// Product Price
		logger.info("INSIDE testProductPrice");
		String FUNCTIONALITY = TcConstants.PDP_FUN_103;
		logger.info(FUNCTIONALITY);
		testCase = new TestCase();
		// testCase.setTestNo("TC-"+TESTNO++);
		testCase.setTestNo(TcConstants.PDP_TC_103);
		testCase.setFunctionality(FUNCTIONALITY);
		testCase.setMocNo(TcConstants.PDP_MOC_103);

		String STATUS = TcConstants.FAIL;
		String REMARKS = "";

		try {
			String SPLITTER = "-";
			String productPrice = tmethods.getWE(driver, "product.price").getText();
			if (productPrice.contains(SPLITTER)) { // $15.00 - $45.00
				String[] lowhighPrices = productPrice.split(SPLITTER);
				String lowPrice = lowhighPrices[0].trim();
				String highPrice = lowhighPrices[1].trim();
				product.setHighPrice(highPrice.trim());
				product.setLowPrice(lowPrice.trim());
			} else {
				// String productPrice =
				// driver.findElement(By.xpath("//*[@id='product-content']/div[2]/div/div[1]/span")).getText();
				product.setPrice(productPrice);
				STATUS = TcConstants.PASS;
			}
		} catch (NoSuchElementException ne) {
			STATUS = TcConstants.FAIL;
			REMARKS = "Product Price Not Found ";
			logger.error(REMARKS + ne.toString());
		} catch (Exception ne) {
			STATUS = TcConstants.FAIL;
			REMARKS = "Product Price Exception ";
			logger.error(REMARKS + ne.toString());
		}

		testCase.setStatus(STATUS);
		testCase.setRemarks(REMARKS);
		tcList.add(testCase);

	}

	/**
	 * Method to test Skin Variants of PDP
	 * 
	 * @param driver
	 * @param locator
	 */
	private void testSkinVariant(WebDriver driver, Properties locator) {
		logger.info("INSIDE testSkinVariant");
		String FUNCTIONALITY = TcConstants.PDP_FUN_104;
		logger.info(FUNCTIONALITY);
		testCase = new TestCase();
		// testCase.setTestNo("TC-"+TESTNO++);
		testCase.setTestNo(TcConstants.PDP_TC_104);
		testCase.setFunctionality(FUNCTIONALITY);
		testCase.setMocNo(TcConstants.PDP_MOC_104);

		String STATUS = TcConstants.FAIL;
		String REMARKS = "";

		// Skin Type
		boolean skinValidity = false;
		try {

			String[] skinVariants = { "Normal", "Combination", "Dry", "Sensitive", "Oily" };
			WebElement skinTitle = driver.findElement(By.xpath(locator.getProperty("product.skinVariant").toString()));

			if (null != skinTitle.getText() && "size".equalsIgnoreCase(skinTitle.getText())) {

				for (int i = 1; i < 5; i++) {
					WebElement skinEle = driver.findElement(By
							.xpath("//*[@id='product-content']/div[3]/div[1]/div[2]/div/div/div/div/div[" + i + "]/a"));
					if (null != skinEle) {
						String skinVariant = skinEle.getText().trim();
						if (elementPresent(skinVariants, skinVariant)) {
							skinValidity = true;
							break;
						}
					}
				}
			}
		} catch (NoSuchElementException ne) {
			/** Skin Variants not present */
			skinValidity = true;
			REMARKS = "Skin Variant is Empty";
			logger.error(" No Skin Variant !!! " + ne.toString());
		}

		if (!skinValidity) {
			STATUS = TcConstants.FAIL;
			REMARKS = "Skin Variant is invalid";
		} else {
			STATUS = TcConstants.PASS;
		}

		testCase.setStatus(STATUS);
		testCase.setRemarks(REMARKS);
		tcList.add(testCase);

	}

	/**
	 * Method to test Size Variants of PDP
	 * 
	 * @param driver
	 * @param locator
	 */
	private void testSizeVariant(WebDriver driver, Properties locator) {
		logger.info("INSIDE testSizeVariant");
		String FUNCTIONALITY = TcConstants.PDP_FUN_105;
		logger.info(FUNCTIONALITY);
		testCase = new TestCase();
		// testCase.setTestNo("TC-"+TESTNO++);
		testCase.setTestNo(TcConstants.PDP_TC_105);
		testCase.setFunctionality(FUNCTIONALITY);
		testCase.setMocNo(TcConstants.PDP_MOC_105);
		String STATUS = TcConstants.FAIL;

		String REMARKS = "";

		// Size
		boolean sizeValidity = false;
		try {

			String[] sizeVariants = { "60g / 2.1 oz.", "10g / .35 oz." };
			WebElement sizeTitle = driver.findElement(By.xpath(locator.getProperty("product.sizeVariant").toString()));
			if (null != sizeTitle.getText() && "size".equalsIgnoreCase(sizeTitle.getText())) {

				for (int i = 1; i < 3; i++) {
					WebElement sizeEle = driver.findElement(By
							.xpath("//*[@id='product-content']/div[3]/div[2]/div[2]/div/div/div/div/div[" + i + "]/a"));
					if (null != sizeEle) {
						String sizeVariant = sizeEle.getText().trim();
						if (elementPresent(sizeVariants, sizeVariant)) {
							sizeValidity = true;
							break;
						}
					}
				}
			}
		} catch (NoSuchElementException ne) {
			/** Size Variants not present */
			sizeValidity = true;
			REMARKS = "Size Variant is Empty";
			logger.error(" No Size Variant !!! " + ne.toString());
		}

		if (!sizeValidity) {
			STATUS = TcConstants.FAIL;
			REMARKS = "Size Variant is invalid";
		} else {
			STATUS = TcConstants.PASS;
		}

		testCase.setStatus(STATUS);
		testCase.setRemarks(REMARKS);
		tcList.add(testCase);

	}

	/**
	 * Method to test Quantity Notification Message of PDP
	 * 
	 * @param driver
	 * @param locator
	 */
	private void testQuantityNotification(WebDriver driver, Properties locator) {
		// Quantity Notification Msg
		logger.info("INSIDE testQuantityNotification");
		String FUNCTIONALITY = TcConstants.PDP_FUN_106;
		logger.info(FUNCTIONALITY);
		testCase = new TestCase();
		// testCase.setTestNo("TC-"+TESTNO++);
		testCase.setTestNo(TcConstants.PDP_TC_106);
		testCase.setFunctionality(FUNCTIONALITY);
		testCase.setMocNo(TcConstants.PDP_MOC_106);
		String STATUS = TcConstants.FAIL;

		String REMARKS = "";

		boolean notifyQty = false;
		try {
			String notifyMessage = "Only A Few Left";
			WebElement notifyMsgEle = driver.findElement(By.cssSelector("div.product-inventory-flag"));
			String notifyMsg = notifyMsgEle.getText();
			if (notifyMessage.toLowerCase().equals(notifyMsg.toLowerCase())) {
				notifyQty = true;
			}
		} catch (NoSuchElementException ne) {
			notifyQty = true;
			REMARKS = "Quantity Notification Message is Empty";
			logger.error(REMARKS + ne.toString());
		}

		if (!notifyQty) {
			STATUS = TcConstants.FAIL;
			REMARKS = "Quantity Notification Message is invalid";
		} else {
			STATUS = TcConstants.PASS;
		}

		testCase.setStatus(STATUS);
		testCase.setRemarks(REMARKS);
		tcList.add(testCase);
	}

	/**
	 * Method to test Quantity DDL of PDP
	 * 
	 * @param driver
	 * @param locator
	 */
	private void testQuantityDDL(WebDriver driver, Properties locator) {
		// Quantity DDL
		logger.info("INSIDE testQuantityDDL");
		String FUNCTIONALITY = TcConstants.PDP_FUN_107;
		logger.info(FUNCTIONALITY);
		testCase = new TestCase();
		testCase.setTestNo(TcConstants.PDP_TC_107);
		testCase.setFunctionality(FUNCTIONALITY);
		testCase.setMocNo(TcConstants.PDP_MOC_107);
		String STATUS = TcConstants.FAIL;

		String REMARKS = "";

		try {
			Select dropdown = new Select(driver.findElement(By.id("Quantity")));
			dropdown.selectByVisibleText("1");
			// dropdown.selectByIndex(2);
			// assertEquals("1 2 3 4 5",
			// driver.findElement(By.id("Quantity")).getText());
			STATUS = TcConstants.PASS;
		} catch (NoSuchElementException ne) {
			STATUS = TcConstants.FAIL;
			REMARKS = "Quantity DDL Not Found";
			logger.error(REMARKS + ne.toString());
		}
		testCase.setStatus(STATUS);
		testCase.setRemarks(REMARKS);
		tcList.add(testCase);
	}

	/**
	 * Method to test Limit Quantity message section of PDP
	 * 
	 * @param driver
	 * @param locator
	 */
	private void testQuantityLimitMessage(WebDriver driver, Properties locator) {
		// Limit Quantity Message
		logger.info("INSIDE testQuantityLimitMessage");
		String FUNCTIONALITY = TcConstants.PDP_FUN_108;
		logger.info(FUNCTIONALITY);
		testCase = new TestCase();
		testCase.setTestNo(TcConstants.PDP_TC_108);
		testCase.setFunctionality(FUNCTIONALITY);
		testCase.setMocNo(TcConstants.PDP_MOC_108);
		String STATUS = TcConstants.FAIL;

		String REMARKS = "";

		boolean limitMessageValidity = false;
		try {
			String[] limitQuantityMsgs = { "Limit 1 per household.", "Limit 3 per household." };
			WebElement limitQuantityEle = driver.findElement(By.cssSelector("div.help-block"));
			if (null != limitQuantityEle) {
				String limitMsg = limitQuantityEle.getText().trim();
				if (elementPresent(limitQuantityMsgs, limitMsg)) {
					limitMessageValidity = true;
				}
			}
		} catch (NoSuchElementException ne) {
			logger.error("Limit Quantity Message is Not Present: " + ne.toString());
			limitMessageValidity = true;
		}

		if (!limitMessageValidity) {
			STATUS = TcConstants.FAIL;
			REMARKS = "Limit Quantity Message is invalid";
		} else {
			STATUS = TcConstants.PASS;
			REMARKS = "Limit Quantity Message is empty";
		}

		testCase.setStatus(STATUS);
		testCase.setRemarks(REMARKS);
		tcList.add(testCase);
	}

	/**
	 * Method to test Reviews section of PDP
	 * 
	 * @param driver
	 * @param locator
	 */
	private void testReviews(WebDriver driver, Properties locator) {
		// Reviews
		logger.info("INSIDE testReviews");
		String FUNCTIONALITY = TcConstants.PDP_FUN_109;
		logger.info(FUNCTIONALITY);
		testCase = new TestCase();
		testCase.setTestNo(TcConstants.PDP_TC_109);
		testCase.setFunctionality(FUNCTIONALITY);
		testCase.setMocNo(TcConstants.PDP_MOC_109);
		String STATUS = TcConstants.FAIL;

		String REMARKS = "";

		try {
			// WebElement reviewEle =
			// driver.findElement(By.xpath("//*[@id='product-content']/div[2]/div[2]/div[2]/a"));
			WebElement reviewEle = driver.findElement(By.xpath(locator.getProperty("product.reviews").toString()));

			String REVIEWS = "9,999 Reviews";
			String reviewText = reviewEle.getText();
			// assertEquals(REVIEWS, reviewText);
			tmethods.assertionChecker(REVIEWS, reviewText);

		} catch (NoSuchElementException ne) {
			STATUS = TcConstants.FAIL;
			REMARKS = "Reviews Not Found";
			logger.error(REMARKS + ne.toString());
		}

		testCase.setStatus(STATUS);
		testCase.setRemarks(REMARKS);
		tcList.add(testCase);

	}

	/**
	 * Method to test Product Images of PDP
	 * 
	 * @param driver
	 * @param locator
	 */
	private void testProductImages(WebDriver driver, Properties locator) {
		// Main Image & Thumbnails
		logger.info("INSIDE testProductImages");
		String FUNCTIONALITY = TcConstants.PDP_FUN_110;
		logger.info(FUNCTIONALITY);
		testCase = new TestCase();
		testCase.setTestNo(TcConstants.PDP_TC_110);
		testCase.setFunctionality(FUNCTIONALITY);
		testCase.setMocNo(TcConstants.PDP_MOC_110);
		String STATUS = TcConstants.FAIL;

		String REMARKS = "";

		try {
			WebElement imgMainEle = driver.findElement(By.xpath(locator.getProperty("product.mainImage").toString()));
			// imgValidity = true;
			// imgMainEle.click();
			STATUS = TcConstants.PASS;
		} catch (NoSuchElementException ne) {
			STATUS = TcConstants.FAIL;
			REMARKS = "Image Thumbnails Not Found";
			logger.error(REMARKS + ne.toString());
		}

		testCase.setStatus(STATUS);
		testCase.setRemarks(REMARKS);
		tcList.add(testCase);

		/** thumbnails are not scene in this sprint 4 */
		// assertEquals("",
		// driver.findElement(By.cssSelector("img.primary-image.img-responsive")).getText());
		// assertEquals("",
		// driver.findElement(By.cssSelector("img.productthumbnail")).getText());
		// driver.findElement(By.xpath("(//img[@alt='One Step Camellia Cleansing
		// Oil'])[3]")).click();
		// assertEquals("",
		// driver.findElement(By.cssSelector("img.primary-image.img-responsive")).getText());
		// driver.findElement(By.cssSelector("img.productthumbnail")).click();
	}

	/**
	 * Method to test Recommended For section of PDP MOC 203
	 * 
	 * @param driver
	 * @param product
	 * @param locator
	 */
	private void testRecommendedFor(WebDriver driver, Product product, Properties locator) {
		// Recommended For
		logger.info("INSIDE testRecommendedFor");
		String FUNCTIONALITY = TcConstants.PDP_FUN_111;
		logger.info(FUNCTIONALITY);
		testCase = new TestCase();
		testCase.setTestNo(TcConstants.PDP_TC_111);
		testCase.setFunctionality(FUNCTIONALITY);
		testCase.setMocNo(TcConstants.PDP_MOC_111);
		String STATUS = TcConstants.FAIL;

		String REMARKS = "";

		try {
			// Benefits - Recommended For
			String BENEFITS = "Benefits";
			String RECOMMENDED_FOR = "Recommended For";

			WebElement benefitsTitle = tmethods.getWE(driver, locator, "benefits.title");
			if (null != benefitsTitle)
				tmethods.assertionChecker(BENEFITS, benefitsTitle.getText());

			WebElement recommendedForTitle = tmethods.getWE(driver, locator, "recommendedFor.title");
			if (null != recommendedForTitle)
				tmethods.assertionChecker(RECOMMENDED_FOR, recommendedForTitle.getText());

			String[] recommended = { "NORMAL", "DRY", "OILY", "ACNE SCARS", "BRIGHTENING", "DARK SPOTS" };
			Set<String> recommendedValues = new HashSet<String>(Arrays.asList(recommended));

			for (int index = 1; index <= 6; index++) {
				try {
					// images
					WebElement img = driver
							.findElement(By.xpath("//*[@id='benefits']/div[1]/div/div/div/div[" + index + "]/img"));
					// 64 x 64s
					assertEquals(64, img.getSize().getHeight());
					assertEquals(64, img.getSize().getWidth());
					// image titles
					WebElement imgTitle = driver
							.findElement(By.xpath("//*[@id='benefits']/div[1]/div/div/div/div[" + index + "]/div"));
					recommendedValues.contains(imgTitle.getText());
					recommendedValues.remove(imgTitle.getText());
					STATUS = TcConstants.PASS;
				} catch (NoSuchElementException ne) {
					logger.error("No Element " + index + " in Recommended For " + ne.toString());
				}
			}
		} catch (NoSuchElementException ne) {
			STATUS = TcConstants.FAIL;
			REMARKS = "Recommended-For Not Found ";
			logger.error(REMARKS + ne.toString());
		}

		testCase.setStatus(STATUS);
		testCase.setRemarks(REMARKS);
		tcList.add(testCase);

	}

	/**
	 * Method to test What it is of PDP MOC 204
	 * 
	 * @param driver
	 * @param product
	 * @param locator
	 */
	private void testWhatItis(WebDriver driver, Product product, Properties locator) {
		// What It Is"
		logger.info("INSIDE testWhatItis");
		String FUNCTIONALITY = TcConstants.PDP_FUN_112;
		logger.info(FUNCTIONALITY);
		testCase = new TestCase();
		testCase.setTestNo(TcConstants.PDP_TC_112);
		testCase.setFunctionality(FUNCTIONALITY);
		testCase.setMocNo(TcConstants.PDP_MOC_112);
		String STATUS = TcConstants.FAIL;

		String REMARKS = "";

		StringBuilder REMARKS_STRING = new StringBuilder();
		boolean FLAG1 = true;
		boolean FLAG2 = true;

		try {
			// whatsLabel
			// Pure and Finest product
			String WHATS_LABEL = "Pure and Finest product";
			WebElement whatsEle = driver.findElement(By.xpath(locator.getProperty("whatItis.text").toString()));
			if (!tmethods.assertionChecker(WHATS_LABEL, whatsEle.getText(), "")) {
				FLAG1 = false;
				REMARKS = "WHAT IS Label mismatch";
				REMARKS_STRING.append(REMARKS);
			}
			// char count 185 MAX
			if (null != whatsEle && whatsEle.getText().length() > 185) {
				FLAG2 = false;
				REMARKS = "For What it is: length of characters is greater than specified limit (185).";
				REMARKS_STRING.append(REMARKS);
				// throw new CharacterLengthExceededException(
				// "For What it is: length of characters is greater than
				// specified limit (185)");
			}
			if (FLAG1 && FLAG2)
				STATUS = TcConstants.PASS;
			else
				STATUS = TcConstants.FAIL;
		} catch (NoSuchElementException ne) {
			STATUS = TcConstants.FAIL;
			REMARKS = "What It Is Not Found.";
			REMARKS_STRING.append(REMARKS);
			logger.error(REMARKS + ne.toString());

		}
		testCase.setStatus(STATUS);
		testCase.setRemarks(REMARKS_STRING.toString());
		tcList.add(testCase);
	}

	/**
	 * Method to test Why it works section of PDP MOC 205
	 * 
	 * @param driver
	 * @param product
	 * @param locator
	 */
	private void testWhyItWorks(WebDriver driver, Product product, Properties locator) {
		// Why It Works
		logger.info("INSIDE testWhyItWorks");
		String FUNCTIONALITY = TcConstants.PDP_FUN_113;
		logger.info(FUNCTIONALITY);
		testCase = new TestCase();
		testCase.setTestNo(TcConstants.PDP_TC_113);
		testCase.setFunctionality(FUNCTIONALITY);
		testCase.setMocNo(TcConstants.PDP_MOC_113);
		String STATUS = TcConstants.FAIL;

		String REMARKS = "";

		StringBuilder REMARKS_STRING = new StringBuilder();
		boolean FLAG1 = true;
		boolean FLAG2 = true;

		try {
			// Title
			WebElement testWiwTitle = driver.findElement(By.xpath(locator.getProperty("whyItworks.title").toString()));

			// points
			String WIW_POINT_NAME = "2 Types of Vitamin C";
			for (int index = 1; index <= 4; index++) {
				try {
					WebElement testWiwPoint = driver.findElement(
							By.xpath("//*[@id='benefits']/div[2]/div/div/div/ul/li[" + index + "]/strong"));
					WebElement testWiwPointdesc = driver
							.findElement(By.xpath("//*[@id='benefits']/div[2]/div/div/div/ul/li[" + index + "]/span"));
					// assertEquals(WIW_POINT_NAME, testWiwPoint.getText());
					if (!tmethods.assertionChecker(WIW_POINT_NAME, testWiwPoint.getText(), "")) {
						FLAG1 = false;
					}
					if (null != testWiwPointdesc && testWiwPointdesc.getText().length() > 750) {
						FLAG2 = false;
						// throw new CharacterLengthExceededException("Length of
						// Characters in greater than limit (750)");
					}

					if (FLAG1 && FLAG2)
						STATUS = TcConstants.PASS;
					else
						STATUS = TcConstants.FAIL;

				} catch (NoSuchElementException ne) {
					STATUS = TcConstants.FAIL;
					REMARKS = "Point " + index + " not found for Why It Works";
					logger.error(REMARKS + ne.toString());
				}
			}
		} catch (NoSuchElementException ne) {
			STATUS = TcConstants.FAIL;
			REMARKS = "Why It Works Not Found";
			logger.error(REMARKS + ne.toString());
		}
		testCase.setStatus(STATUS);
		testCase.setRemarks(REMARKS);
		tcList.add(testCase);
	}

	/**
	 * Method to test Ingredient section of PDP MOC 206
	 * 
	 * @param driver
	 * @param product
	 * @param locator
	 */
	private void testIngredient(WebDriver driver, Product product, Properties locator) {
		logger.info("INSIDE testIngredient");
		// Ingredients - Formulated without / Full Ingredients list / Spotlight
		// Ingredients
		String FUNCTIONALITY = TcConstants.PDP_FUN_114;
		logger.info(FUNCTIONALITY);
		testCase = new TestCase();
		testCase.setTestNo(TcConstants.PDP_TC_114);
		testCase.setFunctionality(FUNCTIONALITY);
		testCase.setMocNo(TcConstants.PDP_MOC_114);
		String STATUS = TcConstants.FAIL;

		String REMARKS = "";

		try {

			// Title
			WebElement ingrTitleEle = driver.findElement(By.xpath(locator.getProperty("ingredient.title").toString()));
			WebElement ingrDescEle = driver.findElement(By.xpath(locator.getProperty("ingredient.desc").toString()));

			// Formulated without:
			WebElement ingrFormHeadEle = driver
					.findElement(By.xpath(locator.getProperty("formulatedWithout.heading").toString()));
			WebElement ingrFormDescEle = driver
					.findElement(By.xpath(locator.getProperty("formulatedWithout.desc").toString()));

			// Full Ingredient List
			WebElement fullIngrListEle = driver
					.findElement(By.xpath(locator.getProperty("fullIngredient.list").toString()));
			STATUS = TcConstants.PASS;
		} catch (NoSuchElementException ne) {
			STATUS = TcConstants.FAIL;
			REMARKS = "Ingredient Not Found";
			logger.error(REMARKS + ne.toString());
		}

		// Spotlight Ingredients
		for (int index = 2; index <= 4; index++) {
			try {
				WebElement spotImgEle = driver
						.findElement(By.xpath("//*[@id='ingredients']/div[" + index + "]/div/div/div[1]/img"));
				logger.info("Image Height: " + spotImgEle.getSize().getHeight());
				logger.info("Image Width: " + spotImgEle.getSize().getWidth());

				WebElement spotTitleEle = driver
						.findElement(By.xpath("//*[@id='ingredients'/div[" + index + "]/div/div/div[2]/h2"));
				logger.info("Spotlight Title - " + spotTitleEle.getText());
				WebElement spotDescEle = driver
						.findElement(By.xpath("//*[@id='ingredients']/div[" + index + "]/div/div/div[2]/p"));
				logger.info("Spotlight Desc - " + spotDescEle.getText());

				STATUS = TcConstants.PASS;
			} catch (NoSuchElementException ne) {
				STATUS = TcConstants.FAIL;
				REMARKS = "Spotlight Ingredient Item " + (index - 1) + " Not Found ";
				logger.error(REMARKS + ne.toString());
			}
		}

		testCase.setStatus(STATUS);
		testCase.setRemarks(REMARKS);
		tcList.add(testCase);
	}

	/**
	 * Method to test Hadasei-3 section of PDP MOC 207
	 * 
	 * @param driver
	 * @param product
	 * @param locator
	 */
	private void testHadasei(WebDriver driver, Product product, Properties locator) {
		logger.info("INSIDE testHadasei");
		// Hadasei-3
		String FUNCTIONALITY = TcConstants.PDP_FUN_115;
		logger.info(FUNCTIONALITY);
		testCase = new TestCase();
		testCase.setTestNo(TcConstants.PDP_TC_115);
		testCase.setFunctionality(FUNCTIONALITY);
		testCase.setMocNo(TcConstants.PDP_MOC_115);
		String STATUS = TcConstants.FAIL;

		String REMARKS = "";

		StringBuilder REMARKS_STRING = new StringBuilder();
		try {
			boolean FLAG1 = true;
			boolean FLAG2 = true;
			boolean FLAG3 = true;

			// Hadasei-3

			// driver.findElement(by);
			/** Checking Hadasei Title and Description */

			String HADASEI_TITLE = "Hadasei-3";
			String HADASEI_DESC = "​Tatcha’s trinity of anti-aging superfoods reveals soft, youthful skin.";

			WebElement hadaTitle = driver.findElement(By.xpath(locator.getProperty("hadasei.title").toString()));
			// assertEquals(HADASEI_TITLE, hadaTitle.getText());
			if (!tmethods.assertionChecker(HADASEI_TITLE, hadaTitle.getText(), "")) {
				FLAG1 = false;
				REMARKS = "Hadasei Title Mis-match.";
				REMARKS_STRING.append(REMARKS);
			}

			WebElement hadaDesc = driver.findElement(By.xpath(locator.getProperty("hadasei.desc").toString()));
			assertEquals(HADASEI_DESC, hadaDesc.getText());
			if (!tmethods.assertionChecker(HADASEI_DESC, hadaDesc.getText(), "")) {
				FLAG2 = false;
				REMARKS = "Hadasei Description Mis-match.";
				REMARKS_STRING.append(REMARKS);
			}

			// Uji Green Tea
			// Okinawa Mozuku Algae
			// Akita Rice
			//
			// Test ContentPrized throughout Japan as the purest, finest source
			// of
			// Green Tea, this powerful antioxidant-rich botanical from Kyoto is
			// known to detoxify and prevent signs of premature aging.
			// This sea treasure from Japan’s tropical islands is rich
			// inpolysaccharides, essential for skin water retention and
			// renewal.
			// This rice is known as the finest rice in Japan for its superior
			// taste
			// and quality. Itis also a nourishing moisturizer, rich in
			// essential
			// proteins.

			for (int index = 1; index <= 3; index++) {
				try {
					WebElement itemImg = driver.findElement(
							By.xpath("//*[@id='ingredients']/div[5]/div/div/div/div[" + index + "]/div/img"));
					// logger.info("HADA - " + itemImg.getTagName());
					WebElement itemName = driver.findElement(
							By.xpath("//*[@id='ingredients']/div[5]/div/div/div/div[" + index + "]/div/h5"));
					// logger.info("HADA - " + itemName.getText());
					WebElement itemDesc = driver.findElement(
							By.xpath("//*[@id='ingredients']/div[5]/div/div/div/div[" + index + "]/div/p"));
					// logger.info("HADA - " + itemDesc.getText());

				} catch (NoSuchElementException ne) {
					FLAG3 = false;
					REMARKS = "HADASEI Item " + index + " Not Found.";
					REMARKS_STRING.append(REMARKS);
					logger.error(REMARKS + ne.toString());
				}

				if (FLAG1 && FLAG2 && FLAG3)
					STATUS = TcConstants.PASS;
				else
					STATUS = TcConstants.FAIL;
			}
		} catch (NoSuchElementException ne) {
			STATUS = TcConstants.FAIL;
			REMARKS = "HADASEI Not Found.";
			REMARKS_STRING.append(REMARKS);
			logger.error(REMARKS + ne.toString());
		} catch (AssertionError ne) {
			STATUS = TcConstants.FAIL;
			REMARKS = "HADASEI Assertion Error.";
			REMARKS_STRING.append(REMARKS);
			logger.error(REMARKS + ne.toString());
		}

		testCase.setStatus(STATUS);
		testCase.setRemarks(REMARKS_STRING.toString());
		tcList.add(testCase);
	}

	/**
	 * Method to test How to Use section of PDP MOC 208
	 * 
	 * @param driver
	 * @param product
	 * @param locator
	 */
	private void testHowToUse(WebDriver driver, Product product, Properties locator) {
		// How To Use
		logger.info("INSIDE testHowToUse");
		String FUNCTIONALITY = TcConstants.PDP_FUN_116;
		logger.info(FUNCTIONALITY);
		testCase = new TestCase();
		testCase.setTestNo(TcConstants.PDP_TC_116);
		testCase.setFunctionality(FUNCTIONALITY);
		testCase.setMocNo(TcConstants.PDP_MOC_116);
		String STATUS = TcConstants.FAIL;

		String REMARKS = "";

		StringBuilder REMARKS_STRING = new StringBuilder();

		try {
			String HOW_USE = "HOW TO USE";
			String HOW_USE_TITLE1 = "Suggested Usage";
			String HOW_USE_TITLE2 = "Dosage";
			String HOW_USE_TITLE3 = "Texture";

			WebElement howUseTitle = tmethods.getWE(driver, locator, "howToUse.title");
			tmethods.assertionChecker(HOW_USE, howUseTitle.getText());

			// *[@id='player_uid_596820346_1']
			WebElement howUsePlayer = tmethods.getWE(driver, locator, "howToUse.player");
			if (null != howUsePlayer)
				howUsePlayer.click();

			// Suggested Usage
			WebElement howUseTitle1 = tmethods.getWE(driver, locator, "howToUse.usage.title");
			WebElement howUseTitle1desc = tmethods.getWE(driver, locator, "howToUse.usage.desc");

			boolean FLAG1 = true;
			boolean FLAG2 = true;
			boolean FLAG3 = true;
			boolean FLAG4 = true;
			boolean FLAG5 = true;
			boolean FLAG6 = true;

			// assertEquals(HOW_USE_TITLE1, howUseTitle1.getText());
			if (!tmethods.assertionChecker(HOW_USE_TITLE1, howUseTitle1.getText(), "")) {
				FLAG1 = false;
				REMARKS = "How to Use title mis-match.";
				REMARKS_STRING.append(REMARKS);
			}

			if (null != howUseTitle1desc && howUseTitle1desc.getText().length() > 200) {
				FLAG2 = false;
				REMARKS = "For Suggested Usage: length of characters is greater than specified limit (200).";
				REMARKS_STRING.append(REMARKS);
				// throw new CharacterLengthExceededException(
				// "For Suggested Usage: length of characters is greater than
				// specified limit (200)");
			}

			// Dosage
			WebElement howUseTitle2 = tmethods.getWE(driver, locator, "howToUse.dosage.title");
			WebElement howUseTitle2desc = tmethods.getWE(driver, locator, "howToUse.dosage.desc");
			if (null != howUseTitle2)
				tmethods.assertionChecker(HOW_USE_TITLE2, howUseTitle2.getText());
			else {
				FLAG3 = false;
				REMARKS = "How to Use Dosage Label is empty.";
				REMARKS_STRING.append(REMARKS);
			}
			if (null != howUseTitle2desc && howUseTitle2desc.getText().length() > 100) {
				FLAG4 = false;
				REMARKS = "For Dosage: length of characters is greater than specified limit (100).";
				REMARKS_STRING.append(REMARKS);
				// throw new CharacterLengthExceededException(
				// "For Dosage: length of characters is greater than specified
				// limit (100)");
			}

			// Texture
			WebElement howUseTitle3 = tmethods.getWE(driver, locator, "howToUse.texture.title");
			if (null != howUseTitle3)
				tmethods.assertionChecker(HOW_USE_TITLE3, howUseTitle3.getText());
			else {
				FLAG5 = false;
				REMARKS = "How to Use Texture Label is empty.";
				REMARKS_STRING.append(REMARKS);
			}
			WebElement howUseTitle3desc = tmethods.getWE(driver, locator, "howToUse.texture.desc");
			if (null != howUseTitle3desc && howUseTitle3desc.getText().length() > 50) {
				FLAG6 = false;
				REMARKS = "For Texture: length of characters is greater than specified limit (50).";
				REMARKS_STRING.append(REMARKS);
				// throw new CharacterLengthExceededException(
				// "For Texture: length of characters is greater than specified
				// limit (50)");
			}

			if (FLAG1 && FLAG2 && FLAG3 && FLAG4 && FLAG5 && FLAG6)
				STATUS = TcConstants.PASS;
			else
				STATUS = TcConstants.FAIL;

		} catch (NoSuchElementException ne) {
			STATUS = TcConstants.FAIL;
			REMARKS = "How To Use Not Found";
			REMARKS_STRING.append(REMARKS);
			logger.error(REMARKS + ne.toString());
		}

		testCase.setStatus(STATUS);
		testCase.setRemarks(REMARKS_STRING.toString());
		tcList.add(testCase);
	}

	/**
	 * MOC 211
	 * 
	 * @throws
	 */
	private void testPurityPromise(WebDriver driver, Product product, Properties locator) {
		// Purity Promise
		logger.info("INSIDE testPurityPromise");
		String FUNCTIONALITY = TcConstants.PDP_FUN_117;
		logger.info(FUNCTIONALITY);
		testCase = new TestCase();
		testCase.setTestNo(TcConstants.PDP_TC_117);
		testCase.setFunctionality(FUNCTIONALITY);
		testCase.setMocNo(TcConstants.PDP_MOC_117);
		String STATUS = TcConstants.FAIL;

		String REMARKS = "";

		StringBuilder REMARKS_STRING = new StringBuilder();

		try {
			boolean FLAG1 = true;
			boolean FLAG2 = true;

			String PP_TITLE = "Purity Promise";
			// Image
			WebElement ppImage = driver.findElement(By.xpath(locator.getProperty("purityPromise.img").toString()));
			ppImage.getSize().getHeight();
			ppImage.getSize().getWidth();

			// Title
			WebElement ppTitle = driver.findElement(By.xpath(locator.getProperty("purityPromise.title").toString()));
			// assertEquals(PP_TITLE, ppTitle.getText());
			if (tmethods.assertionChecker(PP_TITLE, ppTitle.getText(), "")) {
				FLAG1 = false;
				REMARKS = "Purity Promise Title Assert Error";
				REMARKS_STRING.append(REMARKS);
			}
			// Description
			WebElement ppDesc = driver.findElement(By.xpath(locator.getProperty("purityPromise.desc").toString()));
			if (null != ppDesc && ppDesc.getText().length() > 200) {
				FLAG2 = false;
				REMARKS = "For Purity Promise: length of characters is greater than specified limit (200).";
				REMARKS_STRING.append(REMARKS);

				// throw new CharacterLengthExceededException(
				// "For Purity Promise: length of characters is greater than
				// specified limit (200)");
			}

			if (FLAG1 && FLAG2)
				STATUS = TcConstants.PASS;
			else
				STATUS = TcConstants.FAIL;
		} catch (NoSuchElementException ne) {
			STATUS = TcConstants.FAIL;
			REMARKS = "Purity Promise Not Found.";
			REMARKS_STRING.append(REMARKS);
			logger.error(REMARKS + ne.toString());
		}

		testCase.setStatus(STATUS);
		testCase.setRemarks(REMARKS_STRING.toString());
		tcList.add(testCase);
	}

	/**
	 * Method to Add Item To Cart and Checkout
	 * 
	 * @param driver
	 * @param locator
	 */
	private void addItemAndCheckout(WebDriver driver, Properties locator) {
		// Adding Item in PDP and Check Out
		logger.info("INSIDE addItemAndCheckout");
		String FUNCTIONALITY = TcConstants.PDP_FUN_XX;
		logger.info(FUNCTIONALITY);
		testCase = new TestCase();
		testCase.setTestNo(TcConstants.PDP_TC_XX);
		testCase.setFunctionality(FUNCTIONALITY);
		testCase.setMocNo(TcConstants.PDP_MOC_XX);
		String STATUS = TcConstants.FAIL;

		String REMARKS = "";

		try {
			// addItemToCart(driver, product, locator);
			WebElement btnEle = tmethods.getWE(driver, locator, "addToCart.button");
			if (null != btnEle) {
				btnEle.click();

				// gotoCart
				WebElement cartEle = tmethods.getWE(driver, locator, "cart.button");
				cartEle.click();

				// gotoCheckoutLogin
				WebElement checkoutFormEle = tmethods.getWE(driver, locator, "checkout.form");
				WebElement checkoutDiv1Ele = tmethods.getWE(driver, locator, "checkout.div1");
				WebElement checkoutDiv2Ele = tmethods.getWE(driver, locator, "checkout.div2");
				WebElement checkoutDiv3Ele = tmethods.getWE(driver, locator, "checkout.div3");
				WebElement checkoutBtnEle = tmethods.getWE(driver, locator, "checkout.btn");
				// WebElement paypalBtnEle = tmethods.getWE(driver, locator,
				// "paypal.btn");

				Actions actions = new Actions(driver);
				actions.moveToElement(checkoutFormEle);
				actions.moveToElement(checkoutDiv1Ele);
				actions.moveToElement(checkoutDiv2Ele);
				actions.moveToElement(checkoutDiv3Ele);
				actions.moveToElement(checkoutBtnEle);
				actions.click();
				actions.build().perform();
				STATUS = TcConstants.PASS;
			} else {
				logger.info("OUT OF STOCK");
				STATUS = TcConstants.PASS;
				REMARKS = "Product is OUT OF STOCK";
			}
		} catch (NoSuchElementException ee) {
			STATUS = TcConstants.FAIL;
			REMARKS = ee.getMessage();
			logger.error(ee.toString());
		} catch (WebDriverException ee) {
			STATUS = TcConstants.FAIL;
			REMARKS = ee.getMessage();
			logger.error(ee.toString());
		} catch (Exception ee) {
			STATUS = TcConstants.FAIL;
			REMARKS = ee.getMessage();
			logger.error(ee.toString());
		}

		testCase.setStatus(STATUS);
		testCase.setRemarks(REMARKS);
		tcList.add(testCase);

	}

	/**
	 * Method to test Checkout Login MOC 190
	 * 
	 * @param driver
	 * @param product
	 */
	private void checkoutLogin(WebDriver driver, Product product) {
		// Checkout Login
		logger.info("INSIDE checkoutLogin");
		String FUNCTIONALITY = TcConstants.PDP_FUN_XX;
		logger.info(FUNCTIONALITY);
		testCase = new TestCase();
		testCase.setTestNo(TcConstants.PDP_TC_XX);
		testCase.setFunctionality(FUNCTIONALITY);
		testCase.setMocNo(TcConstants.PDP_MOC_XX);
		String STATUS = TcConstants.FAIL;

		String REMARKS = "";

		String CL_TITLE = "WELCOME, FRIEND!";
		String CL_DESC = "We would like to send you a receipt and confirmation. "
				+ "If there is a Tatcha account with that address, " + "you will be asked for the password.";
		String EMAIL_LABEL = "EMAIL";
		String EMAIL = "qa.tatcha@gmail.com"; // from user object
		String EMAIL_PLACEHOLDER = "beautiful@tatcha.com";
		String OR_FB_LABEL = "Or login/register with Facebook.";

		driver.findElement(By.linkText("Checkout")).click();

		WebElement chkLoginTitle = driver.findElement(By.xpath(locator.getProperty("checkoutLogin.title").toString()));
		assertEquals(CL_TITLE, chkLoginTitle.getText());
		WebElement chkLoginDesc = driver.findElement(By.xpath(locator.getProperty("checkoutLogin.desc").toString()));
		assertEquals(CL_TITLE, chkLoginDesc.getText());
		WebElement email_label = driver
				.findElement(By.xpath(locator.getProperty("checkoutLogin.email.label").toString()));
		assertEquals(EMAIL_LABEL, email_label.getText());
		WebElement email = driver.findElement(By.xpath(locator.getProperty("checkoutLogin.emailid").toString()));
		assertEquals(EMAIL, email.getText());

		WebElement email_placeholder = driver
				.findElement(By.xpath(locator.getProperty("checkoutLogin.email.placeholder").toString()));
		// <input id="email" placeholder="beautiful@tatcha.com">

		// Or

		WebElement or_fb_lbl = driver.findElement(By.xpath(locator.getProperty("checkoutLogin.fb.label").toString()));
		assertEquals(OR_FB_LABEL, or_fb_lbl.getText());

		boolean NOT_FB = true;
		WebElement continue_btn = null;

		if (NOT_FB) {
			// CONTINUE button
			continue_btn = driver.findElement(By.xpath(locator.getProperty("checkoutLogin.continue").toString()));
		} else {
			// FB button
			continue_btn = driver.findElement(By.xpath(locator.getProperty("checkoutLogin.fb.continue").toString()));
		}
		continue_btn.click();

		// PASSWORD

		String FIRST_NAME = "qa";
		String PASSWORD = "tatcha123";

		String ENTER_PWD = "Welcome back, " + FIRST_NAME + ". Please enter your password to continue.";
		String PASSWORD_LBL = "PASSWORD";
		String FORGOT_PASSWORD_LBL = "Forgot Your Password?";

		WebElement pwdEnter = driver
				.findElement(By.xpath(locator.getProperty("checkoutLogin.password.enter").toString()));
		assertEquals(ENTER_PWD, pwdEnter.getText());

		WebElement pwdTitle = driver
				.findElement(By.xpath(locator.getProperty("checkoutLogin.password.title").toString()));
		assertEquals(PASSWORD_LBL, pwdTitle.getText());

		WebElement password = driver
				.findElement(By.xpath(locator.getProperty("checkoutLogin.password.text").toString()));
		assertEquals(PASSWORD, password.getText());

		// Forgot Your Password?
		WebElement forgotPwdEle = driver
				.findElement(By.xpath(locator.getProperty("checkoutLogin.password.forgot").toString()));
		assertEquals(FORGOT_PASSWORD_LBL, forgotPwdEle.getText());
		forgotPwdEle.click();

		if (NOT_FB) {
			// CONTINUE button
			continue_btn = driver.findElement(By.xpath(locator.getProperty("checkoutLogin.continue").toString()));
		} else {
			// FB button
			continue_btn = driver.findElement(By.xpath(locator.getProperty("checkoutLogin.fb.continue").toString()));
		}
	}

	/**
	 * Method to test Bag Page for Guest User
	 * 
	 * @param driver
	 * @param productList
	 */
	private void testBAGforGuest(WebDriver driver, List<Product> productList) {
		/** Click on Bag Cart button */

		WebDriverWait wait = (WebDriverWait) new WebDriverWait(driver, 3);
		By byeEle = By.xpath("//*[@id='mini-cart']/div[1]/a");
		wait.until(ExpectedConditions.visibilityOfElementLocated(byeEle));

		WebElement element = driver.findElement(byeEle);
		element.click();

		// WebElement clickEle =
		// driver.findElement(By.xpath("//*[@id='mini-cart']/div[1]/a/svg"));
		// clickEle.click();

		assertEquals("SHOPPING BAG", driver.findElement(By.cssSelector("h1")).getText());

		String itemCount = driver.findElement(By.cssSelector("div.badge.bag-count")).getText();
		int productCount = 0;
		if (null != itemCount) {
			productCount = Integer.parseInt(itemCount);
		}
		/** Empty Bag */
		if (productCount == 0) {
			String EMPTY_BAG_MESSAGE = "You have no items in your shopping bag.";
			assertEquals(EMPTY_BAG_MESSAGE, driver.findElement(By.cssSelector("p")).getText());
			assertEquals("Continue Shopping", driver.findElement(By.name("dwfrm_cart_continueShopping")).getText());
			driver.findElement(By.name("dwfrm_cart_continueShopping")).click();
			assertEquals("Showing 1 - 10 of 10 Results",
					driver.findElement(By.cssSelector("div.results-hits")).getText());
			driver.navigate().back();

		} else {

			// Marketing Messages
			String marketingMessage = "$20 away from getting free US shipping. | \n $50 away from a free gift with purchase.";
			try {
				String marketingText = driver.findElement(By.xpath("//form/div/div/div")).getText();
				if (null != marketingText && !marketingText.isEmpty()) {
					assertEquals(marketingMessage, marketingText);
				}
			} catch (NoSuchElementException ne) {
				logger.error("Bag-Page:No Marketing Banner " + ne.toString());
			}
			// COMPLEMENTARY SAMPLES
			String COMPLEMENTARY_MESSAGE = "CHOOSE 3 COMPLIMENTARY SAMPLES";
			String COMPLEMENTARY_HEADING = "ADD 3 COMPLIMENTARY SAMPLES WITH YOUR ORDER";
			assertEquals(COMPLEMENTARY_MESSAGE, driver.findElement(By.cssSelector("h4.slider-title")).getText());

			driver.findElement(By.id("bonusModalLink")).click();
			WebElement complementaryHeading = driver.findElement(By.cssSelector(
					"#bonusModal > div.modal-dialog.modal-lg > div.modal-content > div.modal-header > h4.modal-title > strong"));
			assertEquals(COMPLEMENTARY_HEADING, complementaryHeading.getText());
			// Cancel Samples
			driver.findElement(By.cssSelector("button.btn.btn-default")).click();
			// Adding Samples
			driver.findElement(By.id("bonusModalLink")).click();

			/**
			 * There is a confusion of how to locate Select buttons in
			 * Complementary Window
			 */

			// driver.findElement(By.xpath("//div[@id='item-59d6fb66d4ea34d749acb68399']/div[2]/label/span")).click();
			// driver.findElement(By.xpath("//div[@id='item-92a974c9cef6e84478e84b2326']/div[2]/label")).click();
			// driver.findElement(By.xpath("//div[@id='item-2ac1e7b494714cdd6b26737de5']/div[2]/label")).click();
			// driver.findElement(By.xpath("//id[contains(text(),'item-')]")).click();

			// *[@id="item-59d6fb66d4ea34d749acb68399"]/div[2]/label/span
			// #item-59d6fb66d4ea34d749acb68399 > div.sample-select > label >
			// span
			// *[@id="item-92a974c9cef6e84478e84b2326"]/div[2]/label/span
			// #item-92a974c9cef6e84478e84b2326 > div.sample-select > label >
			// span

			// driver.findElement(By.xpath("//id[contains(text(),'item-')]")).click();
			// *[@id="item-59d6fb66d4ea34d749acb68399"]/div[2]/label/span

			driver.findElement(By.id("submit-sample-items")).click();

			// PRODUCT LIST

			int index = 0;
			for (Product product : productList) {
				// checking 1st product
				// assertEquals("", driver.findElement(By.cssSelector("a >
				// img.img-responsive.product-img")).getText());
				WebElement titleEle = driver.findElement(
						By.cssSelector("h4.product-name > a[title=\"Go to Product: " + product.getName() + "\"]"));
				assertEquals(product.getName(), titleEle.getText());
				titleEle.click();
				// assertEquals("Camellia Beauty Oil subtitle for testing",
				// driver.findElement(By.cssSelector("div.product-summary-desktop
				// > h1.product-name")).getText());
				assert (driver.findElement(By.cssSelector("div.product-summary-desktop > h1.product-name")).getText()
						.contains(product.getName()));
				driver.navigate().back();

				// variants
				assertEquals("Combination", driver.findElement(By.linkText("Combination")).getText());
				assertEquals("60g / 2.1 oz.", driver.findElement(By.linkText("60g / 2.1 oz.")).getText());

				// quantity

				// driver.findElement(By.name("dwfrm_cart_updateCart")).click();
				assertEquals("Qty",
						driver.findElement(
								By.xpath("//div[@id='cart-table']/div/div[4]/div[2]/div/div[2]/div[2]/div/div/label"))
								.getText());
				WebElement quantityEle = driver
						.findElement(By.name("dwfrm_cart_shipments_i0_items_i" + (index++) + "_quantity"));
				assertEquals("1 2 3 4 5", quantityEle.getText());
				Select dropdown = new Select(quantityEle);
				dropdown.selectByVisibleText("1");
				// dropdown.selectByIndex(2);

				// assertEquals("1 2 3 4 5",
				// driver.findElement(By.name("dwfrm_cart_shipments_i0_items_i1_quantity")).getText());

				// marketing banner
				WebElement marketBannerEle = driver.findElement(By.cssSelector("div.product-marketing-banner-text"));
				String marketBannerText = "Category Specific Promotion|\n Complimentary Samples";
				assertEquals(marketBannerText, marketBannerEle.getText());

				// autodelivery
				driver.findElement(By.name("dwfrm_smartorderrefill_hasOsfSmartOrderRefill")).click();
				WebElement autodeliveryLabel = driver.findElement(By.cssSelector("div.checkbox > label"));
				assertEquals("Auto-Delivery", autodeliveryLabel.getText());
				driver.findElement(By.cssSelector("button[name=\"dwfrm_smartorderrefill_update\"]")).click();

				// autodelivery tooltip

				// ERROR: Caught exception [Error: locator strategy either id or
				// name must be specified explicitly.]
				String TOOLTIP_HEADING = "The Tatcha Replenishment Service";
				String TOOLTIP_DESC = "Enroll now and enjoy the convenience of auto-delivery and "
						+ "an exclusive gift with every shipment when you select our complimentary replenishment service. "
						+ "Update your frequency or cancel anytime by calling 1-888-739-2932 ext. 1.";
				WebElement tooltipHeadingEle = driver.findElement(By.cssSelector(
						"#sorModal > div.modal-dialog > div.modal-content > div.modal-header > h4.modal-title > strong"));
				WebElement tooltipDescEle = driver.findElement(
						By.cssSelector("#sorModal > div.modal-dialog > div.modal-content > div.modal-body"));
				assertEquals(TOOLTIP_HEADING, tooltipHeadingEle.getText());
				assertEquals(TOOLTIP_DESC, tooltipDescEle.getText());
				WebElement tooltipCloseBtn = driver.findElement(By.cssSelector(
						"#sorModal > div.modal-dialog > div.modal-content > div.modal-header > button.close"));
				tooltipCloseBtn.click();

				// ERROR: Caught exception [Error: locator strategy either id or
				// name must be specified explicitly.]
				driver.findElement(By.id("sorModal")).click();

				// frequency
				WebElement freequencyEle = driver.findElement(By.name("dwfrm_smartorderrefill_hasOsfSmartOrderRefill"));
				// String days = "Every 30 days";
				// String days = "Every 60 days";
				String days = "Every 90 days";
				new Select(freequencyEle).selectByVisibleText(days);
				// new
				// Select(driver.findElement(By.name("dwfrm_smartorderrefill_OsfSorSmartOrderRefillInterval"))).selectByVisibleText("Every
				// 90 days");

				// price
				assertEquals(product.getPrice(), driver.findElement(By.cssSelector("span.price-total")).getText());
				// assertEquals("$65.00",
				// driver.findElement(By.cssSelector("span.price-total")).getText());

				// buy 3 get 5% off

				assertEquals("$285.00", driver.findElement(By.cssSelector("span.price-unadjusted > span")).getText());
				assertEquals("$256.50",
						driver.findElement(By.cssSelector("span.price-adjusted-total > span")).getText());

				// click on product img or variants navigates to PDP
				driver.findElement(By.cssSelector("a > img.img-responsive.product-img")).click();
				assertEquals(product.getName(),
						driver.findElement(By.cssSelector("div.product-summary-desktop > h1.product-name")).getText());
				// assertEquals("Classic Rice Enzyme Powder",
				// driver.findElement(By.cssSelector("div.product-summary-desktop
				// > h1.product-name")).getText());
				driver.navigate().back();

				driver.findElement(By.linkText("Combination")).click();
				assertEquals(product.getName(),
						driver.findElement(By.cssSelector("div.product-summary-desktop > h1.product-name")).getText());
				// assertEquals("Classic Rice Enzyme Powder",
				// driver.findElement(By.cssSelector("div.product-summary-desktop
				// > h1.product-name")).getText());
				driver.navigate().back();

				driver.findElement(By.linkText("60g / 2.1 oz.")).click();
				assertEquals(product.getName(),
						driver.findElement(By.cssSelector("div.product-summary-desktop > h1.product-name")).getText());
				// assertEquals("Classic Rice Enzyme Powder",
				// driver.findElement(By.cssSelector("div.product-summary-desktop
				// > h1.product-name")).getText());
				driver.navigate().back();

			} // End of Product List Iteration in Bag Page

			// checking Error Message
			String ERROR_MESSAGE = "Your cart contains one or more smart refill products. "
					+ "Please login or create a new account to continue checkout process.";
			WebElement errorMsgEle = driver.findElement(By.cssSelector("p"));
			assertEquals(ERROR_MESSAGE, errorMsgEle.getText());
			driver.findElement(By.cssSelector("button[name=\"dwfrm_smartorderrefill_update\"]")).click();
			// ERROR: Caught exception [Error: Dom locators are not implemented
			// yet!]
			driver.findElement(By.cssSelector("button[name=\"dwfrm_smartorderrefill_update\"]")).click();
			// ERROR: Caught exception [Error: Dom locators are not implemented
			// yet!]
			driver.findElement(By.cssSelector("button[name=\"dwfrm_smartorderrefill_update\"]")).click();
			// ERROR: Caught exception [Error: Dom locators are not implemented
			// yet!]

			// GIFT WRAP

			String GIFT_HEADING = "Tatcha Gift Wrapping";
			WebElement giftEle = driver.findElement(By.cssSelector("div.col-xs-9 > h4"));
			assertEquals(GIFT_HEADING, giftEle.getText());
			// gift wrap tooltip missing
			// ERROR: Caught exception [Error: locator strategy either id or
			// name must be specified explicitly.]

			// adding Gift wrap and checking $5 added ?
			driver.findElement(By.id("giftwrap-toggle")).click();
			driver.findElement(By.id("add-giftwrap-button")).click();
			WebElement giftedAmountEle = driver
					.findElement(By.xpath("//div[@id='cart-table']/div[2]/div/div[2]/table[2]/tbody/tr/td"));
			double giftedAmount = subTotal + 5.00;
			assertEquals("$" + giftedAmount, giftedAmountEle.getText());
			// removing Gift wrap and checking $5 removed
			driver.findElement(By.id("giftwrap-toggle")).click();
			driver.findElement(By.id("remove-giftwrap-button")).click();
			giftedAmount = subTotal - 5.00;
			assertEquals("$" + giftedAmount, giftedAmountEle.getText());

			// checking hand written message
			String GIFT_FROM = "Jack";
			String GIFT_TO = "Rose";
			String GIFT_MSG = "Best Wishes";

			driver.findElement(By.id("giftwrap-toggle")).click();
			driver.findElement(By.id("add-giftwrap-button")).click();
			driver.findElement(By.id("hasGiftMessage")).click();
			driver.findElement(By.cssSelector("div.checkbox.gift-message  > label")).click();
			assertEquals("From", driver
					.findElement(By.cssSelector("div.col-sm-6 > div.form-group > label.control-label")).getText());
			driver.findElement(By.id("giftFrom")).clear();
			driver.findElement(By.id("giftFrom")).sendKeys(GIFT_FROM);
			assertEquals("To",
					driver.findElement(By.xpath("//div[@id='gift-message-form']/div/div[2]/div/label")).getText());
			driver.findElement(By.id("giftTo")).clear();
			driver.findElement(By.id("giftTo")).sendKeys(GIFT_TO);
			assertEquals("Message", driver
					.findElement(By.cssSelector("div.col-xs-12 > div.form-group > label.control-label")).getText());
			driver.findElement(By.id("giftMessage")).clear();
			driver.findElement(By.id("giftMessage")).sendKeys(GIFT_MSG);
			// End of GIFT WRAP

			// SUMMARY
			assertEquals("Summary", driver.findElement(By.cssSelector("h2.panel-title")).getText());
			assertEquals(prodList.size() + " items", driver.findElement(By.cssSelector("div.panel-status")).getText());
			assertEquals("Subtotal", driver.findElement(By.cssSelector("th.data-label")).getText());
			assertEquals("$" + subTotal, driver.findElement(By.cssSelector("td")).getText());
			/** Tax Calculations Doubtful */
			assertEquals("Shipping", driver
					.findElement(By.xpath("//div[@id='cart-table']/div[2]/div/div[2]/table/tbody/tr[2]/th")).getText());
			assertEquals("TBD", driver
					.findElement(By.xpath("//div[@id='cart-table']/div[2]/div/div[2]/table/tbody/tr[2]/td")).getText());
			assertEquals("Tax", driver
					.findElement(By.xpath("//div[@id='cart-table']/div[2]/div/div[2]/table/tbody/tr[3]/th")).getText());
			assertEquals("TBD", driver
					.findElement(By.xpath("//div[@id='cart-table']/div[2]/div/div[2]/table/tbody/tr[3]/td")).getText());

			// checking Promcode deducting amount
			String PROMOCODE_LIMIT_MSG = "Limit one promotional code per order\\.$";
			String PROMOCODE_COUPEN = "TATCHANEW";
			String PROMOCODE_ERROR_MESSAGE = "Invalid coupon code";
			WebElement promoCodeLabelEle = driver
					.findElement(By.cssSelector("div.checkout-promo-code > div.form-group > label.control-label"));
			assertEquals("Promo Code", promoCodeLabelEle.getText());

			assertTrue(driver.findElement(By.cssSelector("span.help-block")).getText()
					.matches("^exact:[\\s\\S]*" + PROMOCODE_LIMIT_MSG));
			driver.findElement(By.id("dwfrm_cart_couponCode")).clear();
			driver.findElement(By.id("dwfrm_cart_couponCode")).sendKeys(PROMOCODE_COUPEN);
			driver.findElement(By.id("add-coupon")).click();
			WebElement promocodeErrorMsgEle = driver
					.findElement(By.xpath("//html[@id='ext-gen45']/body/main/div/div/div"));
			assertEquals(PROMOCODE_ERROR_MESSAGE, promocodeErrorMsgEle.getText());
			driver.findElement(By.id("dwfrm_cart_couponCode")).clear();
			PROMOCODE_COUPEN = "TATCHA";
			driver.findElement(By.id("dwfrm_cart_couponCode")).sendKeys(PROMOCODE_COUPEN);
			driver.findElement(By.id("add-coupon")).click();
			double DISCOUNT_AMOUNT = 10.00;
			try {
				assertEquals("Discount",
						driver.findElement(By.xpath("//div[@id='cart-table']/div[2]/div/div[2]/table/tbody/tr[2]/th"))
								.getText());
				assertEquals("$" + DISCOUNT_AMOUNT,
						driver.findElement(By.xpath("//div[@id='cart-table']/div[2]/div/div[2]/table/tbody/tr[2]/td"))
								.getText());
			} catch (NoSuchElementException ne) {

			}

			assertEquals("Estimated Total", driver
					.findElement(By.xpath("//div[@id='cart-table']/div[2]/div/div[2]/table[2]/tbody/tr/th")).getText());
			assertEquals("$316.50", driver
					.findElement(By.xpath("//div[@id='cart-table']/div[2]/div/div[2]/table[2]/tbody/tr/td")).getText());

			// checking Error message
			assertEquals(ERROR_MESSAGE, driver.findElement(By.cssSelector("p")).getText());
			// removing items
			driver.findElement(By.name("dwfrm_cart_shipments_i0_items_i0_deleteProduct")).click();
			driver.findElement(By.name("dwfrm_cart_shipments_i0_items_i0_deleteProduct")).click();
			// checkout
			driver.findElement(By.name("dwfrm_cart_checkoutCart")).click();
			// if Guest user
			String USERNAME = "neetu@gmail.com";
			String PASSWORD = "nnnnnnnn";
			driver.findElement(By.id("dwfrm_login_username")).clear();
			driver.findElement(By.id("dwfrm_login_username")).sendKeys(USERNAME);
			driver.findElement(By.id("dwfrm_login_password")).clear();
			driver.findElement(By.id("dwfrm_login_password")).sendKeys(PASSWORD);
			driver.findElement(By.name("dwfrm_login_login")).click();
			// Checkout Page
			String SHIPPING_TITLE = "Select or Enter Shipping Address";
			assert (driver.findElement(By.cssSelector("legend")).getText().contains(SHIPPING_TITLE));
			driver.navigate().back();
		}
		// End of SUMMARY

		// YOU MAY ALSO LIKE - Doubtful
		String YOU_LIKE = "You Might Also Like";
		WebElement youlikeEle = driver.findElement(By.cssSelector("h2.slider-title"));
		assertEquals(YOU_LIKE, youlikeEle.getText());
		String[] likeProductNames = { "10% of your order total will be donated to the Asian Art Museum",
				"Plump + Protect Set with Enriching Renewal Cream", "Ageless Radiance Duo", "Camellia Beauty Oil" };
		for (int i = 0, j = 5; j < 12; i++, j = j + 2) {
			assertEquals("", driver.findElement(By.cssSelector("img[alt='" + likeProductNames[i] + "']")).getText());
			driver.findElement(By.xpath("(//button[@type='submit'])[" + j + "]")).click();
			driver.navigate().back();
		}

	}

	private boolean elementPresent(String[] elementArray, String element) {
		boolean status = false;
		for (String ele : elementArray) {
			if (ele.equals(element)) {
				return true;
			}
		}
		return false;
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
