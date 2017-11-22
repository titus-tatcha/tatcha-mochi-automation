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
import org.openqa.selenium.support.ui.WebDriverWait;

import com.litmus7.tatcha.utils.BrowserDriver;

public class MyAccount_MOC_16 {
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
    baseUrl = "https://demo-na01-tatcha.demandware.net/";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }

  @Test
  public void testMyAccountMOC16() throws Exception {
	    	    /** MOC 16 */
//  driver.get(baseUrl + "/on/demandware.store/Sites-tatcha-Site/default/Login-Show?original=%2fs%2ftatcha%2faccount%3flang%3ddefault");
	  driver.get(BrowserDriver.BASE_URL);
	  MyLogin myAccount = new MyLogin();
	  myAccount.myAccountLogin(driver,prop);
	  myAccountLandingPage(driver,prop);
	  myAccount.myAccountLogout(driver);
	  
//    driver.findElement(By.id("dwfrm_login_username_d0zyqnwbgecn")).clear();
//    driver.findElement(By.id("dwfrm_login_username_d0zyqnwbgecn")).sendKeys("qa.tatcha@gmail.com");
//    driver.findElement(By.id("dwfrm_login_password_d0lqxvaigquu")).clear();
//    driver.findElement(By.id("dwfrm_login_password_d0lqxvaigquu")).sendKeys("tatcha123");  
//    driver.findElement(By.name("dwfrm_login_login")).click();
    
//    assertEquals("Hello, qa", driver.findElement(By.id("dropdownMenu1")).getText());
//    
//    assertEquals("Login Settings & Profile", driver.findElement(By.cssSelector("h4")).getText());
//    assertEquals("My Orders", driver.findElement(By.xpath("//html[@id='ext-gen45']/body/main/div/div/div/div/ul/li[2]/a/div/h4")).getText());
//    assertEquals("Manage your recurring auto-delivery orders.", driver.findElement(By.xpath("//html[@id='ext-gen45']/body/main/div/div/div/div/ul/li[3]/a/div/p")).getText());
//    assertEquals("Address Book", driver.findElement(By.xpath("//html[@id='ext-gen45']/body/main/div/div/div/div/ul/li[4]/a/div/h4")).getText());
//    assertEquals("Manage your saved credit cards, gift cards and store credit.", driver.findElement(By.xpath("//html[@id='ext-gen45']/body/main/div/div/div/div/ul/li[5]/a/div/p")).getText());
//    assertEquals("Email Preferences", driver.findElement(By.xpath("//html[@id='ext-gen45']/body/main/div/div/div/div/ul/li[6]/a/div/h4")).getText());
//    driver.findElement(By.cssSelector("p")).click();
//    driver.findElement(By.xpath("//html[@id='ext-gen45']/body/main/div/div/div/div/ul/li[2]/a/div/p")).click();
//    driver.findElement(By.xpath("//html[@id='ext-gen45']/body/main/div/div/div/div/ul/li[3]/a/div/h4")).click();
//    driver.findElement(By.xpath("//html[@id='ext-gen45']/body/main/div/div/div/div/ul/li[4]/a/div/p")).click();
//    driver.findElement(By.xpath("//html[@id='ext-gen45']/body/main/div/div/div/div/ul/li[5]/a/div/h4")).click();
//    driver.findElement(By.xpath("//html[@id='ext-gen45']/body/main/div/div/div/div/ul/li[6]/a/div/p")).click();
  }

  public void myAccountLandingPage(WebDriver driver, Properties prop){
	  
	  	String WELCOME_MSG = prop.get("login.welcome").toString();
//	  	String MYACCOUNT_ITEM1 = prop.get("myaccount.item1.h4").toString();
//	  	String MYACCOUNT_ITEM1_DESC = prop.get("myaccount.item1.p").toString();
//	  	String MYACCOUNT_ITEM2 = prop.get("myaccount.item2.p").toString();
//	  	String MYACCOUNT_ITEM3 = prop.get("myaccount.item3.p").toString();
//	  	String MYACCOUNT_ITEM4 = prop.get("myaccount.item4.p").toString();
//	  	String MYACCOUNT_ITEM5 = prop.get("myaccount.item5.p").toString();
//	  	String MYACCOUNT_ITEM6 = prop.get("myaccount.item6.p").toString();
	  	String MYACCOUNT_ARROW = prop.get("myaccount.arrow").toString();

//	    assertEquals("Hello, qa", driver.findElement(By.id("dropdownMenu1")).getText());
//	    assertEquals(WELCOME_MSG, driver.findElement(By.id("dropdownMenu1")).getText());
//	    
//	    String actualString = driver.findElement(By.xpath("xpath")).getText();
	  	
	  	/** Can check only Hello, string while Login since Firstname unknown from Username */
	    assertTrue(driver.findElement(By.id("dropdownMenu1")).getText().contains(WELCOME_MSG));
	    
//	    WebDriverWait wait = new WebDriverWait(driver, 10);
//	    WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.id(>someid>)));

	    // Click on LOGIN SETTINGS & PROFILE
	    	String MYACCOUNT_ITEM_1 = prop.get("myaccount.item1").toString();
		  	String MYACCOUNT_ITEM_DESC_1 = prop.get("myaccount.item1.p").toString();
	    	WebElement ITEM_TITLE_1 = driver.findElement(By.xpath("//*[@id='ext-gen44']/body/main/div/div/div/div/ul/li[1]/a/div/h4"));
	    	WebElement ITEM_DESC_1 = driver.findElement(By.xpath("//*[@id='ext-gen44']/body/main/div/div/div/div/ul/li[1]/a/div/p"));	    	
		    assertEquals(MYACCOUNT_ITEM_1.toUpperCase(), ITEM_TITLE_1.getText());
		    assertEquals(MYACCOUNT_ITEM_DESC_1, ITEM_DESC_1.getText());
		    ITEM_TITLE_1.click();
		    	String ITEM1_TITLE1 = prop.get("myaccount.item1.title1").toString();
		    	String ITEM1_TITLE2 = prop.get("myaccount.item1.title2").toString();
			    assertEquals(ITEM1_TITLE1.toUpperCase(), driver.findElement(By.cssSelector("h4.panel-title")).getText());
			    assertEquals(ITEM1_TITLE2.toUpperCase(), driver.findElement(By.cssSelector("div.panel-heading.panel-split > h4.panel-title")).getText());
		    driver.navigate().back();

	    // Click on MY ORDERS
	    	String MYACCOUNT_ITEM_2 = prop.get("myaccount.item2").toString();
		  	String MYACCOUNT_ITEM_DESC_2 = prop.get("myaccount.item2.p").toString();
	    	WebElement ITEM_TITLE_2 = driver.findElement(By.xpath("//*[@id='ext-gen44']/body/main/div/div/div/div/ul/li[2]/a/div/h4"));
	    	WebElement ITEM_DESC_2 = driver.findElement(By.xpath("//*[@id='ext-gen44']/body/main/div/div/div/div/ul/li[2]/a/div/p"));	    	
		    assertEquals(MYACCOUNT_ITEM_2.toUpperCase(), ITEM_TITLE_2.getText());
		    assertEquals(MYACCOUNT_ITEM_DESC_2, ITEM_DESC_2.getText());
		    ITEM_TITLE_2.click();
		    	String ITEM2_TITLE = prop.get("myaccount.item2.title").toString();
		    	assertEquals(ITEM2_TITLE.toUpperCase(), driver.findElement(By.cssSelector("h1.text-center")).getText());
		    driver.navigate().back();
		    
	    
		    // Click on AUTO DELIVERY - nothing happens
	    	String MYACCOUNT_ITEM_3 = prop.get("myaccount.item3").toString();
		  	String MYACCOUNT_ITEM_DESC_3 = prop.get("myaccount.item3.p").toString();
	    	WebElement ITEM_TITLE_3 = driver.findElement(By.xpath("//*[@id='ext-gen44']/body/main/div/div/div/div/ul/li[3]/a/div/h4"));
	    	WebElement ITEM_DESC_3 = driver.findElement(By.xpath("//*[@id='ext-gen44']/body/main/div/div/div/div/ul/li[3]/a/div/p"));	    	
		    assertEquals(MYACCOUNT_ITEM_3.toUpperCase(), ITEM_TITLE_3.getText());
		    assertEquals(MYACCOUNT_ITEM_DESC_3, ITEM_DESC_3.getText());
		    ITEM_TITLE_3.click();
		    driver.navigate().back();
		    
		    // Click on ADDRESS BOOK
		    checkAddressBook(driver,prop); 
		    driver.navigate().back();

		    // Click on PAYMENT OPTIONS 
	    	checkPaymentOptions(driver,prop);		    
		    driver.navigate().back();
		    
		 // Click on EMAIL PREFERENCES - nothing happens
	    	String MYACCOUNT_ITEM_6 = prop.get("myaccount.item6").toString();
		  	String MYACCOUNT_ITEM_DESC_6 = prop.get("myaccount.item6.p").toString();
	    	WebElement ITEM_TITLE_6 = driver.findElement(By.xpath("//*[@id='ext-gen44']/body/main/div/div/div/div/ul/li[6]/a/div/h4"));
	    	WebElement ITEM_DESC_6 = driver.findElement(By.xpath("//*[@id='ext-gen44']/body/main/div/div/div/div/ul/li[6]/a/div/p"));	    	
		    assertEquals(MYACCOUNT_ITEM_6.toUpperCase(), ITEM_TITLE_6.getText());
		    assertEquals(MYACCOUNT_ITEM_DESC_6, ITEM_DESC_6.getText());
		    ITEM_TITLE_6.click();
		    driver.navigate().back();

		    // nav arrow
	  //*[@id="ext-gen44"]/body/main/div/div/div/div/ul/li[1]/a/div[2]
	    
  }
  
  /** Simple Check Address book page */
  public void checkAddressBook(WebDriver driver, Properties prop){
  	String MYACCOUNT_ITEM_4 = prop.get("myaccount.item4").toString();
		  	String MYACCOUNT_ITEM_DESC_4 = prop.get("myaccount.item4.p").toString();
	    	WebElement ITEM_TITLE_4 = driver.findElement(By.xpath("//*[@id='ext-gen44']/body/main/div/div/div/div/ul/li[4]/a/div/h4"));
	    	WebElement ITEM_DESC_4 = driver.findElement(By.xpath("//*[@id='ext-gen44']/body/main/div/div/div/div/ul/li[4]/a/div/p"));	    	
		    assertEquals(MYACCOUNT_ITEM_4.toUpperCase(), ITEM_TITLE_4.getText());
		    assertEquals(MYACCOUNT_ITEM_DESC_4, ITEM_DESC_4.getText());
		    ITEM_TITLE_4.click();
		    	String ITEM4_TITLE1 = prop.get("myaccount.item4.title1").toString();
		    	String ITEM4_TITLE2 = prop.get("myaccount.item4.title2").toString();
		    	String ITEM4_TITLE3 = prop.get("myaccount.item4.title3").toString();
			    assertEquals(ITEM4_TITLE1.toUpperCase(), driver.findElement(By.cssSelector("h1.text-center")).getText());
			    assertEquals(ITEM4_TITLE2, driver.findElement(By.cssSelector("h5.panel-title")).getText());
			    assertEquals(ITEM4_TITLE3, driver.findElement(By.cssSelector("p")).getText());   
  }
  
  /** Simple Check Payment Options page */
  public void checkPaymentOptions(WebDriver driver, Properties prop){
	  String MYACCOUNT_ITEM_5 = prop.get("myaccount.item5").toString();
	  	String MYACCOUNT_ITEM_DESC_5 = prop.get("myaccount.item5.p").toString();
  	WebElement ITEM_TITLE_5 = driver.findElement(By.xpath("//*[@id='ext-gen44']/body/main/div/div/div/div/ul/li[5]/a/div/h4"));
  	WebElement ITEM_DESC_5 = driver.findElement(By.xpath("//*[@id='ext-gen44']/body/main/div/div/div/div/ul/li[5]/a/div/p"));	    	
	    assertEquals(MYACCOUNT_ITEM_5.toUpperCase(), ITEM_TITLE_5.getText());
	    assertEquals(MYACCOUNT_ITEM_DESC_5, ITEM_DESC_5.getText());
	    ITEM_TITLE_5.click();
	    	String ITEM5_TITLE1 = prop.get("myaccount.item5.title1").toString();
	    	String ITEM5_TITLE2 = prop.get("myaccount.item5.title2").toString();
	    	String ITEM5_TITLE3 = prop.get("myaccount.item5.title3").toString();
		    assertEquals(ITEM5_TITLE1.toUpperCase(), driver.findElement(By.cssSelector("h1.text-center")).getText());
		    assertEquals(ITEM5_TITLE2, driver.findElement(By.cssSelector("h5.panel-title")).getText());
//		    assertEquals(ITEM5_TITLE3, driver.findElement(By.cssSelector("h5.panel-body.p")).getText()); 
		    assertEquals(ITEM5_TITLE3, driver.findElement(By.xpath("//*[@id='ext-gen44']/body/main/div/div/div/div/div[2]/a/div/div/p")).getText()); 

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
