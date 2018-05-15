/**
 * 
 */
package com.tatcha.jscripts.summary;


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
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.tatcha.jscripts.dao.Product;
import com.tatcha.jscripts.dao.TestCase;
import com.tatcha.jscripts.dao.User;
import com.tatcha.jscripts.helper.TatchaTestHelper;

/**
 * @author reshma
 *
 */
public class TestSummary {
    
    private TatchaTestHelper testHelper = new TatchaTestHelper();
    private final static Logger logger = Logger.getLogger(TestSummary.class);
    private TestCase testCase;

    /**
     * Test Summary section of checkout
     * 
     * @param driver
     * @param prop
     * @param locator
     * @param user
     * @param map
     * @param tcList
     */
    public void testSummary(WebDriver driver, Properties prop, Properties locator, User user, Map<String,Boolean> map, List<TestCase> tcList) {
        try {
            logger.info("BEGIN testSummary");
            String FUNCTIONALITY = "Verify Summary";
            testCase = new TestCase("TC-18.1", "MOC-NIL", FUNCTIONALITY, "FAIL", "");
            
            WebDriverWait wait = (WebDriverWait) new WebDriverWait(driver, 5);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locator.getProperty("summary.title").toString())));

            String merchTotalString = driver.findElement(By.xpath(locator.getProperty("summary.merchtotal.value").toString())).getText();
            String shipAmtString = driver.findElement(By.xpath(locator.getProperty("summary.shipping.value").toString())).getText();
            String taxAmtString = driver.findElement(By.xpath(locator.getProperty("summary.tax.value").toString())).getText();
            String orderTotalString = driver.findElement(By.xpath(locator.getProperty("summary.total.value").toString())).getText();

//            String merchTotalString = driver.findElement(By.xpath("//*[@id='checkout-summary']/div/div[2]/table/tbody/tr[1]/td")).getText();
//            String shipAmtString = driver.findElement(By.xpath("//*[@id='checkout-summary']/div/div[2]/table/tbody/tr[2]/td")).getText();
//            String taxAmtString = driver.findElement(By.xpath("//*[@id='checkout-summary']/div/div[2]/table/tbody/tr[3]/td")).getText();
//            String orderTotalString = driver.findElement(By.xpath("//*[@id='checkout-summary']/div/div[2]/table/tbody/tr[4]/td")).getText();

            
            getTestHelper().logAssertion(getClass().getSimpleName(),
                    getTestHelper().getPrice(orderTotalString) == getTestHelper().getPrice(merchTotalString)
                            + getTestHelper().getPrice(shipAmtString) + getTestHelper().getPrice(taxAmtString));

            testCase.setStatus("PASS");
            tcList.add(testCase);
            logger.info("END testSummary");
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
     * Test summary section of shopping bag
     * 
     * @param driver
     * @param prop
     * @param locator
     * @param user
     * @param map
     * @param tcList
     */
    public void testBagSummary(WebDriver driver, Properties prop, Properties locator, User user, Map<String,Boolean> map, List<TestCase> tcList) {
        try {
            logger.info("BEGIN testBagSummary");
            String FUNCTIONALITY = "Verify summary in shopping bag page";
            testCase = new TestCase("TC-18.2", "MOC-NIL", FUNCTIONALITY, "FAIL", "");
            
            WebDriverWait wait = (WebDriverWait) new WebDriverWait(driver, 5);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locator.getProperty("summary.title").toString())));

            int productInCart = 0;
            // Get no of items in cart
            for(Product product : user.getProducts()) {
                productInCart += product.getQuantity();
            }      
            logger.info("No of Products in Cart : "+productInCart);
            if(productInCart>1) {
                logger.info("More than one product in cart");
                getTestHelper().logAssertion(getClass().getSimpleName(), productInCart+" items", driver.findElement(By.xpath(locator.getProperty("summary.item.count").toString())).getText());
            } else {
                getTestHelper().logAssertion(getClass().getSimpleName(), productInCart+" item", driver.findElement(By.xpath(locator.getProperty("summary.item.count").toString())).getText());
            }
            
            String merchTotalString = driver.findElement(By.xpath(locator.getProperty("summary.merchtotal.value").toString())).getText();
            String shipAmtString = driver.findElement(By.xpath(locator.getProperty("summary.shipping.value").toString())).getText();
            String taxAmtString = driver.findElement(By.xpath(locator.getProperty("summary.tax.value").toString())).getText();
            String orderTotalString = driver.findElement(By.xpath(locator.getProperty("summary.total.value").toString())).getText();
                        
            getTestHelper().logAssertion(getClass().getSimpleName(),
                    getTestHelper().getPrice(orderTotalString) == getTestHelper().getPrice(merchTotalString)
                            + getTestHelper().getPrice(shipAmtString) + getTestHelper().getPrice(taxAmtString));

            testCase.setStatus("PASS");
            tcList.add(testCase);
            logger.info("END testBagSummary");
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
     * Verify summary when Gift certificate is in cart
     * 
     * @param driver
     * @param prop
     * @param locator
     * @param user
     * @param map
     * @param tcList
     */
    public void testEgiftSummary(WebDriver driver, Properties prop, Properties locator, User user, Map<String,Boolean> map, List<TestCase> tcList) {
        try {
            logger.info("BEGIN testEgiftSummary");
            String FUNCTIONALITY = "Verify summary when Gift certificate is in cart";
            testCase = new TestCase("TC-18.3", "MOC-NIL", FUNCTIONALITY, "FAIL", "");
            
            WebDriverWait wait = (WebDriverWait) new WebDriverWait(driver, 5);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locator.getProperty("summary.title").toString())));

            String merchTotalString = driver.findElement(By.xpath(locator.getProperty("summary3.merchtotal.value").toString())).getText();
            String taxAmtString = driver.findElement(By.xpath(locator.getProperty("summary3.tax.value").toString())).getText();
            String orderTotalString = driver.findElement(By.xpath(locator.getProperty("summary3.total.value").toString())).getText();

            getTestHelper().logAssertion(getClass().getSimpleName(),
                    getTestHelper().getPrice(orderTotalString) == getTestHelper().getPrice(merchTotalString)
                            + getTestHelper().getPrice(taxAmtString));

            testCase.setStatus("PASS");
            tcList.add(testCase);
            logger.info("END testEgiftSummary");
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
