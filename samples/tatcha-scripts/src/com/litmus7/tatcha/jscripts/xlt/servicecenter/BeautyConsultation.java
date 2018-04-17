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
public class BeautyConsultation extends AbstractWebDriverScriptTestCase
{

	private final static Logger logger = Logger.getLogger(BeautyConsultation.class);
	private static WebDriver driver = new XltDriver();
//	private static WebDriver driver = BrowserDriver.getChromeWebDriver();
	private static String baseUrl = BrowserDriver.BASE_URL;

    /**
     * Singleton Instance
     */
    private static BeautyConsultation instance = null; 
    public static BeautyConsultation getInstance() {
		if(instance == null){
			instance  = new BeautyConsultation();
		}
		return instance;
	}
    
    /**
     * Constructor.
     */
    public BeautyConsultation()
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
        // BEAUTY CONSULTATION
        clickAndWait("link=BEAUTY CONSULTATION");
        assertText("//*[@id='ext-gen45']//h1[1]", "BEAUTY CONSULTATION");
        // assertText("//*[@id='ext-gen45']//div[contains(@class,'content-editor-container')][1]","glob: In Japanese, there is a phrase: ichi-go, ichi-e. It means “just this moment, once in a lifetime” and speaks to the preciousness of experiences and interactions in our lives. At Tatcha, we are inspired and humbled by this principle and adopted it as our philosophy for customer service. We treasure every customer and live to surprise and delight you. In every moment, your happiness is our highest priority. The following are promises we make to you as part of our Happiness Guarantee: Free Shipping: We are delighted to offer complimentary shipping for all standard U.S. orders over $25. Three Free Samples: Every order includes three complimentary samples to discover something new to love. Select your samples at checkout. Complimentary Skincare Consultations: Not sure what is best for you? A Tatcha Skincare Specialist is available Monday – Friday, 9 a.m. – 5 p.m. to help you. Simply call (888) 739-2932 ext. 1. Full, Anytime Returns for Domestic Orders on Tatcha.com: We understand skincare is a personal purchase. Sometimes an item is not right for you. Email info@tatcha.com or call (888) 739-2932 ext. 1 for a pre-paid return label to return the items you weren’t happy with and we will issue a full refund to your original form of payment. Regrettably, we are unable to accept empty or nearly empty jars for refund or exchange.*");
        assertText("//html[@id='ext-gen45']/body/main/div[1]/div[2]/div/div[2]/div/div/p[1]", "glob:In Japanese, there is a phrase: ichi-go, ichi-e. It means “just this moment, once in a lifetime” and speaks to the preciousness of experiences and interactions in our lives.");
        assertText("//*[@id='ext-gen45']//p[2]", "At Tatcha, we are inspired and humbled by this principle and adopted it as our philosophy for customer service. We treasure every customer and live to surprise and delight you. In every moment, your happiness is our highest priority.");
        assertText("//*[@id='ext-gen45']//p[3]", "glob:The following are promises we make to you as part of our Happiness Guarantee:");
        assertText("//html[@id='ext-gen45']/body/main/div[1]/div[2]/div/div[2]/div/div/ul/li[1]", "glob:Free Shipping: We are delighted to offer complimentary shipping for all standard U.S. orders over $25.");
        assertText("//html[@id='ext-gen45']/body/main/div[1]/div[2]/div/div[2]/div/div/ul/li[2]", "glob:Three Free Samples: Every order includes three complimentary samples to discover something new to love. Select your samples at checkout.");
        assertText("//html[@id='ext-gen45']/body/main/div[1]/div[2]/div/div[2]/div/div/ul/li[3]", "glob:Complimentary Skincare Consultations: Not sure what is best for you? A Tatcha Skincare Specialist is available Monday – Friday, 9 a.m. – 5 p.m. to help you. Simply call (888) 739-2932 ext. 1.");
        assertText("//html[@id='ext-gen45']/body/main/div[1]/div[2]/div/div[2]/div/div/ul/li[4]", "glob:Full, Anytime Returns for Domestic Orders on Tatcha.com: We understand skincare is a personal purchase. Sometimes an item is not right for you. Email info@tatcha.com or call (888) 739-2932 ext. 1 for a pre-paid return label to return the items you weren’t happy with and we will issue a full refund to your original form of payment. Regrettably, we are unable to accept empty or nearly empty jars for refund or exchange.");

    }

}