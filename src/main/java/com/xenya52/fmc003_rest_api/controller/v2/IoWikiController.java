package com.xenya52.fmc003_rest_api.controller.v2;

import com.xenya52.fmc003_rest_api.entity.dto.GetResponseDto;
import com.xenya52.fmc003_rest_api.entity.model.IoWikiModel;
import com.xenya52.fmc003_rest_api.service.IoWikiByFile;
import com.xenya52.fmc003_rest_api.service.IoWikiService;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/v2/io-wiki")
public class IoWikiController {

    // Attributes
    @Autowired
    private IoWikiService ioWikiService;

    @Autowired
    private IoWikiByFile fileIoWikis;

    // Methods
    // Todo: Implement a optional fil that you can give to the function. It'll process it like the default file, write a description to make this clear
    @GetMapping("/fetch-local-file-into-db")
    public ResponseEntity<String> debug() {
        List<IoWikiModel> idsAndNames = fileIoWikis.getIdsAndNames();
        if (idsAndNames == null) {
            return new ResponseEntity<>("Error", HttpStatus.NOT_FOUND);
        }
        ioWikiService.saveIoWikiList(idsAndNames);
        // Todo change debug
        return new ResponseEntity<>("Debug", HttpStatus.OK);
    }

    @GetMapping("/fetch-from-teltonikaIoWiki-into-db")
    public ResponseEntity<String> fetchFromTeltonikaIoWiki() {
        List<IoWikiModel> idsAndNames =
            ioWikiService.fetchFromTeltonikaIoWiki();
        if (idsAndNames == null) {
            return new ResponseEntity<>("Error", HttpStatus.NOT_FOUND);
        }
        System.out.println("idsAndNames" + idsAndNames);
        ioWikiService.saveIoWikiList(idsAndNames);
        return new ResponseEntity<>("Debug", HttpStatus.OK);
    }
}
