package com.example.colombina;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ColombinaApplication {
//Se corre
	public static void main(String[] args) {
		SpringApplication.run(ColombinaApplication.class, args);
	}

}
