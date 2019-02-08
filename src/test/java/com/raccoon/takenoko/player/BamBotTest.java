package com.raccoon.takenoko.player;

import com.raccoon.takenoko.game.objective.PatternObjective;
import com.raccoon.takenoko.game.tiles.Color;
import com.raccoon.takenoko.game.Game;
import com.raccoon.takenoko.game.tiles.Tile;
import com.raccoon.takenoko.game.objective.PandaObjective;
import com.raccoon.takenoko.tool.UnitVector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.annotation.Resource;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@RunWith(JUnitPlatform.class)
@SpringBootTest
@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
class BamBotTest {


    private Game g;
    private Player bot;
    // I don't want to write Color.GREEN every time I need it (which is a lot)
    private final Color green = Color.GREEN;
    private final Color pink = Color.PINK;
    private final Color yellow = Color.YELLOW;

    @Mock
    private PandaObjective pObj1;
    @Mock
    private PatternObjective aObj1;

    @Resource(name = "&everyOther")
    FactoryBean<Player> playerFactory;

    private void place(int x, int y, Color c) {
        g.getBoard().set(new Point(x,y), new Tile(c));
    }

    private void irrigate(int x, int y, UnitVector v) {
        g.getBoard().irrigate(new Point(x,y), v);
    }



    @BeforeEach
    void init(@Autowired Game game) {
        g = game;
        try {
            g.addPlayers(4, playerFactory);
        } catch (Exception e) {
            e.printStackTrace();
        }
        bot = g.getPlayers().get(1);    // Be careful, how to be sure this won't be another implementation of player ?
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

        assertNotNull(bot.whereToPutDownTile(g, pinkTile));
        assertNotNull(bot.whereToPutDownTile(g, greenTile));

        irrigate(1,1, UnitVector.L);
        irrigate(1,1, UnitVector.K);
        irrigate(1,1, UnitVector.J);
        irrigate(1,1, UnitVector.I);

        assertEquals(new Point(2,2), bot.whereToPutDownTile(g, pinkTile));
        assertNotNull(bot.whereToPutDownTile(g, greenTile));

        irrigate(-1, 0, UnitVector.N);
        irrigate(-1, 0, UnitVector.M);
        irrigate(-2, 0, UnitVector.N);
        irrigate(-1, -1, UnitVector.L);
        irrigate(-2, -2, UnitVector.K);

        assertEquals(new Point(-2, -1), bot.whereToPutDownTile(g, greenTile));
        assertEquals(new Point(2,2), bot.whereToPutDownTile(g, pinkTile));
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
        assertTrue(possibleBest.contains(bot.whereToMoveGardener(g, g.getBoard().getAccessiblePositions(g.getGardener().getPosition()))));

        // Making a second, better, cluster of tiles
        irrigate(-1, 0, UnitVector.J);
        irrigate(-1, 0, UnitVector.K);
        irrigate(-1, 0, UnitVector.L);
        irrigate(-1, 0, UnitVector.M);
        assertEquals(new Point(-1, 0), bot.whereToMoveGardener(g, g.getBoard().getAccessiblePositions(g.getGardener().getPosition())));

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

        assertTrue(possibleBest.contains(bot.whereToMoveGardener(g, g.getBoard().getAccessiblePositions(g.getGardener().getPosition()))));
    }


    @Test
    void whereToMovePanda() {
        place(0, 1, pink);
        place(1,1, pink);
        place(1, 0, green);

        irrigate(1, 1, UnitVector.N);
        irrigate(1, 1, UnitVector.I);
        irrigate(1, 1, UnitVector.J);

        bot.getStomach().put(pink, 1);  // The player already has a pink bamboo in his stomach

        assertEquals(new Point(1, 0), bot.whereToMovePanda(g, g.getBoard().getAccessiblePositions(g.getPanda().getPosition())));    // He goes to eat the green bamboo
        bot.getStomach().put(green, 1); // He eats the green bamboo

        place(2, 1, yellow);
        place(2, 2, yellow);
        g.getGardener().move(new Point(2, 2));

        //assertEquals(new Point(2, 2), bot.whereToMovePanda(g, g.getBoard().getAccessiblePositions(g.getPanda().getPosition())));    // Now he only needs yellow
    }

    @Test
    void planActions() {
    }

    @Test
    void chooseObjectiveToValidate() {

        assertEquals(0, bot.getObjectives().size());    // No objective in hand -> null
        assertNull(bot.chooseObjectiveToValidate());

        bot.addObjective(aObj1);
        bot.addObjective(pObj1);
        when(pObj1.isCompleted()).thenReturn(false);
        when(aObj1.isCompleted()).thenReturn(false);

        assertEquals(2, bot.getObjectives().size());    // 1 objective of each in hand but none completed -> null
        assertNull(bot.chooseObjectiveToValidate());

        when(aObj1.isCompleted()).thenReturn(true); // When only a non-panda objective is completed -> the non-panda objective
        assertSame(aObj1, bot.chooseObjectiveToValidate());

        when(pObj1.isCompleted()).thenReturn(true);
        assertSame(pObj1, bot.chooseObjectiveToValidate());

    }
}