package com.litmus7.tatcha.jscripts.selenium.sprint3;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.litmus7.tatcha.jscripts.dob.User;

public class PaymentOptions {
    
    private LoginHelper loginHelper = new LoginHelper();
    public HashMap<String, String> userDetails = null;
    private final static Logger logger = Logger.getLogger(PaymentOptions.class);
    
    public PaymentOptions(HashMap<String, String> userDetails) {
        this.userDetails = userDetails;
    }
    
    public PaymentOptions() {
        
    }
    /**
     * Handle payment option
     * 
     * @param driver
     * @param prop
     * @param locator
     * @throws Exception
     */
    public void paymentOptions(WebDriver driver, Properties prop, Properties locator) throws Exception {
    	logger.info("inside profileSettings");
        if (getLoginHelper().isLoggedIn(driver)) {
            driver.findElement(By.xpath(locator.getProperty("account.payment").toString())).click();

            // Assert Title
            WebElement webElement = driver.findElement(By.cssSelector("h1.text-center"));
            assertEquals(prop.getProperty("myaccount.item5").toString(), webElement.getText());

            // Assert Default card
            WebElement defaultAddressElement = driver
                    .findElement(By.xpath(locator.getProperty("payment.default").toString()));
            assertEquals(prop.getProperty("myaccount.item5.title2").toString(), defaultAddressElement.getText());

            // Assert Add card
            WebElement addAddressElement = driver.findElement(By.xpath(locator.getProperty("payment.add").toString()));
            assertEquals(prop.getProperty("myaccount.item5.title3").toString(), addAddressElement.getText());

            // Add Credit Card
            driver.findElement(By.xpath(locator.getProperty("payment.add.button").toString())).click();
            addCreditCard(driver, prop, locator);
            System.out.println("Credit card Added");
            
//            // Make a Credit card the default Credit card
//            WebElement makeDefaultButtonElement = null;
//            makeDefaultButtonElement = driver
//                    .findElement(By.xpath(locator.getProperty("payment.makeDefault").toString()));
//            makeDefaultButtonElement.click();
//            System.out.println("Credit card set as default");
//
//            // Make a Credit card the default Credit card
//            makeDefaultButtonElement = driver
//                    .findElement(By.xpath(locator.getProperty("payment.makeDefault").toString()));
//            makeDefaultButtonElement.click();
//            System.out.println("Credit card set as default");
//
//            // Remove a Credit card
//            removeCreditCard(driver, prop, locator);
//            System.out.println("Credit card removed");
        }
    }
    
    /**
     * Handle add credit card
     * 
     * @param driver
     * @param prop
     * @param locator
     */
    private void addCreditCard(WebDriver driver, Properties prop, Properties locator) {
        
        // Get all the web elements in add credit card page
        WebElement addPaymentTitleElement = driver.findElement(By.xpath(locator.getProperty("addPayment.title").toString()));
        WebElement addPaymentNameElement = driver.findElement(By.xpath(locator.getProperty("addPayment.name").toString()));
        WebElement addPaymentCardNoElement = driver.findElement(By.xpath(locator.getProperty("addPayment.cardNo").toString()));
        WebElement addPaymentCvvElement = driver.findElement(By.xpath(locator.getProperty("addPayment.cvv").toString()));
        WebElement addPaymentExpirationElement = driver.findElement(By.xpath(locator.getProperty("addPayment.expiration").toString()));       
        WebElement addPaymentCheckBoxElement = driver.findElement(By.xpath(locator.getProperty("addPayment.checkbox").toString()));
        WebElement addPaymentSaveButtonElement = driver.findElement(By.xpath(locator.getProperty("addPayment.save.button").toString()));
        
        // Assert title
        assertEquals(prop.getProperty("myaccount.item5.title3").toString().toUpperCase(), addPaymentTitleElement.getText());
        
        // Populate name
        addPaymentNameElement.clear();
        addPaymentNameElement.sendKeys(prop.getProperty("addPayment.name").toString());
        
        // Populate credit card no
        addPaymentCardNoElement.clear();
        addPaymentCardNoElement.sendKeys(prop.getProperty("addPayment.cardNo").toString());
        
        // Populate cvv
        addPaymentCvvElement.clear();
        addPaymentCvvElement.sendKeys(prop.getProperty("addPayment.cvv").toString());
        
        // Populate expiration
        addPaymentExpirationElement.clear();
        addPaymentExpirationElement.sendKeys(prop.getProperty("addPayment.expiration").toString());
        
        // Mark as default payment option
        if(!addPaymentCheckBoxElement.isSelected()) {
            addPaymentCheckBoxElement.click();
        }
        
        // Save Credit Card
        addPaymentSaveButtonElement.click();
        
    }

    private void removeCreditCard(WebDriver driver, Properties prop, Properties locator) {
        // TODO Auto-generated method stub
        
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
