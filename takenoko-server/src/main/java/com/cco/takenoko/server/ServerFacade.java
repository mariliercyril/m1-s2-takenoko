package com.cco.takenoko.server;

import java.awt.Point;

import com.cco.takenoko.Takenoko;

import com.cco.takenoko.game.Game;

import com.cco.takenoko.game.tiles.Tile;

import com.cco.takenoko.player.BamBot;
import com.cco.takenoko.player.Player;

public class ServerFacade {

	Game game;

	Tile t = null;

	Player player;

	BamBot bamBot;

	public ServerFacade() {

		game = Takenoko.getGame();

		while (t == null) {
			t = game.getTile();
		}

		for (Player player : game.getPlayers()) {
			if (player instanceof BamBot) {
				bamBot = (BamBot)player;
			}
		}
	}

	public Point whereToPutDownTile() {

		Point point = null;

		if (bamBot != null) {
			point = bamBot.whereToPutDownTile(game, t);
			while (point == null) {
				whereToPutDownTile();
			}
		}

		return point;
	}

}
