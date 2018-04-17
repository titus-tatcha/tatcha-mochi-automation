package com.litmus7.tatcha.jscripts.xlt.servicecenter;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

import com.litmus7.tatcha.jscripts.xlt.Sprint3Register;
import com.litmus7.tatcha.utils.BrowserDriver;
import com.xceptance.xlt.api.engine.scripting.AbstractWebDriverScriptTestCase;
import com.xceptance.xlt.api.webdriver.XltDriver;

/**
 * TODO: Add class description
 */
public class AutoDelivery extends AbstractWebDriverScriptTestCase
{

	private final static Logger logger = Logger.getLogger(AutoDelivery.class);
	private static WebDriver driver = new XltDriver();
//	private static WebDriver driver = BrowserDriver.getChromeWebDriver();
	private static String baseUrl = BrowserDriver.BASE_URL;

    /**
     * Singleton Instance
     */
    private static AutoDelivery instance = null; 
    public static AutoDelivery getInstance() {
		if(instance == null){
			instance  = new AutoDelivery();
		}
		return instance;
	}
    
    /**
     * Constructor.
     */
    public AutoDelivery()
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
        //
        // ~~~ AutoDelivery-0 ~~~
        //
        startAction("AutoDelivery_0");
        clickAndWait("link=AUTO DELIVERY");
        assertText("//*[@id='ext-gen45']//h1[1]", "AUTO DELIVERY");
        assertText("link=AUTO-DELIVERY PROGRAM - Learn about Program perks and available products", "AUTO-DELIVERY PROGRAM - Learn about Program perks and available products");
        // Auto Delivery Program Link
        //
        // ~~~ AutoDelivery-1 ~~~
        //
        startAction("AutoDelivery_1");
        clickAndWait("link=AUTO-DELIVERY PROGRAM - Learn about Program perks and available products");
        assertText("//*[@id='primary']//h1", "ABOUT AUTO DELIVERY");
        assertText("//*[@id='primary']//h3[1]", "TIMELESS BEAUTY WITH YOU ALWAYS");
        assertText("//div[@id='primary']/div[3]/div[2]/div/div/p[1]", "Ensure the timeless gifts of the Japanese geisha are part of your everyday beauty ritual. Enroll now and enjoy the convenience of our complimentary auto-delivery service*. To enroll, select the Auto-Delivery option for your desired product when you add it to your shopping bag, select the shipment frequency and then continue browsing until you are ready to check-out.");
        assertText("//div[@id='primary']/div[3]/div[2]/div/div/p[2]/strong", "glob:By signing up, you’ll receive the following program perks:");
        assertText("//div[@id='primary']/div[3]/div[2]/div/div/ul[1]/li[1]", "10% off all auto delivery purchases");
        assertText("//div[@id='primary']/div[3]/div[2]/div/div/ul[1]/li[2]", "Free shipping & returns");
        assertText("//div[@id='primary']/div[3]/div[2]/div/div/ul[1]/li[3]", "Manage the delivery frequency and billing directly from your Tatcha.com account");
        assertText("//div[@id='primary']/div[3]/div[2]/div/div/ul[1]/li[4]", "Cancel anytime");
        assertText("//div[@id='primary']/div[3]/div[2]/div/div/ul[1]/li[5]", "Convenient billing to your credit card");
        assertText("//*[@id='primary']//li[6]", "Access to Team Love—our skincare experts—for any questions you may have");
        assertText("//div[@id='primary']/div[3]/div[2]/div/div/p[3]/small/strong", "glob:Auto Delivery Terms & Conditions:");
        assertText("//div[@id='primary']/div[3]/div[2]/div/div/ul[2]/li[1]/small", "Currently available only in the US.");
        assertText("//div[@id='primary']/div[3]/div[2]/div/div/ul[2]/li[2]/small", "Returns accepted anytime");
        assertText("//div[@id='primary']/div[3]/div[2]/div/div/ul[2]/li[3]/small", "Your subscription will continue until you cancel it");
        assertText("//div[@id='primary']/div[3]/div[2]/div/div/ul[2]/li[4]/small", "A recurring charge in the amount of the product selected for auto-replenishment will be automatically charged to your credit card for each shipment on the schedule you requested when you selected the product.");
        assertText("//div[@id='primary']/div[3]/div[2]/div/div/ul[2]/li[5]/small", "There is no minimum term of the subscription and no minimum purchase obligation.");
        // Auto Delivery Main Page
        //
        // ~~~ AutoDelivery-2 ~~~
        //
        startAction("AutoDelivery_2");
        clickAndWait("link=Auto-Delivery");
        assertText("//*[@id='ext-gen45']//p[2]", "glob:Q: What is the Tatcha Auto-Delivery Program? A: We created the Auto-Delivery Program to ensure you always have your favorites with you. If you choose to opt in, simply select a frequency when placing your order. Learn more about the products we offer for auto-delivery and program perks. Based on your selected frequency, we will charge the card on file and automatically ship your item to you. Enjoy effortless, timeless beauty with the simplicity of Tatcha’s Replenishment Service. Q: Can I change the frequency of my subscription? A: Of course! Simply sign in and go to your account at TATCHA.com. Choose the Auto-Delivery link. You can change your shipping address, billing information, delivery frequency, skip an order, or cancel auto-delivery. If you have any difficulties adjusting this, please Contact Us and we would be delighted to assist you with the change. Q: Can I change my shipping address? A: Of course! Simply sign in and go to your account at TATCHA.com. Choose the Auto-Delivery link. You can change your shipping address, billing information, delivery frequency, skip an order, or cancel auto-delivery. If you have any difficulties adjusting this, please Contact Us and we would be delighted to assist you with the change. Q: When is my next order date? Can I change my next order date? A: All details about your auto-delivery orders are displayed in the Auto-delivery section of your account dashboard. You can change the order date by skipping a delivery or adjusting the frequency. If you have any difficulties adjusting this, please Contact Us and we would be delighted to assist you with the change. Q: Does my promotion code apply to every order? A: Promotion codes, and the discounts or gift with purchase they provide, will only apply to the first order of an auto-delivery purchase. Promotion codes cannot be added to subsequent replenishment shipments. Q: How do I return a shipment from an Auto-Delivery order? A: We are delighted to offer our Happiness Guarantee. Should you wish to return an item at any time, simply call (888) 739-2932 ext. 1 to speak with a Customer Care Representative. You will be provided with a pre-paid return label via email, and we will process your full refund before sending you a confirmation upon receipt of the return package. Q: Can a replenishment shipment be expedited? A: If, for any reason, you need a replenishment order expedited via 2-day or overnight shipping, please contact our Customer Care Team at (888) 739-2932 ext. 1. Q: How do I cancel my subscription? A: To cancel your replenishment subscription, you can do this from the Auto-Delivery section of your Tatcha.com account. View the auto-delivery order details and select ‘Cancel’ to cancel the subscriptions. If you have any difficulties adjusting this, please call us at 888-739-2932 ext.1 or email us at info@tatcha.com and we would be delighted to assist you with the change.");
        assertText("//*[@id='ext-gen45']//strong[1]", "glob:Q: What is the Tatcha Auto-Delivery Program?");
        assertText("//*[@id='ext-gen45']//strong[2]", "glob:Q: Can I change the frequency of my subscription?");
        assertText("//*[@id='ext-gen45']//strong[3]", "glob:Q: Can I change my shipping address?");
        assertText("//*[@id='ext-gen45']//strong[4]", "glob:Q: When is my next order date? Can I change my next order date?");
        assertText("//*[@id='ext-gen45']//strong[5]", "glob:Q: Does my promotion code apply to every order?");
        assertText("//*[@id='ext-gen45']//strong[6]", "glob:Q: How do I return a shipment from an Auto-Delivery order?");
        assertText("//*[@id='ext-gen45']//strong[7]", "glob:Q: Can a replenishment shipment be expedited?");
        assertText("//*[@id='ext-gen45']//strong[8]", "glob:Q: How do I cancel my subscription?");

    }

}