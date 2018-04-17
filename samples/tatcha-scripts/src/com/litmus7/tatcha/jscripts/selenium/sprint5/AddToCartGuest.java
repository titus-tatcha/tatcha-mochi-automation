package com.litmus7.tatcha.jscripts.selenium.sprint5;

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

import com.litmus7.tatcha.jscripts.dob.Product;
import com.litmus7.tatcha.jscripts.selenium.exception.CharacterLengthExceededException;
import com.litmus7.tatcha.jscripts.selenium.exception.InvalidElementException;
import com.litmus7.tatcha.jscripts.selenium.exception.ProductNotFoundException;
import com.litmus7.tatcha.utils.BrowserDriver;
import com.litmus7.tatcha.utils.ClassUtils;

public class AddToCartGuest {
	private WebDriver driver;
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

	@Before
	public void setUp() throws Exception {
		// driver = new FirefoxDriver();
		// baseUrl = "https://development-na01-tatcha.demandware.net/";
		driver = BrowserDriver.getChromeWebDriver();
		baseUrl = BrowserDriver.STAGE_URL;
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
//			hoverShopAll(driver, product.getName());
			searchProduct(driver, product.getName());
			addItemToCart(driver, product, locator);
			gotoCart(driver, product, locator);
			gotoCheckoutLogin(driver, product, locator);
			
			// going to PDP
			// testPDPforGuest(driver, product, locator);
			// testBAGforGuest(driver, prodList);
			
		}

		
		//*[@id='mini-cart']/div[1]/a/svg
		
		// Mini Cart item count
		String itemCount = driver.findElement(By.cssSelector("div.badge.bag-count")).getText();
		logger.info("Item Count" + itemCount);
		// assertEquals(prodCount, itemCount + "");

		// Going to Bag Page
		driver.findElement(By.linkText("1")).click();
		driver.findElement(By.name("dwfrm_cart_shipments_i0_items_i0_deleteProduct")).click();

		logger.info("subTotal " + subTotal);

		/** ----------------------- Mini Cart Checkings ------------------- */
//		checkMiniCart(driver, prodList, locator);

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
	 * @param driver
	 * @param product
	 * @param locator
	 */
	private void addItemToCart(WebDriver driver, Product product, Properties locator) {
		
		/** Adding to Cart */
//		WebElement cartEle = driver.findElement(By.xpath("//*[@id='add-to-cart']"));
		WebElement addItemButton = driver.findElement(By.xpath(locator.getProperty("addToCart.button").toString()));
		addItemButton.click();
		
	}
	
	//*[@id="primary"]/div[1]/div/h1
	

	/**
	 * Go to Bag Page
	 * @param driver
	 * @param product
	 * @param locator
	 */
	private void gotoCart(WebDriver driver, Product product, Properties locator){	
		WebDriverWait wait = (WebDriverWait) new WebDriverWait(driver, 3);
		By byeEle = By.xpath("//*[@id='mini-cart']/div[1]/a");
		wait.until(ExpectedConditions.visibilityOfElementLocated(byeEle));

		WebElement element = driver.findElement(byeEle);
		element.click();
		
	}
	
