/**
 * 
 */
package com.tatcha.jscripts.bag;


import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.tatcha.jscripts.dao.Product;
import com.tatcha.jscripts.dao.TestCase;
import com.tatcha.jscripts.dao.User;
import com.tatcha.jscripts.helper.TatchaTestHelper;

/**
 * @author Litmus7
 *
 */
public class TestAddToCart {
    
    private TatchaTestHelper testHelper = new TatchaTestHelper();
    private final static Logger logger = Logger.getLogger(TestAddToCart.class);
    private TestCase testCase;

    /**
     * Goto the url of a product's pdp and add it to cart
     * 
     * @param driver2
     * @param prop2
     * @param locator2
     * @param user
     * @param tcList
     */
    public void addSpecificProductToCart(WebDriver driver, Properties prop, Properties locator, User user, List<TestCase> tcList) {
        try {
            logger.info("BEGIN addSpecificProductToCart");
            String FUNCTIONALITY = "Adding a specific product to cart";
            testCase = new TestCase("TC-1.1", "MOC-NIL", FUNCTIONALITY, "FAIL", "");
            
            WebDriverWait wait = (WebDriverWait) new WebDriverWait(driver, 10);

            try {
                By closeButtonLocator = By.xpath("//*[@id='newsletterModal']/div/div/div[1]/button");
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='newsletterModal']/div/div/div[1]/h4")));
                wait.until(ExpectedConditions.elementToBeClickable(closeButtonLocator));
                // Click close button
                driver.findElement(closeButtonLocator).click();
                logger.info("Newsletter is present");
                wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@id='newsletterModal']/div/div/div[1]/h4")));

            } catch(TimeoutException te) {
                logger.info("Newsletter is NOT present");
            }
            List<Product> products = new ArrayList<Product>();
            Product product = new Product();
                    
            wait.until(ExpectedConditions.elementToBeClickable(By.id("add-to-cart")));

            // Get Product name
            String productName = driver.findElement(By.xpath("//*[@id='product-content']/div[2]/h1")).getText();
            product.setName(productName);
            
            // Get Product price
            WebElement priceElement = driver.findElement(By.xpath("//*[@id='product-content']/div[3]/div/div[1]/div/span"));
            product.setPrice(priceElement.getText());
            product.setWasPrice(priceElement.getText());
            products.add(product);   
            
            // Add to cart
            driver.findElement(By.id("add-to-cart")).click();
            logger.info("Item added to cart");
         
            // Set the products associated with user
            user.setProducts(products);
            
