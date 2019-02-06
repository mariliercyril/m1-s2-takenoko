package com.raccoon.takenoko.player;

import com.raccoon.takenoko.game.objective.ObjectivePool;
import com.raccoon.takenoko.game.objective.ObjectiveType;
import com.raccoon.takenoko.game.tiles.IrrigationState;
import com.raccoon.takenoko.game.tiles.Tile;
import com.raccoon.takenoko.game.tiles.Color;
import com.raccoon.takenoko.game.Game;
import com.raccoon.takenoko.game.objective.Objective;
import com.raccoon.takenoko.Takeyesntko;
import com.raccoon.takenoko.game.objective.PandaObjective;
import com.raccoon.takenoko.tool.Constants;
import com.raccoon.takenoko.tool.ForbiddenActionException;
import com.raccoon.takenoko.tool.UnitVector;

import java.awt.Point;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Class representig the player taking part in the game. To be extended by a bot to
 * actually perform a move when it's its turn to play.
 * Will provide all the attributes and methods common to all players.
 */
public abstract class Player {

    private int score;
    private int id;
    private List<Objective> objectives;
    private static int counter = 0;
    private Map<Color, Integer> stomach;
    private int irrigations;

    public Player() {
        score = 0;
        counter++;
        id = counter;
        objectives = new ArrayList<>();
        stomach = new EnumMap<>(Color.class);
        stomach.put(Color.GREEN, 0);
        stomach.put(Color.YELLOW, 0);
        stomach.put(Color.PINK, 0);
        irrigations = 0;
    }

    public int getScore() {
        return score;
    }

    public int getId() {
        return id;
    }

    public int getIrrigations() {
        return irrigations;
    }

    public List<Objective> getObjectives() {
        return objectives;
    }

    public void addObjective(Objective objective) {
        this.objectives.add(objective);
    }

    public Map<Color, Integer> getStomach() {
        return stomach;
    }

    /**
     * This method will be the one called by the game to give their turn to the players/
     * It will be calling the methods for the players to play their turn.
     * <p>
     * DESIGN PATTERN : TEMPLATE METHOD
     *
     * @param game the game in which the player is playing
     */
    public final void play(Game game) throws ForbiddenActionException {

        throwDice(game);

        // 1st step : ask bot to plan actions
        Action[] plannedActions = planActions(game);

        // check if the actions are compatible (exactly 2 costly actions)
        int validityCheck = 0;
        for (int i = 0; i < plannedActions.length; validityCheck += plannedActions[i++].getCost()) { ; }
        if (validityCheck != 2) {
            throw new ForbiddenActionException("Player tried to play an incorrect number of actions.");
        }
        Takeyesntko.print("Choosen actions : " + Arrays.toString(plannedActions));

        // step 2 : execute all actions
        for (Action a : plannedActions) {
            execute(a, game);
        }

        // step 3 : count points
        Takeyesntko.print("Player has played. Current score : " + getScore());
    }

