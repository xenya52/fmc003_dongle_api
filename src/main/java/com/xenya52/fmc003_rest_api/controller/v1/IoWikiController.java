package com.xenya52.fmc003_rest_api.controller.v1;

import com.xenya52.fmc003_rest_api.entity.dto.GetResponseDto;
import com.xenya52.fmc003_rest_api.service.IoWiki.IoWikiService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/v1/io-wiki")
@ConditionalOnProperty(name = "controller.version", havingValue = "v1")
public class IoWikiController {

    // Attributes
    @Autowired
    private IoWikiService ioWikiService;

    // Methods
    @GetMapping("/items/all")
    public ResponseEntity<List<GetResponseDto>> ioWikiAll() {
        List<GetResponseDto> response = ioWikiService.getIoWikiList();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/items/{listOfIds}")
    public ResponseEntity<List<GetResponseDto>> ioWikiList(
        @PathVariable List<String> listOfIds
    ) {
        List<GetResponseDto> responseList = ioWikiService.getIoWikiListById(
            listOfIds
        );

        if (responseList.getFirst() == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }
}
