package com.tatcha.jscripts;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

import com.tatcha.utils.BrowserCapabilities;
import com.tatcha.utils.BrowserDriver;

public class Tcheck_Login {
  private WebDriver driver;
  private static String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();

  @Before
  public void setUp() throws Exception {
//    driver = new FirefoxDriver();
	 driver = BrowserDriver.getChromeWebDriver(); 
    baseUrl = "https://demo-na01-tatcha.demandware.net/";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }

  @Test
  public void testTcheckLogin() throws Exception {
    driver.get(baseUrl + "/on/demandware.store/Sites-Site/default/ViewApplication-ProcessLogin");
    driver.findElement(By.name("LoginForm_Login")).clear();
    driver.findElement(By.name("LoginForm_Login")).sendKeys("admin");
    driver.findElement(By.name("LoginForm_Password")).clear();
    driver.findElement(By.name("LoginForm_Password")).sendKeys("Tatcha@1234");
    driver.findElement(By.name("login")).click();
    // ERROR: Caught exception [ERROR: Unsupported command [selectWindow | name=business manaager | ]]
    driver.findElement(By.cssSelector("a.goto-storefront > span.menu-overview-link-text")).click();
    // ERROR: Caught exception [ERROR: Unsupported command [selectWindow | name=business manaager | ]]
    driver.findElement(By.linkText("Tatcha")).click();
    // ERROR: Caught exception [ERROR: Unsupported command [selectWindow | name=business manaager | ]]
    driver.findElement(By.cssSelector("a.goto-storefront > span.menu-overview-link-text")).click();
    // ERROR: Caught exception [ERROR: Unsupported command [selectWindow | name=business manaager | ]]
    // ERROR: Caught exception [ERROR: Unsupported command [waitForPopUp |  | 30000]]
  }
  
  public void checkLogin(WebDriver driver) throws Exception {
	    driver.get(baseUrl + "/on/demandware.store/Sites-Site/default/ViewApplication-ProcessLogin");
	    driver.findElement(By.name("LoginForm_Login")).clear();
	    driver.findElement(By.name("LoginForm_Login")).sendKeys("admin");
	    driver.findElement(By.name("LoginForm_Password")).clear();
	    driver.findElement(By.name("LoginForm_Password")).sendKeys("Tatcha@1234");
	    driver.findElement(By.name("login")).click();
	    // ERROR: Caught exception [ERROR: Unsupported command [selectWindow | name=business manaager | ]]
	    driver.findElement(By.cssSelector("a.goto-storefront > span.menu-overview-link-text")).click();
	    // ERROR: Caught exception [ERROR: Unsupported command [selectWindow | name=business manaager | ]]
	    driver.findElement(By.linkText("Tatcha")).click();
	    // ERROR: Caught exception [ERROR: Unsupported command [selectWindow | name=business manaager | ]]
	    driver.findElement(By.cssSelector("a.goto-storefront > span.menu-overview-link-text")).click();
	    // ERROR: Caught exception [ERROR: Unsupported command [selectWindow | name=business manaager | ]]
	    // ERROR: Caught exception [ERROR: Unsupported command [waitForPopUp |  | 30000]]
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
