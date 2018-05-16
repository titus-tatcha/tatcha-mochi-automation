package com.tatcha.jscripts.payment;

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
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.tatcha.jscripts.dao.TestCase;
import com.tatcha.jscripts.dao.User;
import com.tatcha.jscripts.helper.TatchaTestHelper;

/**
 * Verify the gift card option of Checkout - Payment page
 * 
 * @author reshma
 *
 */
public class TestGiftCard {

    private TatchaTestHelper testHelper = new TatchaTestHelper();
    private final static Logger logger = Logger.getLogger(TestGiftCard.class);
    private TestCase testCase;

    /**
     * Verify adding Gift card in checkout - Payment page
     * 
     * @param driver
     * @param prop
     * @param locator
     * @param user
     * @param tcList
     * @param checkBalance
     */
    public void testAddGiftCard(WebDriver driver, Properties prop, Properties locator, User user, boolean checkBalance, List<TestCase> tcList) {
        try {
            logger.info("BEGIN testAddGiftCard");   
            String FUNCTIONALITY = "Add a gift card";
            testCase = new TestCase("TC-8.1", "MOC-NIL", FUNCTIONALITY, "FAIL", "");
            
            Actions actions = new Actions(driver);
            By paymentTitleLocator = By.xpath(locator.getProperty("checkout.step2.title").toString());
            String giftCardNo = prop.getProperty("checkout.giftCard").toString();

            WebDriverWait wait = (WebDriverWait) new WebDriverWait(driver, 10);
            wait.until(ExpectedConditions.visibilityOfElementLocated(paymentTitleLocator));
            getTestHelper().logAssertion(getClass().getSimpleName(), "Gift Card", driver.findElement(By.xpath(locator.getProperty("giftCard.title").toString())).getText());
            
            // Add gift Card
            logger.info("Add gift Card");     
            WebElement addGiftCardButtonElement = driver.findElement(By.xpath(locator.getProperty("giftCard.add.button").toString()));
            actions.moveToElement(addGiftCardButtonElement).click(addGiftCardButtonElement);
            actions.perform();
            
            try{
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locator.getProperty("giftCard.modalBox.title").toString())));
            } catch(TimeoutException te) {
                logger.info("Add Gift card button was not clicked");     
                driver.findElement(By.xpath(locator.getProperty("giftCard.add.button").toString())).click();
            }
            getTestHelper().logAssertion(getClass().getSimpleName(), "GIFT CARD", driver.findElement(By.xpath(locator.getProperty("giftCard.modalBox.title").toString())).getText());
            getTestHelper().logAssertion(getClass().getSimpleName(), "GIFT CARD NUMBER", driver.findElement(By.xpath(locator.getProperty("giftCard.modalBox.label").toString())).getText());

            WebElement checkBalanceButtonElement = driver.findElement(By.xpath(locator.getProperty("giftCard.modalBox.balance.button").toString()));
            getTestHelper().logAssertion(getClass().getSimpleName(), !checkBalanceButtonElement.isEnabled());

            WebElement applyCardButtonElement = driver.findElement(By.xpath(locator.getProperty("giftCard.modalBox.apply.button").toString()));
            getTestHelper().logAssertion(getClass().getSimpleName(), !applyCardButtonElement.isEnabled());

            // Enter the GC
            logger.info("Populate GC");    
            WebElement cardTextareaElement = driver.findElement(By.xpath(locator.getProperty("giftCard.modalBox.textarea").toString()));
            cardTextareaElement.clear();
            cardTextareaElement.sendKeys("VSOMPKVSIIHCKOLS");
            
            try {
                wait.until(ExpectedConditions.attributeContains(driver.findElement(By.xpath(locator.getProperty("giftCard.modalBox.textarea").toString())), "value", giftCardNo));
            } catch(TimeoutException te) {
                cardTextareaElement.clear();
                cardTextareaElement.sendKeys("VSOMPKVSIIHCKOLS");
            }
            
            checkBalanceButtonElement = driver.findElement(By.xpath(locator.getProperty("giftCard.modalBox.balance.button").toString()));
            getTestHelper().logAssertion(getClass().getSimpleName(), checkBalanceButtonElement.isEnabled());

            applyCardButtonElement = driver.findElement(By.xpath(locator.getProperty("giftCard.modalBox.apply.button").toString()));
            getTestHelper().logAssertion(getClass().getSimpleName(), applyCardButtonElement.isEnabled());
            
            if(checkBalance) {
                testCheckGiftCardBalance(driver, prop, locator, user, tcList);
            }
            
            // Apply Gc
            logger.info("Apply GC");
            applyCardButtonElement = driver.findElement(By.xpath(locator.getProperty("giftCard.modalBox.apply.button").toString()));
            applyCardButtonElement.click();
            
            testCase.setStatus("PASS");
            tcList.add(testCase);
            logger.info("END testAddGiftCard");     

        }  catch (NoSuchElementException ne) {
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
     * Verify Gift Card Balance in checkout payment page
     * 
     * @param driver
     * @param prop
     * @param locator
     * @param user
     * @param tcList
     */
    public void testCheckGiftCardBalance(WebDriver driver, Properties prop, Properties locator, User user, List<TestCase> tcList) {
        try {
            logger.info("BEGIN testCheckGiftCardBalance");
            String FUNCTIONALITY = "Check gift card balance";
            testCase = new TestCase("TC-8.2", "MOC-NIL", FUNCTIONALITY, "FAIL", "");
            
            WebDriverWait wait = (WebDriverWait) new WebDriverWait(driver, 10);
            String giftCardNo = prop.getProperty("checkout.giftCard").toString();

            wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector("input#dwfrm_billing_giftCertCode.input-text.form-control[value=\""+giftCardNo+"\"]"))));
            WebElement checkBalanceButtonElement = driver.findElement(By.xpath(locator.getProperty("giftCard.modalBox.balance.button").toString()));
            checkBalanceButtonElement.click();
            By balanceInfoLocator = By.xpath(locator.getProperty("giftCard.modalBox.balanceInfo").toString());
            wait.until(ExpectedConditions.visibilityOfElementLocated(balanceInfoLocator));
            getTestHelper().logAssertion(getClass().getSimpleName(), driver.findElement(balanceInfoLocator).isDisplayed());
            
            testCase.setStatus("PASS");
            tcList.add(testCase);
            logger.info("END testCheckGiftCardBalance");
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
     * Verify Removing of added gift card
     * 
     * @param driver
     * @param prop
     * @param locator
     * @param user
     * @param tcList
     */
    public void testRemoveGiftCard(WebDriver driver, Properties prop, Properties locator, User user, List<TestCase> tcList) {
        
        try {
            logger.info("BEGIN testRemoveGiftCard");
            String FUNCTIONALITY = "Remove gift card";
            testCase = new TestCase("TC-8.3", "MOC-NIL", FUNCTIONALITY, "FAIL", "");
            
            By paymentTitleLocator = By.xpath(locator.getProperty("checkout.step2.title").toString());

            WebDriverWait wait = (WebDriverWait) new WebDriverWait(driver, 10);
            wait.until(ExpectedConditions.visibilityOfElementLocated(paymentTitleLocator));
            WebElement undoLinkElement = driver.findElement(By.xpath(locator.getProperty("giftCard.undo.link").toString()));
            undoLinkElement.click();
            wait.until(ExpectedConditions.refreshed(ExpectedConditions.invisibilityOf(undoLinkElement)));
            
            testCase.setStatus("PASS");
            tcList.add(testCase);
            logger.info("END testRemoveGiftCard");
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
