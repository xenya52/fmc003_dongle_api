package com.xenya52.fmc003_rest_api.service;

import com.xenya52.fmc003_rest_api.service.scrape.TeltonikaIoPage;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Fmc003RestApiApplication {

    public static void main(String[] args) {
        TeltonikaIoPage teltonikaIoData = new TeltonikaIoPage();
        teltonikaIoData.getIoData();
    }
}
