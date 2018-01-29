package com.litmus7.tatcha.jscripts.selenium.sprint3;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.litmus7.tatcha.jscripts.dob.User;

public class ProfileSettings {
    
    private LoginHelper loginHelper = new LoginHelper();

    /**
     * Handles the Login and Profile settings of a logged-in user
     * 
     * @param driver
     * @param prop
     * @param locator
     * @throws Exception
     */
    public void profileSettings(WebDriver driver, Properties prop, Properties locator, User user) throws Exception {

        WebDriverWait wait = (WebDriverWait) new WebDriverWait(driver, 5);
        if (getLoginHelper().isLoggedIn(driver)) {
            user.setFname(getLoginHelper().getName(driver));
            driver.findElement(By.xpath(locator.getProperty("account.profile").toString())).click();

            // Assert Title
            WebElement webElement = driver.findElement(By.cssSelector("h1.text-center"));
            assertEquals(prop.getProperty("myaccount.item1").toString(), webElement.getText());

            // Assert Account Information
            WebElement accountInfoElement = driver
                    .findElement(By.xpath(locator.getProperty("profile.accountInfo").toString()));
            assertEquals(prop.getProperty("profile.accountInfo").toString(), accountInfoElement.getText());

            // Assert email id
            String email = user.getEmail();
            int emailLength = 0;
            if (null != email) {
                emailLength = email.length();
            }
            accountInfoElement = driver
                    .findElement(By.xpath(locator.getProperty("profile.accountInfo.email").toString()));
            assertEquals(user.getEmail(), accountInfoElement.getText().substring(0, emailLength));

            // Assert Profile Information
            WebElement profileInfoElement = driver
                    .findElement(By.xpath(locator.getProperty("profile.profileInfo").toString()));
            assertEquals(prop.getProperty("profile.profileInfo").toString(), profileInfoElement.getText());

            // Assert first name
            String name = user.getFname();
            int length = 0;
            if (null != name) {
                length = name.length();
            }
            profileInfoElement = driver
                    .findElement(By.xpath(locator.getProperty("profile.profileInfo.name").toString()));
            assertEquals(name, profileInfoElement.getText().substring(0, length).toUpperCase());

            // Change password
            driver.findElement(By.xpath(locator.getProperty("profile.accountInfo.password.link").toString())).click();
            changePassword(driver, prop, locator, user);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h1.text-center")));

            // Edit email
            driver.findElement(By.cssSelector(locator.getProperty("profile.accountInfo.edit").toString())).click();
            changeEmail(driver, prop, locator);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h1.text-center")));

            // Edit profile information
            driver.findElement(By.xpath(locator.getProperty("profile.profileInfo.edit").toString())).click();
            editProfile(driver, prop, locator);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h1.text-center")));
        }
    }

    /**
     * Handles the Change password of logged in user
     * 
     * @param driver
     * @param prop
     * @param locator
     */
    public void changePassword(WebDriver driver, Properties prop, Properties locator, User user) {

        // Get the web elements
        WebElement currentPasswordElement = driver
                .findElement(By.name(locator.getProperty("password.current").toString()));
        WebElement newPasswordElement = driver.findElement(By.name(locator.getProperty("password.new").toString()));
        WebElement confirmPasswordElement = driver
                .findElement(By.name(locator.getProperty("password.confirm").toString()));
        WebElement passwordSaveButtonElement = driver
                .findElement(By.name(locator.getProperty("password.save.button").toString()));

        // Populate test data and click login
        currentPasswordElement.clear();
        currentPasswordElement.sendKeys(user.getPassword());
        newPasswordElement.clear();
        newPasswordElement.sendKeys(prop.getProperty("password.new").toString());
        confirmPasswordElement.clear();
        confirmPasswordElement.sendKeys(prop.getProperty("password.new").toString());
        passwordSaveButtonElement.click();

        WebElement alertMessageElement = null;
        try {
            alertMessageElement = driver.findElement(By.className("alert alert-danger"));
            WebElement cancelButtonElement = driver
                    .findElement(By.className("btn btn-default edit-cancel account-switch-section"));
            cancelButtonElement.click();
        } catch (NoSuchElementException ne) {
            assertTrue(alertMessageElement == null);
        }
    }

