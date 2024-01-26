@Test
Feature: Login with Invalid Data

  Scenario Outline: User attempts to login with invalid credentials
    Given the user is on the login page
    When the user enters an invalid username "<username>" and an invalid password "<password>"
    And the user clicks on the login button
    Then the user should see an error message indicating invalid credentials "<error Message>"

    Examples:
     | username       | password       | error Message     |
     | standard_user1 | secret_sauce1  | Epic sadface: Username and password do not match any user in this service |
     | standard_user2 | secret_sauce2  | Epic sadface: Username and password do not match any user in this service |
     | standard_user3 |                | Epic sadface: Password is required                                        |
     |                | secret_sauce1  | Epic sadface: Username is required                                        |
