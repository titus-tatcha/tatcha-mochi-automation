package com.tatcha.jscripts.login;

import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.tatcha.jscripts.bag.TestCartItems;
import com.tatcha.jscripts.dao.TestCase;
import com.tatcha.jscripts.dao.User;
import com.tatcha.jscripts.helper.TatchaTestHelper;

/**
 * 
 * @author Reshma
 *
 */
public class TestLogin {

    private TatchaTestHelper testHelper = new TatchaTestHelper();
    private final static Logger logger = Logger.getLogger(TestCartItems.class);
    private TestCase testCase;

    /**
     * Handles User login after adding to cart and clicking checkout in shopping
     * bag as guest user
     * 
     * @param driver
     * @param prop
     * @param locator
     * @param user
     * @param tcList
     * @throws Exception
     */
    public void checkoutLogin(WebDriver driver, Properties data, User user, List<TestCase> tcList) throws Exception {

        logger.info("BEGIN checkoutLogin");
        String FUNCTIONALITY = "Login in the checkout flow";
        testCase = new TestCase("TC-3.1", "MOC-NIL", FUNCTIONALITY, "FAIL", "");

        WebDriverWait wait = (WebDriverWait) new WebDriverWait(driver, 10);

        wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.xpath("//*[@id='login-fb-checkout']/div/div[1]/h1")));

        // Enter registered email id
        WebElement emailIdElement = driver
                .findElement(By.cssSelector("input#dwfrm_login_username.input-text.form-control.required"));
        emailIdElement.clear();
        emailIdElement.sendKeys(data.getProperty("email").toString());
        user.setEmail(data.getProperty("email").toString());
        // Click continue
        driver.findElement(By.xpath("//*[@id='dwfrm_login']/button")).click();

        // Wait until page refreshed
        wait.until(ExpectedConditions.refreshed(ExpectedConditions.stalenessOf(emailIdElement)));
        wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.xpath("//*[@id='dwfrm_login']/div[3]/div[1]/div/h5")));

        // Get user First name
        String message = driver.findElement(By.xpath("//*[@id='dwfrm_login']/div[3]/div[1]/div/h5")).getText();
        int length = message.length();
        String firstName = message.substring(14, (length-42));
        user.setFname(firstName);
        
        // Enter password
        WebElement passwordElement = driver.findElement(By.xpath("//*[@id='dwfrm_login_password']"));
        passwordElement.clear();
        passwordElement.sendKeys(data.getProperty("password").toString());
        user.setPassword(data.getProperty("password").toString());
        // Click continue
        driver.findElement(By.xpath("//*[@id='dwfrm_login']/div[3]/div[2]/div/button")).click();
        testCase.setStatus("PASS");
        tcList.add(testCase);
        logger.info("END checkoutLogin");
    }

    /**
     * Checkout as a guest user
     * 
     * @param driver
     * @param prop
     * @param locator
     * @param user
     * @param tcList
     * @throws Exception
     */
    public void checkoutGuest(WebDriver driver, Properties data, User user, List<TestCase> tcList) throws Exception {

        logger.info("BEGIN checkoutGuest");
        String FUNCTIONALITY = "Checkout as a guest";
        testCase = new TestCase("TC-3.2", "MOC-NIL", FUNCTIONALITY, "FAIL", "");

        WebDriverWait wait = (WebDriverWait) new WebDriverWait(driver, 10);

        wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.xpath("//*[@id='login-fb-checkout']/div/div[1]/h1")));

        // Enter registered email id
        WebElement emailIdElement = driver.findElement(By.xpath("//*[@id='dwfrm_login_username']"));
        emailIdElement.clear();
        emailIdElement.sendKeys(data.getProperty("email").toString());

        // Click continue
        driver.findElement(By.xpath("//*[@id='dwfrm_login']/button")).click();

        // Wait until page refreshed
        wait.until(ExpectedConditions.refreshed(ExpectedConditions.stalenessOf(emailIdElement)));
        testCase.setStatus("PASS");
        tcList.add(testCase);
        logger.info("END checkoutGuest");
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
    public void login(WebDriver driver, Properties prop, Properties locator, User user, List<TestCase> tcList) throws Exception {

        logger.info("BEGIN login");
        String FUNCTIONALITY = "Login to the account";
        testCase = new TestCase("TC-3.3", "MOC-NIL", FUNCTIONALITY, "FAIL", "");

        WebDriverWait wait = (WebDriverWait) new WebDriverWait(driver, 10);
        // Get test username and password
        String username = prop.get("email").toString();
        String password = prop.get("password").toString();

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

        if(loginSuccess) {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("dropdownMenu1")));
        } else {
            // If login not successful assert error message
            getTestHelper().logAssertion(getClass().getSimpleName(), validationMessage, alertMessageElement.getText());
        }
        testCase.setStatus("PASS");
        tcList.add(testCase);
        logger.info("END login");
    }

    /**
     * @return the testHelper
     */
    public TatchaTestHelper getTestHelper() {
        return testHelper;
    }

    /**
     * @param testHelper the testHelper to set
     */
    public void setTestHelper(TatchaTestHelper testHelper) {
        this.testHelper = testHelper;
    }
}
