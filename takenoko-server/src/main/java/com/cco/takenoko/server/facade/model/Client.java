package com.cco.takenoko.server.facade.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The client has the same class...
 * 
 * @author cmarilier
 */
public class Client {

	private int id;

	public Client(@JsonProperty("id") int id) {

		this.id = id;
	}

	public int getId() {

		return id;
	}

}
