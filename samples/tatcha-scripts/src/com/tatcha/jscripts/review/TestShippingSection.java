package com.tatcha.jscripts.review;

import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.tatcha.jscripts.dao.TestCase;
import com.tatcha.jscripts.dao.User;
import com.tatcha.jscripts.helper.TatchaTestHelper;
import com.tatcha.jscripts.shipping.TestShippingAddress;

/**
 * Class that checks shipping section of Checkout - Order Review page
 * 
 * @author Reshma
 *
 */
public class TestShippingSection {

    private TatchaTestHelper testHelper = new TatchaTestHelper();
    private final static Logger logger = Logger.getLogger(TestReviewOrder.class);
    private TestCase testCase;

    /**
     * Verify Shipping section of Order Review, and the edit button for Shipping
     * section
     * 
     * @param driver
     * @param prop
     * @param locator
     * @param user
     * @param map
     * @param tcList
     * @throws Exception
     */
    public void testShippingSection(WebDriver driver, Properties prop, Properties locator, User user,
            Map<String, Boolean> map, List<TestCase> tcList) throws Exception {

        logger.info("BEGIN testShippingSection");
        String FUNCTIONALITY = "Verify the edit button of shipping section in order review page";
        testCase = new TestCase("TC-13.1", "MOC-NIL", FUNCTIONALITY, "FAIL", "");

        WebDriverWait wait = (WebDriverWait) new WebDriverWait(driver, 10);
        WebDriverWait wait3 = (WebDriverWait) new WebDriverWait(driver, 3);

        Actions actions = new Actions(driver);
        WebElement editButtonElement = null;
        WebElement continueButtonElement = null;

        if (map.get("isLogged") && map.get("isUSAddress") && !map.get("isGiftCard") && map.get("isCreditCard")
                && !map.get("isRegister")) {
            logger.info("Condition 1");
            getTestHelper().logAssertion(getClass().getSimpleName(), "SHIPPING",
                    driver.findElement(By.xpath(locator.getProperty("reviewOrder1.shipping.title").toString()))
                            .getText());
            getTestHelper().logAssertion(getClass().getSimpleName(), "Shipping Address",
                    driver.findElement(
                            By.xpath(locator.getProperty("reviewOrder1.shipping.address.label").toString()))
                            .getText());
            getTestHelper().logAssertion(getClass().getSimpleName(), "Shipping Options",
                    driver.findElement(
                            By.xpath(locator.getProperty("reviewOrder1.shipping.option.label").toString()))
                            .getText());

            // Click edit Shipping
            logger.info("Click edit");
            editButtonElement = driver
                    .findElement(By.xpath(locator.getProperty("reviewOrder1.shipping.edit.button").toString()));
            actions.moveToElement(editButtonElement).click(editButtonElement);
            actions.perform();

        } else if (map.get("isLogged") && !map.get("isUSAddress") && !map.get("isGiftCard")
                && map.get("isCreditCard") && !map.get("isRegister")) {
            logger.info("Condition 2");
            getTestHelper().logAssertion(getClass().getSimpleName(), "SHIPPING",
                    driver.findElement(By.xpath(locator.getProperty("reviewOrder2.shipping.title").toString()))
                            .getText());
            getTestHelper().logAssertion(getClass().getSimpleName(), "Shipping Address",
                    driver.findElement(
                            By.xpath(locator.getProperty("reviewOrder2.shipping.address.label").toString()))
                            .getText());
            getTestHelper().logAssertion(getClass().getSimpleName(), "Shipping Options",
                    driver.findElement(
                            By.xpath(locator.getProperty("reviewOrder2.shipping.option.label").toString()))
                            .getText());

            // Click edit Shipping
            logger.info("Click edit");
            editButtonElement = driver
                    .findElement(By.xpath(locator.getProperty("reviewOrder2.shipping.edit.button").toString()));
            actions.moveToElement(editButtonElement).click(editButtonElement);
            actions.perform();
        }

        // Navigate from shipping to Review order
        wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.xpath(locator.getProperty("shipping.title").toString())));
        try {
            // Wait for gif image(loading) to become stale
            wait3.until(ExpectedConditions
                    .stalenessOf(driver.findElement(By.xpath("//div[@class='sk-fading-circle']"))));
            // wait.until(ExpectedConditions.stalenessOf(driver.findElement(By.xpath("//*[@id='ext-gen44']/body/div[15]"))));
        } catch (NoSuchElementException ne) {
            logger.info("Loading Gif image cannot be located");
        }
        wait.until(ExpectedConditions
                .elementToBeClickable(By.xpath(locator.getProperty("shipping.continue.button").toString())));
        continueButtonElement = driver
                .findElement(By.xpath(locator.getProperty("shipping.continue.button").toString()));
        actions.moveToElement(continueButtonElement).click(continueButtonElement);
        actions.perform();

        wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.xpath(locator.getProperty("payment.title").toString())));
        wait.until(ExpectedConditions
                .elementToBeClickable(By.xpath(locator.getProperty("payment.continue.button").toString())));
        continueButtonElement = driver
                .findElement(By.xpath(locator.getProperty("payment.continue.button").toString()));
        actions.moveToElement(continueButtonElement).click(continueButtonElement);
        actions.perform();

        wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.xpath(locator.getProperty("reviewOrder.title").toString())));

        testCase.setStatus("PASS");
        tcList.add(testCase);
        logger.info("END testShippingSection");
    }

    /**
     * Verify the Payment section of order review page in different conditions
     * 
     * @param driver
     * @param prop
     * @param locator
     * @param user
     * @param map
     * @param tcList
     * @throws Exception
     */
    public void testReviewShipping(WebDriver driver, Properties prop, Properties locator, User user,
            Map<String, Boolean> map, List<TestCase> tcList) throws Exception {

        logger.info("BEGIN testReviewShipping");
        String FUNCTIONALITY = "Review shipping details of Order Review page";
        testCase = new TestCase("TC-13.2", "MOC-NIL", FUNCTIONALITY, "FAIL", "");

        if (map.get("isLogged") && map.get("isUSAddress")) {
            logger.info("Condition 1");
            getTestHelper().logAssertion(getClass().getSimpleName(), "SHIPPING",
                    driver.findElement(By.xpath(locator.getProperty("reviewOrder1.shipping.title").toString()))
                            .getText());
            getTestHelper().logAssertion(getClass().getSimpleName(), "Shipping Address",
                    driver.findElement(
                            By.xpath(locator.getProperty("reviewOrder1.shipping.address.label").toString()))
                            .getText());
            getTestHelper().logAssertion(getClass().getSimpleName(), "Shipping Options",
                    driver.findElement(
                            By.xpath(locator.getProperty("reviewOrder1.shipping.option.label").toString()))
                            .getText());

        } else if (map.get("isLogged") && !map.get("isUSAddress")) {
            logger.info("Condition 2");
            getTestHelper().logAssertion(getClass().getSimpleName(), "SHIPPING",
                    driver.findElement(By.xpath(locator.getProperty("reviewOrder2.shipping.title").toString()))
                            .getText());
            getTestHelper().logAssertion(getClass().getSimpleName(), "Shipping Address",
                    driver.findElement(
                            By.xpath(locator.getProperty("reviewOrder2.shipping.address.label").toString()))
                            .getText());
            getTestHelper().logAssertion(getClass().getSimpleName(), "Shipping Options",
                    driver.findElement(
                            By.xpath(locator.getProperty("reviewOrder2.shipping.option.label").toString()))
                            .getText());

        } else if (!map.get("isLogged") && map.get("isUSAddress")) {
            logger.info("Condition 3");
            getTestHelper().logAssertion(getClass().getSimpleName(), "SHIPPING",
                    driver.findElement(By.xpath(locator.getProperty("reviewOrder5.shipping.title").toString()))
                            .getText());
            getTestHelper().logAssertion(getClass().getSimpleName(), "Shipping Address",
                    driver.findElement(
                            By.xpath(locator.getProperty("reviewOrder5.shipping.address.label").toString()))
                            .getText());
            getTestHelper().logAssertion(getClass().getSimpleName(), "Shipping Options",
                    driver.findElement(
                            By.xpath(locator.getProperty("reviewOrder5.shipping.option.label").toString()))
                            .getText());
        } else if (!map.get("isLogged") && !map.get("isUSAddress")) {
            logger.info("Condition 4");
            getTestHelper().logAssertion(getClass().getSimpleName(), "SHIPPING",
                    driver.findElement(By.xpath(locator.getProperty("reviewOrder6.shipping.title").toString()))
                            .getText());
            getTestHelper().logAssertion(getClass().getSimpleName(), "Shipping Address",
                    driver.findElement(
                            By.xpath(locator.getProperty("reviewOrder6.shipping.address.label").toString()))
                            .getText());
            getTestHelper().logAssertion(getClass().getSimpleName(), "Shipping Options",
                    driver.findElement(
                            By.xpath(locator.getProperty("reviewOrder6.shipping.option.label").toString()))
                            .getText());
        }

        testCase.setStatus("PASS");
        tcList.add(testCase);
        logger.info("END testReviewShipping");
    }

    /**
     * Click edit shipping section in order review and select a new shipping
     * address
     * 
     * @param driver
     * @param prop
     * @param locator
     * @param user
     * @param map
     * @param tcList
     * @throws Exception
     */
    public void testEditShipping(WebDriver driver, Properties prop, Properties locator, User user,
            Map<String, Boolean> map, List<TestCase> tcList) throws Exception {

        logger.info("BEGIN testEditShipping");
        String FUNCTIONALITY = "Edit shipping details from order review page";
        testCase = new TestCase("TC-13.3", "MOC-NIL", FUNCTIONALITY, "FAIL", "");

        WebDriverWait wait = (WebDriverWait) new WebDriverWait(driver, 10);
        WebDriverWait wait3 = (WebDriverWait) new WebDriverWait(driver, 3);

        TestShippingAddress testAddress = new TestShippingAddress();

        Actions actions = new Actions(driver);
        WebElement editButtonElement = null;
        WebElement continueButtonElement = null;

        if (map.get("isLogged") && map.get("isUSAddress") && !map.get("isGiftCard") && map.get("isCreditCard")
                && !map.get("isRegister")) {
            logger.info("Condition 1");
            getTestHelper().logAssertion(getClass().getSimpleName(), "SHIPPING",
                    driver.findElement(By.xpath(locator.getProperty("reviewOrder1.shipping.title").toString()))
                            .getText());
            getTestHelper().logAssertion(getClass().getSimpleName(), "Shipping Address",
                    driver.findElement(
                            By.xpath(locator.getProperty("reviewOrder1.shipping.address.label").toString()))
                            .getText());
            getTestHelper().logAssertion(getClass().getSimpleName(), "Shipping Options",
                    driver.findElement(
                            By.xpath(locator.getProperty("reviewOrder1.shipping.option.label").toString()))
                            .getText());

            // Click edit Shipping
            logger.info("Click edit");
            editButtonElement = driver
                    .findElement(By.xpath(locator.getProperty("reviewOrder1.shipping.edit.button").toString()));
            actions.moveToElement(editButtonElement).click(editButtonElement);
            actions.perform();

        } else if (map.get("isLogged") && !map.get("isUSAddress") && !map.get("isGiftCard")
                && map.get("isCreditCard") && !map.get("isRegister")) {
            logger.info("Condition 2");
            getTestHelper().logAssertion(getClass().getSimpleName(), "SHIPPING",
                    driver.findElement(By.xpath(locator.getProperty("reviewOrder2.shipping.title").toString()))
                            .getText());
            getTestHelper().logAssertion(getClass().getSimpleName(), "Shipping Address",
                    driver.findElement(
                            By.xpath(locator.getProperty("reviewOrder2.shipping.address.label").toString()))
                            .getText());
            getTestHelper().logAssertion(getClass().getSimpleName(), "Shipping Options",
                    driver.findElement(
                            By.xpath(locator.getProperty("reviewOrder2.shipping.option.label").toString()))
                            .getText());

            // Click edit Shipping
            logger.info("Click edit");
            editButtonElement = driver
                    .findElement(By.xpath(locator.getProperty("reviewOrder2.shipping.edit.button").toString()));
            actions.moveToElement(editButtonElement).click(editButtonElement);
            actions.perform();
        }

        // Navigate from shipping to Review order
        wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.xpath(locator.getProperty("shipping.title").toString())));
        try {
            // Wait for gif image(loading) to become stale
            wait3.until(ExpectedConditions
                    .stalenessOf(driver.findElement(By.xpath("//div[@class='sk-fading-circle']"))));
            // wait.until(ExpectedConditions.stalenessOf(driver.findElement(By.xpath("//*[@id='ext-gen44']/body/div[15]"))));
        } catch (NoSuchElementException ne) {
            logger.info("Loading Gif image cannot be located");
        }

        testAddress.testSelectShippingAddress(driver, prop, locator, user, tcList);

        try {
            // Wait for gif image(loading) to become stale
            wait3.until(ExpectedConditions
                    .stalenessOf(driver.findElement(By.xpath("//div[@class='sk-fading-circle']"))));
            // wait.until(ExpectedConditions.stalenessOf(driver.findElement(By.xpath("//*[@id='ext-gen44']/body/div[15]"))));
        } catch (NoSuchElementException ne) {
            logger.info("Loading Gif image cannot be located on selecting different address");
        }

        wait.until(ExpectedConditions
                .elementToBeClickable(By.xpath(locator.getProperty("shipping.continue.button").toString())));
        continueButtonElement = driver
                .findElement(By.xpath(locator.getProperty("shipping.continue.button").toString()));
        actions.moveToElement(continueButtonElement).click(continueButtonElement);
        actions.perform();

        wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.xpath(locator.getProperty("payment.title").toString())));
        if (map.containsKey("gotoOrderReview") && map.get("gotoOrderReview")) {
            wait.until(ExpectedConditions
                    .elementToBeClickable(By.xpath(locator.getProperty("payment.continue.button").toString())));
            continueButtonElement = driver
                    .findElement(By.xpath(locator.getProperty("payment.continue.button").toString()));
            actions.moveToElement(continueButtonElement).click(continueButtonElement);
            actions.perform();
            wait.until(ExpectedConditions
                    .visibilityOfElementLocated(By.xpath(locator.getProperty("reviewOrder.title").toString())));
        }

        testCase.setStatus("PASS");
        tcList.add(testCase);
        logger.info("END testEditShipping");
    
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
