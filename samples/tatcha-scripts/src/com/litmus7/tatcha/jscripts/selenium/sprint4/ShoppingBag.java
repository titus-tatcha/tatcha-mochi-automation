package com.litmus7.tatcha.jscripts.selenium.sprint4;

import java.util.List;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.litmus7.tatcha.jscripts.dob.Product;
import com.litmus7.tatcha.jscripts.dob.User;
import com.litmus7.tatcha.jscripts.selenium.sprint3.LoginHelper;

import static org.junit.Assert.assertEquals;


public class ShoppingBag {

    private LoginHelper loginHelper = new LoginHelper();

    public void verifyShoppingBag(WebDriver driver, Properties prop, Properties locator, User user) {
        
        // Assert title
        WebElement titleElement = driver.findElement(By.xpath("//*[@id='ext-gen44']/body/main/div[1]/h1"));
        assertEquals("SHOPPING BAG", titleElement.getText());
        
        // Verify Samples
        verifySamples(driver, prop, locator);
        System.out.println("Samples done");
        
        // Verify Product
        verifyProducts(driver, prop, locator, user);
        System.out.println("Products done");

        // Verify gift wrap
        verifyGiftWrap(driver, prop, locator);
        System.out.println("Giftwrap done");

        // Verify suggestions
        //verifySuggestion(driver, prop, locator);
        
        // Verify summary
        verifySummary(driver, prop, locator, user);
        System.out.println("Summary done");

    }
    
    private void verifySummary(WebDriver driver, Properties prop, Properties locator, User user) {
        
        if(null != user.getProducts()) {
            
            double subtotal = 0;
            String priceString = "";
            // Get the subtotal
            for(Product product : user.getProducts()) {
                priceString = product.getPrice();
                subtotal += getLoginHelper().getPrice(priceString);
            }            
            
            int productInCart = user.getProducts().size();
            // Summary
            assertEquals("Summary", driver.findElement(By.cssSelector("h2.panel-title")).getText());
            if(productInCart > 1) {
                assertEquals(productInCart+" items", driver.findElement(By.cssSelector("div.panel-status")).getText());
            } else {
                assertEquals(productInCart+" item", driver.findElement(By.cssSelector("div.panel-status")).getText());
            }
            assertEquals("SUBTOTAL", driver.findElement(By.xpath("//*[@id='cart-table']/div[2]/div/div[2]/table[1]/tbody/tr[1]/th")).getText());
            assertEquals(getLoginHelper().getPriceString(subtotal), driver.findElement(By.xpath("//*[@id='cart-table']/div[2]/div/div[2]/table[1]/tbody/tr[1]/td")).getText());
            assertEquals("SHIPPING", driver.findElement(By.xpath("//*[@id='cart-table']/div[2]/div/div[2]/table[1]/tbody/tr[2]/th")).getText());
            /*--TBD--*/
            assertEquals("TAX", driver.findElement(By.xpath("//*[@id='cart-table']/div[2]/div/div[2]/table[1]/tbody/tr[3]/th")).getText());
            /*--TBD--*/
            
            // Promotion
            WebElement promoCodeLabelElement = driver.findElement(By.xpath("//*[@id='cart-table']/div[2]/div/div[2]/div/div/label"));
            assertEquals("PROMO CODE", promoCodeLabelElement.getText());
            assertEquals("*Limit one promotional code per order.", driver.findElement(By.cssSelector("span.help-block")).getText());
            
            // Apply valid coupon
            String couponCode = "TATCHA";
            driver.findElement(By.id("dwfrm_cart_couponCode")).clear();
            driver.findElement(By.id("dwfrm_cart_couponCode")).sendKeys(couponCode);
            driver.findElement(By.id("add-coupon")).click();

            
            double discount = 10.00;
            WebElement promocodeValidationMsgElement = null;
            try{
                assertEquals("DISCOUNT", driver.findElement(By.xpath("//*[@id='cart-table']/div[2]/div/div[2]/table[1]/tbody/tr[2]/th")).getText());
                assertEquals(getLoginHelper().getPriceString(discount), driver.findElement(By.xpath("//*[@id='cart-table']/div[2]/div/div[2]/table[1]/tbody/tr[2]/td")).getText());
                promocodeValidationMsgElement = driver.findElement(By.xpath("//*[@id='ext-gen44']/body/main/div[1]/div[1]/div"));
                assertEquals("The promo code has been applied to your order.", promocodeValidationMsgElement.getText());
            }catch(NoSuchElementException ne){
                
            }
            double total = subtotal - discount;
            assertEquals("ESTIMATED TOTAL", driver.findElement(By.xpath("//*[@id='cart-table']/div[2]/div/div[2]/table[2]/tbody/tr/th")).getText());
            assertEquals(getLoginHelper().getPriceString(total), driver.findElement(By.xpath("//*[@id='cart-table']/div[2]/div/div[2]/table[2]/tbody/tr/td")).getText());       
            
            // Remove promotion
            driver.findElement(By.id("remove-coupon")).click();
            total = subtotal;
            assertEquals(getLoginHelper().getPriceString(total), driver.findElement(By.xpath("//*[@id='cart-table']/div[2]/div/div[2]/table[2]/tbody/tr/td")).getText());
            
            // Apply an invalid coupon
            couponCode = "TATCHANEW";
            driver.findElement(By.id("dwfrm_cart_couponCode")).clear();
            driver.findElement(By.id("dwfrm_cart_couponCode")).sendKeys(couponCode);
            driver.findElement(By.id("add-coupon")).click();
            
            // Assert invalid coupon error message
            promocodeValidationMsgElement = driver.findElement(By.xpath("//*[@id='ext-gen44']/body/main/div[1]/div[1]/div"));
            assertEquals("Coupon code \""+couponCode+"\" cannot currently be added to your cart.", promocodeValidationMsgElement.getText());
        }
    }

