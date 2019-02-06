package com.raccoon.takenoko.tool;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.Point;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * This class allows to test methods of the enum {@link UnitVector}.
 */
public class UnitVectorTest {

	private static UnitVector unitVector;

	@BeforeAll
	public static void constructReferenceUnitVector() {

		unitVector = UnitVector.J;
	}

	@Test
	@DisplayName("assert true when the opposite of an unitVector is the expected unitVector")
	public void testOpposite_true() {

		assertTrue(UnitVector.I.opposite().equals(UnitVector.L));
		assertTrue(UnitVector.J.opposite().equals(UnitVector.M));
		assertTrue(UnitVector.K.opposite().equals(UnitVector.N));
		assertTrue(UnitVector.L.opposite().equals(UnitVector.I));
		assertTrue(UnitVector.M.opposite().equals(UnitVector.J));
		assertTrue(UnitVector.N.opposite().equals(UnitVector.K));
	}

	@Test
	@DisplayName("assert true when the rotation of an unitVector is done in the positive (trigonometric, i.e. anticlockwise) sense")
	public void testRotation_truePositiveSense() {

		assertTrue(unitVector.rotation(4).getVector().equals(new Point(0, -1)));
	}

	@Test
	@DisplayName("assert true when the rotation of an unitVector is done in the negative (clockwise) sense")
	public void testRotation_trueNegativeSense() {

		assertTrue(unitVector.rotation(-2).getVector().equals(new Point(0, -1)));
	}

}
