package com.tatcha.jscripts.product;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class SecureCheckout {
	private WebDriver driver;
	private String baseUrl;
	private boolean acceptNextAlert = true;
	private StringBuffer verificationErrors = new StringBuffer();

	@Before
	public void setUp() throws Exception {
		driver = new FirefoxDriver();
		baseUrl = "https://development-na01-tatcha.demandware.net/";
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	@Test
	public void testSecureCheckout() throws Exception {
		driver.get(baseUrl + "/on/demandware.store/Sites-tatcha-Site/default/COShipping-SingleShipping");
		driver.findElement(By.cssSelector("img.tatcha-logo")).click();
		driver.findElement(By.linkText("Shop All")).click();
		// ERROR: Caught exception [Error: locator strategy either id or name
		// must be specified explicitly.]
		driver.findElement(By.cssSelector("img[alt=\"Yume Kimono Cuff - Gold\"]")).click();
		driver.findElement(By.id("add-to-cart")).click();
		driver.findElement(By.linkText("Checkout")).click();
		driver.findElement(By.name("dwfrm_cart_checkoutCart")).click();
		driver.findElement(By.id("dwfrm_login_username")).clear();
		driver.findElement(By.id("dwfrm_login_username")).sendKeys("qa@qa.com");
		driver.findElement(By.name("dwfrm_login_continuelogin")).click();

		secureCheckout(driver);
	}

	public void secureCheckout(WebDriver driver) {
		// BEGIN Secure Checkout
		assertEquals("SECURE CHECKOUT", driver.findElement(By.cssSelector("h1")).getText());
		assertEquals("Shipping", driver.findElement(By.linkText("Shipping")).getText());
		assertEquals("PAYMENT",
				driver.findElement(By.xpath("//html[@id='ext-gen45']/body/main/div/div/ul/li[2]/span")).getText());
		assertEquals("REVIEW ORDER",
				driver.findElement(By.xpath("//html[@id='ext-gen45']/body/main/div/div/ul/li[3]/span")).getText());
		assertEquals("Shipping", driver.findElement(By.cssSelector("h2.panel-title")).getText());
		// Shipping Address BOX
		assertEquals("Shipping Address", driver.findElement(By.cssSelector("h5")).getText());
		assertEquals("First Name", driver.findElement(By.cssSelector("label.control-label > span")).getText());
		assertEquals("This field is required.", driver
				.findElement(By.id("dwfrm_singleshipping_shippingAddress_addressFields_firstName-error")).getText());
		driver.findElement(By.id("dwfrm_singleshipping_shippingAddress_addressFields_lastName")).click();
		assertEquals("Last Name",
				driver.findElement(By
						.xpath("//form[@id='dwfrm_singleshipping_shippingAddress']/div/div[2]/div/div/div/div[2]/label/span"))
						.getText());
		assertEquals("This field is required.", driver
				.findElement(By.id("dwfrm_singleshipping_shippingAddress_addressFields_lastName-error")).getText());
		assertEquals("Country",
				driver.findElement(By
						.xpath("//form[@id='dwfrm_singleshipping_shippingAddress']/div/div[2]/div/div/div/div[3]/label"))
						.getText());
		new Select(driver.findElement(By.id("dwfrm_singleshipping_shippingAddress_addressFields_country")))
				.selectByVisibleText("Uganda");
		driver.findElement(By.cssSelector("option[value=\"UG\"]")).click();
		driver.findElement(By.id("dwfrm_singleshipping_shippingAddress_addressFields_address1")).click();
		assertEquals("Address",
				driver.findElement(By
						.xpath("//form[@id='dwfrm_singleshipping_shippingAddress']/div/div[2]/div/div/div/div[4]/label/span"))
						.getText());
		assertEquals("This field is required.", driver
				.findElement(By.id("dwfrm_singleshipping_shippingAddress_addressFields_address1-error")).getText());
		driver.findElement(By.id("dwfrm_singleshipping_shippingAddress_addressFields_address2")).click();
		assertEquals("Address 2",
				driver.findElement(By
						.xpath("//form[@id='dwfrm_singleshipping_shippingAddress']/div/div[2]/div/div/div/div[5]/label/span"))
						.getText());
		driver.findElement(By.id("dwfrm_singleshipping_shippingAddress_addressFields_postal")).click();
		assertEquals("ZIP Code",
				driver.findElement(By
						.xpath("//form[@id='dwfrm_singleshipping_shippingAddress']/div/div[2]/div/div/div/div[6]/div/div/div/label/span"))
						.getText());
		assertEquals("This field is required.",
				driver.findElement(By.id("dwfrm_singleshipping_shippingAddress_addressFields_postal-error")).getText());
		assertEquals("State",
				driver.findElement(By
						.xpath("//form[@id='dwfrm_singleshipping_shippingAddress']/div/div[2]/div/div/div/div[6]/div[2]/div/div/label/span"))
						.getText());
		assertEquals("This field is required.", driver
				.findElement(By.id("dwfrm_singleshipping_shippingAddress_addressFields_states_state-error")).getText());
		assertEquals("City",
				driver.findElement(By
						.xpath("//form[@id='dwfrm_singleshipping_shippingAddress']/div/div[2]/div/div/div/div[7]/label/span"))
						.getText());
		assertEquals("This field is required.",
				driver.findElement(By.id("dwfrm_singleshipping_shippingAddress_addressFields_city-error")).getText());
		assertEquals("Phone",
				driver.findElement(By
						.xpath("//form[@id='dwfrm_singleshipping_shippingAddress']/div/div[2]/div/div/div/div[8]/div/label/span"))
						.getText());
		assertEquals("This field is required.",
				driver.findElement(By.id("dwfrm_singleshipping_shippingAddress_addressFields_phone-error")).getText());

		// filling fields Shipping Address
		driver.findElement(By.id("dwfrm_singleshipping_shippingAddress_addressFields_firstName")).clear();
		driver.findElement(By.id("dwfrm_singleshipping_shippingAddress_addressFields_firstName")).sendKeys("Peter");
		driver.findElement(By.id("dwfrm_singleshipping_shippingAddress_addressFields_lastName")).clear();
		driver.findElement(By.id("dwfrm_singleshipping_shippingAddress_addressFields_lastName")).sendKeys("Parker");
		new Select(driver.findElement(By.id("dwfrm_singleshipping_shippingAddress_addressFields_country")))
				.selectByVisibleText("United Kingdom");
		driver.findElement(By.cssSelector("option[value=\"GB\"]")).click();
		driver.findElement(By.id("dwfrm_singleshipping_shippingAddress_addressFields_address1")).clear();
		driver.findElement(By.id("dwfrm_singleshipping_shippingAddress_addressFields_address1"))
				.sendKeys("Acme Plc, Acme House");
		driver.findElement(By.id("dwfrm_singleshipping_shippingAddress_addressFields_address2")).clear();
		driver.findElement(By.id("dwfrm_singleshipping_shippingAddress_addressFields_address2"))
				.sendKeys("3 Street, SouthHampton");
		driver.findElement(By.id("dwfrm_singleshipping_shippingAddress_addressFields_postal")).clear();
		driver.findElement(By.id("dwfrm_singleshipping_shippingAddress_addressFields_postal")).sendKeys("SO314NG");
		driver.findElement(By.id("dwfrm_singleshipping_shippingAddress_addressFields_states_state")).clear();
		driver.findElement(By.id("dwfrm_singleshipping_shippingAddress_addressFields_states_state"))
				.sendKeys("England");
		driver.findElement(By.id("dwfrm_singleshipping_shippingAddress_addressFields_city")).clear();
		driver.findElement(By.id("dwfrm_singleshipping_shippingAddress_addressFields_city")).sendKeys("SouthHampton");
		driver.findElement(By.id("dwfrm_singleshipping_shippingAddress_addressFields_phone")).clear();
		driver.findElement(By.id("dwfrm_singleshipping_shippingAddress_addressFields_phone")).sendKeys("03332400707");

		// Shipping Options
		// ERROR: Caught exception [ERROR: Unsupported command [selectWindow |
		// null | ]]
		assertEquals("Shipping Options",
				driver.findElement(By.xpath("//form[@id='dwfrm_singleshipping_shippingAddress']/div/div[2]/div[2]/h5"))
						.getText());
		assertEquals(
				"Estimates based orders placed before 1pm Pacific Time. Orders placed after are normally shipped the next day.",
				driver.findElement(By.cssSelector("em")).getText());
		driver.findElement(By.id("shipping-method-productmatrix_Standard_USPS_Priority")).click();
		driver.findElement(By.id("shipping-method-productmatrix_2-Day")).click();
		driver.findElement(By.id("shipping-method-productmatrix_Overnight")).click();
		driver.findElement(By.id("shipping-method-fedex_INTERNATIONAL_ECONOMY")).click();
		driver.findElement(By.id("shipping-method-productmatrix_USPS_(7-10_Business_Days)")).click();
		assertEquals("", driver.findElement(By.id("shipping-method-fedex_INTERNATIONAL_ECONOMY")).getText());
		assertEquals("",
				driver.findElement(By.id("shipping-method-productmatrix_USPS_(7-10_Business_Days)")).getText());
		// Tatcha Gift Wrapping
		assertEquals("", driver.findElement(By.cssSelector("img.img-responsive.product-img")).getText());
		driver.findElement(By.id("giftwrap-toggle")).click();
		driver.findElement(By.id("add-giftwrap-button")).click();
		assertEquals("Add a gift box for this order. ($10.00)",
				driver.findElement(By.cssSelector("div.checkbox > label")).getText());
		// checking Gift Message Cancel button
		driver.findElement(By.id("hasGiftMessage")).click();
		assertEquals("Add a Gift Message",
				driver.findElement(By.cssSelector(
						"#giftMessageModal > div.modal-dialog > div.modal-content > div.modal-header > h4.modal-title > strong"))
						.getText());
		assertEquals("Message",
				driver.findElement(By.cssSelector("div.modal-body > div.form-group > label.control-label")).getText());
		// checking Gift Message Save button
		driver.findElement(By.id("close-giftmsg")).click();
		driver.findElement(By.id("hasGiftMessage")).click();
		driver.findElement(By.id("textAreaPost")).click();
		driver.findElement(By.id("textAreaPost")).clear();
		driver.findElement(By.id("textAreaPost")).sendKeys("Heartly Greetings");
		driver.findElement(By.xpath("(//button[@type='submit'])[3]")).click();
		// checking Gift Message Modify button
		driver.findElement(By.linkText("Edit Gift Message")).click();
		driver.findElement(By.id("textAreaPost")).clear();
		driver.findElement(By.id("textAreaPost")).sendKeys("Heartly Greetings Couples");
		driver.findElement(By.xpath("(//button[@type='submit'])[3]")).click();
		// verifying Gift Message
		assertEquals("Message", driver.findElement(By.cssSelector("span.data-label")).getText());
		assertEquals("Heartly Greetings Couples", driver.findElement(By.cssSelector("small > em")).getText());
		// deleting Gift Message
		// ERROR: Caught exception [Error: locator strategy either id or name
		// must be specified explicitly.]
		// Continue Button
		driver.findElement(By.name("dwfrm_singleshipping_shippingAddress_save")).click();
		driver.findElement(By.id("braintreeCardOwner")).clear();
		driver.findElement(By.id("braintreeCardOwner")).sendKeys("");
		assertEquals("Payment", driver.findElement(By.cssSelector("h2.panel-title")).getText());
		driver.findElement(By.linkText("Shipping")).click();
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
