package com.xenya52.fmc003_rest_api.controller.v1;

import com.xenya52.fmc003_rest_api.entity.dto.GetResponseDto;
import com.xenya52.fmc003_rest_api.entity.model.IoWikiModel;
import com.xenya52.fmc003_rest_api.service.IoWikiByFile;
import com.xenya52.fmc003_rest_api.service.IoWikiService;
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
@RequestMapping("/v1/io-wiki")
public class IoWikiController {

    // Attributes
    @Autowired
    private IoWikiByFile ioWiki;

    @Autowired
    private IoWikiService ioWikiService;

    // Methods
    // Todo make a own mongoService for this without restapi
    /**
    @GetMapping("/debug-fetch-file-specific-file")
    public ResponseEntity<String> debug() {
        List<IoWikiModel> idsAndNames = ioWiki.getIdsAndNames();
        if (idsAndNames == null) {
            return new ResponseEntity<>(
                "Error",
                HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
        System.out.println("idsAndNames" + idsAndNames);
        ioWikiService.saveIoWikiList(idsAndNames);
        return new ResponseEntity<>("Debug", HttpStatus.OK);
    }
    */
    @GetMapping("/all")
    public ResponseEntity<List<GetResponseDto>> ioWikiAll() {
        List<GetResponseDto> response = ioWikiService.getIoWikiList();
        if (response == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetResponseDto> ioWikiById(@PathVariable String id) {
        GetResponseDto response = ioWikiService.getIoWikiById(id);
        if (response == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
