package com.cco.takenoko.server;

import java.awt.Point;
import java.io.IOException;

import com.cco.takenoko.Takenoko;

import com.cco.takenoko.game.Game;

import com.cco.takenoko.game.tiles.Tile;

import com.cco.takenoko.player.BamBot;
import com.cco.takenoko.player.Player;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

public class ServerFacade {

	Game game;

	Tile tile;

	BamBot bamBot;

	Point point;

	ObjectMapper om = new ObjectMapper();
	ObjectWriter ow = om.writerWithDefaultPrettyPrinter();

	public ServerFacade() {

		game = Takenoko.getGame();

		chooseTile();

		for (Player player : game.getPlayers()) {
			if (player instanceof BamBot) {
				bamBot = (BamBot)player;
			}
		}
	}

	/*			Tile t = this.chooseTile(game);
                Point choice = this.whereToPutDownTile(game, t);
                t.setPosition(choice);
                game.putDownTile(t); */

	public String chooseTile() {

		String tileJson = "";

		tile = game.getTile();
		if (tile == null) {
			tile = new Tile();
		}

		try {
			tileJson = ow.writeValueAsString(tile);	
		} catch (JsonProcessingException jpe) {
			jpe.printStackTrace();
		}

		return tileJson;
	}

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

}
