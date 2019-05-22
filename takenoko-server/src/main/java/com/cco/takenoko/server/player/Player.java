package com.cco.takenoko.server.player;

import java.awt.Point;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

import java.util.stream.Collectors;

import com.cco.takenoko.server.TakenokoServer;

import com.cco.takenoko.server.game.Game;

import com.cco.takenoko.server.game.objective.Objective;
import com.cco.takenoko.server.game.objective.ObjectivePool;
import com.cco.takenoko.server.game.objective.ObjectiveType;
import com.cco.takenoko.server.game.objective.PandaObjective;

import com.cco.takenoko.server.game.tiles.*;

import com.cco.takenoko.server.tool.Constants;
import com.cco.takenoko.server.tool.ForbiddenActionException;
import com.cco.takenoko.server.tool.Tools;
import com.cco.takenoko.server.tool.UnitVector;

public class Player {

	private Integer id;

	private int score;
	private List<Objective> objectives;
	private Map<Color, Integer> stomach;
	private int irrigations;

	private Point lastPlacedTile;

	private static List<Player> players = new ArrayList<>();

	public Player(Integer id) {

		this.id = id;

		initialize();

		players.add(this);
	}

	public int getId() {

		return id;
	}

	public void initialize() {

		score = 0;
		objectives = new ArrayList<>();
		stomach = new EnumMap<>(Color.class);
		for (Color color : Color.values()) {
			stomach.put(color, 0);
		}
		irrigations = 0;
	}

	public int getScore() {

		return score;
	}

	public List<Objective> getObjectives() {

		return objectives;
	}

	public void addObjective(Objective objective) {

		objectives.add(objective);
	}

	public Map<Color, Integer> getStomach() {

		return stomach;
	}

	public int getIrrigations() {

		return irrigations;
	}

	public Point getLastPlacedTile() {

		return lastPlacedTile;
	}

	public static List<Player> getPlayers() {

		return players;
	}

	/**
	 * This method will be the one called by the game to give their turn to the players.
	 * It will be calling the methods for the players to play their turn.
	 * <p>
	 * DESIGN PATTERN: TEMPLATE METHOD
	 * 
	 * @param game the game in which the player is playing
	 */
	// TODO: This method should be the method, with the method "execute()", from which we will develop the services (issue 27)...
	public final void play(Game game) throws ForbiddenActionException {

		this.throwDice(game);

		// 1st step: ask player to plan actions
		Action[] plannedActions = this.planActions(game);

		// Checks whether the actions are compatible (exactly 2 costly actions)
		int validityCheck = 0;
		for (int i = 0; i < plannedActions.length; validityCheck += plannedActions[i++].getCost()) {}
		if (validityCheck != 2) {
			throw new ForbiddenActionException("Player tried to play an incorrect number of actions.");
		}
		TakenokoServer.print("Choosen actions: " + Arrays.toString(plannedActions));

		// 2nd step: execute all actions
		for (Action action : plannedActions) {
			this.execute(action, game);
		}

		// 3rd step: count points
		TakenokoServer.print("Player has played. Current score: " + getScore());
	}

	private void throwDice(Game game) throws ForbiddenActionException {

		Random rand = new Random();
		switch (rand.nextInt() % 6) {
			case 0:
				// We get all improvable tiles
				List<Tile> improvableTiles = game.getBoard().getAllTiles().stream().filter(t -> !t.isImproved()).collect(Collectors.toList());
				// For now, if there are no more improvements to take, we throw the dice again
				if (!game.noMoreImprovements() && !improvableTiles.isEmpty()) {
					this.tileImprovement(game, improvableTiles);
				} else {
					this.throwDice(game);
				}
				break;
			default:
				break;
		}
	}

