package com.raccoon.takenoko.game.tiles;

import com.raccoon.takenoko.tool.Constants;
import com.raccoon.takenoko.tool.UnitVector;

import java.awt.Point;
import java.util.*;

/**
 * Representation of the tiles of the game.
 */
public class Tile {

    /*
     *************************************************
     *                 Fields
     *************************************************
     */

    private Point position;
    private Color color;
    private boolean enclosed;
    private int bambooSize;
    private boolean irrigated;
    private boolean irrigable;
    private int growthSpeed;   // Set to 2 if there is a fertilizer improvement
    private boolean improved;
    private Map<UnitVector, IrrigationState> sideIrrigationState;

    /*
     *************************************************
     *                 Constructors
     *************************************************
     */

    /**
     * Constructs a "pond" tile, that is to say the first tile to put on the board with specifics properties.
     */
    public Tile() {
        this.growthSpeed = Constants.USUAL_BAMBOO_GROWTH;
        this.enclosed = false;
        this.irrigated = false;
        this.color = null;
        this.irrigable = false;
        position = new Point(0, 0);
        initializeSideIrrigation();
        improved = false;
    }

    /**
     * Constructs a new tile of the specified color
     */
    public Tile(Color color) {
        this.growthSpeed = Constants.USUAL_BAMBOO_GROWTH;
        this.color = color;
        bambooSize = 0;
        initializeSideIrrigation();
        this.irrigated = false;
        this.irrigable = false;
        improved = false;

    }

    private void initializeSideIrrigation() {
        // Initialize the map, setting all the border as NOT IRRIGATED
        this.sideIrrigationState = new EnumMap<>(UnitVector.class);
        for (UnitVector vector : UnitVector.values()) {
            sideIrrigationState.put(vector, IrrigationState.NOT_IRRIGATED);
        }

    }

    /*
     *************************************************
     *                 Gets / Sets
     *************************************************
     */

    public int getBambooSize() {
        return bambooSize;
    }

    public Point getPosition() {
        return position;
    }

    /**
     * @return the color of the tile
     */
    public Color getColor() {    // Returns the color of the tile
        return color;
    }

    /**
     * @return the irrigation state of the tile
     */
    public boolean isIrrigated() {
        return irrigated;
    }

    /**
     * Allows to set the position of the tile, useful when the tile is put down
     *
     * @param position a point which will be the position of the Tile
     */
    public void setPosition(Point position) {
        this.position = position;
    }

    public boolean isIrrigable() {
        return irrigable;
    }

    /*
     *************************************************
     *                 Methods
     *************************************************
     */

    /**
     * Allows to know the state of a tile border regarding its irrigation.
     *
     * @param direction the UnitVector pointing to the border we want to check
     *
     * @return the irrigation state
     */
    public IrrigationState getIrrigationState(UnitVector direction) {
        return this.sideIrrigationState.get(direction);
    }

    /**
     * Irrigate the tile : the tile is now irrigated, knows where the irrigation canal is.
     * when called on a {@code Tile} that wasn't irrigated yet, the bamboo grows. If called on an already irrigated
     * {@code Tile} just remember where the irrigation canal is.
     *
     * @param direction a element from UnitVector, indicating the side to put the canal on.
     */
    public void irrigate(UnitVector direction) {

        if (!this.irrigated) {  // If we are not yet irrigated
            this.irrigated = true;  // we become irrigated
            // and a bamboo grows
            this.increaseBambooSize();
        }

        // Whether we are irrigated or not, we remember the presence of a new canal :
        this.sideIrrigationState.put(direction, IrrigationState.IRRIGATED);

        if (!this.sideIrrigationState.containsValue(IrrigationState.IRRIGABLE)) {
            this.irrigable = false;
        }
    }

    /**
     * Set the irrigable state of a {@code Tile}. Doesn't do anything if the side is already irrigable or irrigated.
     *
     * @param direction the UnitVector pointing toward the side to set irrigable.
     */
    public void setIrrigable(UnitVector direction) {

        if (!this.sideIrrigationState.get(direction).equals(IrrigationState.IRRIGATED)) {
            this.sideIrrigationState.put(direction, IrrigationState.IRRIGABLE);
            this.irrigable = true;
        }
    }


    /**
     * Increase the size of the bamboo on the tile. We make sure after increasing the the size doesn't exceed the max size.
     *
     */
    public void increaseBambooSize() {

        if (this.irrigated && Objects.nonNull(this.color)) {
            this.bambooSize += growthSpeed;
        }

        if (this.getBambooSize() > Constants.MAX_BAMBOO_SIZE && this.irrigated) {
            this.bambooSize = Constants.MAX_BAMBOO_SIZE;
        }
    }


    /**
     * Remove on chunk of bamboo on the {@code Tile}
     */
    public void decreaseBambooSize() {
        if (this.bambooSize > 0) {
            this.bambooSize--;
        }
    }

    /**
     *
     * @param direction the direction in which we want to set a potential irrigation
     */
    public void setPendingIrrigable(UnitVector direction){
        if (this.sideIrrigationState.get(direction).equals(IrrigationState.NOT_IRRIGATED)) {
            this.sideIrrigationState.put(direction, IrrigationState.TO_BE_IRRIGABLE);
        }
    }

    @Override
    public String toString() {
        return "Tile " + this.getColor() + " at " + this.getPosition();
    }

    protected void setIrrigated(boolean irrigated) {
        this.irrigated = irrigated;
    }

    protected void setEnclosure(boolean enclosed) {
        this.enclosed = enclosed;
    }

    public boolean isEnclosed() {
        return enclosed;
    }

    protected void setGrowthSpeed(int speed) {
        this.growthSpeed = speed;
    }

    public boolean isImproved() {
        return improved;
    }

    public void setImproved(boolean improved) {
        this.improved = improved;
    }
}
