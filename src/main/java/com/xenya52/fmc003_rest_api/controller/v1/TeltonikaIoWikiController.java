package com.xenya52.fmc003_rest_api.controller.v1;

import com.xenya52.fmc003_rest_api.service.ScrapeTeltonikaIoWiki;
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

    @GetMapping("/teltonika-io-data-json")
    public String getTeltonikaIoPageJson() {
        return ioWiki.getDataSendingParametersJson();
    }

    @GetMapping("/iowiki-id-to-name")
    public String getTeltonikaIoIdAndName(int id) {
        String name = ioWiki.idToName(id);
        return name;
    }

    @GetMapping("/iowiki-name-to-id")
    public String getTeltonikaIoNameAndId(String name) {
        String id = ioWiki.nameToId(name);
        return id;
    }
}