	private void gotoCheckoutLogin(WebDriver driver, Product product, Properties locator){
		
//		WebElement checkoutButton = driver.findElement(By.xpath(""));
//		checkoutButton.click();
		
		WebDriverWait wait = (WebDriverWait) new WebDriverWait(driver, 3);
//		By byeEle = By.xpath("//*[@id='cart-table']/div[2]/div/div[2]/button");
//		By byeEle = By.xpath("//*[contains(@id,'cart-table')]");
		By checkout = By.xpath("//*[contains(@type,'submit') and contains(@value,'Checkout')]");
		wait.until(ExpectedConditions.visibilityOfElementLocated(checkout));

		WebElement element = driver.findElement(checkout);
		element.click();
		
		//*[@id="cart-table"]/div[2]/div/div[2]/button 
//		#cart-table > div.col-md-4 > div > div.panel-body > button
		
		//*[@id="cart-table"]/div[2]/div/div[2]/button
//		<button class="btn btn-primary btn-lg btn-block" type="submit" value="Checkout" name="dwfrm_cart_checkoutCart">
//		Checkout
//		</button>
	}
	/**
	 * Search Filter
	 * 
	 * @throws ProductNotFoundException
	 */
	private void searchProduct(WebDriver driver, String productName) throws ProductNotFoundException {
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
			throw new ProductNotFoundException("Product not found");
		}
	}

	/**
	 * Checking PDP in detail
	 * 
	 * @throws InvalidElementException
	 * @throws CharacterLengthExceededException
	 */
	private void testPDPforGuest(WebDriver driver, Product product, Properties locator)
			throws InvalidElementException, CharacterLengthExceededException {

		// Marketing Flags

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
				throw new InvalidElementException("Marketing Flag 1 is invalid");
			}

			if (!flag2Validity) {
				throw new InvalidElementException("Marketing Flag 2 is invalid");
			}

		} catch (NoSuchElementException ne) {
			/** No marketing banner */
			logger.error(" No Marketing Banner !!! ");
		}

		// Product Title
		try {
			WebElement prodTitleEle = driver
					.findElement(By.cssSelector("div.product-summary-desktop > h1.product-name"));
			String prodTitle = prodTitleEle.getText().toLowerCase();
			assert (prodTitle.contains(product.getName().toLowerCase()));
		} catch (NoSuchElementException ne) {
			logger.error(" No Title !!! " + ne.toString());
		}
		// Subtitle
		try {
			WebElement prodsubtitleEle = driver.findElement(
					By.cssSelector("div.product-summary-desktop > h1.product-name > span.product-subtitle"));
			product.setSubtitle(prodsubtitleEle.getText());
		} catch (NoSuchElementException ne) {
			logger.error(" No Subtitle !!! " + ne.toString());
		}
		// assertEquals(product.getSubtitle().toLowerCase(),
		// prodsubtitleEle.getText().toLowerCase());
		// assertEquals("Tatcha",
		// driver.findElement(By.cssSelector("div.product-summary-desktop >
		// h1.product-name > span.product-subtitle")).getText());

		// Product Price
		String SPLITTER = "-";
		String productPrice = driver.findElement(By.xpath(locator.getProperty("product.price").toString())).getText();
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
		}

		// Skin Type
		// *[@id='product-content']/div[3]/div[1]/div[2]/div/div/div/div/div[1]/a
		// *[@id='product-content']/div[3]/div[1]/div[2]/div/div/div/div/div[2]/a
		// *[@id='product-content']/div[3]/div[1]/div[2]/div/div/div/div/div[3]/a
		// *[@id='product-content']/div[3]/div[1]/div[2]/div/div/div/div/div[4]/a

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
			logger.error(" No Skin Variant !!! " + ne.toString());
		}

		// Size
		// *[@id='product-content']/div[3]/div[2]/div[2]/div/div/div/div/div[1]/a
		// *[@id='product-content']/div[3]/div[2]/div[2]/div/div/div/div/div[2]/a

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
			logger.error(" No Size Varient !!! " + ne.toString());
		}

		/** Raising Invalid Skin & Size Variants as Exceptions */

		if (!skinValidity) {
			throw new InvalidElementException("Skin Variant is invalid");
		}
		if (!sizeValidity) {
			throw new InvalidElementException("Size Variant is invalid");
		}

		// Quantity Notification Msg
		boolean notifyQty = false;
		try {
			String notifyMessage = "Only A Few Left";
			WebElement notifyMsgEle = driver.findElement(By.cssSelector("div.product-inventory-flag"));
			// *[@id="dwfrm_product_addtocart_d0ctugdkwyxu"]/fieldset/div/div[1]
			// //id[contains(text(),My Account')]
			String notifyMsg = notifyMsgEle.getText();
			if (notifyMessage.toLowerCase().equals(notifyMsg.toLowerCase())) {
				notifyQty = true;
			}

			// *[@id="dwfrm_product_addtocart_d0rpopaqcraa"]/fieldset/div/div[2]/div/h2

		} catch (NoSuchElementException ne) {
			notifyQty = true;
			logger.error(" No Notify Message !!! " + ne.toString());
		}

		// Throw exception if quantity notify msg is invalid
		if (!notifyQty) {
			throw new InvalidElementException("Quantity Notification Message is invalid");
		}

		// Quantity DDL
		try {
			Select dropdown = new Select(driver.findElement(By.id("Quantity")));
			dropdown.selectByVisibleText("1");
			// dropdown.selectByIndex(2);
			// assertEquals("1 2 3 4 5",
			// driver.findElement(By.id("Quantity")).getText());
		} catch (NoSuchElementException ne) {
			logger.error("No Quantity DDL : " + ne.toString());

		}

		// Limit Quantity Message
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

		// Throw exception if limit quantity msg is invalid
		if (!limitMessageValidity) {
			throw new InvalidElementException("Limit Quantity Message is invalid");
		}

		/** Calculating Subtotal */
		if (productPrice.contains("-")) {

		} else {
			String amount = productPrice.trim().substring(1);
			logger.info("amount " + amount);
			subTotal += Double.parseDouble(amount);
		}

		/** Updating Product Object */
		// product.setPrice(productPrice);
		prodList.add(product);

		// ADD-TO-CART // SOLD-OUT // COMMING-SOON

		try {
			/** Adding Product to Mini Cart */
			driver.findElement(By.id("add-to-cart")).click();
		} catch (NoSuchElementException ne) {
			// *[@id="dwfrm_product_addtocart_d0wihrksaygc"]/fieldset/div/div[2]/div/h2
			// driver.findElement(By.id("add-to-cart")).click();
			logger.error(" SOLD OUT or COMMING SOON ");
		}

		// Reviews
		// assertEquals("9,999 Reviews",
		// driver.findElement(By.cssSelector("div.product-summary-desktop >
		// div.product-price-block > div.product-rating-summary >
		// a.tatcha-animation.")).getText());
		try {
			// WebElement reviewEle =
			// driver.findElement(By.xpath("//*[@id='product-content']/div[2]/div[2]/div[2]/a"));
			WebElement reviewEle = driver.findElement(By.xpath(locator.getProperty("product.reviews").toString()));

			String reviews = reviewEle.getText();
			assertEquals("9,999 Reviews", reviews);

		} catch (NoSuchElementException ne) {
			logger.error("Reviews Not Found " + ne.toString());
			// imgValidity = false;
			// throw ne;
		}

		// Main Image & Thumbnails
		// boolean imgValidity = false;
		try {
			WebElement imgMainEle = driver.findElement(By.xpath(locator.getProperty("product.mainImage").toString()));
			// imgValidity = true;
			// imgMainEle.click();
		} catch (NoSuchElementException ne) {
			logger.error("Image Thumbnails Not Found " + ne.toString());
			// imgValidity = false;
			// throw ne;
		}

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

		/** Testing Sprint 5 stories - MOC 203,204,205,206,207,208,211 */
		testRecommendedFor(driver, product, locator);
		testWhatItis(driver, product, locator);
		testWhyItWorks(driver, product, locator);
		testIngredient(driver, product, locator);
		testHadasei(driver, product, locator);
		testHowToUse(driver, product, locator);
		// Reviews & QA is missing - need to add
		testPurityPromise(driver, product, locator);
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

	/** MOC 203 */
	private void testRecommendedFor(WebDriver driver, Product product, Properties locator) {
		logger.info("------testRecommendedFor-------");

		try {
			// Benefits - Recommended For
			String BENEFITS = "Benefits";
			String RECOMMENDED_FOR = "Recommended For";

			WebElement benefitsTitle = driver.findElement(By.xpath(locator.getProperty("benefits.title").toString()));
			assertEquals(BENEFITS, benefitsTitle.getText());

			WebElement recommendedForTitle = driver
					.findElement(By.xpath(locator.getProperty("recommendedFor.title").toString()));

			assertEquals(RECOMMENDED_FOR, recommendedForTitle.getText());

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
				} catch (NoSuchElementException ne) {
					logger.error("No Element " + index + " in Recommended For " + ne.toString());
				}

			}
		} catch (NoSuchElementException ne) {
			logger.error("Recommended-For Not Found " + ne.toString());
		}

	}

	/**
	 * MOC 204
	 * 
	 * @throws CharacterLengthExceededException
	 */
	private void testWhatItis(WebDriver driver, Product product, Properties locator)
			throws CharacterLengthExceededException {
		logger.info("------testWhatItis-------");

		try {
			// whatsLabel
			// Pure and Finest product
			String WHATS_LABEL = "Pure and Finest product";
			WebElement whatsEle = driver.findElement(By.xpath(locator.getProperty("whatItis.text").toString()));
			assertEquals(WHATS_LABEL, whatsEle.getText());
			// char count 185 MAX
			if (whatsEle.getText().length() > 185) {
				throw new CharacterLengthExceededException(
						"For What it is: length of characters is greater than specified limit (185)");
			}
		} catch (NoSuchElementException ne) {
			logger.info("Recommended-For Not Found " + ne.toString());
		}
	}

	/**
	 * MOC 205
	 * 
	 * @throws CharacterLengthExceededException
	 */
	private void testWhyItWorks(WebDriver driver, Product product, Properties locator)
			throws CharacterLengthExceededException {
		logger.info("------testWhyItWorks-------");

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
					assertEquals(WIW_POINT_NAME, testWiwPoint.getText());
					if (testWiwPointdesc.getText().length() > 750) {
						throw new CharacterLengthExceededException("Length of Characters in greater than limit (750)");
					}
				} catch (NoSuchElementException ne) {
					logger.error("Point " + index + " not found for Why It Works");
				}
			}
		} catch (NoSuchElementException ne) {
			logger.info("Why It Works Not Found " + ne.toString());
		}
	}

	/** MOC 206 */
	private void testIngredient(WebDriver driver, Product product, Properties locator) {
		logger.info("------testIngredient-------");

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

		} catch (NoSuchElementException ne) {
			logger.error("Ingredient Not present " + ne.toString());
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
			} catch (NoSuchElementException ne) {
				logger.error("Spotlight Ingredient Item " + (index - 1) + " Not present " + ne.toString());
			}
		}
	}

	/** MOC 207 */
	private void testHadasei(WebDriver driver, Product product, Properties locator) {
		logger.info("------testHadasei-------");
		try {
			// Hadasei-3

			// driver.findElement(by);
			/** Checking Hadasei Title and Description */

			String HADASEI_TITLE = "Hadasei-3";
			String HADASEI_DESC = "​Tatcha’s trinity of anti-aging superfoods reveals soft, youthful skin.";

			WebElement hadaTitle = driver.findElement(By.xpath(locator.getProperty("hadasei.title").toString()));
			assertEquals(HADASEI_TITLE, hadaTitle.getText());
			WebElement hadaDesc = driver.findElement(By.xpath(locator.getProperty("hadasei.desc").toString()));
			assertEquals(HADASEI_DESC, hadaDesc.getText());

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
					logger.info("HADA - " + itemImg.getTagName());
					WebElement itemName = driver.findElement(
							By.xpath("//*[@id='ingredients']/div[5]/div/div/div/div[" + index + "]/div/h5"));
					logger.info("HADA - " + itemName.getText());
					WebElement itemDesc = driver.findElement(
							By.xpath("//*[@id='ingredients']/div[5]/div/div/div/div[" + index + "]/div/p"));
					logger.info("HADA - " + itemDesc.getText());
				} catch (NoSuchElementException ne) {
					logger.error("HADASEI Item " + index + " Not present " + ne.toString());
				}
			}
		} catch (NoSuchElementException ne) {
			logger.error("HADASEI Not Found " + ne.toString());
		} catch (AssertionError e) {
			logger.error("HADASEI >> " + e.toString());
			// TODO: handle exception
		}
	}

	/**
	 * MOC 208
	 * 
	 * @throws CharacterLengthExceededException
	 */
	private void testHowToUse(WebDriver driver, Product product, Properties locator)
			throws CharacterLengthExceededException {
		logger.info("------testHowToUse-------");

		try {
			String HOW_USE = "HOW TO USE";
			String HOW_USE_TITLE1 = "Suggested Usage";
			String HOW_USE_TITLE2 = "Dosage";
			String HOW_USE_TITLE3 = "Texture";

			WebElement howUseTitle = driver.findElement(By.xpath(locator.getProperty("howToUse.title").toString()));
			assertEquals(HOW_USE, howUseTitle.getText());

			// *[@id='player_uid_596820346_1']
			WebElement howUsePlayer = driver.findElement(By.xpath(locator.getProperty("howToUse.player").toString()));
			howUsePlayer.click();

			// Suggested Usage
			WebElement howUseTitle1 = driver
					.findElement(By.xpath(locator.getProperty("howToUse.usage.title").toString()));
			WebElement howUseTitle1desc = driver
					.findElement(By.xpath(locator.getProperty("howToUse.usage.desc").toString()));

			assertEquals(HOW_USE_TITLE1, howUseTitle1.getText());
			if (howUseTitle1desc.getText().length() > 200) {
				throw new CharacterLengthExceededException(
						"For Suggested Usage: length of characters is greater than specified limit (200)");
			}

			// Dosage
			WebElement howUseTitle2 = driver
					.findElement(By.xpath(locator.getProperty("howToUse.dosage.title").toString()));
			WebElement howUseTitle2desc = driver
					.findElement(By.xpath(locator.getProperty("howToUse.dosage.desc").toString()));
			assertEquals(HOW_USE_TITLE2, howUseTitle2.getText());
			if (howUseTitle2desc.getText().length() > 100) {
				throw new CharacterLengthExceededException(
						"For Dosage: length of characters is greater than specified limit (100)");
			}

			// Texture
			WebElement howUseTitle3 = driver
					.findElement(By.xpath(locator.getProperty("howToUse.texture.title").toString()));
			WebElement howUseTitle3desc = driver
					.findElement(By.xpath(locator.getProperty("howToUse.texture.desc").toString()));

			assertEquals(HOW_USE_TITLE3, howUseTitle3.getText());
			if (howUseTitle3desc.getText().length() > 50) {
				throw new CharacterLengthExceededException(
						"For Texture: length of characters is greater than specified limit (50)");
			}

		} catch (NoSuchElementException ne) {
			logger.error("How To Use Not Found " + ne.toString());
		}
	}

	/**
	 * MOC 211
	 * 
	 * @throws CharacterLengthExceededException
	 */
	private void testPurityPromise(WebDriver driver, Product product, Properties locator)
			throws CharacterLengthExceededException {
		logger.info("------testPurityPromise-------");

		try {
			String PP_TITLE = "Purity Promise";
			// Image
			WebElement ppImage = driver.findElement(By.xpath(locator.getProperty("purityPromise.img").toString()));
			ppImage.getSize().getHeight();
			ppImage.getSize().getWidth();

			// Title
			WebElement ppTitle = driver.findElement(By.xpath(locator.getProperty("purityPromise.title").toString()));
			assertEquals(PP_TITLE, ppTitle.getText());

			// Description
			WebElement ppDesc = driver.findElement(By.xpath(locator.getProperty("purityPromise.desc").toString()));
			if (ppDesc.getText().length() > 200) {
				throw new CharacterLengthExceededException(
						"For Purity Promise: length of characters is greater than specified limit (200)");
			}
		} catch (NoSuchElementException ne) {
			logger.error("Purity Promise Not Found " + ne.toString());
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
