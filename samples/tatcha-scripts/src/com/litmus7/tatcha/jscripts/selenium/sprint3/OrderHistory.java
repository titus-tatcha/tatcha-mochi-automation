package com.litmus7.tatcha.jscripts.selenium.sprint3;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class OrderHistory {

    
    private LoginHelper loginHelper = new LoginHelper();

    /**
     * Handles order history
     * 
     * @param driver
     * @param prop
     * @param locator
     * @throws Exception
     */
    public void verifyOrderHistory(WebDriver driver, Properties prop, Properties locator) throws Exception {
        Actions actions = new Actions(driver);

        if (getLoginHelper().isLoggedIn(driver)) {
            driver.findElement(By.xpath(locator.getProperty("account.order").toString())).click();
            
            // Assert Title
            WebElement orderHistoryTitleElement = driver.findElement(By.cssSelector("h1.text-center"));
            assertEquals(prop.getProperty("myaccount.item2.title").toString().toUpperCase(), orderHistoryTitleElement.getText());

            List<WebElement> orderHistoryElement = driver.findElements(By.className("panel-title"));
            int noOfOrders = orderHistoryElement.size();
                    
            for(int i=1; i<=noOfOrders; i++) {
                // Get the Order Id
                WebElement orderHistoryIdElement = driver.findElement(By.xpath("//*[@id='dwfrm_orders']/ul/li["+i+"]/div/div[1]/h5/a"));
                String orderId = orderHistoryIdElement.getText();

                // Assert Items
                WebElement orderHistoryItemsLabelElement = driver.findElement(By.xpath("//*[@id='dwfrm_orders']/ul/li["+i+"]/div/div[2]/dl[2]/dt"));
                assertEquals(prop.getProperty("orderHistory.label1").toString(), orderHistoryItemsLabelElement.getText());
                
                // Get Items list
                List<String> productNameList = new ArrayList<String>();
                WebElement orderHistoryItemsElement = driver.findElement(By.xpath("//*[@id='dwfrm_orders']/ul/li["+i+"]/div/div[2]/dl[2]/dd"));
                List<WebElement> orderHistoryItemsListElement = orderHistoryItemsElement.findElements(By.cssSelector("li"));
                for( int j=0; j<orderHistoryItemsListElement.size(); j++) {
                    WebElement orderHistoryItemElement = orderHistoryItemsListElement.get(j);
                    productNameList.add(orderHistoryItemElement.getText());
                }
                
                // View Order details
                WebElement viewDetailsButtonElement = driver.findElement(By.xpath("//*[@id='dwfrm_orders']/ul/li["+i+"]/div/div[3]/button"));
                actions.moveToElement(viewDetailsButtonElement).click(viewDetailsButtonElement);
                actions.perform();
                verifyOrderDetails(driver, prop, locator, orderId, productNameList);
            }
        }
    }

    /**
     * Verify order details of each order
     * 
     * @param driver
     * @param prop
     * @param locator
     * @param orderId
     * @param productNameList
     */
    private void verifyOrderDetails(WebDriver driver, Properties prop, Properties locator, String orderId, List<String> productNameList) {
        WebDriverWait wait = (WebDriverWait) new WebDriverWait(driver, 10);

        // Assert Order Id
        WebElement orderDetailsIdElement = driver.findElement(By.xpath(locator.getProperty("orderDetails.orderId").toString()));
        assertEquals(orderId, orderDetailsIdElement.getText());
        
        // Assert track package
        WebElement orderDetailsTrackElement = driver.findElement(By.xpath(locator.getProperty("orderDetails.track").toString()));
        assertEquals("TRACK PACKAGE", orderDetailsTrackElement.getText());
        
        // Assert Item Label
        WebElement orderDetailsItemLabelElement = driver.findElement(By.xpath(locator.getProperty("orderDetails.items.label").toString()));
        assertEquals("Shipment Items", orderDetailsItemLabelElement.getText());
        
        // Get Items list
        List<WebElement> orderDetailsItemsListElement = driver.findElements(By.className(locator.getProperty("orderDetails.items.list").toString()));

        for(int i=0; i<orderDetailsItemsListElement.size(); i++) {
            WebElement orderDetailsItemElement = orderDetailsItemsListElement.get(i);
            String productName = orderDetailsItemElement.findElement(By.className(locator.getProperty("orderDetails.items.productName").toString())).getText();
            assertEquals(productNameList.get(i).toUpperCase(), productName);    
        } 
        // Go back to Order History page
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(locator.getProperty("orderDetails.back").toString())));

        driver.findElement(By.xpath(locator.getProperty("orderDetails.back").toString())).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h1.text-center")));
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
