package com.xenya52.fmc003_rest_api.controller.v2;

import com.xenya52.fmc003_rest_api.entity.dto.GetResponseDto;
import com.xenya52.fmc003_rest_api.entity.model.IoWikiModel;
import com.xenya52.fmc003_rest_api.service.IoWiki.IoWikiByFile;
import com.xenya52.fmc003_rest_api.service.IoWiki.IoWikiService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/v2/io-wiki")
@ConditionalOnProperty(name = "controller.version", havingValue = "v2")
public class IoWikiController {

    // Attributes
    @Autowired
    private IoWikiService ioWikiService;

    @Autowired
    private IoWikiByFile fileIoWikis;

    // Methods
    @GetMapping("/items/all")
    public ResponseEntity<List<GetResponseDto>> ioWikiAll() {
        List<GetResponseDto> response = ioWikiService.getIoWikiList();
        if (response.getFirst() == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
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

    @PostMapping("/fetch-default-values-into-db")
    public ResponseEntity<String> defaultValuesIntoDB() {
        List<IoWikiModel> idsAndNames;
        idsAndNames = fileIoWikis.dongleModelsByFile();

        if (idsAndNames == null) {
            return new ResponseEntity<>("Error", HttpStatus.NOT_FOUND);
        }

        if (!ioWikiService.saveIoWikiList(idsAndNames)) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/fetch-advanced-values-into-db")
    public ResponseEntity<String> advencedValuesIntoDB() {
        List<IoWikiModel> advancedDongleModels;
        advancedDongleModels = fileIoWikis.advancedDongleModelsByFile();

        if (advancedDongleModels == null) {
            return new ResponseEntity<>("Error", HttpStatus.NOT_FOUND);
        }

        if (!ioWikiService.saveIoWikiList(advancedDongleModels)) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
