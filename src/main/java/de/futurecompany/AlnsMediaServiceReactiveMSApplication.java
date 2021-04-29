package de.futurecompany;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.config.EnableWebFlux;

@SpringBootApplication
@EnableWebFlux
public class AlnsMediaServiceReactiveMSApplication {

	public static void main(String[] args) {
		SpringApplication.run(AlnsMediaServiceReactiveMSApplication.class, args);
	}

}
