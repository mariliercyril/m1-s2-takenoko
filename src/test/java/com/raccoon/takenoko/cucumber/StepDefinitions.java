package com.raccoon.takenoko.cucumber;

import static org.junit.jupiter.api.Assertions.assertTrue;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import com.raccoon.takenoko.game.objective.PandaObjective;
import com.raccoon.takenoko.game.tiles.Color;
import com.raccoon.takenoko.player.Player;

import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class StepDefinitions {

	private static Player mockPlayer;
	private Map<Color, Integer> stomach;
	
	private PandaObjective pandaObjective = new PandaObjective(PandaObjective.Motif.ORIGINAL_GREEN);
	
	@Before
	public void initialize() {

		// Creates a stomach and initializes it
		stomach = new HashMap<>();
        stomach.put(Color.GREEN, 0);
        stomach.put(Color.YELLOW, 0);
        stomach.put(Color.PINK, 0);
	}
	
	@Given("^I am a player$")
	public void i_am_a_player() {
		
		mockPlayer = mock(Player.class);
		
	}

	@Given("^I have eaten (\\d+) green bamboos$")
	public void i_have_eaten_green_bamboos(Integer nbBamboos) {
	    
		stomach.put(Color.GREEN, nbBamboos) ;
	}

	@When("^I check if i have validated the objective$")
	public void i_check_if_i_have_validated_the_objective() {
		
		when(mockPlayer.getStomach()).thenReturn(stomach);
		pandaObjective.checkIfCompleted(mockPlayer);
		
	}

	@Then("^I should be answered yes$")
	public void i_should_be_answered_yes() {
	  
		assertTrue(pandaObjective.isCompleted());
	}
	
}
