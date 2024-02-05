package org.gym.controller;


import org.gym.model.TrainingType;
import org.gym.service.TrainingTypeService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/trainingType", produces = MediaType.APPLICATION_JSON_VALUE)
public class TrainingTypeController {

    private final TrainingTypeService trainingTypeService;

    public TrainingTypeController(TrainingTypeService trainingTypeService) {
        this.trainingTypeService = trainingTypeService;
    }

    @GetMapping
    public ResponseEntity<List<TrainingType>> getTrainingTypes() {

        return ResponseEntity.ok(trainingTypeService.getAll());

    }
}
