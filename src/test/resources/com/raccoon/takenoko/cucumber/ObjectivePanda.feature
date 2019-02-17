Feature: ObjectivePanda
  I am a player and i want to know if i can validate a panda Objective as eating 2 green bamboos.

  Scenario: Testing the validation of an objective
    Given I am a player
    And I have eaten 2 green bamboos
    When I check if i have validated the objective
    Then I should be answered yes
