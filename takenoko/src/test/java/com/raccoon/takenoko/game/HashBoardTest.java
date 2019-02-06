package com.raccoon.takenoko.game;

import com.raccoon.takenoko.game.tiles.Color;
import com.raccoon.takenoko.game.tiles.IrrigationState;
import com.raccoon.takenoko.game.tiles.Tile;
import com.raccoon.takenoko.tool.UnitVector;
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

import java.awt.*;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(JUnitPlatform.class)
@SpringBootTest
@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
class HashBoardTest {

    @Mock
    Tile tile1;

    private Board board;

    private Game g;

    private Game g2;

    private Tile t1;
    private Tile t2;
    private Tile t3;

    private Tile t4;
    private Tile t5;
    private Tile t6;
    private Tile t7;


    @BeforeEach
    void setUp(@Autowired Game game, @Autowired Game g2) {
        this.g2 = g2;
        g = game;
        board = g.getBoard();
        board.set(new Point(0, 1), new Tile(Color.GREEN));

        // put down some tiles
        board.set(new Point(1, 0), new Tile(Color.PINK));
        board.set(new Point(0, -1), new Tile(Color.GREEN));
        board.set(new Point(1, -1), new Tile(Color.YELLOW));

        board.set(new Point(1, 1), new Tile(Color.YELLOW));
        board.set(new Point(0, 1), new Tile(Color.YELLOW));
        board.set(new Point(2, 1), new Tile(Color.YELLOW));
        board.set(new Point(2, 2), new Tile(Color.YELLOW));

        // keep them somewhere (for lisibility)
        Tile origin = board.get(new Point(0, 0));

        t1 = board.get(new Point(1, 0));
        t2 = board.get(new Point(0, -1));
        t3 = board.get(new Point(1, -1));

        t4 = board.get(new Point(1, 1));
        t5 = board.get(new Point(0, 1));
        t6 = board.get(new Point(2, 1));
        t7 = board.get(new Point(2, 2));

        // irrigate them where possible
        board.irrigate(t1.getPosition(), UnitVector.M);
        board.irrigate(t1.getPosition(), UnitVector.N);
        board.irrigate(t1.getPosition(), UnitVector.K);


    }

    @Test
    void getSet() {

        when(tile1.getIrrigationState(any())).thenReturn(IrrigationState.IRRIGABLE);

        board.set(new Point(0, 1), tile1);

        verify(tile1).setPosition(eq(new Point(0, 1)));  // Check that the tile coordinates has been set here again

        assertEquals(board.get(new Point(0, 1)), tile1);

    }

    @Test
    void TestGetNeighbours() {

        assertTrue(board.getAvailablePositions().contains(new Point(-1, 0)));
        assertFalse(board.getAvailablePositions().contains(new Point(-1, 1)));
        assertFalse(board.getAvailablePositions().contains(new Point(0, 1)));

    }

    @Test
    void irrigationTest() {

        when(tile1.getIrrigationState(any())).thenReturn(IrrigationState.IRRIGABLE);

        Tile test_wet = new Tile(Color.YELLOW);
        board.set(new Point(0, 1), test_wet);
        board.set(new Point(1, 1), tile1);   // We need a tile here to put another one in (1, 2) for the next test
        assertTrue(test_wet.isIrrigated(), "The tile should be irrigated because it's adjacent to the pond");

        Tile test_dry = new Tile();
        board.set(new Point(1, 2), test_dry);
        assertFalse(test_dry.isIrrigated(), "This tile shouldn't be irrigated because none of its neighbors have an adjacent irrigation");
    }

    @Test
    void irrigateTest() {
        assertTrue(board.irrigate(t1.getPosition(), UnitVector.J), "Irrigation can't be put between to correct tiles");
        assertFalse(board.irrigate(t3.getPosition(), UnitVector.K.opposite()), "Irrigation put on a tile with no other tile adjacent");

    }

