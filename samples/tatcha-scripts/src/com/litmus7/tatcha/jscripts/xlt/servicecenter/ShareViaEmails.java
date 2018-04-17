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
public class ShareViaEmails extends AbstractWebDriverScriptTestCase
{

	private final static Logger logger = Logger.getLogger(ShareViaEmails.class);
	private static WebDriver driver = new XltDriver();
//	private static WebDriver driver = BrowserDriver.getChromeWebDriver();
	private static String baseUrl = BrowserDriver.BASE_URL;

    /**
     * Singleton Instance
     */
    private static ShareViaEmails instance = null; 
    public static ShareViaEmails getInstance() {
		if(instance == null){
			instance  = new ShareViaEmails();
		}
		return instance;
	}
	
    /**
     * Constructor.
     */
    public ShareViaEmails()
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
        // ~~~ ShareViaEmails-0 ~~~
        //
        startAction("ShareViaEmails_0");
        selectFrame("name=talkable-offer-iframe");
        assertText("xpath=/html/body[contains(@class,'share') and contains(@class,'offer')][1]/div[contains(@class,'wrap-offer')][1]/div[contains(@class,'container')][1]/div[contains(@class,'before-sharing') and contains(@class,'js-before')][1]/div[contains(@class,'header')][1]/h1[contains(@class,'is-top')][1]", "THANK YOU FOR YOUR PURCHASE");
        assertText("xpath=/html/body[contains(@class,'share') and contains(@class,'offer')][1]/div[contains(@class,'wrap-offer')][1]/div[contains(@class,'container')][1]/div[contains(@class,'before-sharing') and contains(@class,'js-before')][1]/div[contains(@class,'header')][1]/h2[1]", "SHARE THE TATCHA SECRET");
        assertText("xpath=/html/body[contains(@class,'share') and contains(@class,'offer')][1]/div[contains(@class,'wrap-offer')][1]/div[contains(@class,'container')][1]/div[contains(@class,'before-sharing') and contains(@class,'js-before')][1]/div[contains(@class,'content')][1]/div[contains(@class,'left')][1]/div[contains(@class,'description')][1]/p[1]", "Introducing your friends to Tatcha only takes a moment, but the gratitude will last a lifetime. When you do, your friend will receive $25 off a $100 purchase on Tatcha.com.");
        // assertText("xpath=/html/body[contains(@class,'share') and contains(@class,'offer')][1]/div[contains(@class,'wrap-offer')][1]/div[contains(@class,'container')][1]/div[contains(@class,'before-sharing') and contains(@class,'js-before')][1]/div[contains(@class,'content')][1]/div[contains(@class,'left')][1]/div[contains(@class,'description')][1]/p[contains(@class,'is-last')][1]","*Your friend's offer expires on Mar 22, 2018 and is limited to one per person. This offer cannot be combined with any other offers and excludes limited edition items, curated gifts, and gift cards. This gift is valid only if your friend is making a first-time order on Tatcha.com");
        assertText("xpath=/html/body[contains(@class,'share') and contains(@class,'offer')][1]/div[contains(@class,'wrap-offer')][1]/div[contains(@class,'container')][1]/div[contains(@class,'before-sharing') and contains(@class,'js-before')][1]/div[contains(@class,'content')][1]/div[contains(@class,'right')][1]/h3[contains(@class,'is-big')][1]", "$25 OFF A $100 PURCHASE FOR YOUR FRIENDS");
        assertText("xpath=/html/body[contains(@class,'share') and contains(@class,'offer')][1]/div[contains(@class,'wrap-offer')][1]/div[contains(@class,'container')][1]/div[contains(@class,'before-sharing') and contains(@class,'js-before')][1]/div[contains(@class,'content')][1]/div[contains(@class,'right')][1]/p[contains(@class,'description') and contains(@class,'is-name')][1]", "Enter your name and your friends’ first names and email addresses below.");
        assertText("xpath=/html/body[contains(@class,'share') and contains(@class,'offer')][1]/div[contains(@class,'wrap-offer')][1]/div[contains(@class,'container')][1]/div[contains(@class,'before-sharing') and contains(@class,'js-before')][1]/div[contains(@class,'content')][1]/div[contains(@class,'right')][1]/div[contains(@class,'js-form-row') and contains(@class,'name-row')][1]/div[contains(@class,'field-title')][1]", "YOUR FIRST NAME");
        // assertText("//input[@type='text' and @value='QAT']","QAT");
        assertText("xpath=/html/body[contains(@class,'share') and contains(@class,'offer')][1]/div[contains(@class,'wrap-offer')][1]/div[contains(@class,'container')][1]/div[contains(@class,'before-sharing') and contains(@class,'js-before')][1]/div[contains(@class,'content')][1]/div[contains(@class,'right')][1]/form[contains(@class,'js-share-via-email-form')][1]/div[contains(@class,'field-title')][1]", "YOUR FRIENDS");
        type("document.forms[0].elements[1]", "Titus");
        type("document.forms[0].elements[2]", "titus@litmus7.com");
        type("document.forms[0].elements[3]", "Joji");
        type("document.forms[0].elements[4]", "joji.thomas@litmus7.com");
        type("document.forms[0].elements[5]", "Neetu");
        type("document.forms[0].elements[6]", "neetu@litmus7.com");
        type("document.forms[0].elements[7]", "Michelle");
        type("document.forms[0].elements[8]", "michelle@litmus7.com");
        type("document.forms[0].elements[9]", "Reshma");
        type("document.forms[0].elements[10]", "reshma@litmus7.com");
        // assertText("document.forms[0].share_message","");
        type("document.forms[0].share_message", "Best Wishes from QA Tatcha");
        // assertText("xpath=/html/body[contains(@class,'share') and contains(@class,'offer')][1]/div[contains(@class,'wrap-offer')][1]/div[contains(@class,'container')][1]/div[contains(@class,'before-sharing') and contains(@class,'js-before')][1]/div[contains(@class,'content')][1]/div[contains(@class,'right')][1]/form[contains(@class,'js-share-via-email-form')][1]/div[contains(@class,'js-form-row') and contains(@class,'form-row')][3]/div[contains(@class,'js-form-inline-validation') and contains(@class,'inline-validation') and contains(@class,'hidden')][1]","This email address is not eligible for this offer.");
        // assertText("xpath=/html/body[contains(@class,'share') and contains(@class,'offer')][1]/div[contains(@class,'wrap-offer')][1]/div[contains(@class,'container')][1]/div[contains(@class,'before-sharing') and contains(@class,'js-before')][1]/div[contains(@class,'content')][1]/div[contains(@class,'right')][1]/div[contains(@class,'js-response-notice') and contains(@class,'notice') and contains(@class,'hidden') and contains(@class,'is-error')][1]","There was an issue with one or more of your submissions, please see errors");
        // selectFrame("name=talkable-offer-iframe");
        // assertText("xpath=/html/body[contains(@class,'share') and contains(@class,'offer')][1]/div[contains(@class,'wrap-offer')][1]/div[contains(@class,'container')][1]/div[contains(@class,'before-sharing') and contains(@class,'js-before')][1]/div[contains(@class,'content')][1]/div[contains(@class,'right')][1]/form[contains(@class,'js-share-via-email-form')][1]/div[contains(@class,'js-form-row') and contains(@class,'form-row')][3]/div[contains(@class,'js-form-inline-validation') and contains(@class,'inline-validation') and contains(@class,'hidden')][1]","This email address is not eligible for this offer.");
        // selectFrame("name=talkable-offer-iframe");
        type("document.forms[0].elements[6]", "neetu1@litmus7.com");
        click("xpath=/html/body[contains(@class,'share') and contains(@class,'offer')][1]/div[contains(@class,'wrap-offer')][1]/div[contains(@class,'container')][1]/div[contains(@class,'close-button') and contains(@class,'js-offer-close')][1]");
        // selectFrame("name=talkable-offer-iframe");
    }

}