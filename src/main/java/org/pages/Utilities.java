package org.pages;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;

public class Utilities {
    private static WebDriver driver;
    private static String testDataPath = "src/Resources/";
    private static String environmentPath = "src/Resources/environment.properties";
    public Utilities(WebDriver driver) {
        Utilities.driver = driver;
    }
    public static String getPropertyValue(String key) throws IOException {
        Properties properties = new Properties();
        properties.load(new FileInputStream(environmentPath));
        return properties.getProperty(key);
    }
    public static void scrollToElement(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", element);
    }
    public static void scrollToPosition(int x, int y) {
        ((JavascriptExecutor) driver).executeScript("window.scrollBy(arguments[0],arguments[1]);", x, y);
    }
    public static String getJsonData(String jsonFilename,String field) {
        try {
            JsonParser parser = new JsonParser();
            FileReader reader = new FileReader(testDataPath +jsonFilename+ ".json");
            Object object = parser.parse(reader);
            JsonObject jsonObject = (JsonObject) object;
            return jsonObject.get(field).getAsString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
    public static String getTimestamp()
    {
        return new SimpleDateFormat("yyyy-MM-dd_h-m-ss-a").format(new Date());
    }
    public static void clicking(WebDriver driver, By locator)
    {
        explicitlyWaitForClickability(driver,locator);
        driver.findElement(locator).click();
    }
    public static void sendData(WebDriver driver, By locator, String data)
    {
        explicitlyWaitForVisibility(driver,locator);
        driver.findElement(locator).sendKeys(data);
    }
    public static void explicitlyWaitForClickability(WebDriver driver , By locator)
    {
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.elementToBeClickable(locator));
    }
    public static void explicitlyWaitForVisibility(WebDriver driver , By locator)
    {
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
    public static String getTextElement(WebDriver driver, By locator)
    {
        explicitlyWaitForVisibility(driver,locator);
        return driver.findElement(locator).getText();
    }
    public static void selectFromDropDown(WebDriver driver, By locator, String elementText)
    {
        explicitlyWaitForVisibility(driver,locator);
        WebElement dropdown = driver.findElement(locator);
        Select select = new Select(dropdown);
        select.selectByVisibleText(elementText);
    }
    public static String getURL(WebDriver driver)
    {
        return driver.getCurrentUrl();
    }
    public static List<String> getTextOfElements(WebDriver driver, List<By> locators) {
        List<String> textValues = new ArrayList<>();
        for (By locator : locators) {
            WebElement element = driver.findElement(locator);
            String text = element.getText();
            textValues.add(text);
        }
        return textValues;
    }
    public static double convertStringToDouble(WebDriver driver, String text) {
        double numericValue = Double.parseDouble(text.replaceAll("[^0-9.]", ""));
        return numericValue;
    }
}
