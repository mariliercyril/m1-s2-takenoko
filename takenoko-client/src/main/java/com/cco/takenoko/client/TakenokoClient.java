package com.cco.takenoko.client;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The {@code TakenokoClient} class is the main class of the Takenoko client.
 * 
 * @author cmarilier
 */
@SpringBootApplication
public class TakenokoClient {

	private static Integer clientId;

	public static void main(String[] args) {

		SpringApplication client = new SpringApplication(TakenokoClient.class);

		clientId = Integer.valueOf(args[0]);

		Map<String, Object> map = new HashMap<>();
		map.put("server.port", 9000 + clientId);
		client.setDefaultProperties(map);

		client.run(args);
	}

}
