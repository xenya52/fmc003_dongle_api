package com.xenya52.fmc003_rest_api.controller.v1;

import com.xenya52.fmc003_rest_api.entity.model.IoWikiModel;
import com.xenya52.fmc003_rest_api.service.IoWikiByFile;
import com.xenya52.fmc003_rest_api.service.IoWikiService;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController("v1-io-wiki") //just the name no path
public class IoWikiController {

    // Attributes
    @Autowired
    private IoWikiByFile ioWiki;

    @Autowired
    private IoWikiService ioWikiService;

    // Methods
    @GetMapping("/v1/wiki/debug-save")
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

    @GetMapping("/v1//wiki/all")
    public ResponseEntity<List<IoWikiModel>> getTeltonikaIoPage() {
        return new ResponseEntity<>(
            ioWikiService.getIoWikiMap(),
            // Todo linker with all simular methods
            HttpStatus.OK
        );
    }

    @GetMapping("/v1/wiki/{id}/items")
    public ResponseEntity<IoWikiModel> ioWikiById(@PathVariable String id) {
        log.info("ioWikiById: " + id);
        return new ResponseEntity<>(
            ioWikiService.getIoWikiById(id),
            // Todo linker with all simular methods
            HttpStatus.OK
        );
    }
}
