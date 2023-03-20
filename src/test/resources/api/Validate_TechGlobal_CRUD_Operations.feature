@api
Feature: As a QE, I validate the TechGlobal CRUD operations

  Scenario Outline: Validating the TechGlobal CRUD operations
    Given Create an user with "<firsName>", "<lastName>", email, "<dob>" and "<urlPath>"
    And Validate that status code is 200
    And I make a GET request with "<urlPath>" with id
    And Validate that status code is 200
    And I make a PUT request with fallowing data and with "<urlPath>"
      | firstName | Global |
      | lastName  | Tech   |

    Examples: TechGlobal Data
      | firsName | lastName | dob        | urlPath   |
      | Batch    | Five     | 2022-08-29 | /students |