package com.litmus7.tatcha.jscripts.xlt.servicecenter;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

import com.litmus7.tatcha.utils.BrowserDriver;
import com.xceptance.xlt.api.engine.scripting.AbstractWebDriverScriptTestCase;
import com.xceptance.xlt.api.webdriver.XltDriver;

/**
 * TODO: Add class description
 */
public class ContactUs extends AbstractWebDriverScriptTestCase
{
	
	private final static Logger logger = Logger.getLogger(ContactUs.class);
	private static WebDriver driver = new XltDriver();
//	private static WebDriver driver = BrowserDriver.getChromeWebDriver();
	private static String baseUrl = BrowserDriver.BASE_URL;
	
    /**
     * Singleton Instance
     */
    private static ContactUs instance = null; 
    public static ContactUs getInstance() {
		if(instance == null){
			instance  = new ContactUs();
		}
		return instance;
	}

    /**
     * Constructor.
     */
    private ContactUs()
    {
        super(driver, baseUrl);
    }





	/**
     * Executes the test.
     *
     * @throws Throwable if anything went wrong
     */
    @Test
    public void test() throws Throwable
    {
        clickAndWait("link=CONTACT US");
        assertText("//*[@id='ext-gen45']//h1[1]", "CONTACT US");
        assertText("//form[@id='RegistrationForm']/div[1]/label/span", "NAME");
        type("id=dwfrm_contactus_fullname", "QA Checker");
        assertText("//form[@id='RegistrationForm']/div[2]/label/span", "E-MAIL");
        type("id=dwfrm_contactus_email", "qa.tatcha@gmail.com");
        assertText("//form[@id='RegistrationForm']/div[3]/label", "SUBJECT");
        assertText("//form[@id='RegistrationForm']/div[4]/label", "WHAT WOULD YOU LIKE TO TELL US?");
        type("id=dwfrm_contactus_comment", "Please reach me as soon as possible");
        click("id=dwfrm_contactus_comment");
        assertText("//*[@id='RegistrationForm']//small", "All personal information is strictly confidential.");
        clickAndWait("id=sendBtn");
        //
        // ~~~ ContactUs-0 ~~~
        //
        startAction("ContactUs_0");

    }

}