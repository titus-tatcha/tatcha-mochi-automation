package com.tatcha.jscripts.dao;

import java.util.List;

public class User {

    private String fname;
    private String email;
    private String password;
    private List<Product> products;
    private List<Sample> samples;
    private boolean isGiftWrap;
    private static final int GIFT_WRAP_PRICE = 5;

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    /**
     * @return the samples
     */
    public List<Sample> getSamples() {
        return samples;
    }

    /**
     * @param samples
     *            the samples to set
     */
    public void setSamples(List<Sample> samples) {
        this.samples = samples;
    }

    /**
     * @return the isGiftWrap
     */
    public boolean getIsGiftWrap() {
        return isGiftWrap;
    }

    /**
     * @param isGiftWrap
     *            the isGiftWrap to set
     */
    public void setIsGiftWrap(boolean isGiftWrap) {
        this.isGiftWrap = isGiftWrap;
    }

    /**
     * @return the giftWrapPrice
     */
    public static int getGiftWrapPrice() {
        return GIFT_WRAP_PRICE;
    }
}
