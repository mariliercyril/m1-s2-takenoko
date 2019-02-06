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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(JUnitPlatform.class)
@SpringBootTest
@ExtendWith(SpringExtension.class)
class TileTest {
    private Game g;

    private Tile origin;
    private Tile t1;

    @BeforeEach
    void setup(@Autowired Game game) {
        g = game;
        g.getBoard().set(new Point(0, 1), new Tile(com.raccoon.takenoko.game.tiles.Color.GREEN));

        // put down some tiles
        g.getBoard().set(new Point(1, 0), new Tile(com.raccoon.takenoko.game.tiles.Color.PINK));
        g.getBoard().set(new Point(0, -1), new Tile(Color.GREEN));

        // keep them somewhere (for lisibility)
        origin = g.getBoard().get(new Point(0, 0));
        t1 = g.getBoard().get(new Point(1, 0));

        // irrigate them where possible
        t1.irrigate(UnitVector.M);
        t1.irrigate(UnitVector.N);

        g.getGardener().move(new Point(0, 1));
    }

    @Test
    void canIncreaseBambooSize() {
        assertTrue( t1.getBambooSize() > 0, "Didn't grow bamboo on tile even though it is irrigated.");
    }

    @Test
    void cantPutBambooOnLake() {
        origin.increaseBambooSize();
        assertEquals(0, origin.getBambooSize(), "Grew a bamboo on the lake tile.");
    }

    @Test
    void canDecreaseBambooSize() {
        int currentSize = t1.getBambooSize();
        t1.decreaseBambooSize();
        assertTrue(t1.getBambooSize() < currentSize, "Didn't decrease bamboo size even though asked.");
    }

    @Test
    void cantEatWhereThereIsNoBamboo() {
        origin.decreaseBambooSize();
        assertFalse(origin.getBambooSize() < 0, "Ate a bamboo on a tile where there was no bamboo.");
    }

    @Test
    void tileNextToLakeIsIrrigated() {
        assertTrue(t1.isIrrigated(), "Tile is not irrigated even though asked.");
        // N is the unit Vector (0, -1)
        assertEquals(IrrigationState.IRRIGATED, t1.getIrrigationState(UnitVector.N), "Tile has not been irrigated in the right direction");
    }

}
