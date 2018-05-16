package com.tatcha.jscripts.review;

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
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.tatcha.jscripts.dao.TestCase;
import com.tatcha.jscripts.dao.User;
import com.tatcha.jscripts.helper.TatchaTestHelper;
import com.tatcha.jscripts.summary.TestItems;
import com.tatcha.jscripts.summary.TestSummary;

/**
 * Class that checks different sections of 
 * Checkout - Order Review page
 * 
 * @author reshma
 *
 */
public class ReviewOrder {

    private TatchaTestHelper testHelper = new TatchaTestHelper();
    private final static Logger logger = Logger.getLogger(ReviewOrder.class);
    private TestCase testCase;
    
    /**
     * Verify Review Order page and edit button 
     * of Shipping and Payment section
     * 
     * @param driver
     * @param prop
     * @param locator
     * @param user
     * @param map
     * @param tcList
     * @throws Exception
     */
    public void verifyReviewOrder1(WebDriver driver, Properties prop, Properties locator, User user, Map<String,Boolean> map, List<TestCase> tcList) throws Exception {
        try {
            logger.info("BEGIN verifyReviewOrder");     
            String FUNCTIONALITY = "Verify Review order and edit button of shipping and payment section";
            testCase = new TestCase("TC-9.1", "MOC-NIL", FUNCTIONALITY, "FAIL", "");

            WebDriverWait wait = (WebDriverWait) new WebDriverWait(driver, 10);

            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(locator.getProperty("checkout.title").toString())));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locator.getProperty("checkout.step3.title").toString())));

            TestShippingSection testShipping = new TestShippingSection();
            testShipping.testShippingSection(driver, prop, locator, user, map, tcList);
            TestPaymentSection testPayment = new TestPaymentSection();
            testPayment.testPaymentSection(driver, prop, locator, user, map, tcList);
            TestReviewOrder testReview = new TestReviewOrder();
            testReview.testReviewOrder(driver, prop, locator, user, map, tcList);
            TestSummary testSummary = new TestSummary();
            testSummary.testSummary(driver, prop, locator, user, map, tcList);
            TestItems testItem = new TestItems();
            testItem.testItems(driver, prop, locator, user, map, tcList);
            
            // Click place order button
            By placeOrderButtonLocator = By.xpath(locator.getProperty("reviewOrder.placeOrder.button").toString());
            WebElement placeOrderButtonElement = driver.findElement(placeOrderButtonLocator);
            if(placeOrderButtonElement.isEnabled()) {
                placeOrderButtonElement.click();
            }
            testCase.setStatus("PASS");
            tcList.add(testCase);
            logger.info("END verifyReviewOrder"); 
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
     * Verify Review order page for logged-in user
     * 
     * @param driver
     * @param prop
     * @param locator
     * @param user
     * @param map
     * @param tcList
     * @throws Exception
     */
    public void verifyReviewOrder2(WebDriver driver, Properties prop, Properties locator, User user, Map<String,Boolean> map, List<TestCase> tcList) throws Exception {
        try {
            logger.info("BEGIN verifyReviewOrder2");     
            String FUNCTIONALITY = "Verify Review order page";
            testCase = new TestCase("TC-9.2", "MOC-NIL", FUNCTIONALITY, "FAIL", "");

            WebDriverWait wait = (WebDriverWait) new WebDriverWait(driver, 10);

            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(locator.getProperty("checkout.title").toString())));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locator.getProperty("checkout.step3.title").toString())));

            TestShippingSection testShipping = new TestShippingSection();
            testShipping.testReviewShipping(driver, prop, locator, user, map, tcList);
            TestPaymentSection testPayment = new TestPaymentSection();
            testPayment.testReviewPayment(driver, prop, locator, user, map, tcList);
            TestReviewOrder testReview = new TestReviewOrder();
            testReview.testReviewOrder(driver, prop, locator, user, map, tcList);
            
            // Click place order button
            By placeOrderButtonLocator = By.xpath(locator.getProperty("reviewOrder.placeOrder.button").toString());
            WebElement placeOrderButtonElement = driver.findElement(placeOrderButtonLocator);
            if(placeOrderButtonElement.isEnabled()) {
                placeOrderButtonElement.click();
            }
            testCase.setStatus("PASS");
            tcList.add(testCase);
            logger.info("END verifyReviewOrder2");
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
     * Verify Review order and edit shipping and payment section separately
     * 
     * @param driver
     * @param prop
     * @param locator
     * @param user
     * @param map
     * @param tcList
     * @throws Exception
     */
    public void verifyReviewOrder3(WebDriver driver, Properties prop, Properties locator, User user, Map<String,Boolean> map, List<TestCase> tcList) throws Exception {
        try {
            logger.info("BEGIN verifyReviewOrder3");     
            String FUNCTIONALITY = "Verify Review order and edit shipping and payment section separately";
            testCase = new TestCase("TC-9.3", "MOC-NIL", FUNCTIONALITY, "FAIL", "");

            WebDriverWait wait = (WebDriverWait) new WebDriverWait(driver, 10);

            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(locator.getProperty("checkout.title").toString())));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locator.getProperty("checkout.step3.title").toString())));

            TestShippingSection testShipping = new TestShippingSection();
            testShipping.testEditShipping(driver, prop, locator, user, map, tcList);
            TestPaymentSection testPayment = new TestPaymentSection();
            testPayment.testEditPayment(driver, prop, locator, user, map, tcList);
            TestReviewOrder testReview = new TestReviewOrder();
            testReview.testReviewOrder(driver, prop, locator, user, map, tcList);
            TestSummary testSummary = new TestSummary();
            testSummary.testSummary(driver, prop, locator, user, map, tcList);
            TestItems testItem = new TestItems();
            testItem.testItems(driver, prop, locator, user, map, tcList);
            
            // Click place order button
            By placeOrderButtonLocator = By.xpath(locator.getProperty("reviewOrder.placeOrder.button").toString());
            WebElement placeOrderButtonElement = driver.findElement(placeOrderButtonLocator);
            if(placeOrderButtonElement.isEnabled()) {
                placeOrderButtonElement.click();
            }
            testCase.setStatus("PASS");
            tcList.add(testCase);
            logger.info("END verifyReviewOrder3"); 
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
     * Verify Review order and edit shipping and payment section
     * 
     * @param driver
     * @param prop
     * @param locator
     * @param user
     * @param map
     * @param tcList
     * @throws Exception
     */
    public void verifyReviewOrder4(WebDriver driver, Properties prop, Properties locator, User user, Map<String,Boolean> map, List<TestCase> tcList) throws Exception {
        try {
            logger.info("BEGIN verifyReviewOrder4");     
            String FUNCTIONALITY = "Verify review order and edit shipping and payment";
            testCase = new TestCase("TC-9.4", "MOC-NIL", FUNCTIONALITY, "FAIL", "");

            WebDriverWait wait = (WebDriverWait) new WebDriverWait(driver, 10);

            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(locator.getProperty("checkout.title").toString())));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locator.getProperty("checkout.step3.title").toString())));

            TestShippingSection testShipping = new TestShippingSection();
            testShipping.testEditShipping(driver, prop, locator, user, map, tcList);
            TestPaymentSection testPayment = new TestPaymentSection();
            testPayment.testEditPaymentBilling(driver, prop, locator, user, map, tcList);
            testPayment.testReviewPayment(driver, prop, locator, user, map, tcList);
            TestReviewOrder testReview = new TestReviewOrder();
            testReview.testReviewOrder(driver, prop, locator, user, map, tcList);
            TestSummary testSummary = new TestSummary();
            testSummary.testSummary(driver, prop, locator, user, map, tcList);
            TestItems testItem = new TestItems();
            testItem.testItems(driver, prop, locator, user, map, tcList);
            
            // Click place order button
            By placeOrderButtonLocator = By.xpath(locator.getProperty("reviewOrder.placeOrder.button").toString());
            WebElement placeOrderButtonElement = driver.findElement(placeOrderButtonLocator);
            if(placeOrderButtonElement.isEnabled()) {
                placeOrderButtonElement.click();
            }
            testCase.setStatus("PASS");
            tcList.add(testCase);
            logger.info("END verifyReviewOrder4"); 
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
     * Verify Review Order page when cart has egift certificate
     * 
     * @param driver
     * @param prop
     * @param locator
     * @param user
     * @param map
     * @param tcList
     * @throws Exception
     */
    public void verifyEGiftReviewOrder(WebDriver driver, Properties prop, Properties locator, User user, Map<String,Boolean> map, List<TestCase> tcList) throws Exception {
        try {
            logger.info("BEGIN verifyEGiftReviewOrder");     
            String FUNCTIONALITY = "Verify Review order when EGift Certificate only in cart";
            testCase = new TestCase("TC-9.5", "MOC-NIL", FUNCTIONALITY, "FAIL", "");

            WebDriverWait wait = (WebDriverWait) new WebDriverWait(driver, 10);

            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(locator.getProperty("checkout.title").toString())));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='ext-gen44']/body/main/div[1]/div/ul/li[2]/a")));

            TestPaymentSection testPayment = new TestPaymentSection();
            testPayment.testReviewEGiftPayment(driver, prop, locator, user, map, tcList);
            TestSummary testSummary = new TestSummary();
            testSummary.testEgiftSummary(driver, prop, locator, user, map, tcList);
            TestItems testItem = new TestItems();
            testItem.testEgiftItem(driver, prop, locator, user, map, tcList);
            
            // Click place order button
            By placeOrderButtonLocator = By.xpath(locator.getProperty("reviewOrder.placeOrder.button").toString());
            WebElement placeOrderButtonElement = driver.findElement(placeOrderButtonLocator);
            if(placeOrderButtonElement.isEnabled()) {
                placeOrderButtonElement.click();
            }
            testCase.setStatus("PASS");
            tcList.add(testCase);
            logger.info("END verifyEGiftReviewOrder"); 
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
     * Verify Review order page for Guest user,
     * User Registration happens depending on 
     * the flag set in the map parameter
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
    public void verifyGuestReviewOrder(WebDriver driver, Properties prop, Properties locator, User user, Map<String,Boolean> map, Properties data, List<TestCase> tcList) throws Exception {
        try {
            logger.info("BEGIN verifyGuestReviewOrder");     
            String FUNCTIONALITY = "Verify Review Order for guest checkout";
            testCase = new TestCase("TC-9.6", "MOC-NIL", FUNCTIONALITY, "FAIL", "");

            WebDriverWait wait = (WebDriverWait) new WebDriverWait(driver, 10);

            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(locator.getProperty("checkout.title").toString())));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locator.getProperty("checkout.step3.title").toString())));

            TestShippingSection testShipping = new TestShippingSection();
            testShipping.testReviewShipping(driver, prop, locator, user, map, tcList);
            TestPaymentSection testPayment = new TestPaymentSection();
            testPayment.testReviewPayment(driver, prop, locator, user, map, tcList);
            TestCreateAccount testCreateAccnt = new TestCreateAccount();
            testCreateAccnt.testCreateAccount(driver, prop, locator, user, map, data, tcList);
            TestReviewOrder testReview = new TestReviewOrder();
            testReview.testGuestReviewOrder(driver, prop, locator, user, map, data, tcList);
            
            // Click place order button
            By placeOrderButtonLocator = By.xpath(locator.getProperty("reviewOrder.placeOrder.button").toString());
            WebElement placeOrderButtonElement = driver.findElement(placeOrderButtonLocator);
            if(placeOrderButtonElement.isEnabled()) {
                placeOrderButtonElement.click();
            }
            testCase.setStatus("PASS");
            tcList.add(testCase);
            logger.info("END verifyGuestReviewOrder");    
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
