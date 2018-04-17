package com.litmus7.tatcha.jscripts.xlt.fullflow;

import static org.junit.Assert.fail;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.litmus7.tatcha.utils.BrowserDriver;
import com.xceptance.xlt.api.engine.scripting.AbstractWebDriverScriptTestCase;
import com.xceptance.xlt.api.webdriver.XltDriver;

/**
 * TODO: Add class description
 */
public class GuestFlow extends AbstractWebDriverScriptTestCase
{

	private final static Logger logger = Logger.getLogger(GuestFlow.class);
	
//	private static WebDriver driver = BrowserDriver.getChromeWebDriver();
//	private static WebDriver driver = new XltDriver();
	
	private static WebDriver driver = null;
	private static String baseurl = System.getProperty("base.url");
	
	static{
		logger.info("sys property - "+System.getProperty("test.type"));
		if(null != System.getProperty("test.type")){
			if(System.getProperty("test.type").equals("load.xlt")){
				logger.info("Load Testing : XLT");
				driver = new XltDriver();
			}else if(System.getProperty("test.type").equals("browser.chrome")){
				logger.info("Browser Automation : Google Chrome");
				driver = BrowserDriver.getChromeWebDriver();
			}
		}else{
			logger.info("System Property : NULL");
		}
	}

    /**
     * Singleton Instance
     */
	
//    private static GuestFlow instance = null; 
//    public static GuestFlow getInstance() {
//		if(instance == null){
//			instance  = new GuestFlow();
//		}
//		return instance;
//	}
    
