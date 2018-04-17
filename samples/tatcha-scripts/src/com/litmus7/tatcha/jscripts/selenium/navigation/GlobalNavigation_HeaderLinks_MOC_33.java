package com.litmus7.tatcha.jscripts.selenium.navigation;

import java.util.regex.Pattern;
import java.io.FileInputStream;
import java.io.InputStream;
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

public class GlobalNavigation_HeaderLinks_MOC_33 {
    
    private static final String INGREDIENTS = "Natural Ingredients";
    private WebDriver driver;
    private String baseUrl;
    private boolean acceptNextAlert = true;
    private StringBuffer verificationErrors = new StringBuffer();

    @Before
    public void setUp() throws Exception {
        driver = BrowserDriver.getChromeWebDriver();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    @Test
    public void testMOC33TCALL() throws Exception {

        driver.get(BrowserDriver.BASE_URL);
   
        Properties prop = new Properties();
        System.out.println("footer path "+getClass().getResource("/tatcha.properties"));
        prop.load(new FileInputStream(getClass().getResource("/tatcha.properties").getFile()));

        /** Checking Sub-Categories of each Header Category */
        driver.get(BrowserDriver.BASE_URL);
        testPromoBanner(driver, prop);
        testLogo(driver, prop);
        testCategories(driver, prop);
    }

    private void testPromoBanner(WebDriver driver2, Properties prop) {
        String[] promoMessageDetails1 = prop.get("header.promo.message1").toString().split("@");
        String[] promoMessageDetails2 = prop.get("header.promo.message2").toString().split("@");

        try {
            WebDriverWait wait = (WebDriverWait) new WebDriverWait(driver, 3);
            wait.until(ExpectedConditions
                    .visibilityOfElementLocated(By.xpath("//*[@id=\"ext-gen44\"]/body/div/div[1]/div/button")));

            // Closing the promo banner
            WebElement bannerElement = driver.findElement(By.xpath("//*[@id=\"ext-gen44\"]/body/div/div[1]/div/button"));
            bannerElement.click();

            assertPromoMessage(promoMessageDetails1, "//*[@id=\"ext-gen44\"]/body/div/div[2]/div/div/div[1]/div/div[1]/div/a[1]");
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//*[@id=\"ext-gen44\"]/body/div/div/div/div/div[1]/div/div[1]/div/a[2]")));
            assertPromoMessage(promoMessageDetails2, "//*[@id=\"ext-gen44\"]/body/div/div/div/div/div[1]/div/div[1]/div/a[2]");
        } catch (NoSuchElementException ne) {
            System.err.println("BANNER : ELEMENT NOT FOUND " + ne.toString());
        } catch (ElementNotVisibleException nv) {
            System.err.println("BANNER : ELEMENT NOT VISIBLE " + nv.toString());
        } catch (TimeoutException te) {
            System.err.println("BANNER : TIMEOUT " + te.toString());
        } catch (StaleElementReferenceException elementHasDisappeared) {
            System.err.println("BANNER : STALE ELE REF " + elementHasDisappeared.toString());
        } catch (WebDriverException we) {
            System.err.println("BANNER : WEBDRIVER ISSUE " + we.toString());
        }
    }

    private void assertPromoMessage(String[] promoMessageDetails, String xPath) {
        WebElement bannerMessage = driver
                .findElement(By.xpath(xPath));
        System.out.println("NAME : " + promoMessageDetails[0] + "\nURL : " + promoMessageDetails[1] + "\nTITLE : "
                + promoMessageDetails[2]);
        // Test Promo message
        assertEquals((null != promoMessageDetails[0]) ? promoMessageDetails[0].trim() : "", bannerMessage.getText());
        // Test Url
        assertEquals((null != promoMessageDetails[1]) ? promoMessageDetails[1].trim() : "",
                bannerMessage.getAttribute("href"));
        bannerMessage.click();
        // Test landing page Title
        assertEquals((null != promoMessageDetails[2]) ? promoMessageDetails[2].trim() : "", driver.getTitle());

        driver.navigate().back();
        
    }

    private void testLogo(WebDriver driver, Properties properties) {
        String[] logoDetails = properties.get("header.tatcha.logo").toString().split("@");
        driver.navigate()
                .to("http://development-na01-tatcha.demandware.net/s/tatcha/CMS-Pages/Our-Story.html?lang=default");
        
        assertLogo(logoDetails, "//*[@id=\"ext-gen44\"]/body/header/nav/div[1]/div[1]/a/img");
        
        try {
            
        } catch (NoSuchElementException ne) {
            System.err.println("LOGO : ELEMENT NOT FOUND " + ne.toString());
        } catch (ElementNotVisibleException nv) {
            System.err.println("LOGO : ELEMENT NOT VISIBLE " + nv.toString());
        } catch (TimeoutException te) {
            System.err.println("LOGO : TIMEOUT " + te.toString());
        } catch (StaleElementReferenceException elementHasDisappeared) {
            System.err.println("LOGO : STALE ELE REF " + elementHasDisappeared.toString());
        } catch (WebDriverException we) {
            System.err.println("LOGO : WEBDRIVER ISSUE " + we.toString());
        }
        
    }

    private void assertLogo(String[] logoDetails, String string) {
        String logoClass = (null != logoDetails[0]) ? logoDetails[0].trim() : "";
        WebElement webElement = driver
                .findElement(By.xpath("//*[@id=\"ext-gen44\"]/body/header/nav/div[1]/div[1]/a/img"));
        System.out.println("CLASS : " + logoClass + "\nURL : " + logoDetails[1] + "\nTITLE : " + logoDetails[2]);
        if (logoClass.equals(webElement.getAttribute("class"))) {
            // String anchorURL = webElement.getAttribute("href");
            String anchorURL = driver.findElement(By.xpath("//*[@id=\"ext-gen44\"]/body/header/nav/div[1]/div[1]/a"))
                    .getAttribute("href");
            assertEquals((null != logoDetails[1]) ? logoDetails[1].trim() : "", anchorURL);
            webElement.click();
        }
        assertEquals((null != logoDetails[2]) ? logoDetails[2].trim() : "", driver.getTitle());
    }

    private void testCategories(WebDriver driver2, Properties prop) {
        int totalNoOfCategories = 5;
        for (int i = 1; i <= totalNoOfCategories; i++) {
            String[] subCategories = prop.get("header.links." + i).toString().split("#");
            // First element is the Category element itself
            WebElement we = driver.findElement(By.linkText(subCategories[0]));
            Actions action = new Actions(driver);
            action.moveToElement(we).build().perform();
            System.out.println("CASE " + i + " CATEGORY : " + subCategories[0]);
            assertWhetherHeaderLinksPresent(driver, subCategories);
        }

    }

    /**
     * This method only works with Anchor tag elements read from
     * footer.properties file
     * 
     * @param driver1
     * @param arrayLinks
     */
    public void assertWhetherHeaderLinksPresent(WebDriver driver, String[] subCategories) {

        boolean isNavigated = false;
        try {
            int totalSubCategories = subCategories.length;
            System.out.println("totalLinks " + totalSubCategories);
            for (int i = 0; i < totalSubCategories; i++) {
                if (isNavigated) {
                    WebElement element = driver.findElement(By.linkText(subCategories[0]));
                    Actions action = new Actions(driver);
                    action.moveToElement(element).build().perform();
                    isNavigated = false;
                }
                if (!subCategories[i].trim().isEmpty()) {
                    String ELEMENT_NAME = null;
                    if (!subCategories[i].contains("@")) {
                        ELEMENT_NAME = subCategories[i];
                        // System.out.println("if ELEMENT_NAME "+ELEMENT_NAME);
                        System.out.println("SUB-CATEGORY : " + ELEMENT_NAME);

                        // WebElement webElement =
                        // driver1.findElement(By.linkText(ELEMENT_NAME));
                        // WebElement webElement =
                        // driver1.findElement(By.xpath("//*a[contains(text(),'"+ELEMENT_NAME+"')]"));
                        // webElement.click();
                        // driver.navigate().back();

                        // if(ELEMENT_NAME.equalsIgnoreCase("heading")){
                        //
                        // }
                    } else {
                        String[] subCategory = subCategories[i].toString().split("@");
                        ELEMENT_NAME = (null != subCategory[0]) ? subCategory[0].trim() : "";

                        // System.out.println("else ELEMENT_NAME
                        // "+ELEMENT_NAME);
                        System.out.println("SUB-CATEGORY : " + ELEMENT_NAME);

                        String HREF_URL = (null != subCategory[1]) ? subCategory[1].trim() : "";
                        String NEXT_PAGE_TITLE = null;
                        if (subCategory.length > 2) {
                            NEXT_PAGE_TITLE = (null != subCategory[2]) ? subCategory[2].trim() : "";
                        }

                        if (!ELEMENT_NAME.isEmpty()) {
                            try {
                                WebDriverWait wait = (WebDriverWait) new WebDriverWait(driver, 3);
                                wait.until(ExpectedConditions.visibilityOfElementLocated((By.linkText(ELEMENT_NAME))));
                                WebElement webElement = null;
                                try {
                                    webElement = driver.findElement(By.linkText(ELEMENT_NAME));
                                    // System.out.println("webElement Text " +
                                    // webElement.getText());
                                    assertEquals(ELEMENT_NAME, webElement.getText());
                                } catch (TimeoutException te) {
                                    WebElement element = driver.findElement(By.linkText("SHOP"));
                                    Actions action = new Actions(driver);
                                    action.moveToElement(element).build().perform();
                                }

                                // webElement.click();

                                if (null != HREF_URL && !HREF_URL.isEmpty() && !HREF_URL.equalsIgnoreCase("NO_LINK")) {
                                    // String parentHandle =
                                    // driver.getWindowHandle();
                                    // System.out.println(parentHandle);
                                    String anchorURL = webElement.getAttribute("href");
                                    assertEquals(HREF_URL, anchorURL);
                                    webElement.click();
                                    System.out.println("SUB-CATEGORY : " + ELEMENT_NAME + " Clicked");

                                    // for (String winHandle :
                                    // driver.getWindowHandles()) {
                                    // System.out.println(winHandle);
                                    // driver.switchTo().window(winHandle);
                                    // }

                                    if (null != NEXT_PAGE_TITLE && !NEXT_PAGE_TITLE.isEmpty()
                                            && !NEXT_PAGE_TITLE.equalsIgnoreCase("NO_TITLE")) {
                                        assertEquals(NEXT_PAGE_TITLE, driver.getTitle());
                                    }

                                    driver.navigate().back();
                                    System.out.println("SUB-CATEGORY : " + ELEMENT_NAME + " Clicked Back");
                                    isNavigated = true;

                                    // driver.close();
                                    // driver.switchTo().window(parentHandle);
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
                    }
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
