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
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.tatcha.jscripts.dao.TestCase;
import com.tatcha.jscripts.dao.User;
import com.tatcha.jscripts.helper.TatchaTestHelper;
import com.tatcha.jscripts.payment.TestBillingAddress;
import com.tatcha.jscripts.payment.TestCreditCard;

/**
 * Class that checks payment section of 
 * Checkout - Order Review page
 * 
 * @author reshma
 *
 */
public class TestPaymentSection {

    private TatchaTestHelper testHelper = new TatchaTestHelper();
    private final static Logger logger = Logger.getLogger(TestReviewOrder.class);
    private TestCase testCase;

    /**
     * Verify Payment section of Order Review, 
     * and the edit button for Payment section
     * 
     * @param driver
     * @param prop
     * @param locator
     * @param user
     * @param map
     * @param tcList
     */
    public void testPaymentSection(WebDriver driver, Properties prop, Properties locator, User user, Map<String,Boolean> map, List<TestCase> tcList) {
        try {
            logger.info("BEGIN testPaymentSection"); 
            String FUNCTIONALITY = "Verify the edit button of payment section in order review page";
            testCase = new TestCase("TC-11.1", "MOC-NIL", FUNCTIONALITY, "FAIL", "");
            
            WebDriverWait wait = (WebDriverWait) new WebDriverWait(driver, 10);
            Actions actions = new Actions(driver);
            WebElement editButtonElement = null;
            
            if(map.get("isLogged") && map.get("isUSAddress") && !map.get("isGiftCard") && map.get("isCreditCard") && !map.get("isRegister")) {
                logger.info("Condition 1");     
                getTestHelper().logAssertion(getClass().getSimpleName(), "PAYMENT", driver.findElement(By.xpath(locator.getProperty("reviewOrder1.payment.title").toString())).getText());
                getTestHelper().logAssertion(getClass().getSimpleName(), "Billing Address", driver.findElement(By.xpath(locator.getProperty("reviewOrder1.payment.billing").toString())).getText());
                getTestHelper().logAssertion(getClass().getSimpleName(), "Credit Card", driver.findElement(By.xpath(locator.getProperty("reviewOrder1.payment.creditCard").toString())).getText());
                
                // Click edit Payment
                logger.info("Click edit");     
                editButtonElement = driver.findElement(By.xpath(locator.getProperty("reviewOrder1.payment.edit.button").toString()));
                actions.moveToElement(editButtonElement).click(editButtonElement);
                actions.perform();
            } else if(map.get("isLogged") && !map.get("isUSAddress") && !map.get("isGiftCard") && map.get("isCreditCard") && !map.get("isRegister")) {
                logger.info("Condition 2");     
                getTestHelper().logAssertion(getClass().getSimpleName(), "PAYMENT", driver.findElement(By.xpath(locator.getProperty("reviewOrder2.payment.title").toString())).getText());
                getTestHelper().logAssertion(getClass().getSimpleName(), "Billing Address", driver.findElement(By.xpath(locator.getProperty("reviewOrder2.payment.billing").toString())).getText());
                getTestHelper().logAssertion(getClass().getSimpleName(), "Credit Card", driver.findElement(By.xpath(locator.getProperty("reviewOrder2.payment.creditCard").toString())).getText());
                
                // Click edit Payment
                logger.info("Click edit");     
                editButtonElement = driver.findElement(By.xpath(locator.getProperty("reviewOrder2.payment.edit.button").toString()));
                actions.moveToElement(editButtonElement).click(editButtonElement);
                actions.perform();
            }
            
            // Navigate from payment to Review order
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locator.getProperty("payment.title").toString())));
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath(locator.getProperty("payment.continue.button").toString())));
            driver.findElement(By.xpath(locator.getProperty("payment.continue.button").toString())).click();

            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locator.getProperty("reviewOrder.title").toString())));
            
            testCase.setStatus("PASS");
            tcList.add(testCase);
            logger.info("END testPaymentSection");  
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
     * Review payment section of order review when egift is in cart
     * 
     * @param driver
     * @param prop
     * @param locator
     * @param user
     * @param map
     * @param tcList
     */
    public void testReviewEGiftPayment(WebDriver driver, Properties prop, Properties locator, User user, Map<String,Boolean> map, List<TestCase> tcList) {
        try {
            logger.info("BEGIN testReviewEGiftPayment");     
            String FUNCTIONALITY = "Verify the payment section in order review page when egift in cart";
            testCase = new TestCase("TC-11.2", "MOC-NIL", FUNCTIONALITY, "FAIL", "");
            
            getTestHelper().logAssertion(getClass().getSimpleName(), "PAYMENT", driver.findElement(By.xpath("//*[@id='ext-gen44']/body/main/div[1]/div/div/div[1]/form/div[1]/div[2]/div[2]/div[1]/div[1]/h4")).getText());
//            getTestHelper().logAssertion(getClass().getSimpleName(), "Billing Address", driver.findElement(By.xpath(locator.getProperty("reviewOrder1.payment.billing").toString())).getText());
            getTestHelper().logAssertion(getClass().getSimpleName(), "Credit Card", driver.findElement(By.xpath("//*[@id='ext-gen44']/body/main/div[1]/div/div/div[1]/form/div[1]/div[2]/div[2]/div[2]/div/div[1]/h5[1]")).getText());
            
            testCase.setStatus("PASS");
            tcList.add(testCase);
            logger.info("END testReviewEGiftPayment");  
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
     * Click edit in payment section of order review and 
     * select a different credit card and billing address
     * 
     * @param driver
     * @param prop
     * @param locator
     * @param user
     * @param map
     * @param tcList
     */
    public void testEditPayment(WebDriver driver, Properties prop, Properties locator, User user, Map<String,Boolean> map, List<TestCase> tcList)throws Exception {
        try {
            logger.info("BEGIN testPaymentSection"); 
            String FUNCTIONALITY = "Edit payment details from order review page";
            testCase = new TestCase("TC-11.3", "MOC-NIL", FUNCTIONALITY, "FAIL", "");
            
            WebDriverWait wait = (WebDriverWait) new WebDriverWait(driver, 10);
            WebDriverWait wait3 = (WebDriverWait) new WebDriverWait(driver, 3);
            Actions actions = new Actions(driver);
            WebElement editButtonElement = null;
            
            TestCreditCard testCard = new TestCreditCard();
            TestBillingAddress testBilling = new TestBillingAddress();
            
            if(map.get("isLogged") && map.get("isUSAddress") && !map.get("isGiftCard") && map.get("isCreditCard") && !map.get("isRegister")) {
                logger.info("Condition 1");     
                getTestHelper().logAssertion(getClass().getSimpleName(), "PAYMENT", driver.findElement(By.xpath(locator.getProperty("reviewOrder1.payment.title").toString())).getText());
                getTestHelper().logAssertion(getClass().getSimpleName(), "Billing Address", driver.findElement(By.xpath(locator.getProperty("reviewOrder1.payment.billing").toString())).getText());
                getTestHelper().logAssertion(getClass().getSimpleName(), "Credit Card", driver.findElement(By.xpath(locator.getProperty("reviewOrder1.payment.creditCard").toString())).getText());
                
                // Click edit Payment
                logger.info("Click edit");     
                editButtonElement = driver.findElement(By.xpath(locator.getProperty("reviewOrder1.payment.edit.button").toString()));
                actions.moveToElement(editButtonElement).click(editButtonElement);
                actions.perform();
            } else if(map.get("isLogged") && !map.get("isUSAddress") && !map.get("isGiftCard") && map.get("isCreditCard") && !map.get("isRegister")) {
                logger.info("Condition 2");     
                getTestHelper().logAssertion(getClass().getSimpleName(), "PAYMENT", driver.findElement(By.xpath(locator.getProperty("reviewOrder2.payment.title").toString())).getText());
                getTestHelper().logAssertion(getClass().getSimpleName(), "Billing Address", driver.findElement(By.xpath(locator.getProperty("reviewOrder2.payment.billing").toString())).getText());
                getTestHelper().logAssertion(getClass().getSimpleName(), "Credit Card", driver.findElement(By.xpath(locator.getProperty("reviewOrder2.payment.creditCard").toString())).getText());
                
                // Click edit Payment
                logger.info("Click edit");     
                editButtonElement = driver.findElement(By.xpath(locator.getProperty("reviewOrder2.payment.edit.button").toString()));
                actions.moveToElement(editButtonElement).click(editButtonElement);
                actions.perform();
            }
            
            try {
                // Wait for gif image(loading) to become stale
                wait3.until(ExpectedConditions.stalenessOf(driver.findElement(By.xpath("//div[@class='sk-fading-circle']"))));
//                wait.until(ExpectedConditions.stalenessOf(driver.findElement(By.xpath("//*[@id='ext-gen44']/body/div[15]"))));
            } catch(NoSuchElementException ne) {
                logger.info("Loading Gif image cannot be located");
            }
            
            testCard.testSelectCreditCard(driver, prop, locator, user, tcList);
            testBilling.testBillingAddress(driver, prop, locator, user, false, true, tcList);
            
            try {
                // Wait for gif image(loading) to become stale
                wait3.until(ExpectedConditions.stalenessOf(driver.findElement(By.xpath("//div[@class='sk-fading-circle']"))));
//                wait.until(ExpectedConditions.stalenessOf(driver.findElement(By.xpath("//*[@id='ext-gen44']/body/div[15]"))));
            } catch(NoSuchElementException ne) {
                logger.info("Loading Gif image cannot be located");
            }
            
            // Navigate from payment to Review order
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locator.getProperty("payment.title").toString())));
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath(locator.getProperty("payment.continue.button").toString())));
            driver.findElement(By.xpath(locator.getProperty("payment.continue.button").toString())).click();

            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locator.getProperty("reviewOrder.title").toString())));

            testCase.setStatus("PASS");
            tcList.add(testCase);
            logger.info("END testPaymentSection");  
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
     * Edit payment page by selecting new credit card and add new billing address
     * 
     * @param driver
     * @param prop
     * @param locator
     * @param user
     * @param map
     * @param tcList
     * @throws Exception
     */
    public void testEditPaymentBilling(WebDriver driver, Properties prop, Properties locator, User user, Map<String,Boolean> map, List<TestCase> tcList)throws Exception {
        
        logger.info("BEGIN testEditPaymentBilling");     
        String FUNCTIONALITY = "Modify payment details";
        testCase = new TestCase("TC-11.4", "MOC-NIL", FUNCTIONALITY, "FAIL", "");
        
        WebDriverWait wait = (WebDriverWait) new WebDriverWait(driver, 10);
        WebDriverWait wait3 = (WebDriverWait) new WebDriverWait(driver, 3);
        
        TestCreditCard testCard = new TestCreditCard();
        TestBillingAddress testBilling = new TestBillingAddress();
        
        try {
            // Wait for gif image(loading) to become stale
            wait3.until(ExpectedConditions.stalenessOf(driver.findElement(By.xpath("//div[@class='sk-fading-circle']"))));
//            wait.until(ExpectedConditions.stalenessOf(driver.findElement(By.xpath("//*[@id='ext-gen44']/body/div[15]"))));
        } catch(NoSuchElementException ne) {
            logger.info("Loading Gif image cannot be located");
        }
        
        testCard.testSelectCreditCard(driver, prop, locator, user, tcList);
        testBilling.testBillingAddress(driver, prop, locator, user, false, true, tcList);
        
        try {
            // Wait for gif image(loading) to become stale
            wait3.until(ExpectedConditions.stalenessOf(driver.findElement(By.xpath("//div[@class='sk-fading-circle']"))));
//            wait.until(ExpectedConditions.stalenessOf(driver.findElement(By.xpath("//*[@id='ext-gen44']/body/div[15]"))));
        } catch(NoSuchElementException ne) {
            logger.info("Loading Gif image cannot be located");
        }
        
        // Navigate from payment to Review order
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locator.getProperty("payment.title").toString())));
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(locator.getProperty("payment.continue.button").toString())));
        driver.findElement(By.xpath(locator.getProperty("payment.continue.button").toString())).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locator.getProperty("reviewOrder.title").toString())));
        
        testCase.setStatus("PASS");
        tcList.add(testCase);
        logger.info("END testEditPaymentBilling");     
    }
    
    /**
     * Verify the Payment section of order review page 
     * in different conditions
     * 
     * @param driver
     * @param prop
     * @param locator
     * @param user
     * @param map
     * @param tcList
     */
    public void testReviewPayment(WebDriver driver, Properties prop, Properties locator, User user, Map<String,Boolean> map, List<TestCase> tcList) {
     
        try {
            logger.info("BEGIN testReviewPayment");     
            String FUNCTIONALITY = "Review payment details of Order Review page";
            testCase = new TestCase("TC-11.5", "MOC-NIL", FUNCTIONALITY, "FAIL", "");
            
            if(map.get("isLogged") && map.get("isUSAddress") && !map.get("isGiftCard") && map.get("isCreditCard")) {
                logger.info("Condition 1");     
                getTestHelper().logAssertion(getClass().getSimpleName(), "PAYMENT", driver.findElement(By.xpath(locator.getProperty("reviewOrder1.payment.title").toString())).getText());
                getTestHelper().logAssertion(getClass().getSimpleName(), "Billing Address", driver.findElement(By.xpath(locator.getProperty("reviewOrder1.payment.billing").toString())).getText());
                getTestHelper().logAssertion(getClass().getSimpleName(), "Credit Card", driver.findElement(By.xpath(locator.getProperty("reviewOrder1.payment.creditCard").toString())).getText());
                
            } else if(map.get("isLogged") && !map.get("isUSAddress") && !map.get("isGiftCard") && map.get("isCreditCard")) {
                logger.info("Condition 2");     
                getTestHelper().logAssertion(getClass().getSimpleName(), "PAYMENT", driver.findElement(By.xpath(locator.getProperty("reviewOrder2.payment.title").toString())).getText());
                getTestHelper().logAssertion(getClass().getSimpleName(), "Billing Address", driver.findElement(By.xpath(locator.getProperty("reviewOrder2.payment.billing").toString())).getText());
                getTestHelper().logAssertion(getClass().getSimpleName(), "Credit Card", driver.findElement(By.xpath(locator.getProperty("reviewOrder2.payment.creditCard").toString())).getText());
                
            } else if(map.get("isLogged") && map.get("isUSAddress") && map.get("isGiftCard") && !map.get("isCreditCard")) {
                logger.info("Condition 3");     
                getTestHelper().logAssertion(getClass().getSimpleName(), "PAYMENT", driver.findElement(By.xpath(locator.getProperty("reviewOrder3.payment.title").toString())).getText());
                getTestHelper().logAssertion(getClass().getSimpleName(), "Billing Address", driver.findElement(By.xpath(locator.getProperty("reviewOrder3.payment.billing").toString())).getText());
                getTestHelper().logAssertion(getClass().getSimpleName(), "Gift Card", driver.findElement(By.xpath(locator.getProperty("reviewOrder3.payment.giftCard").toString())).getText());
            } else if(map.get("isLogged") && !map.get("isUSAddress") && map.get("isGiftCard") && !map.get("isCreditCard")) {
                logger.info("Condition 4");     
                getTestHelper().logAssertion(getClass().getSimpleName(), "PAYMENT", driver.findElement(By.xpath(locator.getProperty("reviewOrder4.payment.title").toString())).getText());
                getTestHelper().logAssertion(getClass().getSimpleName(), "Billing Address", driver.findElement(By.xpath(locator.getProperty("reviewOrder4.payment.billing").toString())).getText());
                getTestHelper().logAssertion(getClass().getSimpleName(), "Gift Card", driver.findElement(By.xpath(locator.getProperty("reviewOrder4.payment.giftCard").toString())).getText());
            } else if(!map.get("isLogged") && map.get("isUSAddress") && map.get("isGiftCard") && !map.get("isCreditCard")) {
                logger.info("Condition 5");     
                getTestHelper().logAssertion(getClass().getSimpleName(), "PAYMENT", driver.findElement(By.xpath(locator.getProperty("reviewOrder5.payment.title").toString())).getText());
                getTestHelper().logAssertion(getClass().getSimpleName(), "Billing Address", driver.findElement(By.xpath(locator.getProperty("reviewOrder5.payment.billing").toString())).getText());
                getTestHelper().logAssertion(getClass().getSimpleName(), "Gift Card", driver.findElement(By.xpath(locator.getProperty("reviewOrder5.payment.giftCard").toString())).getText());
            } else if(!map.get("isLogged") && !map.get("isUSAddress") && map.get("isGiftCard") && !map.get("isCreditCard")) {
                logger.info("Condition 6");     
                getTestHelper().logAssertion(getClass().getSimpleName(), "PAYMENT", driver.findElement(By.xpath(locator.getProperty("reviewOrder6.payment.title").toString())).getText());
                getTestHelper().logAssertion(getClass().getSimpleName(), "Billing Address", driver.findElement(By.xpath(locator.getProperty("reviewOrder6.payment.billing").toString())).getText());
                getTestHelper().logAssertion(getClass().getSimpleName(), "Gift Card", driver.findElement(By.xpath(locator.getProperty("reviewOrder6.payment.giftCard").toString())).getText());
            }
            
            testCase.setStatus("PASS");
            tcList.add(testCase);
            logger.info("END testReviewPayment");    
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
