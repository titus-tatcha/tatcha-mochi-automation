package com.litmus7.tatcha.jscripts.selenium;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

import com.litmus7.tatcha.utils.BrowserDriver;

public class TsignUp_TC_07 {
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();

  @Before
  public void setUp() throws Exception {
//    driver = new FirefoxDriver();
    driver = BrowserDriver.getChromeWebDriver();
    baseUrl = "https://dev05-na01-tatcha.demandware.net/s/SiteGenesis/home?lang=en_US";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }

  @Test
  public void testTsignUpTC07() throws Exception {
    driver.get(baseUrl + "/s/SiteGenesis/register?lang=en_US");
    driver.findElement(By.cssSelector("i.fa.fa-user")).click();
    driver.findElement(By.linkText("Register")).click();
    driver.findElement(By.xpath("//div/input")).clear();
    driver.findElement(By.xpath("//div/input")).sendKeys("Neetu");
    driver.findElement(By.xpath("//div[7]/button")).click();
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
