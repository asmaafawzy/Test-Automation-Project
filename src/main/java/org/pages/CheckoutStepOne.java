package org.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CheckoutStepOne {
    private WebDriver driver;
    public CheckoutStepOne(WebDriver driver){this.driver = driver;}
    private static String checkoutStep1;
    private static final By firstName = By.id("first-name");
    private static final By lastName = By.id("last-name");
    private static final By postalCode = By.id("postal-code");
    private static final By continuebtn = By.id("continue");
    public String getCheckoutStepOneURL(){
        checkoutStep1 = Utilities.getURL(driver);
        return checkoutStep1;
    }
    public CheckoutStepOne enterFirstName(String firstname){
        Utilities.sendData(driver, firstName, firstname);
        return this;
    }
    public CheckoutStepOne enterLastName(String lastname){
        Utilities.sendData(driver, lastName, lastname);
        return this;
    }
    public CheckoutStepOne enterPostalCode(String postalcode){
        Utilities.sendData(driver, postalCode, postalcode);
        return this;
    }
    public CheckoutStepOne clickContinueBtn(){
        Utilities.clicking(driver, continuebtn);
        return this;
    }
}
