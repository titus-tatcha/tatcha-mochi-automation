package com.tatcha.jscripts.xlt.servicecenter;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

import com.tatcha.utils.BrowserDriver;
import com.xceptance.xlt.api.engine.scripting.AbstractWebDriverScriptTestCase;
import com.xceptance.xlt.api.webdriver.XltDriver;

/**
 * TODO: Add class description
 */
public class ThankYou extends AbstractWebDriverScriptTestCase
{

	private final static Logger logger = Logger.getLogger(ThankYou.class);
	private static WebDriver driver = new XltDriver();
//	private static WebDriver driver = BrowserDriver.getChromeWebDriver();
	private static String baseUrl = BrowserDriver.BASE_URL;

    /**
     * Singleton Instance
     */
    private static ThankYou instance = null; 
    public static ThankYou getInstance() {
		if(instance == null){
			instance  = new ThankYou();
		}
		return instance;
	}
    
    /**
     * Constructor.
     */
    public ThankYou()
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
        //
        // ~~~ OpenStartPage ~~~
        //
        startAction("OpenStartPage");
        assertText("//*[@id='main']//h1[1]", "THANK YOU FOR YOUR ORDER");
        assertElementPresent("//*[@id='main']//p[contains(@class,'lead')][1]");
        assertVisible("//*[@id='main']//p[contains(@class,'lead')][1]");
        assertText("//*[@id='main']//p[contains(@class,'lead')][1]", "Hello, QAT!! Your order number is 00002212.");
        assertText("//*[@id='main']//p[2]", "We've emailed your receipt to the address you provided. Once your order is en route, you'll receive a shipping confirmation. We hope you enjoy!");
        assertText("//div[@id='main']/main/div[2]/div/div[1]/div[1]/h4", "ORDERS & RETURNS");
        assertText("link=Cancellations", "Cancellations");
        assertText("link=Refunds", "Refunds");
        assertText("link=Shipping", "Shipping");
        assertText("link=FAQs", "FAQs");
        assertText("//div[@id='main']/main/div[2]/div/div[1]/div[2]/h4", "CONTACT US");
        assertText("//*[@id='main']//em[1]", "M-F, 9am - 5pm Pacific");
        assertText("//*[@id='main']//a[contains(@class,'livechat-open')]", "Chat");
        assertText("//div[@id='main']/main/div[2]/div/div[1]/div[2]/ul/li[4]/a", "Email");
        assertElementPresent("//*[@id='main']//img[contains(@class,'img-responsive')]");
        assertText("//*[@id='main']//img[contains(@class,'img-responsive')]", "");
        
       
                ContactUs.getInstance().test();
    }

}