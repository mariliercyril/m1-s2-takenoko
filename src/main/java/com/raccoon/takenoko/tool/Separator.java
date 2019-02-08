package com.raccoon.takenoko.tool;

public enum Separator {

	/**
	 * The singleton instance for the <b>colon</b> separator (":").
	 */
	COLON(":"),

	/**
	 * The singleton instance for the <b>point</b> separator (".").
	 */
	POINT("."),

	/**
	 * The singleton instance for the <b>slash</b> separator ("/").
	 */
	SLASH("/");

	private final String sign;

	private Separator(String sign) {

		this.sign = sign;
	}

	/**
	 * Returns the sign of the separator.
	 * 
	 * @return the sign of the separator
	 */
	public String getSign() {

		return sign;
	}

}
