Feature: ObjectivePanda
  I am a player and I would like to check if a panda objective is completed.

  Scenario Outline: Checking if the objective "having eaten at least two GREEN bamboo chunks (ORIGINAL_GREEN)" is completed
    Given I am a player
    # <gbcn> is the "green bamboo chunks number"
    And I have eaten <gbcn> green bamboo chunks
    # <ybcn> is the "yellow bamboo chunks number"
    And I have eaten <ybcn> yellow bamboo chunks
    # <pbcn> is the "pink bamboo chunks number"
    And I have eaten <pbcn> pink bamboo chunks
    When I check if the "ORIGINAL_GREEN" is completed
    Then the response should be "<response>"

    Examples:
      | gbcn | ybcn | pbcn | response |
      |    0 |    0 |    0 |       no |
      |    2 |    0 |    0 |      yes |
      |    3 |    0 |    0 |      yes |
      |    1 |    0 |    0 |       no |
      |    0 |    2 |    0 |       no |
      |    0 |    3 |    0 |       no |
      |    0 |    1 |    0 |       no |
      |    0 |    0 |    2 |       no |
      |    0 |    0 |    3 |       no |
      |    0 |    0 |    1 |       no |
      |    0 |    1 |    1 |       no |
      |    1 |    0 |    1 |       no |
      |    1 |    1 |    0 |       no |
      |    1 |    1 |    1 |       no |
      |    1 |    1 |    2 |       no |
      |    1 |    2 |    1 |       no |
      |    2 |    1 |    1 |      yes |

  Scenario Outline: Checking if the objective "having eaten at least two YELLOW bamboo chunks (ORIGINAL_YELLOW)" is completed
    Given I am a player
    # <gbcn> is the "green bamboo chunks number"
    And I have eaten <gbcn> green bamboo chunks
    # <ybcn> is the "yellow bamboo chunks number"
    And I have eaten <ybcn> yellow bamboo chunks
    # <pbcn> is the "pink bamboo chunks number"
    And I have eaten <pbcn> pink bamboo chunks
    When I check if the "ORIGINAL_YELLOW" is completed
    Then the response should be "<response>"

    Examples:
      | gbcn | ybcn | pbcn | response |
      |    0 |    0 |    0 |       no |
      |    2 |    0 |    0 |       no |
      |    3 |    0 |    0 |       no |
      |    1 |    0 |    0 |       no |
      |    0 |    2 |    0 |      yes |
      |    0 |    3 |    0 |      yes |
      |    0 |    1 |    0 |       no |
      |    0 |    0 |    2 |       no |
      |    0 |    0 |    3 |       no |
      |    0 |    0 |    1 |       no |
      |    0 |    1 |    1 |       no |
      |    1 |    0 |    1 |       no |
      |    1 |    1 |    0 |       no |
      |    1 |    1 |    1 |       no |
      |    1 |    1 |    2 |       no |
      |    1 |    2 |    1 |      yes |
      |    2 |    1 |    1 |       no |

  Scenario Outline: Checking if the objective "having eaten at least two PINK bamboo chunks (ORIGINAL_PINK)" is completed
    Given I am a player
    # <gbcn> is the "green bamboo chunks number"
    And I have eaten <gbcn> green bamboo chunks
    # <ybcn> is the "yellow bamboo chunks number"
    And I have eaten <ybcn> yellow bamboo chunks
    # <pbcn> is the "pink bamboo chunks number"
    And I have eaten <pbcn> pink bamboo chunks
    When I check if the "ORIGINAL_PINK" is completed
    Then the response should be "<response>"

    Examples:
      | gbcn | ybcn | pbcn | response |
      |    0 |    0 |    0 |       no |
      |    2 |    0 |    0 |       no |
      |    3 |    0 |    0 |       no |
      |    1 |    0 |    0 |       no |
      |    0 |    2 |    0 |       no |
      |    0 |    3 |    0 |       no |
      |    0 |    1 |    0 |       no |
      |    0 |    0 |    2 |      yes |
      |    0 |    0 |    3 |      yes |
      |    0 |    0 |    1 |       no |
      |    0 |    1 |    1 |       no |
      |    1 |    0 |    1 |       no |
      |    1 |    1 |    0 |       no |
      |    1 |    1 |    1 |       no |
      |    1 |    1 |    2 |      yes |
      |    1 |    2 |    1 |       no |
      |    2 |    1 |    1 |       no |

  Scenario Outline: Checking if the objective "having eaten one bamboo chunk of each of the three colors (ORIGINAL_THREE_COLORS)" is completed
    Given I am a player
    # <gbcn> is the "green bamboo chunks number"
    And I have eaten <gbcn> green bamboo chunks
    # <ybcn> is the "yellow bamboo chunks number"
    And I have eaten <ybcn> yellow bamboo chunks
    # <pbcn> is the "pink bamboo chunks number"
    And I have eaten <pbcn> pink bamboo chunks
    When I check if the "ORIGINAL_THREE_COLORS" is completed
    Then the response should be "<response>"

    Examples:
      | gbcn | ybcn | pbcn | response |
      |    0 |    0 |    0 |       no |
      |    2 |    0 |    0 |       no |
      |    3 |    0 |    0 |       no |
      |    1 |    0 |    0 |       no |
      |    0 |    2 |    0 |       no |
      |    0 |    3 |    0 |       no |
      |    0 |    1 |    0 |       no |
      |    0 |    0 |    2 |       no |
      |    0 |    0 |    3 |       no |
      |    0 |    0 |    1 |       no |
      |    0 |    1 |    1 |       no |
      |    1 |    0 |    1 |       no |
      |    1 |    1 |    0 |       no |
      |    1 |    1 |    1 |      yes |
      |    1 |    1 |    2 |      yes |
      |    1 |    2 |    1 |      yes |
      |    2 |    1 |    1 |      yes |
