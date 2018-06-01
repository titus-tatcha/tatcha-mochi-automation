package com.tatcha.jscripts.myaccount;

import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.tatcha.jscripts.dao.TestCase;
import com.tatcha.jscripts.dao.User;
import com.tatcha.jscripts.helper.TatchaTestHelper;

public class ProfileSettings {
    
    private TatchaTestHelper testHelper = new TatchaTestHelper();
    private final static Logger logger = Logger.getLogger(ProfileSettings.class);
    private TestCase testCase;

    /**
     * Handles the Login and Profile settings of a logged-in user
     * 
     * @param driver
     * @param prop
     * @param locator
     * @param user
     * @throws Exception
     */
    public void verifyProfileSettings(WebDriver driver, Properties prop, Properties locator, Properties data, User user, List<TestCase> tcList) throws Exception {

        logger.info("BEGIN verifyProfileSettings");
        String FUNCTIONALITY = "Verify the profile settings page";
        testCase = new TestCase("TC-21.1", "MOC-NIL", FUNCTIONALITY, "FAIL", "");
        WebDriverWait wait = (WebDriverWait) new WebDriverWait(driver, 5);
        if (getTestHelper().isLoggedIn(driver)) {
            user.setFname(getTestHelper().getName(driver));
            driver.findElement(By.xpath(locator.getProperty("account.profile").toString())).click();

            // Assert Title
            WebElement webElement = driver.findElement(By.cssSelector("h1.text-center"));
            getTestHelper().logAssertion(getClass().getSimpleName(), prop.getProperty("myaccount.item1").toString(), webElement.getText());

            // Assert Account Information
            WebElement accountInfoElement = driver
                    .findElement(By.xpath(locator.getProperty("profile.accountInfo").toString()));
            getTestHelper().logAssertion(getClass().getSimpleName(), prop.getProperty("profile.accountInfo").toString(), accountInfoElement.getText());

            // Assert email id
            String email = user.getEmail();
            int emailLength = 0;
            if (null != email) {
                emailLength = email.length();
            }
            accountInfoElement = driver
                    .findElement(By.xpath(locator.getProperty("profile.accountInfo.email").toString()));
            getTestHelper().logAssertion(getClass().getSimpleName(), user.getEmail(), accountInfoElement.getText().substring(0, emailLength));

            // Assert Profile Information
            WebElement profileInfoElement = driver
                    .findElement(By.xpath(locator.getProperty("profile.profileInfo").toString()));
            getTestHelper().logAssertion(getClass().getSimpleName(), prop.getProperty("profile.profileInfo").toString(), profileInfoElement.getText());

            // Assert first name
            String name = user.getFname();
            int length = 0;
            if (null != name) {
                length = name.length();
            }
            profileInfoElement = driver
                    .findElement(By.xpath(locator.getProperty("profile.profileInfo.name").toString()));
            getTestHelper().logAssertion(getClass().getSimpleName(), name, profileInfoElement.getText().substring(0, length).toUpperCase());

            // Change password
            driver.findElement(By.xpath(locator.getProperty("profile.accountInfo.password.link").toString())).click();
            changePassword(driver, data, locator, user, tcList);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h1.text-center")));

            // Edit email
            driver.findElement(By.cssSelector(locator.getProperty("profile.accountInfo.edit").toString())).click();
            changeEmail(driver, data, locator, tcList);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h1.text-center")));

            // Edit profile information
            driver.findElement(By.xpath(locator.getProperty("profile.profileInfo.edit").toString())).click();
            editProfile(driver, data, locator, tcList);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h1.text-center")));
        }
        testCase.setStatus("PASS");
        tcList.add(testCase);
        logger.info("END verifyProfileSettings");
    }

    /**
     * Handles the Change password of logged in user
     * 
     * @param driver
     * @param prop
     * @param locator
     * @param user
     */
    public void changePassword(WebDriver driver, Properties prop, Properties locator, User user, List<TestCase> tcList) throws Exception {

        logger.info("BEGIN changePassword");
        String FUNCTIONALITY = "Test change password option of profile settings";
        testCase = new TestCase("TC-21.2", "MOC-NIL", FUNCTIONALITY, "FAIL", "");
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
        testCase.setStatus("PASS");
        tcList.add(testCase);
        logger.info("END changePassword");
    }

    /**
     * Handles Edit email id of logged in user
     * 
     * @param driver
     * @param prop
     * @param locator
     */
    public void changeEmail(WebDriver driver, Properties prop, Properties locator, List<TestCase> tcList) throws Exception {
        logger.info("BEGIN changeEmail");
        String FUNCTIONALITY = "Test change email option of profile settings";
        testCase = new TestCase("TC-21.3", "MOC-NIL", FUNCTIONALITY, "FAIL", "");
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
        testCase.setStatus("PASS");
        tcList.add(testCase);
        logger.info("END changeEmail");
    }

    /**
     * Handles edit profile details
     * 
     * @param driver
     * @param prop
     * @param locator
     * @throws Exception
     */
    public void editProfile(WebDriver driver, Properties prop, Properties locator, List<TestCase> tcList) throws Exception {
        logger.info("BEGIN editProfile");
        String FUNCTIONALITY = "Test edit profile option of profile settings";
        testCase = new TestCase("TC-21.4", "MOC-NIL", FUNCTIONALITY, "FAIL", "");
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
                    break;
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
        boolean saveBirthday = Boolean.parseBoolean(prop.getProperty("birthYear.chosen").toString());
        if (saveBirthday && monthEnabled && dayEnabled) {
            WebElement confirmDobSaveButtonElement = driver
                    .findElement(By.id(locator.getProperty("confirmDob.save.button").toString()));
            confirmDobSaveButtonElement.click();
        } else {
            logger.info("Dob not saved");
            wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath(locator.getProperty("confirmDob.modal.title").toString()))));
            WebElement dobCancelButtonElement = driver
                    .findElement(By.xpath(locator.getProperty("confirmDob.cancel.button").toString()));
            actions.moveToElement(dobCancelButtonElement).click(dobCancelButtonElement);

            actions.perform();

            // Select Birth month
            if (birthMonthElement.isEnabled()) {
                month.selectByVisibleText(prop.getProperty("birthMonth.default").toString());
            }

            // Select Birth day
            if (birthDayElement.isEnabled()) {
                day.selectByVisibleText(prop.getProperty("birthDay.default").toString());
            }

            // Select Birth year
            if (birthYearElement.isEnabled()) {
                year.selectByVisibleText(prop.getProperty("birthYear.default").toString());
            }
            
            wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.xpath(locator.getProperty("confirmDob.modal.title").toString()))));
            wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath(locator.getProperty("profile.title").toString()))));

            // Click Save button
            actions.moveToElement(profileSaveButtonElement).click(profileSaveButtonElement);
            actions.perform();
        }
        /*
        WebElement alertMessageElement = null;
        try {
            alertMessageElement = driver.findElement(By.className("alert alert-danger"));
            WebElement cancelButtonElement = driver
                    .findElement(By.className("btn btn-default edit-cancel account-switch-section"));
            cancelButtonElement.click();
        } catch (NoSuchElementException ne) {
            assertTrue(alertMessageElement == null);
        }
        */
        logger.info("END editProfile");
        testCase.setStatus("PASS");
        tcList.add(testCase);
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
