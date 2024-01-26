package org.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import java.util.ArrayList;
import java.util.List;

public class ProductsPage {
    private WebDriver driver;
    public ProductsPage(WebDriver driver) {
        this.driver = driver;
    }
    private static final By pageName = By.xpath("//div[contains(@class, 'header_secondary_container')]/span");
    private static final By selectHighPrice = By.className("product_sort_container");
    private static final By firstProduct = By.id("add-to-cart-sauce-labs-fleece-jacket");
    private static final By firstProductPrice = By.xpath("(//div[@class='inventory_item_price'])[1]");
    private static final By firstprodName = By.xpath("(//div[@class='inventory_item_name'])[1]");
    private static final By secondProduct = By.id("add-to-cart-sauce-labs-backpack");
    private static final By secondProductPrice = By.xpath("(//div[@class='inventory_item_price'])[2]");
    private static final By secondprodName = By.xpath("(//div[@class='inventory_item_name'])[2]");

    private static final By cartLink = By.xpath("//*[@id=\"shopping_cart_container\"]/a");
    private static final String filterPrice = "Price (high to low)";
    private static String pagename;
    private static Double TotalPrice = 0.0;
    private static List<Double> priceValues;

    public ProductsPage closePopUp() {
        Actions action = new Actions(driver);
        action.sendKeys(Keys.ESCAPE).perform();
        return this;
    }
    public String verifyProductsPageTitle() {
        pagename = Utilities.getTextElement(driver, pageName);
        return pagename;
    }

    public ProductsPage selectPriceFilter() {
        Utilities.selectFromDropDown(driver, selectHighPrice, filterPrice);
        return this;
    }

    public ProductsPage addProductsToCart() {
        Utilities.clicking(driver, firstProduct);
        Utilities.clicking(driver, secondProduct);
        return this;
    }
    public ProductsPage clickOnCart() {
        Utilities.clicking(driver, cartLink);
        return this;
    }
    public List<String> getTextValues() {
        List<By> locatorsNames = new ArrayList<>();
        locatorsNames.add(firstprodName);
        locatorsNames.add(secondprodName);
        return Utilities.getTextOfElements(driver, locatorsNames);
    }
    public List<Double> getPriceValues() {
        List<By> locatorsPrices = new ArrayList<>();
        locatorsPrices.add(firstProductPrice);
        locatorsPrices.add(secondProductPrice);
        List<String> priceTextValues = Utilities.getTextOfElements(driver, locatorsPrices);
        priceValues = new ArrayList<>();
        // Convert text to Double
        for (String text : priceTextValues) {
            TotalPrice += Utilities.convertStringToDouble(driver,text);
            priceValues.add(Utilities.convertStringToDouble(driver,text));
        }
        return priceValues;
    }
    public Double getTotalPrice(){
        for (Double price : priceValues) {
            TotalPrice += price;
        }
        return TotalPrice;
    }
    public ProductsPage resetTotalPrice(){
        TotalPrice = 0.0;
        return this;
    }
}

