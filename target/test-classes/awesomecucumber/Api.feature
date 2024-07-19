Feature: API

  Scenario: Successfully Get Request
    Given I'm on the Login Page
    When set the UserName and Password
    And send a Get request to the Endpoint
    Then status code is 200
