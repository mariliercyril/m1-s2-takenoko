package com.cco.takenoko.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The server has the same class...
 * 
 * @author cmarilier
 */
public class Client {

	private int id;

	public Client(@JsonProperty("id") int id) {

		this.id = id;
	}

	@JsonProperty("id")
	public int getId() {

		return id;
	}

}
