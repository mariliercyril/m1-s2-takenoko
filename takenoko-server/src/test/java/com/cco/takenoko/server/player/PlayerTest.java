package com.cco.takenoko.server.player;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;

import org.junit.platform.runner.JUnitPlatform;

import org.junit.runner.RunWith;

import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.cco.takenoko.server.game.Game;

import com.cco.takenoko.server.game.objective.PandaObjective;
import com.cco.takenoko.server.game.objective.PatternObjective;

import com.cco.takenoko.server.game.tiles.Color;
import com.cco.takenoko.server.game.tiles.Tile;

import com.cco.takenoko.server.tool.UnitVector;

import java.awt.Point;

import java.util.List;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static org.mockito.Mockito.when;

@RunWith(JUnitPlatform.class)
@SpringBootTest
@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
class PlayerTest {

	private Game g;
	private Player player;
	// I don't want to write Color.GREEN every time I need it (which is a lot)
	private final Color green = Color.GREEN;
	private final Color pink = Color.PINK;
	private final Color yellow = Color.YELLOW;

	@Mock
	private PandaObjective pObj1;
	@Mock
	private PatternObjective aObj1;

	List<Player> players = new ArrayList<>();

	private void place(int x, int y, Color c) {

		g.getBoard().set(new Point(x,y), new Tile(c));
	}

	private void irrigate(int x, int y, UnitVector v) {

		g.getBoard().irrigate(new Point(x,y), v);
	}

	@BeforeEach
	void init(@Autowired Game game) {

		for (int i = 0; i < 4; i++) {
			players.add(new Player(i + 1));
		}
		g = game;
		try {
			g.addPlayers(players);
		} catch (Exception e) {
			e.printStackTrace();
		}
		player = g.getPlayers().get(1);    // Be careful, how to be sure this won't be another implementation of player ?
		// Actually had to be fixed because with a minor change in the code the implementation was different…
	}

	@Test
	void whereToPutDownTile() {

		place(0,1, yellow);
		place(1,1, pink);
		place(1,0,yellow);
		place(0, -1, pink);
		place(-1, -1, green);
		place(-1, 0, green);

		place(1,2, pink);
		place(2, 1, pink);
		place(1, -1, yellow);
		place(-1, -2, yellow);
		place(-1, 1, pink);

		place(0,2, yellow);
		place(2,0, green);
		place(0,2, green);
		place(-2, -2, green);
		place(-2, 0, green);

		Tile pinkTile = new Tile(pink);
		Tile greenTile = new Tile(green);

		assertNotNull(player.whereToPutDownTile(g, pinkTile));
		assertNotNull(player.whereToPutDownTile(g, greenTile));

		irrigate(1,1, UnitVector.L);
		irrigate(1,1, UnitVector.K);
		irrigate(1,1, UnitVector.J);
		irrigate(1,1, UnitVector.I);

		assertEquals(new Point(2,2), player.whereToPutDownTile(g, pinkTile));
		assertNotNull(player.whereToPutDownTile(g, greenTile));

		irrigate(-1, 0, UnitVector.N);
		irrigate(-1, 0, UnitVector.M);
		irrigate(-2, 0, UnitVector.N);
		irrigate(-1, -1, UnitVector.L);
		irrigate(-2, -2, UnitVector.K);

		assertEquals(new Point(-2, -1), player.whereToPutDownTile(g, greenTile));
		assertEquals(new Point(2,2), player.whereToPutDownTile(g, pinkTile));
	}

