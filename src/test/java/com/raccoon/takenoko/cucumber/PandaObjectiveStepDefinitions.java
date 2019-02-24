package com.raccoon.takenoko.cucumber;

import static org.junit.Assert.assertEquals;

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

	@Given("^I have eaten (\\d+) green bamboo chunks$")
	public void i_have_eaten_n_green_bamboo_chunks(Integer number) {

		stomach.put(Color.GREEN, number) ;
	}

	@Given("^I have eaten (\\d+) yellow bamboo chunks$")
	public void i_have_eaten_n_yellow_bamboo_chunks(Integer number) {

		stomach.put(Color.YELLOW, number) ;
	}

	@Given("^I have eaten (\\d+) pink bamboo chunks$")
	public void i_have_eaten_n_pink_bamboo_chunks(Integer number) {

		stomach.put(Color.PINK, number) ;
	}

	@When("I ask whether the {string} objective is completed")
	public void i_ask_whether_the_objective_is_completed(String objectiveName) {

		// The panda objective depends on the scenario:
		pandaObjective = new PandaObjective(Enum.valueOf(PandaObjective.Motif.class, objectiveName));

        // So that the mock player returns the stomach (of the panda)
		when(mockPlayer.getStomach()).thenReturn(stomach);
		pandaObjective.checkIfCompleted(mockPlayer);
	}

	@Then("^the response should be ([^\"]*).$")
	public void the_response_should_be(String response) {

		assertEquals(Boolean.valueOf(response), pandaObjective.isCompleted());
	}

}
