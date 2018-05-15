package com.tatcha.jscripts.myaccount;

import java.util.regex.Pattern;
import java.io.FileInputStream;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

import com.tatcha.utils.BrowserDriver;

public class PaymentOptions {
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();
  private Properties prop = null;
  
  @Before
  public void setUp() throws Exception {
//    driver = new FirefoxDriver();
//    baseUrl = "https://demo-na01-tatcha.demandware.net/";
	  driver = BrowserDriver.getChromeWebDriver();
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }

  @Test
  public void testPaymentOptions() throws Exception {
//    driver.get(baseUrl + "/s/tatcha/wallet?lang=default");
	  driver.get(BrowserDriver.BASE_URL);
	  prop = new Properties();
	  prop.load(new FileInputStream(getClass().getResource("/tatcha.properties").getFile()));
	 
//    driver.findElement(By.linkText("View Your Account")).click();
//    driver.findElement(By.xpath("//li[5]/a/div/p")).click();
//    assertEquals("Payment Options", driver.findElement(By.cssSelector("h1.text-center")).getText());
//    assertEquals("Default Card", driver.findElement(By.cssSelector("h5.panel-title")).getText());
//    assertEquals("Add A Credit Card", driver.findElement(By.cssSelector("div.panel-body > p")).getText());
    
    MyLogin myLogin = new MyLogin();
    myLogin.myAccountLogin(driver, prop);
	    
    MyAccount_MOC_16 myAccount = new MyAccount_MOC_16();
    myAccount.checkPaymentOptions(driver,prop);
    
    /** Click on Add A Credit Card */
//    driver.findElement(By.cssSelector("p")).click();
    driver.findElement(By.xpath("//*[@id='ext-gen44']/body/main/div/div/div/div/div[2]/a/div/div/p")).click();
    
    // ERROR: Caught exception [Error: locator strategy either id or name must be specified explicitly.]
    driver.findElement(By.id("braintreeCardOwner")).clear();
    driver.findElement(By.id("braintreeCardOwner")).sendKeys("QA MC");
//    driver.findElement(By.id("//*[@id='braintreeCardOwner']")).clear();
//    driver.findElement(By.id("//*[@id='braintreeCardOwner']")).sendKeys("QA MC");
//    driver.findElement(By.id("credit-card-number")).clear();
//    driver.findElement(By.id("credit-card-number")).sendKeys("2223000048400011");
//    driver.findElement(By.xpath("//*[@id='credit-card-number']")).clear();
//    driver.findElement(By.xpath("//*[@id='credit-card-number']")).sendKeys("2223000048400011");
//    driver.findElement(By.cssSelector("#credit-card-number")).clear();
//    driver.findElement(By.cssSelector("#credit-card-number")).sendKeys("2223000048400011");
  //*[@id="braintreeCardOwner"]
//    #credit-card-number
    driver.findElement(By.id("cvv")).clear();
    driver.findElement(By.id("cvv")).sendKeys("121");
//  //*[@id="cvv"]
//    #cvv
//  //*[@id="expiration"]
//    #expiration
    driver.findElement(By.id("expiration")).clear();
    driver.findElement(By.id("expiration")).sendKeys("12 / 2021");

    driver.findElement(By.id("braintreeCreditCardMakeDefault")).click();
    driver.findElement(By.id("braintreeCreditCardMakeDefault")).click();
    
    driver.findElement(By.xpath("//button[@type='submit']")).click();
    
    // Default Card
    assertEquals("Default Card", driver.findElement(By.cssSelector("h5.panel-title")).getText());
    assertEquals("0011", driver.findElement(By.cssSelector("dd.data-value")).getText());
    assertEquals("12 / 21", driver.findElement(By.xpath("//html[@id='ext-gen45']/body/main/div/div/div/div/div/div/div[2]/div[2]/dl/dd[2]")).getText());
    
    // Add a Credit Card
    assertEquals("Add A Credit Card", driver.findElement(By.cssSelector("h4.panel-title")).getText());
    assertEquals("Name on Card", driver.findElement(By.xpath("//div[@id='braintreeCreditCardFieldsContainer']/div/label/span[2]")).getText());
    driver.findElement(By.id("braintreeCardOwner")).clear();
    driver.findElement(By.id("braintreeCardOwner")).sendKeys("Qa Visa");
    assertEquals("Card Number", driver.findElement(By.xpath("//div[@id='braintreeCreditCardFieldsContainer']/div[2]/label/span[2]")).getText());
    // ERROR: Caught exception [ERROR: Unsupported command [selectFrame | braintree-hosted-field-number | ]]
    driver.findElement(By.id("credit-card-number")).clear();
    driver.findElement(By.id("credit-card-number")).sendKeys("4009 3488 8888 1881");
    assertEquals("CVV", driver.findElement(By.xpath("//div[@id='braintreeCreditCardFieldsContainer']/div[3]/label/span[2]")).getText());
    // ERROR: Caught exception [ERROR: Unsupported command [selectFrame | braintree-hosted-field-cvv | ]]
    driver.findElement(By.id("cvv")).clear();
    driver.findElement(By.id("cvv")).sendKeys("202");
    assertEquals("Expiration", driver.findElement(By.xpath("//div[@id='braintreeCreditCardFieldsContainer']/div[4]/label/span[2]")).getText());
    // ERROR: Caught exception [ERROR: Unsupported command [selectFrame | braintree-hosted-field-expirationDate | ]]
    driver.findElement(By.id("expiration")).clear();
    driver.findElement(By.id("expiration")).sendKeys("02 / 2022");
    // ERROR: Caught exception [ERROR: Unsupported command [selectWindow | null | ]]
    driver.findElement(By.id("braintreeCreditCardMakeDefault")).click();
    driver.findElement(By.id("braintreeCreditCardMakeDefault")).click();
    driver.findElement(By.xpath("//button[@type='submit']")).click();
    // Checking Add Card other than default
    assertEquals("0011", driver.findElement(By.xpath("//html[@id='ext-gen45']/body/main/div/div/div/div/div[2]/div/div/div[2]/dl/dd")).getText());
    assertEquals("12 / 21", driver.findElement(By.xpath("//html[@id='ext-gen45']/body/main/div/div/div/div/div[2]/div/div/div[2]/dl/dd[2]")).getText());
    // making New card as Default
    driver.findElement(By.linkText("Make Default")).click();
    assertEquals("0011", driver.findElement(By.cssSelector("dd.data-value")).getText());
    assertEquals("12 / 21", driver.findElement(By.xpath("//html[@id='ext-gen45']/body/main/div/div/div/div/div/div/div[2]/div[2]/dl/dd[2]")).getText());
    // deleting default card
    // ERROR: Caught exception [Error: locator strategy either id or name must be specified explicitly.]
    driver.findElement(By.name("dwfrm_paymentinstruments_creditcards_storedcards_i0_remove")).click();
    // deleting other card
    // ERROR: Caught exception [Error: locator strategy either id or name must be specified explicitly.]
    driver.findElement(By.name("dwfrm_paymentinstruments_creditcards_storedcards_i0_remove")).click();

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
