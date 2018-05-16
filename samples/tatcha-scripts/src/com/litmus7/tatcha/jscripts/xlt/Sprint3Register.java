package com.litmus7.tatcha.jscripts.xlt;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

import com.litmus7.tatcha.utils.BrowserDriver;
import com.xceptance.xlt.api.engine.scripting.AbstractWebDriverScriptTestCase;
import com.xceptance.xlt.api.webdriver.XltDriver;

/**
 * TODO: Add class description
 */
public class Sprint3Register extends AbstractWebDriverScriptTestCase
{
	private final static Logger logger = Logger.getLogger(Sprint3Register.class);
	private static WebDriver driver = new XltDriver();
//	private static WebDriver driver = BrowserDriver.getChromeWebDriver();
	private static String baseUrl = BrowserDriver.BASE_URL;

    /**
     * Constructor.
     */
    public Sprint3Register()
    {
        super(driver, baseUrl);
//        https://development-na01-tatcha.demandware.net/on/demandware.store/Sites-tatcha-Site/
    }

//    https://development-na01-tatcha.demandware.net/s/tatcha/home?lang=default
    /**
     * Executes the test.
     *
     * @throws Throwable if anything went wrong
     */
    @Test
    public void test() throws Throwable
    {
        //
        // ~~~ Open ~~~
        //
        startAction("Open");
        open("/s/tatcha/home?lang=default");
        //
        // ~~~ Register ~~~
        //
        startAction("Register");
        logger.info("------------Register------------");
        clickAndWait("link=LOGIN");
//        clickAndWait("//*[@id='ext-gen44']/body/div[1]/div[2]");
//        clickAndWait("cssSelector = a[href*='tatcha/account']");
       
        clickAndWait("document.forms[3].dwfrm_login_register");
        //
        // ~~~ Fillup ~~~
        //
        logger.info("------------Fill UP------------");
        startAction("Fillup");
        type("id=dwfrm_profile_customer_firstname", "John11");
        type("id=dwfrm_profile_customer_lastname", "Jacob11");
        type("id=dwfrm_profile_customer_email", "john.jacob11@qa.com");
        type("id=dwfrm_profile_login_password", "jacob123");
        type("id=dwfrm_profile_login_passwordconfirm", "jacob123");
        clickAndWait("document.forms[2].dwfrm_profile_confirm");
        //
        // ~~~ Check ~~~
        //
//        startAction("Check");
//        logger.info("------------Click on Profile------------");
//        clickAndWait("//html[@id='ext-gen44']/body/main/div/div/div/div/div/div/ul/li[1]/a/div[1]/h4");
      //*[@id="ext-gen44"]/body/main/div/div/div/div/div/div/ul/li[1]/a/div[1]/h4
        
        //
        // ~~~ Logout ~~~
        //
//        startAction("Logout");
//        logger.info("------------Logout------------");
//        click("//*[@id='dropdownMenu1']");
//        clickAndWait("link=Logout");
//        clickAndWait("//*[@id='ext-gen44']/body/div[1]/div[2]/div/div/div[2]/div/div/ul/li[4]/a");
   
	        
        // error message
//      *[@id="RegistrationForm"]/div

    }

}