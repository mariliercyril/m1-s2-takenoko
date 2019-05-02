package com.cco.takenoko.client.consumer;

import org.springframework.boot.CommandLineRunner;

import org.springframework.core.annotation.Order;

import org.springframework.web.client.RestTemplate;

import com.cco.takenoko.client.model.Client;

/**
 * The {@code SubscriptionCommandLineRunner} allows a client to subscribe to the game.
 * 
 * @author cmarilier
 */
@Order(1)
public class SubscriptionCommandLineRunner implements CommandLineRunner {

	private RestTemplate restTemplate;

	private Integer clientId;

	public SubscriptionCommandLineRunner(RestTemplate restTemplate, Integer clientId) {

		this.restTemplate = restTemplate;
		this.clientId = clientId;
	}

	@Override
	public void run(String... args) throws Exception {
	
		this.subscribe(restTemplate, clientId);
	}

	public void subscribe(RestTemplate restTemplate, Integer clientId) {

		Client client = new Client(clientId);

		restTemplate.postForEntity("http://localhost:8080/takenoko/clients/{id}", client, Client.class, clientId);
	}

}
