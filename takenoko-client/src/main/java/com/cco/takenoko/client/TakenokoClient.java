package com.cco.takenoko.client;

import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The {@code TakenokoClient} class is the main class of the Takenoko client.
 * 
 * @author cmarilier
 */
@SpringBootApplication
public class TakenokoClient {

	public static void main(String[] args) {

		SpringApplication client = new SpringApplication(TakenokoClient.class);

		client.run(args);
	}

}
