package com.raccoon.takenoko.game;

import com.raccoon.takenoko.Takeyesntko;
import com.raccoon.takenoko.game.tiles.Color;
import com.raccoon.takenoko.game.tiles.Tile;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.util.List;

@Component
@Scope ("prototype")
public class Gardener {
    private Point position;
    private Game game;

    public Gardener() {
        this.position = new Point(0, 0);
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Point getPosition() {
        return position;
    }

    /**
     * This methods allows us to tell the gardener to move to a given location
     * @param position The location where we want the gardener to go
     */
    public void move(Point position) {
        Board board = game.getBoard();
        Takeyesntko.print("Gardener moves from " + this.position + " to " + position);
        this.position = position;
        board.get(this.position).increaseBambooSize(); //  Grow the bamboo where the gardener is
        this.grow(board.getNeighbours(this.position), board.get(this.position).getColor()); // ... And on the surrounding tiles of the same color
    }

    /**
     *
     * @param tiles The tiles surrounding the gardener
     * @param color The color of the tiles that can grow
     */
    private void grow(List<Tile> tiles, Color color) {
        for (Tile t : tiles) {
            if (t.getColor() == color) {
                t.increaseBambooSize();
            }
        }
    }
}