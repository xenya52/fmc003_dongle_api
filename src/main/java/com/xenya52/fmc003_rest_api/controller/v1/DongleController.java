package com.xenya52.fmc003_rest_api.controller.v1;

import com.xenya52.fmc003_rest_api.entity.dto.GetResponseDto;
import com.xenya52.fmc003_rest_api.entity.model.IoDongleModel;
import com.xenya52.fmc003_rest_api.service.IoDongleByFile;
import com.xenya52.fmc003_rest_api.service.IoDongleService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/dongle")
public class DongleController {

    // Attributes
    @Autowired
    private IoDongleService dongleService;

    // Methods

    @GetMapping("/items/{id}")
    public ResponseEntity<GetResponseDto> dongleById(String id) {
        GetResponseDto response = dongleService.getIoDongleById(id);
        if (response == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/fetch-file")
    public ResponseEntity<String> fetchFile() {
        try {
            IoDongleByFile dongleByFile = new IoDongleByFile();

            // Debug
            System.out.println("DEBUG");
            System.out.println(dongleByFile.getDongleList());
            System.out.println("DEBUG");

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
        if (response == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
