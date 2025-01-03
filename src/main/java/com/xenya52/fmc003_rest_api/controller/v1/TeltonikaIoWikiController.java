package com.xenya52.fmc003_rest_api.controller.v1;

import com.xenya52.fmc003_rest_api.service.ScrapeTeltonikaIoWiki;
import java.util.Dictionary;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("teltonika-io-data")
public class TeltonikaIoWikiController {

    // Attributes
    private ScrapeTeltonikaIoWiki ioWiki;

    // Constructors
    public TeltonikaIoWikiController() {
        this.ioWiki = new ScrapeTeltonikaIoWiki();
    }

    // Methods
    @GetMapping("/teltonika-io-data")
    public String getTeltonikaIoPage() {
        return ioWiki.getDataSendingParameters();
    }

    @GetMapping("/teltonika-io-translate-{id}")
    public String getTeltonikaIoIdAndName(int propertyIDInAVLPacket) {
        return ioWiki.idToName(propertyIDInAVLPacket);
    }

    // Debugging
    @GetMapping("/teltonika-io-debug")
    public String debug() {
        String data = ioWiki.idToName(80);
        return data;
    }
}
