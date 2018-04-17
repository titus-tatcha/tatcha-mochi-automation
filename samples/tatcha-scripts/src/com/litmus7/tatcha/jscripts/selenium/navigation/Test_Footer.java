package com.litmus7.tatcha.jscripts.selenium.navigation;

import java.util.Properties;

import org.apache.log4j.Logger;
import org.junit.*;
import static org.junit.Assert.*;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.litmus7.tatcha.jscripts.commons.TestMethods;
import com.litmus7.tatcha.utils.BrowserDriver;
import com.xceptance.xlt.api.engine.scripting.AbstractWebDriverScriptTestCase;

public class Test_Footer  extends AbstractWebDriverScriptTestCase {
    
	private final static Logger logger = Logger.getLogger(Test_Footer.class);
	
	private static WebDriver driver;
    private String baseUrl;
    private boolean acceptNextAlert = true;
    private StringBuffer verificationErrors = new StringBuffer();

    @BeforeClass
    public static void initClass() {
    	
    	if (null != System.getProperty("test.type")) {
			if (System.getProperty("test.type").equals("load.xlt")) {
				logger.info("Load Testing : XLT");
				driver = BrowserDriver.getXLTChromeWebDriver();
			} else if (System.getProperty("test.type").equals("browser.chrome")) {
				logger.info("Browser Automation : Google Chrome");
				driver = BrowserDriver.getXLTChromeWebDriver();
			}
		} else {
	        driver = BrowserDriver.getXLTChromeWebDriver();
	        if(null == System.getProperty("work.env")){
	            System.setProperty("work.env", "DEV");
	            System.setProperty("work.module", "FOOTER");
	        }else{
	        	System.setProperty("work.env", "DEV");
	        }
		}

    }

    
	public Test_Footer() {
		super(driver);
		// TODO Auto-generated constructor stub
	}


    @Test
    public void testFooter() throws Exception {
    	
    	try{
    		try{
    			driver.get(BrowserDriver.BASE_URL);
    			pause(5000);
    		}catch(TimeoutException te){
    			verificationErrors.append(te.getMessage());
    		}
	        TestMethods.getInstance().testNewsLetterPopupModal(driver);
	        
	//        Properties prop = new Properties();
	//        prop.load(new FileInputStream(getClass().getResource("/tatcha.properties").getFile()));
	        
	        Properties prop = TestMethods.getInstance().getEnvPropertyFile();
	        String[] socialMedia = prop.get("footer.links.followUs").toString().split("#");
	        
	        /** Checking 5 Footer Links */
			int totalNoOfCategories = 4;
			for (int MENUNUM = 1; MENUNUM <= totalNoOfCategories; MENUNUM++) {
				String[] footerLinks = prop.get("footer.links." + MENUNUM).toString().split("#");
				callFooterMenus(driver, footerLinks, MENUNUM);
			}
	        
	        assertSocialMedia(driver, socialMedia, prop);
	        assertPrivacyAndTerms(driver, prop);
	        // Footer account Info and Order Status is automated in MyLogin.java
    	}catch(Exception e){
    		verificationErrors.append(e.getMessage());
    		logger.error("EXCEPTIONS: "+verificationErrors);
    	}
    }

    private void assertPrivacyAndTerms(WebDriver driver, Properties prop) {
        WebDriverWait wait = (WebDriverWait) new WebDriverWait(driver, 3);

        String[] privacyPolicy = prop.get("footer.links.privacyPolicy").toString().split("@");
        WebElement privacyPolicyElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText(privacyPolicy[0])));
        assertEquals(privacyPolicy[0], privacyPolicyElement.getText());
        assertEquals(privacyPolicy[1], privacyPolicyElement.getAttribute("href"));
        System.out.println("FOOTER PRIVACY POLICY : Asserted");
        
        String[] termsOfUse = prop.get("footer.links.termsOfUse").toString().split("@");
        WebElement termsOfUseElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText(termsOfUse[0])));
        assertEquals(termsOfUse[0], termsOfUseElement.getText());
        assertEquals(termsOfUse[1], termsOfUseElement.getAttribute("href"));
        System.out.println("FOOTER TERMS OF USE : Asserted");
    }

    private void assertSocialMedia(WebDriver driver, String[] socialMedia, Properties prop) {
        String xpathSocialMedia = null;
        String xpathFollowUs = "//*[@id=\"ext-gen44\"]/body/footer/div/div[2]/div[5]/div/div/h5";
        String[] followUs = null;
        WebElement webElement = null;
        WebDriverWait wait = (WebDriverWait) new WebDriverWait(driver, 3);
        
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathFollowUs)));
        webElement = driver.findElement(By.xpath(xpathFollowUs));
        assertEquals(socialMedia[0],webElement.getText());
        try {
            for(int i=1; i<socialMedia.length; i++) {        
                followUs = socialMedia[i].toString().split("@");
                System.out.println("FOOTER FOLLOW US : " + followUs[0]);
                xpathSocialMedia = prop.get("footer.links.xpath." + followUs[0]).toString();
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathSocialMedia)));
                webElement = driver.findElement(By.xpath(xpathSocialMedia));
                assertEquals(followUs[1].trim(), webElement.getAttribute("href"));
                System.out.println("FOOTER FOLLOW US : " + followUs[0]+" Href Asserted");
