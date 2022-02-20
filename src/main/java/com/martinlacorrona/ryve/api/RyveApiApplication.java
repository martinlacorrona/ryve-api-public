package com.martinlacorrona.ryve.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class RyveApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(RyveApiApplication.class, args);
	}

}
