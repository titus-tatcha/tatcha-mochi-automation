package com.litmus7.tatcha.jscripts.xlt;

import org.junit.Test;
import org.openqa.selenium.WebDriver;

import com.litmus7.tatcha.utils.BrowserDriver;
import com.xceptance.xlt.api.engine.scripting.AbstractWebDriverScriptTestCase;
import com.xceptance.xlt.api.webdriver.XltDriver;

/**
 * TODO: Add class description
 */
public class Sprint6Load extends AbstractWebDriverScriptTestCase
{

//	private static WebDriver driver = new XltDriver();
	private static WebDriver driver = BrowserDriver.getChromeWebDriver();
	private static String baseUrl = BrowserDriver.BASE_URL;
	
    /**
     * Constructor.
     */
    public Sprint6Load()
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
        open("/s/tatcha/cart?lang=default");
        //
        // ~~~ Sprint6-0 ~~~
        //
        startAction("Sprint6_0");
        open("/s/tatcha/cart?lang=default");
        //
        // ~~~ Sprint6-1 ~~~
        //
        startAction("Sprint6_1");
        clickAndWait("xpath=id('navigation')/div[contains(@class,'content-asset')][1]/ul[contains(@class,'nav') and contains(@class,'navbar-nav')]/li[contains(@class,'dropdown') and contains(@class,'yamm-fw')][1]/div[contains(@class,'dropdown-menu') and contains(@class,'nav-mobile')][1]/div[contains(@class,'yamm-content') and contains(@class,'container-fluid')]/div[contains(@class,'row')]/div[contains(@class,'col-md-2') and contains(@class,'col-md-offset-1') and contains(@class,'nav-main-cat')][1]/ul[contains(@class,'list-unstyled') and contains(@class,'nav-mobile-section')]/li[1]/a");
        //
        // ~~~ Sprint6-2 ~~~
        //
        startAction("Sprint6_2");
        clickAndWait("link=SOOTHING RICE ENZYME POWDER");
        //
        // ~~~ Sprint6-3 ~~~
        //
        startAction("Sprint6_3");
        click("id=add-to-cart");
        clickAndWait("xpath=id('addToBagModal')/div[contains(@class,'modal-dialog')]/div[contains(@class,'modal-content')]/div[contains(@class,'modal-footer')][1]/div[contains(@class,'row')]/div[contains(@class,'col-sm-5')][2]/a[contains(@class,'btn') and contains(@class,'btn-primary') and contains(@class,'btn-block')]");
        //
        // ~~~ Sprint6-4 ~~~
        //
        startAction("Sprint6_4");
        clickAndWait("//*[@id='dwfrm_login']/button[@name='dwfrm_login_continuelogin' and contains(@class,'btn') and contains(@class,'btn-primary') and contains(@class,'btn-lg') and contains(@class,'btn-block') and contains(@class,'email-check')][1]");
        //
        // ~~~ Sprint6-5 ~~~
        //
        startAction("Sprint6_5");
        type("id=dwfrm_singleshipping_shippingAddress_addressFields_firstName", "QAX1");
        type("id=dwfrm_singleshipping_shippingAddress_addressFields_lastName", "Tatcha1");
        click("id=dwfrm_singleshipping_shippingAddress_addressFields_address1");
        type("id=dwfrm_singleshipping_shippingAddress_addressFields_address1", "Acme Plc, Acme House1");
        click("id=dwfrm_singleshipping_shippingAddress_addressFields_address2");
        type("id=dwfrm_singleshipping_shippingAddress_addressFields_address2", "L7");
        click("id=dwfrm_singleshipping_shippingAddress_addressFields_postal");
        type("id=dwfrm_singleshipping_shippingAddress_addressFields_postal", "12322");
        select("id=dwfrm_singleshipping_shippingAddress_addressFields_states_state", "label=Florida");
        click("id=dwfrm_singleshipping_shippingAddress_addressFields_city");
        type("id=dwfrm_singleshipping_shippingAddress_addressFields_city", "Florida");
        click("id=dwfrm_singleshipping_shippingAddress_addressFields_phone");
        type("id=dwfrm_singleshipping_shippingAddress_addressFields_phone", "333-333-3399");
        clickAndWait("document.forms[0].dwfrm_singleshipping_shippingAddress_save");
        //
        // ~~~ Sprint6Load-0 ~~~
        //
        startAction("Sprint6Load_0");
        click("id=dwfrm_singleshipping_shippingAddress_addressFields_firstName");
        click("id=dwfrm_singleshipping_shippingAddress_addressFields_firstName");

    }

}