	private Action[] planActions(Game game) {

		// Safety action set
		Action[] actionSet = new Action[]{Action.PUT_DOWN_TILE, Action.MOVE_GARDENER, Action.VALID_OBJECTIVE};

		if (game.getBoard().getAllTiles().size() == 1) {
			return actionSet;
		}

		// Rule to pick a new objective
		if (!game.getObjectivePool().isDeckEmpty(ObjectiveType.PANDA) && this.getObjectives().size() < Constants.MAX_AMOUNT_OF_OBJECTIVES) {
			actionSet[0] = Action.DRAW_OBJECTIVE;
		}

		// Rule to move the panda
		if (this.isMovingPandaUseful(game) && this.needToEatBamboo()) {
			actionSet[1] = Action.MOVE_PANDA;
		}

		return actionSet;
	}

	/**
	 * Although the client does not have access to the methods of the game, this method is still private.
	 *
	 * @param a    action to play
	 * @param game current game
	 */
	// TODO: This method should allow us to develop the services (issue 27)...
	private void execute(Action action, Game game) throws ForbiddenActionException {

		TakenokoServer.print("PLAYING " + action);

		switch (action) {
			case PUT_DOWN_TILE:
				// Refactorable: the method "chooseTile()" can return a tile with the chosen position in it.
				Tile tile = this.chooseTile(game);
				Point choice = this.whereToPutDownTile(game, tile);
				tile.setPosition(choice);
				game.putDownTile(tile);
				break;
			case MOVE_GARDENER:
				List<Point> gardenerAccessible = game.getBoard().getAccessiblePositions(game.getGardener().getPosition());
				// Actually, we give the game in parameter...: giving the list of the accessible positions is redundant.
				Point whereToMoveGardener = this.whereToMoveGardener(game, gardenerAccessible);
				// Checks that point is in available points array
				if (!gardenerAccessible.contains(whereToMoveGardener)) {
					throw new ForbiddenActionException("Player tried to put the gardener in a non accessible position.");
				}
				game.getGardener().move(whereToMoveGardener);
				game.getObjectivePool().notifyBambooGrowth(game.getBoard());
				break;
			case DRAW_IRRIGATION:
				TakenokoServer.print("Player drew an irrigation.");
				this.irrigations++;
				if (this.keepIrrigation()) {
					TakenokoServer.print(String.format("Player chooses to keep it. He now has %d irrigations.", irrigations));
					break;
				}
				if (!this.putDownIrrigation(game)) {
					TakenokoServer.print(String.format("He can't put it down where he chooses to. He keeps it, he now has %d irrigations.", irrigations));
					break;
				}
				TakenokoServer.print(String.format("Player has put down an irirgation ! He now has %d irrigations.", irrigations));
				break;
			case VALID_OBJECTIVE:
				Objective objective = this.chooseObjectiveToValidate();
				TakenokoServer.print(String.format("Player choosed to validate the objective %s!", objective));
				this.validateObjective(objective);
				// We may have emptied a stomach
				game.getObjectivePool().notifyStomachEmptied(this);
				break;
			case DRAW_OBJECTIVE:
				// We check whether we are allowed to add an objective
				if (objectives.size() > Constants.MAX_AMOUNT_OF_OBJECTIVES) {
					throw new ForbiddenActionException("Player tried to draw an objective with a full hand already.");
				}
				ObjectiveType type = this.whatTypeToDraw(game.getObjectivePool());
				this.objectives.add(game.drawObjective(type));
				TakenokoServer.print(String.format("Player has drawn a %s objective, he now has %d objectives in his hand.", type, this.objectives.size()));
				break;
			case PUT_DOWN_IRRIGATION:
				if (this.putDownIrrigation(game)) {
					TakenokoServer.print(String.format("Player put down an irrigation that he had in his stock! He now have %s.", irrigations));
				}
				break;
				// Works the same way as MOVE_GARDENER except it's a panda
			case MOVE_PANDA:
				List<Point> pandaAccessible = game.getBoard().getAccessiblePositions(game.getPanda().getPosition());
				Point whereToMovePanda = this.whereToMovePanda(game, pandaAccessible);
				if (!pandaAccessible.contains(whereToMovePanda)) {
					throw new ForbiddenActionException("Player tried to put the panda in a non accessible position.");
				}
				// Checks whether there is bamboo on the destination tile
				boolean destinationHadBamboo = game.getBoard().get(whereToMovePanda).getBambooSize() > 0;
				game.getPanda().move(whereToMovePanda);

				if (destinationHadBamboo) {
					// The panda eats a piece of bamboo on the tile where it lands
					this.eatBamboo(game.getBoard().get(game.getPanda().getPosition()).getColor());
					// The overall bamboo situation has changed
					game.getObjectivePool().notifyBambooEaten(game.getBoard(), this);
				}
				break;
			default:
				TakenokoServer.print(action + " UNSUPPORTED");
		}
	}

