Feature: Login

#  Scenario: Successfully Login into the WebApp
#    Given I'm on the Login Page
#    When set the UserName and Password
#    And click LoginButton
#    Then the user is on "Products" Page
#
#  Scenario: Failed Login into the WebApp
#    Given I'm on the Login Page
#    When set a wrong UserName and Password
#    And click LoginButton
#    Then the system throws the error "Epic sadface: Username and password do not match any user in this service" message

  Scenario: Successfully Login into the WebApp by account in DB
    Given I'm on the Login Page
    When set the UserName and Password which get from DB
    And click LoginButton
    Then the user is on "Products" Page