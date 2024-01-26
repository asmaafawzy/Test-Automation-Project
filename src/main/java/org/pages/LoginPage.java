package org.pages;

import groovy.util.logging.Log;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {
    private WebDriver driver;
    public LoginPage(WebDriver driver){this.driver = driver;}
    private static final By userName = By.id("user-name");
    private static final By password = By.id("password");
    private static final By loginbtn = By.id("login-button");
    private static final By errormsg = By.xpath("//div[contains(@class, 'error-message-container')]/h3");
    private static String errorMessage;
    public LoginPage enterUserName(String username){
        Utilities.sendData(driver, userName, username);
        return this;
    }
    public LoginPage enterPassword(String pass){
        Utilities.sendData(driver, password, pass);
        return this;
    }
    public LoginPage clickLoginbtn(){
        Utilities.clicking(driver, loginbtn);
        return this;
    }
    public String getErrorMsg(){
        errorMessage = Utilities.getTextElement(driver, errormsg);
        return errorMessage;
    }

}
