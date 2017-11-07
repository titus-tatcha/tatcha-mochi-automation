package com.litmus7.tatcha.jscripts.selenium.gheader;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import com.litmus7.tatcha.utils.BrowserDriver;

public class Tcheck_MOC_30_TC_02 {
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();

  @Before
  public void setUp() throws Exception {
//    driver = new FirefoxDriver();
    driver = BrowserDriver.getChromeWebDriver();
    baseUrl = "https://52.43.54.174/";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }

  @Test
  public void testMOC30TC02() throws Exception {
    driver.get(baseUrl + "/mocha/tatcha-bootstrap3/src/prototype-wrapper.html");
    driver.findElement(By.xpath("//img[@alt='1280x96']")).click();
    for (int second = 0;; second++) {
    	if (second >= 60) fail("timeout");
    	try { if ("".equals(driver.findElement(By.cssSelector("img.tatcha-logo")).getText())) break; } catch (Exception e) {}
    	Thread.sleep(1000);
    }

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
