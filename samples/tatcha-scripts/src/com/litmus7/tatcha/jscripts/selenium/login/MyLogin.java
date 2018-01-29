package com.litmus7.tatcha.jscripts.selenium.login;

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

import com.litmus7.tatcha.utils.BrowserDriver;

public class MyLogin {
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
	  myAccountLogin(driver,prop);
//	  loginWithFB(driver,prop);
	  myAccountLogout(driver);
  
  }
  
  public void myAccountLogin(WebDriver driver, Properties prop) throws FileNotFoundException, IOException{
	    /** MOC 48 */
	    String USERNAME_VAL = prop.get("login.username").toString();
	    String PASSWORD_VAL = prop.get("login.password").toString();
	    String USERNAME = prop.get("login.username.id").toString();
	    String PASSWORD = prop.get("login.password.id").toString();
	    String LOGIN = prop.get("login.button.id").toString();
	    	    
	    WebElement login_link = driver.findElement(By.cssSelector("a[href*='tatcha/account']"));
	    login_link.click();
	    
	    WebElement login_username = driver.findElement(By.xpath("//*[contains(@id,'"+USERNAME+"')]"));
	    WebElement login_password = driver.findElement(By.xpath("//*[contains(@id,'"+PASSWORD+"')]"));
	    WebElement login_button = driver.findElement(By.name(LOGIN));
	    
    
	    login_username.clear();
	    login_username.sendKeys(USERNAME_VAL);
	    login_password.clear();
	    login_password.sendKeys(PASSWORD_VAL);
	    login_button.click();
	    
	    String LOGIN_VALIDATION_MISMATCH = prop.get("login.validation.mismatch").toString();
	    WebElement login_error = null;
	    boolean ERRORFLAG = true;
	    
	    try{
	    	login_error = driver.findElement(By.cssSelector("div.alert.alert-danger"));
	    }catch(NoSuchElementException ne){
	    	ERRORFLAG = false;
	    }
	    
	    if(ERRORFLAG)
	    	assertNotEquals(LOGIN_VALIDATION_MISMATCH, login_error.getText());
	    
  }

  
  public void loginWithFB(WebDriver driver, Properties prop) throws Exception {
	  /** MOC 51 */		// No validation checks done
	   
	  	WebElement login_link = driver.findElement(By.cssSelector("a[href*='tatcha/account']"));
	    login_link.click();
	    
	    String FB_LOGIN_TITLE = prop.get("login.fb.title").toString();
	    String FB_USERNAME = prop.get("login.fb.username").toString();
	    String FB_PASSWORD = prop.get("login.fb.password").toString();
	    String WELCOME_HI = prop.get("register.welcome").toString();
	    String WELCOME_HELLO = prop.get("login.welcome").toString();
	    
	    driver.findElement(By.linkText(FB_LOGIN_TITLE)).click();
	    driver.findElement(By.id("email")).clear();
	    driver.findElement(By.id("email")).sendKeys(FB_USERNAME);
	    driver.findElement(By.id("pass")).clear();
	    driver.findElement(By.id("pass")).sendKeys(FB_PASSWORD);
	    driver.findElement(By.id("loginbutton")).click();
	    
	    WebElement HI_ELE = driver.findElement(By.cssSelector("h1.text-center"));
	    assertTrue(HI_ELE.getText().contains(WELCOME_HI));
	    WebElement HELLO_ELE = driver.findElement(By.id("dropdownMenu1"));
	    assertTrue(HELLO_ELE.getText().contains(WELCOME_HELLO));

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
