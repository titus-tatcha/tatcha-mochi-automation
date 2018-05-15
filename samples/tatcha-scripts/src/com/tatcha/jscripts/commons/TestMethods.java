package com.tatcha.jscripts.commons;

import static org.junit.Assert.assertEquals;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.logging.FileHandler;
import java.util.logging.LogManager;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.tatcha.jscripts.dao.Product;
import com.tatcha.utils.BrowserDriver;

public class TestMethods {
	private final static Logger logger = Logger.getLogger(TestMethods.class);

	private static TestMethods instance = null;

	public static final TestMethods getInstance() {
		if (null == instance)
			instance = new TestMethods();
		return instance;
	}

	private TestMethods() {

	}

	public void testNewsLetterPopupModal(WebDriver driver2) {
		// driver2.manage().timeouts().implicitlyWait(3,TimeUnit.SECONDS);
		// JavascriptExecutor js = (JavascriptExecutor)driver2;
		// js.executeAsyncScript("window.setTimeout(arguments[arguments.length -
		// 1], 5000);");
		// new WebDriverWait(driver2, pageLoadTimeout).until(webDriver -> ((js)
		// webDriver).executeScript("return
		// document.readyState").equals("complete"));
		// waitForLoad(driver2);

		Properties propNews = new Properties();
		try {
			propNews.load(new FileInputStream(getClass().getResource("/news_letter.properties").getFile()));
		} catch (IOException ie) {
			logger.error("NEWS LETTER PROPERTIES FILE ERROR: " + ie.toString());
		}

		waitForPageLoaded(driver2);
		assertionChecker(propNews.getProperty("newsletter.popup.heading.text").toString(),
				getWE(driver2, propNews, "newsletter.popup.heading").getText());
		WebElement we = getWE(driver2, propNews, "newsletter.popup.close");
		we.click();
	}

