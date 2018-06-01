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
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.tatcha.jscripts.bag.ShoppingBag;
import com.tatcha.jscripts.bag.TestAddToCart;
import com.tatcha.jscripts.commons.ReportGenerator;
import com.tatcha.jscripts.dao.TestCase;
import com.tatcha.jscripts.commons.TestMethods;
import com.tatcha.jscripts.confirmation.TestOrderConfirmation;
import com.tatcha.jscripts.dao.User;
import com.tatcha.jscripts.exception.TatchaException;
import com.tatcha.jscripts.helper.TatchaTestHelper;
import com.tatcha.jscripts.login.TestLogin;
import com.tatcha.jscripts.review.ReviewOrder;
import com.tatcha.jscripts.review.TestPaymentSection;
import com.tatcha.jscripts.summary.TestSummary;
import com.tatcha.utils.BrowserDriver;

/**
 * Flow : 13
 * 
 * @author Reshma
 *
 */
public class BagAutoDelivery {

    private WebDriver driver = BrowserDriver.getChromeWebDriver();
    private StringBuffer verificationErrors = new StringBuffer();
    private Properties prop = new Properties();
    private Properties locator = new Properties();
    private Properties bagLocator = new Properties();
    private Properties data = new Properties();

    private TatchaTestHelper testHelper = new TatchaTestHelper();
    private final static Logger logger = Logger.getLogger(BagAutoDelivery.class);

    private static TestMethods tmethods;
    private TestCase testCase;
    private List<TestCase> tcList = new ArrayList<TestCase>();
    private final String MODULE = "Flow-19 : BagAutoDelivery";

    @Before
    public void setUp() throws Exception {
        prop.load(new FileInputStream(getClass().getResource("/tatcha.properties").getFile()));
        locator.load(new FileInputStream(getClass().getResource("/checkoutElementLocator.properties").getFile()));
        bagLocator.load(new FileInputStream(getClass().getResource("/shoppingBagElementLocator.properties").getFile()));
        data.load(new FileInputStream(getClass().getResource("/testFlows/BagAutoDelivery.properties").getFile()));

        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

        boolean testInLocal = Boolean.parseBoolean(prop.getProperty("testInLocal").toString());
        if (testInLocal) {
            String url = data.getProperty("url").toString();
            driver.get(url);
        } else {
            tmethods = TestMethods.getInstance();
            String baseUrl = tmethods.getBaseURL();
            driver.get(baseUrl);
        }
    }

    /**
     * Test the checkout flow of logged in user. Pre-requisite : The user should
     * have default shipping and payment details,
     * 
     * @throws Exception
     */
    @Test
    public void testBagAutoDelivery() throws Exception {

        String FUNCTIONALITY = "Enable auto delivery for a product from shopping bag and checkout";
        testCase = new TestCase("Flow-19", "MOC-NIL", FUNCTIONALITY, "FAIL", "");

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy_HH:mm:ss");
        String timeStamp = sdf.format(Calendar.getInstance().getTime());
        logger.info(getClass() + timeStamp);

        ReviewOrder reviewOrder = new ReviewOrder();
        User user = new User();
        Map<String, Boolean> map = new HashMap<String, Boolean>();

        TestAddToCart addToCart = new TestAddToCart();
        TestLogin testLogin = new TestLogin();
        ShoppingBag testBag = new ShoppingBag();
        TestSummary testSummary = new TestSummary();
        TestPaymentSection testPayment = new TestPaymentSection();
        
        map.put("isLogged", true);
        map.put("isUSAddress", true);
        map.put("isGiftCard", false);
        map.put("isCreditCard", true);
        map.put("isRegister", false);
        map.put("verifySamples", false);
        map.put("addSamples", false);
        map.put("verifyProducts", true);
        map.put("enableAutoDelivery", true);
        map.put("verifyGiftWrap", false);
        map.put("addGiftWrap", false);
        map.put("verifyPromo", false);
        map.put("applyPromo", false);
        map.put("verifyGiftCard", true);

        WebDriverWait wait = (WebDriverWait) new WebDriverWait(driver, 10);

        try {
            addToCart.addSpecificProductToCart(driver, prop, locator, user, tcList);

            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h2.panel-title")));
            wait.until(ExpectedConditions
                    .elementToBeClickable(By.xpath("//*[@id='cart-table']/div[2]/div/div[2]/button")));

            testBag.verifyShoppingBag(driver, prop, bagLocator, user, map, tcList);

            // Click checkout button in shopping bag
            Actions actions = new Actions(driver);
            WebElement checkoutButtonElement = driver
                    .findElement(By.xpath("//*[@id='cart-table']/div[2]/div/div[2]/button"));
            actions.moveToElement(checkoutButtonElement).click(checkoutButtonElement);
            actions.perform();

            // Login as a registered user at the checkout
            testLogin.checkoutLogin(driver, data, user, tcList);

            // Verify Review Order for express checkout
            reviewOrder.verifyReviewOrder2(driver, prop, locator, user, map, tcList, false);

            testSummary.testSummary(driver, prop, locator, user, map, tcList);

            testPayment.testPaymentSection(driver, prop, locator, user, map, tcList);
            
            // Click place order button
            By placeOrderButtonLocator = By.xpath(locator.getProperty("reviewOrder.placeOrder.button").toString());
            WebElement placeOrderButtonElement = driver.findElement(placeOrderButtonLocator);
            if (placeOrderButtonElement.isEnabled()) {
                placeOrderButtonElement.click();
            }

            TestOrderConfirmation testConf = new TestOrderConfirmation();
            testConf.verifyOrderConfirmation(driver, prop, locator, user, tcList);

            testCase.setStatus("PASS");
            tcList.add(testCase);
        } catch (Exception exp) {
            try {
                throw new TatchaException(exp, tcList);
            } catch (Exception e) {
                logger.error("Handling Tatcha Exception " + e.toString());
            }
        }
        boolean generateReport = Boolean.parseBoolean(prop.getProperty("generateReport").toString());
        if (generateReport && ReportGenerator.getInstance().generateReport(MODULE, tcList)) {
            logger.info("Report Generation Succeeded for: " + MODULE);
        } else {
            logger.info("Report Generation Failed for: " + MODULE);
        }
        logger.info("END BagAutoDelivery");
    }

    /**
     * @return the testHelper
     */
    public TatchaTestHelper getTestHelper() {
        return testHelper;
    }

    /**
     * @param testHelper
     *            the testHelper to set
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
}
