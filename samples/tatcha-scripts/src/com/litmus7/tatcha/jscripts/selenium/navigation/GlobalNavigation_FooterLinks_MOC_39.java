package com.litmus7.tatcha.jscripts.selenium.navigation;

import java.util.regex.Pattern;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.litmus7.tatcha.jscripts.selenium.Tcheck_Login;
import com.litmus7.tatcha.utils.BrowserDriver;

public class GlobalNavigation_FooterLinks_MOC_39 {
    private WebDriver driver;
    private String baseUrl;
    private boolean acceptNextAlert = true;
    private StringBuffer verificationErrors = new StringBuffer();

    @Before
    public void setUp() throws Exception {
        // driver = new FirefoxDriver();
        driver = BrowserDriver.getChromeWebDriver();
        // baseUrl = "http://demo-na01-tatcha.demandware.net/";
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    @Test
    public void testMOC39TCALL() throws Exception {
        // Tcheck_Login tcheck_Login = new Tcheck_Login();
        // tcheck_Login.checkLogin(driver);
        // driver.get(BrowserDriver.BASE_URL);

        driver.get(BrowserDriver.BASE_URL);
        
        Properties prop = new Properties();
        prop.load(new FileInputStream(getClass().getResource("/tatcha.properties").getFile()));
        String[] socialMedia = prop.get("footer.links.followUs").toString().split("#");
        
        /** Checking 5 Footer Links */
        for (int i = 1; i < 5; i++) {
            String[] footerLinks = prop.get("footer.links." + i).toString().split("#");
            assertWhetherFooterLinksPresent(driver, footerLinks);
            
        }
        assertSocialMedia(driver, socialMedia, prop);
        assertPrivacyAndTerms(driver, prop);
        // Footer account Info and Order Status is automated in MyLogin.java
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

    /**
     * This method only works with Anchor tag elements read from
     * footer.properties file
     * 
     * @param driver1
     * @param arrayLinks
     */
    public void assertWhetherFooterLinksPresent(WebDriver driver, String[] footerLink) {

        String ELEMENT_NAME = null;
        try {
            int totalFooterSubLinks = footerLink.length;
            System.out.println("totalLinks " + totalFooterSubLinks);
            for (int i = 0; i < totalFooterSubLinks; i++) {
                if (!footerLink[i].trim().isEmpty() && footerLink[i].contains("@")) {
                    String[] footerSubLink = footerLink[i].toString().split("@");
                    ELEMENT_NAME = (null != footerSubLink[0]) ? footerSubLink[0].trim() : "";

                    System.out.println("FOOTER SUB-LINK : " + ELEMENT_NAME);

                    String HREF_URL = (null != footerSubLink[1]) ? footerSubLink[1].trim() : "";
                    String NEXT_PAGE_TITLE = null;
                    if (footerSubLink.length > 2) {
                        NEXT_PAGE_TITLE = (null != footerSubLink[2]) ? footerSubLink[2].trim() : "";
                    }
                    if (!ELEMENT_NAME.isEmpty()){

                        try {
                            WebElement webElement = null;
                            WebDriverWait wait = (WebDriverWait) new WebDriverWait(driver, 3);
                            webElement = wait.until(ExpectedConditions.elementToBeClickable((By.linkText(ELEMENT_NAME))));
                            //webElement = wait.until(ExpectedConditions.visibilityOfElementLocated((By.linkText(ELEMENT_NAME))));
                            //webElement = driver.findElement(By.linkText(ELEMENT_NAME));

                            assertEquals(ELEMENT_NAME, webElement.getText());
                            if (null != HREF_URL && !HREF_URL.isEmpty() && !HREF_URL.equalsIgnoreCase("NO_LINK")) {
                                String anchorURL = webElement.getAttribute("href");
                                assertEquals(HREF_URL, anchorURL);
                                //wait.until(ExpectedConditions.elementToBeClickable(By.linkText(ELEMENT_NAME))).click();
                                webElement.click();
                                System.out.println("FOOTER SUB-LINK : " + ELEMENT_NAME + " Clicked");

                                if (null != NEXT_PAGE_TITLE && !NEXT_PAGE_TITLE.isEmpty()
                                        && !NEXT_PAGE_TITLE.equalsIgnoreCase("NO_TITLE")) {
                                    assertEquals(NEXT_PAGE_TITLE, driver.getTitle());
                                }

                                driver.navigate().back();
                                System.out.println("FOOTER SUB-LINK : " + ELEMENT_NAME + " Clicked Back");
                            }
                        } catch (NoSuchElementException ne) {
                            System.err.println(ELEMENT_NAME + " NOT FOUND " + ne.toString());
                        } catch (ElementNotVisibleException nv) {
                            System.err.println(ELEMENT_NAME + " NOT VISIBLE " + nv.toString());
                        } catch (TimeoutException te) {
                            System.err.println(ELEMENT_NAME + " TIMEOUT " + te.toString());
                        } catch (StaleElementReferenceException elementHasDisappeared) {
                            System.err.println(ELEMENT_NAME + " STALE ELE REF " + elementHasDisappeared.toString());
                        } catch (WebDriverException we) {
                            System.err.println(ELEMENT_NAME + " WEBDRIVER ISSUE " + we.toString());
                        }                
                    }
                } else {
                    ELEMENT_NAME = footerLink[0];
                    System.out.println("FOOTER SUB-LINK : " + ELEMENT_NAME);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    @After
    public void tearDown() throws Exception {
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
