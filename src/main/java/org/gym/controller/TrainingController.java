package org.gym.controller;

import org.gym.model.Training;
import org.gym.service.TrainingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/training", produces = MediaType.APPLICATION_JSON_VALUE)
public class TrainingController {

    private final TrainingService trainingService;

    @Autowired
    public TrainingController(TrainingService trainingService) {
        this.trainingService = trainingService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createTraining(@RequestBody Training training) {

        String trainerName = training.getTrainer().getUser().getUserName();
        String traineeName = training.getTrainee().getUser().getUserName();

        Training training1 = trainingService.addTraining(training, trainerName, traineeName, 1L);
        return ResponseEntity.ok(training.toString());

    }

    @GetMapping(value = "/trainee/{username}")
    public ResponseEntity<List<Training>> getTraineeTrainingsByCriteria() {

        return null;
    }

    @GetMapping(value = "/trainer/{username}")
    public ResponseEntity<List<Training>> getTrainerTrainingsByCriteria() {
        return null;

    }


}
