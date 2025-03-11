package com.xenya52.fmc003_rest_api.controller.v2;

import com.xenya52.fmc003_rest_api.entity.dto.GetResponseDto;
import com.xenya52.fmc003_rest_api.entity.model.IoWikiModel;
import com.xenya52.fmc003_rest_api.service.IoWiki.IoWikiByFile;
import com.xenya52.fmc003_rest_api.service.IoWiki.IoWikiService;
import com.xenya52.fmc003_rest_api.service.IoWiki.ScrapeTeltonikaIoWiki;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @brief Controller for IoWiki operations in version 2 of the API.
 */
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

    @Autowired
    private ScrapeTeltonikaIoWiki scrapeTeltonikaIoWiki;

    /**
     * @brief Get all IoWiki items.
     * @return ResponseEntity containing a list of GetResponseDto or NOT_FOUND status.
     */
    @GetMapping("/items/all")
    public ResponseEntity<List<GetResponseDto>> ioWikiAll() {
        List<GetResponseDto> response = ioWikiService.getIoWikiList();
        if (response.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * @brief Get IoWiki items by a list of IDs.
     * @param listOfIds List of IoWiki item IDs.
     * @return ResponseEntity containing a list of GetResponseDto or NOT_FOUND status.
     */
    @GetMapping("/items/{listOfIds}")
    public ResponseEntity<List<GetResponseDto>> ioWikiList(
        @PathVariable List<String> listOfIds
    ) {
        List<GetResponseDto> responseList = ioWikiService.getIoWikiListById(
            listOfIds
        );

        // Debug
        for (GetResponseDto getResponseDto : responseList) {
            System.out.println(getResponseDto);
        }

        if (responseList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    /**
     * @brief Fetch default values into the database.
     * @return ResponseEntity with status OK or error status.
     */
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

    /**
     * @brief Fetch advanced values into the database.
     * @return ResponseEntity with status OK or error status.
     */
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

    /**
     * @brief Fetch Teltonika IoWiki data into a file.
     * @return ResponseEntity with status OK or error status.
     */
    @PostMapping("/fetch-teltonika-io-wiki-into-file")
    public ResponseEntity<String> fetchTeltonikaIoWikiIntoFile() {
        if (!scrapeTeltonikaIoWiki.fetchTeltonikaIoWikiIntoFile()) {
            return new ResponseEntity<>("Error", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * @brief Create a new IoWiki item.
     * @param ioWikiModel IoWikiModel object to be created.
     * @return ResponseEntity with status CREATED or CONFLICT.
     */
    @PostMapping("/create")
    public ResponseEntity<String> createIoWiki(
        @RequestBody IoWikiModel ioWikiModel
    ) {
        if (ioWikiService.saveIoWiki(ioWikiModel)) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    /**
     * @brief Update an existing IoWiki item.
     * @param id ID of the IoWiki item to be updated.
     * @param ioWikiModel IoWikiModel object with updated data.
     * @return ResponseEntity with status OK or NOT_FOUND.
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateIoWiki(
        @PathVariable String id,
        @RequestBody IoWikiModel ioWikiModel
    ) {
        if (ioWikiService.updateIoWiki(ioWikiModel)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * @brief Delete an IoWiki item.
     * @param id ID of the IoWiki item to be deleted.
     * @return ResponseEntity with status OK or NOT_FOUND.
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteIoWiki(@PathVariable String id) {
        if (ioWikiService.deleteIoWiki(id)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
