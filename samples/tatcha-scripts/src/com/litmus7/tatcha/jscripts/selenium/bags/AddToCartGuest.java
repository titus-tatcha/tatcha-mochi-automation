package com.litmus7.tatcha.jscripts.selenium.bags;

import java.util.regex.Pattern;

import javax.activity.InvalidActivityException;

import java.util.ArrayList;
import java.util.List;
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

import com.litmus7.tatcha.jscripts.dob.Product;
import com.litmus7.tatcha.jscripts.selenium.exception.InvalidElementException;
import com.litmus7.tatcha.utils.BrowserDriver;

public class AddToCartGuest {
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();
  private ArrayList<Product> prodList = new ArrayList<Product>();
  private ArrayList<Product> prodEqualList = new ArrayList<Product>();
  private double subTotal = 0;
  private int productId = 10001;
	
  @Before
  public void setUp() throws Exception {
//    driver = new FirefoxDriver();
//    baseUrl = "https://development-na01-tatcha.demandware.net/";
    driver = BrowserDriver.getChromeWebDriver();
    baseUrl = BrowserDriver.BASE_URL;
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }

  @Test
  public void testAddToCartGuest() throws Exception {
//    driver.get(baseUrl + "/on/demandware.store/Sites-tatcha-Site/default/Login-Show?original=%2fs%2ftatcha%2faccount%3flang%3ddefault");
    driver.get(baseUrl);

    /**  ------------ Adding Products to Mini Cart ------------ */
	Actions action = new Actions(driver);
	int prodCount = 3;
	
	ArrayList<String> productNames = new ArrayList<String>();
	productNames.add("Camellia Beauty Oil");
	productNames.add("Yume Kimono Cuff - Gold");
	productNames.add("The Brightening Set");
	
	 WebDriverWait wait = (WebDriverWait) new WebDriverWait(driver, 3);
	for(String productName: productNames){	
		wait.until(ExpectedConditions.visibilityOfElementLocated((By.linkText("SHOP"))));
		try{
			WebElement element = driver.findElement(By.linkText("SHOP"));
		    action.moveToElement(element).build().perform();
		    driver.findElement(By.linkText("Shop All")).click();
		    /** now only the first page items are selected since PROD page not in this sprint */
		    
		    driver.findElement(By.cssSelector("img[alt=\""+productName+"\"]")).click();
		    
		    // going to PDP
		    Product product = new Product();
		    product.setPid("P"+(productId++));
		    product.setName(productName);
		    testPDPforGuest(driver, product);
		    testBAGforGuest(driver,prodList);

		    /** PREV CODE */
//		    /** Getting Price */
//		    String productPrice = driver.findElement(By.xpath("//*[@id='product-content']/div[2]/div/div[1]/span")).getText();
//		    System.out.println("productPrice "+productPrice);
//		    /** Getting Quantity */
//		    Select dropdown = new Select(driver.findElement(By.id("Quantity")));
//		    dropdown.selectByVisibleText("1");
//		    
//		    /** Calculating Subtotal */
//		    String amount = productPrice.trim().substring(1);
//		    System.out.println("amount "+amount);
//		    subTotal += Double.parseDouble(amount);
//		    
//		    /** Creating Product Object */
//		    Product product = new Product();
//		    product.setPid("P"+(productId++));
//		    product.setName(productName);
//		    product.setPrice(productPrice);
//		    prodList.add(product);
//		    driver.findElement(By.id("add-to-cart")).click();
		    
		}catch(NoSuchElementException ne){
			System.err.println("No Element >> "+ne.toString());
		}
	}
	
    // Mini Cart item count
    String itemCount = driver.findElement(By.cssSelector("div.badge.bag-count")).getText();
    assertEquals(prodCount, itemCount+"");
   
    // Going to Bag Page
    driver.findElement(By.linkText("1")).click();
    driver.findElement(By.name("dwfrm_cart_shipments_i0_items_i0_deleteProduct")).click();
    
    System.out.println("subTotal "+subTotal);
	
//	#mini-cart > div.mini-cart-total > a > svg
	
// 	    #cd2a1eba3cb9a2e22ac2ef1082 > div.product-price > span.product-sales-price

//    driver.findElement(By.cssSelector("img[alt=\"Kissed with Gold Set\"]")).click();
//    driver.findElement(By.id("add-to-cart")).click();
//    
//    driver.findElement(By.linkText("Shop All")).click();
//    driver.findElement(By.cssSelector("a.thumb-link > img[alt=\"Soothing Silk Hand Cream\"]")).click();
//    driver.findElement(By.id("add-to-cart")).click();
//    
//    driver.findElement(By.cssSelector("div.badge.bag-count")).click();
//    
//    driver.findElement(By.linkText("Shop All")).click();
//    driver.findElement(By.cssSelector("img[alt=\"The Essence\"]")).click();
//    driver.findElement(By.id("add-to-cart")).click();
//    
    /** ----------------------- Mini Cart Checkings ------------------- */
    // checking items
	
//	for(String imageName: imageNames){	
    
    
    int cartItem = 1;
    prodList.size();
	for(Product product: prodList){	
		try{	   
			/** Hovering over mini cart*/
			By byeEle = By.xpath("//*[@id='mini-cart']/div[1]/a");
			wait.until(ExpectedConditions.visibilityOfElementLocated(byeEle));
	
			WebElement element = driver.findElement(byeEle);
		    action.moveToElement(element).build().perform();   
//		    assertEquals(product.getName(),driver.findElement(By.linkText(product.getName())).getText());
//		    assert(driver.findElement(By.cssSelector("div.dropdown-bag-item-qty-price")).getText().contains(product.getPrice()));
		    driver.findElement(By.xpath("//*[@id='mini-cart']/div[2]/div[2]/div["+cartItem+"]/div[2]/h5/a")).click();
		    cartItem++;
		    /** PDP Page */ 
		    String productNamePDP = driver.findElement(By.xpath("//*[@id='product-content']/div[2]/h1")).getText();
		    String productPricePDP = driver.findElement(By.xpath("//*[@id='product-content']/div[2]/div/div[1]/span")).getText();
		    
		    if(prodEqualList.contains(product)){
				assertEquals(product.getName(),productNamePDP);
				assertEquals(product.getPrice(),productPricePDP);
		    }
						
//		    assertEquals("Qty: 1 | $85.50", driver.findElement(By.cssSelector("div.dropdown-bag-item-qty-price")).getText());
    
		}catch(NoSuchElementException ne){
			System.err.println("No Element >> "+ne.toString());
		}
			
	}
	
//    assertEquals("The Essence", driver.findElement(By.linkText("The Essence")).getText());
//    assertEquals("Qty:", driver.findElement(By.cssSelector("span.data-label")).getText());
//    assertEquals("Qty: 1 | $85.50", driver.findElement(By.cssSelector("div.dropdown-bag-item-qty-price")).getText());
//    
//    assertEquals("", driver.findElement(By.cssSelector("img.dropdown-bag-item-img-small")).getText());
//    driver.findElement(By.linkText("The Essence")).click();
//    // next item
//    assertEquals("", driver.findElement(By.xpath("//img[@alt='Kissed with Gold Set']")).getText());
//    assertEquals("Kissed with Gold Set", driver.findElement(By.linkText("Kissed with Gold Set")).getText());
//    assertEquals("Qty:", driver.findElement(By.xpath("//div[@id='mini-cart']/div[2]/div[2]/div[2]/div[2]/div/span")).getText());
//    assertEquals("Qty: 1 | $42.00", driver.findElement(By.xpath("//div[@id='mini-cart']/div[2]/div[2]/div[2]/div[2]/div")).getText());
//    driver.findElement(By.linkText("Kissed with Gold Set")).click();
    
    // cart page
//    assertEquals("4 Item(s) | Subtotal: $194.50", driver.findElement(By.cssSelector("div.dropdown-bag-totals.mini-cart-totals")).getText());
    // CTA Select Free Sample
    driver.findElement(By.linkText("Select Free Samples")).click();
    assertEquals("SHOPPING BAG", driver.findElement(By.cssSelector("h1")).getText());
    // CTA Checkout
    driver.findElement(By.linkText("Checkout")).click();
    assertEquals("Select an Address", driver.findElement(By.cssSelector("div.select-address.form-row > label")).getText());
    // click to Bag Page
    // ERROR: Caught exception [Error: locator strategy either id or name must be specified explicitly.]
  }

