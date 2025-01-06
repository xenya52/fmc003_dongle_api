package com.xenya52.fmc003_rest_api.controller.v1;

import com.xenya52.fmc003_rest_api.model.IoWikiModel;
import com.xenya52.fmc003_rest_api.service.IoWikiByFile;
import com.xenya52.fmc003_rest_api.service.IoWikiService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("io-wiki") //just the name no path
public class IoWikiController {

    // Attributes
    @Autowired
    private IoWikiByFile ioWiki;

    @Autowired
    private IoWikiService ioWikiService;

    // Methods
    @GetMapping("/debug-save")
    public ResponseEntity<String> debug() {
        Map<String, String> idsAndNames = ioWiki.getIdsAndNames();
        if (idsAndNames == null) {
            return new ResponseEntity<>(
                "Error",
                HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
        System.out.println("idsAndNames" + idsAndNames);
        ioWikiService.saveIoWikiMap(idsAndNames);
        return new ResponseEntity<>("Debug", HttpStatus.OK);
    }

    @GetMapping("/io-wiki/all")
    public ResponseEntity<Map<String, String>> getTeltonikaIoPage() {
        return new ResponseEntity<>(
            ioWikiService.getIoWikiMap(),
            HttpStatus.OK
        );
    }

    @GetMapping("/io-wiki/id-to-name")
    public ResponseEntity<IoWikiModel> getTeltonikaIoIdAndName(String id) {
        return new ResponseEntity<>(
            ioWikiService.getIoWikiById(id),
            HttpStatus.OK
        );
    }

    @GetMapping("/io-wiki/name-to-id")
    public ResponseEntity<IoWikiModel> getTeltonikaIoNameAndId(String name) {
        return new ResponseEntity<>(
            ioWikiService.getIoWikiByName(name),
            HttpStatus.OK
        );
    }
}
