package com.xenya52.fmc003_rest_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class Fmc003RestApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(Fmc003RestApiApplication.class, args);
    }
}
