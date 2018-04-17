package com.litmus7.tatcha.jscripts.selenium.sprint4;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.litmus7.tatcha.jscripts.dob.Product;
import com.litmus7.tatcha.jscripts.dob.User;
import com.litmus7.tatcha.jscripts.selenium.sprint3.LoginHelper;
import com.litmus7.tatcha.utils.BrowserDriver;

public class AddToCartGuestUser {

    private WebDriver driver = BrowserDriver.getChromeWebDriver();
    private String baseUrl = BrowserDriver.BASE_URL;
    private boolean acceptNextAlert = true;
    private StringBuffer verificationErrors = new StringBuffer();
    private Properties prop = new Properties();
    private Properties locator = new Properties();
    private LoginHelper loginHelper = new LoginHelper();

    @Before
    public void setUp() throws Exception {
        prop.load(new FileInputStream(getClass().getResource("/tatcha.properties").getFile()));
        locator.load(new FileInputStream(getClass().getResource("/elementLocator.properties").getFile()));

        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.get(baseUrl);
    }

    @Test
    public void testAddToCart() throws Exception {

        ShoppingBag bag = new ShoppingBag();
        User user = new User();
        
        try {
            addToCart(driver, prop, locator, user);
            
            // Verify elements of bag page
            bag.verifyShoppingBag(driver, prop, locator, user);

        } catch (NoSuchElementException ne) {
            System.err.println("LOGIN : ELEMENT NOT FOUND " + ne.toString());
        } catch (ElementNotVisibleException nv) {
            System.err.println("LOGIN : ELEMENT NOT VISIBLE " + nv.toString());
        } catch (TimeoutException te) {
            System.err.println("LOGIN : TIMEOUT " + te.toString());
        } catch (StaleElementReferenceException sr) {
            System.err.println("LOGIN : STALE ELE REF " + sr.toString());
        } catch (WebDriverException we) {
            System.err.println("LOGIN : WEBDRIVER ISSUE " + we.toString());
        }
    }
 
    private void addToCart(WebDriver driver, Properties prop, Properties locator, User user) {
        
        List<Product> products = new ArrayList<Product>();
        Actions action = new Actions(driver);
        WebDriverWait wait = (WebDriverWait) new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOfElementLocated((By.linkText("SHOP"))));

        String productName = null;
        for(int i=1; i<10 ; i++) {
            try {
                productName = prop.getProperty("addToCart.item"+i+".name");
                if(null != productName) {
                    // Go to Product list page
                    WebElement element = driver.findElement(By.linkText("SHOP"));
                    action.moveToElement(element).build().perform();
                    driver.findElement(By.linkText("Shop All")).click();
                    // Click on the product name
                    WebElement addToCartElement = null;
                    try {
                        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(text(),'"+productName+"')]")));
                        WebElement productImageElement = driver.findElement(By.xpath("//a[contains(text(),'"+productName+"')]"));
                        productImageElement.click();
                        
                        addToCartElement = driver.findElement(By.id("add-to-cart"));
                    } catch (NoSuchElementException exe) {
                        continue;
                    } catch (TimeoutException exe) {
                        continue;
                    } catch (WebDriverException exe) {
                        continue;
                    }
                    Product product = new Product();
                    product.setName(productName);
                    
                    WebElement priceElement = driver.findElement(By.xpath("//*[@id='product-content']/div[3]/div/div[1]/div/span"));
                    product.setPrice(priceElement.getText());
                    product.setWasPrice(priceElement.getText());
                    products.add(product);   
                    
                    // Add to cart
                    addToCartElement.click();
                } else {
                    break;
                }
            
            } catch(NoSuchElementException exe) {
                System.err.println("ADD TO CART : " + exe.toString());
            } catch (TimeoutException exe) {
                System.err.println("ADD TO CART : " + exe.toString());
            }
        } 

        // Set the products associated with user(guest)
        user.setProducts(products);
        
        // Get inline bag and assert the item count
        WebElement inlineBagElement = driver.findElement(By.xpath("//*[@id='mini-cart']/div[1]/a/div"));
        assertEquals(Integer.toString(products.size()), inlineBagElement.getText());
        
        // Go to bag page
        inlineBagElement.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated((By.xpath("//*[@id='cart-table']/div[1]/div[2]/div[1]/h4"))));
        
    }

    /**
     * @return the loginHelper
     */
    public LoginHelper getLoginHelper() {
        return loginHelper;
    }

    /**
     * @param loginHelper the loginHelper to set
     */
    public void setLoginHelper(LoginHelper loginHelper) {
        this.loginHelper = loginHelper;
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
