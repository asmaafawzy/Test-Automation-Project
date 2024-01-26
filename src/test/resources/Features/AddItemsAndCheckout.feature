@Test
Feature: Checkout and Order Completion

  Scenario Outline: User Adds items to cart and checkout to verifies order details
    Given the user navigates to the login page
    When the user login with a valid username "<username>" and password "<password>"
    Then the user should be logged in successfully and navigated to the products page

    When the user adds the most expensive two products to the cart
    And clicks on the cart button
    Then the user should be navigated to the Cart page with the previously selected products

    When the user clicks on the Checkout button
    Then the user should be navigated to the Checkout page

    When the user fills all the displayed form first name "<firstname>", lastname "<lastname>" and postalcode "<postalcode>"
    And clicks on the Continue button
    Then the user should be navigated to the Overview page

    When the user clicks on the Finish button
    Then the user should see the Thank You "<messageone>" and the Order has been dispatched messages "<messagetwo>"

    Examples:
      | username       | password      | firstname | lastname | postalcode | messageone | messagetwo|
      | standard_user  | secret_sauce  | Test      | Last     | 12345      |Thank you for your order!| Your order has been dispatched, and will arrive just as fast as the pony can get there!|


