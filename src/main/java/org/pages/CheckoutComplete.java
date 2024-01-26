package org.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CheckoutComplete {
    private WebDriver driver;
    public CheckoutComplete(WebDriver driver){this.driver = driver;}
    private static final By thankYouMsg = By.xpath("//*[@id=\"checkout_complete_container\"]/h2");
    private static final By orderDispatched = By.xpath("//*[@id=\"checkout_complete_container\"]/div");
    private static String thankYouMessage;
    private static String orderDispatchedMessage;
    public String getThankYouMessage(){
        thankYouMessage = Utilities.getTextElement(driver, thankYouMsg);
        return thankYouMessage;
    }
    public String getOrderDispatchedMessage(){
        orderDispatchedMessage = Utilities.getTextElement(driver, orderDispatched);
        return orderDispatchedMessage;
    }


}
