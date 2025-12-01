package com.tjoeun.corunning;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class CoRunningApplication {

	public static void main(String[] args) {
		SpringApplication.run(CoRunningApplication.class, args);
		
	}

}