	/**
	 * Is random for now but should be overriden in child classes according to the player's strategies.
	 */
	private void tileImprovement(Game game, List<Tile> improvableTiles) throws ForbiddenActionException {

		// For safety
		if (game.noMoreImprovements()) {
			return;
		}
		List<ImprovementType> availableImprovements = new ArrayList<>();
		// We get all the different improvements still available
		for (ImprovementType improvementType : ImprovementType.values()) {
			if (game.isImprovementAvailable(improvementType)) {
				availableImprovements.add(improvementType);
			}
		}
		Collections.shuffle(improvableTiles);
		Collections.shuffle(availableImprovements);
		ImprovementType improvement = game.takeImprovement(availableImprovements.get(0));
		improvement.improve(improvableTiles.get(0));
	}

	/**
	 * Tells if there is any bamboo to be eaten.
	 */
	private boolean isMovingPandaUseful(Game game) {

		for (Point point : game.getBoard().getAccessiblePositions(game.getPanda().getPosition())) {
			if (game.getBoard().get(point).getBambooSize() > 0) {
				return true;
			}
		}

		return false;
	}

	/**
	 * The player wants to always have three bamboo of each color.
	 */
	private boolean needToEatBamboo() {

		for (Color color : Color.values()) {
			if (this.getStomach().get(color) < 3) {
				return true;
			}
		}

		return false;
	}

	private Tile chooseTile(Game game) {

		List<Tile> tiles = game.getTiles();
		Map<Tile, Point> bestMoves = new HashMap<>();

		// Figuring out the best move for each tile
		for (Tile tile : tiles) {
			bestMoves.put(tile, this.whereToPutDownTile(game, tile));
		}

		// Choosing the tile that gives the most bamboo growth
		Map<Tile, Integer> tileGrowth = new HashMap<>();
		for (Tile tile : tiles) {
			tileGrowth.put(tile, 0);
			for (Tile adjacent : game.getBoard().getNeighbours(bestMoves.get(tile))) {
				if (tile.getColor() == adjacent.getColor() && adjacent.isIrrigated() && adjacent.getBambooSize() < 4) {
					tileGrowth.put(tile, tileGrowth.get(tile) + 1);
				}
			}
		}

		Tile choice = Tools.mapMaxKey(tileGrowth);
		for (Tile tile : tiles) {
			if (tile != choice) {
				game.putBackTile(tile);
			}
		}

		return choice;
	}

	/**
	 * Puts down a tile at a random available location on the board given by the game.
	 *
	 * @param game the game in which the player is playing
	 */
	public Point whereToPutDownTile(Game game, Tile tile) {

		Map<Point, Integer> possibleBambooGrowth = new HashMap<>();

		// Checking the possible outcomes of placing the tile
		for (Point available : game.getBoard().getAvailablePositions()) {
			possibleBambooGrowth.put(available, 0);
			for (Tile adjacent : game.getBoard().getNeighbours(available)) {
				if (tile.getColor() == adjacent.getColor() && adjacent.isIrrigated() && adjacent.getBambooSize() < 4) {
					possibleBambooGrowth.put(available, possibleBambooGrowth.get(available) + 1);
				}
			}
		}

		this.lastPlacedTile = Tools.mapMaxKey(possibleBambooGrowth);

		return lastPlacedTile;
	}

