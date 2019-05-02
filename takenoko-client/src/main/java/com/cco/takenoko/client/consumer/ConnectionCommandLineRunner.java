package com.cco.takenoko.client.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.CommandLineRunner;

import org.springframework.core.annotation.Order;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.client.RestTemplate;

/**
 * The {@code ConnectionController} allows a client to initialize a connection with the server.
 * 
 * @author cmarilier
 */
@Order(0)
public class ConnectionCommandLineRunner implements CommandLineRunner {

	private static final Logger LOGGER = LoggerFactory.getLogger(ConnectionCommandLineRunner.class);

	private static final String SERVER_RESPONSE_FORMAT = "Status of the connection from client %d to the server: %s.";

	private RestTemplate restTemplate;

	private Integer clientId;

	public ConnectionCommandLineRunner(RestTemplate restTemplate, Integer clientId) {

		this.restTemplate = restTemplate;
		this.clientId = clientId;
	}

	@Override
	public void run(String... args) throws Exception {

		int code = 0;

		while (code != 200) {
			code = this.ping(restTemplate, clientId);
		}
	}

	/**
	 * Allows the client to consume the connection service
	 * (corresponding to the server method "pong()").
	 * 
	 * @param restTemplate
	 *  for consuming the service
	 * @param id
	 *  the ID of the client
	 */
	public int ping(RestTemplate restTemplate, Integer id) {

		ResponseEntity<String> responseEntity = restTemplate.getForEntity("http://localhost:8080/takenoko/", String.class);
		HttpStatus statusCode = responseEntity.getStatusCode();

		int code = statusCode.value();

		String serverResponse = code + " " + statusCode.getReasonPhrase();

		if (code == 200) {
			LOGGER.info(String.format(SERVER_RESPONSE_FORMAT, id, serverResponse));
		} else {
			LOGGER.error(serverResponse);
		}

		return code;
	}

}
