package com.tatcha.jscripts.bag;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
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

import com.tatcha.jscripts.dao.TestCase;
import com.tatcha.jscripts.dao.Sample;
import com.tatcha.jscripts.dao.User;
import com.tatcha.jscripts.exception.TatchaException;
import com.tatcha.jscripts.helper.TatchaTestHelper;

/**
 * 
 * @author Reshma
 *
 */
public class ShoppingBag {

    private TatchaTestHelper testHelper = new TatchaTestHelper();
    private final static Logger logger = Logger.getLogger(ShoppingBag.class);
    private TestCase testCase;
    private static final String COUPON_CODE = "TATCHATEST";
    private static final int NO_OF_SAMPLES = 3;

    public void verifyShoppingBag(WebDriver driver, Properties prop, Properties locator, User user,
            Map<String, Boolean> map, List<TestCase> tcList) throws Exception {
        try {
            logger.info("BEGIN verifyShoppingBag");
            String FUNCTIONALITY = "Verify Shopping Bag page";
            testCase = new TestCase("TC-19.1", "MOC-NIL", FUNCTIONALITY, "FAIL", "");

            // Assert title
            WebElement titleElement = driver.findElement(By.xpath(locator.getProperty("bag.title").toString()));
            getTestHelper().logAssertion(getClass().getSimpleName(), "SHOPPING BAG", titleElement.getText());

            // Verify Samples
            if (map.containsKey("verifySamples") && map.get("verifySamples")) {
                verifySamples(driver, prop, locator, user, tcList, map);
                logger.debug("Samples done");
            }

            // Verify Product
            if (map.containsKey("verifyProducts") && map.get("verifyProducts")) {
                verifyProducts(driver, prop, locator, user, tcList, map);
                logger.debug("Products done");
            }

            // Verify gift wrap
            if (map.containsKey("verifyGiftWrap") && map.get("verifyGiftWrap")) {
                verifyGiftWrap(driver, prop, locator, user, tcList, map);
                logger.debug("Giftwrap done");
            }

            // Verify summary
            verifySummary(driver, prop, locator, user, tcList);
            logger.debug("Summary done");

            // Verify Promotion
            if (map.containsKey("verifyPromo") && map.get("verifyPromo")) {

                if (map.containsKey("applyPromo") && map.get("applyPromo")) {
                    verifyPromoSummary(driver, prop, locator, user, tcList, map);
                } else {
                    verifyPromotion(driver, prop, locator, user, tcList, map);
                }
            }
            logger.info("END verifyShoppingBag");
        } catch (Exception exp) {
            throw new TatchaException(exp, tcList);
        }
    }

