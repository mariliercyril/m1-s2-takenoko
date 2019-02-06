package com.raccoon.takenoko.game.objective;

import com.raccoon.takenoko.game.*;
import com.raccoon.takenoko.game.tiles.Color;
import com.raccoon.takenoko.game.tiles.Tile;
import com.raccoon.takenoko.player.Player;
import com.raccoon.takenoko.tool.UnitVector;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * The pool of objectives needed in a game, and all the means to interact with it.
 * Provides the notify methods to induce a completion checking for the right objectives.
 */
@Service
@Scope("prototype")
public class ObjectivePool {

    private Game game;      // The game to which this pool belongs

    /*
    The list of objectives by type, needed to check for the completion of a specific
    type of objective. Useful because not all the events affect all the objectives completion.
    ***** CAUTION ***** : This is a draft, has to be updated with an interface or a mother class
    for all the types. For now we just have one objective per type.
    */
    private List<Objective> bambooObjectives;
    private List<Objective> patternObjectives;
    private List<Objective> gardenerObjectives;

    private EnumMap<ObjectiveType, List<Objective>> deck;

    /**
     * Constructs a pool of objectives ready to be drawn in a random order.
     *
     */
    public ObjectivePool() {

        // Instanciation of the lists
        bambooObjectives = new ArrayList<>();
        patternObjectives = new ArrayList<>();
        gardenerObjectives = new ArrayList<>();

        // instanciation of the generic deck
        deck = new EnumMap<>(ObjectiveType.class);
        deck.put(ObjectiveType.PATTERN, new ArrayList<>());
        deck.put(ObjectiveType.PANDA, new ArrayList<>());
        deck.put(ObjectiveType.GARDENER, new ArrayList<>());

        // Instanciation of the objectives and filling of the lists.
        // pattern objectives :
        List<PatternObjective> allPatterns = this.getPatternObjectives();
        patternObjectives.addAll(allPatterns);
        deck.get(ObjectiveType.PATTERN).addAll(allPatterns);
        Collections.shuffle(patternObjectives);

        // panda objectives
        for (int i = 0; i < 10; i++) {
            for (PandaObjective.Motif pandaMotif : PandaObjective.Motif.values()) {
                PandaObjective newObjective = new PandaObjective(pandaMotif);
                bambooObjectives.add(newObjective);
                deck.get(ObjectiveType.PANDA).add(newObjective);
            }
        }
        Collections.shuffle(bambooObjectives);

        // Gardener objectives
        for (int i = 1; i < 4; i++) {
            for (Color color : Color.values()) {
                GardenerObjective newGObjective = new GardenerObjective(i, color, 1);
                gardenerObjectives.add(newGObjective);
                deck.get(ObjectiveType.GARDENER).add(newGObjective);
            }
        }
        Collections.shuffle(gardenerObjectives);
    }

    public void setGame(Game game) {
        this.game = game;
    }

    /**
     * Draws an objective from the deck.
     *
     * @return an objective yet unplayed in this game ATTENTION can be null if asked deck is empty
     */
    public Objective draw(ObjectiveType t) {
        if (!deck.get(t).isEmpty()) { return deck.get(t).remove(0); }
        return null;
    }

    /**
     * Notifies the pool that a tile has been put on the board. The completion checking of the objectives
     * depending on the patterns is triggered.
     *
     * @param tile the tile that has been put down, allows to check only the area where this event happened
     */
    public void notifyTilePut(Tile tile, Board b) {
        updatePatternObjectives(tile, b);
    }

    /**
     * Notifies the pool that a player's stomach state has changed. Either he had the panda to eat a bamboo
     * or he validated a bamboo objective, and his stomach has been emptied of some bamboo chunks.
     *
     * @param player the {@code Player} who triggered the action. The changes will have happened in his stomach.
     */
    public void notifyStomachEmptied(Player player) {
        updatePandaObjectives(player);
    }

    public void notifyBambooEaten(Board b, Player p) {
        updatePandaObjectives(p);
        updateGardenerObjectives(b);
    }


    public void notifyBambooGrowth(Board b) {
        updateGardenerObjectives(b);
    }

    private void updatePatternObjectives(Tile t, Board b) {
        for (Objective objective : patternObjectives) {
            for(Tile next : b.getAllTilesDistance(t.getPosition(), 2)){
                objective.checkIfCompleted(next, game.getBoard());
            }
        }
    }

