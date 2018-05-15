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
public class Okini extends AbstractWebDriverScriptTestCase
{

	private final static Logger logger = Logger.getLogger(Okini.class);
	private static WebDriver driver = new XltDriver();
//	private static WebDriver driver = BrowserDriver.getChromeWebDriver();
	private static String baseUrl = BrowserDriver.BASE_URL;

    /**
     * Singleton Instance
     */
    private static Okini instance = null; 
    public static Okini getInstance() {
		if(instance == null){
			instance  = new Okini();
		}
		return instance;
	}
    
    /**
     * Constructor.
     */
    public Okini()
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
        // ~~~ Okini-0 ~~~
        //
        startAction("Okini_0");
        selectFrame("name=talkable-offer-iframe");
        assertText("xpath=/html/body[contains(@class,'share') and contains(@class,'offer')][1]/div[contains(@class,'wrap-offer')][1]/div[contains(@class,'container')][1]/div[contains(@class,'post-share') and contains(@class,'js-after') and contains(@class,'hidden')][1]/div[contains(@class,'header')][1]/h2[1]", "OKINI!");
        assertText("xpath=/html/body[contains(@class,'share') and contains(@class,'offer')][1]/div[contains(@class,'wrap-offer')][1]/div[contains(@class,'container')][1]/div[contains(@class,'post-share') and contains(@class,'js-after') and contains(@class,'hidden')][1]/div[contains(@class,'header')][1]/h1[1]", "(THANK YOU!)");
        assertText("xpath=/html/body[contains(@class,'share') and contains(@class,'offer')][1]/div[contains(@class,'wrap-offer')][1]/div[contains(@class,'container')][1]/div[contains(@class,'post-share') and contains(@class,'js-after') and contains(@class,'hidden')][1]/div[contains(@class,'content')][1]/div[contains(@class,'right')][1]/p[contains(@class,'subtitle')][1]", "Thank you for sharing the secrets of exquisite skin with your friends.");
        // selectFrame("name=talkable-offer-iframe");
        assertText("xpath=/html/body[contains(@class,'share') and contains(@class,'offer')][1]/div[contains(@class,'wrap-offer')][1]/div[contains(@class,'container')][1]/div[contains(@class,'post-share') and contains(@class,'js-after') and contains(@class,'hidden')][1]/div[contains(@class,'content')][1]/div[contains(@class,'right')][1]/p[contains(@class,'subtitle') and contains(@class,'is-last')][1]", "Have even more friends you'd like to include? Hooray! Click below.");
        click("link=SHARE WITH MORE FRIENDS");

    }

}