package com.tatcha.jscripts.product;

import javax.activity.InvalidActivityException;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.tatcha.jscripts.dao.Product;
import com.tatcha.utils.BrowserDriver;
import com.tatcha.utils.ClassUtils;
import com.xceptance.xlt.api.engine.scripting.AbstractWebDriverScriptTestCase;
import com.xceptance.xlt.api.webdriver.XltDriver;

public class AddItemToCart extends AbstractWebDriverScriptTestCase {

	public AddItemToCart() {
		super(driver, baseUrl);
		// new AddItemToCart(driver,baseUrl);
		// TODO Auto-generated constructor stub
	}

	// public AddItemToCart(WebDriver driver, String baseUrl) {
	// super(driver, baseUrl);
	// // TODO Auto-generated constructor stub
	// }

	private static WebDriver driver = new XltDriver();
//	private static WebDriver driver = BrowserDriver.getChromeWebDriver();
	private static String baseUrl = BrowserDriver.BASE_URL;
	private boolean acceptNextAlert = true;
	private StringBuffer verificationErrors = new StringBuffer();
	private ArrayList<Product> prodList = new ArrayList<Product>();
	private ArrayList<Product> prodEqualList = new ArrayList<Product>();
	private double subTotal = 0;
	private int productId = 10001;

	private final static Logger logger = Logger.getLogger(AddItemToCart.class);
	private Properties locator = new Properties();
	private Properties prop = new Properties();
	private boolean loadTestFlag = true;

	@Before
	public void setUp() {
		// driver = new FirefoxDriver();
		// baseUrl = "https://development-na01-tatcha.demandware.net/";
		// if(!loadTestFlag){
		// driver = new XltDriver();
		// }else{
		// driver = BrowserDriver.getChromeWebDriver();
		// }
//		baseUrl = BrowserDriver.BASE_URL;
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

	}

	/**
	 * For testing POM Dynamic attributes
	 * 
	 * @throws IOException
	 */
	private void testMavenDynamicProperties() throws IOException {
		logger.info("inside testMavenDynamicProperties");
		java.io.InputStream is = this.getClass().getResourceAsStream("QA.properties");
		java.util.Properties p = new Properties();
		p.load(is);
		String username = p.getProperty("login.username");
		String password = p.getProperty("login.password");
		logger.info("username:" + username);
		logger.info("password:" + password);
	}

	@Test
	public void testAddItemToCart() throws Exception {
		// driver.get(baseUrl +
		// "/on/demandware.store/Sites-tatcha-Site/default/Login-Show?original=%2fs%2ftatcha%2faccount%3flang%3ddefault");
		driver.get(baseUrl);

		// testMavenDynamicProperties();

		locator.load(new FileInputStream(getClass().getResource("/elementLocator.properties").getFile()));
		prop.load(new FileInputStream(getClass().getResource("/tatcha.properties").getFile()));

		String[] productsNames = prop.getProperty("product.names").toString().split("\\|");
		logger.info("Lengh: " + productsNames.length);
		for (String productName : productsNames) {

			/** Find product by Search Filter */
			// create product
			Product product = createProduct(productName.trim());
			// hoverShopAll(driver, product.getName());
			 searchProduct(driver, product.getName(), locator);
			// addItemToCart(driver, product, locator);
			// gotoCart(driver, product, locator);
			// gotoCheckoutLogin(driver, product, locator);

		}
	}

	/**
	 * Hovering Shop All Menu Link
	 * 
	 * @param driver
	 * @param productName
	 */
	private void hoverShopAll(WebDriver driver, String productName) {
		/** Find product by hovering shop all - works only for Page one */
		WebDriverWait wait = (WebDriverWait) new WebDriverWait(driver, 3);
		Actions action = new Actions(driver);
		wait.until(ExpectedConditions.visibilityOfElementLocated((By.linkText("SHOP"))));
		try {
			WebElement element = driver.findElement(By.linkText("SHOP"));
			action.moveToElement(element).build().perform();
			driver.findElement(By.linkText("Shop All")).click();
			/**
			 * now only the first page items are selected since PROD page not in
			 * this sprint
			 */

			driver.findElement(By.cssSelector("img[alt=\"" + productName + "\"]")).click();

		} catch (NoSuchElementException ne) {
			logger.error("No Element >> " + ne.toString());
		}

	}

	/**
	 * Create Product object
	 * 
	 * @param productName
	 * @return
	 */
	private Product createProduct(String productName) {
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
	 * Add Each Item to Cart
	 * 
	 * @param driver
	 * @param product
	 * @param locator
	 */
	private void addItemToCart(WebDriver driver, Product product, Properties locator) {

		/** Adding to Cart */
		// WebElement cartEle =
		// driver.findElement(By.xpath("//*[@id='add-to-cart']"));
		WebElement addItemButton = driver.findElement(By.xpath(locator.getProperty("addToCart.button").toString()));
		addItemButton.click();

	}

	/**
	 * Go to Bag Page
	 * 
	 * @param driver
	 * @param product
	 * @param locator
	 */
	private void gotoCart(WebDriver driver, Product product, Properties locator) {
		// findElement(driver,"cart.button",locator);
		findElement(driver, "cart.popup", locator);
		findElement(driver, "checkout.button", locator);
	}

	/**
	 * Find a waiting Element
	 * 
	 * @param driver
	 * @param keyElement
	 * @param locator
	 */
	private void findElement(WebDriver driver, String keyElement, Properties locator) {
		try {
			WebDriverWait wait = (WebDriverWait) new WebDriverWait(driver, 3);
			By byeEle = By.xpath(locator.getProperty(keyElement).toString());
			wait.until(ExpectedConditions.visibilityOfElementLocated(byeEle));

			WebElement element = driver.findElement(byeEle);
			wait.until(ExpectedConditions.visibilityOf(element));
			// Thread.sleep(1000);
			element.click();

		} catch (WebDriverException we) {
			logger.error(we);
			// we.printStackTrace();
		}
		// catch (InterruptedException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
	}

	/**
	 * Search Filter
	 * 
	 * @throws ProductNotFoundException
	 */
	private void searchProduct(WebDriver driver, String productName, Properties locator) {
		try {
			
			type(locator.getProperty("search.box").toString(), productName);
			checkAndWait(locator.getProperty("search.box").toString());
			pause(5000);
//			click(locator.getProperty("search.box").toString(), Keys.RETURN);
			
//			 WebElement searchEle =
//			 driver.findElement(By.xpath("//*[@id='q']"));
			
			
//			WebElement searchEle = driver.findElement(By.xpath(locator.getProperty("search.box").toString()));
//
//			searchEle.sendKeys(productName);
//			searchEle.sendKeys(Keys.RETURN);
//
//			if (productListingPage(driver, productName, locator) > 1) {
//				logger.info("Product has more than one variants");
//			}

		} catch (NoSuchElementException ne) {
			logger.error("Product not found" + ne);
			// throw new ProductNotFoundException("Product not found");
		}
	}

	/**
	 * Check whether product has variants
	 * 
	 * @param driver
	 * @param productName
	 * @param locator
	 * @return
	 */
	private int productListingPage(WebDriver driver, String productName, Properties locator) {
		// <div class="product-tile product-list-unit"
		// div[contains(@class,'product-list-unit')
		List<WebElement> searchEleList = null;
		try {
			searchEleList = driver.findElements(By.xpath(locator.getProperty("product.listing").toString()));
			for (WebElement searchEle : searchEleList) {
				searchEle.click();
			}
		} catch (NoSuchElementException ne) {
			return 1;
		}

		return searchEleList.size();
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
		logger.info("Testing OVer");
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
