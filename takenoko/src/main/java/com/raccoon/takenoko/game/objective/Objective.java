package com.raccoon.takenoko.game.objective;

import com.raccoon.takenoko.game.tiles.Color;
import com.raccoon.takenoko.game.tiles.Tile;
import com.raccoon.takenoko.game.Board;

import com.raccoon.takenoko.player.Player;

import java.util.Map;

/**
 * This class provides a skeletal implementation for the <b>objectives</b> of the game
 * (<i>parcel</i> objectives, <i>gardener</i> objectives, <i>panda</i> objectives)
 * to minimize the effort required to do this implementation.
 *
 * <p>To implement an objective, the programmer needs only to extend this class
 * and provide a redefinition for one of the versions of the {@code checkIfCompleted} (overloaded) method.</p>
 */
public abstract class Objective {

    private static final String RESULT_FORMAT = "%s{isCompleted=%B, score=%d}";

    protected boolean isCompleted;
    protected int score;

    /**
     * Sole constructor. (For invocation by subclass constructors.)
     */
    public Objective() {

        isCompleted = false;
    }

    /**
     * Returns the completed state of the objective.
     *
     * @return true if the objective has been completed, otherwise false
     */
    public boolean isCompleted() {

        return isCompleted;
    }

    /**
     * Checks whether the objective is completed from a player.
     * (This version is typically redefined by an {@code Objective} class for <i>panda</i>.)
     *
     * @param player the player in which the bamboo chunks are looked for
     */
    public void checkIfCompleted(Player player) {

        throw new UnsupportedOperationException();
    }

    /**
     * Checks whether the objective is completed from a tile and a board.
     * (This version is typically redefined by an {@code Objective} class for <i>parcel</i>.)
     *
     * @param tile  the tile from which the checking is performed
     * @param board the game board, on which the pattern is looked for
     */
    public void checkIfCompleted(Tile tile, Board board) {

        throw new UnsupportedOperationException();
    }

    /**
     *Checks wether an objective is completed from a board
     * This method is to be overridden for gardener objectives
     *
     * @param board the game board, on which we look for bamboo growth
     */
    public void checkIfCompleted(Board board) {

        throw new UnsupportedOperationException();
    }

    /**
     * Returns the score which we should have if the objective were completed.
     *
     * @return the score which we should have if the objective were completed
     */
    public int getScore() {

        return score;
    }


    /**
     * Gets the expected pattern for completing the PandaObjective in question.
     *
     * @return pattern
     * 	the expected pattern depending on colors
     */
    public Map<Color, Integer> getPatternForCompleting() {

        throw new UnsupportedOperationException();
    }

    /**
     * Returns a string representation of the objective.
     * Here, the {@code toString} method returns a string that represents this object with its <i>type</i>
     * and, between curly brackets, its <i>completed state</i>, the <i>score</i> and the <i>color which is expected</i>;
     * for example:
     * <blockquote><b>AlignmentParcelObjective{isCompleted=TRUE, score=2, color=GREEN}</b></blockquote>
     *
     * @return a string representation of the objective with its <i>type</i>, followed by its <i>completed state</i>,
     * the <i>score</i> and the <i>color which is expected</i>
     */
    @Override
    public String toString() {

        return String.format(RESULT_FORMAT, ( this.getClass() ).getSimpleName(), isCompleted, score);
    }

}
