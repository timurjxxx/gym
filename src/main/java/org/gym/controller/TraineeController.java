package org.gym.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api")
public class TraineeController {

    @GetMapping
    public ResponseEntity<String> get(){
        String s = "HELlo";
        return ResponseEntity.ok(s);
    }
}
