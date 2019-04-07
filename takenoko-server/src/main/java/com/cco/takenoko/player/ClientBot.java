package com.cco.takenoko.player;

import java.awt.Point;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cco.takenoko.server.ServerFacade;

@RestController
public class ClientBot {

	@RequestMapping("/where-to-put-down-tile")
	public String whereToPutDownTile() {

		ServerFacade serverFacade = new ServerFacade();

		Point p = serverFacade.whereToPutDownTile();

		return "Could put down a tile in x=" + p.x + ", y=" + p.y + ".";
	}

}
