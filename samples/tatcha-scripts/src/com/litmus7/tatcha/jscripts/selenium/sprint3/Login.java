package com.litmus7.tatcha.jscripts.selenium.sprint3;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import org.openqa.selenium.*;

import com.litmus7.tatcha.jscripts.commons.TestMethods;
import com.litmus7.tatcha.jscripts.dob.User;
import com.litmus7.tatcha.utils.BrowserDriver;
import com.xceptance.xlt.api.webdriver.XltChromeDriver;
import com.xceptance.xlt.api.webdriver.XltDriver;

public class Login {
    private WebDriver driver = BrowserDriver.getChromeWebDriver();
//    private WebDriver driver = new XltChromeDriver();
//    private WebDriver driver = new XltDriver();
    private String baseUrl = BrowserDriver.BASE_URL;
    private boolean acceptNextAlert = true;
    private StringBuffer verificationErrors = new StringBuffer();
    private Properties prop = new Properties();
    private Properties locator = new Properties();

    @Before
    public void setUp() throws Exception {
        prop.load(new FileInputStream(getClass().getResource("/tatcha.properties").getFile()));
        locator.load(new FileInputStream(getClass().getResource("/myAccountElementLocator.properties").getFile()));
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.get(baseUrl);
    }

    @Test
    public void testLogin() throws Exception {
    	TestMethods.getInstance().testNewsLetterPopupModal(driver);
        User user = new User();

        ProfileSettings profile = new ProfileSettings();
        AddressBook address = new AddressBook();
//        PaymentOptions payment = new PaymentOptions();
        OrderHistory order = new OrderHistory();
                
        try {
            // User is logged in
            login(driver, prop, locator, user);
            
            // Assert and test profile settings in my account
            profile.verifyProfileSettings(driver, prop, locator, user);
            driver.findElement(By.xpath(locator.getProperty("account.back").toString())).click();
            
            // Assert and test address book in my account
            address.verifyAddressBook(driver, prop, locator);
            driver.findElement(By.xpath(locator.getProperty("account.back").toString())).click();
            
            // Assert and test payment options in my account - Cannot retrieve braintree fields
//            payment.verifyPaymentOptions(driver, prop, locator);
//            driver.findElement(By.xpath(locator.getProperty("account.back").toString())).click();
            
            // Assert and test order history in my account
            order.verifyOrderHistory(driver, prop, locator);
            driver.findElement(By.xpath(locator.getProperty("account.back").toString())).click();
            
        } catch (NoSuchElementException ne) {
            System.err.println("LOGIN : ELEMENT NOT FOUND " + ne.toString());
        } catch (ElementNotVisibleException nv) {
            System.err.println("LOGIN : ELEMENT NOT VISIBLE " + nv.toString());
        } catch (TimeoutException te) {
            System.err.println("LOGIN : TIMEOUT " + te.toString());
        } catch (StaleElementReferenceException sr) {
            System.err.println("LOGIN : STALE ELE REF " + sr.toString());
        } catch (WebDriverException we) {
            System.err.println("LOGIN : WEBDRIVER ISSUE " + we.toString());
        } catch (InterruptedException io) {
            System.err.println("LOGIN : IOEXCEPTION " + io.toString());
        } catch (FileNotFoundException fnf) {
            System.err.println("LOGIN : FILE NOT FOUND" + fnf.toString());
        } catch (IOException io) {
            System.err.println("LOGIN : IOEXCEPTION " + io.toString());
        }
    }

    /**
     * Log-in registered user
     * 
     * @param driver
     * @param prop
     * @param locator
     * @param user
     * @throws Exception
     */
    public void login(WebDriver driver, Properties prop, Properties locator, User user) throws Exception {

        // Get test username and password
        String username = prop.get("login.username").toString();
        String password = prop.get("login.password").toString();

        // Get the locators for fields and button
        String usernameFieldLocator = locator.getProperty("login.username").toString();
        String passwordFieldLocator = locator.getProperty("login.password").toString();
        String loginButtonLocator = locator.getProperty("login.button").toString();

        // Go to the login page
        WebElement loginLink = driver.findElement(By.cssSelector(locator.getProperty("login.inlineLink").toString()));
        loginLink.click();

        // Get the web elements
        WebElement usernameElement = driver.findElement(By.xpath("//*[contains(@id,'" + usernameFieldLocator + "')]"));
        WebElement passwordElement = driver.findElement(By.xpath("//*[contains(@id,'" + passwordFieldLocator + "')]"));
        WebElement loginButtonElement = driver.findElement(By.name(loginButtonLocator));

        // Populate test data and click login
        usernameElement.clear();
        usernameElement.sendKeys(username);
        passwordElement.clear();
        passwordElement.sendKeys(password);
        loginButtonElement.click();

        // Get validation error message
        String validationMessage = prop.get("login.validation.mismatch").toString();
        WebElement alertMessageElement = null;
        boolean loginSuccess = true;

        // Get the web element for validation error message, if not found login
        // is successful
        try {
            alertMessageElement = driver.findElement(By.className("alert alert-danger"));
        } catch (NoSuchElementException ne) {
            loginSuccess = true;
            user.setEmail(username);
            user.setPassword(password);
        }

        // If login not successful assert error message
        if (!loginSuccess)
            assertEquals(validationMessage, alertMessageElement.getText());
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
