package com.litmus7.tatcha.jscripts.selenium.gfooter;

import java.util.regex.Pattern;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import com.litmus7.tatcha.jscripts.selenium.Tcheck_Login;
import com.litmus7.tatcha.utils.BrowserDriver;

public class MOC_39_TC_ALL {
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();

  @Before
  public void setUp() throws Exception {
//    driver = new FirefoxDriver();
    driver = BrowserDriver.getChromeWebDriver();
//    baseUrl = "http://demo-na01-tatcha.demandware.net/";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }

  @Test
  public void testMOC39TCALL() throws Exception {
//	  Tcheck_Login tcheck_Login = new Tcheck_Login();
//	  tcheck_Login.checkLogin(driver);
    driver.get(BrowserDriver.BASE_URL);
   
    Properties prop = new Properties();

//	prop.load(new FileInputStream("/src/com/litmus7/tatcha/data/footer.properties"));
	
//	prop.load(getClass().getResourceAsStream("src/com/litmus7/tatcha/data/footer.properties"));
//	prop.load(new FileInputStream(getClass().getResource("classpath:src/com/litmus7/tatcha/data/footer.properties").getFile()));
    System.out.println("footer path "+getClass().getResource("/tatcha.properties"));
	prop.load(new FileInputStream(getClass().getResource("/tatcha.properties").getFile()));
//	prop.load(new FileInputStream("footer.properties"));
    
//    InputStream inputStream = MOC_39_TC_ALL.class.getResourceAsStream("footer.properties");
//    prop.load(inputStream);
//    inputStream.close();
    
    BrowserDriver browserDriver = new BrowserDriver();
    
    /** Checking Footer Links */
//    for(int i=1;i<5;i++){
//    	String[] footerLinks = prop.get("footer.links."+i).toString().split("#");
//    	browserDriver.assertWhetherLinksPresent(driver, footerLinks);
//    }
//    

    /** Checking 5 Header Links */
    driver.get(BrowserDriver.BASE_URL);
    for(int i=1;i<=5;i++){
    	String[] headerLinks = prop.get("header.links."+i).toString().split("#");
    	
	    WebElement we = driver.findElement(By.linkText(headerLinks[0]));
	    Actions action = new Actions(driver);
	    action.moveToElement(we).build().perform();
	    
    	browserDriver.assertWhetherLinksPresent(driver, headerLinks);
    }
    
    /** Checking 5 Footer Links */
	    for(int i=1;i<=5;i++){
	    	String[] footerLinks = prop.get("footer.links."+i).toString().split("#");
	    	browserDriver.assertWhetherLinksPresent(driver, footerLinks);
	    }
    
/*    String footerH1 = 
     = footerH1.toString().split("#");
	browserDriver.assertWhetherLinksPresent(driver, footerLinksH1);
	driver.navigate().back();
    String footerH2 = prop.get("footer.H2").toString();
    String[] footerLinksH2 = prop.get("footerLinks.H2").toString().split("#");
	browserDriver.assertWhetherLinksPresent(driver, footerLinksH2);
	
    String footerH3 = prop.get("footer.H3").toString();
    String[] footerLinksH3 = prop.get("footerLinks.H3").toString().split("#");
	browserDriver.assertWhetherLinksPresent(driver, footerLinksH3);
	
    String footerH4 = prop.get("footer.H4").toString();
    String[] footerLinksH4 = prop.get("footerLinks.H4").toString().split("#");
	browserDriver.assertWhetherLinksPresent(driver, footerLinksH4);
	
    String footerH5 = prop.get("footer.H5").toString();
    String[] footerLinksH5 = prop.get("footerLinks.H5").toString().split("#");
	browserDriver.assertWhetherLinksPresent(driver, footerLinksH5);*/
	
	/** Switching Tabs via browser */
	
	 /*	String parentHandle = driver.getWindowHandle(); // get the current window handle
	    System.out.println(parentHandle);               //Prints the parent window handle 
	    String anchorURL = anchor.getAttribute("href"); //Assuming u are clicking on a link which opens a new browser window
	    anchor.click();                                 //Clicking on this window
	    for (String winHandle : driver.getWindowHandles()) { //Gets the new window handle
	        System.out.println(winHandle);
	        driver.switchTo().window(winHandle);        // switch focus of WebDriver to the next found window handle (that's your newly opened window)              
	    }
	//Now your driver works on the current new handle
	//Do some work here.....
	//Time to go back to parent window
	    driver.close();                                 // close newly opened window when done with it
	    driver.switchTo().window(parentHandle);         // switch back to the original window
	    */
	    
	    /** Switching browser windows */
	    
	 /*   // for navigating left to right side:

	    	Actions action= new Actions(driver);
	    	action.keyDown(Keys.CONTROL).sendKeys(Keys.TAB).build().perform();

	    	For navigating right to left :

	    	Actions action= new Actions(driver);
	    	action.keyDown(Keys.CONTROL).keyDown(Keys.SHIFT).sendKeys(Keys.TAB).build().perform();*/
    
//    assertEquals("New - Our Story Page | Tatcha", driver.getTitle());
  }
  
//  Actions action = new Actions(webdriver);
//  WebElement we = webdriver.findElement(By.xpath("html/body/div[13]/ul/li[4]/a"));
//  action.moveToElement(we).moveToElement(webdriver.findElement(By.xpath("/expression-here"))).click().build().perform();
//  action.moveToElement(we).build().perform();


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
