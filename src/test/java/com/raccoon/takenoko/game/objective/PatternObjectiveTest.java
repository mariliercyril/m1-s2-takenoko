package com.raccoon.takenoko.game.objective;

import com.raccoon.takenoko.game.Board;
import com.raccoon.takenoko.game.Game;
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
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(JUnitPlatform.class)
@SpringBootTest
@ExtendWith(SpringExtension.class)
class PatternObjectiveTest {
    private Board b;
    private ArrayList<PatternObjective> objList;

    private PatternObjective pAligned;   // alignment of 3 tiles
    private PatternObjective pVShape; // V shaped pattern
    private PatternObjective pTriangle; // triangle shaped pattern
    private PatternObjective pDualColorZ; // dual color Zshaped pattern

    @BeforeEach
    void setUp(@Autowired Game game) {

        b = game.getBoard();
        b.set(new Point(1, 1), new Tile(Color.PINK));

        // I create all the PatternObjectives that I am going to test
        objList = new ArrayList<>();

        pAligned = new PatternObjective(new ArrayList<>(Arrays.asList(UnitVector.J, UnitVector.J)), Color.PINK, 2);
        pVShape = new PatternObjective(new ArrayList<>(Arrays.asList(UnitVector.J, UnitVector.I)), Color.YELLOW, 2);
        pTriangle = new PatternObjective(new ArrayList<>(Arrays.asList(UnitVector.J, UnitVector.N)), Color.GREEN, 2);
        pDualColorZ = new PatternObjective(new ArrayList<>(Arrays.asList(UnitVector.I, UnitVector.M, UnitVector.I)), new ArrayList<>(Arrays.asList( Color.GREEN, Color.GREEN, Color.PINK, Color.PINK)), 2);

        objList.addAll(Arrays.asList(pAligned, pVShape, pTriangle, pDualColorZ));
    }

    @Test
    void noTilesAllFalse() {
        assertFalse(pAligned.isCompleted(), "Pattern Aligned should not be completed yet as there are no tiles on the board.");
        assertFalse(pVShape.isCompleted(), "Pattern VShape should not be completed yet as there are no tiles on the board.");
        assertFalse(pTriangle.isCompleted(), "Pattern Triangle should not be completed yet as there are no tiles on the board.");
        assertFalse(pDualColorZ.isCompleted(), "Pattern DualColorZ should not be completed yet as there are no tiles on the board.");
    }

    @Test
    void notValidIfNotIrrigated() {
        this.placeAllTiles();

        pAligned.checkIfCompleted(b.get(new Point(1,1)), b); // we only check our basic tiles
        pVShape.checkIfCompleted(b.get(new Point(2,2)), b); // we only check our basic tiles
        pTriangle.checkIfCompleted(b.get(new Point(2,1)), b); // we only check our basic tiles
        pDualColorZ.checkIfCompleted(b.get(new Point(-1, -1)), b); // we only check our basic tiles

        assertFalse(pAligned.isCompleted(), "Pattern Aligned should not be completed yet as none of the tiles are irrigated");
        assertFalse(pVShape.isCompleted(), "Pattern VShape should not be completed yet as none of the tiles are irrigated");
        assertFalse(pTriangle.isCompleted(), "Pattern Triangle should not be completed yet as none of the tiles are irrigated");
        assertFalse(pDualColorZ.isCompleted(), "Pattern DualColorZ should not be completed yet as none of the tiles are irrigated");
    }

    @Test
    void validIfIrrigated() {
        this.placeAllTiles();
        this.irrigateAllTiles();

        pAligned.checkIfCompleted(b.get(new Point(1,1)), b); // we only check our basic tiles
        pVShape.checkIfCompleted(b.get(new Point(2,2)), b); // we only check our basic tiles
        pTriangle.checkIfCompleted(b.get(new Point(2,1)), b); // we only check our basic tiles
        pDualColorZ.checkIfCompleted(b.get(new Point(-1, -1)), b); // we only check our basic tiles


        assertTrue(pAligned.isCompleted(), "Pattern Aligned should be completed by now.");
        assertTrue(pVShape.isCompleted(), "Pattern VShape should be completed by now.");
        assertTrue(pTriangle.isCompleted(), "Pattern Triangle should be completed by now.");
        assertTrue(pDualColorZ.isCompleted(), "Pattern DualColorZ should be completed by now.");
    }


    // util
    private void placeAllTiles() {
        // caution : as the rules apply, we must place the tiles in the right order
        putDownAndNotify(new Point(1, 0), Color.PINK);    // part of pAligned
        putDownAndNotify(new Point(0, -1), Color.GREEN);  // part of pDualColorZ
        putDownAndNotify(new Point(1, -1), Color.PINK);   // part of pAligned
        putDownAndNotify(new Point(-1, -1), Color.GREEN); // part of PDualColorZ
        putDownAndNotify(new Point(2, 1), Color.GREEN);   // part of pTriangle
        putDownAndNotify(new Point(-1, -2), Color.PINK);  // part of pDualColorZ
        putDownAndNotify(new Point(0, -2), Color.PINK);   // part of pDualColorZ
        putDownAndNotify(new Point(2, 2), Color.YELLOW);  // part of pVShape
        putDownAndNotify(new Point(3, 2), Color.GREEN);   // part of pTriangle
        putDownAndNotify(new Point(3, 1), Color.GREEN);   // part of pTriangle
        putDownAndNotify(new Point(3, 3), Color.YELLOW);  // part of pVShape
        putDownAndNotify(new Point(4, 3), Color.YELLOW);  // part of pVShape
    }

    private void irrigateAllTiles() {
        irrigateAndNotify(new Point(1, 0), UnitVector.K);
        irrigateAndNotify(new Point(1, 0), UnitVector.M);
        irrigateAndNotify(new Point(1, 0), UnitVector.N);
        irrigateAndNotify(new Point(1, 1), UnitVector.I);
        irrigateAndNotify(new Point(2, 2), UnitVector.N);
        irrigateAndNotify(new Point(2, 2), UnitVector.I);
        irrigateAndNotify(new Point(3, 2), UnitVector.K);
        irrigateAndNotify(new Point(3, 2), UnitVector.J);
        irrigateAndNotify(new Point(3, 2), UnitVector.M);
        irrigateAndNotify(new Point(3, 2), UnitVector.N);
        irrigateAndNotify(new Point(0, -1), UnitVector.L);
        irrigateAndNotify(new Point(0, -1), UnitVector.M);
        irrigateAndNotify(new Point(0, -1), UnitVector.N);
    }

    private void putDownAndNotify(Point pos, Color color) {
        b.set(pos, new Tile(color));
        for (PatternObjective p : objList) {
            p.checkIfCompleted(b.get(pos), b);
        }
    }

    private void irrigateAndNotify(Point pos, UnitVector dir) {
        if (b.irrigate(pos, dir)) {
            for (PatternObjective p : objList) {
                p.checkIfCompleted(b.get(pos), b);
            }
            for (PatternObjective p : objList) {
                p.checkIfCompleted(b.get(dir.getVector().applyTo(pos)), b);
            }

        }
    }
}
