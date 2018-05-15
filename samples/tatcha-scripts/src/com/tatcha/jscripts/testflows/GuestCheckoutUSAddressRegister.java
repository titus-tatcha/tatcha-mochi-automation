package com.tatcha.jscripts.testflows;

import static org.junit.Assert.fail;

import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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

import com.tatcha.jscripts.bag.TestAddToCart;
import com.tatcha.jscripts.commons.ReportGenerator;
import com.tatcha.jscripts.commons.TestMethods;
import com.tatcha.jscripts.dao.Product;
import com.tatcha.jscripts.dao.TestCase;
import com.tatcha.jscripts.dao.User;
import com.tatcha.jscripts.helper.TatchaTestHelper;
import com.tatcha.jscripts.login.TestLogin;
import com.tatcha.jscripts.payment.PaymentOption;
import com.tatcha.jscripts.review.ReviewOrder;
import com.tatcha.jscripts.shipping.ShippingAddress;
import com.tatcha.utils.BrowserDriver;

/**
 * Flow : Add to cart - Checkout as guest - Add US address - Add Gift card - Register and Place order
 * 
 * @author reshma
 *
 */
public class GuestCheckoutUSAddressRegister {

    private WebDriver driver = BrowserDriver.getChromeWebDriver();
    private boolean acceptNextAlert = true;
    private StringBuffer verificationErrors = new StringBuffer();
    private Properties prop = new Properties();
    private Properties locator = new Properties();
    private Properties data = new Properties();

    private TatchaTestHelper testHelper = new TatchaTestHelper();
    private final static Logger logger = Logger.getLogger(GuestCheckoutUSAddressRegister.class);

    private static TestMethods tmethods;
    private TestCase testCase;
    private List<TestCase> tcList = new ArrayList<TestCase>();
    private final String MODULE = "Flow-11 : GuestCheckoutUSAddressRegister";
    
    @Before
    public void setUp() throws Exception {
        prop.load(new FileInputStream(getClass().getResource("/tatcha.properties").getFile()));
        locator.load(new FileInputStream(getClass().getResource("/checkoutElementLocator.properties").getFile()));
        data.load(new FileInputStream(getClass().getResource("/GuestCheckoutUSAddressRegister.properties").getFile()));
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

        boolean testInLocal = Boolean.parseBoolean(prop.getProperty("testInLocal").toString());
        if(testInLocal) {            
            String url = data.getProperty("url").toString();
            driver.get(url);
            getTestHelper().basicAuth(url);
            driver.manage().window().maximize();
        } else {
            tmethods = TestMethods.getInstance();
            String baseUrl = tmethods.getBaseURL();
            driver.get(baseUrl);
            driver.manage().window().maximize();
        }
    }

    /**
     * Test the Checkout flow of guest user with US address
     * 
     * @throws Exception
     */
    @Test
    public void testGuestCheckoutUSAddressRegister() throws Exception {

        String FUNCTIONALITY = "Checkout as guest with US address and register in order review page";
        testCase = new TestCase("Flow-11", "MOC-NIL", FUNCTIONALITY, "FAIL", "");
        
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy_HH:mm:ss");
        String timeStamp = sdf.format(Calendar.getInstance().getTime());
        
        logger.info(getClass()+timeStamp);

        ShippingAddress shipping = new ShippingAddress();
        PaymentOption payment = new PaymentOption();
        ReviewOrder reviewOrder = new ReviewOrder();
        User user = new User();
        TestAddToCart addToCart = new TestAddToCart();
        TestLogin testLogin = new TestLogin();
        
        Map<String,Boolean> map = new HashMap<String,Boolean>();     
        map.put("isLogged", false);
        map.put("isUSAddress", true);
        map.put("isGiftCard", true);
        map.put("isCreditCard", false);
        map.put("isRegister", true);
        
        WebDriverWait wait = (WebDriverWait) new WebDriverWait(driver, 10);
        
        try {
            addToCart.addSpecificProductToCart(driver, prop, locator, user, tcList);
            
            // wait till shopping bag title is visible and checkout button is clickable
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h2.panel-title")));
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='cart-table']/div[2]/div/div[2]/button")));
            
            // Click checkout button in shopping bag
            Actions actions = new Actions(driver);
            WebElement checkoutButtonElement = driver.findElement(By.xpath("//*[@id='cart-table']/div[2]/div/div[2]/button"));
            actions.moveToElement(checkoutButtonElement).click(checkoutButtonElement);
            actions.perform();
            
            // Login as a registered user at the checkout
            testLogin.checkoutGuest(driver, data, user, tcList);
             
            // Verify shipping address page
            shipping.verifyShippingAddress(driver, prop, locator, user, map, data, tcList);
            
            // Verify payment page
            payment.verifyPaymentOption2(driver, prop, locator, user, map, tcList);
 
            //Verify Review Order
            reviewOrder.verifyGuestReviewOrder(driver, prop, locator, user, map, data, tcList);

            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='main']/main/div[1]/div/h1")));
            getTestHelper().logAssertion(getClass().getSimpleName(), "THANK YOU FOR YOUR ORDER", driver.findElement(By.xpath("//*[@id='main']/main/div[1]/div/h1")).getText());
            timeStamp = sdf.format(Calendar.getInstance().getTime());
            logger.info("ORDER ID : "+driver.findElement(By.xpath(locator.getProperty("confirmOrder.orderId.label").toString())).getText()+" TIMESTAMP : "+timeStamp);

            testCase.setStatus("PASS");
            tcList.add(testCase);
            logger.info("END testGuestCheckoutUSAddressRegister");
            ReportGenerator.getInstance().generateReport(MODULE, tcList);        
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
                
        // Get Product name
        String productName = driver.findElement(By.xpath("//*[@id='product-content']/div[2]/h1")).getText();
        product.setName(productName);
        
        // Get Product price
        WebElement priceElement = driver.findElement(By.xpath("//*[@id='product-content']/div[3]/div/div[1]/div/span"));
        product.setPrice(priceElement.getText());
        product.setWasPrice(priceElement.getText());
        products.add(product);   
        
        // Add to cart
        driver.findElement(By.id("add-to-cart")).click();
        logger.info("Item added to cart");
     
        // Set the products associated with user
        user.setProducts(products);
        
        // Click View bag in the modal box
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='addToBagModal']/div/div/div[1]/h4/strong")));
        } catch(TimeoutException te) {
            driver.findElement(By.id("add-to-cart")).click();
        }
        driver.findElement(By.xpath("//*[@id='addToBagModal']/div/div/div[3]/div/div[2]/a")).click();
        
        // Wait until shopping bag title appears
        wait.until(ExpectedConditions.visibilityOfElementLocated((By.xpath("//*[@id='ext-gen44']/body/main/div[2]/h1"))));
        
    }

    /**
     * Checkout as a guest user
     * 
     * @param driver
     * @param prop
     * @param locator
     * @param user
     */
    private void checkoutGuest(WebDriver driver, Properties prop, Properties locator, User user) {
        
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
