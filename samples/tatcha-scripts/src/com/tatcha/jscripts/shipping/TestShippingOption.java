package com.tatcha.jscripts.shipping;

import java.util.List;
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

/**
 * Class that checks different shipping options of 
 * Checkout - Shipping page
 * 
 * @author reshma
 *
 */
public class TestShippingOption {
    private TatchaTestHelper testHelper = new TatchaTestHelper();
    private final static Logger logger = Logger.getLogger(TestShippingAddress.class);
    public static final String COUNTRY_US = "United States";
    private TestCase testCase;

    /**
     * Select a shipping option from list of available options 
     * in Checkout - Shipping page
     * 
     * @param driver
     * @param prop
     * @param locator
     * @param user
     * @param tcList
     */
    public void testSelectShippingOption(WebDriver driver, Properties prop, Properties locator, User user, List<TestCase> tcList) {

        try {
            logger.info("BEGIN testSelectShippingOption");
            String FUNCTIONALITY = "Select a shipping option";
            testCase = new TestCase("TC-16.1", "MOC-NIL", FUNCTIONALITY, "FAIL", "");
            
            WebDriverWait wait = (WebDriverWait) new WebDriverWait(driver, 10);
            getTestHelper().logAssertion(getClass().getSimpleName(), "Shipping Options", driver.findElement(By.xpath(locator.getProperty("shipping.option.title").toString())).getText());
            getTestHelper().logAssertion(getClass().getSimpleName(), prop.getProperty("checkout.shippingOption.info").toString(), driver.findElement(By.xpath(locator.getProperty("shipping.option.info").toString())).getText());

            logger.info("Get all shipping options for the selected shipping address");
            try {
                wait.until(ExpectedConditions.stalenessOf(driver.findElements(By.name(locator.getProperty("shipping.optionList").toString())).get(0)));
            } catch (TimeoutException te) {
                logger.error("Time out waiting for modified Shipping Option");
            }
            List<WebElement> shippingOptionsListElement = null;
            WebElement shippingOptionElement = null;
                    
            try {
                wait.until(ExpectedConditions.stalenessOf(driver.findElements(By.name(locator.getProperty("shipping.optionList").toString())).get(0)));
                shippingOptionsListElement = driver.findElements(By.name(locator.getProperty("shipping.optionList").toString()));
                logger.info("Shipping Option List Size: "+shippingOptionsListElement.size());
                getTestHelper().logAssertion(getClass().getSimpleName(), shippingOptionsListElement.get(0).isSelected());

                String id = shippingOptionsListElement.get(0).getAttribute("id");
                logger.info("Shipping Option : "+id);
//                WebElement shippingOptionElement = shippingOptionsListElement.get(0).findElement(By.cssSelector("label[for=\""+id+"\"]"));//("img[alt=\""+productName+"\"]")
                shippingOptionElement = shippingOptionsListElement.get(0).findElement(By.xpath(".."));
                String priceString = shippingOptionElement.findElement(By.className("ship-price")).getText();
                double shippingPriceFromList = getTestHelper().getPrice(priceString);
                logger.info("Shipping Total : "+shippingPriceFromList);
                getTestHelper().logAssertion(getClass().getSimpleName(), priceString, driver.findElement(By.xpath(locator.getProperty("summary.shipping.value").toString())).getText());
                logger.info("END verifyShippingOption");
            } catch(StaleElementReferenceException sre) {
                logger.info("Stale element referenced");
                shippingOptionsListElement = driver.findElements(By.name(locator.getProperty("shipping.optionList").toString()));
                getTestHelper().logAssertion(getClass().getSimpleName(), shippingOptionsListElement.get(0).isSelected());
                shippingOptionElement = shippingOptionsListElement.get(0).findElement(By.xpath(".."));
                String priceString = shippingOptionElement.findElement(By.className("ship-price")).getText();
                getTestHelper().logAssertion(getClass().getSimpleName(), priceString, driver.findElement(By.xpath(locator.getProperty("summary.shipping.value").toString())).getText());        
            } catch(TimeoutException te) {
                logger.info("Waiting timeout");
                shippingOptionsListElement = driver.findElements(By.name(locator.getProperty("shipping.optionList").toString()));
                getTestHelper().logAssertion(getClass().getSimpleName(), shippingOptionsListElement.get(0).isSelected());
                shippingOptionElement = shippingOptionsListElement.get(0).findElement(By.xpath(".."));
                String priceString = shippingOptionElement.findElement(By.className("ship-price")).getText();
                getTestHelper().logAssertion(getClass().getSimpleName(), priceString, driver.findElement(By.xpath(locator.getProperty("summary.shipping.value").toString())).getText());        
            }
            
            testCase.setStatus("PASS");
            tcList.add(testCase);
            logger.info("END testSelectShippingOption");
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
