package com.juno.ounapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class OunApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(OunApiApplication.class, args);
	}

}
