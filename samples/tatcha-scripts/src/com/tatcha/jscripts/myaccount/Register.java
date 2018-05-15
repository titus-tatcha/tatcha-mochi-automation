package com.tatcha.jscripts.myaccount;

import java.util.regex.Pattern;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

import com.tatcha.utils.BrowserDriver;

public class Register {
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();
  private Properties prop = null;
  
  @Before
  public void setUp() throws Exception {
	  prop = new Properties();
	  prop.load(new FileInputStream(getClass().getResource("/tatcha.properties").getFile()));
	  
//    driver = new FirefoxDriver();
	driver = BrowserDriver.getChromeWebDriver();
//    baseUrl = "https://demo-na01-tatcha.demandware.net/";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }

  @Test
  public void testMyLogin() throws Exception {
  
	  driver.get(BrowserDriver.BASE_URL);
	  registerAccount(driver,prop);			
	  myAccountLogout(driver);
  
  }
  
  
  public void registerAccount(WebDriver driver, Properties prop){
	    /** MOC 53*/			// No validation checks done
	  	WebElement login_link = driver.findElement(By.cssSelector("a[href*='tatcha/account']"));
	    login_link.click();
	    
	    String WELCOME_MSG = prop.get("register.welcome").toString();
	    String FIRST_NAME = prop.get("register.fname").toString();
	    String LAST_NAME = prop.get("register.lname").toString();
	    String PASSWORD = prop.get("register.password").toString();
	    String EMAIL_ID = "qa.tatcha@gmail.com";
	    		
	  	driver.findElement(By.name("dwfrm_login_register")).click();
	    driver.findElement(By.id("dwfrm_profile_customer_firstname")).clear();
	    driver.findElement(By.id("dwfrm_profile_customer_firstname")).sendKeys(FIRST_NAME);
	    driver.findElement(By.id("dwfrm_profile_customer_lastname")).clear();
	    driver.findElement(By.id("dwfrm_profile_customer_lastname")).sendKeys(LAST_NAME);
	    driver.findElement(By.id("dwfrm_profile_login_password")).clear();
	    driver.findElement(By.id("dwfrm_profile_login_password")).sendKeys(PASSWORD);
	    driver.findElement(By.id("dwfrm_profile_customer_email")).clear();
	    driver.findElement(By.id("dwfrm_profile_customer_email")).sendKeys(EMAIL_ID);
	    driver.findElement(By.id("dwfrm_profile_login_passwordconfirm")).clear();
	    driver.findElement(By.id("dwfrm_profile_login_passwordconfirm")).sendKeys(PASSWORD);
	    driver.findElement(By.name("dwfrm_profile_confirm")).click();
	    assertEquals(WELCOME_MSG+" "+FIRST_NAME.toUpperCase(), driver.findElement(By.cssSelector("h1.text-center")).getText());
  }
  
  public void myAccountLogout(WebDriver driver) throws Exception {
	    driver.findElement(By.id("dropdownMenu1")).click();
	    driver.findElement(By.linkText("Logout")).click();
	    WebElement login_link = driver.findElement(By.cssSelector("a[href*='tatcha/account']"));
	    assert(login_link.isDisplayed());
	  }
  

  @After
  public void tearDown() throws Exception {
    driver.quit();
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      fail(verificationErrorString);
    }
  }

  private boolean isElementPresent(By by) {
    try {
      driver.findElement(by);
      return true;
    } catch (NoSuchElementException e) {
      return false;
    }
  }

  private boolean isAlertPresent() {
    try {
      driver.switchTo().alert();
      return true;
    } catch (NoAlertPresentException e) {
      return false;
    }
  }

  private String closeAlertAndGetItsText() {
    try {
      Alert alert = driver.switchTo().alert();
      String alertText = alert.getText();
      if (acceptNextAlert) {
        alert.accept();
      } else {
        alert.dismiss();
      }
      return alertText;
    } finally {
      acceptNextAlert = true;
    }
  }
}
