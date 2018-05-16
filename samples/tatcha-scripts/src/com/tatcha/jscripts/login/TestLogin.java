package com.tatcha.jscripts.login;

import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.tatcha.jscripts.bag.TestCartItems;
import com.tatcha.jscripts.dao.TestCase;
import com.tatcha.jscripts.dao.User;

public class TestLogin {

    private final static Logger logger = Logger.getLogger(TestCartItems.class);
    private TestCase testCase;
    
    /**
     * Handles User login after adding to cart and 
     * clicking checkout in shopping bag as guest user
     * 
     * @param driver
     * @param prop
     * @param locator
     * @param user
     * @param tcList
     */
    public void checkoutLogin(WebDriver driver, Properties data, User user, List<TestCase> tcList) {
        logger.info("BEGIN checkoutLogin");
        String FUNCTIONALITY = "Login in the checkout flow";
        testCase = new TestCase("TC-3.1", "MOC-NIL", FUNCTIONALITY, "FAIL", "");
        
        WebDriverWait wait = (WebDriverWait) new WebDriverWait(driver, 10);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='login-fb-checkout']/div/div[1]/h1")));
        
        // Enter registered email id
        WebElement emailIdElement = driver.findElement(By.cssSelector("input#dwfrm_login_username.input-text.form-control.required"));
        emailIdElement.clear();
        emailIdElement.sendKeys(data.getProperty("email").toString());
        user.setEmail(data.getProperty("email").toString());
        // Click continue
        driver.findElement(By.xpath("//*[@id='dwfrm_login']/button")).click();
        
        // Wait until page refreshed
        wait.until(ExpectedConditions.refreshed(ExpectedConditions.stalenessOf(emailIdElement)));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='dwfrm_login']/div[3]/div[1]/div/h5")));

        // Enter password
        WebElement passwordElement = driver.findElement(By.xpath("//*[@id='dwfrm_login_password']"));
        passwordElement.clear();
        passwordElement.sendKeys(data.getProperty("password").toString());
        user.setPassword(data.getProperty("password").toString());
        // Click continue
        driver.findElement(By.xpath("//*[@id='dwfrm_login']/div[3]/div[2]/div/button")).click();
        testCase.setStatus("PASS");
        tcList.add(testCase);
        logger.info("END checkoutLogin");
    }
    
    /**
     * Checkout as a guest user
     * 
     * @param driver
     * @param prop
     * @param locator
     * @param user
     * @param tcList
     */
    public void checkoutGuest(WebDriver driver, Properties data, User user, List<TestCase> tcList) {
        logger.info("BEGIN checkoutGuest");
        String FUNCTIONALITY = "Checkout as a guest";
        testCase = new TestCase("TC-3.2", "MOC-NIL", FUNCTIONALITY, "FAIL", "");
        
        WebDriverWait wait = (WebDriverWait) new WebDriverWait(driver, 10);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='login-fb-checkout']/div/div[1]/h1")));
        
        // Enter registered email id
        WebElement emailIdElement = driver.findElement(By.xpath("//*[@id='dwfrm_login_username']"));
        emailIdElement.clear();
        emailIdElement.sendKeys(data.getProperty("email").toString());
        
        // Click continue
        driver.findElement(By.xpath("//*[@id='dwfrm_login']/button")).click();
        
        // Wait until page refreshed
        wait.until(ExpectedConditions.refreshed(ExpectedConditions.stalenessOf(emailIdElement)));
        testCase.setStatus("PASS");
        tcList.add(testCase);
        logger.info("END checkoutGuest");
    }
}
