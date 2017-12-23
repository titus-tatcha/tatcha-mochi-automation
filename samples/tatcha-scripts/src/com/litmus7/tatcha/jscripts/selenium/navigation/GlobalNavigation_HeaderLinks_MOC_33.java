package com.litmus7.tatcha.jscripts.selenium.navigation;

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
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.litmus7.tatcha.jscripts.selenium.Tcheck_Login;
import com.litmus7.tatcha.utils.BrowserDriver;

public class GlobalNavigation_HeaderLinks_MOC_33 {
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();

  @Before
  public void setUp() throws Exception {
    driver = BrowserDriver.getChromeWebDriver();
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }

  @Test
  public void testMOC33TCALL() throws Exception {

    driver.get(BrowserDriver.BASE_URL);
   
    Properties prop = new Properties();
    System.out.println("footer path "+getClass().getResource("/tatcha.properties"));
	prop.load(new FileInputStream(getClass().getResource("/tatcha.properties").getFile()));

    /** Checking 5 Header Links */
    driver.get(BrowserDriver.BASE_URL);
    for(int i=1;i<=5;i++){
    	String[] headerLinks = prop.get("header.links."+i).toString().split("#");   	
	    WebElement we = driver.findElement(By.linkText(headerLinks[0]));
	    Actions action = new Actions(driver);
	    action.moveToElement(we).build().perform();
	    System.out.println("CASE "+i);
    	assertWhetherHeaderLinksPresent(driver, headerLinks);
    }
  }
  
  /**
	  * This method only works with Anchor tag elements 
	  * read from footer.properties file
	  * 
	  * @param driver1
	  * @param arrayLinks
	  */
	public void assertWhetherHeaderLinksPresent(WebDriver driver1 , String[] arrayLinks){
		    
			  try{
				    int totalLinks = arrayLinks.length;
				    System.out.println("totalLinks "+totalLinks);
				    for(int i=0;i<totalLinks;i++){
					    if(!arrayLinks[i].trim().isEmpty()){
					    	String ELEMENT_NAME = null;
					    	if(!arrayLinks[i].contains("@")){	
					    		ELEMENT_NAME = arrayLinks[i];
					    		System.out.println("if ELEMENT_NAME "+ELEMENT_NAME);	
//					    		WebElement webElement = driver1.findElement(By.linkText(ELEMENT_NAME));
//					    		WebElement webElement = driver1.findElement(By.xpath("//*a[contains(text(),'"+ELEMENT_NAME+"')]"));
//					    		webElement.click();
//					    		driver.navigate().back();
					    		
//					    		if(ELEMENT_NAME.equalsIgnoreCase("heading")){
//									  
//					    		}
					    	}else{
					    		String[] eleHrefTitle = arrayLinks[i].toString().split("@");
						    	ELEMENT_NAME = (null != eleHrefTitle[0])?eleHrefTitle[0].trim():"";
						    	
						    	System.out.println("else ELEMENT_NAME "+ELEMENT_NAME);	
					    	
						    	String HREF_URL = (null != eleHrefTitle[1])?eleHrefTitle[1].trim():"";
						    	String NEXT_PAGE_TITLE = null;
						    	if(eleHrefTitle.length>2){
						    		NEXT_PAGE_TITLE = (null != eleHrefTitle[2])?eleHrefTitle[2].trim():"";
						    	}
						    	
						    	if(!ELEMENT_NAME.isEmpty()){	    		 	 
								    	try{
								    		WebDriverWait wait = (WebDriverWait) new WebDriverWait(driver, 3);
								    			wait.until(ExpectedConditions.visibilityOfElementLocated((By.linkText(ELEMENT_NAME))));
								    		WebElement webElement = null;
								    		try{	
									    		webElement = driver1.findElement(By.linkText(ELEMENT_NAME));
									    		System.out.println("webElement Text " + webElement.getText());
									        	assertEquals(ELEMENT_NAME, webElement.getText());
								    		}catch(TimeoutException te){
												WebElement element = driver1.findElement(By.linkText("SHOP"));
												Actions action = new Actions(driver1);
												action.moveToElement(element).build().perform();
								    		}   
								    		
											webElement.click();
								        	
								        	
								        	if(null!= HREF_URL && !HREF_URL.isEmpty() && !HREF_URL.equalsIgnoreCase("NO_LINK")){
	//							        		String parentHandle = driver.getWindowHandle(); 
	//								    	    System.out.println(parentHandle);             
									    	    String anchorURL = webElement.getAttribute("href"); 
									    	    assertEquals(HREF_URL, anchorURL);
									    	    webElement.click();  
									    	    
		//							    	    for (String winHandle : driver.getWindowHandles()) { 
		//							    	        System.out.println(winHandle);
		//							    	        driver.switchTo().window(winHandle);                 
		//							    	    }
									    	    
									    	    if(null!= NEXT_PAGE_TITLE && !NEXT_PAGE_TITLE.isEmpty() && !NEXT_PAGE_TITLE.equalsIgnoreCase("NO_TITLE")){
										    	    assertEquals(NEXT_PAGE_TITLE, driver.getTitle());
									    	    }
									    	    
									    	    driver.navigate().back();
									    	    
		//							    	    driver.close();                                
		//							    	    driver.switchTo().window(parentHandle);   
								        	}
								    	}catch(NoSuchElementException ne){
								    		System.err.println(ELEMENT_NAME+" NOT FOUND "+ne.toString());
								    	}catch (ElementNotVisibleException nv) {
								    		System.err.println(ELEMENT_NAME+" NOT VISIBLE "+nv.toString());
										}catch (TimeoutException te) {
											System.err.println(ELEMENT_NAME+" TIMEOUT "+te.toString());
										}catch (StaleElementReferenceException elementHasDisappeared) {
											System.err.println(ELEMENT_NAME+" STALE ELE REF "+elementHasDisappeared.toString());
										}catch (WebDriverException we) {
											System.err.println(ELEMENT_NAME+" WEBDRIVER ISSUE "+we.toString());
										}
						    		}
						    	}
					    	}
				    	}
				        	
				    
			  }catch(Exception e){
			  		e.printStackTrace();
			  	
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
