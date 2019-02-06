package com.raccoon.takenoko.game;

import com.raccoon.takenoko.game.tiles.Color;
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
class PawnTest {

    private Board testBoard;

    private Gardener testGardener;
    private Panda testPanda;

    private Tile greenTile0 = new Tile(com.raccoon.takenoko.game.tiles.Color.GREEN);
    private Tile greenTile1 = new Tile(com.raccoon.takenoko.game.tiles.Color.GREEN);
    private Tile greenTile2 = new Tile(com.raccoon.takenoko.game.tiles.Color.GREEN);
    private Tile pinkTile0 = new Tile(com.raccoon.takenoko.game.tiles.Color.PINK);
    private Tile yellowTile0 = new Tile(com.raccoon.takenoko.game.tiles.Color.YELLOW);
    private Tile greenTile3 = new Tile(Color.GREEN);


    @BeforeEach
    void setUp(@Autowired Game game) {
        this.testBoard = game.getBoard();
        this.testGardener = game.getGardener();
        this.testPanda = game.getPanda();
    }

    @Test
    void move() {

        /*
        We add 5 tiles to create a board
         */
        testBoard.set(new Point(0,1), greenTile0);
        testBoard.set(new Point(1,1), pinkTile0);
        testBoard.set(new Point(1,2), greenTile1);
        testBoard.set(new Point(0,-1), greenTile2);
        testBoard.set(new Point(0,2), yellowTile0);
        testBoard.set(new Point(1,0), greenTile3);

        testBoard.irrigate(new Point(1, 1), UnitVector.L);
        testBoard.irrigate(new Point(1, 1), UnitVector.K);

        assertEquals(1, testBoard.get(new Point(1, 2)).getBambooSize());

        testGardener.move(new Point(0,1));
        assertEquals(2, greenTile1.getBambooSize(), "The tile adjacent to the tile where the gardener moved didn't grow a bamboo");

        testPanda.move(new Point(1,2));  // For the purpose of this test, it doesn't matter whether the panda is actually allowed to the tiles, we are only testing the effects of the panda arriving
        // The following tests also make sure that the surrounding tiles weren't affected by the panda
        assertEquals(1, greenTile1.getBambooSize(), "The tile where the panda landed didn't have one piece of bamboo eaten");
        testPanda.move(new Point(1,0));
        assertEquals(0, greenTile3.getBambooSize(), "The tile where the panda landed had a piece of bamboo eaten even though there wasn't any bamboo to eat");
        assertEquals(2, greenTile0.getBambooSize(), "The tile where the gardener is moved didn't grow a bamboo");
        assertEquals(1, pinkTile0.getBambooSize(), "The tile adjacent to the gardener grew a bamboo but wasn't of the same colour");
        assertEquals(0, yellowTile0.getBambooSize(), "The tile adjacent to the gardener grew a bamboo but wasn't of the same colour");
        assertEquals(1, greenTile2.getBambooSize(), "A tile of the right colour but not adjacent to the gardener's one changed its bamboo");

    }
}