//                webElement.click();
//                driver.navigate().back();
//                System.out.println("FOOTER FOLLOW US : " + followUs[0]+" Clicked Back");
            } 
        } catch (NoSuchElementException ne) {
            System.err.println(followUs[0] + " NOT FOUND " + ne.toString());
        } catch (ElementNotVisibleException nv) {
            System.err.println(followUs[0] + " NOT VISIBLE " + nv.toString());
        } catch (TimeoutException te) {
            System.err.println(followUs[0] + " TIMEOUT " + te.toString());
        } catch (StaleElementReferenceException elementHasDisappeared) {
            System.err.println(followUs[0] + " STALE ELE REF " + elementHasDisappeared.toString());
        } catch (WebDriverException we) {
            System.err.println(followUs[0] + " WEBDRIVER ISSUE " + we.toString());
        }        
    }
    
    private void callFooterMenus(WebDriver driver2, String[] categories, int MENUNUM) {

		// String SHOP_TAB_ID = "";
		int LINECOUNT = 0;
		int FOOTER_TABLINK_NO = 1;

		if ((MENUNUM == 1) || (MENUNUM == 2)){
			LINECOUNT = 5;
		} else if (MENUNUM == 3) {
			LINECOUNT = 3;
		} else {
			LINECOUNT = 1;
		}

		for (int LISTITEM = 0; LISTITEM < LINECOUNT; LISTITEM++) {

			String[] subcategories = null;
			String ELEMENT_NAME = null;
			String HREF_NAME = null;
			String NEXT_TITLE = null;
			String XPATH_EXPR = "";
			boolean NOT_A_LINK = false;

			if (categories[LISTITEM].contains("@")) {
				subcategories = categories[LISTITEM].split("@");
				ELEMENT_NAME = subcategories[0];
			} else {
				ELEMENT_NAME = categories[LISTITEM];
			}

			if (null != subcategories) {
				if (subcategories.length > 1)
					HREF_NAME = subcategories[1];
				if (subcategories.length > 2)
					NEXT_TITLE = subcategories[2];
			}

			logger.info("PROP ELEMENT_NAME :" + ELEMENT_NAME);
			logger.info("PROP HREF_NAME :" + HREF_NAME);
			logger.info("PROP NEXT_TITLE :" + NEXT_TITLE);

			if ((MENUNUM == 4) || 
					ELEMENT_NAME.equalsIgnoreCase("PURITY PROMISE") || 
					ELEMENT_NAME.equalsIgnoreCase("SERVICE") || 
					ELEMENT_NAME.equalsIgnoreCase("CONSULTATION") ||
					ELEMENT_NAME.equalsIgnoreCase("COMPANY")
					){
				NOT_A_LINK = true;
			}

			if (!NOT_A_LINK) {
				if (MENUNUM == 1) {
			    	XPATH_EXPR = "//*[@id='collapsePromise']/li["+(FOOTER_TABLINK_NO++)+"]/a";				    
				} else if (MENUNUM == 2) {
			    	XPATH_EXPR = "//*[@id='collapseService']/li["+(FOOTER_TABLINK_NO++)+"]/a";
				}else {
			    	XPATH_EXPR = "//*[@id='collapseCompany']/li["+(FOOTER_TABLINK_NO++)+"]/a";
				}
			}
			try{
				TestMethods tmethods = TestMethods.getInstance();
				WebElement webEle = tmethods.getWE(driver2, XPATH_EXPR);
				if (null != webEle) {
					tmethods.assertionChecker(ELEMENT_NAME, webEle.getText());
					tmethods.assertionChecker(HREF_NAME, webEle.getAttribute("href"));
					try {
						Actions actions = new Actions(driver2);
						WebElement menuEle = driver2.findElement(By.xpath(XPATH_EXPR));
						actions.moveToElement(menuEle).perform();
						webEle.click();
					} catch (ElementNotVisibleException ee) {
						logger.error("ELEMENT NOT VISIBLE: " + ELEMENT_NAME + ee.toString());
	
					} catch (NoSuchElementException ne) {
						logger.error("NO SUCH ELEMENT: " + ELEMENT_NAME + ne.toString());
					}
	
					tmethods.assertionChecker(NEXT_TITLE, driver2.getTitle());
					driver2.navigate().back();
				}
			} catch (NoSuchElementException ne) {
                logger.error(ELEMENT_NAME + " NOT FOUND " + ne.toString());
            } catch (ElementNotVisibleException nv) {
                logger.error(ELEMENT_NAME + " NOT VISIBLE " + nv.toString());
            } catch (TimeoutException te) {
                logger.error(ELEMENT_NAME + " TIMEOUT " + te.toString());
            } catch (StaleElementReferenceException elementHasDisappeared) {
                logger.error(ELEMENT_NAME + " STALE ELE REF " + elementHasDisappeared.toString());
            } catch (WebDriverException we) {
                logger.error(ELEMENT_NAME + " WEBDRIVER ISSUE " + we.toString());
            } 
		}
	}
    
    @After
    public void tearDown() {
        driver.quit();
        String verificationErrorString = verificationErrors.toString();
        if (!"".equals(verificationErrorString)) {
            fail(verificationErrorString);
        }
    }

    private boolean isElementPresent(By by) {
        try {
            driver.findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    private boolean isAlertPresent() {
        try {
            driver.switchTo().alert();
            return true;
        } catch (NoAlertPresentException e) {
            return false;
        }
    }

    private String closeAlertAndGetItsText() {
        try {
            Alert alert = driver.switchTo().alert();
            String alertText = alert.getText();
            if (acceptNextAlert) {
                alert.accept();
            } else {
                alert.dismiss();
            }
            return alertText;
        } finally {
            acceptNextAlert = true;
        }
    }
}
