package com.xenya52.fmc003_rest_api.controller.v1;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/dongle")
public class DongleController {

    @GetMapping("/items/all")
    public ResponseEntity<List<GetResponseDto>> dongleAll() {
        List<GetResponseDto> response = dongleService.getDongleList();
        if (response == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