	public void waitForLoad(WebDriver driver) {
		ExpectedCondition<Boolean> pageLoadCondition = new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
			}
		};
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(pageLoadCondition);
	}

	public void waitForPageLoaded(WebDriver driver2) {
		ExpectedCondition<Boolean> expectation = new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				return ((JavascriptExecutor) driver).executeScript("return document.readyState").toString()
						.equals("complete");
			}
		};
		try {
			Thread.sleep(1000);
			WebDriverWait wait = new WebDriverWait(driver2, 30);
			wait.until(expectation);
		} catch (Throwable error) {
			Assert.fail("Timeout waiting for Page Load Request to complete.");
		}
	}

	public WebElement getWE(WebDriver driver, Properties prop, String propKey) {
		WebElement we = null;
		try {
			we = driver.findElement(By.xpath(prop.getProperty(propKey).toString()));
		} catch (NoSuchElementException ne) {
			logger.error("ELEMENT NOT FOUND VIA PROPERTY KEY: " + ne.toString());
		}
		return we;
	}

	public WebElement getWE(WebDriver driver, String expression) {
		WebElement we = null;
		try {
			we = driver.findElement(By.xpath(expression));
		} catch (NoSuchElementException ne) {
			logger.error("ELEMENT NOT FOUND VIA XPATH: " + ne.toString());
		}
		return we;
	}

	public WebElement getWEbyCSS(WebDriver driver, String selector) {
		WebElement we = null;
		try {
			we = driver.findElement(By.cssSelector(selector));
		} catch (NoSuchElementException ne) {
			logger.error("ELEMENT NOT FOUND VIA CSS SELECTOR: " + ne.toString());
		}
		return we;
	}

	public WebElement getWEbyID(WebDriver driver, String id) {
		WebElement we = null;
		try {
			we = driver.findElement(By.id(id));
		} catch (NoSuchElementException ne) {
			logger.error("ELEMENT NOT FOUND VIA ID: " + ne.toString());
		}
		return we;
	}

	public WebElement getWEbyClass(WebDriver driver, String className) {
		WebElement we = null;
		try {
			we = driver.findElement(By.className(className));
		} catch (NoSuchElementException ne) {
			logger.error("ELEMENT NOT FOUND VIA CLASS NAME: " + ne.toString());
		}
		return we;
	}

	public void assertionChecker(String PARAM1, String PARAM2) {
		try {
			if (null != PARAM1 && null != PARAM2)
				assertEquals(PARAM1, PARAM2);
			else {
				logger.error("ASSERT-EQUALS FAILS as PARAMS NULL");
			}
		} catch (AssertionError ae) {
			logger.error("ASSERT-EQUALS ERROR b/w " + PARAM1 + " & " + PARAM2);
		}
	}

	public boolean assertionChecker(String PARENT, String CHILD, String TYPE) {
		boolean FLAG = false;
		try {
			if (null != PARENT && null != CHILD) {
				if (null != TYPE && TYPE.equalsIgnoreCase("CONTAINS")) {
					assert (PARENT.contains(CHILD));
				} else // EQUALS
					assertEquals(PARENT, CHILD);
				FLAG = true;
			} else {
				FLAG = false;
				logger.error("ASSERTION FAILS as PARAMS NULL");
			}
		} catch (AssertionError ae) {
			FLAG = false;
			logger.error("ASSERT-CONTAINS ERROR b/w " + PARENT + " & " + CHILD);
		}
		return FLAG;
	}

	public Properties getEnvPropertyFile() {
		Properties prop = new Properties();
		if (null != System.getProperty("work.env")) {
			String ENV = System.getProperty("work.env").toString();
			String MODULE = System.getProperty("work.module").toString();
			if (ENV.equalsIgnoreCase("PRODUCTION")) {

			} else if (ENV.equalsIgnoreCase("PREVIEW")) {

			} else if (ENV.equalsIgnoreCase("STAGING")) {

			} else if (ENV.equalsIgnoreCase("DEMO")) {

			} else if (ENV.equalsIgnoreCase("DEV") || ENV.equalsIgnoreCase("DEV_SEC")) {
				if (MODULE.equalsIgnoreCase("HEADER")) {
					try {
						prop.load(new FileInputStream(getClass().getResource("/DEV_Header.properties").getFile()));
					} catch (IOException ie) {
						logger.error("PROPERTY FILE MISSING: DEV_HeaderElement" + ie.toString());
					}
				}

				if (MODULE.equalsIgnoreCase("FOOTER")) {
					try {
						prop.load(new FileInputStream(getClass().getResource("/DEV_Footer.properties").getFile()));
					} catch (IOException ie) {
						logger.error("PROPERTY FILE MISSING: DEV_HeaderElement" + ie.toString());
					}
				}
			} else {
				logger.error("ENVIRONMENT NOT SPECIFIED");
			}
		}
		return prop;
	}

	public String getBaseURL() {
		String ENV = System.getProperty("work.env");
		String MODULE = System.getProperty("work.module");
		String PRODUCT = System.getProperty("product.name");

		StringBuilder BASE_URL = new StringBuilder();
		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream(getClass().getResource("/URL_HTTP.properties").getFile()));
			if (null != ENV && !ENV.isEmpty()) {
				if (ENV.equalsIgnoreCase("LIVE"))
					BASE_URL.append(prop.get("url.live").toString());
				else if (ENV.equalsIgnoreCase("STAGE"))
					BASE_URL.append(prop.get("url.stage").toString());
				else if (ENV.equalsIgnoreCase("DEMO"))
					BASE_URL.append(prop.get("url.demo").toString());
				else if (ENV.equalsIgnoreCase("DEV_SEC"))
					BASE_URL.append(prop.get("url.dev.security").toString());
				else
					BASE_URL.append(prop.get("url.dev").toString());
			} else {
				BASE_URL.append(prop.get("url.dev").toString());
			}

			/** EITHER MODULE OR PRODUCT GIVEN */
			/** MODULES FOR PRODUCT LISTING PAGE */
			if (null != MODULE && !MODULE.trim().isEmpty()) {
				if (MODULE.equalsIgnoreCase("SHOPALL"))
					BASE_URL.append(prop.get("ctg.shopall").toString());
				else if (MODULE.equalsIgnoreCase("MOIST"))
					BASE_URL.append(prop.get("ctg.moist").toString());
				else if (MODULE.equalsIgnoreCase("CLEANS"))
					BASE_URL.append(prop.get("ctg.cleans").toString());
				else if (MODULE.equalsIgnoreCase("FACE"))
					BASE_URL.append(prop.get("ctg.face").toString());
				else if (MODULE.equalsIgnoreCase("MASKS"))
					BASE_URL.append(prop.get("ctg.masks").toString());
				else if (MODULE.equalsIgnoreCase("ESSENCE"))
					BASE_URL.append(prop.get("ctg.essence").toString());
				else if (MODULE.equalsIgnoreCase("EYE"))
					BASE_URL.append(prop.get("ctg.eye").toString());
				else if (MODULE.equalsIgnoreCase("LIP"))
					BASE_URL.append(prop.get("ctg.lip").toString());
				else if (MODULE.equalsIgnoreCase("MAKEUP"))
					BASE_URL.append(prop.get("ctg.makeup").toString());
				else if (MODULE.equalsIgnoreCase("PRIMING"))
					BASE_URL.append(prop.get("ctg.priming").toString());
				else if (MODULE.equalsIgnoreCase("BODY"))
					BASE_URL.append(prop.get("ctg.body").toString());
				else if (MODULE.equalsIgnoreCase("BLOTTING"))
					BASE_URL.append(prop.get("ctg.blotting").toString());
				else if (MODULE.equalsIgnoreCase("NORMALDRY"))
					BASE_URL.append(prop.get("ctg.normaldry").toString());
				else if (MODULE.equalsIgnoreCase("NORMALOILY"))
					BASE_URL.append(prop.get("ctg.normaloily").toString());
				else if (MODULE.equalsIgnoreCase("DRY"))
					BASE_URL.append(prop.get("ctg.dry").toString());
				else if (MODULE.equalsIgnoreCase("SENSITIVE"))
					BASE_URL.append(prop.get("ctg.sensitive").toString());
				else if (MODULE.equalsIgnoreCase("BESTSELLERS"))
					BASE_URL.append(prop.get("ctg.bestsellers").toString());
				else if (MODULE.equalsIgnoreCase("NEW"))
					BASE_URL.append(prop.get("ctg.new").toString());
				else if (MODULE.equalsIgnoreCase("GIFTSETS"))
					BASE_URL.append(prop.get("ctg.giftsets").toString());
				else if (MODULE.equalsIgnoreCase("GIFTPURCHASE"))
					BASE_URL.append(prop.get("ctg.giftpurchase").toString());
				else if (MODULE.equalsIgnoreCase("GIFTPOUCH"))
					BASE_URL.append(prop.get("ctg.giftpouch").toString());
				else if (MODULE.equalsIgnoreCase("OURSTORY"))
					BASE_URL.append(prop.get("ctg.ourstory").toString());
				else if (MODULE.equalsIgnoreCase("NATURAL"))
					BASE_URL.append(prop.get("ctg.natural").toString());
				else if (MODULE.equalsIgnoreCase("TIPS"))
					BASE_URL.append(prop.get("ctg.tips").toString());
				else if (MODULE.equalsIgnoreCase("GIVING"))
					BASE_URL.append(prop.get("ctg.giving").toString());
				// else if (MODULE.equalsIgnoreCase("HEADER") ||
				// (MODULE.equalsIgnoreCase("FOOTER")))
				// BASE_URL.append(prop.get("ctg.home").toString());
				else
					BASE_URL.append(prop.get("ctg.home").toString());

				/** PRODUCTS FOR PRODUCT DETAILS PAGE */
			} else if (null != PRODUCT && !PRODUCT.trim().isEmpty()) {
				if (PRODUCT.equalsIgnoreCase("WATERCREAM"))
					BASE_URL.append(prop.get("product.watercream").toString());
				else if (PRODUCT.equalsIgnoreCase("SILKCANVAS"))
					BASE_URL.append(prop.get("product.silkcanvas").toString());
				else if (PRODUCT.equalsIgnoreCase("PCOIL"))
					BASE_URL.append(prop.get("product.pcoil").toString());
				else if (PRODUCT.equalsIgnoreCase("PCOIL.TRAVEL"))
					BASE_URL.append(prop.get("product.pcoil.travel").toString());
				else if (PRODUCT.equalsIgnoreCase("PCOIL.SUPER"))
					BASE_URL.append(prop.get("product.pcoil.super").toString());
				else if (PRODUCT.equalsIgnoreCase("PINKSTARTER.NORMDRY"))
					BASE_URL.append(prop.get("product.pinkstarter.normdry").toString());
				else if (PRODUCT.equalsIgnoreCase("REDLIP"))
					BASE_URL.append(prop.get("product.redlip").toString());
				else if (PRODUCT.equalsIgnoreCase("DPCLEANSE"))
					BASE_URL.append(prop.get("product.dpcleanse").toString());
				else
					BASE_URL.append(prop.get("product.watercream").toString());

			} else {
				BASE_URL.append(prop.get("ctg.home").toString());
			}

		} catch (IOException ie) {
			logger.error("PROPERTY FILE MISSING: URL_HTTP" + ie.toString());
		}
		return BASE_URL.toString();
	}

	public void basicAuth(String webURL) {
		try {
			// String webPage = BrowserDriver.DEV_URL;

			String name = BrowserDriver.USERNAME;
			String password = BrowserDriver.PASSWORD;

			String authString = name + ":" + password;
			byte[] authEncBytes = Base64.encodeBase64(authString.getBytes());
			String authStringEnc = new String(authEncBytes);
			System.out.println("Base64 encoded auth string: " + authStringEnc);

			URL url = new URL(webURL);
			URLConnection urlConnection = url.openConnection();
			urlConnection.setRequestProperty("Authorization", "Basic " + authStringEnc);

			// return urlConnection.getURL();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// return null;
	}

	public boolean writeToPropertiesFile(List<Product> prodList) {

		String MODULE = System.getProperty("work.module");
		if (null != MODULE) {
			MODULE = "SHOP ALL";
		}

		Properties prop = new Properties();
		OutputStream output = null;
		try {

			output = new FileOutputStream("PRODUCT_DETAILS.properties");

			/** Sort using java 8 only */
			Collections.sort(prodList, (o1, o2) -> o1.getProductNo() - o2.getProductNo());
			// Collections.sort(prodList,new ProductSort());

			for (Product prod : prodList) {
				String PID = prod.getPid();
				if (null != PID) {
					if (null != prod.getName())
						prop.setProperty("name." + PID, prod.getName());
					if (null != prod.getSubtitle())
						prop.setProperty("subtitle." + PID, prod.getSubtitle());
					if (null != prod.getPrice())
						prop.setProperty("price." + PID, prod.getPrice() + "");
					if (0 != prod.getSize())
						prop.setProperty("size." + PID, prod.getSize() + "");
					if (0 != prod.getReviews())
						prop.setProperty("reviews." + PID, prod.getReviews() + "");
					if (null != prod.getStatus())
						prop.setProperty("status." + PID, prod.getStatus());
					if (null != prod.getFlag1())
						prop.setProperty("flag1." + PID, prod.getFlag1());
					if (null != prod.getFlag2())
						prop.setProperty("flag2." + PID, prod.getFlag2());

				}
			}

			// save properties to project root folder
			String COMMENTS = "Product Details with total count " + prodList.size();
			prop.store(output, COMMENTS);

			return true;

		} catch (IOException io) {
			// io.printStackTrace();
			logger.error(MODULE + " WRITE TO PROP FILE EXP:" + io.toString());
		} finally {
			if (output != null) {
				try {
					output.close();
				} catch (IOException e) {
					// e.printStackTrace();
					logger.error(MODULE + " WRITE TO PROP FILE FINALLY EXP:" + e.toString());
				}
			}
		}

		return false;
	}

	public void writeToFile() {
		Path path = Paths.get("/Product_Details.txt");
		try (BufferedWriter writer = Files.newBufferedWriter(path)) {
			writer.write("Hello World !!");
		} catch (IOException e) {
			// e.printStackTrace();
			logger.error("FILE WRITING EXP:'");
		}
	}

	public WebDriver getWebDriver() {
		logger.info("inside getWebDriver");
		WebDriver driver = null;
		if (null != System.getProperty("test.type")) {
			/** Load Testing */
			if (System.getProperty("test.type").equals("load.xlt")) {
				logger.info("Load Testing : XLT");
				driver = BrowserDriver.getXLTChromeWebDriver();
				// driver = new XltDriver();
				/** Browser Automation */
			} else if (System.getProperty("test.type").equals("browser.chrome")) {
				logger.info("Browser Automation : Google Chrome");
				driver = BrowserDriver.getChromeWebDriver();
			}
		} else {
			driver = BrowserDriver.getChromeWebDriver();
			/** Customly needed for JUnit Exec */
			if (null == System.getProperty("work.env")) {
				System.setProperty("work.env", "DEV_SEC");
			}

			// if (null == System.getProperty("work.module")) {
			// System.setProperty("work.module", "FOOTER");
			// }

//			if (null == System.getProperty("product.name")) {
//				System.setProperty("product.name", "SILKCANVAS");
//			}
		}

		/** Setting System properties for JUnit Execution */
		// Commented as it affects SSL Authentication
		/*
		 * if (null == System.getProperty("work.env")) { Properties systemProp =
		 * new Properties(); try { systemProp.load(new
		 * FileInputStream(getClass().getResource("/SYSTEM.properties").getFile(
		 * ))); System.setProperties(systemProp); } catch (FileNotFoundException
		 * e) { logger.error("SYSTEM PROPERTIES File Not Found:" +
		 * e.toString()); } catch (IOException e) { logger.error(
		 * "SYSTEM PROPERTIES IO Exp:" + e.toString()); } catch
		 * (NullPointerException e) { logger.error("SYSTEM PROPERTIES is NULL:"
		 * + e.toString()); } }
		 */

		return driver;
	}

	public boolean outputLogs(String className) {
		boolean FLAG1 = false;
		boolean FLAG2 = false;
		boolean APPEND = true;
		String SUCCESS_FILE = "/logs-dir/success.log";
		String ERROR_FILE = "/logs-dir/error.log";

		Logger logger2 = Logger.getLogger(className);
		LogManager logManager = LogManager.getLogManager();
		// FileHandler fileHandler = new FileHandler();
		try {
			FileHandler fileHandler = new FileHandler(SUCCESS_FILE, APPEND);
			// logger2.addHandler(fileHandler);
			// logger2.addAppender(newAppender);
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (FLAG1 && FLAG2)
			return true;
		return false;
	}

	public String getTestCase() {
		String val = null;
		Properties prop = new Properties();

		try {
			prop.load(new FileInputStream(getClass().getResource("/TESTCASE.properties").getFile()));

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return val;
	}
}
