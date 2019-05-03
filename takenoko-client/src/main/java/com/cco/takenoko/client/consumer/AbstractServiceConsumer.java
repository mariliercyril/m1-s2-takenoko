package com.cco.takenoko.client.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.CommandLineRunner;

import org.springframework.web.client.RestTemplate;

public abstract class AbstractServiceConsumer implements CommandLineRunner {

	protected static final Logger LOGGER = LoggerFactory.getLogger(AbstractServiceConsumer.class);

	// Socket + game root...
	protected static final String SERVER_URL = "http://localhost:8080/takenoko";

	// For consuming service
	protected RestTemplate restTemplate;

	// The client ID
	protected int id;

	public AbstractServiceConsumer(RestTemplate restTemplate, int id) {

		this.restTemplate = restTemplate;
		this.id = id;
	}

	public abstract void run(String... args) throws Exception;

}