    /**
     * Handles Edit email id of logged in user
     * 
     * @param driver
     * @param prop
     * @param locator
     */
    public void changeEmail(WebDriver driver, Properties prop, Properties locator) {

        // Get the web elements
        WebElement newEmailElement = driver.findElement(By.name(locator.getProperty("email.new").toString()));
        WebElement confirmEmailElement = driver.findElement(By.name(locator.getProperty("email.confirm").toString()));
        WebElement emailSaveButtonElement = driver
                .findElement(By.name(locator.getProperty("email.save.button").toString()));

        // Populate test data and click login
        newEmailElement.clear();
        newEmailElement.sendKeys(prop.getProperty("email.new").toString());
        confirmEmailElement.clear();
        confirmEmailElement.sendKeys(prop.getProperty("email.new").toString());
        emailSaveButtonElement.click();

        WebElement alertMessageElement = null;
        try {
            alertMessageElement = driver.findElement(By.className("alert alert-danger"));
            WebElement cancelButtonElement = driver
                    .findElement(By.className("btn btn-default edit-cancel account-switch-section"));
            cancelButtonElement.click();
        } catch (NoSuchElementException ne) {
            assertTrue(alertMessageElement == null);
        }
    }

    /**
     * Handles edit profile details
     * 
     * @param driver
     * @param prop
     * @param locator
     * @throws Exception
     */
    public void editProfile(WebDriver driver, Properties prop, Properties locator) throws Exception {
        WebDriverWait wait = (WebDriverWait) new WebDriverWait(driver, 10);
        boolean monthEnabled = false, dayEnabled = false;
        // Get the web elements
        WebElement firstNameElement = driver.findElement(By.name(locator.getProperty("profile.firstName").toString()));
        WebElement lastNameElement = driver.findElement(By.name(locator.getProperty("profile.lastName").toString()));
        List<WebElement> skinTypeElements = driver
                .findElements(By.name(locator.getProperty("profile.skinType").toString()));
        WebElement profileSaveButtonElement = driver
                .findElement(By.xpath(locator.getProperty("profile.save.button").toString()));
        WebElement birthMonthElement = driver
                .findElement(By.xpath(locator.getProperty("profile.birthMonth").toString()));
        WebElement birthDayElement = driver.findElement(By.xpath(locator.getProperty("profile.birthDay").toString()));
        WebElement birthYearElement = driver.findElement(By.xpath(locator.getProperty("profile.birthYear").toString()));

        // Populate first and last name
        firstNameElement.clear();
        firstNameElement.sendKeys(prop.getProperty("firstName.new").toString());
        lastNameElement.clear();
        lastNameElement.sendKeys(prop.getProperty("lastName.new").toString());

        // Select a skin type
        String skinType = prop.getProperty("skinType.chosen").toString();
        for (int i = 0; i < skinTypeElements.size(); i++) {
            if (null != skinTypeElements.get(i) && null != skinType
                    && skinType.equalsIgnoreCase((skinTypeElements.get(i)).getAttribute("value"))) {
                if (!skinTypeElements.get(i).isSelected()) {
                    skinTypeElements.get(i).click();
//                    wait.until(ExpectedConditions.elementToBeSelected(skinTypeElements.get(i)));
                    wait.until(ExpectedConditions.elementSelectionStateToBe(skinTypeElements.get(i), true));
                }
            }
        }

        // Select Birth month
        Select month = new Select(birthMonthElement);
        if (birthMonthElement.isEnabled()) {
            month.selectByVisibleText(prop.getProperty("birthMonth.chosen").toString());
            monthEnabled = true;
        }

        // Select Birth day
        Select day = new Select(birthDayElement);
        if (birthDayElement.isEnabled()) {
            day.selectByVisibleText(prop.getProperty("birthDay.chosen").toString());
            dayEnabled = true;
        }

        // Select Birth year
        Select year = new Select(birthYearElement);
        if (birthYearElement.isEnabled()) {
            year.selectByVisibleText(prop.getProperty("birthYear.chosen").toString());
        }

        // Click Save button
        Actions actions = new Actions(driver);
        actions.moveToElement(profileSaveButtonElement).click(profileSaveButtonElement);
        actions.perform();

        // If birth day or month is edited
        if (monthEnabled && dayEnabled) {
            WebElement confirmDobSaveButtonElement = driver
                    .findElement(By.id(locator.getProperty("confirmDob.save.button").toString()));
            confirmDobSaveButtonElement.click();
        }
        /*
        WebElement alertMessageElement = null;
        try {
            alertMessageElement = driver.findElement(By.className("alert alert-danger"));
            System.out.println("Step11");
            WebElement cancelButtonElement = driver
                    .findElement(By.className("btn btn-default edit-cancel account-switch-section"));
            System.out.println("Step12");
            cancelButtonElement.click();
            System.out.println("Step13");
        } catch (NoSuchElementException ne) {
            assertTrue(alertMessageElement == null);
        }
        */
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
