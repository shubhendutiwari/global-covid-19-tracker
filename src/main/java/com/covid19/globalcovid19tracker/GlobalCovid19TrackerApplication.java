package com.covid19.globalcovid19tracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class GlobalCovid19TrackerApplication {

	public static void main(String[] args) {
		SpringApplication.run(GlobalCovid19TrackerApplication.class, args);
	}

}
