Feature: As a QE, I validate POST GoRest POST
  Scenario: Validating the POST request
    Given I send a POST request with body
    Then Status code is 201