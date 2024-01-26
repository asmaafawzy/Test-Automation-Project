package stepDefinitions;

import groovy.util.logging.Log;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.pages.LoginPage;
import org.pages.RestAssuredAPIs;
import org.testng.asserts.SoftAssert;
import tests.TestBase;

import java.io.IOException;

public class LoginWithInvalidData extends TestBase{
    private static SoftAssert softAssert;
    private static String expectedErrorMsg;

    @Given("the user is on the login page")
    public void theUserIsOnTheLoginPage() throws IOException {
        new TestBase().openPortal();
    }
    @When("the user enters an invalid username {string} and an invalid password {string}")
    public void the_user_enters_an_invalid_username_and_an_invalid_password(String username, String password) {
        new LoginPage(driver).enterUserName(username).enterPassword(password);
    }
    @When("the user clicks on the login button")
    public void the_user_clicks_on_the_login_button() {
        new LoginPage(driver).clickLoginbtn();
    }

    @Then("the user should see an error message indicating invalid credentials {string}")
    public void theUserShouldSeeAnErrorMessageIndicatingInvalidCredentials(String errorMessge) {
        softAssert = new SoftAssert();
        expectedErrorMsg = new LoginPage(driver).getErrorMsg();
        softAssert.assertEquals(errorMessge, expectedErrorMsg);
        softAssert.assertAll();
        new TestBase().closeBrowser();
    }
}
