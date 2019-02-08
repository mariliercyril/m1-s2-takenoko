package com.raccoon.takenoko.player;

import com.raccoon.takenoko.game.objective.ObjectivePool;
import com.raccoon.takenoko.game.objective.ObjectiveType;
import com.raccoon.takenoko.game.tiles.Tile;
import com.raccoon.takenoko.game.Game;
import com.raccoon.takenoko.game.objective.Objective;
import com.raccoon.takenoko.game.objective.PandaObjective;
import com.raccoon.takenoko.tool.Constants;

import java.awt.*;

import com.raccoon.takenoko.game.tiles.Color;
import com.raccoon.takenoko.tool.Tools;
import com.raccoon.takenoko.tool.UnitVector;

import java.util.*;
import java.util.List;

public class BamBot extends RandomBot {

    private Point lastPlacedTile;

    @Override
    protected Point whereToPutDownTile(Game game, Tile t) {
        Map<Point, Integer> possibleBambooGrowth = new HashMap<>();

        for (Point available : game.getBoard().getAvailablePositions()) {    // Checking the possible outcomes of placing the tile
            possibleBambooGrowth.put(available, 0);
            for (Tile adjacent : game.getBoard().getNeighbours(available)) {
                if (t.getColor() == adjacent.getColor() && adjacent.isIrrigated() && adjacent.getBambooSize() < 4) {
                    possibleBambooGrowth.put(available, possibleBambooGrowth.get(available) + 1);
                }
            }
        }
        this.lastPlacedTile = Tools.mapMaxKey(possibleBambooGrowth);
        return lastPlacedTile;
    }

    @Override
    protected Tile chooseTile(Game game) {
        List<Tile> tiles = game.getTiles();
        Map<Tile, Point> bestMoves = new HashMap<>();

        for (Tile t : tiles) {  //  Figuring out the best move for each tile
            bestMoves.put(t, whereToPutDownTile(game, t));
        }

        Map<Tile, Integer> tileGrowth = new HashMap<>();  // Choosing the tile that gives the most bamboo growth
        for (Tile t : tiles) {
            tileGrowth.put(t, 0);
            for (Tile adjacent : game.getBoard().getNeighbours(bestMoves.get(t))) {
                if (t.getColor() == adjacent.getColor() && adjacent.isIrrigated() && adjacent.getBambooSize() < 4) {
                    tileGrowth.put(t, tileGrowth.get(t) + 1);
                }
            }
        }

        Tile choice = Tools.mapMaxKey(tileGrowth);
        for (Tile t : tiles) {
            if (t != choice) {
                game.putBackTile(t);
            }
        }

        return choice;
    }

    @Override
    protected Point whereToMoveGardener(Game game, List<Point> available) {
        Map<Point, Integer> outcomes = new HashMap<>();
        Tile destination;

        for (Point p : available) { // Searching for the place where the gardener can grow the most bamboos
            outcomes.put(p, 0);
            destination = game.getBoard().get(p);
            for (Tile adjacent : game.getBoard().getNeighbours(p)) {
                if (adjacent.getColor() == destination.getColor() && adjacent.isIrrigated() && adjacent.getBambooSize() < 4) {
                    outcomes.put(p, outcomes.get(p) + 1);
                }
            }
        }

        return Tools.mapMaxKey(outcomes);
    }

    @Override
    protected Point whereToMovePanda(Game game, List<Point> available) {
        //  The player choses to eat the bamboo pieces he lacks the most
        int minValue = getStomach().get(Color.PINK);
        Color minColor = Color.PINK;

        for (Color c : Color.values()) {
            if (getStomach().get(c) < minValue) {
                minValue = getStomach().get(c);
                minColor = c;
            }
        }

        //  Looks for the first tile of the needed color

        for (Point p : available) {
            if (game.getBoard().get(p).getColor() == minColor && game.getBoard().get(p).getBambooSize() > 0) {
                return p;
            }
        }

        return available.get(0);
    }

    @Override
    protected Action[] planActions(Game game) {
        Action[] actionSet = new Action[]{Action.PUT_DOWN_TILE, Action.MOVE_GARDENER, Action.VALID_OBJECTIVE};  // Safety action set

        if (game.getBoard().getAllTiles().size() == 1) {
            return actionSet;
        }

        if (!game.getObjectivePool().isDeckEmpty(ObjectiveType.PANDA) && getObjectives().size() < Constants.MAX_AMOUNT_OF_OBJECTIVES) {   //  Rule to pick a new objective
            actionSet[0] = Action.DRAW_OBJECTIVE;
        }

        if (isMovingPandaUseful(game) && needToEatBamboo()) { // Rule to move the panda
            actionSet[1] = Action.MOVE_PANDA;
        }

        return actionSet;
    }

    private boolean isMovingPandaUseful(Game game) {    //  Tells if there is any bamboo to be eaten
        for (Point p : game.getBoard().getAccessiblePositions(game.getPanda().getPosition())) {
            if (game.getBoard().get(p).getBambooSize() > 0) {
                return true;
            }
        }
        return false;
    }

    private boolean needToEatBamboo() { //  The player wants to always have three bamboo of each color
        for (Color c : Color.values()) {
            if (getStomach().get(c) < 3) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected Objective chooseObjectiveToValidate() {   // The player validates the bamboo objectives first
        // For now, he doesn't try to validate the highest-scoring objective

        List<Objective> completedObjectives = new ArrayList<>();
        for (Objective obj : getObjectives()) {
            obj.checkIfCompleted(this);
            if (obj.isCompleted()) {
                completedObjectives.add(obj);
            }
        }

        if (!completedObjectives.isEmpty()) {
            for (Objective completed : completedObjectives) {
                if (completed instanceof PandaObjective) {
                    return completed;
                }
            }
            return completedObjectives.get(0);
        }

        return null;
    }

    @Override
    protected ObjectiveType whatTypeToDraw(ObjectivePool pool) {
        if (!pool.isDeckEmpty(ObjectiveType.PANDA)) { return ObjectiveType.PANDA; }
        return super.whatTypeToDraw(pool);
    }

    @Override
    protected boolean putDownIrrigation(Game game) {
        boolean successfulIrrigation = false;
        if (Objects.nonNull(this.lastPlacedTile)) { //  If a tile has been placed since the last time we irrigated
            UnitVector[] directionsTable = UnitVector.values();
            for (int i = 0; !successfulIrrigation && (i < directionsTable.length); i++) {   // We try to irrigate the tile
                successfulIrrigation = putDownIrrigation(game, this.lastPlacedTile, directionsTable[i]);
            }
        }

        if (successfulIrrigation) {
            this.lastPlacedTile = null;
        }

        return successfulIrrigation;
    }
}
