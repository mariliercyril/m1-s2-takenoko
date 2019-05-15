Feature: Is a client inscribed?

  Scenario Outline: Testing the subscription/inscription of a client
    When the client <id> makes a request of subscription
    Then the client returns it status code of 200

    Examples: 
      | id |
      | 7  |
      | 11 |
