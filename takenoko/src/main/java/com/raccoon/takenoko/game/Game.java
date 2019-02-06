package com.raccoon.takenoko.game;

import com.raccoon.takenoko.Takeyesntko;
import com.raccoon.takenoko.game.objective.Objective;
import com.raccoon.takenoko.game.objective.ObjectivePool;
import com.raccoon.takenoko.game.objective.ObjectiveType;
import com.raccoon.takenoko.game.tiles.Color;
import com.raccoon.takenoko.game.tiles.ImprovementType;
import com.raccoon.takenoko.game.tiles.Tile;
import com.raccoon.takenoko.player.Player;
import com.raccoon.takenoko.tool.Constants;
import com.raccoon.takenoko.tool.ForbiddenActionException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.List;

/**
 * Class representing the games, and allowing to interract with it
 */
@Service
@Scope("prototype")
public class Game {

    private List<Player> players;           // The Players participating the game

    private LinkedList<Tile> tilesDeck;     // The deck in which players get the tiles

    private Map<ImprovementType, Integer> improvements; // The number of improvements of each type available

    @Autowired
    private Panda panda;                    // Probably the panda

    @Autowired
    private Gardener gardener;              // The gardener (obviously)

    /* Spring components */
    @Autowired
    private ObjectivePool objectivePool;    // The pool of objective cards

    @Autowired
    private Board board;                    // The game board, with all the tiles

    private Player hasEmperor;

    /*
     *************************************************
     *                 Constructors
     *************************************************
     */


    /**
     * Construct a game without players
     *
     */
    public Game() {

        this.players = new ArrayList<>();

        Player.reinitCounter();

        initTileDeck();
        initImprovements();
    }

    /**
     * Constructs a game with a given list of players. Useful to test and give a specific composition of bots to the game.
     *
     * @param players the list of {@code Players} to add to the game
     */
    public Game(List<Player> players) {
        this.gardener = new Gardener();
        this.players = players;
        initTileDeck();
        initImprovements();
    }

    @PostConstruct
    public void init() {
        this.objectivePool.setGame(this);
        this.gardener.setGame(this);
        this.panda.setGame(this);
    }

    // Initialize the deck of tiles, with the right amount of tile of each colour
    private void initTileDeck() {
        tilesDeck = new LinkedList<>();
        Color[] colors = new Color[]{Color.PINK, Color.GREEN, Color.YELLOW};

        for (Color c : colors) {
            for (int i = 0; i < c.getQuantite(); i++) {
                tilesDeck.push(new Tile(c));
            }
        }
        Collections.shuffle(tilesDeck);
    }

    public void addPlayers(int numberOfPlayers, FactoryBean<Player> factory) throws Exception {
        // If we didn't use the constructor with players in it we add them in the game
        if (players.isEmpty()) {
            for (int i = 0; i < numberOfPlayers; i++) {
                this.players.add(factory.getObject());
            }
        }
    }

    /*
     *************************************************
     *                 Get/Set
     *************************************************
     */

