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
public class PaymentNBilling extends AbstractWebDriverScriptTestCase
{

	private final static Logger logger = Logger.getLogger(PaymentNBilling.class);
	private static WebDriver driver = new XltDriver();
//	private static WebDriver driver = BrowserDriver.getChromeWebDriver();
	private static String baseUrl = BrowserDriver.BASE_URL;

    /**
     * Singleton Instance
     */
    private static PaymentNBilling instance = null; 
    public static PaymentNBilling getInstance() {
		if(instance == null){
			instance  = new PaymentNBilling();
		}
		return instance;
	}
    
    /**
     * Constructor.
     */
    public PaymentNBilling()
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
        // PAYMENT &  BILLING
        clickAndWait("link=PAYMENT & BILLING");
        assertText("//*[@id='ext-gen45']//h1[1]", "PAYMENT & BILLING");
        assertText("//html[@id='ext-gen45']/body/main/div[1]/div[2]/div/div[2]/div/div/p", "glob:Q: What forms of payment does Tatcha accept? A: We are pleased to accept Visa, Mastercard, American Express and Discover credit cards for Tatcha orders. We also accept Paypal and ApplePay. *");
        assertText("//html[@id='ext-gen45']/body/main/div[1]/div[2]/div/div[2]/div/div/p", "glob:*Q: When will I be billed for my purchase? A: On average, we bill 1-2 business days after a shipping confirmation email is issued*");
        assertText("//html[@id='ext-gen45']/body/main/div[1]/div[2]/div/div[2]/div/div/p", "glob:*Q: What do I do if I have trouble checking out? A: Simply contact us and we will help troubleshoot the issue.*");
        assertText("//html[@id='ext-gen45']/body/main/div[1]/div[2]/div/div[2]/div/div/p", "glob:*Q: What do I do if my order gets declined? A: First, double check to make sure all the shipping and billing information is accurate. If you’re still having trouble, please Contact us");

    }

}