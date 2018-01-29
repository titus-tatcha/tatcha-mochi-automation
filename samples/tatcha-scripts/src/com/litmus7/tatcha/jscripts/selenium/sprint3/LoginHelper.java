package com.litmus7.tatcha.jscripts.selenium.sprint3;

import java.util.HashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.apache.commons.lang.StringUtils;

public class LoginHelper {

    // Holds the user details, such as first name
    public HashMap<String, String> userDetails = new HashMap<String, String>();

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
        if(StringUtils.isNotBlank(priceString)) {
            price = Double.parseDouble(priceString.substring(1, priceString.length()));
        }
        return price;
    }
    
    /**
     * @return the userDetails
     */
    public HashMap<String, String> getUserDetails() {
        return userDetails;
    }

    /**
     * @param userDetails
     *            the userDetails to set
     */
    public void setUserDetails(HashMap<String, String> userDetails) {
        this.userDetails = userDetails;
    }
}