  /** Checking PDP in detail 
 * @throws InvalidElementException */
  private void testPDPforGuest(WebDriver driver,Product product) throws InvalidElementException{
	    
	  // Marketing Flags
	  
	    String[] marketingFlags = { "New", "Bestseller", "Limited Edition", "Exclusive" };
	    WebElement flag1Ele1 = driver.findElement(By.cssSelector("div.product-summary-desktop > div.product-marketing-flag-block > span.product-marketing-flag"));
	    String marketFlag1 = flag1Ele1.getText().trim();
	    WebElement flag1Ele2 = driver.findElement(By.xpath("//div[@id='product-content']/div[2]/div/span[3]"));
	    String marketFlag2 = flag1Ele2.getText().trim();
	    
//	    assertEquals("New", marketFlag1);
//	    assertEquals("Bestseller", marketFlag2);
	    
	    /** Checking whether Marketing flag if Present is listing any of the Marketing Message */
	    
	    boolean flag1Validity = false;
	    boolean flag2Validity = false;
	    
	    if(null != marketFlag1 && !marketFlag1.isEmpty()){
	    	 for(String flagName : marketingFlags){
	 	    	if(flagName.equalsIgnoreCase(marketFlag1)){
	 	    		flag1Validity = true;
	 	    		break;
	 	    	}
	 	    }	
	    }else{
	    	flag1Validity = true;
	    }
	    
	    if(null != marketFlag1 && !marketFlag1.isEmpty()){
		    for(String flagName : marketingFlags){
		    	if(flagName.equalsIgnoreCase(marketFlag2)){
		    		flag2Validity = true;
		    		break;
		    	}
		    }
	    }else{
	    	flag2Validity = true;
	    }

	    /** Raising Invalid Market Flags as Exceptions */
	    if(!flag1Validity){
	    	throw new InvalidElementException("Marketing Flag 1 is invalid");
	    }
	    
	    if(!flag2Validity){
	    	throw new InvalidElementException("Marketing Flag 2 is invalid");
	    }
	    
	    
	    // Product Title
//	    assertEquals("One Step Camellia Cleansing Oil Tatcha", driver.findElement(By.cssSelector("div.product-summary-desktop > h1.product-name")).getText());
	    WebElement prodTitleEle = driver.findElement(By.cssSelector("div.product-summary-desktop > h1.product-name"));
	    String prodTitle = prodTitleEle.getText().toLowerCase();
	    // checking product name same
	    assert(prodTitle.contains(product.getName().toLowerCase()));  

	    // Subtitlel
	    WebElement prodsubtitleEle = driver.findElement(By.cssSelector("div.product-summary-desktop > h1.product-name > span.product-subtitle"));
	    product.setSubtitle(prodsubtitleEle.getText());
//	    assertEquals(product.getSubtitle().toLowerCase(), prodsubtitleEle.getText().toLowerCase()); 
//	    assertEquals("Tatcha", driver.findElement(By.cssSelector("div.product-summary-desktop > h1.product-name > span.product-subtitle")).getText());
	   	  
//	    product-price
//	    #product-content > div.product-summary-desktop > div > div.product-price > span
	  //*[@id="product-content"]/div[2]/div/div[1]/span
//	    .//*[@id='product-content']/div[2]/div/div[1]/span
	    
	    String productPrice = driver.findElement(By.xpath("//*[@id='product-content']/div[2]/div/div[1]/span")).getText();
	    product.setPrice(productPrice);
	    
	    // Product Price ( can be in a range $15.00 - $45.00)
	//    assertEquals("$45.00", driver.findElement(By.cssSelector("div.product-summary-desktop > div.product-price-block > div.product-price > span.price-sales")).getText());
	   	      
//	    Skin Type
	    //*[@id='product-content']/div[3]/div[1]/div[2]/div/div/div/div/div[1]/a
	    //*[@id='product-content']/div[3]/div[1]/div[2]/div/div/div/div/div[2]/a
	    //*[@id='product-content']/div[3]/div[1]/div[2]/div/div/div/div/div[3]/a
	    //*[@id='product-content']/div[3]/div[1]/div[2]/div/div/div/div/div[4]/a

	    boolean skinValidity = false;
	    try{
		    String[] skinVariants = {"Normal","Combination","Dry","Sensitive","Oily"};
	    	WebElement skinTitle = driver.findElement(By.xpath("//*[@id='product-content']/div[3]/div[1]/div[1]"));
    		if(null != skinTitle.getText() && "size".equalsIgnoreCase(skinTitle.getText())){
    			
			    for(int i=1;i<5;i++){
			    	WebElement skinEle = driver.findElement(By.xpath("//*[@id='product-content']/div[3]/div[1]/div[2]/div/div/div/div/div["+i+"]/a"));
			    	if(null != skinEle){
				    	String skinVariant = skinEle.getText().trim();
				    	if(elementPresent(skinVariants, skinVariant)){
				    		skinValidity = true;
				    		break;
				    	}
			    	}
			    }
    		}
	    }catch(NoSuchElementException ne){
	    	/** Skin Variants not present */
	    	skinValidity = true;
	    }
	    
	    boolean sizeValidity = false;
	    try{
		    	String[] sizeVariants = {"60g / 2.1 oz.", "10g / .35 oz."};
	    		WebElement sizeTitle = driver.findElement(By.xpath("//*[@id='product-content']/div[3]/div[2]/div[1]"));
	    		if(null != sizeTitle.getText() && "size".equalsIgnoreCase(sizeTitle.getText())){
	    		    
	    		    for(int i=1;i<3;i++){
	    		    	WebElement sizeEle = driver.findElement(By.xpath("//*[@id='product-content']/div[3]/div[2]/div[2]/div/div/div/div/div["+i+"]/a"));
	    		    	if(null != sizeEle){
	    			    	String sizeVariant = sizeEle.getText().trim();
	    			    	if(elementPresent(sizeVariants, sizeVariant)){
	    			    		sizeValidity = true;
	    			    		break;
	    			    	}
	    		    	}
	    		    }
	    		}
	    }catch(NoSuchElementException ne){
	    	/** Size Variants not present */
	    	sizeValidity = true;
	    }

	  //*[@id="product-content"]/div[3]/div[2]/div[1]
//	    #product-content > div.product-variations > div:nth-child(2) > div.form-label
	    
	    // Product Variants (Size only now seen) Optional

	  
//	   Size 
	    //*[@id='product-content']/div[3]/div[2]/div[2]/div/div/div/div/div[1]/a
	    //*[@id='product-content']/div[3]/div[2]/div[2]/div/div/div/div/div[2]/a	   
		

	    /** Raising Invalid Skin & Size Variants as Exceptions */
	    
	    if(!skinValidity){
	    	throw new InvalidElementException("Skin Variant is invalid");
	    } 
	    if(!sizeValidity){
	    	throw new InvalidElementException("Size Variant is invalid");
	    } 
	    
//	    assertEquals("60g / 2.1 oz.", .getText());
//	    assertEquals("10g / .35 oz.", driver.findElement(By.linkText("10g / .35 oz.")).getText());
//	    assertEquals("60g / 2.1 oz.", driver.findElement(By.linkText("60g / 2.1 oz.")).getText());
	    
	    // Quantity Fields
	    
	    boolean notifyQty = false;	
	    try{
		    String notifyMessage = "Only A Few Left";
		    WebElement  notifyMsgEle = driver.findElement(By.cssSelector("div.product-inventory-flag"));
		  //*[@id="dwfrm_product_addtocart_d0ctugdkwyxu"]/fieldset/div/div[1] //id[contains(text(),My Account')]
		    String notifyMsg = notifyMsgEle.getText();
		    if(notifyMessage.toLowerCase().equals(notifyMsg.toLowerCase())){
		    	notifyQty = true;
		    }
		 }catch(NoSuchElementException ne){
	    	notifyQty = true;
	    }
	    
	    if(!notifyQty){
	    	throw new InvalidElementException("Quantity Notification Message is invalid");
	    }  
	    
	    /** Getting Quantity DDL */
	    Select dropdown = new Select(driver.findElement(By.id("Quantity")));
	    dropdown.selectByVisibleText("1");
//	    dropdown.selectByIndex(2);
	    
//	    assertEquals("1 2 3 4 5", driver.findElement(By.id("Quantity")).getText());
	    
	    boolean limitMessageValidity = false;
	    try{
		    String[] limitQuantityMsgs = {"Limit 1 per household.","Limit 3 per household."};	    
		    WebElement limitQuantityEle = driver.findElement(By.cssSelector("div.help-block"));
	    	if(null != limitQuantityEle){
		    	String limitMsg = limitQuantityEle.getText().trim();
		    	if(elementPresent(limitQuantityMsgs, limitMsg)){
		    		limitMessageValidity = true;
		    	}
	    	}		    
	    }catch(NoSuchElementException ne){
	    	limitMessageValidity = true;
	    }
	    
	    if(!limitMessageValidity){
	    	throw new InvalidElementException("Limit Quantity Message is invalid");
	    }  
	    
	    /** Calculating Subtotal */
	    String amount = productPrice.trim().substring(1);
	    System.out.println("amount "+amount);
	    subTotal += Double.parseDouble(amount);
	    
	    /** Updating Product Object */
	    product.setPrice(productPrice);
	    prodList.add(product);
	    
	    /** Adding Product to Mini Cart*/
	    driver.findElement(By.id("add-to-cart")).click();
	    
	    // Reviews
//	    assertEquals("9,999 Reviews", driver.findElement(By.cssSelector("div.product-summary-desktop > div.product-price-block > div.product-rating-summary > a.tatcha-animation.")).getText());
	    WebElement reviewEle = driver.findElement(By.xpath("//*[@id='product-content']/div[2]/div[2]/div[2]/a"));
	    String reviews  = reviewEle.getText();
	    assertEquals("9,999 Reviews",reviews);
	    
	    // Main Image & Thumbnails
//	    boolean imgValidity = false;
	    try{
		    WebElement imgMainEle = driver.findElement(By.xpath("//*[@id='image-container']/img"));
//		    imgValidity = true;
//		    imgMainEle.click();
	    }catch(NoSuchElementException ne){
//	    	imgValidity = false;
	    	throw ne;
	    }
    
/** thumbnails are not scene in this sprint 4 */	    
//	    assertEquals("", driver.findElement(By.cssSelector("img.primary-image.img-responsive")).getText());
//	    assertEquals("", driver.findElement(By.cssSelector("img.productthumbnail")).getText());
//	    driver.findElement(By.xpath("(//img[@alt='One Step Camellia Cleansing Oil'])[3]")).click();
//	    assertEquals("", driver.findElement(By.cssSelector("img.primary-image.img-responsive")).getText());
//	    driver.findElement(By.cssSelector("img.productthumbnail")).click(); 	    
}
  
