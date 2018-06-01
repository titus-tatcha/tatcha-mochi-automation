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

import com.tatcha.jscripts.bag.TestAddToCart;
import com.tatcha.jscripts.commons.ReportGenerator;
import com.tatcha.jscripts.dao.TestCase;
import com.tatcha.jscripts.commons.TestMethods;
import com.tatcha.jscripts.confirmation.TestOrderConfirmation;
import com.tatcha.jscripts.dao.User;
import com.tatcha.jscripts.exception.TatchaException;
import com.tatcha.jscripts.helper.TatchaTestHelper;
import com.tatcha.jscripts.login.TestLogin;
import com.tatcha.jscripts.payment.PaymentOption;
import com.tatcha.jscripts.review.ReviewOrder;
import com.tatcha.jscripts.shipping.ShippingAddress;
import com.tatcha.utils.BrowserDriver;

/**
 * Flow : Add to cart - Checkout as guest - Add international address - Add Gift
 * card - Register and Place order
 * 
 * @author Reshma
 *
 */
public class GuestCheckoutInternationalAddressRegister {

    private WebDriver driver = BrowserDriver.getChromeWebDriver();
    private StringBuffer verificationErrors = new StringBuffer();
    private Properties prop = new Properties();
    private Properties locator = new Properties();
    private Properties data = new Properties();

    private TatchaTestHelper testHelper = new TatchaTestHelper();
    private final static Logger logger = Logger.getLogger(GuestCheckoutInternationalAddressRegister.class);

    private static TestMethods tmethods;
    private TestCase testCase;
    private List<TestCase> tcList = new ArrayList<TestCase>();
    private final String MODULE = "Flow-12 : GuestCheckoutInternationalAddressRegister";

    @Before
    public void setUp() throws Exception {
        prop.load(new FileInputStream(getClass().getResource("/tatcha.properties").getFile()));
        locator.load(new FileInputStream(getClass().getResource("/checkoutElementLocator.properties").getFile()));
        data.load(new FileInputStream(
                getClass().getResource("/testFlows/GuestCheckoutInternationalAddressRegister.properties").getFile()));
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
     * Test the Checkout flow of guest user with international address
     * 
     * @throws Exception
     */
    @Test
    public void testGuestCheckoutInternationalAddressRegister() throws Exception {

        String FUNCTIONALITY = "Checkout as guest with international address and register in order review page";
        testCase = new TestCase("Flow-12", "MOC-NIL", FUNCTIONALITY, "FAIL", "");

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy_HH:mm:ss");
        String timeStamp = sdf.format(Calendar.getInstance().getTime());
        logger.info(getClass() + timeStamp);

        ShippingAddress shipping = new ShippingAddress();
        PaymentOption payment = new PaymentOption();
        ReviewOrder reviewOrder = new ReviewOrder();
        User user = new User();
        TestAddToCart addToCart = new TestAddToCart();
        TestLogin testLogin = new TestLogin();

        Map<String, Boolean> map = new HashMap<String, Boolean>();
        map.put("isLogged", false);
        map.put("isUSAddress", false);
        map.put("isGiftCard", true);
        map.put("isCreditCard", false);
        map.put("isRegister", true);

        WebDriverWait wait = (WebDriverWait) new WebDriverWait(driver, 10);

        try {
            addToCart.addSpecificProductToCart(driver, prop, locator, user, tcList);

            // wait till shopping bag title is visible and checkout button is
            // clickable
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h2.panel-title")));
            wait.until(ExpectedConditions
                    .elementToBeClickable(By.xpath("//*[@id='cart-table']/div[2]/div/div[2]/button")));

            // Click checkout button in shopping bag
            Actions actions = new Actions(driver);
            WebElement checkoutButtonElement = driver
                    .findElement(By.xpath("//*[@id='cart-table']/div[2]/div/div[2]/button"));
            actions.moveToElement(checkoutButtonElement).click(checkoutButtonElement);
            actions.perform();

            // Login as a registered user at the checkout
            testLogin.checkoutGuest(driver, data, user, tcList);

            // Verify shipping address page
            shipping.verifyShippingAddress(driver, prop, locator, user, map, data, tcList);

            // Verify payment page
            payment.verifyPaymentOption2(driver, prop, locator, user, map, tcList);

            // Verify Review Order
            reviewOrder.verifyGuestReviewOrder(driver, prop, locator, user, map, data, tcList);

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
        logger.info("END testGuestCheckoutInternationalAddressRegister");
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