    private void updatePandaObjectives(Player p) {

        List<Objective> objectives = p.getObjectives();    // First we get the list of the player's objectives

        for (Objective objective : objectives) {            // For each of these objectives,
            if (this.bambooObjectives.contains(objective)) { // we check if it is a bamboo objective
                // (thanks to its membership in the bamboo objective list)
                objective.checkIfCompleted(p); // If it is, we check for its completion
            }
        }
    }

    private void updateGardenerObjectives(Board b) {
        for (Objective go : gardenerObjectives) {
            go.checkIfCompleted(b);
        }
    }

    public boolean isDeckEmpty(ObjectiveType t) {
        return deck.get(t).isEmpty();
    }

    /**
     * Allows to get the type of an objective, without having to use the {@code instanceof} tool.
     * @param objective An Objective
     * @return the type of the objective
     */
    public ObjectiveType getObjectiveType(Objective objective) {

        if (this.bambooObjectives.contains(objective)) return ObjectiveType.PANDA;
        if (this.gardenerObjectives.contains(objective)) return ObjectiveType.GARDENER;
        if (this.patternObjectives.contains(objective)) return ObjectiveType.PATTERN;

        throw new RuntimeException("Objective not member of this pool");

    }

    private List<PatternObjective> getPatternObjectives(){
        ArrayList<PatternObjective> patterns = new ArrayList<>();

        // alignment
        patterns.add(new PatternObjective(new ArrayList<>(Arrays.asList(UnitVector.J, UnitVector.J)), Color.GREEN, 2));
        patterns.add(new PatternObjective(new ArrayList<>(Arrays.asList(UnitVector.J, UnitVector.J)), Color.YELLOW, 3));
        patterns.add(new PatternObjective(new ArrayList<>(Arrays.asList(UnitVector.J, UnitVector.J)), Color.PINK, 4));

        // V shape
        patterns.add(new PatternObjective(new ArrayList<>(Arrays.asList(UnitVector.J, UnitVector.I)), Color.GREEN, 2));
        patterns.add(new PatternObjective(new ArrayList<>(Arrays.asList(UnitVector.J, UnitVector.I)), Color.YELLOW, 3));
        patterns.add(new PatternObjective(new ArrayList<>(Arrays.asList(UnitVector.J, UnitVector.I)), Color.PINK, 4));

        // triangle
        patterns.add(new PatternObjective(new ArrayList<>(Arrays.asList(UnitVector.J, UnitVector.N)), Color.GREEN, 2));
        patterns.add(new PatternObjective(new ArrayList<>(Arrays.asList(UnitVector.J, UnitVector.N)), Color.YELLOW, 3));
        patterns.add(new PatternObjective(new ArrayList<>(Arrays.asList(UnitVector.J, UnitVector.N)), Color.PINK, 4));

        // Z single color
        patterns.add(new PatternObjective(new ArrayList<>(Arrays.asList(UnitVector.I, UnitVector.M, UnitVector.I)), Color.GREEN, 3));
        patterns.add(new PatternObjective(new ArrayList<>(Arrays.asList(UnitVector.I, UnitVector.M, UnitVector.I)), Color.YELLOW, 4));
        patterns.add(new PatternObjective(new ArrayList<>(Arrays.asList(UnitVector.I, UnitVector.M, UnitVector.I)), Color.PINK, 5));

        // Z dual color
        patterns.add(new PatternObjective(new ArrayList<>(Arrays.asList(UnitVector.I, UnitVector.M, UnitVector.I)), new ArrayList<>(Arrays.asList( Color.GREEN, Color.GREEN, Color.YELLOW, Color.YELLOW)), 2));
        patterns.add(new PatternObjective(new ArrayList<>(Arrays.asList(UnitVector.I, UnitVector.M, UnitVector.I)), new ArrayList<>(Arrays.asList( Color.GREEN, Color.GREEN, Color.PINK, Color.PINK)), 4));
        patterns.add(new PatternObjective(new ArrayList<>(Arrays.asList(UnitVector.I, UnitVector.M, UnitVector.I)), new ArrayList<>(Arrays.asList( Color.PINK, Color.PINK, Color.YELLOW, Color.YELLOW )), 5));

        return patterns;
    }

}
