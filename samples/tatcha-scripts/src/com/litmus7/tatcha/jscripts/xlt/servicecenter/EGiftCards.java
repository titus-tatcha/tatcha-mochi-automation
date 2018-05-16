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
public class EGiftCards extends AbstractWebDriverScriptTestCase
{

	private final static Logger logger = Logger.getLogger(EGiftCards.class);
	private static WebDriver driver = new XltDriver();
//	private static WebDriver driver = BrowserDriver.getChromeWebDriver();
	private static String baseUrl = BrowserDriver.BASE_URL;

    /**
     * Singleton Instance
     */
    private static EGiftCards instance = null; 
    public static EGiftCards getInstance() {
		if(instance == null){
			instance  = new EGiftCards();
		}
		return instance;
	}
    
    /**
     * Constructor.
     */
    private EGiftCards()
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
        // E-GIFT CARDS
        clickAndWait("link=E-GIFT CARDS");
        assertText("//*[@id='ext-gen45']//h1[1]", "E-GIFT CARDS");
        assertText("//html[@id='ext-gen45']/body/main/div[1]/div[2]/div/div[2]/div/div/ul", "*When you purchase an e-gift certificate, the gift certificate code and amount will be emailed to the recipient. You will receive an order confirmation email. You cannot purchase a gift certificate with a gift certificate. If your gift certificate amount is less than your order, you can complete your order with a credit card. You can check your e-gift certificate balance when checking out in the payment section.*");

    }

}