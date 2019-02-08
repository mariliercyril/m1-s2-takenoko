package com.raccoon.takenoko.game.objective;

import com.raccoon.takenoko.game.Board;
import com.raccoon.takenoko.game.tiles.Color;
import com.raccoon.takenoko.game.tiles.Tile;

import java.util.List;

public class GardenerObjective extends Objective {
    private int nbrToCValidate;
    private Color color;

    public GardenerObjective(int nbrToCValidate, Color c, int score) {
        this.nbrToCValidate = nbrToCValidate;
        this.score = score;
        this.color = c;
    }

    public GardenerObjective(int nbrToCValidate, Color c) {
        this.nbrToCValidate = nbrToCValidate;
        this.score = 1;
        this.color = c;
    }

    @Override
    public void checkIfCompleted(Board board) {
        List<Tile> tiles = board.getAllTiles();
        tiles.removeIf(t -> ( t.getColor() != color ) || ( t.getBambooSize() != nbrToCValidate ));

        this.isCompleted = !tiles.isEmpty();
    }

    /**
     *
     * @return the number of bamboos needed on a tile to complete the objective
     */
    public int getNbrToCValidate() {
        return nbrToCValidate;
    }
}
