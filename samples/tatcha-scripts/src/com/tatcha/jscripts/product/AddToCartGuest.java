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

import com.tatcha.jscripts.commons.TestMethods;
import com.tatcha.jscripts.dao.Product;
import com.tatcha.utils.BrowserDriver;
import com.tatcha.utils.ClassUtils;

public class AddToCartGuest {
	private static WebDriver driver;
	private String baseUrl;
	private boolean acceptNextAlert = true;
	private StringBuffer verificationErrors = new StringBuffer();
	private ArrayList<Product> prodList = new ArrayList<Product>();
	private ArrayList<Product> prodEqualList = new ArrayList<Product>();
	private double subTotal = 0;
	private int productId = 10001;

	private final static Logger logger = Logger.getLogger(AddToCartGuest.class);
	private Properties locator = new Properties();
	private Properties prop = new Properties();
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
	public void testAddToCartGuest() throws Exception {
		// driver.get(baseUrl +
		// "/on/demandware.store/Sites-tatcha-Site/default/Login-Show?original=%2fs%2ftatcha%2faccount%3flang%3ddefault");
		driver.get(baseUrl);

		// testMavenDynamicProperties();

		locator.load(new FileInputStream(getClass().getResource("/elementLocator.properties").getFile()));
		prop.load(new FileInputStream(getClass().getResource("/tatcha.properties").getFile()));

		String[] productsNames = prop.getProperty("product.names").toString().split("\\|");

		for (String productName : productsNames) {

			/** Find product by Search Filter */
			// create product
			Product product = createProduct(productName.trim());
			// hoverShopAll(driver, product.getName());
			searchProduct(driver, product.getName());
			addItemToCart(driver, product, locator);
			gotoCart(driver, product, locator);
			gotoCheckoutLogin(driver, product, locator);

			// going to PDP
			// testPDPforGuest(driver, product, locator);
			// testBAGforGuest(driver, prodList);

		}

		// *[@id='mini-cart']/div[1]/a/svg

		// Mini Cart item count
		String itemCount = driver.findElement(By.cssSelector("div.badge.bag-count")).getText();
		logger.info("Item Count" + itemCount);
		// assertEquals(prodCount, itemCount + "");

		// Going to Bag Page
		driver.findElement(By.linkText("1")).click();
		driver.findElement(By.name("dwfrm_cart_shipments_i0_items_i0_deleteProduct")).click();

		logger.info("subTotal " + subTotal);

		/** ----------------------- Mini Cart Checkings ------------------- */
		// checkMiniCart(driver, prodList, locator);

		// driver.findElement(By.linkText("Select Free Samples")).click();
		// assertEquals("SHOPPING BAG",
		// driver.findElement(By.cssSelector("h1")).getText());

		// driver.findElement(By.linkText("Checkout")).click();
		// assertEquals("Select an Address",
		// driver.findElement(By.cssSelector("div.select-address.form-row >
		// label")).getText());
		// click to Bag Page
		// ERROR: Caught exception [Error: locator strategy either id or name
		// must be specified explicitly.]
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
			// searchProduct(driver,productName);
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
	 * Check Mini Cart
	 */
	private void checkMiniCart(WebDriver driver, List<Product> prodList, Properties locator) {
		WebDriverWait wait = (WebDriverWait) new WebDriverWait(driver, 3);
		Actions action = new Actions(driver);
		int cartItem = 1;
		prodList.size();
		for (Product prod : prodList) {
			try {
				/** Hovering over mini cart */
				By byeEle = By.xpath("//*[@id='mini-cart']/div[1]/a");
				wait.until(ExpectedConditions.visibilityOfElementLocated(byeEle));

				WebElement element = driver.findElement(byeEle);
				action.moveToElement(element).build().perform();
				// assertEquals(product.getName(),driver.findElement(By.linkText(product.getName())).getText());
				// assert(driver.findElement(By.cssSelector("div.dropdown-bag-item-qty-price")).getText().contains(product.getPrice()));
				driver.findElement(By.xpath("//*[@id='mini-cart']/div[2]/div[2]/div[" + cartItem + "]/div[2]/h5/a"))
						.click();
				cartItem++;
				/** PDP Page */
				String productNamePDP = driver.findElement(By.xpath("//*[@id='product-content']/div[2]/h1")).getText();
				String productPricePDP = driver
						.findElement(By.xpath("//*[@id='product-content']/div[2]/div/div[1]/span")).getText();

				if (prodEqualList.contains(prod)) {
					assertEquals(prod.getName(), productNamePDP);
					assertEquals(prod.getPrice(), productPricePDP);
				}

				// assertEquals("Qty: 1 | $85.50",
				// driver.findElement(By.cssSelector("div.dropdown-bag-item-qty-price")).getText());

				// CTA Checkout
				checkoutLogin(driver, prod);

			} catch (NoSuchElementException ne) {
				logger.error("No Element >> " + ne.toString());
			}

		}
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

	// *[@id="primary"]/div[1]/div/h1

	/**
	 * Go to Bag Page
	 * 
	 * @param driver
	 * @param product
	 * @param locator
	 */
	private void gotoCart(WebDriver driver, Product product, Properties locator) {
		WebDriverWait wait = (WebDriverWait) new WebDriverWait(driver, 3);
		By byeEle = By.xpath("//*[@id='mini-cart']/div[1]/a");
		wait.until(ExpectedConditions.visibilityOfElementLocated(byeEle));

		WebElement element = driver.findElement(byeEle);
		element.click();

	}

	private void gotoCheckoutLogin(WebDriver driver, Product product, Properties locator) {

		// WebElement checkoutButton = driver.findElement(By.xpath(""));
		// checkoutButton.click();

		WebDriverWait wait = (WebDriverWait) new WebDriverWait(driver, 3);
		// By byeEle =
		// By.xpath("//*[@id='cart-table']/div[2]/div/div[2]/button");
		// By byeEle = By.xpath("//*[contains(@id,'cart-table')]");
		By checkout = By.xpath("//*[contains(@type,'submit') and contains(@value,'Checkout')]");
		wait.until(ExpectedConditions.visibilityOfElementLocated(checkout));

		WebElement element = driver.findElement(checkout);
		element.click();

		// *[@id="cart-table"]/div[2]/div/div[2]/button
		// #cart-table > div.col-md-4 > div > div.panel-body > button

		// *[@id="cart-table"]/div[2]/div/div[2]/button
		// <button class="btn btn-primary btn-lg btn-block" type="submit"
		// value="Checkout" name="dwfrm_cart_checkoutCart">
		// Checkout
		// </button>
	}

	/**
	 * Search Filter
	 * 
	 * @throws ProductNotFoundException
	 */
	private void searchProduct(WebDriver driver, String productName) {
		try {
			WebElement searchEle = driver.findElement(By.xpath("//*[@id='q']"));
			searchEle.sendKeys(productName);
			searchEle.sendKeys(Keys.RETURN);
			// searchEle.sendKeys(Keys.ENTER);

			// WebElement.sendKeys(Keys.RETURN);
			// WebElement searchBtn =
			// driver.findElement(By.xpath("//*[@id='navigation']/ul[2]/li[1]/form/div/span/svg"));
			// searchBtn.click();
		} catch (NoSuchElementException ne) {
			logger.info("Product not found");
		}
	}

	/** MOC 190 */
	private void checkoutLogin(WebDriver driver, Product product) {
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

	private void testBAGforGuest(WebDriver driver, List<Product> productList) throws Exception {
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
