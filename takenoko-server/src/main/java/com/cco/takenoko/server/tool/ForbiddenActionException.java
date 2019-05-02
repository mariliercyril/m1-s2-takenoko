package com.cco.takenoko.server.tool;

public class ForbiddenActionException extends Exception {
    public ForbiddenActionException(String s) {
        super(s);
    }
}
