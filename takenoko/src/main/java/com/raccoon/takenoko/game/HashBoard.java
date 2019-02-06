package com.raccoon.takenoko.game;

import com.raccoon.takenoko.Takeyesntko;
import com.raccoon.takenoko.game.tiles.IrrigationState;
import com.raccoon.takenoko.game.tiles.Tile;
import com.raccoon.takenoko.tool.UnitVector;
import com.raccoon.takenoko.tool.Vector;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.*;
import java.util.List;

@Service("hashBoard")
@Scope("prototype")
public class HashBoard implements Board {

    /*
     ******** Fields ********
     */
    private HashMap<Point, Tile> board;         // The actual representation of the board, which is an HashMap in this implementation
    private List<Point> availablePositions;     // the list of the tiles with a free border
    /*
     ******** Constructor ********
     */

    /**
     * Constructs a new HashBoard, initialized with a "pond" Tile in its center (0, 0).
     * The representation is hexagonal. Each horizontal line share a common 'y' component of its coordinates, each
     * diagonal from bottom left to top right share a common 'x' component of its coordinates, and each diagonal from
     * top left to bottom right has both its coordinates components evolving in the oposite direction (i.e. [-1, +1], or [+1, -1]).
     *
     */
    public HashBoard() {

        this.board = new HashMap<>();

        Point firstPosition = new Point(0, 0);

        this.availablePositions = new ArrayList<>(Arrays.asList(this.getNeighbouringCoordinates(firstPosition)));

        Tile firstTile = new Tile();
        this.set(new Point(0, 0), firstTile);
        firstTile.setPosition(new Point(0, 0));
        firstTile.setImproved(true);    // We consider the pond tile to be already improved
    }

    /*
     ******** Methods ********
     */

    /*
     **** Private ****
     */

    private Point[] getNeighbouringCoordinates(Point position) {
        /*
        Returns an array with the coordinates of all the neighbours position
        of the specified position
         */

        UnitVector[] unitVectors = UnitVector.values();
        Point[] neighbouringCoordinates = new Point[6];

        for (int i = 0; i < unitVectors.length; i++) {
            neighbouringCoordinates[i] = unitVectors[i].getVector().applyTo(position);
        }

        return neighbouringCoordinates;

    }


    private List<Point> getFreeNeighbouringPositions(Point position) {
        /*
        Returns the free positions on the board adjacent to the given position
         */
        List<Point> neighboursAvailable = new ArrayList<>();

        Point[] neighbours = this.getNeighbouringCoordinates(position);

        for (Point point : neighbours) {

            if (!board.containsKey(point)) {
                neighboursAvailable.add(point);
            }

        }
        neighboursAvailable.remove(position);

        return neighboursAvailable;
    }

    /*
     **** Public ****
     */

    @Override
    public Tile get(Point position) {
        // Simple translation of the HashMap get
        return board.get(position);
    }


    @Override
    public void set(Point position, Tile tile) {

        this.availablePositions.remove(position);    // We remove our position from the list (will crash if this position was not available…)

        board.put(position, tile);      // We simply put the tile in the HashMap with the right key

        List<Point> neighbourPositions = this.getFreeNeighbouringPositions(position);   // We get the list of the free positions adjacent to one of the new tile

        tile.setPosition(position);     // we indicate its coordinates to the tile

        /*
         ********************************************************
         * All this code just to treat the case of the irrigation
         * of the tiles next to the pond.
         * This is the initialisation of the conditions to build a coherent
         * irrigation network.
         */
        if (Arrays.asList(getNeighbouringCoordinates(position)).contains(new Point(0, 0))) {
            UnitVector directionOfThePond = null;
            /* We look for the UnitVector pointing toward the pond from our tile
            As we don't have a way to get a UnitVector from a corresponding vector.
            */
            for (int i = 0; i < UnitVector.values().length; i++) {
                if (UnitVector.values()[i].getVector().equals(( new Vector(position) ).getOpposite())) {
                    directionOfThePond = UnitVector.values()[i];
                }
            }

            // We put an irrigation chanel on the border adjacent to the pond
            tile.setIrrigable(directionOfThePond);
            this.irrigate(position, directionOfThePond);

        }

        // We maintain the list of available positions
        for (Point emptyPosition : neighbourPositions) {        // For each empty position adjacent to a tile
            if (this.getNeighbours(emptyPosition).size() >= 2 && !availablePositions.contains(emptyPosition)) {
                // If 2 tiles at least are adjacent and if it's not there yet
                this.availablePositions.add(emptyPosition);     // we add it to the available positions
            }
        }

        try {
            for (UnitVector u : UnitVector.values()) {
                // in all directions, check the status of the neighbour and set it to irrigable if it is pending
                Tile n = board.get(u.getVector().applyTo(position));

                if (Objects.nonNull(n) && n.getIrrigationState(u.opposite()).equals(IrrigationState.TO_BE_IRRIGABLE)) {
                    tile.setIrrigable(u);
                    n.setIrrigable(u.opposite());
                }
            }
        } catch (NullPointerException e) {
            return;
        }

        Takeyesntko.print("A tile has been placed at " + position + ".");
    }