    private void verifyGiftWrap(WebDriver driver, Properties prop, Properties locator) {
        
        WebDriverWait wait = (WebDriverWait) new WebDriverWait(driver, 5);

        // Assert title
        WebElement titleElement = driver.findElement(By.xpath("//*[@id='bag-gift-block']/h4"));
        assertEquals("TATCHA GIFT WRAPPING", titleElement.getText());
        
        // Verify tool tip
        WebElement toolTipElement = driver.findElement(By.xpath("//*[@id='bag-gift-block']/h4/a"));
        toolTipElement.click();
        WebElement toolTipTitleElement = driver.findElement(By.xpath("//*[@id='giftInfoModal']/div/div/div[1]/h4/strong"));
        assertEquals("GIFT BOXES", toolTipTitleElement.getText());
        WebElement toolTipDescriptionElement = driver.findElement(By.xpath("//*[@id='giftInfoModal']/div/div/div[2]/p[2]"));
        assertEquals(prop.getProperty("shoppingBag.giftWrap.toolTip").toString(), toolTipDescriptionElement.getText());
        WebElement toolTipCloseElement = driver.findElement(By.xpath("//*[@id='giftInfoModal']/div/div/div[1]/button"));
        toolTipCloseElement.click();
        
        // Assert Check box label
        WebElement checkboxLabelElement = driver.findElement(By.xpath("//*[@id='bag-gift-block']/div/div[2]/div[1]/label"));
        assertEquals("Add a gift box for this order. ($10.00)", checkboxLabelElement.getText());
      
        // Verify check box
        WebElement checkboxElement = driver.findElement(By.xpath("//*[@id='giftwrap-toggle']"));
        checkboxElement.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='hasGiftMessage']")));
        
        // Verify Gift message
        WebElement giftMessageButtonElement = driver.findElement(By.xpath("//*[@id='hasGiftMessage']"));
        giftMessageButtonElement.click();
        
        // Assert Gift message modal box
        WebElement giftMessageTitleElement = driver.findElement(By.xpath("//*[@id='giftMessageModal']/div/div/div/h4/strong"));
        assertEquals("ADD A GIFT MESSAGE", giftMessageTitleElement.getText());
        WebElement giftMessageLabelElement = driver.findElement(By.xpath("//*[@id='giftMessageModal']/div/div/form/div[1]/div/label"));
        assertEquals("MESSAGE", giftMessageLabelElement.getText());
        WebElement giftMessageInfoElement = driver.findElement(By.xpath("//*[@id='giftMessageModal']/div/div/form/div[1]/div/div"));
        assertEquals("You have 210 characters left out of 210", giftMessageInfoElement.getText());
        
        // Save gift message
        WebElement giftMessageElement = driver.findElement(By.xpath("//*[@id='textAreaPost']"));
        giftMessageElement.clear();
        giftMessageElement.sendKeys("Thank You.");       
        giftMessageInfoElement = driver.findElement(By.xpath("//*[@id='giftMessageModal']/div/div/form/div[1]/div/div"));
        assertEquals("You have 200 characters left out of 210", giftMessageInfoElement.getText());
        WebElement giftMessageSaveButtonElement = driver.findElement(By.xpath("//*[@id='giftMessageModal']/div/div/form/div[2]/button[2]"));
        giftMessageSaveButtonElement.click();
        
        // Verify gift message
        giftMessageLabelElement = driver.findElement(By.xpath("//*[@id='bag-gift-block']/div/div[2]/div[2]/div[2]/div/span"));
        assertEquals("MESSAGE", giftMessageLabelElement.getText());
        giftMessageElement = driver.findElement(By.xpath("//*[@id='bag-gift-block']/div/div[2]/div[2]/div[2]/div/small"));
        assertEquals("Thank You.", giftMessageElement.getText());

