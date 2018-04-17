package com.litmus7.tatcha.jscripts.selenium.sprint3;

import static org.junit.Assert.assertEquals;

import java.util.Properties;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AddressBook {
    
    public static final String COUNTRY_US = "United States";
    private LoginHelper loginHelper = new LoginHelper();
    private final static Logger logger = Logger.getLogger(AddressBook.class);

    /**
     * Handles address book
     * 
     * @param driver
     * @param prop
     * @param locator
     * @throws Exception
     */
    public void verifyAddressBook(WebDriver driver, Properties prop, Properties locator) throws Exception {
        if (getLoginHelper().isLoggedIn(driver)) {
            driver.findElement(By.xpath(locator.getProperty("account.address").toString())).click();

            // Assert Title
            WebElement webElement = driver.findElement(By.cssSelector("h1.text-center"));
            assertEquals(prop.getProperty("myaccount.item4").toString(), webElement.getText());

            // Assert Default address
            WebElement defaultAddressElement = driver
                    .findElement(By.xpath(locator.getProperty("address.default").toString()));
            assertEquals(prop.getProperty("myaccount.item4.title2").toString(), defaultAddressElement.getText());

            // Assert Add address
            WebElement addAddressElement = driver.findElement(By.xpath(locator.getProperty("address.add").toString()));
            assertEquals(prop.getProperty("myaccount.item4.title3").toString(), addAddressElement.getText());

            // Add Address to address book
            driver.findElement(By.xpath(locator.getProperty("address.add.button").toString())).click();
            addAddress(driver, prop, locator, false);
            logger.info("Address Added");

            // Add Address to address book, which will become the default
            // address
            driver.findElement(By.xpath(locator.getProperty("address.add.button").toString())).click();
            addAddress(driver, prop, locator, false);
            logger.info("Address Added");

            // Edit default address
            driver.findElement(By.xpath(locator.getProperty("address.default.edit").toString())).click();
            addAddress(driver, prop, locator, true);
            logger.info("Address Edited");

            // Edit an address in address book
            driver.findElement(By.xpath(locator.getProperty("address.edit").toString())).click();
            addAddress(driver, prop, locator, false);
            logger.info("Address Edited");

            // Make an address the default address
            WebElement makeDefaultButtonElement = null;
            makeDefaultButtonElement = driver
                    .findElement(By.xpath(locator.getProperty("address.makeDefault").toString()));
            makeDefaultButtonElement.click();
            logger.info("Address set as default");

            // Make an address the default address
            makeDefaultButtonElement = driver
                    .findElement(By.xpath(locator.getProperty("address.makeDefault").toString()));
            makeDefaultButtonElement.click();
            logger.info("Address set as default");

            // Remove default address from address book
            removeAddress(driver, prop, locator);
            logger.info("Address removed");
        }
    }

    /**
     * Add an address to the address book
     * 
     * @param driver
     * @param prop
     * @param locator
     * @param isEditDefaultAddress
     */
    public void addAddress(WebDriver driver, Properties prop, Properties locator, boolean isEditDefaultAddress) {

        boolean isAddAddress = false;
        WebElement titleElement = driver
                .findElement(By.xpath("//*[@id='ext-gen44']/body/main/div/div/div/div/div/div/div[1]/h4"));
        String title = titleElement.getText();
        if ("ADD ADDRESS".equalsIgnoreCase(title)) {
            isAddAddress = true;
        }
        
        populateAddressBook(driver, prop, locator, isEditDefaultAddress, isAddAddress);
        
        // Save address
        WebElement addAddressSaveButtonElement = driver
                .findElement(By.xpath(locator.getProperty("addAddr.save.button").toString()));
        addAddressSaveButtonElement.click();
    }

    /**
     * Populate the fields of address book
     * 
     * @param driver
     * @param prop
     * @param locator
     * @param isEditDefaultAddress
     * @param isAddAddress
     */
    public String populateAddressBook(WebDriver driver, Properties prop, Properties locator,
            boolean isEditDefaultAddress, boolean isAddAddress) {
        
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
        WebElement addressIdElement = driver.findElement(By.id("dwfrm_profile_address_addressid"));
        String addressId = addressIdElement.getAttribute("value");
        logger.info("Id of the Address added : "+addressId);

        // Populate first and last name
        addAddrFirstNameElement.clear();
        addAddrFirstNameElement.sendKeys(prop.getProperty("addressbook.fname").toString() + addressId);
        if (isAddAddress) {
            addAddrLastNameElement.clear();
            addAddrLastNameElement.sendKeys(prop.getProperty("addressbook.lname").toString());
        } else {
            addAddrLastNameElement.clear();
            addAddrLastNameElement.sendKeys("EDITED");
        }

        String countryValue = null;
        if (isAddAddress) {
            // Select Country
            Select country = new Select(addAddrCountryElement);
            countryValue = prop.getProperty("addressbook.country").toString();
            if (addAddrCountryElement.isEnabled()) {
                country.selectByVisibleText(countryValue);
            }

            // Populate address1 and address2
            addAddr1Element.clear();
            addAddr1Element.sendKeys(prop.getProperty("addressbook.addr1").toString());
            addAddr2Element.clear();
            addAddr2Element.sendKeys(prop.getProperty("addressbook.addr2").toString());
        }
        
        // Populate zip code and city
        addAddrZipCodeElement.clear();
        addAddrZipCodeElement.sendKeys(prop.getProperty("addressbook.pin").toString());
        wait.until(ExpectedConditions.textToBePresentInElementValue(addAddrZipCodeElement, prop.getProperty("addressbook.pin").toString()));
        WebElement addAddrCityElement = driver.findElement(By.xpath(locator.getProperty("addAddr.city").toString()));
        if(addAddrCityElement.getAttribute("value").isEmpty()) {
            addAddrCityElement.clear();
            addAddrCityElement.sendKeys(prop.getProperty("addressbook.city").toString());
        }

        WebElement addAddrStateElement = null;
        if (COUNTRY_US.equals(countryValue)) {
            addAddrStateElement = driver.findElement(By.xpath(locator.getProperty("addAddr.states").toString()));
            // Select State if country is US
            Select state = new Select(addAddrStateElement);
            if (!addAddrStateElement.isSelected()) {
                state.selectByVisibleText(prop.getProperty("addressbook.state").toString());
            }
        } else {
            addAddrStateElement = driver.findElement(By.xpath(locator.getProperty("addAddr.states").toString()));
            if(addAddrStateElement.getAttribute("value").isEmpty()) {
                // Enter state if country is not US
                addAddrStateElement.clear();
                addAddrStateElement.sendKeys(prop.getProperty("addressbook.state").toString());
            }
        }

        if (isAddAddress) {
            // Populate phone no.
            addAddrPhoneElement.clear();
            addAddrPhoneElement.sendKeys(prop.getProperty("addressbook.phone").toString());

            if (!isEditDefaultAddress) {
                WebElement addAddrCheckBoxElement = driver
                        .findElement(By.xpath(locator.getProperty("addAddr.checkbox").toString()));
                // Mark as default address
                if (!addAddrCheckBoxElement.isSelected()) {
                    addAddrCheckBoxElement.click();
                }
            }
        }
        return addressId;
    }

    /**
     * Remove an address from address book
     * 
     * @param driver
     * @param prop
     * @param locator
     */
    public void removeAddress(WebDriver driver, Properties prop, Properties locator) {
        WebDriverWait wait = (WebDriverWait) new WebDriverWait(driver, 5);
        WebElement removeIconElement = driver.findElement(By.xpath(locator.getProperty("address.remove").toString()));

        // Click 'X' to delete address
        removeIconElement.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locator.getProperty("removeAddr.confirm.title").toString())));

        WebElement confirmModalDialogeTitleElement = driver
                .findElement(By.xpath(locator.getProperty("removeAddr.confirm.title").toString()));
        
        // Assert the title of dialogue box
        assertEquals("ARE YOU SURE?", confirmModalDialogeTitleElement.getText());
        wait.until(ExpectedConditions.elementToBeClickable(By.linkText("DELETE")));
        WebElement confirmDeleteElement = driver
                .findElement(By.cssSelector(locator.getProperty("removeAddr.confirm.button").toString()));
              
        // Confirm delete address
        confirmDeleteElement.click();
        wait.until(ExpectedConditions.invisibilityOf(confirmModalDialogeTitleElement));
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
