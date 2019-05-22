package com.cco.takenoko.server.facade;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.util.stream.Collectors;

import org.springframework.beans.factory.ObjectFactory;

import org.springframework.context.annotation.Bean;

import com.cco.takenoko.server.TakenokoServer;

import com.cco.takenoko.server.game.Game;

import com.cco.takenoko.server.player.Player;

import com.cco.takenoko.server.tool.Constants;

public class ServerFacade {

	private static List<Player> players;
	private static int playersNumber;

	private static Game game;

	/**
	 * Launches the games (1000 by default) and prints the output.
	 * 
	 * @param gameObjectFactory
	 *  A factory of game 
	 */
	@Bean
	public void launchGames(ObjectFactory<Game> gameObjectFactory) {

		players = Player.getPlayers();
		playersNumber = players.size();

		TakenokoServer.setVerbose(false);

		Map<Integer, Integer> playerVictories = new HashMap<>();
		int[] scores = new int[playersNumber];

		for (int i = 1 ; i < playersNumber + 1 ; i++) {
			playerVictories.put(i, 0);
		}

		int voidedGames = 0;

		String[] playersTypes = new String[playersNumber];

		for (int i = 0; i < Constants.NUMBER_OF_GAMES_FOR_STATS; i++) {
			players.forEach(p -> p.initialize());
			game = gameObjectFactory.getObject();
			try {
				game.addPlayers(players);
			} catch (Exception e) {
				TakenokoServer.print("Something went wrong while adding the players");
			}
			game.start();

			// First check that it isn't a void game (all players at 0)
			int numberOfNullResults = 0;
			for (Player pl : game.getPlayers()) {
				if (pl.getScore() != 0) {
					break;
				} else {
					numberOfNullResults++;
				}
			}
			if (numberOfNullResults == playersNumber) {
				voidedGames++;
				continue;
			}

			// increments the wins of the winner
			playerVictories.put(game.getWinner().getId(), playerVictories.get(game.getWinner().getId()) + 1);

			// counts the points
			for (Player pl : game.getPlayers()) {
				playersTypes[pl.getId() - 1] = pl.getClass().getSimpleName();
				scores[pl.getId() - 1] += pl.getScore();
			}
		}

		// printing out results
		TakenokoServer.setVerbose(true);
		List<Map.Entry<Integer, Integer>> sortedWinners = playerVictories.entrySet().stream().sorted(Map.Entry.comparingByValue()).collect(Collectors.toList()); // Sorting the players according to their score
		TakenokoServer.print(String.format(" -- Launched %6.0f games!", Constants.NUMBER_OF_GAMES_FOR_STATS));
		TakenokoServer.print(String.format("| %-7s| %-10s| %-14s|", "Player", "Victories", "Average Score"));
		for (int i = sortedWinners.size() - 1; i + 1 > 0; i--) {
			int currentPlayer = sortedWinners.get(i).getKey();
			float currentPlayerVictories = (float)sortedWinners.get(i).getValue()*100 / Constants.NUMBER_OF_GAMES_FOR_STATS;
			float currentPlayerAverageScore = (float)scores[currentPlayer - 1] / Constants.NUMBER_OF_GAMES_FOR_STATS;
			TakenokoServer.print(String.format("| #%-6d|   %5.1f %% |    %10.2f |", currentPlayer, currentPlayerVictories, currentPlayerAverageScore));
		}
		TakenokoServer.print(String.format(" -- There has been %d void games where all players' scores were 0 (roughly %3.1f percents)", voidedGames, (voidedGames * 100 / Constants.NUMBER_OF_GAMES_FOR_STATS)));

		// Checksum : if the checksum is not nbGames, points were badly distributed
		int totalGames = 0;
		for (int w : playerVictories.values()) {
			totalGames += w;
		}
		TakenokoServer.print(String.format(" -- Checksum : won + voided games add up to %d (should be %3.0f)%n", totalGames + voidedGames, Constants.NUMBER_OF_GAMES_FOR_STATS));
	}

}
