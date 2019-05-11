package com.cco.takenoko.server.game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;

import org.junit.platform.runner.JUnitPlatform;

import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.cco.takenoko.server.TakenokoServer;

import com.cco.takenoko.server.game.Game;

import com.cco.takenoko.server.game.tiles.Tile;

import com.cco.takenoko.server.player.Player;

import com.cco.takenoko.server.tool.ForbiddenActionException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.Point;

import java.util.ArrayList;
import java.util.List;

@RunWith(JUnitPlatform.class)
@SpringBootTest
@ExtendWith(SpringExtension.class)
class GameTest {

	List<Player> players = new ArrayList<>();

	private Game game;

	@BeforeEach
	void setUp(@Autowired Game game) {

		for (int i = 0; i < 4; i++) {
			players.add(new Player(i + 1));
		}
		TakenokoServer.setVerbose(false);
		this.game = game;
		try {
			game.addPlayers(players);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	void gameOver() {

		// Plays one game
		game.start();
		// When the game ends, it should be over
		assertTrue(game.gameOver(), "Game never ends");
	}

	@Test
	void start() {

		game.start();
		assertNotNull(game.getBoard().get(new Point(0, 0)), "There is noooooo board~");     // As good as it gets for now
	}

	//@Disabled("Method evolved, mock it")
	@Test
	void getWinner() {

		for (int i = 0; i <= 9; i++) {
			try {
				game.getPlayers().get(0).play(game);
			} catch (ForbiddenActionException e) {
				assertNotNull(null, "Player's turn threw an exception.");
			}
		}
		assertNotNull(game.getWinner(), "The winner does not exist");
		assertTrue(game.getPlayers().get(0).getScore() >= game.getPlayers().get(1).getScore(), "Second player seems to be better than first player");
	}

	@Test
	void initDeck() {

		assertEquals(27, game.getTilesDeck().size(), "Full deck has not been initialized");
	}

	@Test
    void putBackTileTest() {

		Tile test = game.getTile();
		assertFalse(game.getTilesDeck().contains(test), "Deck still contains taken tile");
		game.putBackTile(test);

		assertTrue(game.getTilesDeck().contains(test), "Desk doesn't contain put back tile");
	}

	@Test
    void getTilesTest() {

		// Checks that we actually pick three tiles
		List<Tile> threeTiles = game.getTiles();
		assertEquals(3, threeTiles.size(), "Player hasn't taken 3 tiles");
		while (game.getTilesDeck().size() > 0) {
			// Removes three tiles
			game.getTile();
		}
		game.putBackTile(threeTiles.get(0));
		game.putBackTile(threeTiles.get(1));
		// There should only be two tiles picked since there were only two in the deck
		assertEquals(2, game.getTilesDeck().size());
		List<Tile> twoTiles = game.getTiles();
		assertEquals(2, twoTiles.size());
	}

	@Test
	void irrigatedFirstTile() {

		Tile t = game.getTile();
		game.getBoard().set(new Point(0, 1), t);
		assertTrue(t.isIrrigated(), "Tile next to lake is not irrigated");
	}

	@Test
    void bambooSizeOnTileTest() {

		Tile t = game.getTile();
		game.getBoard().set(new Point(0, 1), t);
		if (t.isIrrigated()) {
			assertTrue(t.getBambooSize() > 0, "Tile is irrigated but bamboo didn't grow");
		} else {
			assertFalse(t.getBambooSize() > 0, "Tile is dry but bamboo grew");
		}
	}

}
