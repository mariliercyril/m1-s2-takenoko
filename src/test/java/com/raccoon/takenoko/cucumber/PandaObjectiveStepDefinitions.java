package com.raccoon.takenoko.cucumber;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import com.raccoon.takenoko.game.objective.PandaObjective;

import com.raccoon.takenoko.game.tiles.Color;

import com.raccoon.takenoko.player.Player;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

/**
 * The {@code PandaObjectiveStepDefinitions} class defines the Gherkin steps (in the scenarios
 * of the Cucumber <i>feature</i> "Is the panda objective completed?") as Java methods.
 * 
 * @author obenazzouz
 * @author cmarilier
 */
public class PandaObjectiveStepDefinitions {

    // A player (which should be mocked)
	private static Player mockPlayer;

	// Creates a stomach (for the player)...
	private Map<Color, Integer> stomach = new HashMap<>();

	// The panda objective to test...
	private PandaObjective pandaObjective;

	@Given("^I am a player$")
	public void i_am_a_player() {

	    // Creates the mock player
		mockPlayer = mock(Player.class);

	}
	 
	
	
	@Given("I have an objective of type ORIGINAL_GREEN")
	public void i_have_an_objective_of_type_ORIGINAL_GREEN() {
		
		pandaObjective = new PandaObjective(PandaObjective.Motif.ORIGINAL_GREEN) ;
	    
	}
	
	@Given("I have an objective of type ORIGINAL_YELLOW")
	public void i_have_an_objective_of_type_ORIGINAL_YELLOW() {
		
		pandaObjective = new PandaObjective(PandaObjective.Motif.ORIGINAL_YELLOW) ;
	    
	}
	
	@Given("I have an objective of type ORIGINAL_PINK")
	public void i_have_an_objective_of_type_ORIGINAL_PINK() {
		
		pandaObjective = new PandaObjective(PandaObjective.Motif.ORIGINAL_PINK) ;
	  
	}
	
	@Given("I have an objective of type ORIGINAL_THREE_COLORS")
	public void i_have_an_objective_of_type_ORIGINAL_THREE_COLORS() {
	    
		pandaObjective = new PandaObjective(PandaObjective.Motif.ORIGINAL_THREE_COLORS) ;
	}
	
	
	@Given("I have eaten {int} GREEN bamboos")
	public void i_have_eaten_GREEN_bamboos(int nombreGREEN) {
	    
		stomach.put(Color.GREEN, nombreGREEN) ;
	}

	@Given("I have eaten {int} YELLOW bamboos")
	public void i_have_eaten_YELLOW_bamboos(int nombreYELLOW) {
	    
		stomach.put(Color.YELLOW, nombreYELLOW) ;
	}

	@Given("I have eaten {int} PINK bamboos")
	public void i_have_eaten_PINK_bamboos(int nombrePINK) {
	    
		stomach.put(Color.PINK, nombrePINK) ;
	}
	
	@When("I check if i have validated the objective")
	public void i_check_if_i_have_validated_the_objective() {
	    
		when(mockPlayer.getStomach()).thenReturn(stomach);
		pandaObjective.checkIfCompleted(mockPlayer);
	}
	
	@Then("I should be answered ([^\"]*).$")
	public void i_should_be_answered(String answer) {
	    
		assertEquals(Boolean.valueOf(answer), pandaObjective.isCompleted());
	}

}