    protected List getTilesDeck() {
        return tilesDeck;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public ObjectivePool getObjectivePool() {
        return objectivePool;
    }

    public Board getBoard() {
        return board;
    }

    public Gardener getGardener() {
        return gardener;
    }

    public Panda getPanda() {
        return panda;
    }

    /*
     *************************************************
     *                 Methods
     *************************************************
     */

    public boolean gameOver() {     // Currently, the game is over as soon as a player reaches a score of 9 or the tilesDeck is empty
        boolean emperorDistributed = false;
        if (tilesDeck.isEmpty()) {
            return true;
        }

        for (Player p : players) {
            if (p == this.hasEmperor) { // we compare if the player that has the emperor points to this player
                return true;
            }
            if (p.getScore() >= 9 && !emperorDistributed) {
                emperorDistributed = true;
                p.giveEmperor();
                Takeyesntko.print(String.format("Player #%d has won the emperor !", p.getId()));
                this.hasEmperor = p;
            }

        }

        return false;
    }

    public void start() {           // Starts the game: while the game isn't over, each player plays

        int playerNumber = 0;
        int turnNumber = 0;
        while (!gameOver()) {

            if (playerNumber == 0) {   // If it's the first player turn, I.E. we are at the beginning of a turn
                Takeyesntko.print("\n######################################################");
                Takeyesntko.print("Beginning of turn number " + ++turnNumber);    // We print the new turn number
            }

            Takeyesntko.print("\nPlayer #" + players.get(playerNumber).getId() + " " + players.get(playerNumber).getClass().getSimpleName() + " is playing now.");
            try {
                players.get(playerNumber).play(this);
            } catch (ForbiddenActionException e) {
                Takeyesntko.print("\nPlayer #" + players.get(playerNumber).getId() + " tried to cheat: " + e.getMessage() + " I can see you, Player #" + players.get(playerNumber).getId() + "!");
            }
            playerNumber = ( playerNumber + 1 ) % players.size();   // To keep playerNumber between 0 and the size of the list of players
        }
        printRanking();
    }

    public Tile getTile() {         //  Takes a tile from the tilesDeck
        return tilesDeck.poll();
    }

    public List<Tile> getTiles() {       // Takes n (three) tiles from the tilesDeck

        ArrayList<Tile> tiles = new ArrayList<>();
        Tile candidate;
        for (int i = 0; i < Constants.NUMBER_OF_TILES_TO_DRAW; i++) {
            candidate = getTile();
            if (candidate != null) {
                tiles.add(candidate);
            }
        }
        return tiles;
    }

    public void putBackTile(Tile tile) {
        tilesDeck.add(tile);
    }

    public Player getWinner() {
        players.sort((Player p1, Player p2) -> p2.getScore() - p1.getScore());

        return players.get(0);
    }

    private void printRanking() {
        players.sort((Player p1, Player p2) -> p2.getScore() - p1.getScore());
        Takeyesntko.print("\n RANKING");
        for (Player pl : players) {
            Takeyesntko.print("Player #" + pl.getId() + " has " + pl.getScore() + " points (" + pl.getClass().getSimpleName() + ")");
        }
    }

    /**
     * Allows a player to put down a tile on the board. It also notifies the objective pool, so the pattern objectives
     * completion is checked.
     *
     * @param tile The tile to put down, with its position attribute set
     */
    public void putDownTile(Tile tile) {

        this.board.set(tile.getPosition(), tile);   // The tile is put in the right position in the board
        // Notification that a tile has been put, the completion of some objectives could be changed
        this.objectivePool.notifyTilePut(tile, getBoard());

    }

    /**
     * Allows a player to draw an objective card.
     *
     * @return the first objective card of the deck
     */
    public Objective drawObjective(ObjectiveType t) {
        /*
        This might be replaced by a direct call to the draw method of the objectivePool
        in the classes needing it
         */
        return this.objectivePool.draw(t);   // We just get the objective from the pool
    }

    public boolean isImprovementAvailable(ImprovementType improvement) {    // Tells if there is an improvement of the given type available
        return improvements.get(improvement) > 0;
    }

    public ImprovementType takeImprovement(ImprovementType improvement) {
        if (isImprovementAvailable(improvement)) {
            improvements.put(improvement, improvements.get(improvement) - 1);
            return improvement;
        } else {
            return null;
        }
    }

    private void initImprovements() {
        this.improvements = new EnumMap<>(ImprovementType.class);
        for (ImprovementType it : ImprovementType.values()) {  // at the beginning, we have two improvements of each type
            improvements.put(it, 2);
        }
    }

    public boolean noMoreImprovements() {
        boolean res = true;
        for (ImprovementType improvement : improvements.keySet()) {
            res = res && !isImprovementAvailable(improvement);
        }
        return res;
    }
}