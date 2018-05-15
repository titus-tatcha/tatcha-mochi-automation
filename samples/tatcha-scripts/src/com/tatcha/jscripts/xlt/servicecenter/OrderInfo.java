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
public class OrderInfo extends AbstractWebDriverScriptTestCase
{

	private final static Logger logger = Logger.getLogger(OrderInfo.class);
	private static WebDriver driver = new XltDriver();
//	private static WebDriver driver = BrowserDriver.getChromeWebDriver();
	private static String baseUrl = BrowserDriver.BASE_URL;

    /**
     * Singleton Instance
     */
    private static OrderInfo instance = null; 
    public static OrderInfo getInstance() {
		if(instance == null){
			instance  = new OrderInfo();
		}
		return instance;
	}
    
    /**
     * Constructor.
     */
    public OrderInfo()
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
        open("/s/tatcha/faq-order-info.html?lang=default");
        assertText("//*[@id='ext-gen45']//h1[1]", "ORDER INFO");
        assertText("//html[@id='ext-gen45']/body/main/div[1]/div[2]/div/div[2]/div/div/p[1]/u/strong", "PLACING AN ORDER");
        // assertText("//html[@id='ext-gen45']/body/main/div[1]/div[2]/div/div[2]/div/div/p[2]/strong","glob:Q: What forms of payment does Tatcha accept?");
        assertText("//*[@id='ext-gen45']//p[2]", "glob:Q: What forms of payment does Tatcha accept? A: We are pleased to accept Visa, Mastercard, American Express and Discover credit cards for Tatcha orders. We also accept Paypal and ApplePay.");
        assertText("//*[@id='ext-gen45']//p[3]", "glob:Q: How do I know if my order was placed successfully? A: After you place an order on Tatcha.com, you will receive an email notification to the email address you provided while placing the order, confirming that we received your order.");
        assertText("//*[@id='ext-gen45']//p[4]", "A Client Care Specialist reviews and handles every order. The Order Confirmation email you receive does not confirm your order is being fulfilled and shipped. Once your order is fulfilled and on it’s way, we will send you a Shipment Confirmation email to confirm. If we cannot fulfill your order, a Client Care Specialist will send a follow-up email and the purchase amount will be returned to your method of payment.");
        assertText("//*[@id='ext-gen45']//p[5]", "glob:Q: Can I cancel or modify an order once it has been placed? A: To cancel or modify an order, please contact us within one hour after the order has been placed. We will do our best to accommodate your request depending on volume.");
        assertText("//*[@id='ext-gen45']//p[6]", "glob:Please note: Items on our website are not available to purchase for commercial and/or resale purposes. Please see our Terms & Conditions for additional details.");
        assertText("//*[@id='ext-gen45']//p[7]", "glob:Q: Maximum Purchase Quantity A: We want everyone to enjoy the gift of Tatcha. To make this possible, each order from Tatcha.com can have no more than 5 units of any one item. Certain items and other limited edition sets may be restricted to only one or two units. If you have any questions, please do not hesitate to reach out. We would be delighted to be of service and can be reached at 888-739-2932 ext. 1.");
        assertText("//*[@id='ext-gen45']//p[8]", "glob:Q: If a product goes out of stock during a promotion, can I apply the promotion when the product is back in stock? A: Sorry, we cannot offer gifts or discounts after the promotion has expired.");

    }

}