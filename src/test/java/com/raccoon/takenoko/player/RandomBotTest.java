package com.raccoon.takenoko.player;

import com.raccoon.takenoko.Takeyesntko;
import com.raccoon.takenoko.game.*;
import com.raccoon.takenoko.game.tiles.Color;
import com.raccoon.takenoko.game.tiles.Tile;
import com.raccoon.takenoko.tool.ForbiddenActionException;
import org.junit.jupiter.api.Assertions;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


import java.awt.*;
import java.util.List;

import static junit.framework.TestCase.assertTrue;


@RunWith(JUnitPlatform.class)
@SpringBootTest
@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
class RandomBotTest {

    private Player p;
    private Game g;


    @Mock
    private RandomBot mockedBot;

    @BeforeEach
    void build(@Autowired Game game) {
    	Takeyesntko.setVerbose(false);

        g = game;
        p = new RandomBot();

        Tile greenTile0 = new Tile(Color.GREEN);
        Tile greenTile1 = new Tile(Color.GREEN);
        Tile pinkTile0 = new Tile(Color.PINK);

        g.getBoard().set(new Point(0, 1), greenTile0);
        g.getBoard().set(new Point(1, 1), pinkTile0);
        g.getBoard().set(new Point(1, 2), greenTile1);

    }

    @Test
    void testCreation() {
        assertEquals(0, p.getScore());
    }

    @Test
    void testPlayIncidenceOnBoard() {
        try {
            p.play(g);
        } catch (ForbiddenActionException e) {
            Assertions.assertNotNull(null, "Player's turn threw an exception.");
        }
        // we test that the starting tile has at least one neighbour
        assertTrue(g.getBoard().getNeighbours(new Point(0, 0)).size() > 0);
    }

    @Test
    void testWhereToPutGardener() {
        List<Point> accessiblePositions = g.getBoard().getAccessiblePositions(g.getGardener().getPosition());

        assertNotNull(p.whereToMoveGardener(g, accessiblePositions));
        assertTrue(accessiblePositions.contains(p.whereToMoveGardener(g, accessiblePositions)));
    }

    @Test
    void failingPlannedActions() {
        when(mockedBot.planActions(any())).thenReturn(new Action[]{});
        Point beforePoint = g.getGardener().getPosition();
        List<Point> av = g.getBoard().getAvailablePositions();

        try {
            mockedBot.play(g);
            fail("Expected an ForbiddenActionException to be thrown");
        } catch (Exception e) {
            assertEquals(ForbiddenActionException.class, e.getClass());
        }

        // for this test, we test that the player has had no impact on the board
        assertSame(beforePoint, g.getGardener().getPosition());
        assertSame(av, g.getBoard().getAvailablePositions());
    }

    @Test
    void failingMovingGardener() {
        g.getBoard().set(new Point(1, 1), new Tile(Color.GREEN));
        g.getBoard().set(new Point(2, 1), new Tile(Color.GREEN));
        Point beforePoint = g.getGardener().getPosition();

        when(mockedBot.planActions(any())).thenReturn(new Action[]{Action.MOVE_GARDENER, Action.VALID_OBJECTIVE});

        try {
            mockedBot.play(g);
            fail("Expected an ForbiddenActionException to be thrown");
        } catch (Exception e) {
            assertEquals(ForbiddenActionException.class, e.getClass());
        }

        // for this test, we test that the gardener hasn't moved
        assertSame(beforePoint, g.getGardener().getPosition());

    }
}
