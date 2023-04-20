package com.github.martinfrank.games.ninasgame.client;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class NinasGameClientApplication {

	public static void main(String[] args) {

		new SpringApplicationBuilder(NinasGameClientApplication.class)
				.headless(false)
				.web(WebApplicationType.NONE)
				.run(args);

	}

}
