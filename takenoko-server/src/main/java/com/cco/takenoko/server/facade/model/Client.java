package com.cco.takenoko.server.facade.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The client has the same class (without the counter)...
 * 
 * @author cmarilier
 */
public class Client {

	private static int counter = 0;

	private int id;

	public Client(@JsonProperty("id") int id) {

		this.id = id;

		counter++;
	}

	@JsonProperty("id")
	public int getId() {

		return id;
	}

	public static int getCounter() {

		return counter;
	}

}
