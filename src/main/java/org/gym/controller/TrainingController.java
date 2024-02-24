package org.gym.controller;

import org.gym.aspect.Authenticated;
import org.gym.model.Training;
import org.gym.model.TrainingSearchCriteria;
import org.gym.service.TrainingService;
import org.gym.service.TrainingTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping(value = "/training", produces = MediaType.APPLICATION_JSON_VALUE)
public class TrainingController {

    private final TrainingService trainingService;

    private final TrainingTypeService trainingTypeService;

    @Autowired
    public TrainingController(TrainingService trainingService, TrainingTypeService trainingTypeService) {
        this.trainingService = trainingService;
        this.trainingTypeService = trainingTypeService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createTraining(@RequestBody Training training) {

        String trainerName = training.getTrainer().getUser().getUserName();
        String traineeName = training.getTrainee().getUser().getUserName();
        String trainingTypeName = training.getTrainingTypes().getTrainingTypeName();
        trainingService.addTraining(training, trainerName, traineeName, trainingTypeName);
        return ResponseEntity.ok().build();

    }

    @Authenticated
    @GetMapping(value = "/trainee/{username}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getTraineeTrainingsByCriteria(@PathVariable String username, @RequestHeader("password") String password, @RequestBody TrainingSearchCriteria criteria) {

        List<Training> trainings = trainingService.getTraineeTrainingsByCriteria(username, criteria);
        return ResponseEntity.ok(trainings.toString());

    }

    @Authenticated
    @GetMapping(value = "/trainer/{username}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getTrainerTrainingsByCriteria(@PathVariable String username, @RequestHeader("password") String password, @RequestBody TrainingSearchCriteria criteria) {

        List<Training> trainings = trainingService.getTrainerTrainingsByCriteria(username, criteria);
        return ResponseEntity.ok(trainings.toString());

    }


}
