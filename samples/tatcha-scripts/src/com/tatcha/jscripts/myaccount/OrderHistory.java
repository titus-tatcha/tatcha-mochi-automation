package com.tatcha.jscripts.myaccount;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.tatcha.jscripts.dao.TestCase;
import com.tatcha.jscripts.helper.TatchaTestHelper;

public class OrderHistory {

    
    private TatchaTestHelper testHelper = new TatchaTestHelper();
    private final static Logger logger = Logger.getLogger(OrderHistory.class);
    private TestCase testCase;

    /**
     * Handles order history
     * 
     * @param driver
     * @param prop
     * @param locator
     * @throws Exception
     */
    public void verifyOrderHistory(WebDriver driver, Properties prop, Properties locator, List<TestCase> tcList) throws Exception {
        logger.info("BEGIN verifyOrderHistory");
        String FUNCTIONALITY = "Verify the order history page";
        testCase = new TestCase("TC-20.1", "MOC-NIL", FUNCTIONALITY, "FAIL", "");
        
        Actions actions = new Actions(driver);
        boolean isOrderHistoryPresent = false;
        try {
            WebElement noOrderLabelElement = driver.findElement(By.xpath(locator.getProperty("orderDetails.noOrderHistory.label").toString()));
            getTestHelper().logAssertion(getClass().getSimpleName(), "You have not placed any orders.", noOrderLabelElement.getText());
        } catch(NoSuchElementException ne) {
            isOrderHistoryPresent = true;
        }
        if (getTestHelper().isLoggedIn(driver) && isOrderHistoryPresent) {
            driver.findElement(By.xpath(locator.getProperty("account.order").toString())).click();
            
            // Assert Title
            WebElement orderHistoryTitleElement = driver.findElement(By.cssSelector("h1.text-center"));
            getTestHelper().logAssertion(getClass().getSimpleName(), prop.getProperty("myaccount.item2.title").toString().toUpperCase(), orderHistoryTitleElement.getText());

            List<WebElement> orderHistoryElement = driver.findElements(By.className("panel-title"));
            int noOfOrders = orderHistoryElement.size();
                    
            for(int i=1; i<=noOfOrders; i++) {
                // Get the Order Id
                WebElement orderHistoryIdElement = driver.findElement(By.xpath("//*[@id='dwfrm_orders']/ul/li["+i+"]/div/div[1]/h5/a"));
                String orderId = orderHistoryIdElement.getText();

                // Assert Items
                WebElement orderHistoryItemsLabelElement = driver.findElement(By.xpath("//*[@id='dwfrm_orders']/ul/li["+i+"]/div/div[2]/dl[2]/dt"));
                getTestHelper().logAssertion(getClass().getSimpleName(), prop.getProperty("orderHistory.label1").toString(), orderHistoryItemsLabelElement.getText());
                
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
                verifyOrderDetails(driver, prop, locator, orderId, productNameList, tcList);
            }
        }
        testCase.setStatus("PASS");
        tcList.add(testCase);
        logger.info("END verifyOrderHistory");
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
    private void verifyOrderDetails(WebDriver driver, Properties prop, Properties locator, String orderId, List<String> productNameList, List<TestCase> tcList) throws Exception {
        logger.info("BEGIN verifyOrderDetails");
        String FUNCTIONALITY = "Verify the order details of each order in order history";
        testCase = new TestCase("TC-20.2", "MOC-NIL", FUNCTIONALITY, "FAIL", "");
        WebDriverWait wait = (WebDriverWait) new WebDriverWait(driver, 10);

        // Assert Order Id
        WebElement orderDetailsIdElement = driver.findElement(By.xpath(locator.getProperty("orderDetails.orderId").toString()));
        getTestHelper().logAssertion(getClass().getSimpleName(), orderId, orderDetailsIdElement.getText());
        
        // Assert track package
        WebElement orderDetailsTrackElement = driver.findElement(By.xpath(locator.getProperty("orderDetails.track").toString()));
        getTestHelper().logAssertion(getClass().getSimpleName(), "TRACK PACKAGE", orderDetailsTrackElement.getText());
        
        // Assert Item Label
        WebElement orderDetailsItemLabelElement = driver.findElement(By.xpath(locator.getProperty("orderDetails.items.label").toString()));
        getTestHelper().logAssertion(getClass().getSimpleName(), "Shipment Items", orderDetailsItemLabelElement.getText());
        
        // Get Items list
        List<WebElement> orderDetailsItemsListElement = driver.findElements(By.className(locator.getProperty("orderDetails.items.list").toString()));

        for(int i=0; i<orderDetailsItemsListElement.size(); i++) {
            WebElement orderDetailsItemElement = orderDetailsItemsListElement.get(i);
            String productName = orderDetailsItemElement.findElement(By.className(locator.getProperty("orderDetails.items.productName").toString())).getText();
            getTestHelper().logAssertion(getClass().getSimpleName(), productNameList.get(i).toUpperCase(), productName);    
        } 
        // Go back to Order History page
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(locator.getProperty("orderDetails.back").toString())));

        driver.findElement(By.xpath(locator.getProperty("orderDetails.back").toString())).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h1.text-center")));
        
        testCase.setStatus("PASS");
        tcList.add(testCase);
        logger.info("END verifyOrderDetails");

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
