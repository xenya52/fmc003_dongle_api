package com.xenya52.fmc003_rest_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableMongoRepositories
@EnableScheduling // Needed for ScrapeTeltonikaIoWiki
public class Fmc003RestApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(Fmc003RestApiApplication.class, args);
    }
}
