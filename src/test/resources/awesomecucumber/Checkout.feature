Feature: Checkout

  Scenario: Checkout success
    Given login_success
    When addCart_and_Checkout
    And click Checkout
    And Input Checkout info and Continue
    And Click Finish
    Then Checkout is completed