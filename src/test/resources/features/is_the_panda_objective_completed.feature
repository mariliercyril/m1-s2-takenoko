Feature: Is the panda objective completed?
  I am a player and I would like to check if a panda objective is completed.

  Scenario Outline: Checking if the objective "having eaten two GREEN bamboo chunks" is completed
    Given I am a player
    # <g> is the "green bamboo chunks number"
    And I have eaten <g> green bamboo chunks
    # <y> is the "yellow bamboo chunks number"
    And I have eaten <y> yellow bamboo chunks
    # <p> is the "pink bamboo chunks number"
    And I have eaten <p> pink bamboo chunks
    When I ask whether the "ORIGINAL_GREEN" objective is completed
    Then the response should be <response>.

    Examples:
      | g | y | p | response |
      | 0 | 0 | 0 |    false |
      | 2 | 0 | 0 |     true |
      | 3 | 0 | 0 |     true |
      | 1 | 0 | 0 |    false |
      | 0 | 2 | 0 |    false |
      | 0 | 3 | 0 |    false |
      | 0 | 1 | 0 |    false |
      | 0 | 0 | 2 |    false |
      | 0 | 0 | 3 |    false |
      | 0 | 0 | 1 |    false |
      | 0 | 1 | 1 |    false |
      | 1 | 0 | 1 |    false |
      | 1 | 1 | 0 |    false |
      | 1 | 1 | 1 |    false |
      | 1 | 1 | 2 |    false |
      | 1 | 2 | 1 |    false |
      | 2 | 1 | 1 |     true |
      | 2 | 2 | 1 |     true |
      | 2 | 1 | 2 |     true |
      | 1 | 2 | 2 |    false |
      | 2 | 2 | 2 |     true |

  Scenario Outline: Checking if the objective "having eaten two YELLOW bamboo chunks" is completed
    Given I am a player
    # <g> is the "green bamboo chunks number"
    And I have eaten <g> green bamboo chunks
    # <y> is the "yellow bamboo chunks number"
    And I have eaten <y> yellow bamboo chunks
    # <p> is the "pink bamboo chunks number"
    And I have eaten <p> pink bamboo chunks
    When I ask whether the "ORIGINAL_YELLOW" objective is completed
    Then the response should be <response>.

    Examples:
      | g | y | p | response |
      | 0 | 0 | 0 |    false |
      | 2 | 0 | 0 |    false |
      | 3 | 0 | 0 |    false |
      | 1 | 0 | 0 |    false |
      | 0 | 2 | 0 |     true |
      | 0 | 3 | 0 |     true |
      | 0 | 1 | 0 |    false |
      | 0 | 0 | 2 |    false |
      | 0 | 0 | 3 |    false |
      | 0 | 0 | 1 |    false |
      | 0 | 1 | 1 |    false |
      | 1 | 0 | 1 |    false |
      | 1 | 1 | 0 |    false |
      | 1 | 1 | 1 |    false |
      | 1 | 1 | 2 |    false |
      | 1 | 2 | 1 |     true |
      | 2 | 1 | 1 |    false |
      | 2 | 2 | 1 |     true |
      | 2 | 1 | 2 |    false |
      | 1 | 2 | 2 |     true |
      | 2 | 2 | 2 |     true |

  Scenario Outline: Checking if the objective "having eaten two PINK bamboo chunks" is completed
    Given I am a player
    # <g> is the "green bamboo chunks number"
    And I have eaten <g> green bamboo chunks
    # <y> is the "yellow bamboo chunks number"
    And I have eaten <y> yellow bamboo chunks
    # <p> is the "pink bamboo chunks number"
    And I have eaten <p> pink bamboo chunks
    When I ask whether the "ORIGINAL_PINK" objective is completed
    Then the response should be <response>.

    Examples:
      | g | y | p | response |
      | 0 | 0 | 0 |    false |
      | 2 | 0 | 0 |    false |
      | 3 | 0 | 0 |    false |
      | 1 | 0 | 0 |    false |
      | 0 | 2 | 0 |    false |
      | 0 | 3 | 0 |    false |
      | 0 | 1 | 0 |    false |
      | 0 | 0 | 2 |     true |
      | 0 | 0 | 3 |     true |
      | 0 | 0 | 1 |    false |
      | 0 | 1 | 1 |    false |
      | 1 | 0 | 1 |    false |
      | 1 | 1 | 0 |    false |
      | 1 | 1 | 1 |    false |
      | 1 | 1 | 2 |     true |
      | 1 | 2 | 1 |    false |
      | 2 | 1 | 1 |    false |
      | 2 | 2 | 1 |    false |
      | 2 | 1 | 2 |     true |
      | 1 | 2 | 2 |     true |
      | 2 | 2 | 2 |     true |

  Scenario Outline: Checking if the objective "having eaten one bamboo chunk of each of the three colors" is completed
    Given I am a player
    # <g> is the "green bamboo chunks number"
    And I have eaten <g> green bamboo chunks
    # <y> is the "yellow bamboo chunks number"
    And I have eaten <y> yellow bamboo chunks
    # <p> is the "pink bamboo chunks number"
    And I have eaten <p> pink bamboo chunks
    When I ask whether the "ORIGINAL_THREE_COLORS" objective is completed
    Then the response should be <response>.

    Examples:
      | g | y | p | response |
      | 0 | 0 | 0 |    false |
      | 2 | 0 | 0 |    false |
      | 3 | 0 | 0 |    false |
      | 1 | 0 | 0 |    false |
      | 0 | 2 | 0 |    false |
      | 0 | 3 | 0 |    false |
      | 0 | 1 | 0 |    false |
      | 0 | 0 | 2 |    false |
      | 0 | 0 | 3 |    false |
      | 0 | 0 | 1 |    false |
      | 0 | 1 | 1 |    false |
      | 1 | 0 | 1 |    false |
      | 1 | 1 | 0 |    false |
      | 1 | 1 | 1 |     true |
      | 1 | 1 | 2 |     true |
      | 1 | 2 | 1 |     true |
      | 2 | 1 | 1 |     true |
      | 2 | 2 | 1 |     true |
      | 2 | 1 | 2 |     true |
      | 1 | 2 | 2 |     true |
      | 2 | 2 | 2 |     true |
