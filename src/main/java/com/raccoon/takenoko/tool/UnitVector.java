package com.raccoon.takenoko.tool;

/**
 * An unit vector, such as '<i>i</i>'.
 * <p>{@code UnitVector} is an enum representing all the necessary unit vectors –
 * not only the vectors (1, 0) and (0, 1), but also the vectors (1, 1), (-1, 0), (-1, -1) and (0, -1) –
 * ordered in the trigonometric (anticlockwise) sense, for facilitating the tile position operations.
 * Each of these six vectors is defined by a {@link Vector} provided as a parameter.</p>
 */
public enum UnitVector {

	/**
	 * The singleton instance for the unit vector <i>i</i> = (1, 0).
	 */
	I(new Vector(1, 0)),

	/**
	 * The singleton instance for the unit vector <i>j</i> = (1, 1).
	 */
	J(new Vector(1, 1)),

	/**
	 * The singleton instance for the unit vector <i>k</i> = (0, 1).
	 */
	K(new Vector(0, 1)),

	/**
	 * The singleton instance for the unit vector <i>l</i> = (-1, 0).
	 */
	L(new Vector(-1, 0)),

	/**
	 * The singleton instance for the unit vector <i>m</i> = (-1, -1).
	 */
	M(new Vector(-1, -1)),

	/**
	 * The singleton instance for the unit vector <i>n</i> = (0, -1).
	 */
	N(new Vector(0, -1));

	private final Vector vector;

	private UnitVector(Vector vector) {

		this.vector = vector;
	}

	/**
	 * Returns the vector as a {@code Vector} object.
	 * 
	 * @return the vector as a <i>Vector</i> object.
	 */
	public Vector getVector() {

		return vector;
	}

    /**
     * Returns the opposite {@code UnitVector} of the enum element.
     * (This method corresponds to do "rotation(3)".)
     * 
     * @return an enum element, the opposite of the one its called to
     */
	public UnitVector opposite() {

		return values()[(this.ordinal() + 3) % 6];
	}

	/**
	 * Returns the {@code UnitVector} resulting from the rotation from an unit Vector <i>v</i>.
	 * 
	 * @param angle
	 *  the angle as the number, <i>n</i>, of rotations to be done
	 *  for getting the <i>n</i>-th unit Vector in the clockwise sense
	 *  as well as the trigonometric sense from <i>v</i>
	 * 
	 * @return the unit Vector resulting from the rotation from an unit Vector <i>v</i>
	 */
	public UnitVector rotation(int angle) {

		return values()[(this.ordinal() + ((angle < 0) ? 6 : 0) + (angle % 6)) % 6];
	}

}
