package com.tatcha.jscripts.payment;

import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.tatcha.jscripts.dao.TestCase;
import com.tatcha.jscripts.dao.User;
import com.tatcha.jscripts.helper.TatchaTestHelper;

/**
 * Verify the credit card option of Checkout - Payment page
 * 
 * @author Reshma
 *
 */
public class TestCreditCard {

    private TatchaTestHelper testHelper = new TatchaTestHelper();
    private final static Logger logger = Logger.getLogger(TestCreditCard.class);
    public static final String COUNTRY_US = "United States";
    private TestCase testCase;

    /**
     * Verify Adding the first Credit card during checkout
     * 
     * @param driver
     * @param prop
     * @param locator
     * @param user
     * @param tcList
     * @throws Exception
     */
    public void testAddFirstCreditCard(WebDriver driver, Properties prop, Properties locator, User user,
            List<TestCase> tcList) throws Exception {

        logger.info("BEGIN testAddFirstCreditCard");
        String FUNCTIONALITY = "Add first credit card";
        testCase = new TestCase("TC-7.1", "MOC-NIL", FUNCTIONALITY, "FAIL", "");

        WebDriverWait wait = new WebDriverWait(driver, 10);
        By creditCardTitleLocator = By.xpath(locator.getProperty("creditCard.title").toString());
        // By cardNameLabelLocator =
        // By.xpath(locator.getProperty("guest.creditCard.name.label").toString());
        // By cardNumberLabelLocator =
        // By.xpath(locator.getProperty("guest.creditCard.number.label").toString());
        // By expirationLabelLocator =
        // By.xpath(locator.getProperty("guest.creditCard.epiration.label").toString());
        // By cvvLabelLocator =
        // By.xpath(locator.getProperty("guest.creditCard.cvv.label").toString());

        wait.until(ExpectedConditions.visibilityOfElementLocated(creditCardTitleLocator));
        // Assert Elements in the credit card section
        getTestHelper().logAssertion(getClass().getSimpleName(),
                prop.getProperty("payment.creditCard.title").toString(),
                driver.findElement(creditCardTitleLocator).getText());

        // Verify Credit card fields
        getTestHelper().logAssertion(getClass().getSimpleName(),
                prop.getProperty("creditCard.name.label").toString(),
                driver.findElement(creditCardTitleLocator).getText());
        getTestHelper().logAssertion(getClass().getSimpleName(),
                prop.getProperty("creditCard.number.label").toString(),
                driver.findElement(creditCardTitleLocator).getText());
        getTestHelper().logAssertion(getClass().getSimpleName(),
                prop.getProperty("creditCard.epiration.label").toString(),
                driver.findElement(creditCardTitleLocator).getText());
        getTestHelper().logAssertion(getClass().getSimpleName(),
                prop.getProperty("creditCard.cvv.label").toString(),
                driver.findElement(creditCardTitleLocator).getText());

        // Braintree do not allow access of credit card fields, hence add
        // gift card
        testCase.setStatus("PASS");
        tcList.add(testCase);
        logger.info("END testAddFirstCreditCard");
    }

    /**
     * Verify adding a new credit card to the list of available credit card
     * during checkout
     * 
     * @param driver
     * @param prop
     * @param locator
     * @param user
     * @param tcList
     * @throws Exception
     */
    public void testAddCreditCard(WebDriver driver, Properties prop, Properties locator, User user,
            List<TestCase> tcList) throws Exception {

        logger.info("BEGIN testAddCreditCard");
        String FUNCTIONALITY = "Add a credit card to the list of credit cards";
        testCase = new TestCase("TC-7.2", "MOC-NIL", FUNCTIONALITY, "FAIL", "");

        Actions actions = new Actions(driver);
        WebDriverWait wait = new WebDriverWait(driver, 10);
        By creditCardAddButtonLocator = By.cssSelector(locator.getProperty("creditCard.addCard.button").toString());
        By addCreditCardModalTitleLocator = By.xpath(locator.getProperty("addCard.title").toString());

        getTestHelper().logAssertion(getClass().getSimpleName(),
                prop.getProperty("payment.addCard.button.label").toString(),
                driver.findElement(creditCardAddButtonLocator).getText());

        // Verify Add Card
        wait.until(ExpectedConditions.visibilityOfElementLocated(creditCardAddButtonLocator));
        WebElement addCreditCardButtonElement = driver.findElement(creditCardAddButtonLocator);
        actions.moveToElement(addCreditCardButtonElement).click(addCreditCardButtonElement);
        actions.perform();
        wait.until(ExpectedConditions.visibilityOfElementLocated(addCreditCardModalTitleLocator));
        wait.until(ExpectedConditions
                .elementToBeClickable(By.xpath(locator.getProperty("addcard.cancel.button").toString())));
        getTestHelper().logAssertion(getClass().getSimpleName(),
                prop.getProperty("payment.addCard.modalBox.title").toString(),
                driver.findElement(addCreditCardModalTitleLocator).getText());
        WebElement cancelButtonElement = driver
                .findElement(By.xpath(locator.getProperty("addcard.cancel.button").toString()));
        actions.moveToElement(cancelButtonElement).click(cancelButtonElement);
        actions.perform();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(addCreditCardModalTitleLocator));
        testCase.setStatus("PASS");
        tcList.add(testCase);
        logger.info("END testAddCreditCard");
    }

    /**
     * Verify Selecting a credit card from list of credit cards
     * 
     * @param driver
     * @param prop
     * @param locator
     * @param user
     * @param tcList
     * @throws Exception
     */
    public void testSelectCreditCard(WebDriver driver, Properties prop, Properties locator, User user,
            List<TestCase> tcList) throws Exception {

        logger.info("BEGIN testSelectCreditCard");
        String FUNCTIONALITY = "Select a credit card from list of credit cards";
        testCase = new TestCase("TC-7.3", "MOC-NIL", FUNCTIONALITY, "FAIL", "");

        Actions actions = new Actions(driver);

        // If credit card already present in payment option, select it
        logger.info("Select a credit card");
        List<WebElement> creditCardListElement = driver
                .findElements(By.className(locator.getProperty("creditCard.selectCard.list").toString()));
        if (creditCardListElement.isEmpty()) {
            logger.info("Saved Credit Cards are not present");
        } else if (creditCardListElement.size() >= 2) {
            actions.moveToElement(creditCardListElement.get(1)).click(creditCardListElement.get(1));
        } else {
            actions.moveToElement(creditCardListElement.get(0)).click(creditCardListElement.get(0));
        }
        actions.build().perform();
        testCase.setStatus("PASS");
        tcList.add(testCase);
        logger.info("END testSelectCreditCard");
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
