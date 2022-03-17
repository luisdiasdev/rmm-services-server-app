package com.luisdias.rmmservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

	// Each customer has N devices
	// Each customer has N services
	// Each service can have N pricing policies
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
