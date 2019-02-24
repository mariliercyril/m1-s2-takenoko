package com.raccoon.takenoko.cucumber;

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

public class StepDefinitions {

	private static Player mockPlayer;

	private Map<Color, Integer> stomach = new HashMap<>();

	private PandaObjective pandaObjective;

	@Given("^I am a player$")
	public void i_am_a_player() {

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

	@When("^I check if the \"([^\"]*)\" is completed$")
	public void i_check_if_the_objective_in_question_is_completed(String objectiveName) {

		pandaObjective = new PandaObjective(Enum.valueOf(PandaObjective.Motif.class, objectiveName));

		when(mockPlayer.getStomach()).thenReturn(stomach);
		pandaObjective.checkIfCompleted(mockPlayer);
	}

	@Then("^the response should be \"([^\"]*)\"$")
	public void the_response_should_be(String response) {

		System.out.println(response);
	}

}
