package com.litmus7.tatcha.utils;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.TimeUnit;

import javax.naming.spi.DirStateFactory.Result;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;

import com.xceptance.xlt.api.engine.scripting.AbstractScriptTestCase;
import com.xceptance.xlt.api.engine.scripting.ScriptName;

/*
 * FF and GC browser drivers with System settings
 * Invoked while automating test scripts w/o XLT
 */
public class BrowserDriver extends AbstractScriptTestCase {

	 private static WebDriver driver = null;
	 private static DesiredCapabilities capabilities = null;
	 private String webDriver = null;
	 private String pathToBrowser = null;
	 private String pathToDriverServer = null;
	
//	 public static final String BASE_URL = "https://52.43.54.174/mocha/tatcha-bootstrap3/src/prototype-wrapper.html";
//	 public static final String BASE_URL = "http://52.43.54.174/mocha/tatcha-bootstrap3/src/prototype-wrapper.html";
	 public static final String BASE_URL = "http://demo-na01-tatcha.demandware.net/on/demandware.store/Sites-tatcha-Site";
//	 public static final String QA_URL = "http://demo-na01-tatcha.demandware.net/on/demandware.store/Sites-tatcha-Site";
	 
	public static WebDriver getFireFoxWebDriver(){
        capabilities = DesiredCapabilities.firefox();
        
        System.setProperty("webdriver.firefox.bin", "D:/programs/firefox/47/firefox.exe");
        System.setProperty("webdriver.firefox.driver", "D:/project/SeleniumTestScripts/geckodriver-v0.18.0-win64/geckodriver.exe");
        driver = new FirefoxDriver(capabilities);
        return driver;
 	}

	public static WebDriver getChromeWebDriver(){
        capabilities = DesiredCapabilities.chrome();
        
//      capabilities.setCapability("webdriver.chrome.bin", path);
//	   	 pathToBrowser = getProperty("xlt.webDriver.chrome.pathToBrowser");
//	     pathToDriverServer = getProperty("xlt.webDriver.chrome.pathToDriverServer");  
//        System.setProperty("webdriver.chrome.bin", "xlt.webDriver.chrome.pathToBrowser");
//        System.setProperty("webdriver.chrome.driver", "xlt.webDriver.chrome.pathToDriverServer");
      
      System.setProperty("webdriver.chrome.bin", "C:/Program Files (x86)/Google/Chrome/Application/chrome.exe");
      System.setProperty("webdriver.chrome.driver", "D:/project/SeleniumTestScripts/chromedriver_win32/chromedriver.exe");
        
        driver = new ChromeDriver(capabilities);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        return driver;
 	}
 	
	public WebDriver getSafariWebDriver(){
// 		System.setProperty("webdriver.safari.bin", "D:/programs/Safari/Safari.exe");
// 		capabilities = DesiredCapabilities.safari();
// 		capabilities.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.IGNORE);
// 		driver = new SafariDriver(capabilities);
 		System.setProperty("webdriver.safari.noinstall", "true"); 
 		driver = new SafariDriver();	
 		return driver;
 	}
 	
	public WebDriver getSafariWebDriver2(){
	 	SafariOptions options = new SafariOptions();
	 	options.setUseCleanSession(true);
	 	driver = new SafariDriver(options);
//	 	capabilities = DesiredCapabilities.safari();
//	 	capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
//	 	capabilities.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, "dismiss");
//	 	capabilities.setCapability(SafariOptions.CAPABILITY, options);
//	 	driver = new SafariDriver(capabilities);
	 	driver.manage().deleteAllCookies();
	 	return driver;
 	}


	/** Checking Security Token for all fields */
	 public static String getSecurityToken(String contentName){
		 String UNDERSCORE = "_";
		 String secToken=null;
		 if(null!=contentName && contentName.contains(UNDERSCORE) && contentName.startsWith("DWFRM".toLowerCase()) ){
			 String[] contentCSRFname = contentName.split(UNDERSCORE);
			 secToken = contentCSRFname[contentCSRFname.length-1];
		 }
		 return secToken;
	 }
 
	 /**
	  * This method only works with Anchor tag elements 
	  * read from footer.properties file
	  * 
	  * @param driver1
	  * @param arrayLinks
	  */
	public void assertWhetherLinksPresent(WebDriver driver1 , String[] arrayLinks){
		    
			  try{
				    int totalLinks = arrayLinks.length;
				    System.out.println("totalLinks "+totalLinks);
				    for(int i=0;i<totalLinks;i++){
					    if(!arrayLinks[i].trim().isEmpty()){
					    	String ELEMENT_NAME = null;
					    	if(!arrayLinks[i].contains("@")){	
					    		ELEMENT_NAME = arrayLinks[i];
					    		System.out.println("ELEMENT_NAME "+ELEMENT_NAME);	
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
						    	System.out.println("ELEMENT_NAME "+ELEMENT_NAME);	
					    	
						    	if(!ELEMENT_NAME.isEmpty()){
						    		if(ELEMENT_NAME.equalsIgnoreCase("heading")){
					    				
						    		}else{		    		
								    	String HREF_URL = (null != eleHrefTitle[1])?eleHrefTitle[1].trim():"";
								    	String NEXT_PAGE_TITLE = null;
								    	if(eleHrefTitle.length>2){
								    		NEXT_PAGE_TITLE = (null != eleHrefTitle[2])?eleHrefTitle[2].trim():"";
								    	}
								    	 
								    	try{
								    	
								    		WebElement webElement = driver1.findElement(By.linkText(ELEMENT_NAME));
								        	assertEquals(ELEMENT_NAME, webElement.getText());
								        	System.out.println(webElement.getText());
								        	
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
								    		System.err.println(ELEMENT_NAME+" HREF NOT FOUND "+ne.toString());
								    	}catch (ElementNotVisibleException nv) {
								    		System.err.println(ELEMENT_NAME+" NOT VISIBLE "+nv.toString());
										}
						    		}
						    	}
					    	}
				    	}
				        	
				    }
			  }catch(Exception e){
			  		e.printStackTrace();
			  	
			  }
	}
}

/*
xlt.webDriver.chrome.pathToBrowser = C:/Program Files (x86)/Google/Chrome/Application/chrome.exe
xlt.webDriver.chrome.pathToDriverServer = D:/project/SeleniumTestScripts/chromedriver_win32/chromedriver.exe

# Firefox
xlt.webDriver.firefox.pathToBrowser = D:/programs/FF_47/firefox.exe
xlt.webDriver.firefox.pathToDriverServer = D:/project/SeleniumTestScripts/geckodriver-v0.18.0-win64/geckodriver.exe
*/
