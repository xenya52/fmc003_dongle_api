package com.xenya52.fmc003_rest_api.controller.v2;

import com.xenya52.fmc003_rest_api.entity.dto.GetResponseDto;
import com.xenya52.fmc003_rest_api.entity.model.teltonika.dongle.TeltonikaFmc003DongleModel;
import com.xenya52.fmc003_rest_api.service.teltonika.dongle.teltonikaFmc003DongleFileService;
import com.xenya52.fmc003_rest_api.service.teltonika.dongle.TeltonikaFmc003DongleService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller class for handling IoDongle related requests.
 */
@RestController
@RequestMapping("/v2/dongle")
@ConditionalOnProperty(name = "controller.version", havingValue = "v2")
public class DongleController {

    // Attributes
    @Autowired
    private TeltonikaFmc003DongleService dongleService;

    @Autowired
    private teltonikaFmc003DongleFileService fileIoDongle;

    /**
     * Retrieves a list of IoDongle items by their IDs.
     *
     * @param listOfIds List of IoDongle IDs.
     * @return ResponseEntity containing the list of GetResponseDto objects and HTTP status.
     */
    @GetMapping("/items/{listOfIds}")
    public ResponseEntity<List<GetResponseDto>> dongleById(
        @PathVariable List<String> listOfIds
    ) {
        List<GetResponseDto> responseList =
            dongleService.getIoDongleListBySpecificId(listOfIds);

        if (responseList.getFirst() == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    /**
     * Retrieves all IoDongle items.
     *
     * @return ResponseEntity containing the list of GetResponseDto objects and HTTP status.
     */
    @GetMapping("/items/all")
    public ResponseEntity<List<GetResponseDto>> dongleAll() {
        List<GetResponseDto> response = dongleService.getIoDongleList();

        if (response.getFirst() == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Fetches default IoDongle values from a file and saves them into the database.
     *
     * @return ResponseEntity containing a status message and HTTP status.
     */
    @PostMapping("/fetch-default-values-into-db")
    public ResponseEntity<String> fetchFile() {
        List<TeltonikaFmc003DongleModel> dongleList;
        dongleList = fileIoDongle.dongleModelsByFile();

        if (dongleList == null) {
            return new ResponseEntity<>("Error", HttpStatus.NOT_FOUND);
        }

        if (!dongleService.saveIoDongleList(dongleList)) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/generate-dongles-with-none-values-into-db")
    public ResponseEntity<String> generateDonglesWithNoneValues(
        @RequestParam int dongleAmount,
        @RequestParam int wikiAmount
    ) {
        if (
            !dongleService.generateDonglesWithNoneValuesAndSaveIntoDB(
                dongleAmount,
                wikiAmount
            )
        ) {
            return new ResponseEntity<>("Error", HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Creates a new IoDongle item.
     *
     * @param dongleModel The IoDongleModel object to be created.
     * @return ResponseEntity containing the created IoDongleModel object and HTTP status.
     */
    @PostMapping("/create")
    public ResponseEntity<TeltonikaFmc003DongleModel> createDongle(
        @RequestBody TeltonikaFmc003DongleModel dongleModel
    ) {
        TeltonikaFmc003DongleModel createdDongle = dongleService.saveIoDongle(dongleModel);
        return new ResponseEntity<>(createdDongle, HttpStatus.CREATED);
    }

    /**
     * Updates an existing IoDongle item.
     *
     * @param dongleModel The IoDongleModel object to be updated.
     * @return ResponseEntity containing the updated IoDongleModel object and HTTP status.
     */
    @PostMapping("/update")
    public ResponseEntity<TeltonikaFmc003DongleModel> updateDongle(
        @RequestBody TeltonikaFmc003DongleModel dongleModel
    ) {
        TeltonikaFmc003DongleModel updatedDongle = dongleService.updateIoDongle(dongleModel);
        if (updatedDongle == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(updatedDongle, HttpStatus.OK);
    }

    /**
     * Deletes an IoDongle item by its ID.
     *
     * @param id The ID of the IoDongle item to be deleted.
     * @return ResponseEntity containing HTTP status.
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteDongle(@PathVariable String id) {
        boolean isDeleted = dongleService.deleteIoDongle(id);
        if (!isDeleted) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
