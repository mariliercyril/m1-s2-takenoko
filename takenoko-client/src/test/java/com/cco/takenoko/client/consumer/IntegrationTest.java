package com.cco.takenoko.client.consumer;

import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.http.ResponseEntity;

import org.springframework.test.context.ContextConfiguration;

import org.springframework.web.client.RestTemplate;

import com.cco.takenoko.client.TakenokoClient;

import com.cco.takenoko.client.model.Client;

/**
 * The {@code IntegrationTest} allows to test the integration between client and server.
 * (It should contain at least one method per service.)
 * 
 * @author cmarilier
 * @author oualid benazzouz
 */
@ContextConfiguration(classes = TakenokoClient.class)
@SpringBootTest
public class IntegrationTest {

	protected ResponseEntity<?> responseEntity = null;

	protected RestTemplate restTemplate = null;

	/**
	 * Tests the subscription/inscription service.
	 * 
	 * @param id
	 *  the client ID (is also the ID of the corresponding player)
	 */
	protected void consumeSubscriptionService(int id) {

		if (restTemplate == null) {
			restTemplate = new RestTemplate();
		}

		Client client = new Client(id);
		responseEntity = restTemplate.postForEntity(AbstractServiceConsumer.SERVER_URL + "/clients", client, Client.class);
	}
	
	protected void consumeConnectionService(int id) {
		
		if (restTemplate == null) {
			restTemplate = new RestTemplate();
		}
		
		responseEntity = restTemplate.getForEntity(AbstractServiceConsumer.SERVER_URL + "/{id}", String.class, id);
		
		
	}

}
