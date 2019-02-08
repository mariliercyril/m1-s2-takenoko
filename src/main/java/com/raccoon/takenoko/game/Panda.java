package com.raccoon.takenoko.game;

import com.raccoon.takenoko.Takeyesntko;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.awt.*;

@Component
@Scope("prototype")
public class Panda {
    private Point position;
    private Game game;

    public Panda() {
        this.position = new Point(0, 0);
    }

    public Point getPosition() {
        return position;
    }

    /**
     * Allows us to tell the panda to move to a given location.
     *
     * @param position The location where we want the panda to go
     */
    public void move(Point position) {
        Takeyesntko.print("Panda moves from " + this.position + " to " + position);
        this.position = position;
        this.eat(this.position); // Eat the bamboo where the panda lands
    }

    private void eat(Point position) {
        if (!game.getBoard().get(position).isEnclosed()) {    // The panda can't eat if there is an enclosure
            game.getBoard().get(position).decreaseBambooSize();
        }
    }

    public void setGame(Game g) {
        this.game = g;
    }
}
