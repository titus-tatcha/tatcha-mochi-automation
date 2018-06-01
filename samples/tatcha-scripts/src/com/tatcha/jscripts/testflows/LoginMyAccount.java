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
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.tatcha.jscripts.commons.TestMethods;
import com.tatcha.jscripts.commons.ReportGenerator;
import com.tatcha.jscripts.dao.TestCase;
import com.tatcha.jscripts.dao.User;
import com.tatcha.jscripts.exception.TatchaException;
import com.tatcha.jscripts.helper.TatchaTestHelper;
import com.tatcha.jscripts.login.TestLogin;
import com.tatcha.jscripts.myaccount.AddressBook;
import com.tatcha.jscripts.myaccount.OrderHistory;
import com.tatcha.jscripts.myaccount.ProfileSettings;
import com.tatcha.utils.BrowserDriver;

/**
 * Flow : My Account
 * 
 * @author Reshma
 *
 */
public class LoginMyAccount {

    private WebDriver driver = BrowserDriver.getChromeWebDriver();
    private StringBuffer verificationErrors = new StringBuffer();
    private Properties prop = new Properties();
    private Properties locator = new Properties();
    private Properties data = new Properties();

    private TatchaTestHelper testHelper = new TatchaTestHelper();
    private final static Logger logger = Logger.getLogger(LoginMyAccount.class);

    private static TestMethods tmethods;
    private TestCase testCase;
    private List<TestCase> tcList = new ArrayList<TestCase>();
    private final String MODULE = "Flow-18 : LoginMyAccount";

    @Before
    public void setUp() throws Exception {
        prop.load(new FileInputStream(getClass().getResource("/tatcha.properties").getFile()));
        locator.load(new FileInputStream(getClass().getResource("/myAccountElementLocator.properties").getFile()));
        data.load(new FileInputStream(getClass().getResource("/testFlows/LoginMyAccount.properties").getFile()));
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
     * have default shipping and payment details, and also the default address
     * should be US
     * 
     * @throws Exception
     */
    @Test
    public void testLoginMyAccount() throws Exception {

        logger.info("BEGIN testLoginMyAccount");
        String FUNCTIONALITY = "Login and verify My Account";
        testCase = new TestCase("Flow-18", "MOC-NIL", FUNCTIONALITY, "FAIL", "");

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy_HH:mm:ss");
        String timeStamp = sdf.format(Calendar.getInstance().getTime());
        logger.info(getClass() + timeStamp);
        
        User user = new User();
        ProfileSettings profile = new ProfileSettings();
        AddressBook address = new AddressBook();
//        PaymentOptions payment = new PaymentOptions();
        OrderHistory order = new OrderHistory();

        TestLogin testLogin = new TestLogin();

        Map<String, Boolean> map = new HashMap<String, Boolean>();
        map.put("isLogged", true);
        map.put("isUSAddress", true);
        map.put("isGiftCard", false);
        map.put("isCreditCard", true);
        map.put("isRegister", false);

        WebDriverWait wait = (WebDriverWait) new WebDriverWait(driver, 10);

        try {

            try {
                By closeButtonLocator = By.xpath("//*[@id='newsletterModal']/div/div/div[1]/button");
                wait.until(ExpectedConditions
                        .visibilityOfElementLocated(By.xpath("//*[@id='newsletterModal']/div/div/div[1]/h4")));
                wait.until(ExpectedConditions.elementToBeClickable(closeButtonLocator));
                // Click close button
                driver.findElement(closeButtonLocator).click();
                logger.info("Newsletter is present");
                wait.until(ExpectedConditions
                        .invisibilityOfElementLocated(By.xpath("//*[@id='newsletterModal']/div/div/div[1]/h4")));

            } catch (TimeoutException te) {
                logger.info("Newsletter is NOT present");
            }
            
            // User is logged in
            testLogin.login(driver, data, locator, user, tcList);
            logger.info("SUCCESSFULLY LOGGED-IN");
            
            // Assert and test profile settings in my account
            profile.verifyProfileSettings(driver, prop, locator, data, user, tcList);
            driver.findElement(By.xpath(locator.getProperty("account.back").toString())).click();
            logger.info("Profile Settings done");

            // Assert and test address book in my account
            address.verifyAddressBook(driver, prop, locator, tcList);
            driver.findElement(By.xpath(locator.getProperty("account.back").toString())).click();
            logger.info("Address Book done");

            // Assert and test payment options in my account - Cannot retrieve braintree fields
//            payment.verifyPaymentOptions(driver, prop, locator);
//            driver.findElement(By.xpath(locator.getProperty("account.back").toString())).click();
            
            // Assert and test order history in my account
            order.verifyOrderHistory(driver, prop, locator, tcList);
            driver.findElement(By.xpath(locator.getProperty("account.back").toString())).click();
            logger.info("Order History done");

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
        logger.info("END testLoginMyAccount");
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
