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
public class ShippingNReturns extends AbstractWebDriverScriptTestCase
{

	private final static Logger logger = Logger.getLogger(ShippingNReturns.class);
	private static WebDriver driver = new XltDriver();
//	private static WebDriver driver = BrowserDriver.getChromeWebDriver();
	private static String baseUrl = BrowserDriver.BASE_URL;

    /**
     * Singleton Instance
     */
    private static ShippingNReturns instance = null; 
    public static ShippingNReturns getInstance() {
		if(instance == null){
			instance  = new ShippingNReturns();
		}
		return instance;
	}
	
    /**
     * Constructor.
     */
    public ShippingNReturns()
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
        // SHIPPING & RETURNS
        clickAndWait("link=SHIPPING & RETURNS");
        // Shipping Information - U.S. Domestic
        assertText("//html[@id='ext-gen45']/body/main/div[1]/div[2]/div/div[2]/div/div/h2", "Shipping Information");
        assertText("//html[@id='ext-gen45']/body/main/div[1]/div[2]/div/div[2]/div/div/h3", "U.S. DOMESTIC");
        assertText("//html[@id='ext-gen45']/body/main/div[1]/div[2]/div/div[2]/div/div/p", "Standard orders placed before 1 P.M. PST Monday through Friday (excluding holidays) usually ship the same day. Expedited orders placed before 3 P.M. PST Monday through Friday (excluding holidays) will ship the same day. ");
        assertText("//html[@id='ext-gen45']/body/main/div[1]/div[2]/div/div[2]/div/div/div/table/tbody/tr[1]/td[1]/p", "Shipping Method");
        assertText("//html[@id='ext-gen45']/body/main/div[1]/div[2]/div/div[2]/div/div/div/table/tbody/tr[1]/td[2]/p/strong", "Costs");
        assertText("//html[@id='ext-gen45']/body/main/div[1]/div[2]/div/div[2]/div/div/div/table/tbody/tr[1]/td[3]", " Total Delivery Time (including processing time)*");
        assertText("//html[@id='ext-gen45']/body/main/div[1]/div[2]/div/div[2]/div/div/div/table/tbody/tr[3]", " Standard Shipping - orders under $25 $5 3-5 business days*");
        assertText("//html[@id='ext-gen45']/body/main/div[1]/div[2]/div/div[2]/div/div/div/table/tbody/tr[2]/td[2]/p", "Complimentary");
        assertText("//html[@id='ext-gen45']/body/main/div[1]/div[2]/div/div[2]/div/div/div/table/tbody/tr[2]/td[3]/p", "3-5 business days");
        assertText("//html[@id='ext-gen45']/body/main/div[1]/div[2]/div/div[2]/div/div/div/table/tbody/tr[3]/td[1]/p", "Standard Shipping - orders under $25");
        assertText("//html[@id='ext-gen45']/body/main/div[1]/div[2]/div/div[2]/div/div/div/table/tbody/tr[3]/td[2]/p", "$5");
        assertText("//html[@id='ext-gen45']/body/main/div[1]/div[2]/div/div[2]/div/div/div/table/tbody/tr[3]/td[3]/p", "3-5 business days");
        assertText("//html[@id='ext-gen45']/body/main/div[1]/div[2]/div/div[2]/div/div/div/table/tbody/tr[4]/td[1]/p", "Two-day expedited shipping");
        click("//html[@id='ext-gen45']/body/main/div[1]/div[2]/div/div[2]/div/div/div/table/tbody/tr[4]/td[2]");
        assertText("//html[@id='ext-gen45']/body/main/div[1]/div[2]/div/div[2]/div/div/div/table/tbody/tr[4]/td[2]/p", "$10");
        assertText("//html[@id='ext-gen45']/body/main/div[1]/div[2]/div/div[2]/div/div/div/table/tbody/tr[4]/td[3]/p", "2 business days");
        assertText("//html[@id='ext-gen45']/body/main/div[1]/div[2]/div/div[2]/div/div/div/table/tbody/tr[5]/td[1]/p", "Overnight shipping");
        assertText("//html[@id='ext-gen45']/body/main/div[1]/div[2]/div/div[2]/div/div/div/table/tbody/tr[5]/td[2]/p", "$25");
        assertText("//html[@id='ext-gen45']/body/main/div[1]/div[2]/div/div[2]/div/div/div/table/tbody/tr[5]/td[3]/p", "1 business day");
        assertText("//html[@id='ext-gen45']/body/main/div[1]/div[2]/div/div[2]/div/div/div/p[1]", " Carriers");
        assertText("//html[@id='ext-gen45']/body/main/div[1]/div[2]/div/div[2]/div/div/div", "* FedEx signature required upon request FedEx does not consider weekends and holidays as business days PO box address will also be delivered via USPS and does not qualify for expedited shipping*");
        assertText("//html[@id='ext-gen45']/body/main/div[1]/div[2]/div/div[2]/div/div/div/p[4]", "General*");
        assertText("//html[@id='ext-gen45']/body/main/div[1]/div[2]/div/div[2]/div/div/div", "* After you place an order on Tatcha.com, you will receive an Order Confirmation email. Once your order is fulfilled and on its way, we will send you a Shipment Confirmation email with a tracking number. To cancel or modify an order, please contact us within one hour after the order has been placed. After the one-hour window, we cannot guarantee the order will be immediately cancelled.*");
        assertText("//html[@id='ext-gen45']/body/main/div[1]/div[2]/div/div[2]/div/div/div/p[6]", "Deliveries using 3rd party services and forwarding *");
        assertText("//html[@id='ext-gen45']/body/main/div[1]/div[2]/div/div[2]/div/div/div/p[6]", "*We are not responsible for packages being delivered by third party shipping agencies as well as the contents or the delivery once it has arrived to its original destination.");
        assertText("//html[@id='ext-gen45']/body/main/div[1]/div[2]/div/div[2]/div/div/div/p[7]", "If you have any trouble with your order, or need an item urgently, please don’t hesitate to Contact Us.");
        // Shipping Information - International
        assertText("//html[@id='ext-gen45']/body/main/div[1]/div[2]/div/div[2]/div/div/div/h3", "INTERNATIONAL");
        assertText("//html[@id='ext-gen45']/body/main/div[1]/div[2]/div/div[2]/div/div/div/div/table/tbody/tr[1]/td[1]/p/strong", "Country");
        assertText("//html[@id='ext-gen45']/body/main/div[1]/div[2]/div/div[2]/div/div/div/div/table/tbody/tr[1]/td[2]/p", "Shipping Options");
        assertText("//html[@id='ext-gen45']/body/main/div[1]/div[2]/div/div[2]/div/div/div/div/table/tbody/tr[1]/td[3]/p/strong", "Costs");
        assertText("//html[@id='ext-gen45']/body/main/div[1]/div[2]/div/div[2]/div/div/div/div/table/tbody/tr[1]/td[4]", " Total Delivery Time (including processing time)*");
        assertText("//html[@id='ext-gen45']/body/main/div[1]/div[2]/div/div[2]/div/div/div/div/table/tbody/tr[2]/td/p/strong", "Tatcha is pleased to provide complimentary international shipping for any order over $250.");
        assertText("//html[@id='ext-gen45']/body/main/div[1]/div[2]/div/div[2]/div/div/div/div/table/tbody/tr[3]/td[1]/p", "Canada");
        assertText("//html[@id='ext-gen45']/body/main/div[1]/div[2]/div/div[2]/div/div/div/div/table/tbody/tr[3]/td[2]/p", "USPS");
        assertText("//html[@id='ext-gen45']/body/main/div[1]/div[2]/div/div[2]/div/div/div/div/table/tbody/tr[3]/td[3]/p", "$20");
        assertText("//html[@id='ext-gen45']/body/main/div[1]/div[2]/div/div[2]/div/div/div/div/table/tbody/tr[3]/td[4]/p", "7-10 business days");
        assertText("//html[@id='ext-gen45']/body/main/div[1]/div[2]/div/div[2]/div/div/div/div/table/tbody/tr[4]/td[1]/p", "Canada");
        assertText("//html[@id='ext-gen45']/body/main/div[1]/div[2]/div/div[2]/div/div/div/div/table/tbody/tr[4]/td[2]/p", "FedEx");
        assertText("//html[@id='ext-gen45']/body/main/div[1]/div[2]/div/div[2]/div/div/div/div/table/tbody/tr[4]/td[3]/p", "Market rate calculated at checkout");
        assertText("//html[@id='ext-gen45']/body/main/div[1]/div[2]/div/div[2]/div/div/div/div/table/tbody/tr[4]/td[4]/p", "5-7 business days");
        assertText("//html[@id='ext-gen45']/body/main/div[1]/div[2]/div/div[2]/div/div/div/div/table/tbody/tr[5]/td[1]/p", "All other countries");
        assertText("//html[@id='ext-gen45']/body/main/div[1]/div[2]/div/div[2]/div/div/div/div/table/tbody/tr[5]/td[2]/p", "USPS");
        assertText("//html[@id='ext-gen45']/body/main/div[1]/div[2]/div/div[2]/div/div/div/div/table/tbody/tr[5]/td[3]/p", "$35");
        assertText("//html[@id='ext-gen45']/body/main/div[1]/div[2]/div/div[2]/div/div/div/div/table/tbody/tr[5]/td[4]/p", "7-10 business days");
        assertText("//html[@id='ext-gen45']/body/main/div[1]/div[2]/div/div[2]/div/div/div/div/table/tbody/tr[6]/td[1]/p", "All other countries");
        assertText("//html[@id='ext-gen45']/body/main/div[1]/div[2]/div/div[2]/div/div/div/div/table/tbody/tr[6]/td[2]/p", "FedEx");
        assertText("//html[@id='ext-gen45']/body/main/div[1]/div[2]/div/div[2]/div/div/div/div/table/tbody/tr[6]/td[3]/p", "Market rate calculated at checkout");
        assertText("//html[@id='ext-gen45']/body/main/div[1]/div[2]/div/div[2]/div/div/div/div/table/tbody/tr[6]/td[4]/p", "5-7 business days*");
        assertText("//html[@id='ext-gen45']/body/main/div[1]/div[2]/div/div[2]/div/div/div/div/ul[1]/li", "We do not ship to the following countries Spain, North Korea, Turkey, Cosovo, Iran, Iraq, Cuba");
        assertText("//html[@id='ext-gen45']/body/main/div[1]/div[2]/div/div[2]/div/div/div/div/p[1]", "Customs and Exceptions");
        assertText("//html[@id='ext-gen45']/body/main/div[1]/div[2]/div/div[2]/div/div/div/div", "* All international orders placed through FedEx require a signature upon receipt. Most countries can choose FedEx Ecom or Priority. Note that the carrier do not consider weekends and holidays business days. Please be aware that shipping charges do not include international customs, duties, tariff, or broker taxes. All applicable taxes and duties will be paid by the customer and are due to the carrier. Contact the customs office in your destination country for information about any applicable duties or taxes. Packages may held at the local sorting facility until customs are paid. Customs fees may be applied after package is received. Tatcha happily ships to AFO/APO addresses. However, please be aware that orders to these locations cannot be expedited and can only ship via USPS.*");
        // RETURNS - U.S. Domestic
        assertText("//html[@id='ext-gen45']/body/main/div[1]/div[2]/div/div[2]/div/div/div/div/h2", "Returns");
        assertText("//html[@id='ext-gen45']/body/main/div[1]/div[2]/div/div[2]/div/div/div/div/h3[1]", "U.S. DOMESTIC");
        assertText("//html[@id='ext-gen45']/body/main/div[1]/div[2]/div/div[2]/div/div/div/div", "* Your happiness is our highest priority. If you are dissatisfied with your Tatcha.com purchase for any reason, you are welcome to return it for a full refund. We are delighted to offer complimentary returns for all domestic Tatcha.com orders. If you redeemed a complimentary promotional item with purchase, please be aware that this item will need to be returned along with the other returned items. Only products purchased on Tatcha.com may be returned for a refund or exchange. We cannot accept items purchased from another retail store location for an exchange or refund. We also cannot accept empty jars, nearly empty jars, or jars filled with tampered with or other creams for refund or exchange. Shipping cost are not eligible for refunds. We can not accept partial returns or exchanges for bundle sets. You must return the complete set for a refund. If a package is marked as delivered, but it is stolen or you are unable to locate it, we are unable to refund the order amount. We are not responsible for personal damages to an item. If the item has been dropped, stolen, spilled, misused, etc., we cannot refund or accept a return. Simply email info@tatcha.com or call (888) 739-2932 ext.1 for a complimentary pre-paid Fedex return label. We will issue the refund back to the original form of payment within 3-5 business days upon receiving the return shipment and send an email confirmation confirming the details of your refund.*");
        // INTERNATIONAL - International
        assertText("//*[@id='ext-gen45']//h3[2]", "INTERNATIONAL");
        assertText("//html[@id='ext-gen45']/body/main/div[1]/div[2]/div/div[2]/div/div/div/div", "glob:* For returns outside of the United States, please be aware that shipping costs are not covered by Tatcha at this time. Contact us to request an RA #. Shipping fees will not be refunded We are not responsible for refunds if: Packages are refused due to high duties and taxes. Packages can not clear customs because of restrictions. Items that are missing because they are confiscated by Customs.*");

    }

}