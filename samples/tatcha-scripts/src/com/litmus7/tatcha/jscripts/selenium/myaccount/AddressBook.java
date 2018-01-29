package com.litmus7.tatcha.jscripts.selenium.myaccount;

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

import com.litmus7.tatcha.utils.BrowserDriver;

public class AddressBook {
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();
  private Properties prop = null;
  
  @Before
  public void setUp() throws Exception {
//    driver = new FirefoxDriver();
	  driver = BrowserDriver.getChromeWebDriver();
//    baseUrl = "https://demo-na01-tatcha.demandware.net/";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }

  @Test
  public void testAddressBook() throws Exception {
//    driver.get(baseUrl + "/s/tatcha/account?lang=default");
    driver.get(BrowserDriver.BASE_URL);
	  prop = new Properties();
	  prop.load(new FileInputStream(getClass().getResource("/tatcha.properties").getFile()));
	  
    MyLogin myLogin = new MyLogin();
    myLogin.myAccountLogin(driver, prop);
    
//    driver.findElement(By.linkText("View Your Account")).click();
//    driver.findElement(By.xpath("//li[4]/a/div/p")).click();
//    assertEquals("Address Book", driver.findElement(By.cssSelector("h1.text-center")).getText());
//    assertEquals("Default Address", driver.findElement(By.cssSelector("h5.panel-title")).getText());
//    assertEquals("Add An Address", driver.findElement(By.cssSelector("p")).getText());
    
    MyAccount_MOC_16 myAccount = new MyAccount_MOC_16();
    myAccount.checkAddressBook(driver,prop);
    
    /** Click on Add An Address */
    driver.findElement(By.cssSelector("p")).click();
    // Add Address
    // ERROR: Caught exception [Error: locator strategy either id or name must be specified explicitly.]
    
    String ADDRESSBOOK_NAME = prop.get("addressbook.id").toString(); 
    String ADDRESSBOOK_FNAME = prop.get("addressbook.fname").toString(); 
    String ADDRESSBOOK_LNAME = prop.get("addressbook.lname").toString(); 
    String ADDRESSBOOK_COUNTRY = prop.get("addressbook.country").toString();
    String ADDRESSBOOK_ADDR1 = prop.get("addressbook.addr1").toString();
    String ADDRESSBOOK_ADDR2 = prop.get("addressbook.addr2").toString();
    String ADDRESSBOOK_CITY = prop.get("addressbook.city").toString();
    String ADDRESSBOOK_STATE = prop.get("addressbook.state").toString();
    String ADDRESSBOOK_PIN = prop.get("addressbook.pin").toString(); 
    String ADDRESSBOOK_PHONE = prop.get("addressbook.phone").toString();
    
    
    driver.findElement(By.id("dwfrm_profile_address_addressid")).clear();
    driver.findElement(By.id("dwfrm_profile_address_addressid")).sendKeys(ADDRESSBOOK_NAME);
    driver.findElement(By.id("dwfrm_profile_address_firstname")).clear();
    driver.findElement(By.id("dwfrm_profile_address_firstname")).sendKeys(ADDRESSBOOK_FNAME);
    driver.findElement(By.id("dwfrm_profile_address_lastname")).clear();
    driver.findElement(By.id("dwfrm_profile_address_lastname")).sendKeys(ADDRESSBOOK_LNAME);
//    driver.findElement(By.id("dwfrm_profile_address_firstname")).clear();
//    driver.findElement(By.id("dwfrm_profile_address_firstname")).sendKeys("testerOne");
    new Select(driver.findElement(By.id("dwfrm_profile_address_country"))).selectByVisibleText(ADDRESSBOOK_COUNTRY);
    driver.findElement(By.id("dwfrm_profile_address_address1")).clear();
    driver.findElement(By.id("dwfrm_profile_address_address1")).sendKeys(ADDRESSBOOK_ADDR1);
    driver.findElement(By.id("dwfrm_profile_address_address2")).clear();
    driver.findElement(By.id("dwfrm_profile_address_address2")).sendKeys(ADDRESSBOOK_ADDR2);
    driver.findElement(By.id("dwfrm_profile_address_city")).clear();
    driver.findElement(By.id("dwfrm_profile_address_city")).sendKeys(ADDRESSBOOK_CITY);
//    driver.findElement(By.xpath("//option[@value='KS']")).click();
    new Select(driver.findElement(By.id("dwfrm_profile_address_states_state"))).selectByVisibleText(ADDRESSBOOK_STATE);

    driver.findElement(By.id("dwfrm_profile_address_postal")).clear();
    driver.findElement(By.id("dwfrm_profile_address_postal")).sendKeys(ADDRESSBOOK_PIN);
    driver.findElement(By.id("dwfrm_profile_address_phone")).clear();
    driver.findElement(By.id("dwfrm_profile_address_phone")).sendKeys(ADDRESSBOOK_PHONE);
    driver.findElement(By.name("dwfrm_profile_address_create")).click();
    
    /** Verifying by Checking Whether Address Book Title is on Page */
    String ITEM4_TITLE1 = prop.get("myaccount.item4.title1").toString();
    assertEquals(ITEM4_TITLE1.toUpperCase(), driver.findElement(By.cssSelector("h1.text-center")).getText());
    
    // adding 2nd address
    // ERROR: Caught exception [Error: locator strategy either id or name must be specified explicitly.]
    
/*    driver.findElement(By.id("dwfrm_profile_address_addressid")).click();
    assertEquals("Add Address", driver.findElement(By.cssSelector("h4.panel-title")).getText());
    assertEquals("Address Name", driver.findElement(By.cssSelector("label.control-label > span")).getText());
    driver.findElement(By.id("dwfrm_profile_address_addressid")).clear();
    driver.findElement(By.id("dwfrm_profile_address_addressid")).sendKeys("QA Address2");
    assertEquals("First Name", driver.findElement(By.xpath("//form[@id='edit-address-form']/fieldset/div[2]/label/span")).getText());
    driver.findElement(By.id("dwfrm_profile_address_firstname")).clear();
    driver.findElement(By.id("dwfrm_profile_address_firstname")).sendKeys("test2");
    assertEquals("Last Name", driver.findElement(By.xpath("//form[@id='edit-address-form']/fieldset/div[3]/label")).getText());
    driver.findElement(By.id("dwfrm_profile_address_lastname")).clear();
    driver.findElement(By.id("dwfrm_profile_address_lastname")).sendKeys("tatcha2");
    assertEquals("Country", driver.findElement(By.xpath("//form[@id='edit-address-form']/fieldset/div[4]/label")).getText());
    new Select(driver.findElement(By.id("dwfrm_profile_address_country"))).selectByVisibleText("United States");
    assertEquals("Address 1", driver.findElement(By.xpath("//form[@id='edit-address-form']/fieldset/div[5]/label/span")).getText());
    driver.findElement(By.id("dwfrm_profile_address_address1")).clear();
    driver.findElement(By.id("dwfrm_profile_address_address1")).sendKeys("China Town");
    assertEquals("Address 2", driver.findElement(By.xpath("//form[@id='edit-address-form']/fieldset/div[6]/label/span")).getText());
    driver.findElement(By.id("dwfrm_profile_address_address2")).clear();
    driver.findElement(By.id("dwfrm_profile_address_address2")).sendKeys("Rode Island");
    assertEquals("City", driver.findElement(By.xpath("//form[@id='edit-address-form']/fieldset/div[7]/label/span")).getText());
    driver.findElement(By.id("dwfrm_profile_address_city")).clear();
    driver.findElement(By.id("dwfrm_profile_address_city")).sendKeys("Dallas");
    assertEquals("State", driver.findElement(By.xpath("//form[@id='edit-address-form']/fieldset/div[8]/label/span")).getText());
    new Select(driver.findElement(By.id("dwfrm_profile_address_states_state"))).selectByVisibleText("Texas");
    driver.findElement(By.id("dwfrm_profile_address_postal")).click();
    assertEquals("ZIP Code", driver.findElement(By.xpath("//form[@id='edit-address-form']/fieldset/div[9]/label")).getText());
    driver.findElement(By.id("dwfrm_profile_address_postal")).clear();
    driver.findElement(By.id("dwfrm_profile_address_postal")).sendKeys("45653");
    assertEquals("Phone", driver.findElement(By.xpath("//form[@id='edit-address-form']/fieldset/div[10]/label/span")).getText());
    driver.findElement(By.id("dwfrm_profile_address_phone")).clear();
    driver.findElement(By.id("dwfrm_profile_address_phone")).sendKeys("333-333-3333");
    driver.findElement(By.name("dwfrm_profile_address_create")).click();*/
    
//    driver.findElement(By.linkText("Make Default")).click();
    // Delete not working
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
