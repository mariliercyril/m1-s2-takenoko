package com.cco.takenoko.client.consumer;

import org.springframework.core.annotation.Order;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.client.RestTemplate;

/**
 * The {@code ConnectionServiceConsumer} allows a client to initialize a connection with the server.
 * 
 * @author cmarilier
 */
@Order(0)
public class ConnectionServiceConsumer extends AbstractServiceConsumer {

	private static final String SERVER_RESPONSE_FORMAT = "Status of the connection from client %d to the server: %s.";

	public ConnectionServiceConsumer(RestTemplate restTemplate, int id) {

		super(restTemplate, id);
	}

	@Override
	public void run(String... args) throws Exception {

		int code = 500;

		while (code != 200) {
			code = this.ping();
		}
	}

	/**
	 * Allows a client to consume the connection service
	 * (corresponding to the server method "pong()").
	 */
	public int ping() {

		ResponseEntity<String> responseEntity = restTemplate.getForEntity(SERVER_URL + "/{id}", String.class, id);
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
