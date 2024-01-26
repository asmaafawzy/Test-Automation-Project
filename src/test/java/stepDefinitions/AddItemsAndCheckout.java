package stepDefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.pages.*;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;
import tests.TestBase;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class AddItemsAndCheckout extends TestBase {

    private static SoftAssert softAssert = new SoftAssert();
    private static String actualPageName;
    private static String actualCartURL;
    private static String expectedCartURL = "https://www.saucedemo.com/cart.html";
    private static String expectedCheckoutStep1 = "https://www.saucedemo.com/checkout-step-one.html";
    private static String expectedCheckoutStep2 = "https://www.saucedemo.com/checkout-step-two.html";
    private static String actualCheckoutStep1;
    private static String actualCheckoutStep2;
    private static String overviewPageTitle = "Checkout: Overview";
    private Double totalPriceBeforeTaxes;

    private static List<String> productNames = Arrays.asList("Sauce Labs Fleece Jacket", "Sauce Labs Backpack");
    private static List<Double> productPrices = Arrays.asList(49.99, 29.99);
    private static List<String> actualproductNames;
    private static List<Double> actualproductPrices;
    private static String productName1 = "Sauce Labs Fleece Jacket";
    private static String productName2 = "Sauce Labs Backpack";

    @Given("the user navigates to the login page")
    public void theUserNavigatesToTheLoginPage() throws IOException {
        new TestBase().openPortal();
    }
    @When("the user login with a valid username {string} and password {string}")
    public void theUserLoginwithAValidUsernameAndPassword(String username, String password) {
        new LoginPage(driver).enterUserName(username).enterPassword(password).clickLoginbtn();
    }
    @Then("the user should be logged in successfully and navigated to the products page")
    public void theUserShouldBeLoggedInSuccessfullyAndNavigatedToTheProductsPage() {
        actualPageName = new ProductsPage(driver).verifyProductsPageTitle();
        Assert.assertEquals("Products", actualPageName);
    }
    @When("the user adds the most expensive two products to the cart")
    public void theUserAddsTheMostExpensiveTwoProductsToTheCart() {
        new ProductsPage(driver).selectPriceFilter().addProductsToCart();
    }
    @And("clicks on the cart button")
    public void clicksOnTheCartButton() {
        new ProductsPage(driver).clickOnCart();
    }
    @Then("the user should be navigated to the Cart page with the previously selected products")
    public void theUserShouldBeNavigatedToTheCartPageWithThePreviouslySelectedProducts() {
        actualCartURL = new CartPage(driver).getCartURLPage();
        softAssert.assertEquals(expectedCartURL, actualCartURL);
        actualproductNames = new ProductsPage(driver).getTextValues();
        softAssert.assertEqualsNoOrder(productNames.toArray(), actualproductNames.toArray());
        actualproductPrices = new ProductsPage(driver).getPriceValues();
        softAssert.assertEqualsNoOrder(productPrices.toArray(), actualproductPrices.toArray());
    }
    @When("the user clicks on the Checkout button")
    public void theUserClicksOnTheCheckoutButton() {
        new CartPage(driver).clickCheckout();
    }
    @Then("the user should be navigated to the Checkout page")
    public void theUserShouldBeNavigatedToTheCheckoutPage() {
        actualCheckoutStep1 = new CheckoutStepOne(driver).getCheckoutStepOneURL();
        softAssert.assertEquals(expectedCheckoutStep1, actualCheckoutStep1);
    }
    @When("the user fills all the displayed form first name {string}, lastname {string} and postalcode {string}")
    public void the_user_fills_all_the_displayed_form_first_name_lastname_and_postalcode(String firstname, String lastname, String postalcode) {
        new CheckoutStepOne(driver).enterFirstName(firstname).enterLastName(lastname).enterPostalCode(postalcode);
    }
    @And("clicks on the Continue button")
    public void clicksOnTheContinueButton() {
        new CheckoutStepOne(driver).clickContinueBtn();
    }
    @Then("the user should be navigated to the Overview page")
    public void theUserShouldBeNavigatedToTheOverviewPage() {
        actualCheckoutStep2 = new CheckoutStepTwo(driver).getCheckoutStepTwoURL();
        new ProductsPage(driver).resetTotalPrice();
        softAssert.assertEquals(overviewPageTitle, new CheckoutStepTwo(driver).verifyPageTitle());
        softAssert.assertEquals(expectedCheckoutStep2, actualCheckoutStep2);
        softAssert.assertEquals(new ProductsPage(driver).getTotalPrice(), new CheckoutStepTwo(driver).getTotalPrice());
    }
    @When("the user clicks on the Finish button")
    public void theUserClicksOnTheFinishButton() {
        new CheckoutStepTwo(driver).clickFinish();
    }

    @Then("the user should see the Thank You {string} and the Order has been dispatched messages {string}")
    public void the_user_should_see_the_thank_you_and_the_order_has_been_dispatched_messages(String messageone, String messagetwo) {
        softAssert.assertEquals(messageone, new CheckoutComplete(driver).getThankYouMessage());
        softAssert.assertTrue(messagetwo.contains(new CheckoutComplete(driver).getOrderDispatchedMessage()));
        softAssert.assertAll();
        new TestBase().closeBrowser();
    }
}