        // Remove gift message
        WebElement giftMessageCloseElement = driver.findElement(By.xpath("//*[@id='bag-gift-block']/div/div[2]/div[2]/div[2]/div/a"));
        giftMessageCloseElement.click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@id='bag-gift-block']/div/div[2]/div[2]/div[2]/div/a")));
        
        // Un-check check box
        checkboxElement = driver.findElement(By.xpath("//*[@id='giftwrap-toggle']"));
        checkboxElement.click();
    }

    private void verifyProducts(WebDriver driver, Properties prop, Properties locator, User user) {
        
        WebDriverWait wait = (WebDriverWait) new WebDriverWait(driver, 5);
        
        if(null != user.getProducts() && !user.getProducts().isEmpty()) {
            int productInCart = user.getProducts().size();
            int index = productInCart;
            for(int i=0; i<productInCart; i++) {
                
                // Assert Product name
                String productName = user.getProducts().get(i).getName().toUpperCase();
                WebElement titleElement = driver.findElement(By.xpath("//*[@id='cart-table']/div[1]/div[4]/div["+(index-i)+"]/div/div[2]/div[1]/div/div/h4/a"));
                assertEquals(productName, titleElement.getText());
                
                // Assert QTY
                int qty = 2;
                WebElement qtyLabelElement = driver.findElement(By.xpath("//*[@id='cart-table']/div[1]/div[4]/div["+(index-i)+"]/div/div[2]/div[2]/div[1]/div[1]/label"));
                assertEquals("QTY", qtyLabelElement.getText());
                WebElement quantityElement = driver.findElement(By.xpath("//*[@id='cart-table']/div[1]/div[4]/div["+(index-i)+"]/div/div[2]/div[2]/div[1]/div[1]/select"));
                Select dropdown = new Select(quantityElement);
                dropdown.selectByVisibleText(Integer.toString(qty));
                
                // Assert Price
                boolean isPromoApplied = false;
                try {
                    WebElement totalPriceElement = driver.findElement(By.xpath("//*[@id='cart-table']/div[1]/div[4]/div["+(index-i)+"]/div/div[2]/div[2]/div[2]/div/span"));
                    String priceString = user.getProducts().get(i).getPrice();
                    if(null != priceString) {
                        double price = getLoginHelper().getPrice(priceString)*qty;
                        // Formatted price string
                        priceString = getLoginHelper().getPriceString(price);
                        assertEquals(priceString, totalPriceElement.getText());
                        user.getProducts().get(i).setPrice(priceString);
                    }
                } catch(NoSuchElementException exe) {
                    isPromoApplied = true;
                }
                
                WebElement wasPriceElement = null;
                if(isPromoApplied) {
                    WebElement priceElement = driver.findElement(By.xpath("//*[@id='cart-table']/div[1]/div[4]/div["+(index-i)+"]/div/div[2]/div[2]/div[2]/div/span[2]/span"));
                    try {
                        wasPriceElement = driver.findElement(By.xpath("//*[@id='cart-table']/div[1]/div[4]/div["+(index-i)+"]/div/div[2]/div[2]/div[2]/div/span[1]/span"));
                        assertEquals(user.getProducts().get(i).getPrice(), wasPriceElement.getText());
                        assert(null != priceElement.getText());
                        user.getProducts().get(i).setPrice(priceElement.getText());
                    } catch(NoSuchElementException exe) {
                        assertEquals(user.getProducts().get(i).getPrice(), priceElement.getText());
                    }
                }
                
                // Assert Specific Promo and complimentary sample msg
                try {
                    WebElement marketingBannerElement = driver.findElement(By.xpath("//*[@id='cart-table']/div[1]/div[4]/div["+(index-i)+"]/div/div[2]/div[2]/div[2]/div/div[2]/div"));
                    assert(null != marketingBannerElement.getText());
                } catch(NoSuchElementException exe) {
                    // If Was Price is greater than present price, promo msg should be present
                    assert(null == wasPriceElement);
                }        
                
                // Check if item is out of stock, if so assert the error message shown
                WebElement outOfStockMessageElement = null;
                WebElement errorElement = null;
                try {
                    outOfStockMessageElement = driver.findElement(By.xpath("//*[@id='cart-table']/div[1]/div[4]/div["+(index-i)+"]/div/div[2]/div[2]/div[2]/div/div[1]/span/span"));
                    errorElement = driver.findElement(By.xpath("//*[@id='ext-gen44']/body/main/div[1]/div[2]"));  
                    assertEquals(prop.getProperty("shoppingBag.invalidItem.errorMsg").toString(), errorElement.getText());
                } catch(NoSuchElementException exe) {
                    
                }
                
                // Assert Auto Delivery
                try{
                    WebElement autoDeliveryLabelElement = driver.findElement(By.xpath("(//*[@id='refill']/div/div/div/label)["+(index-i)+"]"));
                    assertEquals("Auto-Delivery", autoDeliveryLabelElement.getText());
                    
                    // click Tooltip
                    driver.findElement(By.xpath("(//*[@id='refill']/div/div/div/label/a)["+(index-i)+"]")).click();
                    // close tooltip
                    driver.findElement(By.cssSelector("#sorModal > div.modal-dialog > div.modal-content > div.modal-header > button.close")).click();
                    // click checkbox
                    driver.findElement(By.xpath("(//input[@name='dwfrm_smartorderrefill_hasOsfSmartOrderRefill'])["+(index-i)+"]")).click();
                    
                    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//div[@id='refill']/div/div/span/select)["+(index-i)+"]")));
                    WebElement frequencySelectElement = driver.findElement(By.xpath("(//div[@id='refill']/div/div/span/select)["+(index-i)+"]"));
                    Select frequencyDropdown = new Select(frequencySelectElement);
                    frequencyDropdown.selectByVisibleText("Every 60 days");
                    
                    // uncheck checkbox
                    driver.findElement(By.xpath("(//input[@name='dwfrm_smartorderrefill_hasOsfSmartOrderRefill'])["+(index-i)+"]")).click();
                } catch(NoSuchElementException exe) {
                    
                } catch(TimeoutException exe) {
                    
                } catch(ElementNotVisibleException exe) {
                    
                }
            }
        }
    }

    public void verifySamples(WebDriver driver, Properties prop, Properties locator) {
      
        WebDriverWait wait = (WebDriverWait) new WebDriverWait(driver, 10);
        Actions actions = new Actions(driver);

        // Assert title
        WebElement titleElement = driver.findElement(By.xpath("//*[@id='cart-table']/div[1]/div[2]/div[1]/h4"));
        assertEquals("CHOOSE 3 COMPLIMENTARY SAMPLES", titleElement.getText());
        
        // Click ADD SAMPLES on top right 
        WebElement addSampleButtonElement = driver.findElement(By.xpath("//*[@id='bonusModalLink']"));
        assertEquals("ADD SAMPLES", addSampleButtonElement.getText());
        addSampleButtonElement.click();
        // Assert title of modal box
        By modalBoxTitle = By.xpath("//*[@id='bonusModal']/div/div/div[2]/h4/strong");
        wait.until(ExpectedConditions.visibilityOfElementLocated(modalBoxTitle));
        WebElement titleModalElement = driver.findElement(modalBoxTitle);
        assertEquals("ADD 3 COMPLIMENTARY SAMPLES WITH YOUR ORDER", titleModalElement.getText());

        // Close modal box
        WebElement closeSampleModalElement = driver.findElement(By.xpath("//*[@id='bonusModal']/div/div/div[2]/button"));
        closeSampleModalElement.click();
        wait.until(ExpectedConditions.invisibilityOf(titleModalElement));

        // Click ADD A SAMPLE icon
        By addSampleIcon = By.cssSelector("div.vertical-middle > h5.product-name");
        WebElement addSampleIconElement = driver.findElement(addSampleIcon);
        assertEquals("ADD A SAMPLE", addSampleIconElement.getText());
        wait.until(ExpectedConditions.visibilityOf(addSampleIconElement));
        actions.moveToElement(addSampleIconElement).click(addSampleIconElement);
        actions.perform();
        
        // Click ADD for 4 samples (only max 3 samples can be added)
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='bonusModal']/div/div/div[2]/h4/strong")));
        driver.findElement(By.xpath("//div[1]/div/div[2]/label")).click();
        driver.findElement(By.xpath("//div[2]/div/div[2]/label")).click();
        driver.findElement(By.xpath("//div[3]/div/div[2]/label")).click();

        // Add selected samples
        driver.findElement(By.id("submit-sample-items")).click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@id='bonusModal']/div/div/div[2]/h4/strong")));

        // Click edit sample on top right and Remove all the samples
        driver.findElement(By.id("bonusModalLink")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='bonusModal']/div/div/div[2]/h4/strong")));

        driver.findElement(By.xpath("//div[1]/div/div[2]/label")).click();
        driver.findElement(By.xpath("//div[2]/div/div[2]/label")).click();
        driver.findElement(By.xpath("//div[3]/div/div[2]/label")).click();
        driver.findElement(By.id("submit-sample-items")).click();

        // Verify modal cancel button
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@id='bonusModal']/div/div/div[2]/h4/strong")));
        driver.findElement(By.xpath("//*[@id='cart-table']/div[1]/div[2]/div[2]/div/div/div/a")).click();
        driver.findElement(By.cssSelector("button.btn.btn-default")).click();
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
    
}