    /**
     * Verify the adding and removing of sample in shopping bag
     * 
     * @param driver
     * @param prop
     * @param locator
     * @throws Exception
     */
    public void verifySamples(WebDriver driver, Properties prop, Properties locator, User user, List<TestCase> tcList,
            Map<String, Boolean> map) throws Exception {

        logger.info("BEGIN verifySamples");
        try {
            String FUNCTIONALITY = "Verify Samples section of shopping bag";
            testCase = new TestCase("TC-19.2", "MOC-NIL", FUNCTIONALITY, "FAIL", "");

            WebDriverWait wait = (WebDriverWait) new WebDriverWait(driver, 10);
            Actions actions = new Actions(driver);

            List<Sample> samplesList = new ArrayList<Sample>();

            // Assert title
            WebElement titleElement = driver.findElement(By.xpath(locator.getProperty("sample.title").toString()));
            getTestHelper().logAssertion(getClass().getSimpleName(), "CHOOSE 3 COMPLIMENTARY SAMPLES",
                    titleElement.getText());

            // Click ADD SAMPLES on top right
            WebElement addSampleButtonElement = driver
                    .findElement(By.xpath(locator.getProperty("addSample.button").toString()));
            getTestHelper().logAssertion(getClass().getSimpleName(), "ADD SAMPLES", addSampleButtonElement.getText());
            addSampleButtonElement.click();

            logger.debug("Assert title");
            By modalBoxTitle = By.xpath(locator.getProperty("addSample.modalBox.title").toString());
            wait.until(ExpectedConditions.visibilityOfElementLocated(modalBoxTitle));
            WebElement titleModalElement = driver.findElement(modalBoxTitle);
            getTestHelper().logAssertion(getClass().getSimpleName(), "CHOOSE 3 COMPLIMENTARY SAMPLES",
                    titleModalElement.getText());

            // Close modal box
            logger.debug("Close modal box");
            WebElement closeSampleModalElement = driver
                    .findElement(By.xpath(locator.getProperty("addSample.modalBox.close").toString()));
            closeSampleModalElement.click();
            wait.until(ExpectedConditions.invisibilityOf(titleModalElement));
            wait.until(ExpectedConditions.refreshed(ExpectedConditions
                    .stalenessOf(driver.findElement(By.xpath(locator.getProperty("sample.title").toString())))));

            logger.debug("Click sample icon");
            By addSampleIcon = By.cssSelector(locator.getProperty("addSample.plusIcon").toString());
            WebElement addSampleIconElement = driver.findElement(addSampleIcon);
            getTestHelper().logAssertion(getClass().getSimpleName(), "Add A Sample", addSampleIconElement.getText());
            wait.until(ExpectedConditions.visibilityOf(addSampleIconElement));
            actions.moveToElement(addSampleIconElement).click(addSampleIconElement);
            actions.perform();

            logger.debug("Add samples");
            wait.until(ExpectedConditions
                    .visibilityOfElementLocated(By.xpath(locator.getProperty("addSample.modalBox.title").toString())));
            for (int i = 1; i <= NO_OF_SAMPLES; i++) {
                driver.findElement(By.xpath(locator.getProperty("addSample.sample" + i + ".select").toString()))
                        .click();
                // driver.findElement(By.xpath(locator.getProperty("addSample.sample2.select").toString())).click();
                // driver.findElement(By.xpath(locator.getProperty("addSample.sample3.select").toString())).click();
            }

            // Add selected samples
            driver.findElement(By.id(locator.getProperty("addSample.save.button").toString())).click();
            wait.until(ExpectedConditions.invisibilityOfElementLocated(
                    By.xpath(locator.getProperty("addSample.modalBox.title").toString())));
            WebElement sampleNameElement = null;
            Sample sample = null;
            for (int j = 1; j <= NO_OF_SAMPLES; j++) {
                try {
                    sampleNameElement = driver
                            .findElement(By.xpath(locator.getProperty("sample.sample" + j + ".name").toString()));
                    sample = new Sample();
                    sample.setName(sampleNameElement.getText());
                    samplesList.add(sample);
                } catch (NoSuchElementException ne) {
                    logger.info("Samples are not added");
                }
            }

            if (map.containsKey("addSamples") && !map.get("addSamples")) {
                // Click edit sample on top right and Remove all the samples
                logger.info("Remove samples");
                driver.findElement(By.xpath(locator.getProperty("addSample.button").toString())).click();
                wait.until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath(locator.getProperty("addSample.modalBox.title").toString())));

                driver.findElement(By.xpath(locator.getProperty("addSample.sample1.select").toString())).click();
                driver.findElement(By.xpath(locator.getProperty("addSample.sample2.select").toString())).click();
                driver.findElement(By.xpath(locator.getProperty("addSample.sample3.select").toString())).click();
                driver.findElement(By.id(locator.getProperty("addSample.save.button").toString())).click();

                wait.until(ExpectedConditions.invisibilityOfElementLocated(
                        By.xpath(locator.getProperty("addSample.modalBox.title").toString())));
                samplesList.clear();
            }

