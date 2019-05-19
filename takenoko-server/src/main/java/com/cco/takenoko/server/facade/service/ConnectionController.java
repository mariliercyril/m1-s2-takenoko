package com.cco.takenoko.server.facade.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The {@code ConnectionController} allows a client to finalize its connection with the server.
 * 
 * @author cmarilier
 */
@RestController
@RequestMapping("/takenoko")
public class ConnectionController {

	/**
	 * Allows the server to provide the connection service
	 * (corresponding to the client method "ping()").
	 * 
	 * @param clientId
	 */
	@GetMapping("/{id}")
	public ResponseEntity<String> pong(@PathVariable("id") int clientId) {

		return new ResponseEntity<String>(HttpStatus.OK);
	}

}
