package com.example.demo;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @RequestMapping("/greeting")
    public ResponseEntity greeting() {
        return ResponseEntity.accepted().body("ahoojo");
    }
}
