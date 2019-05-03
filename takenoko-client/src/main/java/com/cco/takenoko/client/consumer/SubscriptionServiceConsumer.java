package com.cco.takenoko.client.consumer;

import org.springframework.core.annotation.Order;

import org.springframework.web.client.RestTemplate;

import com.cco.takenoko.client.model.Client;

/**
 * The {@code SubscriptionCommandLineRunner} allows a client to subscribe to the game.
 * 
 * @author cmarilier
 */
@Order(1)
public class SubscriptionServiceConsumer extends AbstractServiceConsumer {

	public SubscriptionServiceConsumer(RestTemplate restTemplate, Integer id) {

		super(restTemplate, id);
	}

	@Override
	public void run(String... args) throws Exception {
	
		this.subscribe();
	}

	/**
	 * Allows a client to subscribe to the game server.
	 */
	public void subscribe() {

		Client client = new Client(id);

		restTemplate.postForEntity(SERVER_URL + "/clients/{id}", client, Client.class, id);
	}

}
