package com.cco.takenoko.player;

import java.awt.Point;
import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cco.takenoko.game.Board;
import com.cco.takenoko.game.tiles.Tile;
import com.cco.takenoko.server.ServerFacade;

@RestController
public class ClientBot {

	private String tileJson = "";
	private String pointJson = "";

	private ServerFacade serverFacade;

	@RequestMapping("/")
	public String getServerFacade() {

		serverFacade = new ServerFacade();

		if (serverFacade != null) {
			return "Server facade instancied.";
		} else {
			return "No server facade instancied.";
		}
	}

	@RequestMapping("/choose-tile")
	public String chooseTile() {

		if (serverFacade != null) {
			tileJson = serverFacade.chooseTile();
		}

		return tileJson;
	}

	@RequestMapping("/where-to-put-down-tile")
	public String whereToPutDownTile() {

		String response = "";

		if (serverFacade != null) {
			if (tileJson == "") {
				response = "First please choose a tile.";
			} else {
				pointJson = serverFacade.whereToPutDownTile(tileJson);
				response = pointJson;
			}
		}

		return response;
	}

	@RequestMapping("/put-down-tile")
	public String putDownTile() {

		String response = "";

		if (serverFacade != null && tileJson != "" && pointJson != "") {
			if (serverFacade.putDownTile(tileJson, pointJson)) {
				response = "You have put down the tile.";
			} else {
				response = "Putting down the tile has failed.";
			}
		} else {
			response = "You have forgotten to choose a tile or ask where to put it down.";
		}

		return response;
	}
	
	@RequestMapping("/isIrrigable/{x}/{y}")
	public String isIrrigable(@PathVariable("x") int x, @PathVariable("y") int y){
		String str="";
		Point position=new Point(x,y);
		Board board=this.serverFacade.getGame().getBoard();
		Tile tile=board.get(position);
		// List<Tile> irrigableTiles=board.getIrrigableTiles();
		str=tile.isIrrigable() 
				? String.format("The tile at (%d, %d) is irrigable", x, y) 
				: String.format("The tile at (%d, %d) is <b>not</b> irrigable", x, y);  
		return str;
	}
}
