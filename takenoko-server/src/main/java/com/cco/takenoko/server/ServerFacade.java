package com.cco.takenoko.server;

import java.awt.Point;

import java.io.IOException;

import com.cco.takenoko.Takenoko;

import com.cco.takenoko.game.Game;

import com.cco.takenoko.game.tiles.Color;
import com.cco.takenoko.game.tiles.Tile;

import com.cco.takenoko.player.BamBot;
import com.cco.takenoko.player.Player;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

public class ServerFacade {

	private Game game;

	private Tile tile;

	private BamBot bamBot;

	private Point point;

	private ObjectMapper om = new ObjectMapper();
	private ObjectWriter ow = om.writerWithDefaultPrettyPrinter();

	public ServerFacade() {

		game = Takenoko.getGame();

		chooseTile();

		for (Player player : game.getPlayers()) {
			if (player instanceof BamBot) {
				bamBot = (BamBot)player;
			}
		}
	}

	////////////////////////////
	// PUT_DOWN_TILE services //
	////////////////////////////
	//-> Tile t = this.chooseTile(game);
	//-> Point choice = this.whereToPutDownTile(game, t);
	//-> t.setPosition(choice);
	//-> game.putDownTile(t);
	// TODO: Javadoc of this service as a method.
	public String chooseTile() {

		String tileJson = "";

		tile = game.getTile();
		if (tile == null) {
			tile = new Tile();
		}

		TileJSON tileJSON = new TileJSON(tile);

		try {
			tileJson = ow.writeValueAsString(tileJSON);	
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}

		return tileJson;
	}

	// TODO: Javadoc of this service as a method.
	public String whereToPutDownTile(String tileJson) {

		String pointJson = "";

		if (bamBot != null) {
			try {
				Tile t = om.readValue(tileJson, Tile.class);
				point = bamBot.whereToPutDownTile(game, t);
				pointJson = ow.writeValueAsString(point);
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}

		return pointJson;
	}

	// TODO: Javadoc of this service as a method.
	public boolean putDownTile(String tileJson, String pointJson) {

		try {
			Tile t = om.readValue(tileJson, Tile.class);
			Point p = om.readValue(pointJson, Point.class);

			t.setPosition(p);
			game.putDownTile(t);

			return true;
		} catch (IOException ioe) {
			return false;
		}
	}

	/**
	 * This class allows to get a JSON of a soft version of a Tile object.
	 * 
	 * @author cmarilier
	 */
	@JsonPropertyOrder({ "position", "color", "irrigable", "irrigated" })
	private class TileJSON {

		private Point position;

		private Color color;

		private boolean irrigable;
		private boolean irrigated;

		private TileJSON(Tile tile) {

			this.setPosition(tile);

			this.setColor(tile);

			this.isIrrigable(tile);
			this.isIrrigated(tile);
		}

		@JsonSetter("position")
		private void setPosition(Tile tile) {

			position = tile.getPosition();
	    }

		@JsonGetter("position")
		private Point getPosition() {

			return position;
	    }

		@JsonSetter("color")
		private void setColor(Tile tile) {

			color = tile.getColor();
	    }

		@JsonGetter("color")
		private Color getColor() {

			return color;
	    }

		@JsonSetter("irrigable")
		private void isIrrigable(Tile tile) {

			irrigable = tile.isIrrigable();
	    }

		@JsonGetter("irrigable")
		private boolean isIrrigable() {

			return irrigable;
	    }

		@JsonSetter("irrigated")
		private void isIrrigated(Tile tile) {

			irrigated = tile.isIrrigated();
	    }

		@JsonGetter("irrigated")
		private boolean isIrrigated() {

			return irrigated;
	    }

	}

}
