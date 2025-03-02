package com.xenya52.fmc003_rest_api.controller.v2;

import com.xenya52.fmc003_rest_api.entity.dto.GetResponseDto;
import com.xenya52.fmc003_rest_api.entity.model.IoDongleModel;
import com.xenya52.fmc003_rest_api.service.IoDongle.IoDongleByFile;
import com.xenya52.fmc003_rest_api.service.IoDongle.IoDongleService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v2/dongle")
@ConditionalOnProperty(name = "controller.version", havingValue = "v2")
public class DongleController {

    // Attributes
    @Autowired
    private IoDongleService dongleService;

    @Autowired
    private IoDongleByFile fileIoDongle;

    // Methods
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

    @GetMapping("/items/all")
    public ResponseEntity<List<GetResponseDto>> dongleAll() {
        List<GetResponseDto> response = dongleService.getIoDongleList();

        if (response.getFirst() == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/fetch-default-values-into-db")
    public ResponseEntity<String> fetchFile() {
        List<IoDongleModel> dongleList;
        dongleList = fileIoDongle.dongleModelsByFile();

        if (dongleList == null) {
            return new ResponseEntity<>("Error", HttpStatus.NOT_FOUND);
        }

        if (!dongleService.saveIoDongleList(dongleList)) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
