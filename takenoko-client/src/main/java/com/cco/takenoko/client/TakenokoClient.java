package com.cco.takenoko.client;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.boot.web.client.RestTemplateBuilder;

import org.springframework.context.annotation.Bean;

import org.springframework.web.client.RestTemplate;

import com.cco.takenoko.client.consumer.ConnectionServiceConsumer;
import com.cco.takenoko.client.consumer.SubscriptionServiceConsumer;

/**
 * The {@code TakenokoClient} class is the main class of a Takenoko client.
 * 
 * @author cmarilier
 */
@SpringBootApplication
public class TakenokoClient {

	// The client ID
	private static int id;

	@Autowired
	private RestTemplate restTemplate;

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {

		return restTemplateBuilder.build();
	}

	public static void main(String[] args) {

		SpringApplication client = new SpringApplication(TakenokoClient.class);

		id = Integer.parseInt(args[0]);

		Map<String, Object> map = new HashMap<>();
		map.put("server.port", 9000 + id);
		client.setDefaultProperties(map);

		client.run(args);
	}

	@Bean
	public ConnectionServiceConsumer getConnectionServiceConsumer() {

		return new ConnectionServiceConsumer(restTemplate, id);
	}

	@Bean
	public SubscriptionServiceConsumer getSubscriptionServiceConsumer() {

		return new SubscriptionServiceConsumer(restTemplate, id);
	}

}
