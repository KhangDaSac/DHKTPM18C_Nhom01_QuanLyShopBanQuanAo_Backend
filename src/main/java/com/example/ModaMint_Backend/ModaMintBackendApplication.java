package com.example.ModaMint_Backend;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ModaMintBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(ModaMintBackendApplication.class, args);
	}
}
