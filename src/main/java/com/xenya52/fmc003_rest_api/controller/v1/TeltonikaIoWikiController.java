package com.xenya52.fmc003_rest_api.controller.v1;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("teltonikaIoWiki")
public class TeltonikaIoWikiController {

    // Methods
    @GetMapping("/")
    public String getTeltonikaIoPage() {
        return ioWiki.getDataSendingParameters();
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
