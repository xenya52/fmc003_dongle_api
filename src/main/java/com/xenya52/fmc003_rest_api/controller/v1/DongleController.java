package com.xenya52.fmc003_rest_api.controller.v1;

import com.xenya52.fmc003_rest_api.entity.dto.GetResponseDto;
import com.xenya52.fmc003_rest_api.entity.model.IoDongleModel;
import com.xenya52.fmc003_rest_api.service.IoDongleByFile;
import com.xenya52.fmc003_rest_api.service.IoDongleService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/dongle")
public class DongleController {

    // Attributes
    @Autowired
    private IoDongleService dongleService;

    // Methods

    @GetMapping("/items/{listOfIds}")
    public ResponseEntity<List<GetResponseDto>> dongleById(
        @PathVariable List<String> listOfIds
    ) {
        List<GetResponseDto> responseList = new ArrayList<>();

        for (String id : listOfIds) {
            GetResponseDto response = dongleService.getIoDongleById(id);
            responseList.add(response);
        }
        if (responseList.getFirst() == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    @GetMapping("/fetch-file")
    public ResponseEntity<String> fetchFile() {
        try {
            // Debug
            System.out.println("DEBUG - before fetchFile");

            IoDongleByFile dongleByFile = new IoDongleByFile();

            // Debug
            System.out.println(dongleByFile.getDongleList());
            System.out.println("DEBUG - after fetchFile");

            for (IoDongleModel dongleModel : dongleByFile.getDongleList()) {
                // Debug
                System.out.println("DEBUG");
                System.out.println(dongleModel.toJson());

                dongleService.saveIoDongle(dongleModel);
            }
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/items/all")
    public ResponseEntity<List<GetResponseDto>> dongleAll() {
        List<GetResponseDto> response = dongleService.getIoDongleList();
        // Todo
        // Implement edge cases, at the moment Im getting a error if i try to index the first element of a empty list
        /**
        if (response.getFirst()== null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        */
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