  private void testBAGforGuest(WebDriver driver, List<Product> productList) throws Exception {
	  /** Click on Bag Cart button */
	  	WebDriverWait wait = (WebDriverWait) new WebDriverWait(driver, 3);
		By byeEle = By.xpath("//*[@id='mini-cart']/div[1]/a");
		wait.until(ExpectedConditions.visibilityOfElementLocated(byeEle));

		WebElement element = driver.findElement(byeEle);
		element.click();
		
//	  	WebElement clickEle = driver.findElement(By.xpath("//*[@id='mini-cart']/div[1]/a/svg"));
//	  	clickEle.click();
	    
	  	assertEquals("SHOPPING BAG", driver.findElement(By.cssSelector("h1")).getText());
	  	
	  	String itemCount = driver.findElement(By.cssSelector("div.badge.bag-count")).getText();
	  	int productCount = 0;
	  	if(null!=itemCount){
		  	productCount = Integer.parseInt(itemCount);
	  	}
	  	/**  Empty Bag */
	  	if(productCount==0){
		  	String EMPTY_BAG_MESSAGE = "You have no items in your shopping bag.";
		    assertEquals(EMPTY_BAG_MESSAGE, driver.findElement(By.cssSelector("p")).getText());
		    assertEquals("Continue Shopping", driver.findElement(By.name("dwfrm_cart_continueShopping")).getText());
		    driver.findElement(By.name("dwfrm_cart_continueShopping")).click();
		    assertEquals("Showing 1 - 10 of 10 Results", driver.findElement(By.cssSelector("div.results-hits")).getText());
		    driver.navigate().back();
		   
	  	}else{
	  		
	 // Marketing Messages
	    String marketingMessage = "$20 away from getting free US shipping. | \n $50 away from a free gift with purchase.";
	    try{
		    String marketingText = driver.findElement(By.xpath("//form/div/div/div")).getText();
		    if(null != marketingText && !marketingText.isEmpty()){
			    assertEquals(marketingMessage,marketingText);
		    }
	    }catch(NoSuchElementException ne){
	    	System.err.println("Bag-Page:No Marketing Banner "+ne.toString());
	    }
	    // COMPLEMENTARY SAMPLES
	    String COMPLEMENTARY_MESSAGE = "CHOOSE 3 COMPLIMENTARY SAMPLES";
	    String COMPLEMENTARY_HEADING = "ADD 3 COMPLIMENTARY SAMPLES WITH YOUR ORDER";
	    assertEquals(COMPLEMENTARY_MESSAGE, driver.findElement(By.cssSelector("h4.slider-title")).getText());
	    
	    driver.findElement(By.id("bonusModalLink")).click();
	    WebElement complementaryHeading = driver.findElement(By.cssSelector("#bonusModal > div.modal-dialog.modal-lg > div.modal-content > div.modal-header > h4.modal-title > strong"));
	    assertEquals(COMPLEMENTARY_HEADING, complementaryHeading.getText());
	    // Cancel Samples
	    driver.findElement(By.cssSelector("button.btn.btn-default")).click();
	    // Adding Samples
	    driver.findElement(By.id("bonusModalLink")).click();
	    
	    /** There is a confusion of how to locate Select buttons in Complementary Window */
	    
//	    driver.findElement(By.xpath("//div[@id='item-59d6fb66d4ea34d749acb68399']/div[2]/label/span")).click();
//	    driver.findElement(By.xpath("//div[@id='item-92a974c9cef6e84478e84b2326']/div[2]/label")).click();
//	    driver.findElement(By.xpath("//div[@id='item-2ac1e7b494714cdd6b26737de5']/div[2]/label")).click();
//	    driver.findElement(By.xpath("//id[contains(text(),'item-')]")).click();
	  
	    //*[@id="item-59d6fb66d4ea34d749acb68399"]/div[2]/label/span
//	    #item-59d6fb66d4ea34d749acb68399 > div.sample-select > label > span
	  //*[@id="item-92a974c9cef6e84478e84b2326"]/div[2]/label/span
//	    #item-92a974c9cef6e84478e84b2326 > div.sample-select > label > span
	 
//	    driver.findElement(By.xpath("//id[contains(text(),'item-')]")).click();
	    		//*[@id="item-59d6fb66d4ea34d749acb68399"]/div[2]/label/span
	    		
	    driver.findElement(By.id("submit-sample-items")).click();
	    
	    // PRODUCT LIST
	    
	    int index = 0;
	    for(Product product : productList){	    	
		    // checking 1st product
//		    assertEquals("", driver.findElement(By.cssSelector("a > img.img-responsive.product-img")).getText());
	    	WebElement titleEle = driver.findElement(By.cssSelector("h4.product-name > a[title=\"Go to Product: "+product.getName()+"\"]"));
		    assertEquals(product.getName(), titleEle.getText());
		    titleEle.click();
	//	    	assertEquals("Camellia Beauty Oil subtitle for testing", driver.findElement(By.cssSelector("div.product-summary-desktop > h1.product-name")).getText());
			    assert(driver.findElement(By.cssSelector("div.product-summary-desktop > h1.product-name")).getText().contains(product.getName()));
		    driver.navigate().back();
		    
		   
		    // variants
		    assertEquals("Combination", driver.findElement(By.linkText("Combination")).getText());
		    assertEquals("60g / 2.1 oz.", driver.findElement(By.linkText("60g / 2.1 oz.")).getText());
		    
		    // quantity
		       
//		    driver.findElement(By.name("dwfrm_cart_updateCart")).click();		    
		    assertEquals("Qty", driver.findElement(By.xpath("//div[@id='cart-table']/div/div[4]/div[2]/div/div[2]/div[2]/div/div/label")).getText());
		    WebElement quantityEle = driver.findElement(By.name("dwfrm_cart_shipments_i0_items_i"+(index++)+"_quantity"));
		    assertEquals("1 2 3 4 5", quantityEle.getText());
		    Select dropdown = new Select(quantityEle);
		    dropdown.selectByVisibleText("1");
	//	    dropdown.selectByIndex(2);
		    
	//	    assertEquals("1 2 3 4 5", driver.findElement(By.name("dwfrm_cart_shipments_i0_items_i1_quantity")).getText());
		 
		    // marketing banner
		    WebElement marketBannerEle = driver.findElement(By.cssSelector("div.product-marketing-banner-text"));
		    String marketBannerText = "Category Specific Promotion|\n Complimentary Samples";
		    assertEquals(marketBannerText, marketBannerEle.getText());

		    // autodelivery
		    driver.findElement(By.name("dwfrm_smartorderrefill_hasOsfSmartOrderRefill")).click();
		    WebElement autodeliveryLabel = driver.findElement(By.cssSelector("div.checkbox > label"));
		    assertEquals("Auto-Delivery", autodeliveryLabel.getText());
		    driver.findElement(By.cssSelector("button[name=\"dwfrm_smartorderrefill_update\"]")).click();
		    
		    // autodelivery tooltip
		    
		    // ERROR: Caught exception [Error: locator strategy either id or name must be specified explicitly.]
		    String TOOLTIP_HEADING = "The Tatcha Replenishment Service";
		    String TOOLTIP_DESC = "Enroll now and enjoy the convenience of auto-delivery and "
		    		+ "an exclusive gift with every shipment when you select our complimentary replenishment service. "
		    		+ "Update your frequency or cancel anytime by calling 1-888-739-2932 ext. 1.";
		    WebElement tooltipHeadingEle = driver.findElement(By.cssSelector("#sorModal > div.modal-dialog > div.modal-content > div.modal-header > h4.modal-title > strong"));
		    WebElement tooltipDescEle = driver.findElement(By.cssSelector("#sorModal > div.modal-dialog > div.modal-content > div.modal-body"));
		    assertEquals(TOOLTIP_HEADING, tooltipHeadingEle.getText());
		    assertEquals(TOOLTIP_DESC, tooltipDescEle.getText());
		    WebElement tooltipCloseBtn = driver.findElement(By.cssSelector("#sorModal > div.modal-dialog > div.modal-content > div.modal-header > button.close"));
		    tooltipCloseBtn.click();
		    
		    // ERROR: Caught exception [Error: locator strategy either id or name must be specified explicitly.]
		    driver.findElement(By.id("sorModal")).click();
		    
		    // frequency
		    WebElement freequencyEle = driver.findElement(By.name("dwfrm_smartorderrefill_hasOsfSmartOrderRefill"));
	//	    String days = "Every 30 days";
	//	    String days = "Every 60 days";
		    String days = "Every 90 days";
		    new Select(freequencyEle).selectByVisibleText(days);
	//	    new Select(driver.findElement(By.name("dwfrm_smartorderrefill_OsfSorSmartOrderRefillInterval"))).selectByVisibleText("Every 90 days");
		    
		    // price
		    assertEquals( product.getPrice(), driver.findElement(By.cssSelector("span.price-total")).getText());
	//	    assertEquals("$65.00", driver.findElement(By.cssSelector("span.price-total")).getText());
		    
//		    buy 3 get 5% off
		    
		    assertEquals("$285.00", driver.findElement(By.cssSelector("span.price-unadjusted > span")).getText());
		    assertEquals("$256.50", driver.findElement(By.cssSelector("span.price-adjusted-total > span")).getText());
		    
		    
		    // click on product img or variants navigates to PDP
		    driver.findElement(By.cssSelector("a > img.img-responsive.product-img")).click();
	    		assertEquals(product.getName(), driver.findElement(By.cssSelector("div.product-summary-desktop > h1.product-name")).getText());
	//	    	assertEquals("Classic Rice Enzyme Powder", driver.findElement(By.cssSelector("div.product-summary-desktop > h1.product-name")).getText());
		    driver.navigate().back();
		    
		    driver.findElement(By.linkText("Combination")).click();
				assertEquals(product.getName(), driver.findElement(By.cssSelector("div.product-summary-desktop > h1.product-name")).getText());
	//	    	assertEquals("Classic Rice Enzyme Powder", driver.findElement(By.cssSelector("div.product-summary-desktop > h1.product-name")).getText());
		    driver.navigate().back();
		    
		    driver.findElement(By.linkText("60g / 2.1 oz.")).click();
		    	assertEquals(product.getName(), driver.findElement(By.cssSelector("div.product-summary-desktop > h1.product-name")).getText());
	//	    	assertEquals("Classic Rice Enzyme Powder", driver.findElement(By.cssSelector("div.product-summary-desktop > h1.product-name")).getText());
		    driver.navigate().back();
		    
	    }	// End of Product List Iteration in Bag Page
	    
	    
	    // checking Error Message
	    String ERROR_MESSAGE = "Your cart contains one or more smart refill products. "
	    		+ "Please login or create a new account to continue checkout process.";
	    WebElement errorMsgEle = driver.findElement(By.cssSelector("p"));
	    assertEquals(ERROR_MESSAGE, errorMsgEle.getText());
	    driver.findElement(By.cssSelector("button[name=\"dwfrm_smartorderrefill_update\"]")).click();
	    // ERROR: Caught exception [Error: Dom locators are not implemented yet!]
	    driver.findElement(By.cssSelector("button[name=\"dwfrm_smartorderrefill_update\"]")).click();
	    // ERROR: Caught exception [Error: Dom locators are not implemented yet!]
	    driver.findElement(By.cssSelector("button[name=\"dwfrm_smartorderrefill_update\"]")).click();
	    // ERROR: Caught exception [Error: Dom locators are not implemented yet!]
	    
	    
	    // GIFT WRAP
	    
	    String GIFT_HEADING = "Tatcha Gift Wrapping";
	    WebElement giftEle = driver.findElement(By.cssSelector("div.col-xs-9 > h4"));
	    assertEquals(GIFT_HEADING, giftEle.getText());
	    // gift wrap tooltip missing
	    // ERROR: Caught exception [Error: locator strategy either id or name must be specified explicitly.]
	    
	    // adding Gift wrap and checking $5 added ?
	    driver.findElement(By.id("giftwrap-toggle")).click();
	    driver.findElement(By.id("add-giftwrap-button")).click();
	    WebElement giftedAmountEle = driver.findElement(By.xpath("//div[@id='cart-table']/div[2]/div/div[2]/table[2]/tbody/tr/td"));
	    double giftedAmount = subTotal+5.00;
	    assertEquals("$"+giftedAmount, giftedAmountEle.getText());
	    // removing Gift wrap and checking $5 removed 
	    driver.findElement(By.id("giftwrap-toggle")).click();
	    driver.findElement(By.id("remove-giftwrap-button")).click();
	    giftedAmount = subTotal-5.00;
	    assertEquals("$"+giftedAmount, giftedAmountEle.getText());
	    
	    // checking hand written message
	    String GIFT_FROM = "Jack";
	    String GIFT_TO = "Rose";
	    String GIFT_MSG = "Best Wishes";
	    
	    driver.findElement(By.id("giftwrap-toggle")).click();
	    driver.findElement(By.id("add-giftwrap-button")).click();
	    driver.findElement(By.id("hasGiftMessage")).click();
	    driver.findElement(By.cssSelector("div.checkbox.gift-message  > label")).click();
	    assertEquals("From", driver.findElement(By.cssSelector("div.col-sm-6 > div.form-group > label.control-label")).getText());
	    driver.findElement(By.id("giftFrom")).clear();
	    driver.findElement(By.id("giftFrom")).sendKeys(GIFT_FROM);
	    assertEquals("To", driver.findElement(By.xpath("//div[@id='gift-message-form']/div/div[2]/div/label")).getText());
	    driver.findElement(By.id("giftTo")).clear();
	    driver.findElement(By.id("giftTo")).sendKeys(GIFT_TO);
	    assertEquals("Message", driver.findElement(By.cssSelector("div.col-xs-12 > div.form-group > label.control-label")).getText());
	    driver.findElement(By.id("giftMessage")).clear();
	    driver.findElement(By.id("giftMessage")).sendKeys(GIFT_MSG);
	    // End of GIFT WRAP
	    
	    // SUMMARY
	    assertEquals("Summary", driver.findElement(By.cssSelector("h2.panel-title")).getText());
	    assertEquals(prodList.size()+" items", driver.findElement(By.cssSelector("div.panel-status")).getText());
	    assertEquals("Subtotal", driver.findElement(By.cssSelector("th.data-label")).getText());
	    assertEquals("$"+subTotal, driver.findElement(By.cssSelector("td")).getText());
	    /** Tax Calculations Doubtful */
	    assertEquals("Shipping", driver.findElement(By.xpath("//div[@id='cart-table']/div[2]/div/div[2]/table/tbody/tr[2]/th")).getText());
	    assertEquals("TBD", driver.findElement(By.xpath("//div[@id='cart-table']/div[2]/div/div[2]/table/tbody/tr[2]/td")).getText());
	    assertEquals("Tax", driver.findElement(By.xpath("//div[@id='cart-table']/div[2]/div/div[2]/table/tbody/tr[3]/th")).getText());
	    assertEquals("TBD", driver.findElement(By.xpath("//div[@id='cart-table']/div[2]/div/div[2]/table/tbody/tr[3]/td")).getText());
	    
	    // checking Promcode deducting amount
	    String PROMOCODE_LIMIT_MSG = "Limit one promotional code per order\\.$";
	    String PROMOCODE_COUPEN = "TATCHANEW";
	    String PROMOCODE_ERROR_MESSAGE = "Invalid coupon code";
	    WebElement promoCodeLabelEle = driver.findElement(By.cssSelector("div.checkout-promo-code > div.form-group > label.control-label"));
	    assertEquals("Promo Code", promoCodeLabelEle.getText());
	    
	    assertTrue(driver.findElement(By.cssSelector("span.help-block")).getText().matches("^exact:[\\s\\S]*"+PROMOCODE_LIMIT_MSG));
	    driver.findElement(By.id("dwfrm_cart_couponCode")).clear();
	    driver.findElement(By.id("dwfrm_cart_couponCode")).sendKeys(PROMOCODE_COUPEN);
	    driver.findElement(By.id("add-coupon")).click();
	    WebElement promocodeErrorMsgEle = driver.findElement(By.xpath("//html[@id='ext-gen45']/body/main/div/div/div"));
	    assertEquals(PROMOCODE_ERROR_MESSAGE, promocodeErrorMsgEle.getText());
	    driver.findElement(By.id("dwfrm_cart_couponCode")).clear();
	    PROMOCODE_COUPEN = "TATCHA";
	    driver.findElement(By.id("dwfrm_cart_couponCode")).sendKeys(PROMOCODE_COUPEN);
	    driver.findElement(By.id("add-coupon")).click();
	    double DISCOUNT_AMOUNT = 10.00;
	    try{
	    	assertEquals("Discount", driver.findElement(By.xpath("//div[@id='cart-table']/div[2]/div/div[2]/table/tbody/tr[2]/th")).getText());
	    	assertEquals("$"+DISCOUNT_AMOUNT, driver.findElement(By.xpath("//div[@id='cart-table']/div[2]/div/div[2]/table/tbody/tr[2]/td")).getText());
	    }catch(NoSuchElementException ne){
	    	
	    }
	    
	    assertEquals("Estimated Total", driver.findElement(By.xpath("//div[@id='cart-table']/div[2]/div/div[2]/table[2]/tbody/tr/th")).getText());
	    assertEquals("$316.50", driver.findElement(By.xpath("//div[@id='cart-table']/div[2]/div/div[2]/table[2]/tbody/tr/td")).getText());
	   
	    // checking Error message
	    assertEquals(ERROR_MESSAGE, driver.findElement(By.cssSelector("p")).getText());
	    // removing items
	    driver.findElement(By.name("dwfrm_cart_shipments_i0_items_i0_deleteProduct")).click();
	    driver.findElement(By.name("dwfrm_cart_shipments_i0_items_i0_deleteProduct")).click();
	    // checkout
	    driver.findElement(By.name("dwfrm_cart_checkoutCart")).click();
	    // if Guest user
	    String USERNAME = "neetu@gmail.com";
	    String PASSWORD = "nnnnnnnn";
	    driver.findElement(By.id("dwfrm_login_username")).clear();
	    driver.findElement(By.id("dwfrm_login_username")).sendKeys(USERNAME);
	    driver.findElement(By.id("dwfrm_login_password")).clear();
	    driver.findElement(By.id("dwfrm_login_password")).sendKeys(PASSWORD);
	    driver.findElement(By.name("dwfrm_login_login")).click();
	    // Checkout Page
	    String SHIPPING_TITLE = "Select or Enter Shipping Address";
	    assert(driver.findElement(By.cssSelector("legend")).getText().contains(SHIPPING_TITLE));
	  	driver.navigate().back();
	  	}
	  	// End of SUMMARY
	  	
	    // YOU MAY ALSO LIKE - Doubtful
	  	String YOU_LIKE = "You Might Also Like";
	  	WebElement youlikeEle = driver.findElement(By.cssSelector("h2.slider-title"));
	    assertEquals(YOU_LIKE, youlikeEle.getText());
	    String[] likeProductNames = {
		    		"10% of your order total will be donated to the Asian Art Museum",
		    		"Plump + Protect Set with Enriching Renewal Cream",
		    		"Ageless Radiance Duo",
		    		"Camellia Beauty Oil"
	    		};
	    for(int i=0,j=5;j<12;i++,j=j+2){
		    assertEquals("", driver.findElement(By.cssSelector("img[alt='"+likeProductNames[i]+"']")).getText());
		    driver.findElement(By.xpath("(//button[@type='submit'])["+j+"]")).click();
		    driver.navigate().back();
	    }

	  }
  private boolean elementPresent(String[] elementArray, String element){
	  boolean status = false;
	  for(String ele : elementArray){
		if(ele.equals(element)) {
			return true;
		}
	  }
	  return false;
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
