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
import com.tatcha.jscripts.dao.TestCase;
import com.tatcha.jscripts.dao.User;
import com.tatcha.jscripts.helper.TatchaTestHelper;
import com.tatcha.jscripts.login.TestLogin;
import com.tatcha.jscripts.review.ReviewOrder;
import com.tatcha.utils.BrowserDriver;

/**
 * Flow : Add to cart - Login in Checkout page - Order Review(With US address) - Place order
 * 
 * @author reshma
 *
 */
public class GiftCertificateCheckout {

    private WebDriver driver = BrowserDriver.getChromeWebDriver();
    private boolean acceptNextAlert = true;
    private StringBuffer verificationErrors = new StringBuffer();
    private Properties prop = new Properties();
    private Properties locator = new Properties();
    private Properties data = new Properties();
    private Properties giftLocator = new Properties();

    private TatchaTestHelper testHelper = new TatchaTestHelper();
    private final static Logger logger = Logger.getLogger(GiftCertificateCheckout.class);

    private static TestMethods tmethods;
    private TestCase testCase;
    private List<TestCase> tcList = new ArrayList<TestCase>();
    private final String MODULE = "Flow-15 : GiftCertificateCheckout";

    @Before
    public void setUp() throws Exception {
        prop.load(new FileInputStream(getClass().getResource("/tatcha.properties").getFile()));
        locator.load(new FileInputStream(getClass().getResource("/checkoutElementLocator.properties").getFile()));
        data.load(new FileInputStream(getClass().getResource("/GiftCertificateCheckout.properties").getFile()));
        giftLocator.load(new FileInputStream(getClass().getResource("/giftCertificateLocator.properties").getFile()));
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
     * Test the checkout flow of logged in user.
     * Pre-requisite : The user should have default shipping and payment details, 
     * 
     * @throws Exception
     */
    @Test
    public void testGiftCertificateCheckout() throws Exception {
              
        String FUNCTIONALITY = "Add Gift Certificate to cart and place order with default payment";
        testCase = new TestCase("Flow-15", "MOC-NIL", FUNCTIONALITY, "FAIL", "");

        
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy_HH:mm:ss");
        String timeStamp = sdf.format(Calendar.getInstance().getTime());
        
        ReviewOrder reviewOrder = new ReviewOrder();
        User user = new User();
        Map<String,Boolean> map = new HashMap<String,Boolean>();
        
        TestAddToCart addToCart = new TestAddToCart();
        TestLogin testLogin = new TestLogin();

        WebDriverWait wait = (WebDriverWait) new WebDriverWait(driver, 10);
        
        try {
            addToCart.addGiftCertificateToCart(driver, giftLocator, user, tcList);
            
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h2.panel-title")));
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='cart-table']/div[2]/div/div[2]/button")));
            
            // Click checkout button in shopping bag
            Actions actions = new Actions(driver);
            WebElement checkoutButtonElement = driver.findElement(By.xpath("//*[@id='cart-table']/div[2]/div/div[2]/button"));
            actions.moveToElement(checkoutButtonElement).click(checkoutButtonElement);
            actions.perform();
            
            // Login as a registered user at the checkout
            testLogin.checkoutLogin(driver, data, user, tcList);
            
            //Verify Review Order for express checkout
            reviewOrder.verifyEGiftReviewOrder(driver, prop, locator, user, map, tcList);

            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='main']/main/div[1]/div/h1")));
            getTestHelper().logAssertion(getClass().getSimpleName(), "THANK YOU FOR YOUR ORDER", driver.findElement(By.xpath("//*[@id='main']/main/div[1]/div/h1")).getText());
            timeStamp = sdf.format(Calendar.getInstance().getTime());
            logger.info("ORDER ID : "+driver.findElement(By.xpath(locator.getProperty("confirmOrder.orderId.label").toString())).getText()+" TIMESTAMP : "+timeStamp);
//            logger.info("PROFILE DETAILS : "+user.getEmail()+"/"+user.getPassword());
        
            testCase.setStatus("PASS");
            tcList.add(testCase);
            logger.info("END testGiftCertificateCheckout");
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
