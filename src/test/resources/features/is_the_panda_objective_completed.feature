Feature: Is the panda objective completed?
  I am a player and I would like to know whether a panda objective is completed.

  Scenario Outline: Testing the validation of several objectives
    Given I am a player
    And I have an objective of type <motif>
    And I have eaten <numberGREEN> GREEN bamboos
    And I have eaten <numberYELLOW> YELLOW bamboos
    And I have eaten <numberPINK> PINK bamboos
    When I check if i have validated the objective
    Then I should be answered <answer>.

    Examples: 
      | motif                 | numberGREEN | numberYELLOW | numberPINK | answer |
      | ORIGINAL_YELLOW       |           0 |            0 |          0 | false  |
      | ORIGINAL_YELLOW       |           2 |            0 |          0 | false  |
      | ORIGINAL_YELLOW       |           3 |            0 |          0 | false  |
      | ORIGINAL_YELLOW       |           1 |            0 |          0 | false  |
      | ORIGINAL_YELLOW       |           0 |            2 |          0 | true   |
      | ORIGINAL_YELLOW       |           0 |            3 |          0 | true   |
      | ORIGINAL_YELLOW       |           0 |            1 |          0 | false  |
      | ORIGINAL_YELLOW       |           0 |            0 |          2 | false  |
      | ORIGINAL_YELLOW       |           0 |            0 |          3 | false  |
      | ORIGINAL_YELLOW       |           0 |            0 |          1 | false  |
      | ORIGINAL_GREEN        |           0 |            0 |          0 | false  |
      | ORIGINAL_GREEN        |           2 |            0 |          0 | true   |
      | ORIGINAL_GREEN        |           3 |            0 |          0 | true   |
      | ORIGINAL_GREEN        |           1 |            0 |          0 | false  |
      | ORIGINAL_GREEN        |           0 |            2 |          0 | false  |
      | ORIGINAL_GREEN        |           0 |            3 |          0 | false  |
      | ORIGINAL_GREEN        |           0 |            1 |          0 | false  |
      | ORIGINAL_GREEN        |           0 |            0 |          2 | false  |
      | ORIGINAL_GREEN        |           0 |            0 |          3 | false  |
      | ORIGINAL_GREEN        |           0 |            0 |          1 | false  |
      | ORIGINAL_PINK         |           0 |            0 |          0 | false  |
      | ORIGINAL_PINK         |           2 |            0 |          0 | false  |
      | ORIGINAL_PINK         |           3 |            0 |          0 | false  |
      | ORIGINAL_PINK         |           1 |            0 |          0 | false  |
      | ORIGINAL_PINK         |           0 |            2 |          0 | false  |
      | ORIGINAL_PINK         |           0 |            3 |          0 | false  |
      | ORIGINAL_PINK         |           0 |            1 |          0 | false  |
      | ORIGINAL_PINK         |           0 |            0 |          2 | true   |
      | ORIGINAL_PINK         |           0 |            0 |          3 | true   |
      | ORIGINAL_PINK         |           0 |            0 |          1 | false  |
      | ORIGINAL_THREE_COLORS |           0 |            0 |          0 | false  |
      | ORIGINAL_THREE_COLORS |           2 |            0 |          0 | false  |
      | ORIGINAL_THREE_COLORS |           3 |            0 |          0 | false  |
      | ORIGINAL_THREE_COLORS |           1 |            0 |          0 | false  |
      | ORIGINAL_THREE_COLORS |           0 |            2 |          0 | false  |
      | ORIGINAL_THREE_COLORS |           0 |            3 |          0 | false  |
      | ORIGINAL_THREE_COLORS |           0 |            1 |          0 | false  |
      | ORIGINAL_THREE_COLORS |           0 |            0 |          2 | false  |
      | ORIGINAL_THREE_COLORS |           0 |            0 |          3 | false  |
      | ORIGINAL_THREE_COLORS |           0 |            0 |          1 | false  |
      | ORIGINAL_THREE_COLORS |           1 |            1 |          1 | true   |
      | ORIGINAL_THREE_COLORS |           1 |            2 |          1 | true   |
