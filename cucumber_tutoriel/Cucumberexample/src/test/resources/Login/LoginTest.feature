Feature: User Login
  User should be able to login using valid credentials

  Scenario Outline: Testing login with valid credentials
    Given I open login page
    When I enter username as "<username>" and password as "<password>"
    And I submit login page
    Then I redirect to user home page

    Examples: 
      | username | password  |
      | oualid   | benazzouz |
      | admin    | admin     |

  Scenario: Testing login with invalid credentials
    Given I open login page
    When I enter username as "invalid" and password as "invalid"
    And I submit login page
    Then I am on login page
