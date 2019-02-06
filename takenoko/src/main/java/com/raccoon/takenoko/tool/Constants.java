package com.raccoon.takenoko.tool;

public final class Constants {

	private Constants() {}  // Prevent this class to be constructed

	private static final PropertiesFileReader PF_READER = PropertiesFileReader.getInstance();

	private static final String FILE_NAME = "takeyesntko";

	public static final int MAX_AMOUNT_OF_OBJECTIVES = PF_READER.getIntProperty(FILE_NAME, "max.amount.of.objectives", 5);
	public static final int NUMBER_OF_TILES_TO_DRAW = PF_READER.getIntProperty(FILE_NAME, "number.of.tiles.to.draw", 3);

	public static final int MAX_BAMBOO_SIZE = PF_READER.getIntProperty(FILE_NAME, "max.bamboo.size", 4);
	public static final int USUAL_BAMBOO_GROWTH = PF_READER.getIntProperty(FILE_NAME, "usual.bamboo.growth", 1);

	public static final float NUMBER_OF_GAMES_FOR_STATS = PF_READER.getFloatProperty(FILE_NAME, "number.of.games.for.stats", 1000);

}