    @Test
    void accessiblePositionsTest() {

        Point start = new Point(0, 0);

        board.set(new Point(0, -1), new Tile(Color.YELLOW));
        board.set(new Point(1, 0), new Tile(Color.YELLOW));
        board.set(new Point(1, -1), new Tile(Color.YELLOW));

        assertTrue(board.getAccessiblePositions(start).contains(new Point(0, -1)));
        assertTrue(board.getAccessiblePositions(start).contains(new Point(1, 0)));
        assertFalse(board.getAccessiblePositions(start).contains(new Point(1, -1)));
        assertFalse(board.getAccessiblePositions(start).contains(new Point(-1, -2)));

        board.set(new Point(-1, -1), new Tile(Color.YELLOW));
        board.set(new Point(-1, -2), new Tile(Color.YELLOW));
        board.set(new Point(-2, -2), new Tile(Color.YELLOW));
        board.set(new Point(0, -2), new Tile(Color.YELLOW));
        board.set(new Point(2, 0), new Tile(Color.YELLOW));

        assertTrue(board.getAccessiblePositions(start).contains(new Point(-2, -2)));
        assertTrue(board.getAccessiblePositions(start).contains(new Point(2, 0)));
        assertTrue(board.getAccessiblePositions(start).contains(new Point(0, -2)));
        assertFalse(board.getAccessiblePositions(start).contains(new Point(-1, -2)));
    }

    @Test
    void irrigatedTowardsSomething() {
        assertTrue(t3.isIrrigated(), "Tile next to last irrigated tile, and in the right direction, is not irrigated");
        // K is the unit Vector (0, 1)
        assertEquals(IrrigationState.IRRIGATED, t3.getIrrigationState(UnitVector.K), "Next tile irrigated is irrigated in the wrong direction.");
    }

    @Test
    void isIrrigable() {
        // test case with current setup
        assertFalse(t7.isIrrigable(), "Should not be irrigable for there is no water path to it");
        assertTrue(t4.isIrrigable(), "Should be irrigable as it in sext to pond.");

        // add an irrigation and check consequences
        assertTrue(board.irrigate(t4.getPosition(), UnitVector.I), "If previous test passed, should be irrigated.");
        assertTrue(t6.isIrrigable(), "Just set a path to it, should be irrigable.");
        assertTrue(t7.isIrrigable(), "Just set a path to it, should be irrigable by now.");
        assertEquals(IrrigationState.TO_BE_IRRIGABLE, t1.getIrrigationState(UnitVector.I), "should be irrigable in this direction once a tile is put down");

        // set a tile at an irrigable place and check if I can put down an irrigation between them.
        board.set(new Point(2, 0), new Tile(Color.PINK));
        assertEquals(IrrigationState.IRRIGABLE, t1.getIrrigationState(UnitVector.I), "Just put down a tile at the right place, this place should be irrigable.");
        assertEquals(IrrigationState.IRRIGABLE, board.get(new Point(2, 0)).getIrrigationState(UnitVector.I.opposite()), "Just put down a tile at the right place, the new tile should be irrigable.");

        // irrigate an entire tile and check consequences on said tile
        board.irrigate(t1.getPosition(), UnitVector.I);
        board.irrigate(t1.getPosition(), UnitVector.J);
        assertFalse(t1.isIrrigable(), "Tile should be entirely irrigated, can't set an irrigation there anymore.");
    }

    @Test
    void nextNNeighbours() {
        // We just need a set of tiles to test, I will put them here for lisibility

        Board b2 = g2.getBoard();
        b2.set(new Point(1, 0), new Tile(Color.PINK));
        b2.set(new Point(0, -1), new Tile(Color.GREEN));
        b2.set(new Point(1, -1), new Tile(Color.PINK));
        b2.set(new Point(-1, -1), new Tile(Color.GREEN));
        b2.set(new Point(2, 1), new Tile(Color.GREEN));
        b2.set(new Point(-1, -2), new Tile(Color.PINK));
        b2.set(new Point(0, -2), new Tile(Color.PINK));
        b2.set(new Point(2, 2), new Tile(Color.YELLOW));
        b2.set(new Point(3, 2), new Tile(Color.GREEN));
        b2.set(new Point(3, 1), new Tile(Color.GREEN));
        b2.set(new Point(3, 3), new Tile(Color.YELLOW));
        b2.set(new Point(4, 3), new Tile(Color.YELLOW));

        Set<Tile> dist1 = b2.getAllTilesDistance(new Point(0, 0), 1);
        assertFalse(dist1.contains(b2.get(new Point(2, 2))), "Tile is too far away to be at distance 1");
        assertTrue(dist1.contains(b2.get(new Point(1, 1))),"Tile is supposed to be in the list of distances 1");

        Set<Tile> dist2 = b2.getAllTilesDistance(new Point(0, 0), 2);
        assertFalse(dist2.contains(b2.get(new Point(4, 3))), "Tile is too far away to be at distance 2");
        assertTrue(dist2.contains(b2.get(new Point(2, 1))), "Tile is supposed to be in the list of distance 2");
    }

}