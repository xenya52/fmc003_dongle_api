package com.xenya52.fmc003_rest_api;

import java.util.Arrays;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class Fmc003RestApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(Fmc003RestApiApplication.class, args);
    }
}
