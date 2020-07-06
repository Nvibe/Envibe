package com.envibe.envibe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Core class for Spring application. Handles bootstrapping the Spring server.
 *
 * @author ARMmaster17
 */
@SpringBootApplication
public class EnvibeApplication {

	/**
	 * Main entry point for the application. Bootstraps Spring server.
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(EnvibeApplication.class, args);
	}

}
