package com.litmus7.tatcha.jscripts.selenium.sprint8;

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

import com.litmus7.tatcha.jscripts.dob.User;
import com.litmus7.tatcha.jscripts.selenium.sprint3.LoginHelper;

public class ReviewOrderCheckout {
    private LoginHelper loginHelper = new LoginHelper();
    private final static Logger logger = Logger.getLogger(ReviewOrderCheckout.class);
    
    /**
     * Verify Review order page
     * 
     * @param driver
     * @param prop
     * @param locator
     * @param user
     * @throws Exception
     */
    public void verifyReviewOrder(WebDriver driver, Properties prop, Properties locator, User user) throws Exception {

        try {
            logger.info("BEGIN verifyReviewOrder");     

            WebDriverWait wait = (WebDriverWait) new WebDriverWait(driver, 10);
            WebDriverWait wait3 = (WebDriverWait) new WebDriverWait(driver, 3);

            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(locator.getProperty("checkout.title").toString())));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locator.getProperty("checkout.step3.title").toString())));
//            getLoginHelper().logAssertion(getClass().getSimpleName(), "REVIEW ORDER", driver.findElement(By.xpath(locator.getProperty("checkout.step3.title").toString())).getText());
//            getLoginHelper().logAssertion(getClass().getSimpleName(), "Review", driver.findElement(By.xpath(locator.getProperty("reviewOrder.title").toString())).getText());

            // Verify if acknowledgement is not checked, place order button is disabled
            By checkboxLocator = By.xpath(locator.getProperty("reviewOrder.ack.checkbox").toString());
            By placeOrderButtonLocator = By.xpath(locator.getProperty("reviewOrder.placeOrder.button").toString());
            WebElement placeOrderButtonElement = driver.findElement(placeOrderButtonLocator);

            try {
                wait3.until(ExpectedConditions.visibilityOfElementLocated(checkboxLocator));
                WebElement checkboxElement = driver.findElement(checkboxLocator);
                if(!checkboxElement.isSelected()) {
//                    getLoginHelper().logAssertion(getClass().getSimpleName(), !placeOrderButtonElement.isEnabled());
                }
                
                checkboxElement = driver.findElement(checkboxLocator);
                placeOrderButtonElement = driver.findElement(placeOrderButtonLocator);
                checkboxElement.click();
                if(checkboxElement.isSelected()) {
//                    getLoginHelper().logAssertion(getClass().getSimpleName(), placeOrderButtonElement.isEnabled());
                }
            } catch (TimeoutException te){
                logger.info("If checkbox not present then shipping address should be US address");
            }
            
            placeOrderButtonElement.click();
            
            logger.info("END verifyReviewOrder");     
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
     * Verify Review order page
     * 
     * @param driver
     * @param prop
     * @param locator
     * @param user
     * @param map
     * @throws Exception
     */
    public void verifyReviewOrder(WebDriver driver, Properties prop, Properties locator, User user, Map<String,Boolean> map) throws Exception {
        try {
            logger.info("BEGIN verifyReviewOrder");     

            WebDriverWait wait = (WebDriverWait) new WebDriverWait(driver, 10);
            WebDriverWait wait3 = (WebDriverWait) new WebDriverWait(driver, 3);

            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(locator.getProperty("checkout.title").toString())));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locator.getProperty("checkout.step3.title").toString())));
            getLoginHelper().logAssertion(getClass().getSimpleName(), "REVIEW ORDER", driver.findElement(By.xpath(locator.getProperty("checkout.step3.title").toString())).getText());
            getLoginHelper().logAssertion(getClass().getSimpleName(), "Review", driver.findElement(By.xpath(locator.getProperty("reviewOrder.title").toString())).getText());
            
            // Verify Shipping 
            verifyShipping(driver, prop, locator, user, map);
            
            // Verify Payment
            verifyPayment(driver, prop, locator, user, map);
            
            // Verify if acknowledgement is not checked, place order button is disabled
            By checkboxLocator = By.xpath(locator.getProperty("reviewOrder.ack.checkbox").toString());
            By placeOrderButtonLocator = By.xpath(locator.getProperty("reviewOrder.placeOrder.button").toString());
            WebElement placeOrderButtonElement = driver.findElement(placeOrderButtonLocator);

            try {
                wait3.until(ExpectedConditions.visibilityOfElementLocated(checkboxLocator));
                WebElement checkboxElement = driver.findElement(checkboxLocator);
                if(!checkboxElement.isSelected()) {
                    getLoginHelper().logAssertion(getClass().getSimpleName(), !placeOrderButtonElement.isEnabled());
                }
                
                checkboxElement = driver.findElement(checkboxLocator);
                placeOrderButtonElement = driver.findElement(placeOrderButtonLocator);
                checkboxElement.click();
                if(checkboxElement.isSelected()) {
                    getLoginHelper().logAssertion(getClass().getSimpleName(), placeOrderButtonElement.isEnabled());
                }
            } catch (TimeoutException te){
                logger.info("If checkbox not present then shipping address should be US address");
            }
            
            placeOrderButtonElement.click();
            
            logger.info("END verifyReviewOrder");     
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
     * Verify Review order page for guest user
     * 
     * @param driver
     * @param prop
     * @param locator
     * @param user
     * @throws Exception
     */
    public void verifyGuestReviewOrder(WebDriver driver, Properties prop, Properties locator, User user) throws Exception {
        logger.info("BEGIN verifyGuestReviewOrder");     
        WebDriverWait wait = (WebDriverWait) new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(locator.getProperty("checkout.title").toString())));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locator.getProperty("checkout.step3.title").toString())));
        getLoginHelper().logAssertion(getClass().getSimpleName(), "REVIEW ORDER", driver.findElement(By.xpath(locator.getProperty("checkout.step3.title").toString())).getText());
        getLoginHelper().logAssertion(getClass().getSimpleName(), "Review", driver.findElement(By.xpath(locator.getProperty("reviewOrder.title").toString())).getText());
        
        // Verify if acknowledgement is not checked, place order button is disabled
        By checkboxLocator = By.xpath(locator.getProperty("reviewOrder.ack.checkbox").toString());
        By placeOrderButtonLocator = By.xpath(locator.getProperty("reviewOrder.placeOrder.button").toString());
        WebElement checkboxElement = driver.findElement(checkboxLocator);
        WebElement placeOrderButtonElement = driver.findElement(placeOrderButtonLocator);
        if(!checkboxElement.isSelected()) {
            getLoginHelper().logAssertion(getClass().getSimpleName(), !placeOrderButtonElement.isEnabled());
        }
        getLoginHelper().logAssertion(getClass().getSimpleName(), prop.getProperty("checkout.guest.emailReceipt.title").toString(), driver.findElement(By.xpath(locator.getProperty("guest.emailReceipt.title").toString())).getText());
        getLoginHelper().logAssertion(getClass().getSimpleName(), prop.getProperty("checkout.guest.emailReceipt.checkbox.label").toString(), driver.findElement(By.xpath(locator.getProperty("guest.emailReceipt.checkbox.label").toString())).getText());
        driver.findElement(By.xpath(locator.getProperty("guest.emailReceipt.checkbox").toString())).click();
        
        getLoginHelper().logAssertion(getClass().getSimpleName(), prop.getProperty("checkout.guest.createAccnt.title").toString(), driver.findElement(By.xpath(locator.getProperty("guest.createAccnt.title").toString())).getText());
        getLoginHelper().logAssertion(getClass().getSimpleName(), prop.getProperty("checkout.guest.createAccnt.description").toString(), driver.findElement(By.xpath(locator.getProperty("guest.createAccnt.description").toString())).getText());
        getLoginHelper().logAssertion(getClass().getSimpleName(), prop.getProperty("checkout.guest.createAccnt.checkbox.label").toString(), driver.findElement(By.xpath(locator.getProperty("guest.createAccnt.checkbox.label").toString())).getText());
        
        if(Boolean.parseBoolean(prop.getProperty("createAccount").toString())) {
            driver.findElement(By.xpath(locator.getProperty("guest.createAccnt.checkbox").toString())).click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locator.getProperty("guest.createAccnt.password.label").toString())));
            getLoginHelper().logAssertion(getClass().getSimpleName(), "Minimum 8 characters", driver.findElement(By.xpath(locator.getProperty("guest.createAccnt.password.info").toString())).getText());

            WebElement passwordElement = driver.findElement(By.xpath(locator.getProperty("guest.createAccnt.password").toString()));
            passwordElement.clear();
            passwordElement.sendKeys("tatcha123");
            WebElement confirmPasswordElement = driver.findElement(By.xpath(locator.getProperty("guest.createAccnt.confirmPassword").toString()));
            confirmPasswordElement.clear();
            confirmPasswordElement.sendKeys("tatcha123");
        }
        
        // Verify Shipping 
        verifyShipping(driver, prop, locator, user);
        
        // Verify Payment
        verifyPayment(driver, prop, locator, user);
        
        checkboxElement = driver.findElement(checkboxLocator);
        placeOrderButtonElement = driver.findElement(placeOrderButtonLocator);
        checkboxElement.click();
        if(checkboxElement.isSelected()) {
            getLoginHelper().logAssertion(getClass().getSimpleName(), placeOrderButtonElement.isEnabled());
        }
        logger.info("END verifyGuestReviewOrder");     
    }

    /**
     * Verify payment section of review order
     * 
     * @param driver
     * @param prop
     * @param locator
     * @param user
     */
    private void verifyPayment(WebDriver driver, Properties prop, Properties locator, User user) {
        logger.info("BEGIN verifyPayment");     
        WebDriverWait wait = (WebDriverWait) new WebDriverWait(driver, 10);
        Actions actions = new Actions(driver);

        getLoginHelper().logAssertion(getClass().getSimpleName(), "PAYMENT", driver.findElement(By.xpath(locator.getProperty("reviewOrder1.payment.title").toString())).getText());
        getLoginHelper().logAssertion(getClass().getSimpleName(), "Billing Address", driver.findElement(By.xpath(locator.getProperty("reviewOrder1.payment.billing").toString())).getText());
        getLoginHelper().logAssertion(getClass().getSimpleName(), "Credit Card", driver.findElement(By.xpath(locator.getProperty("reviewOrder1.payment.creditCard").toString())).getText());
    
        // Click edit Payment
        WebElement editButtonElement = driver.findElement(By.xpath(locator.getProperty("reviewOrder1.payment.edit.button").toString()));
        actions.moveToElement(editButtonElement).click(editButtonElement);
        actions.perform();
        
        // Navigate from payment to Review order
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locator.getProperty("payment.title").toString())));
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(locator.getProperty("payment.continue.button").toString())));
        driver.findElement(By.xpath(locator.getProperty("payment.continue.button").toString())).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locator.getProperty("reviewOrder.title").toString())));

        logger.info("END verifyPayment");     
    }
    
    /**
     * Verify payment section of review order based on different scenario
     * 
     * @param driver
     * @param prop
     * @param locator
     * @param user
     */
    private void verifyPayment(WebDriver driver, Properties prop, Properties locator, User user, Map<String,Boolean> map) {
        logger.info("BEGIN verifyPayment");     
        WebDriverWait wait = (WebDriverWait) new WebDriverWait(driver, 10);
        Actions actions = new Actions(driver);
        WebElement editButtonElement = null;
        
        if(map.get("defaultShipping") && map.get("defaultPayment") && map.get("isLogged") && map.get("isUSAddress")) {
            getLoginHelper().logAssertion(getClass().getSimpleName(), "PAYMENT", driver.findElement(By.xpath(locator.getProperty("reviewOrder1.payment.title").toString())).getText());
            getLoginHelper().logAssertion(getClass().getSimpleName(), "Billing Address", driver.findElement(By.xpath(locator.getProperty("reviewOrder1.payment.billing").toString())).getText());
            getLoginHelper().logAssertion(getClass().getSimpleName(), "Credit Card", driver.findElement(By.xpath(locator.getProperty("reviewOrder1.payment.creditCard").toString())).getText());
            
            // Click edit Payment
            editButtonElement = driver.findElement(By.xpath(locator.getProperty("reviewOrder1.payment.edit.button").toString()));
            actions.moveToElement(editButtonElement).click(editButtonElement);
            actions.perform();
        } else if(map.get("defaultShipping") && map.get("defaultPayment") && map.get("isLogged") && !map.get("isUSAddress")) {
            getLoginHelper().logAssertion(getClass().getSimpleName(), "PAYMENT", driver.findElement(By.xpath(locator.getProperty("reviewOrder2.payment.title").toString())).getText());
            getLoginHelper().logAssertion(getClass().getSimpleName(), "Billing Address", driver.findElement(By.xpath(locator.getProperty("reviewOrder2.payment.billing").toString())).getText());
            getLoginHelper().logAssertion(getClass().getSimpleName(), "Credit Card", driver.findElement(By.xpath(locator.getProperty("reviewOrder2.payment.creditCard").toString())).getText());
            
            // Click edit Payment
            editButtonElement = driver.findElement(By.xpath(locator.getProperty("reviewOrder2.payment.edit.button").toString()));
            actions.moveToElement(editButtonElement).click(editButtonElement);
            actions.perform();
        }
        
        // Navigate from payment to Review order
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locator.getProperty("payment.title").toString())));
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(locator.getProperty("payment.continue.button").toString())));
        driver.findElement(By.xpath(locator.getProperty("payment.continue.button").toString())).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locator.getProperty("reviewOrder.title").toString())));

        logger.info("END verifyPayment");     
    }

    /**
     * Verify Shipping section of review order
     * 
     * @param driver
     * @param prop
     * @param locator
     * @param user
     */
    private void verifyShipping(WebDriver driver, Properties prop, Properties locator, User user) {
        logger.info("BEGIN verifyShipping");     
        WebDriverWait wait = (WebDriverWait) new WebDriverWait(driver, 10);
        Actions actions = new Actions(driver);

        getLoginHelper().logAssertion(getClass().getSimpleName(), "SHIPPING", driver.findElement(By.xpath(locator.getProperty("reviewOrder1.shipping.title").toString())).getText());
        getLoginHelper().logAssertion(getClass().getSimpleName(), "Shipping Address", driver.findElement(By.xpath(locator.getProperty("reviewOrder1.shipping.address.label").toString())).getText());
        getLoginHelper().logAssertion(getClass().getSimpleName(), "Shipping Options", driver.findElement(By.xpath(locator.getProperty("reviewOrder1.shipping.option.label").toString())).getText());

        // Click edit Shipping
        WebElement editButtonElement = driver.findElement(By.xpath(locator.getProperty("reviewOrder1.shipping.edit.button").toString()));
        actions.moveToElement(editButtonElement).click(editButtonElement);
        actions.perform();
        
        // Navigate from shipping to Review order
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locator.getProperty("shipping.title").toString())));
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(locator.getProperty("shipping.continue.button").toString())));
        driver.findElement(By.xpath(locator.getProperty("shipping.continue.button").toString())).click();
        
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locator.getProperty("payment.title").toString())));
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(locator.getProperty("payment.continue.button").toString())));
        driver.findElement(By.xpath(locator.getProperty("payment.continue.button").toString())).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locator.getProperty("reviewOrder.title").toString())));

        logger.info("END verifyShipping");     

    }

    /**
     * Verify Shipping section of review order
     * 
     * @param driver
     * @param prop
     * @param locator
     * @param user
     */
    private void verifyShipping(WebDriver driver, Properties prop, Properties locator, User user, Map<String,Boolean> map) {
        logger.info("BEGIN verifyShipping");     
        WebDriverWait wait = (WebDriverWait) new WebDriverWait(driver, 10);
        WebDriverWait wait3 = (WebDriverWait) new WebDriverWait(driver, 3);

        Actions actions = new Actions(driver);
        WebElement editButtonElement = null;
        WebElement continueButtonElement = null;

        if(map.get("defaultShipping") && map.get("defaultPayment") && map.get("isLogged") && map.get("isUSAddress")) {
//            getLoginHelper().logAssertion(getClass().getSimpleName(), "SHIPPING", driver.findElement(By.xpath(locator.getProperty("reviewOrder1.shipping.title").toString())).getText());
//            getLoginHelper().logAssertion(getClass().getSimpleName(), "Shipping Address", driver.findElement(By.xpath(locator.getProperty("reviewOrder1.shipping.address.label").toString())).getText());
//            getLoginHelper().logAssertion(getClass().getSimpleName(), "Shipping Options", driver.findElement(By.xpath(locator.getProperty("reviewOrder1.shipping.option.label").toString())).getText());

            // Click edit Shipping
            editButtonElement = driver.findElement(By.xpath(locator.getProperty("reviewOrder1.shipping.edit.button").toString()));
            actions.moveToElement(editButtonElement).click(editButtonElement);
            actions.perform();

        } else if(map.get("defaultShipping") && map.get("defaultPayment") && map.get("isLogged") && !map.get("isUSAddress")) {
//            getLoginHelper().logAssertion(getClass().getSimpleName(), "SHIPPING", driver.findElement(By.xpath(locator.getProperty("reviewOrder2.shipping.title").toString())).getText());
//            getLoginHelper().logAssertion(getClass().getSimpleName(), "Shipping Address", driver.findElement(By.xpath(locator.getProperty("reviewOrder2.shipping.address.label").toString())).getText());
//            getLoginHelper().logAssertion(getClass().getSimpleName(), "Shipping Options", driver.findElement(By.xpath(locator.getProperty("reviewOrder2.shipping.option.label").toString())).getText());

            // Click edit Shipping
            editButtonElement = driver.findElement(By.xpath(locator.getProperty("reviewOrder2.shipping.edit.button").toString()));
            actions.moveToElement(editButtonElement).click(editButtonElement);
            actions.perform();
        }        
        
        // Navigate from shipping to Review order
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locator.getProperty("shipping.title").toString())));
        try {
            // Wait for gif image(loading) to become stale
            wait3.until(ExpectedConditions.stalenessOf(driver.findElement(By.xpath("//div[@class='sk-fading-circle']"))));
//            wait.until(ExpectedConditions.stalenessOf(driver.findElement(By.xpath("//*[@id='ext-gen44']/body/div[15]"))));
        } catch(NoSuchElementException ne) {
            logger.info("Loading Gif image cannot be located");
        }
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(locator.getProperty("shipping.continue.button").toString()))); 
        continueButtonElement = driver.findElement(By.xpath(locator.getProperty("shipping.continue.button").toString()));
        actions.moveToElement(continueButtonElement).click(continueButtonElement);
        actions.perform();
        
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locator.getProperty("payment.title").toString())));
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(locator.getProperty("payment.continue.button").toString())));
        continueButtonElement = driver.findElement(By.xpath(locator.getProperty("payment.continue.button").toString()));
        actions.moveToElement(continueButtonElement).click(continueButtonElement);
        actions.perform();
        
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locator.getProperty("reviewOrder.title").toString())));

        logger.info("END verifyShipping");     
    }

    /**
     * @return the loginHelper
     */
    public LoginHelper getLoginHelper() {
        return loginHelper;
    }

    /**
     * @param loginHelper the loginHelper to set
     */
    public void setLoginHelper(LoginHelper loginHelper) {
        this.loginHelper = loginHelper;
    }

}