	@Test
	void whereToMoveGardener() {

		// First ring
		place(0,1, green);
		place(1,1, yellow);
		place(1,0, green);
		place(0,-1,yellow);
		place(-1, -1, pink);
		place(-1,0, green);

		// Half of the second ring
		place(-1, 1, green);
		place(1,2, pink);
		place(2, 1, yellow);
		place(1, -1, pink);
		place(-1, -2, pink);
		place(-2, -1, green);

		// Second half of the second ring
		place(0, 2, pink);
		place(2, 2, green);
		place(2,0, yellow);
		place(0, -2, yellow);
		place(-2, -2, pink);
		place(-2, 0, green);

		// Irrigating a few tiles to make a single cluster of growable tiles
		irrigate(-1, -1, UnitVector.I);
		irrigate(-1, -1, UnitVector.N);
		irrigate(-1, -1, UnitVector.M);

		List<Point> possibleBest = new ArrayList<>();   // These two spots are equivalent, the best could be either
		possibleBest.add(new Point(-2, -2));
		possibleBest.add(new Point(-1, -1));
		assertTrue(possibleBest.contains(player.whereToMoveGardener(g, g.getBoard().getAccessiblePositions(g.getGardener().getPosition()))));

		// Making a second, better, cluster of tiles
		irrigate(-1, 0, UnitVector.J);
		irrigate(-1, 0, UnitVector.K);
		irrigate(-1, 0, UnitVector.L);
		irrigate(-1, 0, UnitVector.M);
		assertEquals(new Point(-1, 0), player.whereToMoveGardener(g, g.getBoard().getAccessiblePositions(g.getGardener().getPosition())));

		// Making the second cluster worst than the first one by saturating it with bamboos
		Tile currentGrow = g.getBoard().get(new Point(0, 1));
		while (currentGrow.getBambooSize() < 4) {
			currentGrow.increaseBambooSize();
		}
		currentGrow  = g.getBoard().get(new Point(-1, 1));
		while (currentGrow.getBambooSize() < 4) {
			currentGrow.increaseBambooSize();
		}

		currentGrow = g.getBoard().get(new Point(-2, 0));
		while (currentGrow.getBambooSize() < 4) {
			currentGrow.increaseBambooSize();
		}

		assertTrue(possibleBest.contains(player.whereToMoveGardener(g, g.getBoard().getAccessiblePositions(g.getGardener().getPosition()))));
	}

    @Test
    void whereToMovePanda() {

    	place(0, 1, pink);
    	place(1,1, pink);
    	place(1, 0, green);

    	irrigate(1, 1, UnitVector.N);
    	irrigate(1, 1, UnitVector.I);
    	irrigate(1, 1, UnitVector.J);

    	player.getStomach().put(pink, 1);  // The player already has a pink bamboo in his stomach

    	// He goes to eat the green bamboo
    	assertEquals(new Point(1, 0), player.whereToMovePanda(g, g.getBoard().getAccessiblePositions(g.getPanda().getPosition())));
    	player.getStomach().put(green, 1); // He eats the green bamboo

    	place(2, 1, yellow);
    	place(2, 2, yellow);
    	g.getGardener().move(new Point(2, 2));

    	// Now he only needs yellow
    	//assertEquals(new Point(2, 2), player.whereToMovePanda(g, g.getBoard().getAccessiblePositions(g.getPanda().getPosition())));
	}

	@Test
	void planActions() {}

	@Test
	void chooseObjectiveToValidate() {

		// No objective in hand -> null
		assertEquals(0, player.getObjectives().size());
        assertNull(player.chooseObjectiveToValidate());

        player.addObjective(aObj1);
        player.addObjective(pObj1);
        when(pObj1.isCompleted()).thenReturn(false);
        when(aObj1.isCompleted()).thenReturn(false);

        // 1 objective of each in hand but none completed -> null
        assertEquals(2, player.getObjectives().size());
        assertNull(player.chooseObjectiveToValidate());

        // When only a non-panda objective is completed -> the non-panda objective
        when(aObj1.isCompleted()).thenReturn(true);
        assertSame(aObj1, player.chooseObjectiveToValidate());

        when(pObj1.isCompleted()).thenReturn(true);
        assertSame(pObj1, player.chooseObjectiveToValidate());
	}

}
