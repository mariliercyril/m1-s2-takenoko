package com.cco.takenoko.launcher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cco.takenoko.client.TakenokoClient;

import com.cco.takenoko.server.TakenokoServer;

/**
 * An object of {@code TakenokoLauncher} type is a launcher of the game Takenoko:
 * it first launches the server, then the clients (one by one)...
 * (It will be replaced with Docker...)
 */
public class TakenokoLauncher {

	private static final Logger LOGGER = LoggerFactory.getLogger(TakenokoLauncher.class);

	public TakenokoLauncher(int clientsNumber) {

		TakenokoServer.main(new String[]{String.valueOf(clientsNumber)});

		LOGGER.info(clientsNumber + " client" + ((clientsNumber > 1) ? "s" : "") + " to launch...");

		for (int i = 1; i < clientsNumber + 1; i++) {
			TakenokoClient.main(new String[]{String.valueOf(i)});
		}
	}

	public static void main(String[] args) {

		int clientsNumber = 2;

		if (args.length > 0) {
			clientsNumber = Integer.parseInt(args[0]);
			if (clientsNumber < 2) {
				clientsNumber = 2;
			}
		}

		new TakenokoLauncher(clientsNumber);

		//System.exit(0);
	}

}
