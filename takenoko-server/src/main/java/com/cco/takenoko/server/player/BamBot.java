package com.cco.takenoko.server.player;

import java.awt.Point;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

import com.cco.takenoko.server.game.Game;

import com.cco.takenoko.server.game.objective.Objective;
import com.cco.takenoko.server.game.objective.ObjectivePool;
import com.cco.takenoko.server.game.objective.ObjectiveType;
import com.cco.takenoko.server.game.objective.PandaObjective;

import com.cco.takenoko.server.game.tiles.Color;
import com.cco.takenoko.server.game.tiles.ImprovementType;
import com.cco.takenoko.server.game.tiles.Tile;

import com.cco.takenoko.server.tool.Constants;
import com.cco.takenoko.server.tool.ForbiddenActionException;
import com.cco.takenoko.server.tool.Tools;
import com.cco.takenoko.server.tool.UnitVector;

public class BamBot extends Player {

	private Point lastPlacedTile;

	public BamBot(Integer id) {

		super(id);
	}

	public Point getLastPlacedTile() {

		return lastPlacedTile;
	}

	@Override
	public boolean keepIrrigation() {

		return new Random().nextBoolean();
	}

	/**
	 * is random for now but should be overriden in child classes according to the bot's strategies
	 */
	@Override
	public void tileImprovement(Game game, List<Tile> improvableTiles) throws ForbiddenActionException {

		// For safety
		if (game.noMoreImprovements()) {
			return;
		}
		List<ImprovementType> availableImprovements = new ArrayList<>();
		// We get all the different improvements still available
		for (ImprovementType imp : ImprovementType.values()) {
			if (game.isImprovementAvailable(imp)) {
				availableImprovements.add(imp);
			}
		}
		Collections.shuffle(improvableTiles);
		Collections.shuffle(availableImprovements);
		ImprovementType improvement = game.takeImprovement(availableImprovements.get(0));
		improvement.improve(improvableTiles.get(0));
	}

	/**
	 * Puts down a tile at a random available location on the board given by the game
	 *
	 * @param game the game in which the player is playing
	 */
	@Override
	public Point whereToPutDownTile(Game game, Tile t) {

		Map<Point, Integer> possibleBambooGrowth = new HashMap<>();

		// Checking the possible outcomes of placing the tile
		for (Point available : game.getBoard().getAvailablePositions()) {
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

	// TODO: Improve the quick fix "Replace 'protected' by 'public'".
	@Override
	public Tile chooseTile(Game game) {

		List<Tile> tiles = game.getTiles();
		Map<Tile, Point> bestMoves = new HashMap<>();

		// Figuring out the best move for each tile
		for (Tile t : tiles) {
			bestMoves.put(t, whereToPutDownTile(game, t));
		}

		// Choosing the tile that gives the most bamboo growth
		Map<Tile, Integer> tileGrowth = new HashMap<>();
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

		// Searching for the place where the gardener can grow the most bamboos
		for (Point p : available) {
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

		// The player chooses to eat the bamboo pieces he lacks the most
		int minValue = getStomach().get(Color.PINK);
		Color minColor = Color.PINK;

		for (Color c : Color.values()) {
			if (getStomach().get(c) < minValue) {
				minValue = getStomach().get(c);
				minColor = c;
			}
		}

		// Looks for the first tile of the needed color
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

		// Rule to move the panda
		if (isMovingPandaUseful(game) && needToEatBamboo()) {
			actionSet[1] = Action.MOVE_PANDA;
		}

		return actionSet;
	}

	// Tells if there is any bamboo to be eaten
	private boolean isMovingPandaUseful(Game game) {

		for (Point p : game.getBoard().getAccessiblePositions(game.getPanda().getPosition())) {
			if (game.getBoard().get(p).getBambooSize() > 0) {
				return true;
			}
		}

		return false;
	}

	/**
	 * The player wants to always have three bamboo of each color
	 * 
	 * @return
	 */
	private boolean needToEatBamboo() {

		for (Color c : Color.values()) {
			if (getStomach().get(c) < 3) {
				return true;
			}
		}

		return false;
	}

	/**
	 * The player validates the bamboo objectives first
	 * For now, he doesn't try to validate the highest-scoring objective
	 */
	@Override
	protected Objective chooseObjectiveToValidate() {

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

		if (!pool.isDeckEmpty(ObjectiveType.PANDA)) {
			return ObjectiveType.PANDA;
		}

		List<ObjectiveType> types = new ArrayList<>(Arrays.asList(ObjectiveType.values()));
		types.removeIf(pool::isDeckEmpty);
		Collections.shuffle(types);

		return types.get(0);
	}

	@Override
	protected boolean putDownIrrigation(Game game) {

		boolean successfulIrrigation = false;
		// If a tile has been placed since the last time we irrigated
		if (Objects.nonNull(this.lastPlacedTile)) {
			UnitVector[] directionsTable = UnitVector.values();
			// We try to irrigate the tile
			for (int i = 0; !successfulIrrigation && (i < directionsTable.length); i++) {
				successfulIrrigation = putDownIrrigation(game, this.lastPlacedTile, directionsTable[i]);
			}
		}

		if (successfulIrrigation) {
			this.lastPlacedTile = null;
		}

		return successfulIrrigation;
	}

}
