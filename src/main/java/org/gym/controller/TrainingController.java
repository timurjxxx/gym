package org.gym.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api(value = "Training Controller", description = "Operations related to training management")
public class TrainingController {

    private final TrainingService trainingService;

    private final TrainingTypeService trainingTypeService;

    @Autowired
    public TrainingController(TrainingService trainingService, TrainingTypeService trainingTypeService) {
        this.trainingService = trainingService;
        this.trainingTypeService = trainingTypeService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Create a new training", response = ResponseEntity.class)
    public ResponseEntity<Void> createTraining(@RequestBody Training training) {

        String trainerName = training.getTrainer().getUser().getUserName();
        String traineeName = training.getTrainee().getUser().getUserName();
        String trainingTypeName = training.getTrainingTypes().getTrainingTypeName();
        trainingService.addTraining(training, trainerName, traineeName, trainingTypeName);
        return ResponseEntity.ok().build();

    }

    @Authenticated
    @GetMapping(value = "/trainee", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get trainee trainings by criteria", response = ResponseEntity.class)
    public ResponseEntity<String> getTraineeTrainingsByCriteria(@RequestHeader("username") String username, @RequestHeader("password") String password, @RequestBody TrainingSearchCriteria criteria) {

        List<Training> trainings = trainingService.getTraineeTrainingsByCriteria(username, criteria);
        return ResponseEntity.ok(trainings.toString());

    }

    @Authenticated
    @GetMapping(value = "/trainer", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get trainer trainings by criteria", response = ResponseEntity.class)
    public ResponseEntity<String> getTrainerTrainingsByCriteria(@RequestHeader("username") String username, @RequestHeader("password") String password, @RequestBody TrainingSearchCriteria criteria) {

        List<Training> trainings = trainingService.getTrainerTrainingsByCriteria(username, criteria);
        return ResponseEntity.ok(trainings.toString());

    }


}
