package com.raccoon.takenoko.game.objective;

import com.raccoon.takenoko.game.Board;
import com.raccoon.takenoko.game.tiles.Color;
import com.raccoon.takenoko.game.Game;
import com.raccoon.takenoko.game.tiles.Tile;
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
class GardenerObjectiveTest {

    private Board b;
    private Objective go1;

    @BeforeEach
    void setup(@Autowired Game game) {

        this.b = game.getBoard();

        b.set(new Point(1, 1), new Tile(Color.PINK));
        b.set(new Point(0, 1), new Tile(Color.YELLOW));
        b.set(new Point(1, 0), new Tile(Color.GREEN));

        go1 = new GardenerObjective(3, Color.GREEN);
    }

    @Test
    void isntCompletedIfNoBambooGrew() {
        go1.checkIfCompleted(b);
        assertFalse(go1.isCompleted(), "Gardener objective is completed even if number of chunks is wrong");
    }

    @Test
    void isCompletedIfThereAreEnoughBamboos() {
        Tile t = b.get(new Point(1, 0));
        t.increaseBambooSize();
        t.increaseBambooSize();
        go1.checkIfCompleted(b);
        assertTrue(go1.isCompleted(), "Gardener objective isn't completed even though there are enough bamboos of the right color on the board");

    }
}