    @Override
    public List<Point> getAvailablePositions() {
        return this.availablePositions;
    }

    @Override
    public List<Tile> getNeighbours(Point position) {

        ArrayList<Tile> neighbours = new ArrayList<>();

        Point[] points = this.getNeighbouringCoordinates(position);

        for (Point point : points) {

            if (board.containsKey(point)) {
                neighbours.add(this.get(point));
            }

        }
        neighbours.remove(this.get(position));

        return neighbours;
    }

    @Override
    public List<Point> getAccessiblePositions(Point initialPosition) {

        ArrayList<Point> accessiblePositions = new ArrayList<>();   // Instantiation of the empty list

        for (UnitVector unitVector : UnitVector.values()) {
            Point tempPoint = initialPosition;      // tempPoint will travel to every position accessible in straight line
            // using the UNIT vectors.

            while (this.board.containsKey(tempPoint = unitVector.getVector().applyTo(tempPoint))) {
                accessiblePositions.add(tempPoint);
            }
        }

        return accessiblePositions;
    }

    @Override
    public boolean irrigate(Point p, UnitVector direction) {

        // Get the coordinates of the second tile involved in this irrigation
        Point otherPositionToIrrigate = new Point(direction.getVector().applyTo(p));

        if (this.get(p)
                .getIrrigationState(direction)
                .equals(IrrigationState.IRRIGABLE)) {
            // The IrrigationState is IRRIGABLE only when we have two adjacent tiles connected to the irrigation network

            this.get(p).irrigate(direction);    // We irrigate the tile
            this.get(otherPositionToIrrigate).irrigate(direction.opposite());   // and the adjacent one

            /*
            **********************************************************************************
            DANGER ZONE : I'll comment, refactor and explain this later. Pontential source of bug.
            Variables have to have a better name


             */

            int[] angles = {-1, 1};

            for (int angle : angles) {
                UnitVector directionVector = direction.rotation(angle);
                Point irrigablePosition = directionVector.getVector().applyTo(p);
                if (board.containsKey(irrigablePosition)) {
                    // If we have a tile that becomes irrigable
                    Tile irrigableTile = board.get(irrigablePosition);

                    this.get(p).setIrrigable(directionVector);
                    this.get(otherPositionToIrrigate).setIrrigable(directionVector.rotation(angle));

                    irrigableTile.setIrrigable(directionVector.opposite());
                    irrigableTile.setIrrigable(directionVector.opposite().rotation(angle));
                } else {
                    // if we have no neighbour in a side next to where we irrigate, we want to flag the side anyway
                    this.get(p).setPendingIrrigable(directionVector);
                }
            }

            /*
            END OF THE DANGER ZONE
            ***************************************************
             */

            return true;    // Means the irrigation procedure worked
        }

        return false;
    }

    @Override
    public List<Tile> getAllTiles() {
        return new ArrayList<>(board.values());
    }

    @Override
    public List<Tile> getIrrigableTiles() {
        List<Tile> allT = getAllTiles();
        allT.removeIf(Tile::isIrrigable);
        return allT;
    }

    @Override
    public Set<Tile> getAllTilesDistance(Point pos, int n) {
        Set<Tile> memory = new HashSet<>();

        if (n <= 0) {
            return new HashSet<>(Collections.singletonList(this.get(pos)));
        }

        if (Objects.nonNull(this.get(pos))) {
            for (UnitVector v : UnitVector.values()) {
                // we look in all directions

                // we add the current tile
                memory.add(this.get(pos));
                // we add our neighbour
                memory.add(this.get(v.getVector().applyTo(pos)));
                // We search for its neighbours
                memory.addAll(getAllTilesDistance(v.getVector().applyTo(pos), n - 1));
            }
        }

        return memory;
    }
}
