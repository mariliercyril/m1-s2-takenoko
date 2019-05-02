package com.cco.takenoko.server.facade.controller;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.ObjectFactory;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cco.takenoko.server.TakenokoServer;

import com.cco.takenoko.server.facade.ServerFacade;

import com.cco.takenoko.server.facade.model.Client;

import com.cco.takenoko.server.game.Game;

import com.cco.takenoko.server.player.BamBot;

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

	@PostMapping("/clients/{id}")
	public void inscribe(@PathVariable("id") Integer id, @RequestBody Client client) {

		String clientJson = "";
		try {
			clientJson = OBJECT_WRITER.writeValueAsString(client);
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}

		LOGGER.info(String.format(SERVER_RESPONSE_FORMAT, id));
		System.out.println(clientJson + "\n");

		new BamBot(id);

		if (Client.getCounter() == TakenokoServer.getClientsNumber()) {
			(new ServerFacade()).launchGames(gameObjectFactory);
		}
	}

}
