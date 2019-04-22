package com.cco.takenoko.player;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClientBot {

	@RequestMapping("/")
	public String home() {

		return "Hello world!";
	}

}
