package com.xenya52.fmc003_rest_api.controller.v1;

import com.xenya52.fmc003_rest_api.service.ScrapeTeltonikaPage;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("teltonika-io-data")
public class TeltonikaIoDataController {

    // Attributes
    private ScrapeTeltonikaPage teltonikaIoPage;

    // Constructors
    public TeltonikaIoDataController() {
        this.teltonikaIoPage = new ScrapeTeltonikaPage();
    }

    // Methods
    @GetMapping("/teltonika-io-data")
    public String getTeltonikaIoPage() {
        ScrapeTeltonikaPage teltonikaIoPage = new ScrapeTeltonikaPage();
        return teltonikaIoPage.getIoData();
    }
}
