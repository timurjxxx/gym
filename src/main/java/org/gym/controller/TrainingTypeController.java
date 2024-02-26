package org.gym.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api(value = "Training Type Controller", description = "Operations related to training types")
public class TrainingTypeController {

    private final TrainingTypeService trainingTypeService;

    public TrainingTypeController(TrainingTypeService trainingTypeService) {
        this.trainingTypeService = trainingTypeService;
    }

    @GetMapping
    @ApiOperation(value = "Get all training types", response = ResponseEntity.class)
    public ResponseEntity<List<TrainingType>> getTrainingTypes() {

        return ResponseEntity.ok(trainingTypeService.getAll());

    }
}
