package com.tatcha.jscripts.xlt;
import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

import com.tatcha.jscripts.product.AddItemToCart;
import com.tatcha.utils.BrowserDriver;
import com.xceptance.xlt.api.engine.scripting.AbstractScriptTestCase;
import com.xceptance.xlt.api.engine.scripting.AbstractWebDriverScriptTestCase;
import com.xceptance.xlt.api.webdriver.XltDriver;

//public class TcheckSprint6 extends AbstractScriptTestCase{
public class TcheckSprint6 extends AbstractWebDriverScriptTestCase{
	
	public TcheckSprint6(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	private WebDriver driver;
//	private String baseUrl;
	  	@Test
	    public void test() throws Exception
	    {
		  // calling methods of Selenium Scripts
		  AddItemToCart addItem = new AddItemToCart();
		  addItem.setUp();
		  addItem.testAddItemToCart();
		  addItem.tearDown();
	    }
	  	
//		@Before
//		public void setUp() {
//			// driver = new FirefoxDriver();
//			// baseUrl = "https://development-na01-tatcha.demandware.net/";
//			driver = new XltDriver();
////			baseUrl = BrowserDriver.BASE_URL;
//			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
//		}
		
	    public TcheckSprint6()
	    {
	    	
	    	  super(new XltDriver(), "http://development-na01-tatcha.demandware.net/on/demandware.store/Sites-tatcha-Site");
	    }
		
}
