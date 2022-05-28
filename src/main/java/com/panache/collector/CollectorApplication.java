package com.panache.collector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class CollectorApplication {
	private static final Logger log = LoggerFactory.getLogger(CollectorApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(CollectorApplication.class, args);
	}
	//bored ape -> 0xBC4CA0EdA7647A8aB7C2061c2E118A18a936f13D

}
