package com.cco.takenoko.player;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
