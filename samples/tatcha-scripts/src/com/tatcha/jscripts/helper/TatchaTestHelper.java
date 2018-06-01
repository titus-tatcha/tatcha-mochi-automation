package com.tatcha.jscripts.helper;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.tatcha.jscripts.dao.Product;
import com.tatcha.jscripts.dao.User;
import com.tatcha.utils.BrowserDriver;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * Class has helper methods
 * 
 * @author Reshma
 *
 */
public class TatchaTestHelper {

    private final static Logger logger = Logger.getLogger(TatchaTestHelper.class);

    public void basicAuth(String webURL) {
        try {
            // String webPage = BrowserDriver.DEV_URL;

            String name = BrowserDriver.USERNAME;
            String password = BrowserDriver.PASSWORD;

            String authString = name + ":" + password;
            byte[] authEncBytes = Base64.encodeBase64(authString.getBytes());
            String authStringEnc = new String(authEncBytes);

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

    /**
     * Method to determine is a user is logged in or not
     * 
     * @param driver
     * @param prop
     * @param locator
     * @return
     * @throws Exception
     */
    public boolean isLoggedIn(WebDriver driver) throws Exception {

        try {
            driver.findElement(By.id("dropdownMenu1")).click();
            driver.findElement(By.linkText("Logout"));
            return true;
        } catch (NoSuchElementException noSuchElementExcep) {
            return false;
        }
    }

    /**
     * Get the first name of user
     * 
     * @param driver
     * @return
     * @throws Exception
     */
    public String getName(WebDriver driver) throws Exception {

        try {
            WebElement webElement = driver.findElement(By.className("text-center"));
            String text = webElement.getText();
            return text.substring(9, text.length());
        } catch (NoSuchElementException noSuchElementExcep) {
            return null;
        }
    }

    /**
     * Convert price in double to String containing currency symbol a two point
     * precision
     * 
     * @param price
     * @return
     */
    public String getPriceString(double price) {
        String priceString = Double.toString(price);
        if (priceString.indexOf(".") == (priceString.length() - 3)) {
            priceString = "$" + priceString;
        } else {
            priceString = "$" + priceString + "0";
        }
        return priceString;
    }

    /**
     * Return the price(excluding '$') as double type
     * 
     * @param priceString
     * @return
     */
    public double getPrice(String priceString) {
        double price = 0.0;
        if (StringUtils.isNotBlank(priceString) && !"Free".equalsIgnoreCase(priceString)) {
            price = Double.parseDouble(priceString.substring(priceString.indexOf("$") + 1, priceString.length()));
        }
        return price;
    }

    /**
     * Get no of items in cart
     * 
     * @param user
     * @return
     */
    public int getItemCountInCart(User user) {
        int productInCart = 0;
        int samplesInCart = 0;
        int giftWrap = 0;
        if (null != user) {
            if (null != user.getSamples()) {
                samplesInCart = user.getSamples().size();
            }
            if (user.getIsGiftWrap()) {
                giftWrap = 1;
            }
        }
        for (Product product : user.getProducts()) {
            productInCart += product.getQuantity();
        }
        logger.info("No of Items in Cart : Product : " + productInCart + ", Samples : " + samplesInCart
                + ", GiftWrap : " + giftWrap);
        return productInCart + samplesInCart + giftWrap;
    }

    /**
     * Get the cart subtotal
     * 
     * @param user
     * @return
     */
    public double getSubtotal(User user) {
        double subtotal = 0;
        String priceString = "";
        for (Product product : user.getProducts()) {
            priceString = product.getPrice();
            subtotal += getPrice(priceString) * product.getQuantity();
        }
        if (user.getIsGiftWrap()) {
            subtotal = User.getGiftWrapPrice();
        }
        logger.info("Subtotal : " + subtotal);
        return subtotal;
    }

    /**
     * Log the assertion
     * 
     * @param className
     * @param expected
     * @param actual
     */
    public void logAssertion(String className, String expected, String actual) {
        try {
            assertEquals(expected, actual);
            logger.info(className + " :: ASSERTION IS TRUE");
        } catch (AssertionError ae) {
            logger.error(className + " :: " + ae.toString());
        }
    }

    /**
     * Log assertion
     * 
     * @param className
     * @param expected
     */
    public void logAssertion(String className, boolean expected) {
        try {
            assert (expected);
            logger.info(className + " :: ASSERTION HOLDS TRUE");
        } catch (AssertionError ae) {
            logger.error(className + " :: " + ae.toString());
        }
    }
}
