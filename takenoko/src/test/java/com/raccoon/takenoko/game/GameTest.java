package com.raccoon.takenoko.game;

import com.raccoon.takenoko.Takeyesntko;
import com.raccoon.takenoko.game.tiles.Tile;
import com.raccoon.takenoko.player.Player;
import com.raccoon.takenoko.tool.ForbiddenActionException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.annotation.Resource;
import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(JUnitPlatform.class)
@SpringBootTest
@ExtendWith(SpringExtension.class)
class GameTest {

    @Resource(name = "&everyOther")
    FactoryBean<Player> playerFactory;


    private Game game;

    @BeforeEach
    void setUp(@Autowired Game game) {
        Takeyesntko.setVerbose(false);
        this.game = game;
        try {
            game.addPlayers(4, playerFactory);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    void gameOver() {

        game.start();                   // Play one game
        assertTrue(game.gameOver(), "Game never ends");    // When the game ends, it should be over
    }

    @Test
    void start() {
        game.start();
        assertNotNull(game.getBoard().get(new Point(0, 0)), "There is noooooo board~");     // As good as it gets for now
    }

    // @Disabled("Method evolved, mock it")
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
        java.util.List<Tile> threeTiles = game.getTiles();   // Check that we actually pick three tiles
        assertEquals(3, threeTiles.size(), "Player hasn't taken 3 tiles");
        while (game.getTilesDeck().size() > 0) {
            game.getTile(); //  Removes three tiles
        }
        game.putBackTile(threeTiles.get(0));
        game.putBackTile(threeTiles.get(1));
        assertEquals(2, game.getTilesDeck().size()); // There should only be two tiles picked since there were only two in the deck
        java.util.List<Tile> twoTiles = game.getTiles();
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
/*
    @Test
    void purgeTest() {
        game.getBoard().set(new Point(0, 1), game.getTile());
        game.getBoard().set(new Point(1,0), game.getTile());
        assertEquals(25, game.getTilesDeck().size());
        game.purge();
        assertNull(game.getBoard().get(new Point(0,1)));
        assertNull(game.getBoard().get(new Point(1,0)));
        assertEquals(27, game.getTilesDeck().size());
    }


    @Test
    public void purgeGame() {
        Board b = game.getBoard();
        game.purge();
        assertNotSame(b, game.getBoard(), "Board has not been reinitialized.");
        assertEquals(27, game.getTilesDeck().size(), "Tile dech has not been reinitialized.");
    }*/
}