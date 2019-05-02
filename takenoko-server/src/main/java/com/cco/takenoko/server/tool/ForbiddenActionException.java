package com.cco.takenoko.server.tool;

public class ForbiddenActionException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ForbiddenActionException(String message) {

		super(message);
	}

}
