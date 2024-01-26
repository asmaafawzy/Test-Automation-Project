package org.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheckoutStepTwo {
    private static WebDriver driver;
    public CheckoutStepTwo(WebDriver driver){this.driver = driver;}
    private static String checkoutStep2;
    private static String pagetitle;
    private static String totalPriceBeforeTaxesText;
    private static final By finishBtn = By.id("finish");
    private static final By pageTitle = By.xpath("//*[@id=\"header_container\"]/div[2]/span");
    private static final By totalPrice = By.xpath("//div[@class='summary_subtotal_label']");
    public String getCheckoutStepTwoURL(){
        checkoutStep2 = Utilities.getURL(driver);
        return checkoutStep2;
    }
    public String verifyPageTitle() {
        pagetitle = Utilities.getTextElement(driver, pageTitle);
        return pagetitle;
    }
    public Double getTotalPrice(){
        totalPriceBeforeTaxesText = Utilities.getTextElement(driver, totalPrice);
        Pattern pattern = Pattern.compile("\\$([0-9]+\\.[0-9]+)");
        Matcher matcher = pattern.matcher(totalPriceBeforeTaxesText);
        if (matcher.find()) {
            return Double.parseDouble(matcher.group(1));}
        else {
            throw new IllegalArgumentException("No numeric value found in the input text");
        }
    }
    public CheckoutStepTwo clickFinish(){
        Utilities.clicking(driver, finishBtn);
        return this;
    }
}
