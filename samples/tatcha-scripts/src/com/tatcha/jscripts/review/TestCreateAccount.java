package com.tatcha.jscripts.review;

import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.tatcha.jscripts.dao.TestCase;
import com.tatcha.jscripts.dao.User;
import com.tatcha.jscripts.helper.TatchaTestHelper;

/**
 * Class that checks Create account option of Checkout - Payment page
 * 
 * @author Reshma
 *
 */
public class TestCreateAccount {
    private TatchaTestHelper testHelper = new TatchaTestHelper();
    private final static Logger logger = Logger.getLogger(TestCreateAccount.class);
    private TestCase testCase;

    /**
     * Verify the Register account section of Order Review when the user is a
     * guest user
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
    public void testCreateAccount(WebDriver driver, Properties prop, Properties locator, User user,
            Map<String, Boolean> map, Properties data, List<TestCase> tcList) throws Exception {


        logger.info("BEGIN testCreateAccount");
        String FUNCTIONALITY = "Create account on checkout";
        testCase = new TestCase("TC-10.1", "MOC-NIL", FUNCTIONALITY, "FAIL", "");
        WebDriverWait wait = (WebDriverWait) new WebDriverWait(driver, 10);

        if (map.get("isUSAddress")) {
            logger.info("Inside Condition 1");
            getTestHelper().logAssertion(getClass().getSimpleName(),
                    prop.getProperty("checkout.guest.emailReceipt.title").toString(),
                    driver.findElement(
                            By.xpath(locator.getProperty("reviewOrder1.guest.emailReceipt.title").toString()))
                            .getText());
            getTestHelper()
                    .logAssertion(getClass().getSimpleName(),
                            prop.getProperty("checkout.guest.emailReceipt.checkbox.label").toString(),
                            driver.findElement(By.xpath(locator
                                    .getProperty("reviewOrder1.guest.emailReceipt.checkbox.label").toString()))
                                    .getText());
            driver.findElement(By.xpath(locator.getProperty("reviewOrder1.guest.emailReceipt.checkbox").toString()))
                    .click();

            getTestHelper().logAssertion(getClass().getSimpleName(),
                    prop.getProperty("checkout.guest.createAccnt.title").toString(),
                    driver.findElement(
                            By.xpath(locator.getProperty("reviewOrder1.guest.createAccnt.title").toString()))
                            .getText());
            getTestHelper().logAssertion(getClass().getSimpleName(),
                    prop.getProperty("checkout.guest.createAccnt.description").toString(),
                    driver.findElement(
                            By.xpath(locator.getProperty("reviewOrder1.guest.createAccnt.description").toString()))
                            .getText());
            getTestHelper()
                    .logAssertion(getClass().getSimpleName(),
                            prop.getProperty("checkout.guest.createAccnt.checkbox.label").toString(),
                            driver.findElement(By.xpath(locator
                                    .getProperty("reviewOrder1.guest.createAccnt.checkbox.label").toString()))
                                    .getText());
        } else if (!map.get("isUSAddress")) {
            logger.info("Inside Condition 2");
            getTestHelper().logAssertion(getClass().getSimpleName(),
                    prop.getProperty("checkout.guest.emailReceipt.title").toString(),
                    driver.findElement(
                            By.xpath(locator.getProperty("reviewOrder2.guest.emailReceipt.title").toString()))
                            .getText());
            getTestHelper()
                    .logAssertion(getClass().getSimpleName(),
                            prop.getProperty("checkout.guest.emailReceipt.checkbox.label").toString(),
                            driver.findElement(By.xpath(locator
                                    .getProperty("reviewOrder2.guest.emailReceipt.checkbox.label").toString()))
                                    .getText());
            driver.findElement(By.xpath(locator.getProperty("reviewOrder2.guest.emailReceipt.checkbox").toString()))
                    .click();

            getTestHelper().logAssertion(getClass().getSimpleName(),
                    prop.getProperty("checkout.guest.createAccnt.title").toString(),
                    driver.findElement(
                            By.xpath(locator.getProperty("reviewOrder2.guest.createAccnt.title").toString()))
                            .getText());
            getTestHelper().logAssertion(getClass().getSimpleName(),
                    prop.getProperty("checkout.guest.createAccnt.description").toString(),
                    driver.findElement(
                            By.xpath(locator.getProperty("reviewOrder2.guest.createAccnt.description").toString()))
                            .getText());
            getTestHelper()
                    .logAssertion(getClass().getSimpleName(),
                            prop.getProperty("checkout.guest.createAccnt.checkbox.label").toString(),
                            driver.findElement(By.xpath(locator
                                    .getProperty("reviewOrder2.guest.createAccnt.checkbox.label").toString()))
                                    .getText());
        }

        if (map.get("isRegister")) {
            logger.info("User is not logged-in");
            if (map.get("isUSAddress")) {
                logger.info("Condition 1");
                driver.findElement(
                        By.xpath(locator.getProperty("reviewOrder1.guest.createAccnt.checkbox").toString()))
                        .click();
                wait.until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath(locator.getProperty("reviewOrder1.guest.createAccnt.password.label").toString())));
                getTestHelper()
                        .logAssertion(getClass().getSimpleName(), "Minimum 8 characters",
                                driver.findElement(By.xpath(locator
                                        .getProperty("reviewOrder1.guest.createAccnt.password.info").toString()))
                                        .getText());

                WebElement passwordElement = driver.findElement(
                        By.xpath(locator.getProperty("reviewOrder1.guest.createAccnt.password").toString()));
                passwordElement.clear();
                passwordElement.sendKeys(data.getProperty("password").toString());
                WebElement confirmPasswordElement = driver.findElement(
                        By.xpath(locator.getProperty("reviewOrder1.guest.createAccnt.confirmPassword").toString()));
                confirmPasswordElement.clear();
                confirmPasswordElement.sendKeys(data.getProperty("password").toString());

            } else if (!map.get("isUSAddress")) {
                logger.info("Condition 2");
                driver.findElement(
                        By.xpath(locator.getProperty("reviewOrder2.guest.createAccnt.checkbox").toString()))
                        .click();
                wait.until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath(locator.getProperty("reviewOrder2.guest.createAccnt.password.label").toString())));
                getTestHelper()
                        .logAssertion(getClass().getSimpleName(), "Minimum 8 characters",
                                driver.findElement(By.xpath(locator
                                        .getProperty("reviewOrder2.guest.createAccnt.password.info").toString()))
                                        .getText());

                WebElement passwordElement = driver.findElement(
                        By.xpath(locator.getProperty("reviewOrder2.guest.createAccnt.password").toString()));
                passwordElement.clear();
                passwordElement.sendKeys(data.getProperty("password").toString());
                WebElement confirmPasswordElement = driver.findElement(
                        By.xpath(locator.getProperty("reviewOrder2.guest.createAccnt.confirmPassword").toString()));
                confirmPasswordElement.clear();
                confirmPasswordElement.sendKeys(data.getProperty("password").toString());
            }
        }
        testCase.setStatus("PASS");
        tcList.add(testCase);
        logger.info("END testCreateAccount");
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
