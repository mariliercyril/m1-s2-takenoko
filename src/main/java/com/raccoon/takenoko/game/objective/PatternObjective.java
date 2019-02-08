package com.raccoon.takenoko.game.objective;

import com.raccoon.takenoko.game.Board;
import com.raccoon.takenoko.game.tiles.Color;
import com.raccoon.takenoko.game.tiles.Tile;
import com.raccoon.takenoko.tool.UnitVector;

import java.awt.Point;
import java.util.*;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class PatternObjective extends Objective {

    private final List<Color> colors;
    private final List<UnitVector> vectors;
    private Color color;

    /**
     * Caution : the colors list needs to have 1 more element than the vectors.
     * The colors are the order of the colors we check
     * The vectors are their positions relatively to the tile before
     *  @param vectors the list of vectors starting from the previous tile
     * @param colors  the list of colors in the right order
     */
    public PatternObjective(List<UnitVector> vectors, List<Color> colors, int score) {
        super();
        this.color = colors.remove(0);
        this.colors = colors;
        this.vectors = vectors;
        this.score = score;
    }

    public PatternObjective(List<UnitVector> vectors, Color color, int score) {
        super();
        this.color = color;
        this.colors = new ArrayList<>(Collections.nCopies(vectors.size(), color));
        this.vectors = vectors;
        this.score = score;
    }

    @Override
    public void checkIfCompleted(Tile tile, Board board) {

        // as tiles can't move on the board, pattern objectives don't get invalidated
        // if the tile we check is not the start of our pattern (not the right color or not irrigated) we don't need to do the rest
        if (this.isCompleted || Objects.isNull(tile) || tile.getColor() != this.color || !tile.isIrrigated()) {
            return;
        }

        // for all directions
        for (int rotation = 0; rotation < UnitVector.values().length; rotation++) {
            // we allocate a temporary vector set
            UnitVector[] currentVectorSet = new UnitVector[vectors.size()];
            // we start by turning all of the vectors of our path
            for (int i = 0; i < vectors.size(); i++) {
                currentVectorSet[i] = vectors.get(i).rotation(rotation);
            }

            // then we check that the path is all the right colors
            // this is where it is mandatory that our arrays have the right length
            Point currentRelativePosition = tile.getPosition();
            int i;
            int stepsCompleted = 1;
            for (i = 0; i < currentVectorSet.length; i++) {
                UnitVector v = currentVectorSet[i];
                // if the position we check doesn't have the right color or if it is not irrigated
                currentRelativePosition = v.getVector().applyTo(currentRelativePosition);
                Tile currentCheckingTile = board.get(currentRelativePosition);
                if (Objects.nonNull(currentCheckingTile)
                        && currentCheckingTile.isIrrigated()
                        && currentCheckingTile.getColor() == colors.get(i)) {
                    stepsCompleted++;
                } else {
                    break;
                }
            }
            isCompleted = stepsCompleted == vectors.size() + 1;
            // if we are completed, we stop trying to look
            if (isCompleted) { break; }
        }

    }
}