            user.setSamples(samplesList);
            testCase.setStatus("PASS");
            tcList.add(testCase);
            logger.info("END verifySamples");
        } catch (Exception exp) {
            throw new TatchaException(exp, tcList);
        }

    }

    /**
     * Verify each product in the shopping bag
     * 
     * @param driver
     * @param prop
     * @param locator
     * @param user
     * @throws Exception
     */
    private void verifyProducts(WebDriver driver, Properties prop, Properties locator, User user, List<TestCase> tcList,
            Map<String, Boolean> map) throws Exception {
        try {
            logger.info("BEGIN verifyProducts");
            String FUNCTIONALITY = "Verify Products in shopping bag";
            testCase = new TestCase("TC-19.3", "MOC-NIL", FUNCTIONALITY, "FAIL", "");

            WebDriverWait wait = (WebDriverWait) new WebDriverWait(driver, 5);
            String productLocator = locator.getProperty("product.locator").toString();
            if (null != user.getProducts() && !user.getProducts().isEmpty()) {
                int productInCart = user.getProducts().size();
                int index = productInCart;
                for (int i = 0; i < productInCart; i++) {

                    logger.info("Assert product name");
                    String productName = user.getProducts().get(i).getName().toUpperCase();
                    WebElement titleElement = driver.findElement(
                            By.xpath(productLocator + (index - i) + locator.getProperty("product.name").toString()));
                    getTestHelper().logAssertion(getClass().getSimpleName(), productName,
                            titleElement.getText().toUpperCase());

                    // Assert QTY
                    logger.info("Started Quantity");
                    int qty = 2;
                    WebElement qtyLabelElement = driver.findElement(By
                            .xpath(productLocator + (index - i) + locator.getProperty("product.qty.label").toString()));
                    getTestHelper().logAssertion(getClass().getSimpleName(), "QTY", qtyLabelElement.getText());
                    WebElement quantityElement = driver.findElement(By.xpath(
                            productLocator + (index - i) + locator.getProperty("product.qty.dropdown").toString()));
                    Select dropdown = new Select(quantityElement);
                    dropdown.selectByVisibleText(Integer.toString(qty));
                    user.getProducts().get(i).setQuantity(qty);

                    // Assert Price
                    boolean isPromoApplied = false;
                    try {
                        WebElement totalPriceElement = driver.findElement(By
                                .xpath(productLocator + (index - i) + locator.getProperty("product.price").toString()));
                        String priceString = user.getProducts().get(i).getPrice();
                        if (null != priceString) {
                            double price = getTestHelper().getPrice(priceString) * qty;
                            // Formatted price string
                            priceString = getTestHelper().getPriceString(price);
                            getTestHelper().logAssertion(getClass().getSimpleName(), priceString,
                                    totalPriceElement.getText());
                            user.getProducts().get(i).setPrice(priceString);
                        }
                    } catch (NoSuchElementException exe) {
                        isPromoApplied = true;
                    }

                    WebElement wasPriceElement = null;
                    if (isPromoApplied) {
                        WebElement priceElement = driver.findElement(By.xpath(productLocator + (index - i)
                                + locator.getProperty("product.discountedPrice").toString()));
                        try {
                            wasPriceElement = driver.findElement(By.xpath(productLocator + (index - i)
                                    + locator.getProperty("product.actualPrice").toString()));
                            getTestHelper().logAssertion(getClass().getSimpleName(),
                                    user.getProducts().get(i).getPrice(), wasPriceElement.getText());
                            getTestHelper().logAssertion(getClass().getSimpleName(), null != priceElement.getText());
                            user.getProducts().get(i).setPrice(priceElement.getText());
                        } catch (NoSuchElementException exe) {
                            getTestHelper().logAssertion(getClass().getSimpleName(),
                                    user.getProducts().get(i).getPrice(), priceElement.getText());
                        }
                    }

                    logger.info("Started Promo Msg");
                    // Assert Specific Promo and complimentary sample msg
                    try {
                        WebElement marketingBannerElement = driver.findElement(By.xpath(
                                productLocator + (index - i) + locator.getProperty("product.promoMsg").toString()));
                        getTestHelper().logAssertion(getClass().getSimpleName(),
                                null != marketingBannerElement.getText());
                    } catch (NoSuchElementException exe) {
                        // If Was Price is greater than present price, promo msg
                        // should be present
                        logger.info("Product do not have any promo message");
                    }

                    // Check if item is out of stock, if so assert the error
                    // message shown
                    WebElement outOfStockMessageElement = null;
                    WebElement errorElement = null;
                    try {
                        outOfStockMessageElement = driver.findElement(By.xpath(productLocator + (index - i)
                                + locator.getProperty("product.outOfStockMsg").toString()));
                        if (null != outOfStockMessageElement) {
                            errorElement = driver
                                    .findElement(By.xpath(locator.getProperty("bag.outOfStock.errorMsg").toString()));
                            getTestHelper().logAssertion(getClass().getSimpleName(),
                                    prop.getProperty("shoppingBag.invalidItem.errorMsg").toString(),
                                    errorElement.getText());
                        }
                    } catch (NoSuchElementException exe) {
                        logger.info("Product is not out of stock");
                    }

                    logger.info("Started Auto delivery of : " + productName);
                    // Assert Auto Delivery
                    Actions actions = new Actions(driver);
                    try {
                        WebElement autoDeliveryLabelElement = driver.findElement(
                                By.xpath(locator.getProperty("autoDelivery.label").toString() + (index - i) + "]"));
                        getTestHelper().logAssertion(getClass().getSimpleName(), "Auto-Delivery",
                                autoDeliveryLabelElement.getText());

                        // click Tooltip
                        WebElement autoDeliveryTooltipElement = driver.findElement(
                                By.xpath(locator.getProperty("autoDelivery.tooltip").toString() + (index - i) + "]"));
                        actions.moveToElement(autoDeliveryTooltipElement).click(autoDeliveryTooltipElement);
                        actions.perform();
                        wait.until(ExpectedConditions.visibilityOfElementLocated(
                                By.xpath(locator.getProperty("autoDelivery.tooltip.title").toString())));

                        // close tooltip
                        driver.findElement(By.cssSelector(locator.getProperty("autoDelivery.tooltip.close").toString()))
                                .click();
                        wait.until(ExpectedConditions.invisibilityOfElementLocated(
                                By.xpath(locator.getProperty("autoDelivery.tooltip.title").toString())));

                        // click checkbox
                        WebElement autoDeliveryCheckboxElement = driver.findElement(
                                By.xpath(locator.getProperty("autoDelivery.checkbox").toString() + (index - i) + "]"));
                        autoDeliveryCheckboxElement.click();
                        wait.until(ExpectedConditions
                                .refreshed(ExpectedConditions.stalenessOf(autoDeliveryCheckboxElement)));

                        // Select frequency
                        wait.until(ExpectedConditions.visibilityOfElementLocated(By
                                .xpath(locator.getProperty("autoDelivery.frequency").toString() + (index - i) + "]")));
                        WebElement frequencySelectElement = driver.findElement(
                                By.xpath(locator.getProperty("autoDelivery.frequency").toString() + (index - i) + "]"));
                        autoDeliveryCheckboxElement = driver.findElement(
                                By.xpath(locator.getProperty("autoDelivery.checkbox").toString() + (index - i) + "]"));

                        Select frequencyDropdown = new Select(frequencySelectElement);
                        frequencyDropdown.selectByIndex(1);
                        wait.until(ExpectedConditions
                                .refreshed(ExpectedConditions.stalenessOf(autoDeliveryCheckboxElement)));

                        // uncheck checkbox
                        if (map.containsKey("enableAutoDelivery") && !map.get("enableAutoDelivery")) {
                            logger.info("Remove auto delivery");
                            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
                                    locator.getProperty("autoDelivery.checkbox").toString() + (index - i) + "]")));
                            autoDeliveryCheckboxElement = driver.findElement(By.xpath(
                                    locator.getProperty("autoDelivery.checkbox").toString() + (index - i) + "]"));
                            actions.moveToElement(autoDeliveryCheckboxElement).click(autoDeliveryCheckboxElement);
                            actions.perform();
                            wait.until(ExpectedConditions
                                    .refreshed(ExpectedConditions.stalenessOf(autoDeliveryCheckboxElement)));
                        }
                        // wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(locator.getProperty("autoDelivery.frequency").toString()+(index-i)+"]")));
                        testCase.setStatus("PASS");
                        tcList.add(testCase);
                        logger.info("END verifyProducts");

                    } catch (NoSuchElementException exe) {
                        logger.info("Auto-Delivery not available for : " + productName);
                        testCase.setStatus("PASS");
                        tcList.add(testCase);
                    } catch (TimeoutException exe) {

                    } catch (ElementNotVisibleException exe) {

                    }
                }
            }
        } catch (Exception exp) {
            throw new TatchaException(exp, tcList);
        }
    }

    /**
     * verify Adding and removing of gift wrap
     * 
     * @param driver
     * @param prop
     * @param locator
     * @throws Exception
     */
    public void verifyGiftWrap(WebDriver driver, Properties prop, Properties locator, User user, List<TestCase> tcList,
            Map<String, Boolean> map) throws Exception {
        try {
            logger.info("BEGIN verifyGiftWrap");
            String FUNCTIONALITY = "Verify Gift Wrap section of shopping bag";
            testCase = new TestCase("TC-19.4", "MOC-NIL", FUNCTIONALITY, "FAIL", "");

            WebDriverWait wait = (WebDriverWait) new WebDriverWait(driver, 10);
            WebDriverWait wait5 = (WebDriverWait) new WebDriverWait(driver, 10);
            Actions actions = new Actions(driver);

            boolean isGiftWrapAdded = true;

            wait.until(ExpectedConditions
                    .visibilityOfElementLocated(By.xpath(locator.getProperty("giftWrap.title").toString())));

            // Assert title
            WebElement titleElement = driver.findElement(By.xpath(locator.getProperty("giftWrap.title").toString()));
            getTestHelper().logAssertion(getClass().getSimpleName(), "Tatcha Gift Wrapping", titleElement.getText());

            // Verify tool tip
            WebElement toolTipElement = driver
                    .findElement(By.xpath(locator.getProperty("giftWrap.tooltip").toString()));
            actions.moveToElement(toolTipElement).click(toolTipElement);
            actions.perform();
            wait.until(ExpectedConditions
                    .visibilityOfElementLocated(By.xpath(locator.getProperty("giftWrap.tooltip.title").toString())));
            WebElement toolTipTitleElement = driver
                    .findElement(By.xpath(locator.getProperty("giftWrap.tooltip.title").toString()));
            getTestHelper().logAssertion(getClass().getSimpleName(), "GIFT BOXES", toolTipTitleElement.getText());
            WebElement toolTipDescriptionElement = driver
                    .findElement(By.xpath(locator.getProperty("giftWrap.tooltip.description").toString()));
            getTestHelper().logAssertion(getClass().getSimpleName(),
                    prop.getProperty("shoppingBag.giftWrap.toolTip").toString(), toolTipDescriptionElement.getText());
            WebElement toolTipCloseElement = driver
                    .findElement(By.xpath(locator.getProperty("giftWrap.tooltip.close").toString()));
            toolTipCloseElement.click();
            wait.until(ExpectedConditions
                    .invisibilityOfElementLocated(By.xpath(locator.getProperty("giftWrap.tooltip.title").toString())));

            // Assert Check box label
            WebElement checkboxLabelElement = driver
                    .findElement(By.xpath(locator.getProperty("giftWrap.checkbox.label").toString()));
            getTestHelper().logAssertion(getClass().getSimpleName(), "Add a gift box for this order. ($5.00)",
                    checkboxLabelElement.getText());

            // Verify check box
            logger.info("Verify checkbox");
            WebElement checkboxElement = driver
                    .findElement(By.xpath(locator.getProperty("giftWrap.checkbox").toString()));
            checkboxElement.click();
            wait.until(ExpectedConditions.refreshed(ExpectedConditions
                    .elementToBeClickable(By.xpath(locator.getProperty("giftwrap.message.button").toString()))));
            // wait.until(ExpectedConditions.elementToBeClickable(By.xpath(locator.getProperty("giftwrap.message.button").toString())));

            // Verify Gift message
            logger.info("Verify gift message");
            WebElement giftMessageButtonElement = driver
                    .findElement(By.xpath(locator.getProperty("giftwrap.message.button").toString()));
            wait.until(ExpectedConditions.elementToBeClickable(giftMessageButtonElement));
            actions.moveToElement(giftMessageButtonElement).click(giftMessageButtonElement);
            actions.perform();

            try {
                wait.until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath(locator.getProperty("giftwrap.message.modalBox.title").toString())));
            } catch (TimeoutException te) {
                giftMessageButtonElement = driver
                        .findElement(By.xpath(locator.getProperty("giftwrap.message.button").toString()));
                actions.moveToElement(giftMessageButtonElement).click(giftMessageButtonElement);
                actions.perform();
                wait.until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath(locator.getProperty("giftwrap.message.modalBox.title").toString())));
            }

            // Assert Gift message modal box
            WebElement giftMessageTitleElement = driver
                    .findElement(By.xpath(locator.getProperty("giftwrap.message.modalBox.title").toString()));
            getTestHelper().logAssertion(getClass().getSimpleName(), "ADD A GIFT MESSAGE",
                    giftMessageTitleElement.getText());
            WebElement giftMessageLabelElement = driver
                    .findElement(By.xpath(locator.getProperty("giftwrap.message.modalBox.label").toString()));
            getTestHelper().logAssertion(getClass().getSimpleName(), "MESSAGE", giftMessageLabelElement.getText());
            WebElement giftMessageInfoElement = driver
                    .findElement(By.xpath(locator.getProperty("giftwrap.message.modalBox.info").toString()));
            getTestHelper().logAssertion(getClass().getSimpleName(), "You have 210 characters left out of 210",
                    giftMessageInfoElement.getText());

            // Save gift message
            logger.info("Save gift message");
            WebElement giftMessageElement = driver
                    .findElement(By.xpath(locator.getProperty("giftwrap.message.modalBox.textarea").toString()));
            giftMessageElement.clear();
            giftMessageElement.sendKeys("Thank You.");
            giftMessageInfoElement = driver
                    .findElement(By.xpath(locator.getProperty("giftwrap.message.modalBox.info").toString()));
            getTestHelper().logAssertion(getClass().getSimpleName(), "You have 200 characters left out of 210",
                    giftMessageInfoElement.getText());
            WebElement giftMessageSaveButtonElement = driver
                    .findElement(By.xpath(locator.getProperty("giftwrap.message.modalBox.save.button").toString()));
            giftMessageSaveButtonElement.click();
            // wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(locator.getProperty("giftwrap.message.modalBox.title").toString())));
            // wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locator.getProperty("giftwrap.message.label").toString())));
            try {
                // Wait for gif image(loading) to become stale
                wait5.until(ExpectedConditions
                        .stalenessOf(driver.findElement(By.xpath("//div[@class='sk-fading-circle']"))));
            } catch (NoSuchElementException ne) {
                logger.info("Loading Gif image cannot be located");
            } catch (TimeoutException ne) {
                logger.info("Timeout waiting for staleness of loading gif image");
            }
            // Verify gift message
            giftMessageLabelElement = driver
                    .findElement(By.xpath(locator.getProperty("giftwrap.message.label").toString()));
            getTestHelper().logAssertion(getClass().getSimpleName(), "MESSAGE", giftMessageLabelElement.getText());
            giftMessageElement = driver.findElement(By.xpath(locator.getProperty("giftwrap.message").toString()));
            getTestHelper().logAssertion(getClass().getSimpleName(), "Thank You.", giftMessageElement.getText());

            if (map.containsKey("addGiftWrap") && !map.get("addGiftWrap")) {
                // Remove gift message
                logger.info("Remove gift message");
                WebElement giftMessageCloseElement = driver
                        .findElement(By.xpath(locator.getProperty("giftwrap.message.close").toString()));
                checkboxElement = driver.findElement(By.xpath(locator.getProperty("giftWrap.checkbox").toString()));
                actions.moveToElement(giftMessageCloseElement).click(giftMessageCloseElement);
                actions.perform();
                try {
                    // Wait for gif image(loading) to become stale
                    wait5.until(ExpectedConditions
                            .stalenessOf(driver.findElement(By.xpath("//div[@class='sk-fading-circle']"))));
                } catch (NoSuchElementException ne) {
                    logger.info("Loading Gif image cannot be located");
                } catch (TimeoutException ne) {
                    logger.info("Timeout waiting for staleness of loading gif image");
                }
                // wait.until(ExpectedConditions.refreshed(ExpectedConditions.stalenessOf(checkboxElement)));
                wait.until(ExpectedConditions.invisibilityOfElementLocated(
                        By.xpath(locator.getProperty("giftwrap.message.close").toString())));

                // Un-check check box
                logger.info("Remove Gift box");
                checkboxElement = driver.findElement(By.xpath(locator.getProperty("giftWrap.checkbox").toString()));
                actions.moveToElement(checkboxElement).click(checkboxElement);
                actions.perform();

                isGiftWrapAdded = false;
            }
            user.setIsGiftWrap(isGiftWrapAdded);
            wait.until(ExpectedConditions.refreshed(ExpectedConditions.stalenessOf(checkboxElement)));
            testCase.setStatus("PASS");
            tcList.add(testCase);
            logger.info("END verifyGiftWrap");
        } catch (Exception exp) {
            throw new TatchaException(exp, tcList);
        }
    }

    /**
     * Verify the order summary section of shopping bag
     * 
     * @param driver
     * @param prop
     * @param locator
     * @param user
     * @throws Exception
     */
    public void verifySummary(WebDriver driver, Properties prop, Properties locator, User user, List<TestCase> tcList)
            throws Exception {
        try {
            logger.info("BEGIN verifySummary");
            String FUNCTIONALITY = "Verify Summary in shopping bag";
            testCase = new TestCase("TC-19.5", "MOC-NIL", FUNCTIONALITY, "FAIL", "");

            WebDriverWait wait = (WebDriverWait) new WebDriverWait(driver, 5);
            wait.until(ExpectedConditions
                    .visibilityOfElementLocated(By.xpath(locator.getProperty("summary.title").toString())));

            if (null != user.getProducts()) {

                int itemsInCart = getTestHelper().getItemCountInCart(user);

                // Summary
                getTestHelper().logAssertion(getClass().getSimpleName(), "Summary",
                        driver.findElement(By.xpath(locator.getProperty("summary.title").toString())).getText());
                if (itemsInCart > 1) {
                    getTestHelper().logAssertion(getClass().getSimpleName(), itemsInCart + " items", driver
                            .findElement(By.xpath(locator.getProperty("summary.item.count").toString())).getText());
                } else {
                    getTestHelper().logAssertion(getClass().getSimpleName(), itemsInCart + " item", driver
                            .findElement(By.xpath(locator.getProperty("summary.item.count").toString())).getText());
                }
                getTestHelper().logAssertion(getClass().getSimpleName(), "MERCHANDISE TOTAL", driver
                        .findElement(By.xpath(locator.getProperty("summary.merchtotal.label").toString())).getText());
                getTestHelper().logAssertion(getClass().getSimpleName(), "ESTIMATED SHIPPING", driver
                        .findElement(By.xpath(locator.getProperty("summary.shipping.label").toString())).getText());
                getTestHelper().logAssertion(getClass().getSimpleName(), "ESTIMATED TAX",
                        driver.findElement(By.xpath(locator.getProperty("summary.tax.label").toString())).getText());
                getTestHelper().logAssertion(getClass().getSimpleName(), "ORDER TOTAL",
                        driver.findElement(By.xpath(locator.getProperty("summary.total.label").toString())).getText());

                String merchTotalString = driver
                        .findElement(By.xpath(locator.getProperty("summary.merchtotal.value").toString())).getText();
                String shipAmtString = driver
                        .findElement(By.xpath(locator.getProperty("summary.shipping.value").toString())).getText();
                String taxAmtString = driver.findElement(By.xpath(locator.getProperty("summary.tax.value").toString()))
                        .getText();
                String orderTotalString = driver
                        .findElement(By.xpath(locator.getProperty("summary.total.value").toString())).getText();

                getTestHelper()
                        .logAssertion(getClass().getSimpleName(),
                                getTestHelper().getPrice(orderTotalString) == getTestHelper().getPrice(merchTotalString)
                                        + getTestHelper().getPrice(shipAmtString)
                                        + getTestHelper().getPrice(taxAmtString));
            }
            testCase.setStatus("PASS");
            tcList.add(testCase);
            logger.info("END verifySummary");
        } catch (Exception exp) {
            throw new TatchaException(exp, tcList);
        }
    }

    /**
     * Verify summary when promotion applied
     * 
     * @param driver
     * @param prop
     * @param locator
     * @param user
     * @param tcList
     * @param map
     * @throws Exception
     */
    public void verifyPromoSummary(WebDriver driver, Properties prop, Properties locator, User user,
            List<TestCase> tcList, Map<String, Boolean> map) throws Exception {
        try {
            logger.info("BEGIN verifyPromoSummary");
            String FUNCTIONALITY = "Verify Summary in shopping bag when promotion is applied";
            testCase = new TestCase("TC-19.6", "MOC-NIL", FUNCTIONALITY, "FAIL", "");

            WebDriverWait wait = (WebDriverWait) new WebDriverWait(driver, 5);
            wait.until(ExpectedConditions
                    .visibilityOfElementLocated(By.xpath(locator.getProperty("summary.title").toString())));

            if (null != user.getProducts()) {
                int itemsInCart = getTestHelper().getItemCountInCart(user);

                // Summary
                getTestHelper().logAssertion(getClass().getSimpleName(), "Summary",
                        driver.findElement(By.xpath(locator.getProperty("summary.title").toString())).getText());
                if (itemsInCart > 1) {
                    getTestHelper().logAssertion(getClass().getSimpleName(), itemsInCart + " items", driver
                            .findElement(By.xpath(locator.getProperty("summary.item.count").toString())).getText());
                } else {
                    getTestHelper().logAssertion(getClass().getSimpleName(), itemsInCart + " item", driver
                            .findElement(By.xpath(locator.getProperty("summary.item.count").toString())).getText());
                }

                verifyPromotion(driver, prop, locator, user, tcList, map);

                getTestHelper().logAssertion(getClass().getSimpleName(), "MERCHANDISE TOTAL", driver
                        .findElement(By.xpath(locator.getProperty("summary2.merchtotal.label").toString())).getText());
                getTestHelper().logAssertion(getClass().getSimpleName(), "DISCOUNT: " + COUPON_CODE, driver
                        .findElement(By.xpath(locator.getProperty("summary2.discount.label").toString())).getText());
                getTestHelper().logAssertion(getClass().getSimpleName(), "SUBTOTAL", driver
                        .findElement(By.xpath(locator.getProperty("summary2.subtotal.label").toString())).getText());
                getTestHelper().logAssertion(getClass().getSimpleName(), "ESTIMATED SHIPPING", driver
                        .findElement(By.xpath(locator.getProperty("summary2.shipping.label").toString())).getText());
                getTestHelper().logAssertion(getClass().getSimpleName(), "ESTIMATED TAX",
                        driver.findElement(By.xpath(locator.getProperty("summary2.tax.label").toString())).getText());
                getTestHelper().logAssertion(getClass().getSimpleName(), "ORDER TOTAL",
                        driver.findElement(By.xpath(locator.getProperty("summary2.total.label").toString())).getText());

                String merchTotalString = driver
                        .findElement(By.xpath(locator.getProperty("summary2.merchtotal.value").toString())).getText();
                String discountString = driver
                        .findElement(By.xpath(locator.getProperty("summary2.discount.value").toString())).getText();
                String subtotalString = driver
                        .findElement(By.xpath(locator.getProperty("summary2.subtotal.value").toString())).getText();
                String shipAmtString = driver
                        .findElement(By.xpath(locator.getProperty("summary2.shipping.value").toString())).getText();
                String taxAmtString = driver.findElement(By.xpath(locator.getProperty("summary2.tax.value").toString()))
                        .getText();
                String orderTotalString = driver
                        .findElement(By.xpath(locator.getProperty("summary2.total.value").toString())).getText();

                getTestHelper().logAssertion(getClass().getSimpleName(),
                        getTestHelper().getPrice(subtotalString) == getTestHelper().getPrice(merchTotalString)
                                - getTestHelper().getPrice(discountString));

                getTestHelper()
                        .logAssertion(getClass().getSimpleName(),
                                getTestHelper().getPrice(orderTotalString) == getTestHelper().getPrice(subtotalString)
                                        + getTestHelper().getPrice(shipAmtString)
                                        + getTestHelper().getPrice(taxAmtString));
            }
            testCase.setStatus("PASS");
            tcList.add(testCase);
            logger.info("END verifyPromoSummary");
        } catch (Exception exp) {
            throw new TatchaException(exp, tcList);
        }
    }

    /**
     * Verify Promotion section of summary
     * 
     * @param driver
     * @param prop
     * @param locator
     * @param user
     * @param tcList
     * @param map
     * @throws Exception
     */
    public void verifyPromotion(WebDriver driver, Properties prop, Properties locator, User user, List<TestCase> tcList,
            Map<String, Boolean> map) throws Exception {

        try {
            logger.info("BEGIN verifyPromotion");
            String FUNCTIONALITY = "Verify Promotion in shopping bag";
            testCase = new TestCase("TC-19.7", "MOC-NIL", FUNCTIONALITY, "FAIL", "");

            WebDriverWait wait = (WebDriverWait) new WebDriverWait(driver, 5);
            wait.until(ExpectedConditions
                    .visibilityOfElementLocated(By.xpath(locator.getProperty("summary.title").toString())));

            // Promotion
            WebElement promoCodeLabelElement = driver
                    .findElement(By.xpath(locator.getProperty("summary.promo.label").toString()));
            getTestHelper().logAssertion(getClass().getSimpleName(), "PROMO CODE", promoCodeLabelElement.getText());
            getTestHelper().logAssertion(getClass().getSimpleName(), "*Limit one promotional code per order.",
                    driver.findElement(By.cssSelector(locator.getProperty("summary.promo.info").toString())).getText());

            // Apply valid coupon
            logger.info("Applying valid coupon");
            driver.findElement(By.id(locator.getProperty("summary.promo.textarea").toString())).clear();
            driver.findElement(By.id(locator.getProperty("summary.promo.textarea").toString())).sendKeys(COUPON_CODE);
            WebElement applyCouponButtonElement = driver
                    .findElement(By.id(locator.getProperty("summary.promo.apply.button").toString()));
            applyCouponButtonElement.click();
            wait.until(ExpectedConditions.refreshed(ExpectedConditions
                    .visibilityOf(driver.findElement(By.xpath(locator.getProperty("summary.title").toString())))));

            if (map.containsKey("applyPromo") && !map.get("applyPromo")) {
                WebElement promocodeValidationMsgElement = null;
                try {
                    promocodeValidationMsgElement = driver
                            .findElement(By.xpath(locator.getProperty("summary.promo.validation.message").toString()));
                    getTestHelper().logAssertion(getClass().getSimpleName(),
                            "Promotional code " + COUPON_CODE + " has been applied.",
                            promocodeValidationMsgElement.getText());
                } catch (NoSuchElementException ne) {

                }

                // Remove promotion
                logger.info("Removing coupon");
                driver.findElement(By.id(locator.getProperty("summary.promo.remove.button").toString())).click();

                // Apply an invalid coupon
                String couponCode = "ABCDEF";
                logger.info("Applying invalid coupon");
                driver.findElement(By.id(locator.getProperty("summary.promo.textarea").toString())).clear();
                driver.findElement(By.id(locator.getProperty("summary.promo.textarea").toString()))
                        .sendKeys(couponCode);
                applyCouponButtonElement = driver
                        .findElement(By.id(locator.getProperty("summary.promo.apply.button").toString()));
                applyCouponButtonElement.click();

                // Assert invalid coupon error message
                promocodeValidationMsgElement = driver
                        .findElement(By.xpath(locator.getProperty("summary.promo.validation.message").toString()));
                getTestHelper().logAssertion(getClass().getSimpleName(), "This promo code is invalid.",
                        promocodeValidationMsgElement.getText());

            }
            testCase.setStatus("PASS");
            tcList.add(testCase);
            logger.info("END verifyPromotion");
        } catch (Exception exp) {
            throw new TatchaException(exp, tcList);
        }
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