    /**
     * BOT CAN'T ACCESS THIS METHOD
     * Used to enforce a honest behavior
     *
     * @param a    action to play
     * @param game current game
     */
    private void execute(Action a, Game game) throws ForbiddenActionException {
        Takeyesntko.print("PLAYING " + a);

        switch (a) {
            case PUT_DOWN_TILE:
                // refactorable : chooseTile can return a tile with the chosen position in it.
                Tile t = this.chooseTile(game);
                Point choice = this.whereToPutDownTile(game, t);
                t.setPosition(choice);
                game.putDownTile(t);
                break;
            case MOVE_GARDENER:
                List<Point> gardenerAccessible = game.getBoard().getAccessiblePositions(game.getGardener().getPosition());
                Point whereToMoveGardener = whereToMoveGardener(game, gardenerAccessible);  // Actually, we give the game in parameter… giving the list of accessible position is redundant
                // check that point is in available points array
                if (!gardenerAccessible.contains(whereToMoveGardener)) {
                    throw new ForbiddenActionException("Player tried to put the gardener in a non accessible position.");
                }
                game.getGardener().move(whereToMoveGardener);
                game.getObjectivePool().notifyBambooGrowth(game.getBoard());
                break;
            case DRAW_IRRIGATION:
                Takeyesntko.print("Player drew an irrigation");
                this.irrigations++;
                if (this.keepIrrigation()) {
                    Takeyesntko.print(String.format("Player chooses to keep it. He now has %d irrigations.", irrigations));
                    break;
                }
                if (!this.putDownIrrigation(game)) {
                    Takeyesntko.print(String.format("He can't put it down where he chooses to. He keeps it, he now has %d irrigations.", irrigations));
                    break;
                }
                Takeyesntko.print(String.format("Player has put down an irirgation ! He now has %d irrigations.", irrigations));
                break;
            case VALID_OBJECTIVE:
                Objective objective = this.chooseObjectiveToValidate();
                Takeyesntko.print(String.format("Player choosed to validate the objective %s !", objective));
                validateObjective(objective);
                // we may have emptied a stomach
                game.getObjectivePool().notifyStomachEmptied(this);
                break;
            case DRAW_OBJECTIVE:
                if (objectives.size() > Constants.MAX_AMOUNT_OF_OBJECTIVES) {    // We check if we are allowed to add an objective
                    throw new ForbiddenActionException("Player tried to draw an objective with a full hand already");
                }
                ObjectiveType type = this.whatTypeToDraw(game.getObjectivePool());
                objectives.add(game.drawObjective(type));
                Takeyesntko.print(String.format("Player has drawn a %s objective, he now has %d objectives in his hand", type, objectives.size()));
                break;
            case PUT_DOWN_IRRIGATION:
                if (this.putDownIrrigation(game)) {
                    Takeyesntko.print("Player put down an irrigation that he had in his stock ! He now have " + irrigations);
                }
                break;
            case MOVE_PANDA: // Works the same way as MOVE_GARDENER except it's a panda
                List<Point> pandaAccessible = game.getBoard().getAccessiblePositions(game.getPanda().getPosition());
                Point whereToMovePanda = whereToMovePanda(game, pandaAccessible);
                if (!pandaAccessible.contains(whereToMovePanda)) {
                    throw new ForbiddenActionException("Player tried to put the panda in a non accessible position.");
                }
                boolean destinationHadBamboo = game.getBoard().get(whereToMovePanda).getBambooSize() > 0; // Checks if there is bamboo on the destination tile
                game.getPanda().move(whereToMovePanda);

                if (destinationHadBamboo) {
                    eatBamboo(game.getBoard().get(game.getPanda().getPosition()).getColor()); // The panda eats a piece of bamboo on the tile where it lands
                    game.getObjectivePool().notifyBambooEaten(game.getBoard(), this);  // The overall bamboo situation has changed
                }
                break;
            default:
                Takeyesntko.print(a + " UNSUPPORTED");
        }
    }

    protected abstract ObjectiveType whatTypeToDraw(ObjectivePool pool);

    public abstract boolean keepIrrigation();

    private void validateObjective(Objective objective) {
        if (objective != null) {
            Takeyesntko.print("Player has completed an objective ! " + objective);
            this.objectives.remove(objective);
            this.score += objective.getScore();

            if (objective instanceof PandaObjective) {
                /* Be careful, the number here has to be changed when we'll have objective involving a
                   different amount of bamboos. This action could be managed by the objectives themselves
                   or by the Game maybe.
                 */
                Map<Color, Integer> pandaMotif = ( (PandaObjective) objective ).getMotifForCompleting();
                for (Color color : Color.values()) {
                    this.stomach.put(color, this.stomach.get(color) - pandaMotif.get(color));
                }
            }
        }
    }

    protected abstract boolean putDownIrrigation(Game game);

    protected abstract Action[] planActions(Game game);

    protected abstract Point whereToPutDownTile(Game game, Tile t);

    protected abstract Tile chooseTile(Game game);

    public static void reinitCounter() {
        counter = 0;
    }

    protected abstract Point whereToMoveGardener(Game game, List<Point> available);

    protected abstract Point whereToMovePanda(Game game, List<Point> available);

    protected void eatBamboo(Color color) {
        if (Objects.nonNull(color)) {
            stomach.put(color, stomach.get(color) + 1);
            Takeyesntko.print(String.format("Player has eaten a %s bamboo ! He now has %d %s bamboo(s) in his stomach", color, stomach.get(color), color));
        }
    }

    protected abstract Objective chooseObjectiveToValidate();

    public final boolean putDownIrrigation(Game game, Point pos, UnitVector direction) {
        if (irrigations > 0 && game.getBoard().get(pos).getIrrigationState(direction).equals(IrrigationState.IRRIGABLE)) {
            irrigations--;
            return game.getBoard().irrigate(pos, direction);
        }
        return false;
    }

    public void throwDice(Game game) throws ForbiddenActionException {
        Random rand = new Random();
        switch (rand.nextInt() % 6) {
            case 0:
                List<Tile> improvableTiles = game.getBoard().getAllTiles().stream().filter(t -> !t.isImproved()).collect(Collectors.toList());  // We get all improvable tiles
                if (!game.noMoreImprovements() && !improvableTiles.isEmpty()) {   // For now, if there are no more improvements to take, we throw the dice again
                    tileImprovement(game, improvableTiles);
                } else {
                    throwDice(game);
                }
                break;
            default:
                break;
        }
    }

    public abstract void tileImprovement(Game game, List<Tile> improvableTiles) throws ForbiddenActionException;

    public void giveEmperor() {
        score += 2;
    }
}
