package com.xenya52.fmc003_rest_api.service.api;

import com.xenya52.fmc003_rest_api.service.scrape.TeltonikaIoPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(value = "Teltonika IO Data", tags = { "Teltonika IO" })
public class TeltonikaIoController {

    TeltonikaIoPage teltonikaIoPage = new TeltonikaIoPage();

    @GetMapping("/teltonika-io")
    @ApiOperation(
        value = "Get Teltonika IO Data",
        notes = "Fetches data from Teltonika IO"
    )
    public String getTeltonikaIoPage() {
        return teltonikaIoPage.getIoData();
    }
}
