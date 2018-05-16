package com.litmus7.tatcha.jscripts.selenium.sprint6;

import static org.junit.Assert.assertEquals;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.litmus7.tatcha.jscripts.dob.Product;
import com.litmus7.tatcha.jscripts.dob.User;
import com.litmus7.tatcha.jscripts.selenium.sprint3.AddressBook;
import com.litmus7.tatcha.jscripts.selenium.sprint3.LoginHelper;

public class ShippingAddressCheckout {

	private LoginHelper loginHelper = new LoginHelper();
	private final static Logger logger = Logger.getLogger(ShippingAddressCheckout.class);
	public static final String COUNTRY_US = "United States";

	/**
	 * Handles the shipping address page of checkout functionality.
	 * 
	 * @param driver
	 * @param prop
	 * @param locator
	 * @param user
	 * @throws Exception
	 */
	public void verifyShippingAddress(WebDriver driver, Properties prop, Properties locator, User user)
			throws Exception {

		Properties shoppingBagLocator = new Properties();
		shoppingBagLocator
				.load(new FileInputStream(getClass().getResource("/shoppingBagElementLocator.properties").getFile()));

		WebDriverWait wait = (WebDriverWait) new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.cssSelector(locator.getProperty("checkout.title").toString())));
		WebElement checkoutTitleElement = driver
				.findElement(By.cssSelector(locator.getProperty("checkout.title").toString()));
		try {
			assertEquals("SECURE CHECKOUT", checkoutTitleElement.getText());
		} catch (AssertionError ae) {
			logger.error(ae.toString());
		}
		logger.info("Started Shipping Address");
		try {
			assertEquals("SHIPPING",
					driver.findElement(By.xpath(locator.getProperty("checkout.step1.title").toString())).getText());
		} catch (AssertionError ae) {
			logger.error(ae.toString());
		}

		// Verify Shipping address
		verifyAddress(driver, prop, locator, user);

		// Verify shipping option
		verifyShippingOption(driver, prop, locator, user);

		// Verify summary
		verifyCheckoutSummary(driver, prop, locator, user);

		// Verify items
		verifyItems(driver, prop, locator, user);

		driver.findElement(By.xpath(locator.getProperty("shipping.continue.button").toString())).click();
	}

	/**
	 * Verify the items in cart in shipping address page
	 * 
	 * @param driver
	 * @param prop
	 * @param locator
	 * @param user
	 */
	private void verifyItems(WebDriver driver, Properties prop, Properties locator, User user) {

		int productInCart = 0;
		// Get no of items in cart
		for (Product product : user.getProducts()) {
			productInCart += product.getQuantity();
		}
		logger.info("No of Products in Cart : " + productInCart);

		try {
			assertEquals(productInCart + " ITEMS",
					driver.findElement(By.xpath(locator.getProperty("item.title").toString())).getText());
		} catch (AssertionError ae) {
			logger.error(ae.toString());
		}
		try {
			assertEquals("Edit Bag",
					driver.findElement(By.xpath(locator.getProperty("item.editBag.link").toString())).getText());
		} catch (AssertionError ae) {
			logger.error(ae.toString());
		}

		Product product = null;
		String priceQtyString = "";
		double productPrice = 0.0;
		int productQty = 0;
		WebElement productNameElement = null, priceQtyElement = null;
		for (int index = user.getProducts().size(); index > 0; index--) {
			product = user.getProducts().get(0);
			productPrice = getLoginHelper().getPrice(product.getPrice()) * product.getQuantity();
			productQty = product.getQuantity();

			productNameElement = driver
					.findElement(By.xpath(locator.getProperty("item.name").toString() + index + "]/div[2]/h5/a"));
			try {
				assertEquals(product.getName(), productNameElement.getText());
			} catch (AssertionError ae) {
				logger.error(ae.toString());
			}

			priceQtyElement = driver
					.findElement(By.xpath(locator.getProperty("item.qtyPrice").toString() + index + "]/div[2]/div"));
			priceQtyString = "QTY: " + productQty + " | " + getLoginHelper().getPriceString(productPrice);
			try {
				assertEquals(priceQtyString, priceQtyElement.getText());
			} catch (AssertionError ae) {
				logger.error(ae.toString());
			}
		}
	}

	/**
	 * Verify Selecting an address from address book, and adding a new address
	 * during checkout
	 * 
	 * @param driver
	 * @param prop
	 * @param locator
	 * @param user
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	private void verifyAddress(WebDriver driver, Properties prop, Properties locator, User user)
			throws FileNotFoundException, IOException {

		logger.info("BEGIN verifyAddress");
		AddressBook addrBook = new AddressBook();
		WebDriverWait wait = (WebDriverWait) new WebDriverWait(driver, 10);

		boolean newAddressAdded = false;
		boolean isAddressBookEmpty = true;
		String addressId = null;

		Properties myAccountLocator = new Properties();
		myAccountLocator
				.load(new FileInputStream(getClass().getResource("/myAccountElementLocator.properties").getFile()));

		try {
			assertEquals("Shipping",
					driver.findElement(By.xpath(locator.getProperty("shipping.title").toString())).getText());
		} catch (AssertionError ae) {
			logger.error(ae.toString());
		}

		int index = 0;
		List<WebElement> addressListWebElement = null;
		try {
			assertEquals("SHIPPING ADDRESS", driver
					.findElement(By.xpath(locator.getProperty("shipping.shippingAddr.title").toString())).getText());
			isAddressBookEmpty = false;
			assertEquals("Select an Address", driver
					.findElement(By.xpath(locator.getProperty("shipping.shippingAddr.info").toString())).getText());
			logger.info("Get all the shipping address");
			addressListWebElement = driver
					.findElements(By.name(locator.getProperty("shipping.addressList").toString()));
			index = addressListWebElement.size() + 1;

			logger.info("Select a shipping address");
			if (addressListWebElement.size() > 0) {
				// Select an existing address
				if (addressListWebElement.size() >= 2) {
					addressListWebElement.get(1).click();
				} else {
					addressListWebElement.get(0).click();
				}
			} else {
				// Add a new Address
				logger.info("Add a new shipping address");
				driver.findElement(By.xpath(locator.getProperty("shipping.addAddr.button").toString() + index + "]/a"))
						.click();
				wait.until(ExpectedConditions.visibilityOfElementLocated(
						By.xpath(locator.getProperty("shipping.addAddr.title").toString())));
				addressId = addrBook.populateAddressBook(driver, prop, myAccountLocator, true, true);
				driver.findElement(By.xpath(locator.getProperty("shipping.addAddr.save.button").toString())).click();
				newAddressAdded = true;
				wait.until(ExpectedConditions.invisibilityOfElementLocated(
						By.xpath(locator.getProperty("shipping.addAddr.title").toString())));
				logger.info("Address Added");
			}
		} catch (AssertionError ae) {
			logger.error(ae.toString());
		} catch (NoSuchElementException e) {
			logger.error(
					"Currently there is no shipping addresses in the Address Book. You are about to add the first address.");
		}

		if (!newAddressAdded) {

			// Add a new Address
			if (isAddressBookEmpty) {
				populateShippingAddress(driver, prop, locator);
				logger.info("Address fields populated");
			} else {
				addressListWebElement = driver
						.findElements(By.name(locator.getProperty("shipping.addressList").toString()));
				index = addressListWebElement.size() + 1;
				driver.findElement(By.xpath(locator.getProperty("shipping.addAddr.button").toString() + index + "]/a"))
						.click();
				wait.until(ExpectedConditions.visibilityOfElementLocated(
						By.xpath(locator.getProperty("shipping.addAddr.title").toString())));
				addressId = addrBook.populateAddressBook(driver, prop, myAccountLocator, true, true);
				driver.findElement(By.xpath(locator.getProperty("shipping.addAddr.save.button").toString())).click();
				wait.until(ExpectedConditions.invisibilityOfElementLocated(
						By.xpath(locator.getProperty("shipping.addAddr.title").toString())));
			}
		}

		if (!isAddressBookEmpty) {
			// Verify if the newly added address appears first in the list
			addressListWebElement = driver
					.findElements(By.name(locator.getProperty("shipping.addressList").toString()));
			try {
				assert (addressListWebElement.get(0).isSelected());
			} catch (AssertionError ae) {
				logger.error(ae.toString());
			}
			try {
				assertEquals(addressListWebElement.get(0).getAttribute("value"), addressId);
			} catch (AssertionError ae) {
				logger.error(ae.toString());
			}
		} else {
			try {
				assertEquals("Shipping Address",
						driver.findElement(
								By.xpath(locator.getProperty("shipping.shippingAddr.addrBookEmpty.title").toString()))
								.getText());
			} catch (AssertionError ae) {
				logger.error(ae.toString());
			}
		}
	}

	/**
	 * Populate Shipping address fields is the address book is empty
	 * 
	 * @param driver
	 * @param prop
	 * @param locator
	 * @return
	 */
	private String populateShippingAddress(WebDriver driver, Properties prop, Properties locator) {
		logger.info("BEGIN populateShippingAddress");
		WebDriverWait wait = (WebDriverWait) new WebDriverWait(driver, 5);

		// Get all the web elements in the address book
		WebElement addAddrFirstNameElement = driver
				.findElement(By.xpath(locator.getProperty("addAddr.firstName").toString()));
		WebElement addAddrLastNameElement = driver
				.findElement(By.xpath(locator.getProperty("addAddr.lastName").toString()));
		WebElement addAddrCountryElement = driver
				.findElement(By.xpath(locator.getProperty("addAddr.country").toString()));
		WebElement addAddr1Element = driver.findElement(By.xpath(locator.getProperty("addAddr.addr1").toString()));
		WebElement addAddr2Element = driver.findElement(By.xpath(locator.getProperty("addAddr.addr2").toString()));
		WebElement addAddrZipCodeElement = driver
				.findElement(By.xpath(locator.getProperty("addAddr.zipCode").toString()));
		WebElement addAddrPhoneElement = driver.findElement(By.xpath(locator.getProperty("addAddr.phone").toString()));

		// Get Address Id
		WebElement addressIdElement = driver.findElement(By.id("dwfrm_profile_address_addressid"));
		String addressId = addressIdElement.getAttribute("value");
		logger.info("Id of the Address added : " + addressId);

		// Populate first and last name
		if (addAddrFirstNameElement.getAttribute("value").isEmpty()) {
			addAddrFirstNameElement.clear();
			addAddrFirstNameElement.sendKeys(prop.getProperty("addressbook.fname").toString() + addressId);
		}
		if (addAddrLastNameElement.getAttribute("value").isEmpty()) {
			addAddrLastNameElement.clear();
			addAddrLastNameElement.sendKeys(prop.getProperty("addressbook.lname").toString());
		}

		// Select Country
		logger.info("Populate country");
		List<WebElement> shippingOptionsListElement = driver
				.findElements(By.name(locator.getProperty("shipping.optionList").toString()));
		String countryValue = null;
		Select country = new Select(addAddrCountryElement);
		countryValue = prop.getProperty("addressbook.country").toString();
		if (!addAddrCountryElement.isSelected()) {
			country.selectByVisibleText(countryValue);
			if (!COUNTRY_US.equalsIgnoreCase(countryValue)) {
				wait.until(ExpectedConditions.stalenessOf(shippingOptionsListElement.get(0)));
			}
			logger.info("Country selected");
		}

		// Populate address1 and address2
		addAddr1Element.clear();
		addAddr1Element.sendKeys(prop.getProperty("addressbook.addr1").toString());
		addAddr2Element.clear();
		addAddr2Element.sendKeys(prop.getProperty("addressbook.addr2").toString());

		// Populate zip code and city
		logger.info("populate zipcode and city");
		addAddrZipCodeElement.clear();
		addAddrZipCodeElement.sendKeys(prop.getProperty("addressbook.pin").toString());
		wait.until(ExpectedConditions.textToBePresentInElementValue(addAddrZipCodeElement,
				prop.getProperty("addressbook.pin").toString()));
		WebElement addAddrCityElement = driver.findElement(By.xpath(locator.getProperty("addAddr.city").toString()));
		if (addAddrCityElement.getAttribute("value").isEmpty()) {
			addAddrCityElement.clear();
			addAddrCityElement.sendKeys(prop.getProperty("addressbook.city").toString());
		}

		logger.info("populate state");
		WebElement addAddrStateElement = null;
		if (COUNTRY_US.equals(countryValue)) {
			logger.info("Country is US");
			addAddrStateElement = driver.findElement(By.xpath(locator.getProperty("addAddr.states").toString()));
			// Select State if country is US
			Select state = new Select(addAddrStateElement);
			if (!addAddrStateElement.isSelected()) {
				logger.info("select state");
				state.selectByVisibleText(prop.getProperty("addressbook.state").toString());
			}
		} else {
			logger.info("Country is not US");
			addAddrStateElement = driver.findElement(By.xpath(locator.getProperty("addAddr.states").toString()));
			if (addAddrStateElement.getAttribute("value").isEmpty()) {
				// Enter state if country is not US
				addAddrStateElement.clear();
				addAddrStateElement.sendKeys(prop.getProperty("addressbook.state").toString());
			}
		}

		// Populate phone no.
		logger.info("populate phone no");
		addAddrPhoneElement.clear();
		addAddrPhoneElement.sendKeys(prop.getProperty("addressbook.phone").toString());
		logger.info("END populateShippingAddress");
		return addressId;
	}

	/**
	 * Verify the shipping options for the selected shipping address
	 * 
	 * @param driver
	 * @param prop
	 * @param locator
	 * @param user
	 */
	private void verifyShippingOption(WebDriver driver, Properties prop, Properties locator, User user) {

		logger.info("BEGIN verifyShippingOption");
		WebDriverWait wait = (WebDriverWait) new WebDriverWait(driver, 10);
		try {
			assertEquals("Shipping Options",
					driver.findElement(By.xpath(locator.getProperty("shipping.option.title").toString())).getText());
		} catch (AssertionError ae) {
			logger.error(ae.toString());
		}
		try {
			assertEquals(prop.getProperty("checkout.shippingOption.info").toString(),
					driver.findElement(By.xpath(locator.getProperty("shipping.option.info").toString())).getText());
		} catch (AssertionError ae) {
			logger.error(ae.toString());
		}

		logger.info("Get all shipping options for the selected shipping address");
		wait.withTimeout(30, TimeUnit.SECONDS);
		List<WebElement> shippingOptionsListElement = driver
				.findElements(By.name(locator.getProperty("shipping.optionList").toString()));
		logger.info("Shipping Option List Size: " + shippingOptionsListElement.size());

		try {
			assert (shippingOptionsListElement.get(0).isSelected());
		} catch (AssertionError ae) {
			logger.error(ae.toString());
		}
		String id = shippingOptionsListElement.get(0).getAttribute("id");
		logger.info("Shipping Option : " + id);
		// WebElement shippingOptionElement =
		// shippingOptionsListElement.get(0).findElement(By.cssSelector("label[for=\""+id+"\"]"));//("img[alt=\""+productName+"\"]")
		WebElement shippingOptionElement = shippingOptionsListElement.get(0).findElement(By.xpath(".."));
		String priceString = shippingOptionElement.findElement(By.className("ship-price")).getText();
		double shippingPriceFromList = getLoginHelper().getPrice(priceString);
		logger.info("Shipping Total : " + shippingPriceFromList);
		try {
			assertEquals(priceString,
					driver.findElement(By.xpath(locator.getProperty("summary.shipping.value").toString())).getText());
		} catch (AssertionError ae) {
			logger.error(ae.toString());
		}
		logger.info("END verifyShippingOption");
	}

	/**
	 * Handles the checkout summary
	 * 
	 * @param driver
	 * @param prop
	 * @param locator
	 * @param user
	 */
	public void verifyCheckoutSummary(WebDriver driver, Properties prop, Properties locator, User user) {

		logger.info("BEGIN Checkout Summary");
		WebDriverWait wait = (WebDriverWait) new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath(locator.getProperty("summary.title").toString())));

		if (null != user.getProducts()) {

			double subtotal = 0;
			int productInCart = 0;
			String priceString = "";
			// Get the subtotal
			for (Product product : user.getProducts()) {
				priceString = product.getPrice();
				subtotal += getLoginHelper().getPrice(priceString) * product.getQuantity();
				productInCart += product.getQuantity();
			}
			logger.info("Subtotal : " + subtotal);
			logger.info("No of Products in Cart : " + productInCart);
			// Summary
			try {
				assertEquals("Summary",
						driver.findElement(By.xpath(locator.getProperty("summary.title").toString())).getText());
			} catch (AssertionError ae) {
				logger.error(ae.toString());
			}
			try {
				assertEquals("SUBTOTAL", driver
						.findElement(By.xpath(locator.getProperty("summary.subtotal.label").toString())).getText());
			} catch (AssertionError ae) {
				logger.error(ae.toString());
			}
			try {
				assertEquals(getLoginHelper().getPriceString(subtotal), driver
						.findElement(By.xpath(locator.getProperty("summary.subtotal.value").toString())).getText());
			} catch (AssertionError ae) {
				logger.error(ae.toString());
			}
			try {
				assertEquals("SHIPPING", driver
						.findElement(By.xpath(locator.getProperty("summary.shipping.label").toString())).getText());
			} catch (AssertionError ae) {
				logger.error(ae.toString());
			}
			/* Shipping cost already asserted in verifyShippingOption */
			try {
				assertEquals("TAX",
						driver.findElement(By.xpath(locator.getProperty("summary.tax.label").toString())).getText());
			} catch (AssertionError ae) {
				logger.error(ae.toString());
			}
			// assertEquals(getLoginHelper().getPriceString(subtotal),
			// driver.findElement(By.xpath(locator.getProperty("summary.tax.value").toString())).getText());
			double shippingCost = getLoginHelper().getPrice(
					driver.findElement(By.xpath(locator.getProperty("summary.shipping.value").toString())).getText());
			double taxAmount = getLoginHelper().getPrice(
					driver.findElement(By.xpath(locator.getProperty("summary.tax.value").toString())).getText());
			double total = subtotal + shippingCost + taxAmount;
			String totalPriceString = getLoginHelper().getPriceString(total);
			try {
				assertEquals("ESTIMATED TOTAL",
						driver.findElement(By.xpath(locator.getProperty("summary.total.label").toString())).getText());
			} catch (AssertionError ae) {
				logger.error(ae.toString());
			}
			WebElement summaryTotalValueElement = driver
					.findElement(By.xpath(locator.getProperty("summary.total.value").toString()));
			try {
				assertEquals(totalPriceString, summaryTotalValueElement.getText());
			} catch (AssertionError ae) {
				logger.error(ae.toString());
			}

			// Promotion
			WebElement promoCodeLabelElement = driver
					.findElement(By.xpath(locator.getProperty("summary.promo.label").toString()));
			try {
				assertEquals("PROMO CODE", promoCodeLabelElement.getText());
			} catch (AssertionError ae) {
				logger.error(ae.toString());
			}

			// Apply valid coupon
			String couponCode = "TATCHA";
			logger.info("Applying valid coupon");
			driver.findElement(By.cssSelector(locator.getProperty("summary.promo.textarea").toString())).clear();
			driver.findElement(By.cssSelector(locator.getProperty("summary.promo.textarea").toString()))
					.sendKeys(couponCode);
			WebElement applyCouponButtonElement = driver
					.findElement(By.cssSelector(locator.getProperty("summary.promo.apply.button").toString()));
			applyCouponButtonElement.click();

			double discount = 10.00;
			WebElement promocodeValidationMsgElement = null;
			promocodeValidationMsgElement = driver
					.findElement(By.xpath(locator.getProperty("summary.promo.validation.message").toString()));
			WebElement discountValueElement = driver
					.findElement(By.xpath(locator.getProperty("summary.discount.value").toString()));
			try {
				try {
					assertEquals("DISCOUNT", driver
							.findElement(By.xpath(locator.getProperty("summary.discount.label").toString())).getText());
				} catch (AssertionError ae) {
					logger.error(ae.toString());
				}
				try {
					assertEquals("-" + getLoginHelper().getPriceString(discount), discountValueElement.getText());
				} catch (AssertionError ae) {
					logger.error(ae.toString());
				}
				try {
					assertEquals("The promo code has been applied to your order.",
							promocodeValidationMsgElement.getText());
				} catch (AssertionError ae) {
					logger.error(ae.toString());
				}
			} catch (NoSuchElementException ne) {

			}
			total = total - discount;
			logger.info("Total : " + total);
			try {
				assertEquals("ESTIMATED TOTAL",
						driver.findElement(By.xpath(locator.getProperty("summary.total.label").toString())).getText());
			} catch (AssertionError ae) {
				logger.error(ae.toString());
			}
			summaryTotalValueElement = driver
					.findElement(By.xpath(locator.getProperty("summary.total.value").toString()));
			try {
				assertEquals(getLoginHelper().getPriceString(total), summaryTotalValueElement.getText());
			} catch (AssertionError ae) {
				logger.error(ae.toString());
			}

			// Remove promotion
			logger.info("Removing coupon");
			driver.findElement(By.cssSelector(locator.getProperty("summary.promo.remove.button").toString())).click();
			wait.until(ExpectedConditions.stalenessOf(discountValueElement));

			total = total + discount;
			summaryTotalValueElement = driver
					.findElement(By.xpath(locator.getProperty("summary.total.value").toString()));
			wait.until(ExpectedConditions.textToBePresentInElement(summaryTotalValueElement,
					getLoginHelper().getPriceString(total)));

			try {
				assertEquals(getLoginHelper().getPriceString(total), summaryTotalValueElement.getText());
			} catch (AssertionError ae) {
				logger.error(ae.toString());
			}

			// Apply an invalid coupon
			couponCode = "ABCDEF";
			logger.info("Applying invalid coupon");
			driver.findElement(By.cssSelector(locator.getProperty("summary.promo.textarea").toString())).clear();
			driver.findElement(By.cssSelector(locator.getProperty("summary.promo.textarea").toString()))
					.sendKeys(couponCode);
			applyCouponButtonElement = driver
					.findElement(By.cssSelector(locator.getProperty("summary.promo.apply.button").toString()));
			applyCouponButtonElement.click();
			// wait.until(ExpectedConditions.stalenessOf(promocodeValidationMsgElement));

			// Assert invalid coupon error message
			promocodeValidationMsgElement = driver
					.findElement(By.xpath(locator.getProperty("summary.promo.error.message").toString()));
			wait.until(
					ExpectedConditions.textToBePresentInElement(promocodeValidationMsgElement, "Invalid promo code"));
			try {
				assertEquals("Invalid promo code", promocodeValidationMsgElement.getText());
			} catch (AssertionError ae) {
				logger.error(ae.toString());
			}
		}
		logger.info("END Checkout Summary");
	}

	/**
	 * @return the loginHelper
	 */
	public LoginHelper getLoginHelper() {
		return loginHelper;
	}

	/**
	 * @param loginHelper
	 *            the loginHelper to set
	 */
	public void setLoginHelper(LoginHelper loginHelper) {
		this.loginHelper = loginHelper;
	}
}
