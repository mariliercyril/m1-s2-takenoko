Feature: Is a client connected to the server
  I am a client and I want to know whether I am connected to the server

  Scenario Outline: Testing the connection of a client
    When the client <id> makes a request of connection to the server
    Then the client receives a status code of 200

    Examples: 
      | id |
      |  3 |
      |  4 |
