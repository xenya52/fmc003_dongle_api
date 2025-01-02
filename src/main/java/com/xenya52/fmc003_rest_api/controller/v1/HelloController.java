package com.xenya52.fmc003_rest_api.controller.v1;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("debug")
public class HelloController {

    @GetMapping("/debug")
    public String index() {
        return "Greetings from Spring Boot!";
    }
}
