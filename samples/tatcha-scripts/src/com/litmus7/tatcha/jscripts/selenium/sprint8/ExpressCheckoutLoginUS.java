package com.litmus7.tatcha.jscripts.selenium.sprint8;

import static org.junit.Assert.fail;

import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.litmus7.tatcha.jscripts.dob.Product;
import com.litmus7.tatcha.jscripts.dob.User;
import com.litmus7.tatcha.jscripts.selenium.sprint3.LoginHelper;
//import com.litmus7.tatcha.jscripts.selenium.sprint6.AddToCartLogInCheckout;
import com.litmus7.tatcha.jscripts.selenium.sprint8.ReviewOrderCheckout;
import com.litmus7.tatcha.utils.BrowserDriver;

public class ExpressCheckoutLoginUS {

    private WebDriver driver = BrowserDriver.getChromeWebDriver();
    private boolean acceptNextAlert = true;
    private StringBuffer verificationErrors = new StringBuffer();
    private Properties prop = new Properties();
    private Properties locator = new Properties();
    private LoginHelper loginHelper = new LoginHelper();
    private final static Logger logger = Logger.getLogger(ExpressCheckoutLoginUS.class);

    @Before
    public void setUp() throws Exception {
        prop.load(new FileInputStream(getClass().getResource("/tatcha.properties").getFile()));
        locator.load(new FileInputStream(getClass().getResource("/checkoutElementLocator.properties").getFile()));

        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        String url = "http://development-na01-tatcha.demandware.net/s/tatcha/p-indigo-hand-cream/INDG-HAND.html?lang=default";
        driver.get(url);
    }

