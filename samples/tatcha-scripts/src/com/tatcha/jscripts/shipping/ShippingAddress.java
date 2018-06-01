package com.tatcha.jscripts.shipping;

import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.tatcha.jscripts.dao.TestCase;
import com.tatcha.jscripts.dao.User;
import com.tatcha.jscripts.helper.TatchaTestHelper;

/**
 * Class that checks different options of Checkout - Shipping page
 * 
 * @author Reshma
 *
 */
public class ShippingAddress {

    private TatchaTestHelper testHelper = new TatchaTestHelper();
    private final static Logger logger = Logger.getLogger(ShippingAddress.class);
    private TestCase testCase;

    /**
     * Add the first shipping address in Checkout - Shipping page
     * 
     * @param driver
     * @param prop
     * @param locator
     * @param user
     * @param map
     * @param data
     * @param tcList
     * @throws Exception
     */
    public void verifyShippingAddress(WebDriver driver, Properties prop, Properties locator, User user,
            Map<String, Boolean> map, Properties data, List<TestCase> tcList) throws Exception {

        logger.info("BEGIN verifyShippingAddress");
        String FUNCTIONALITY = "Add first shipping address in Checkout-shipping page";
        testCase = new TestCase("TC-14.1", "MOC-NIL", FUNCTIONALITY, "FAIL", "");

        Actions actions = new Actions(driver);
        WebDriverWait wait = (WebDriverWait) new WebDriverWait(driver, 10);
        WebElement continueButtonElement = null;

        TestShippingAddress testAddress = new TestShippingAddress();
        testAddress.testAddFirstShippingAddress(driver, data, locator, user, tcList);
        TestShippingOption testOption = new TestShippingOption();
        testOption.testSelectShippingOption(driver, prop, locator, user, tcList);

        wait.until(ExpectedConditions
                .elementToBeClickable(By.xpath(locator.getProperty("shipping.continue.button").toString())));
        continueButtonElement = driver
                .findElement(By.xpath(locator.getProperty("shipping.continue.button").toString()));
        actions.moveToElement(continueButtonElement).click(continueButtonElement);
        actions.perform();

        testCase.setStatus("PASS");
        tcList.add(testCase);
        logger.info("END verifyShippingAddress");
    }

    /**
     * Add a shipping address to the list of available addresses in Checkout -
     * Shipping page
     * 
     * @param driver
     * @param prop
     * @param locator
     * @param user
     * @param map
     * @param tcList
     * @throws Exception
     */
    public void verifyShippingAddress2(WebDriver driver, Properties prop, Properties locator, User user,
            Map<String, Boolean> map, List<TestCase> tcList) throws Exception {

        logger.info("BEGIN verifyShippingAddress2");
        String FUNCTIONALITY = "Add a shipping address to the list of available addresses in Checkout-shipping page";
        testCase = new TestCase("TC-14.2", "MOC-NIL", FUNCTIONALITY, "FAIL", "");
        Actions actions = new Actions(driver);
        WebDriverWait wait = (WebDriverWait) new WebDriverWait(driver, 10);
        WebElement continueButtonElement = null;

        TestShippingAddress testAddress = new TestShippingAddress();
        testAddress.testAddShippingAddress(driver, prop, locator, user, tcList);
        TestShippingOption testOption = new TestShippingOption();
        testOption.testSelectShippingOption(driver, prop, locator, user, tcList);

        wait.until(ExpectedConditions
                .elementToBeClickable(By.xpath(locator.getProperty("shipping.continue.button").toString())));
        continueButtonElement = driver
                .findElement(By.xpath(locator.getProperty("shipping.continue.button").toString()));
        actions.moveToElement(continueButtonElement).click(continueButtonElement);
        actions.perform();

        testCase.setStatus("PASS");
        tcList.add(testCase);
        logger.info("END verifyShippingAddress2");
    }

    /**
     * Select a shipping address from the available list of shipping addresses
     * in Checkout - Shipping page
     * 
     * @param driver
     * @param prop
     * @param locator
     * @param user
     * @param map
     * @param tcList
     * @throws Exception
     */
    public void verifyShippingAddress3(WebDriver driver, Properties prop, Properties locator, User user,
            Map<String, Boolean> map, List<TestCase> tcList) throws Exception {

        logger.info("BEGIN verifyShippingAddress3");
        String FUNCTIONALITY = "Select a shipping address from the available list of shipping addresses in Checkout - Shipping page";
        testCase = new TestCase("TC-14.3", "MOC-NIL", FUNCTIONALITY, "FAIL", "");

        Actions actions = new Actions(driver);
        WebDriverWait wait = (WebDriverWait) new WebDriverWait(driver, 10);
        WebElement continueButtonElement = null;

        TestShippingAddress testAddress = new TestShippingAddress();
        testAddress.testSelectShippingAddress(driver, prop, locator, user, tcList);
        TestShippingOption testOption = new TestShippingOption();
        testOption.testSelectShippingOption(driver, prop, locator, user, tcList);

        wait.until(ExpectedConditions
                .elementToBeClickable(By.xpath(locator.getProperty("shipping.continue.button").toString())));
        continueButtonElement = driver
                .findElement(By.xpath(locator.getProperty("shipping.continue.button").toString()));
        actions.moveToElement(continueButtonElement).click(continueButtonElement);
        actions.perform();

        testCase.setStatus("PASS");
        tcList.add(testCase);
        logger.info("END verifyShippingAddress3");
    }

    /**
     * Add a new international shipping address to the list of addresses in
     * Checkout - Shipping page
     * 
     * @param driver
     * @param prop
     * @param locator
     * @param user
     * @param map
     * @param data
     * @param tcList
     * @throws Exception
     */
    public void verifyShippingAddress4(WebDriver driver, Properties prop, Properties locator, User user,
            Map<String, Boolean> map, Properties data, List<TestCase> tcList) throws Exception {

        logger.info("BEGIN verifyShippingAddress4");
        String FUNCTIONALITY = "Add an international shipping address to the list of available addresses in Checkout-shipping page";
        testCase = new TestCase("TC-14.4", "MOC-NIL", FUNCTIONALITY, "FAIL", "");

        Actions actions = new Actions(driver);
        WebDriverWait wait = (WebDriverWait) new WebDriverWait(driver, 10);
        WebElement continueButtonElement = null;

        TestShippingAddress testAddress = new TestShippingAddress();
        testAddress.testAddShippingAddress(driver, data, locator, user, tcList);
        TestShippingOption testOption = new TestShippingOption();
        testOption.testSelectShippingOption(driver, prop, locator, user, tcList);

        wait.until(ExpectedConditions
                .elementToBeClickable(By.xpath(locator.getProperty("shipping.continue.button").toString())));
        continueButtonElement = driver
                .findElement(By.xpath(locator.getProperty("shipping.continue.button").toString()));
        actions.moveToElement(continueButtonElement).click(continueButtonElement);
        actions.perform();

        testCase.setStatus("PASS");
        tcList.add(testCase);
        logger.info("END verifyShippingAddress4");
    }

    /**
     * @return the testHelper
     */
    public TatchaTestHelper getTestHelper() {
        return testHelper;
    }

    /**
     * @param testHelper
     *            the testHelper to set
     */
    public void setTestHelper(TatchaTestHelper testHelper) {
        this.testHelper = testHelper;
    }
}
