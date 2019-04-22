package com.cco.takenoko.server;

import java.awt.Point;

import com.cco.takenoko.Takenoko;

import com.cco.takenoko.game.Game;

import com.cco.takenoko.game.tiles.Tile;

import com.cco.takenoko.player.BamBot;
import com.cco.takenoko.player.Player;

public class ServerFacade {

	Game game;

	Tile t;

	BamBot bamBot;

	Point point;

	public ServerFacade() {

		game = Takenoko.getGame();

		t = game.getTile();
		if (t == null) {
			t = new Tile();
		}

		for (Player player : game.getPlayers()) {
			if (player instanceof BamBot) {
				bamBot = (BamBot)player;
			}
		}
	}

	public Point whereToPutDownTile() {

		if (bamBot != null) {
			point = bamBot.whereToPutDownTile(game, t);
		}

		return point;
	}

}
