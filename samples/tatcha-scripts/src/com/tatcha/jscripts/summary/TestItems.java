/**
 * 
 */
package com.tatcha.jscripts.summary;

import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.tatcha.jscripts.dao.TestCase;
import com.tatcha.jscripts.dao.Product;
import com.tatcha.jscripts.dao.User;
import com.tatcha.jscripts.helper.TatchaTestHelper;

/**
 * @author Reshma
 *
 */
public class TestItems {

    private TatchaTestHelper testHelper = new TatchaTestHelper();
    private final static Logger logger = Logger.getLogger(TestItems.class);
    private TestCase testCase;

    /**
     * Test Items section of summary
     * 
     * @param driver
     * @param prop
     * @param locator
     * @param user
     * @param map
     * @param tcList
     * @throws Exception
     */
    public void testItems(WebDriver driver, Properties prop, Properties locator, User user, Map<String, Boolean> map,
            List<TestCase> tcList) throws Exception {

        logger.info("BEGIN testItems");
        String FUNCTIONALITY = "verify items section of summary";
        testCase = new TestCase("TC-17.1", "MOC-NIL", FUNCTIONALITY, "FAIL", "");

        WebDriverWait wait = new WebDriverWait(driver, 10);
        int itemsInCart = getTestHelper().getItemCountInCart(user);

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(locator.getProperty("item.title").toString())));
        logger.info("No of items in Cart : " + itemsInCart);
        if (itemsInCart > 1) {
            getTestHelper().logAssertion(getClass().getSimpleName(), itemsInCart + " ITEMS",
                    driver.findElement(By.xpath(locator.getProperty("item.title").toString())).getText());
        } else {
            getTestHelper().logAssertion(getClass().getSimpleName(), itemsInCart + " ITEM",
                    driver.findElement(By.xpath(locator.getProperty("item.title").toString())).getText());
        }

        Product product = null;
        String priceQtyString = "";
        double productPrice = 0.0;
        int productQty = 0;
        WebElement productNameElement = null, priceQtyElement = null;
        for (int index = user.getProducts().size(); index > 0; index--) {
            product = user.getProducts().get(index - 1);
            productPrice = getTestHelper().getPrice(product.getPrice()) * product.getQuantity();
            productQty = product.getQuantity();
            try {
                productNameElement = driver
                        .findElement(By.xpath(locator.getProperty("item.name").toString() + index + "]/div[2]/h5"));
            } catch (StaleElementReferenceException se) {
                logger.info("Stale element referenced");
                wait.until(ExpectedConditions.stalenessOf(driver.findElement(
                        By.xpath(locator.getProperty("item.name").toString() + index + "]/div[2]/h5"))));
                productNameElement = driver
                        .findElement(By.xpath(locator.getProperty("item.name").toString() + index + "]/div[2]/h5"));
            } catch (NoSuchElementException ne) {
                logger.info("No Such Element");
            }
            getTestHelper().logAssertion(getClass().getSimpleName(), product.getName(),
                    productNameElement.getText());

            priceQtyElement = driver.findElement(
                    By.xpath(locator.getProperty("item.qtyPrice").toString() + index + "]/div[2]/div[2]"));
            priceQtyString = "QTY: " + productQty + " | " + getTestHelper().getPriceString(productPrice);
            getTestHelper().logAssertion(getClass().getSimpleName(), priceQtyString, priceQtyElement.getText());
        }

        testCase.setStatus("PASS");
        tcList.add(testCase);
        logger.info("END testItems");
    }

    /**
     * Test Egift in item sectiion
     * 
     * @param driver
     * @param prop
     * @param locator
     * @param user
     * @param map
     * @param tcList
     * @throws Exception
     */
    public void testEgiftItem(WebDriver driver, Properties prop, Properties locator, User user,
            Map<String, Boolean> map, List<TestCase> tcList) throws Exception {


        logger.info("BEGIN testEgiftItem");
        String FUNCTIONALITY = "Verify items section of summary for egift";
        testCase = new TestCase("TC-17.2", "MOC-NIL", FUNCTIONALITY, "FAIL", "");

        WebDriverWait wait = new WebDriverWait(driver, 10);
        int itemsInCart = getTestHelper().getItemCountInCart(user);

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(locator.getProperty("item.title").toString())));
        logger.info("No of items in Cart : " + itemsInCart);
        if (itemsInCart > 1) {
            getTestHelper().logAssertion(getClass().getSimpleName(), itemsInCart + " ITEMS",
                    driver.findElement(By.xpath(locator.getProperty("item.title").toString())).getText());
        } else {
            getTestHelper().logAssertion(getClass().getSimpleName(), itemsInCart + " ITEM",
                    driver.findElement(By.xpath(locator.getProperty("item.title").toString())).getText());
        }

        Product product = null;
        String priceString = "";
        double productPrice = 0.0;
        WebElement productNameElement = null, priceElement = null;
        for (int index = user.getProducts().size(); index > 0; index--) {
            product = user.getProducts().get(index - 1);
            productPrice = Double.parseDouble(product.getPrice());

            try {
                productNameElement = driver
                        .findElement(By.xpath(locator.getProperty("item.name").toString() + index + "]/div[2]/h5"));
            } catch (StaleElementReferenceException se) {
                logger.info("Stale element referenced");
                wait.until(ExpectedConditions.stalenessOf(driver.findElement(
                        By.xpath(locator.getProperty("item.name").toString() + index + "]/div[2]/h5"))));
                productNameElement = driver
                        .findElement(By.xpath(locator.getProperty("item.name").toString() + index + "]/div[2]/h5"));
            }
            getTestHelper().logAssertion(getClass().getSimpleName(), product.getName(),
                    productNameElement.getText());

            priceElement = driver.findElement(
                    By.xpath(locator.getProperty("item.qtyPrice").toString() + index + "]/div[2]/div"));

            priceString = getTestHelper().getPriceString(productPrice);
            getTestHelper().logAssertion(getClass().getSimpleName(), priceString, priceElement.getText());
        }

        testCase.setStatus("PASS");
        tcList.add(testCase);
        logger.info("END testEgiftItem");
    }

    /**
     * @return the testHelper
     */
    public TatchaTestHelper getTestHelper() {
        return testHelper;
    }

    /**
     * @param testHelper
     *            the testHelper to set
     */
    public void setTestHelper(TatchaTestHelper testHelper) {
        this.testHelper = testHelper;
    }

}
