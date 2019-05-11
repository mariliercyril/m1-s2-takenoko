package com.cco.takenoko.server.facade.controller;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.ObjectFactory;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cco.takenoko.server.TakenokoServer;

import com.cco.takenoko.server.facade.ServerFacade;

import com.cco.takenoko.server.facade.model.Client;

import com.cco.takenoko.server.game.Game;

import com.cco.takenoko.server.player.Player;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

/**
 * The {@code InscriptionController} allows the server to inscribe a client...
 * 
 * @author cmarilier
 */
@RestController
@RequestMapping("/takenoko")
public class InscriptionController {

	private static final Logger LOGGER = LoggerFactory.getLogger(InscriptionController.class);

	private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
	private static final ObjectWriter OBJECT_WRITER = OBJECT_MAPPER.writerWithDefaultPrettyPrinter();

	private static final String SERVER_RESPONSE_FORMAT = "Client %d inscribed:";

	@Autowired
	private ObjectFactory<Game> gameObjectFactory;

	@PostMapping("/clients")
	public void inscribe(@RequestBody Client client) {

		int clientId = client.getId();

		LOGGER.info(String.format(SERVER_RESPONSE_FORMAT, clientId));

		Player player = new Player(clientId);

		String playerJson = "";
		try {
			playerJson = OBJECT_WRITER.writeValueAsString(player);
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		System.out.println(playerJson + "\n");

		// When all the clients are inscribed, the server launch games...
		// TODO: Currently, the clients play by proxy...
		//       (A player is assigned to each of them.)
		if (Client.getCounter() == TakenokoServer.getClientsNumber()) {
			(new ServerFacade()).launchGames(gameObjectFactory);
		}
	}

}
