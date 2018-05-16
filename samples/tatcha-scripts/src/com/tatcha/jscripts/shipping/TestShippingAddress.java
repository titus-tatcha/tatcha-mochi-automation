package com.tatcha.jscripts.shipping;


import java.io.FileInputStream;
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

import com.tatcha.jscripts.dao.TestCase;
import com.tatcha.jscripts.dao.User;
import com.tatcha.jscripts.helper.TatchaTestHelper;
import com.tatcha.jscripts.myaccount.AddressBook;

/**
 * Class that checks Adding/Selecting an address in
 * Checkout - Shipping page
 * 
 * @author reshma
 *
 */
public class TestShippingAddress {
    
    private TatchaTestHelper testHelper = new TatchaTestHelper();
    private final static Logger logger = Logger.getLogger(TestShippingAddress.class);
    public static final String COUNTRY_US = "United States";
    private TestCase testCase;

    /**
     * Add the First address in Checkout - Shipping page
     * 
     * @param driver
     * @param prop
     * @param locator
     * @param user
     * @param tcList
     * @return
     * @throws Exception
     */
    public String testAddFirstShippingAddress(WebDriver driver, Properties prop, Properties locator, User user, List<TestCase> tcList) throws Exception {
        String addressId = null;
        try {
            logger.info("BEGIN testAddFirstShippingAddress");
            String FUNCTIONALITY = "Populate shipping address fields of thr first address";
            testCase = new TestCase("TC-15.1", "MOC-NIL", FUNCTIONALITY, "FAIL", "");
            
            WebDriverWait wait = (WebDriverWait) new WebDriverWait(driver, 5);

            // Get all the web elements in the address book
            WebElement addAddrFirstNameElement = driver
                    .findElement(By.xpath(locator.getProperty("addAddr.firstName").toString()));
            WebElement addAddrLastNameElement = driver
                    .findElement(By.xpath(locator.getProperty("addAddr.lastName").toString()));
            WebElement addAddrCountryElement = driver
                    .findElement(By.xpath(locator.getProperty("addAddr.country").toString()));
            WebElement addAddr1Element = driver.findElement(By.xpath(locator.getProperty("addAddr.addr1").toString()));
            WebElement addAddr2Element = driver.findElement(By.xpath(locator.getProperty("addAddr.addr2").toString()));
            WebElement addAddrZipCodeElement = driver
                    .findElement(By.xpath(locator.getProperty("addAddr.zipCode").toString()));
            WebElement addAddrPhoneElement = driver.findElement(By.xpath(locator.getProperty("addAddr.phone").toString()));

            // Get Address Id
            try {
                WebElement addressIdElement = driver.findElement(By.id("dwfrm_profile_address_addressid"));
                addressId = addressIdElement.getAttribute("value");
                logger.info("Id of the Address added : "+addressId);
            } catch(NoSuchElementException ne) {
                logger.info("Guest user is trying to checkout, hence no address id");
            }
            // Populate first and last name
            if(addAddrFirstNameElement.getAttribute("value").isEmpty()) {
                addAddrFirstNameElement.clear();
                addAddrFirstNameElement.sendKeys(prop.getProperty("addressbook.fname").toString() + addressId);
            }
            if(addAddrLastNameElement.getAttribute("value").isEmpty()) {
                addAddrLastNameElement.clear();
                addAddrLastNameElement.sendKeys(prop.getProperty("addressbook.lname").toString());
            }
            
            // Select Country
            logger.info("Populate country");
            List<WebElement> shippingOptionsListElement = driver.findElements(By.name(locator.getProperty("shipping.optionList").toString()));
            String countryValue = null;
            Select country = new Select(addAddrCountryElement);
            countryValue = prop.getProperty("addressbook.country").toString();
            if (!addAddrCountryElement.isSelected()) {
                country.selectByVisibleText(countryValue);
                if(!COUNTRY_US.equalsIgnoreCase(countryValue)) {
                    try {
                        wait.until(ExpectedConditions.stalenessOf(shippingOptionsListElement.get(0)));
                    } catch(TimeoutException te) {
                        logger.info("Shipping Option is modified");
                    }
                } 
                logger.info("Country selected");
            }

            // Populate address1 and address2
            addAddr1Element.clear();
            addAddr1Element.sendKeys(prop.getProperty("addressbook.addr1").toString());
            addAddr2Element.clear();
            addAddr2Element.sendKeys(prop.getProperty("addressbook.addr2").toString());
            
            // Populate zip code and city
            logger.info("populate zipcode and city");
            addAddrZipCodeElement.clear();
            addAddrZipCodeElement.sendKeys(prop.getProperty("addressbook.pin").toString());
            wait.until(ExpectedConditions.textToBePresentInElementValue(addAddrZipCodeElement, prop.getProperty("addressbook.pin").toString()));
            WebElement addAddrCityElement = driver.findElement(By.xpath(locator.getProperty("addAddr.city").toString()));
            if(addAddrCityElement.getAttribute("value").isEmpty()) {
                addAddrCityElement.clear();
                addAddrCityElement.sendKeys(prop.getProperty("addressbook.city").toString());
            }

            logger.info("populate state");
            WebElement addAddrStateElement = null;
            if (COUNTRY_US.equals(countryValue)) {
                logger.info("Country is US");
                addAddrStateElement = driver.findElement(By.xpath(locator.getProperty("addAddr.states").toString()));
                // Select State if country is US
                Select state = new Select(addAddrStateElement);
                if (!addAddrStateElement.isSelected()) {
                    logger.info("select state");
                    try {
                        wait.until(ExpectedConditions.textToBe(By.xpath(locator.getProperty("addAddr.states").toString()), prop.getProperty("addressbook.state").toString()));
                    } catch(TimeoutException te) {
                        logger.info("State was not auto-populated");
                        state.selectByVisibleText(prop.getProperty("addressbook.state").toString());
                    }
                }
            } else {
                logger.info("Country is not US");
                addAddrStateElement = driver.findElement(By.xpath(locator.getProperty("addAddr.states").toString()));
                if(addAddrStateElement.getAttribute("value").isEmpty()) {
                    // Enter state if country is not US
                    addAddrStateElement.clear();
                    addAddrStateElement.sendKeys(prop.getProperty("addressbook.state").toString());
                }
            }

            // Populate phone no.
            logger.info("populate phone no");
            addAddrPhoneElement.clear();
            addAddrPhoneElement.sendKeys(prop.getProperty("addressbook.phone").toString());
            
            testCase.setStatus("PASS");
            tcList.add(testCase);
            logger.info("END testAddFirstShippingAddress");
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
        return addressId;
    }
    
    /**
     * Add a new address in Checkout - Shipping page
     * 
     * @param driver
     * @param prop
     * @param locator
     * @param user
     * @param tcList
     * @throws Exception
     */
    public void testAddShippingAddress(WebDriver driver, Properties prop, Properties locator, User user, List<TestCase> tcList) throws Exception {
        try {
            logger.info("BEGIN testAddShippingAddress");
            String FUNCTIONALITY = "Populate the shipping address fields";
            testCase = new TestCase("TC-15.2", "MOC-NIL", FUNCTIONALITY, "FAIL", "");
            
            AddressBook addrBook = new AddressBook();
            WebDriverWait wait = (WebDriverWait) new WebDriverWait(driver, 10);
            Actions actions = new Actions(driver);
            Properties myAccountLocator = new Properties();
            myAccountLocator.load(new FileInputStream(getClass().getResource("/myAccountElementLocator.properties").getFile()));
            int index = 0;
            
            List<WebElement> addressListWebElement = driver.findElements(By.name(locator.getProperty("shipping.addressList").toString()));
            index = addressListWebElement.size()+1;
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath(locator.getProperty("shipping.addAddr.button").toString()+index+"]/a")));
            
//            addAddressButtonElement = driver.findElement(By.xpath(locator.getProperty("shipping.addAddr.button").toString()+index+"]/a"));
            WebElement addAddressButtonElement = driver.findElement(By.linkText("ADD ADDRESS"));
            actions.moveToElement(addAddressButtonElement).click(addAddressButtonElement);
            actions.perform();
            try {
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locator.getProperty("shipping.addAddr.title").toString())));
            } catch(TimeoutException te) {
                logger.info("Hack : Add address button was not clicked");
                addAddressButtonElement.click();
            }
            logger.info("Add a new shipping address for test");
            addrBook.populateAddressBook(driver, prop, myAccountLocator, true, true, tcList);
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath(locator.getProperty("shipping.addAddr.save.button").toString())));
            driver.findElement(By.xpath(locator.getProperty("shipping.addAddr.save.button").toString())).click();
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(locator.getProperty("shipping.addAddr.title").toString())));
            
            testCase.setStatus("PASS");
            tcList.add(testCase);
            logger.info("END testAddShippingAddress");
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
     * Select an existing address in Checkout - Shipping page
     * 
     * @param driver
     * @param prop
     * @param locator
     * @param user
     * @param tcList
     * @throws Exception
     */
    public void testSelectShippingAddress(WebDriver driver, Properties prop, Properties locator, User user, List<TestCase> tcList) throws Exception {
        try {
            logger.info("BEGIN testSelectShippingAddress");
            String FUNCTIONALITY = "Select a shipping address from the list";
            testCase = new TestCase("TC-15.3", "MOC-NIL", FUNCTIONALITY, "FAIL", "");
            
            WebDriverWait wait = (WebDriverWait) new WebDriverWait(driver, 10);
            Actions actions = new Actions(driver);
            
            logger.info("Get all the shipping address");
            List<WebElement> addressListWebElement = driver.findElements(By.name(locator.getProperty("shipping.addressList").toString()));
            
            logger.info("Select a shipping address from "+addressListWebElement.size()+" addresses");
            if(addressListWebElement.size() > 0) {
                // Select an existing address
                if(addressListWebElement.size() >= 2) {
                    logger.info("Select 2nd address");
                    actions.moveToElement(addressListWebElement.get(1)).click(addressListWebElement.get(1));
                    actions.perform();
                    wait.until(ExpectedConditions.invisibilityOf(addressListWebElement.get(1)));
                } else {
                    logger.info("Select 1st address");
                    actions.moveToElement(addressListWebElement.get(0)).click(addressListWebElement.get(0));
                    actions.perform();
                    wait.until(ExpectedConditions.invisibilityOf(addressListWebElement.get(0)));
                }
            } 
            
            testCase.setStatus("PASS");
            tcList.add(testCase);
            logger.info("END testSelectShippingAddress");
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
