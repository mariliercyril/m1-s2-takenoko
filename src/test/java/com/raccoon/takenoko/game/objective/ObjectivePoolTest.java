package com.raccoon.takenoko.game.objective;

import com.raccoon.takenoko.game.Board;
import com.raccoon.takenoko.game.tiles.Color;
import com.raccoon.takenoko.player.Player;
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

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@RunWith(JUnitPlatform.class)
@SpringBootTest
@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
class ObjectivePoolTest {

    @Mock
    Player player;

    @Mock
    Board board;

    private ObjectivePool objectivePool;

    private Objective gardenerObjective;
    private Objective pandaObjective;
    private Objective patternObjective;

    private Map<Color,Integer> stomach;

    @BeforeEach
    void setUp(@Autowired ObjectivePool objectivePool) {

        this.objectivePool = objectivePool;

        this.gardenerObjective = this.objectivePool.draw(ObjectiveType.GARDENER);
        this.pandaObjective = this.objectivePool.draw(ObjectiveType.PANDA);
        this.patternObjective = this.objectivePool.draw(ObjectiveType.PATTERN);

        this.stomach = new EnumMap<>(Color.class);


    }

    @Test
    void draw() {

        assertNotNull(gardenerObjective, "First gardener objective drawn is null");
        assertNotNull(pandaObjective, "First panda objective drawn is null");
        assertNotNull(patternObjective, "First pattern objective drawn is null");

        assertTrue(gardenerObjective instanceof GardenerObjective, "Drawn a gardener objective, ended up with another kind of objective…");
        assertTrue(pandaObjective instanceof PandaObjective, "Drawn a panda objective, ended up with another kind of objective…");
        assertTrue(patternObjective instanceof PatternObjective, "Drawn a pattern objective, ended up with another kind of objective…");

    }

    @Test
    void notifyBambooEaten() {

        when(player.getStomach()).thenReturn(stomach);
        when(player.getObjectives()).thenReturn(new ArrayList<>(Arrays.asList(this.pandaObjective)));

        when(board.getAllTiles()).thenReturn(new ArrayList<>());

        stomach.put(Color.YELLOW, 2);
        stomach.put(Color.PINK, 2);
        stomach.put(Color.GREEN, 2);

        objectivePool.notifyBambooEaten(board, player);

        assertTrue(this.pandaObjective.isCompleted(), "2 bamboos of each kind in the stomach and still not a panda objective completed ?");
        assertFalse(this.gardenerObjective.isCompleted(), "For now, with an empty board, a GardenerObjective shouldn't be completed");

        stomach.put(Color.YELLOW, 0);
        stomach.put(Color.PINK, 0);
        stomach.put(Color.GREEN, 0);

        objectivePool.notifyStomachEmptied(player);

        assertFalse(this.pandaObjective.isCompleted(), "No more bamboos in the stomach, still a panda objective completed");

    }

    @Test
    void objectiveTypeTest() {

        assertEquals(ObjectiveType.GARDENER, objectivePool.getObjectiveType(gardenerObjective), "The panda objective we drawn is not recognised as a panda objective");
        assertEquals(ObjectiveType.PANDA, objectivePool.getObjectiveType(pandaObjective), "The gardener objective we drawn is not recognised as a gardener objective");
        assertEquals(ObjectiveType.PATTERN, objectivePool.getObjectiveType(patternObjective), "The pattern objective we drawn is not recognised as a pattern objective");

        assertThrows(RuntimeException.class, () -> objectivePool.getObjectiveType(new PandaObjective(PandaObjective.Motif.ORIGINAL_GREEN)), "Pool testing an objective from outside without complaining");
    }
}
