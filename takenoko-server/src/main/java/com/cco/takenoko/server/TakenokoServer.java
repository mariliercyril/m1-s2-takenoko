package com.cco.takenoko.server;

import org.apache.log4j.Logger;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.context.annotation.Bean;

/**
 * The {@code TakenokoServer} class is the main class of the Takenoko server.
 */
@SpringBootApplication
public class TakenokoServer {

	private static final Logger LOGGER = Logger.getLogger(TakenokoServer.class);

	private static boolean verbose = true;

	private static Integer clientsNumber;

	public static Integer getClientsNumber() {

		return clientsNumber;
	}

	public static void main(String[] args) {

		clientsNumber = Integer.valueOf(args[0]);

		SpringApplication.run(TakenokoServer.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner() {

		return args -> {

			print(" ________      _       __   __   ______    __    __   ________   __   __   ________  ");
			print("|__    __|   /   \\    |  | /  / |   ___|  |  \\  |  | |   __   | |  | /  / |   __   | ");
			print("   |  |     / /_\\ \\   |  |/  /  |  |__    |  |\\ |  | |  |  |  | |  |/  /  |  |  |  | ");
			print("   |  |    / _____ \\  |     <   |   __|_  |  | \\|  | |  |__|  | |     <   |  |__|  | ");
			print("   |__|   /_/     \\_\\ |__|\\__\\  |_______| |__|  \\__| |________| |__|\\__\\  |________| ");
			print("                                                         Presented by 2co\n");
		};
	}

	/**
	 * Allows a conditional print.
	 * 
	 * @param str The String to be printed.
	 */
	public static void print(String str) {

		if (verbose) {
			LOGGER.info(str);
		}
	}

	/**
	 * Allows to inject (i.e. at runtime) a verbose value (which is 'true' by default).
	 * 
	 * @param verbose typically a new verbose value
	 */
	public static void setVerbose(boolean verbose) {

		TakenokoServer.verbose = verbose;
	}

}
