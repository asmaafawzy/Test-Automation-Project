package org.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CartPage {
    private WebDriver driver;
    public CartPage(WebDriver driver) {
        this.driver = driver;
    }
    private String cartURL;
    private final By checkoutbtn = By.id("checkout");
    public String getCartURLPage(){
        cartURL = Utilities.getURL(driver);
        return cartURL;
    }
    public CartPage clickCheckout(){
        Utilities.clicking(driver, checkoutbtn);
        return this;
    }
}
