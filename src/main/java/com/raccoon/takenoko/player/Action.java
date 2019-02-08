package com.raccoon.takenoko.player;

public enum Action {
    PUT_DOWN_TILE(1),
    MOVE_GARDENER(1),
    DRAW_OBJECTIVE(1),
    DRAW_IRRIGATION(1),
    VALID_OBJECTIVE(0),
    PUT_DOWN_IRRIGATION(0),
    MOVE_PANDA(1);

    Action(int cost) {
        this.cost = cost;
    }

    /**
     * 1 if the action is among the limited actions you must choose to play
     * 0 if the action is among the asynchronous actions you can play whenever
     */
    private int cost;

    public int getCost() {
        return cost;
    }

}
