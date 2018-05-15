package com.tatcha.jscripts.payment;

import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.tatcha.jscripts.dao.TestCase;
import com.tatcha.jscripts.dao.User;
import com.tatcha.jscripts.helper.TatchaTestHelper;

/**
 * Class that checks different options of 
 * Checkout - Payment page
 * 
 * @author reshma
 *
 */
public class PaymentOption {

    private TatchaTestHelper testHelper = new TatchaTestHelper();
    private final static Logger logger = Logger.getLogger(PaymentOption.class);
    private TestCase testCase;

    /**
     * Verify payment option of checkout by selecting a credit card
     * 
     * @param driver
     * @param prop
     * @param locator
     * @param user
     * @param map
     * @param tcList
     * @throws Exception
     */
    public void verifyPaymentOption(WebDriver driver, Properties prop, Properties locator, User user, Map<String,Boolean> map, List<TestCase> tcList) throws Exception {
        try {
            logger.info("BEGIN verifyPaymentOption");     
            String FUNCTIONALITY = "Verify Payment option by selecting Credit card";
            testCase = new TestCase("TC-5.1", "MOC-NIL", FUNCTIONALITY, "FAIL", "");
            Actions actions = new Actions(driver);
            WebDriverWait wait = (WebDriverWait) new WebDriverWait(driver, 10);
            WebElement continueButtonElement = null;

            TestCreditCard testcredit = new TestCreditCard();
            testcredit.testSelectCreditCard(driver, prop, locator, user, tcList);

            wait.until(ExpectedConditions.elementToBeClickable(By.xpath(locator.getProperty("payment.continue.button").toString())));
            continueButtonElement = driver.findElement(By.xpath(locator.getProperty("payment.continue.button").toString()));
            actions.moveToElement(continueButtonElement).click(continueButtonElement);
            actions.perform();
            
            testCase.setStatus("PASS");
            tcList.add(testCase);
            logger.info("END verifyPaymentOption");
        } catch (NoSuchElementException ne) {
            System.err.println(getClass().getSimpleName() + " : ELEMENT NOT FOUND " + ne.toString());
        } catch (ElementNotVisibleException nv) {
            System.err.println(getClass().getSimpleName() + " : ELEMENT NOT VISIBLE " + nv.toString());
        } catch (TimeoutException te) {
            System.err.println(getClass().getSimpleName() + " : TIMEOUT " + te.toString());
        } catch (StaleElementReferenceException sr) {
            System.err.println(getClass().getSimpleName() + " : STALE ELE REF " + sr.toString());
        } catch (WebDriverException we) {
            System.err.println(getClass().getSimpleName() + " : WEBDRIVER ISSUE " + we.toString());
        }
    }
    
    /**
     * Verify payment option of checkout by adding a gift card
     *      
     * @param driver
     * @param prop
     * @param locator
     * @param user
     * @param map
     * @param tcList
     * @throws Exception
     */
    public void verifyPaymentOption2(WebDriver driver, Properties prop, Properties locator, User user, Map<String,Boolean> map, List<TestCase> tcList) throws Exception {
        try {
            logger.info("BEGIN verifyPaymentOption2");   
            String FUNCTIONALITY = "Verify payment option by adding a gift card";
            testCase = new TestCase("TC-2.1", "MOC-NIL", FUNCTIONALITY, "FAIL", "");
            Actions actions = new Actions(driver);
            WebDriverWait wait = (WebDriverWait) new WebDriverWait(driver, 10);
            WebElement continueButtonElement = null;

            TestGiftCard testgift = new TestGiftCard();
            testgift.testAddGiftCard(driver, prop, locator, user, false, tcList);

            wait.until(ExpectedConditions.elementToBeClickable(By.xpath(locator.getProperty("payment.continue.button").toString())));
            continueButtonElement = driver.findElement(By.xpath(locator.getProperty("payment.continue.button").toString()));
            actions.moveToElement(continueButtonElement).click(continueButtonElement);
            actions.perform();
            
            testCase.setStatus("PASS");
            tcList.add(testCase);
            logger.info("END verifyPaymentOption2"); 
        } catch (NoSuchElementException ne) {
            System.err.println(getClass().getSimpleName() + " : ELEMENT NOT FOUND " + ne.toString());
        } catch (ElementNotVisibleException nv) {
            System.err.println(getClass().getSimpleName() + " : ELEMENT NOT VISIBLE " + nv.toString());
        } catch (TimeoutException te) {
            System.err.println(getClass().getSimpleName() + " : TIMEOUT " + te.toString());
        } catch (StaleElementReferenceException sr) {
            System.err.println(getClass().getSimpleName() + " : STALE ELE REF " + sr.toString());
        } catch (WebDriverException we) {
            System.err.println(getClass().getSimpleName() + " : WEBDRIVER ISSUE " + we.toString());
        }
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