    /**
     * Test the checkout flow of logged in user.
     * Pre-requisite : The user should have default shipping and payment details, 
     * and also the default address should be US
     * 
     * @throws Exception
     */
    @Test
    public void testExpressCheckoutLoginUS() throws Exception {

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy_HH:mm:ss");
        String timeStamp = sdf.format(Calendar.getInstance().getTime());
        
        logger.info("VERIFYING CHECKOUT FLOW FOR LOGGED IN USER WITH US ADDRESS : "+getClass()+timeStamp);
        ReviewOrderCheckout reviewOrder = new ReviewOrderCheckout();
        User user = new User();
        Map<String,Boolean> map = new HashMap<String,Boolean>();
        
        map.put("defaultShipping", true);
        map.put("defaultPayment", true);
        map.put("isLogged", true);
        map.put("isUSAddress", true);
        
        WebDriverWait wait = (WebDriverWait) new WebDriverWait(driver, 10);
        
        try {
            addSpecificProductToCart(driver, prop, locator, user);
            
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h2.panel-title")));
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='cart-table']/div[2]/div/div[2]/button")));
            
            // Click checkout button in shopping bag
            Actions actions = new Actions(driver);
            WebElement checkoutButtonElement = driver.findElement(By.xpath("//*[@id='cart-table']/div[2]/div/div[2]/button"));
            actions.moveToElement(checkoutButtonElement).click(checkoutButtonElement);
            actions.perform();
            
            // Login as a registered user at the checkout
            checkoutLogin(driver, prop, locator, user);
            
            //Verify Review Order
            reviewOrder.verifyReviewOrder(driver, prop, locator, user, map);

            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='main']/main/div[1]/div/h1")));
            getLoginHelper().logAssertion(getClass().getSimpleName(), "THANK YOU FOR YOUR ORDER", driver.findElement(By.xpath("//*[@id='main']/main/div[1]/div/h1")).getText());
            timeStamp = sdf.format(Calendar.getInstance().getTime());
            logger.info("ORDER ID : "+driver.findElement(By.xpath(locator.getProperty("confirmOrder.orderId.label").toString())).getText()+" TIMESTAMP : "+timeStamp);
            logger.info("PROFILE DETAILS : "+user.getEmail()+"/"+user.getPassword());
        
        } catch (NoSuchElementException ne) {
            System.err.println("LOGIN : ELEMENT NOT FOUND " + ne.toString());
        } catch (ElementNotVisibleException nv) {
            System.err.println("LOGIN : ELEMENT NOT VISIBLE " + nv.toString());
        } catch (TimeoutException te) {
            System.err.println("LOGIN : TIMEOUT " + te.toString());
        } catch (StaleElementReferenceException sr) {
            System.err.println("LOGIN : STALE ELE REF " + sr.toString());
        } catch (WebDriverException we) {
            System.err.println("LOGIN : WEBDRIVER ISSUE " + we.toString());
        }
    }
    
    /**
     * Goto the url of a product's pdp and add it to cart
     * 
     * @param driver2
     * @param prop2
     * @param locator2
     * @param user
     */
    private void addSpecificProductToCart(WebDriver driver2, Properties prop2, Properties locator2, User user) {
        
        logger.info("BEGIN addSpecificProductToCart");
        WebDriverWait wait = (WebDriverWait) new WebDriverWait(driver, 10);

        List<Product> products = new ArrayList<Product>();
        Product product = new Product();
                
        String productName = driver.findElement(By.xpath("//*[@id='product-content']/div[2]/h1")).getText();
        product.setName(productName);
        
        WebElement priceElement = driver.findElement(By.xpath("//*[@id='product-content']/div[3]/div/div[1]/div/span"));
        product.setPrice(priceElement.getText());
        product.setWasPrice(priceElement.getText());
        products.add(product);   
        
        // Add to cart
        driver.findElement(By.id("add-to-cart")).click();
        logger.info("Item added to cart");
     
        // Set the products associated with user(guest)
        user.setProducts(products);
        
        // Click View bag in the modal box
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='addToBagModal']/div/div/div[1]/h4/strong")));
        driver.findElement(By.xpath("//*[@id='addToBagModal']/div/div/div[3]/div/div[2]/a")).click();
        
        wait.until(ExpectedConditions.visibilityOfElementLocated((By.xpath("//*[@id='ext-gen44']/body/main/div[2]/h1"))));
        
    }

    /**
     * Handles User login after adding to cart and 
     * clicking checkout in shopping bag as guest user
     * 
     * @param driver
     * @param prop
     * @param locator
     * @param user
     */
    private void checkoutLogin(WebDriver driver, Properties prop, Properties locator, User user) {
        
        WebDriverWait wait = (WebDriverWait) new WebDriverWait(driver, 10);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='login-fb-checkout']/div/div[1]/h1")));
        
        // Enter registered email id
        WebElement emailIdElement = driver.findElement(By.cssSelector("input#dwfrm_login_username.input-text.form-control.required"));
        emailIdElement.clear();
        emailIdElement.sendKeys("us@test.com");
        
        // Click continue
        driver.findElement(By.xpath("//*[@id='dwfrm_login']/button")).click();
        
        // Wait until page refreshed
        wait.until(ExpectedConditions.refreshed(ExpectedConditions.stalenessOf(emailIdElement)));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='dwfrm_login']/div[3]/div[1]/div/h5")));

        // Enter password
        WebElement passwordElement = driver.findElement(By.xpath("//*[@id='dwfrm_login_password']"));
        passwordElement.clear();
        passwordElement.sendKeys("tatcha123");
        
        // Click continue
        driver.findElement(By.xpath("//*[@id='dwfrm_login']/div[3]/div[2]/div/button")).click();
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
    
    @After
    public void tearDown() throws Exception {
        driver.quit();
        String verificationErrorString = verificationErrors.toString();
        if (!"".equals(verificationErrorString)) {
            fail(verificationErrorString);
        }
    }

    private boolean isElementPresent(By by) {
        try {
            driver.findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    private boolean isAlertPresent() {
        try {
            driver.switchTo().alert();
            return true;
        } catch (NoAlertPresentException e) {
            return false;
        }
    }

    private String closeAlertAndGetItsText() {
        try {
            Alert alert = driver.switchTo().alert();
            String alertText = alert.getText();
            if (acceptNextAlert) {
                alert.accept();
            } else {
                alert.dismiss();
            }
            return alertText;
        } finally {
            acceptNextAlert = true;
        }
    }
}