    /**
     * Constructor.
     */
    public GuestFlow()
    {
//        super(driver, BrowserDriver.BASE_URL);
        super(driver);
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
        startAction("Starting");
        logger.info("Starting "+getClass().getName());
        logger.info("BASE URL : "+baseurl);
        logger.info("WEB DRIVER : "+driver.getTitle());
        open(baseurl);
//        open("/s/tatcha/home?lang=default");
//        open(TATCHA_STAGE_URL);
        //
        // ~~~ GuestFlow-0 ~~~
        //
        startAction("Shop ALL");
        clickAndWait("=id('navigation')/div[contains(@class,'content-asset')][1]/ul[contains(@class,'nav') and contains(@class,'navbar-nav')]/li[contains(@class,'dropdown') and contains(@class,'yamm-fw')][1]/div[contains(@class,'dropdown-menu') and contains(@class,'nav-mobile')][1]/div[contains(@class,'yamm-content') and contains(@class,'container-fluid')]/div[contains(@class,'row')]/div[contains(@class,'col-md-2') and contains(@class,'col-md-offset-1') and contains(@class,'nav-main-cat')][1]/ul[contains(@class,'list-unstyled') and contains(@class,'nav-mobile-section')]/li[1]/a");
        //
        // ~~~ GuestFlow-1 ~~~
        //
        startAction("Select Product");
//        clickAndWait("//*[@id='d437624aa8206c64cd1f962869']//img[contains(@class,'img-responsive') and contains(@class,'product-img')]");


        clickAndWait("//img[contains(@class,'img-responsive') and contains(@class,'product-img')]");
      

        //
        // ~~~ GuestFlow-2 ~~~
        //
        startAction("Add to Cart");
        boolean YES = false;
        try{
        		click("//*[@id='add-to-cart']");
		    	YES = true;
		    }catch(NoSuchElementException ne){
		    	logger.info("Product Not Available for Add To Cart");
		//    	assert("contains(@class,'product-cta') and")
		//      <div class="product-cta"><a> </a>
		//      contains(@class,'product-cta') and 
		//      Out of Stock
		//      Add to Bag
		//      Coming Soon
		    }
		    if(YES){
		    	
		    	 clickAndWait("//div[@id='addToBagModal']/div/div/div[3]/div/div[2]/a");
		         //
		         // ~~~ GuestFlow-3 ~~~
		         //
		         startAction("Click Checkout");
//		         clickAndWait("document.dwfrm_cart.dwfrm_cart_checkoutCart");
		         pause(1000);
		         try{
		        		        	 
		        	 WebElement checkoutElement = driver.findElement(By.name("dwfrm_cart_checkoutCart"));
		        	 WebElement cartTableElement = driver.findElement(By.id("cart-table"));
		        	 WebElement cartSummaryElement = driver.findElement(By.id("cart-summary"));
		        	 Actions actions = new Actions(driver);
		        	 actions.moveToElement(cartTableElement);
//		        	 actions.perform();
		        	 actions.moveToElement(cartSummaryElement);
//		        	 actions.perform();
		        	 actions.moveToElement(checkoutElement).click();
		        	 actions.perform();

		         }catch(NoSuchElementException ne){
		        	 
		         	logger.info("Product is Missing");
		         }

		         //
		         // ~~~ GuestFlow-4 ~~~
		         //
		         startAction("Checkout Login");
		         type("id=dwfrm_login_username", "qat.tatcha@gmail.com");
		         clickAndWait("document.forms[0].dwfrm_login_continuelogin");
		         //
		         // ~~~ GuestFlow-5 ~~~
		         //
		         startAction("Secure Checkout - SHIPPING");
//		         click("id=dwfrm_singleshipping_shippingAddress_addressFields_firstName");
		         type("id=dwfrm_singleshipping_shippingAddress_addressFields_firstName", "QAT");
		         type("id=dwfrm_singleshipping_shippingAddress_addressFields_lastName", "Tatcha");
		         click("id=dwfrm_singleshipping_shippingAddress_addressFields_address1");
		         type("id=dwfrm_singleshipping_shippingAddress_addressFields_address1", "L7 Systems Pvt ltd");
		         type("id=dwfrm_singleshipping_shippingAddress_addressFields_address2", "Bernad St #123");
		         type("id=dwfrm_singleshipping_shippingAddress_addressFields_postal", "85001");
		         type("id=dwfrm_singleshipping_shippingAddress_addressFields_states_state", "Arizona");	 
		         type("id=dwfrm_singleshipping_shippingAddress_addressFields_city", "Phoenix");     
		         type("id=dwfrm_singleshipping_shippingAddress_addressFields_phone", "3344223344");
		       
		         //*[@id="dwfrm_singleshipping_shippingAddress"]/div/div[3]/button
		         
		         WebElement continueBtnFormEle = driver.findElement(By.id("dwfrm_singleshipping_shippingAddress"));
		         WebElement continueBtnPanelDivEle = driver.findElement(By.className("panel-checkout"));
		         WebElement continueBtnFooterDivEle = driver.findElement(By.className("panel-footer"));
//		         WebElement continueBtnEle = driver.findElement(By.xpath("button[contains(@type,'submit')]/span"));
		         WebElement continueBtnEle = driver.findElement(By.xpath("/button"));
		       //*[@id="dwfrm_singleshipping_shippingAddress"]/div/div[3]/button/span
	        	 Actions actions = new Actions(driver);
	        	 actions.moveToElement(continueBtnFormEle);
	        	 actions.perform();
	        	 actions.moveToElement(continueBtnPanelDivEle);
	        	 actions.perform();
	        	 actions.moveToElement(continueBtnFooterDivEle);
	        	 actions.perform();
	         	 actions.moveToElement(continueBtnEle).click();
	         	 actions.perform();
	        	 
//		         div class col-md-7
//		         form id dwfrm_singleshipping_shippingAddress
//		         div panel-checkout
//		         div class panel-footer
//			         	type submit
//			         	value Continue to Billing
			         	
		         clickAndWait("document.forms[0].elements[17]");
		         //
		         // ~~~ GuestFlow-6 ~~~
		         //
		         startAction("Secure Checkout - PAYMENT");
		         click("id=braintreeCardOwner");
		         type("id=braintreeCardOwner", "QAT Namer");
		         selectFrame("id=braintree-hosted-field-number");
		         type("id=credit-card-number", "4111 1111 1111 1111");
		         selectFrame("relative=top");
		         selectFrame("id=braintree-hosted-field-expirationDate");
		         type("id=expiration", "12 / 33");
		         selectFrame("relative=top");
		         selectFrame("id=braintree-hosted-field-cvv");
		         type("id=cvv", "232");
		         selectFrame("relative=top");
		         clickAndWait("id=save-payment");
		         // 
		         // ~~~ GuestFlow-7 ~~~
		         //
		         startAction("Secure Checkout - REVIEW");
		         clickAndWait("document.forms[0].submit");
		         assertText("//*[@id='main']//h1[1]", "THANK YOU FOR YOUR ORDER");
		    }
      
    }
    
	private StringBuffer verificationErrors = new StringBuffer();
	
	@After
	public void tearDown() {
		driver.quit();
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			fail(verificationErrorString);
		}
		logger.info("Testing OVer");
	}

}