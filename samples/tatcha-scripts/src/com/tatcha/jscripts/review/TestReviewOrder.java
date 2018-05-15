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

/**
 * 
 * @author reshma
 *
 */
public class TestReviewOrder {
    private TatchaTestHelper testHelper = new TatchaTestHelper();
    private final static Logger logger = Logger.getLogger(TestReviewOrder.class);
    private TestCase testCase;

    /**
     * Test and verify elements of Review order page
     * 
     * @param driver
     * @param prop
     * @param locator
     * @param user
     * @param map
     * @param tcList
     * @throws Exception
     */
    public void testReviewOrder(WebDriver driver, Properties prop, Properties locator, User user, Map<String,Boolean> map, List<TestCase> tcList) throws Exception {
        try {
            logger.info("BEGIN testReviewOrder");     
            String FUNCTIONALITY = "Verify checkbox when address is international";
            testCase = new TestCase("TC-12.1", "MOC-NIL", FUNCTIONALITY, "FAIL", "");
            
            WebDriverWait wait = (WebDriverWait) new WebDriverWait(driver, 10);
            WebDriverWait wait3 = (WebDriverWait) new WebDriverWait(driver, 3);

            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(locator.getProperty("checkout.title").toString())));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locator.getProperty("checkout.step3.title").toString())));
            getTestHelper().logAssertion(getClass().getSimpleName(), "REVIEW ORDER", driver.findElement(By.xpath(locator.getProperty("checkout.step3.title").toString())).getText());
            getTestHelper().logAssertion(getClass().getSimpleName(), "Review", driver.findElement(By.xpath(locator.getProperty("reviewOrder.title").toString())).getText());
            
            // Verify if acknowledgement is not checked, place order button is disabled
            By checkboxLocator = By.xpath(locator.getProperty("reviewOrder.ack.checkbox").toString());
            By placeOrderButtonLocator = By.xpath(locator.getProperty("reviewOrder.placeOrder.button").toString());
            WebElement placeOrderButtonElement = driver.findElement(placeOrderButtonLocator);

            try {
                wait3.until(ExpectedConditions.visibilityOfElementLocated(checkboxLocator));
                WebElement checkboxElement = driver.findElement(checkboxLocator);
                if(!checkboxElement.isSelected()) {
                    getTestHelper().logAssertion(getClass().getSimpleName(), !placeOrderButtonElement.isEnabled());
                }
                
                checkboxElement = driver.findElement(checkboxLocator);
                placeOrderButtonElement = driver.findElement(placeOrderButtonLocator);
                checkboxElement.click();
                if(checkboxElement.isSelected()) {
                    getTestHelper().logAssertion(getClass().getSimpleName(), placeOrderButtonElement.isEnabled());
                }
            } catch (TimeoutException te){
                logger.info("If checkbox not present then shipping address should be US address");
            }            
            
            testCase.setStatus("PASS");
            tcList.add(testCase);
            logger.info("END testReviewOrder");     
        } catch (NoSuchElementException ne) {
            System.err.println(ne.toString()+getClass().getSimpleName()+" : Review Order testing not complete");
        } catch (ElementNotVisibleException nv) {
            System.err.println(nv.toString()+getClass().getSimpleName()+" : Review Order testing not complete");
        } catch (TimeoutException te) {
            System.err.println(te.toString()+getClass().getSimpleName()+" : Review Order testing not complete");
        } catch (StaleElementReferenceException sr) {
            System.err.println(sr.toString()+getClass().getSimpleName()+" : Review Order testing not complete");
        } catch (WebDriverException we) {
            System.err.println(we.toString()+getClass().getSimpleName()+" : Review Order testing not complete");
        }
    }
    
    /**
     * Verify elements of review Order page for guest
     * 
     * @param driver
     * @param prop
     * @param locator
     * @param user
     * @param map
     * @param tcList
     * @throws Exception
     */
    public void testGuestReviewOrder(WebDriver driver, Properties prop, Properties locator, User user, Map<String,Boolean> map, Properties data, List<TestCase> tcList) throws Exception {
        try {
            logger.info("BEGIN testGuestReviewOrder");     
            String FUNCTIONALITY = "Verify checkbox when address is international for guest checkout";
            testCase = new TestCase("TC-12.2", "MOC-NIL", FUNCTIONALITY, "FAIL", "");
            
            WebDriverWait wait = (WebDriverWait) new WebDriverWait(driver, 10);
            WebDriverWait wait3 = (WebDriverWait) new WebDriverWait(driver, 3);
            Actions action = new Actions(driver);

            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(locator.getProperty("checkout.title").toString())));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locator.getProperty("checkout.step3.title").toString())));
            getTestHelper().logAssertion(getClass().getSimpleName(), "REVIEW ORDER", driver.findElement(By.xpath(locator.getProperty("checkout.step3.title").toString())).getText());
            getTestHelper().logAssertion(getClass().getSimpleName(), "Review", driver.findElement(By.xpath(locator.getProperty("reviewOrder.title").toString())).getText());
            
            // Verify if acknowledgement is not checked, place order button is disabled
            By checkboxLocator = By.xpath(locator.getProperty("reviewOrder.ack.checkbox").toString());
            By placeOrderButtonLocator = By.xpath(locator.getProperty("reviewOrder.placeOrder.button").toString());
            WebElement placeOrderButtonElement = driver.findElement(placeOrderButtonLocator);

            try {
                wait3.until(ExpectedConditions.visibilityOfElementLocated(checkboxLocator));
                WebElement checkboxElement = driver.findElement(checkboxLocator);
                if(!checkboxElement.isSelected()) {
                    getTestHelper().logAssertion(getClass().getSimpleName(), !placeOrderButtonElement.isEnabled());
                }
                checkboxElement = driver.findElement(checkboxLocator);
                placeOrderButtonElement = driver.findElement(placeOrderButtonLocator);
                action.moveToElement(checkboxElement);
                wait3.until(ExpectedConditions.elementToBeClickable(checkboxElement));
                action.click(checkboxElement);
                action.build().perform();
//                checkboxElement.click();
                try {
                    wait3.until(ExpectedConditions.elementToBeClickable(checkboxLocator));

                } catch(TimeoutException te) {
                    checkboxElement = driver.findElement(checkboxLocator);
                }
                if(checkboxElement.isSelected()) {
                    getTestHelper().logAssertion(getClass().getSimpleName(), placeOrderButtonElement.isEnabled());
                }
            } catch (TimeoutException te){
                logger.info("If checkbox not present then shipping address should be US address");
            }    

            testCase.setStatus("PASS");
            tcList.add(testCase);
            logger.info("END testGuestReviewOrder");     
        } catch (NoSuchElementException ne) {
            System.err.println(ne.toString()+getClass().getSimpleName()+" : Review Order testing not complete");
        } catch (ElementNotVisibleException nv) {
            System.err.println(nv.toString()+getClass().getSimpleName()+" : Review Order testing not complete");
        } catch (TimeoutException te) {
            System.err.println(te.toString()+getClass().getSimpleName()+" : Review Order testing not complete");
        } catch (StaleElementReferenceException sr) {
            System.err.println(sr.toString()+getClass().getSimpleName()+" : Review Order testing not complete");
        } catch (WebDriverException we) {
            System.err.println(we.toString()+getClass().getSimpleName()+" : Review Order testing not complete");
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
