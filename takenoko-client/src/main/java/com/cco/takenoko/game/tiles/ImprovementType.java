package com.cco.takenoko.game.tiles;

import com.cco.takenoko.tool.Constants;
import com.cco.takenoko.tool.ForbiddenActionException;

public enum ImprovementType {
    ENCLOSURE, FERTILIZER, WATERSHED;

    public void improve(Tile t) throws ForbiddenActionException {
        if (t.isImproved()) {
            throw(new ForbiddenActionException("Tried to improve a tile that already had an improvement !"));
        }
        switch(this) {
            case WATERSHED:
                boolean wasIrrigated = t.isIrrigated();
                t.setIrrigated(true);   // First irrigation growth rule
                if (!wasIrrigated) {
                    t.increaseBambooSize();
                }
                break;
            case ENCLOSURE:
                t.setEnclosure(true);
                break;
            case FERTILIZER:
                t.setGrowthSpeed(2*Constants.USUAL_BAMBOO_GROWTH);
                break;
                default:
                    break;
        }
        t.setImproved(true);
    }
}
