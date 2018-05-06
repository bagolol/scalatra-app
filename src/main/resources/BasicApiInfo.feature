Feature: Getting Api Info

  Scenario: I navigate to the home page
    Given I navigate to the home page
    Then I should receive a success response
    And I am told the name of the application

  Scenario: I navigate to the status page
    Given I navigate to the status
    Then I should receive a success response
    And I am shown the application status