	public Point whereToMoveGardener(Game game, List<Point> available) {

		Map<Point, Integer> outcomes = new HashMap<>();
		Tile destination;

		// Searching for the place where the gardener can grow the most bamboos
		for (Point point : available) {
			outcomes.put(point, 0);
			destination = game.getBoard().get(point);
			for (Tile adjacent : game.getBoard().getNeighbours(point)) {
				if (adjacent.getColor() == destination.getColor() && adjacent.isIrrigated() && adjacent.getBambooSize() < 4) {
					outcomes.put(point, outcomes.get(point) + 1);
				}
			}
		}

		return Tools.mapMaxKey(outcomes);
	}

	private boolean keepIrrigation() {

		return new Random().nextBoolean();
	}

	private boolean putDownIrrigation(Game game) {

		boolean successfulIrrigation = false;
		// If a tile has been placed since the last time we irrigated
		if (Objects.nonNull(this.lastPlacedTile)) {
			UnitVector[] directionsTable = UnitVector.values();
			// We try to irrigate the tile
			for (int i = 0; !successfulIrrigation && (i < directionsTable.length); i++) {
				successfulIrrigation = this.putDownIrrigation(game, this.lastPlacedTile, directionsTable[i]);
			}
		}

		if (successfulIrrigation) {
			this.lastPlacedTile = null;
		}

		return successfulIrrigation;
	}

	/**
	 * The player validates the bamboo objectives first.
	 * For now, he doesn't try to validate the highest-scoring objective.
	 */
	public Objective chooseObjectiveToValidate() {

		List<Objective> completedObjectives = new ArrayList<>();
		for (Objective objective : this.getObjectives()) {
			objective.checkIfCompleted(this);
			if (objective.isCompleted()) {
				completedObjectives.add(objective);
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

	private void validateObjective(Objective objective) {

		if (objective != null) {
			TakenokoServer.print("Player has completed an objective! " + objective);
			this.objectives.remove(objective);
			this.score += objective.getScore();

			if (objective instanceof PandaObjective) {
				/* Be careful, the number here has to be changed when we'll have objective involving a
				 * different amount of bamboos. This action could be managed by the objectives themselves
				 * or by the Game maybe.
                 */
				Map<Color, Integer> pandaMotif = ((PandaObjective) objective).getMotifForCompleting();
				for (Color color : Color.values()) {
					this.stomach.put(color, this.stomach.get(color) - pandaMotif.get(color));
				}
			}
		}
	}

	private ObjectiveType whatTypeToDraw(ObjectivePool pool) {

		if (!pool.isDeckEmpty(ObjectiveType.PANDA)) {
			return ObjectiveType.PANDA;
		}

		List<ObjectiveType> types = new ArrayList<>(Arrays.asList(ObjectiveType.values()));
		types.removeIf(pool::isDeckEmpty);
		Collections.shuffle(types);

		return types.get(0);
	}

	public Point whereToMovePanda(Game game, List<Point> available) {

		// The player chooses to eat the bamboo pieces he lacks the most
		int minValue = this.getStomach().get(Color.PINK);
		Color minColor = Color.PINK;

		for (Color color : Color.values()) {
			if (this.getStomach().get(color) < minValue) {
				minValue = this.getStomach().get(color);
				minColor = color;
			}
		}

		// Looks for the first tile of the needed color
		for (Point point : available) {
			if (game.getBoard().get(point).getColor() == minColor && game.getBoard().get(point).getBambooSize() > 0) {
				return point;
			}
		}

		return available.get(0);
	}

	private void eatBamboo(Color color) {

		if (Objects.nonNull(color)) {
			this.stomach.put(color, this.stomach.get(color) + 1);
			TakenokoServer.print(String.format("Player has eaten a %s bamboo ! He now has %d %s bamboo(s) in his stomach", color, this.stomach.get(color), color));
		}
	}

	private final boolean putDownIrrigation(Game game, Point position, UnitVector direction) {

		if (irrigations > 0 && game.getBoard().get(position).getIrrigationState(direction).equals(IrrigationState.IRRIGABLE)) {
			irrigations--;
			return game.getBoard().irrigate(position, direction);
		}

		return false;
	}

	public void giveEmperor() {

		score += 2;
	}

}