            // Click View bag in the modal box
            try {
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='addToBagModal']/div/div/div[1]/h4/strong")));
            } catch(TimeoutException te) {
                driver.findElement(By.id("add-to-cart")).click();
            }
            driver.findElement(By.xpath("//*[@id='addToBagModal']/div/div/div[3]/div/div[2]/a")).click();
            
            // Wait until shopping bag title appears
            wait.until(ExpectedConditions.visibilityOfElementLocated((By.xpath("//*[@id='ext-gen44']/body/main/div[2]/h1"))));
            testCase.setStatus("PASS");
            tcList.add(testCase);
            logger.info("END addSpecificProductToCart");
            
        } catch (NoSuchElementException ne) {
            System.err.println(getClass().getSimpleName() + " : ELEMENT NOT FOUND " + ne.toString());
        } catch (ElementNotVisibleException nv) {
            System.err.println(getClass().getSimpleName() + " : ELEMENT NOT VISIBLE " + nv.toString());
        } catch (TimeoutException te) {
            System.err.println(getClass().getSimpleName() + " : TIMEOUT " + te.toString());
        } catch (StaleElementReferenceException sr) {
            System.err.println(getClass().getSimpleName() + " : STALE ELE REF " + sr.toString());
        } catch (WebDriverException we) {
            System.err.println(getClass().getSimpleName() + " : WEBDRIVER ISSUE " + we.toString());
        }
    }

    /**
     * Add Gift certificate to cart
     * 
     * @param driver
     * @param locator
     * @param user
     * @param tcList
     */
    public void addGiftCertificateToCart(WebDriver driver, Properties locator, User user, List<TestCase> tcList) {
        logger.info("BEGIN addGiftCertificateToCart");
        String FUNCTIONALITY = "Adding a gift certificate to cart";
        testCase = new TestCase("TC-1.2", "MOC-NIL", FUNCTIONALITY, "FAIL", "");
        
        try {
            WebDriverWait wait = (WebDriverWait) new WebDriverWait(driver, 10);
            List<Product> products = new ArrayList<Product>();
            Product product = new Product();
            
            try {
                By closeButtonLocator = By.xpath("//*[@id='newsletterModal']/div/div/div[1]/button");
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='newsletterModal']/div/div/div[1]/h4")));
                wait.until(ExpectedConditions.elementToBeClickable(closeButtonLocator));
                // Click close button
                driver.findElement(closeButtonLocator).click();
                logger.info("Newsletter is present");
                wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@id='newsletterModal']/div/div/div[1]/h4")));

            } catch(TimeoutException te) {
                logger.info("Newsletter is NOT present");
            }
            
            By recipientNameLocator = By.xpath(locator.getProperty("recipient.name.textbox").toString());
            By recipientEmailLocator = By.xpath(locator.getProperty("recipient.email.textbox").toString());
            By senderNameLocator = By.xpath(locator.getProperty("sender.name.textbox").toString());
            By senderEmailLocator = By.xpath(locator.getProperty("sender.email.textbox").toString());
            By messageLocator = By.xpath(locator.getProperty("gift.message.textarea").toString());
            By amountLocator = By.xpath(locator.getProperty("gift.amount.dropdown").toString());
            By inlineBagLocator = By.xpath(locator.getProperty("inline.bag").toString());

            wait.until(ExpectedConditions.visibilityOfElementLocated(recipientNameLocator));

            WebElement recipientNameElement = driver.findElement(recipientNameLocator);
            recipientNameElement.clear();
            recipientNameElement.sendKeys(locator.getProperty("recipient.name.value").toString());
            
            WebElement recipientEmailElement = driver.findElement(recipientEmailLocator);
            recipientEmailElement.clear();
            recipientEmailElement.sendKeys(locator.getProperty("recipient.email.value").toString());
            
            WebElement senderNameElement = driver.findElement(senderNameLocator);
            senderNameElement.clear();
            senderNameElement.sendKeys(locator.getProperty("sender.name.value").toString());
            
            WebElement senderEmailElement = driver.findElement(senderEmailLocator);
            senderEmailElement.clear();
            senderEmailElement.sendKeys(locator.getProperty("sender.email.value").toString());
            
            WebElement messageElement = driver.findElement(messageLocator);
            messageElement.clear();
            messageElement.sendKeys(locator.getProperty("gift.message.value").toString());
            
            WebElement amountElement = driver.findElement(amountLocator);
            Select amount = new Select(amountElement);
            String amountValue = locator.getProperty("gift.amount.value").toString();
            if (amountElement.isEnabled()) {
                amount.selectByVisibleText(amountValue);
            }
            
            WebElement addToBagElement = driver.findElement(By.xpath(locator.getProperty("addToBag.button").toString()));
            wait.until(ExpectedConditions.elementToBeClickable(addToBagElement));
            addToBagElement.click();
            logger.info("Gift certificate added to cart");
            
            product.setName("GIFT CERTIFICATE");
            
            // Get Product price
            product.setPrice(amountValue);
            products.add(product);   
         
            // Set the products associated with user
            user.setProducts(products);     
            
            getTestHelper().logAssertion(getClass().getSimpleName(), "1", driver.findElement(inlineBagLocator).getText());
            driver.findElement(inlineBagLocator).click();
            wait.until(ExpectedConditions.textToBe(By.xpath(locator.getProperty("shoppingBag.title").toString()), "SHOPPING BAG"));
        } catch (NoSuchElementException ne) {
            System.err.println(getClass().getSimpleName() + " : ELEMENT NOT FOUND " + ne.toString());
        } catch (ElementNotVisibleException nv) {
            System.err.println(getClass().getSimpleName() + " : ELEMENT NOT VISIBLE " + nv.toString());
        } catch (TimeoutException te) {
            System.err.println(getClass().getSimpleName() + " : TIMEOUT " + te.toString());
        } catch (StaleElementReferenceException sr) {
            System.err.println(getClass().getSimpleName() + " : STALE ELE REF " + sr.toString());
        } catch (WebDriverException we) {
            System.err.println(getClass().getSimpleName() + " : WEBDRIVER ISSUE " + we.toString());
        }
        testCase.setStatus("PASS");
        tcList.add(testCase);
        logger.info("END addGiftCertificateToCart");
    }
    
    /**
     * Adding a list of products to cart
     * 
     * @param driver
     * @param prop
     * @param locator
     * @param user
     * @param tcList
     */
    public void addProductsToCart(WebDriver driver, Properties prop, Properties locator, User user, List<TestCase> tcList) {
        logger.info("BEGIN addProductsToCart");

        String FUNCTIONALITY = "Adding a list of products to cart";
        testCase = new TestCase("TC-1.3", "MOC-NIL", FUNCTIONALITY, "FAIL", "");
        
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
        getTestHelper().logAssertion(getClass().getSimpleName(), Integer.toString(products.size()), inlineBagElement.getText());
        
        // Go to bag page
        inlineBagElement.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated((By.xpath("//*[@id='ext-gen44']/body/main/div[1]/h1"))));
        testCase.setStatus("PASS");
        tcList.add(testCase);
        logger.info("END addProductsToCart");
    }
    
    /**
     * @return the testHelper
     */
    public TatchaTestHelper getTestHelper() {
        return testHelper;
    }

    /**
     * @param testHelper the testHelper to set
     */
    public void setTestHelper(TatchaTestHelper testHelper) {
        this.testHelper = testHelper;
    }

}
