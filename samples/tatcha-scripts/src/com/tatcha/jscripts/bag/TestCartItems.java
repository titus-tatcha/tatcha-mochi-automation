/**
 * 
 */
package com.tatcha.jscripts.bag;

import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.tatcha.jscripts.dao.TestCase;
import com.tatcha.jscripts.dao.User;
import com.tatcha.jscripts.helper.TatchaTestHelper;

/**
 * @author Reshma
 *
 */
public class TestCartItems {

    private TatchaTestHelper testHelper = new TatchaTestHelper();
    private final static Logger logger = Logger.getLogger(TestCartItems.class);
    private int QTY_TWO = 2;
    private TestCase testCase;

    /**
     * Goto the url of a product's pdp and add it to cart
     * 
     * @param driver2
     * @param prop2
     * @param locator2
     * @param user
     * @param tcList
     * @throws Exception
     */
    public void testUpdateItemQuantity(WebDriver driver, Properties prop, Properties locator, User user,
            List<TestCase> tcList) throws Exception {
        logger.info("BEGIN testUpdateItemQuantity");
        String FUNCTIONALITY = "Update the quntity of a product in shopping bag";
        testCase = new TestCase("TC-2.1", "MOC-NIL", FUNCTIONALITY, "FAIL", "");

        WebDriverWait wait = (WebDriverWait) new WebDriverWait(driver, 10);
        // Actions actions = new Actions(driver);
        WebElement titleElement = null;

        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("bag-item-line ")));
        List<WebElement> bagLineItemElements = driver.findElements(By.className("bag-item-line "));
        logger.info("Shopping bag has " + bagLineItemElements.size() + " LINE item");

        String productLocator = locator.getProperty("product.locator").toString();
        if (null != user.getProducts() && !user.getProducts().isEmpty()) {
            int productInCart = user.getProducts().size();
            int index = productInCart;
            for (int i = 0; i < productInCart; i++) {

                String productName = user.getProducts().get(i).getName().toUpperCase();
                titleElement = driver.findElement(
                        By.xpath(productLocator + (index - i) + locator.getProperty("product.name").toString()));
                getTestHelper().logAssertion(getClass().getSimpleName(), productName, titleElement.getText());

                WebElement qtyLabelElement = driver.findElement(By
                        .xpath(productLocator + (index - i) + locator.getProperty("product.qty.label").toString()));
                getTestHelper().logAssertion(getClass().getSimpleName(), "QTY", qtyLabelElement.getText());

                WebElement quantityElement = driver.findElement(By.xpath(
                        productLocator + (index - i) + locator.getProperty("product.qty.dropdown").toString()));
                Select quantity = new Select(quantityElement);
                if (quantityElement.isEnabled()) {
                    logger.info("Dropdown is enabled");
                    quantity.selectByVisibleText(Integer.toString(QTY_TWO));
                }
                user.getProducts().get(i).setQuantity(QTY_TWO);
            }
        }

        // WebElement qtyDropdownElement = (WebElement)
        // actions.moveToElement(bagLineItemElements.get(0)).moveToElement(
        // (WebElement)
        // driver.findElement(By.cssSelector(locator.getProperty("product.qty.dropdown").toString())));
        //
        // WebElement productNameElement = (WebElement)
        // actions.moveToElement(bagLineItemElements.get(0)).moveToElement(
        // (WebElement)
        // driver.findElement(By.cssSelector(locator.getProperty("product.name").toString())));
        // String productName = productNameElement.getText();
        //
        // Select quantity = new Select(qtyDropdownElement);
        // if(qtyDropdownElement.isEnabled()) {
        // logger.info("Dropdown is enabled");
        // quantity.selectByVisibleText(Integer.toString(QTY_TWO));
        // }
        // for(Product product : user.getProducts()) {
        // if(!productName.isEmpty() &&
        // productName.equalsIgnoreCase(product.getName())) {
        // product.setQuantity(2);
        // }
        // }
        wait.until(ExpectedConditions.refreshed(ExpectedConditions.stalenessOf(titleElement)));
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("bag-item-line ")));

        testCase.setStatus("PASS");
        tcList.add(testCase);
        logger.info("END testUpdateItemQuantity");
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
