package org.gym.controller;

import io.swagger.annotations.ApiOperation;
import org.gym.aspect.Authenticated;
import org.gym.model.Trainee;
import org.gym.service.TraineeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
@RestController
@RequestMapping(value = "/trainee", produces = MediaType.APPLICATION_JSON_VALUE)
public class TraineeController {

    private final TraineeService traineeService;

    @Autowired
    public TraineeController(TraineeService traineeService) {
        this.traineeService = traineeService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createTrainee(@RequestBody Trainee trainee) {

        Trainee createdTrainee = traineeService.createTrainee(trainee, trainee.getUser());
        return ResponseEntity.ok("Username :" + createdTrainee.getUser().getUserName() + " Password :" + createdTrainee.getUser().getPassword());

    }

    @Authenticated
    @GetMapping("/login/{username}/{password}")
    @ApiOperation("Create a new user")
    public ResponseEntity<Void> login(@PathVariable String username, @PathVariable String password) {

        Trainee trainee = traineeService.selectTraineeByUserName(username);
        if (trainee != null) {
            return ResponseEntity.ok().build();

        } else {
            throw new EntityNotFoundException("Not found");
        }

    }

    @GetMapping("/{username}")
    public ResponseEntity<String> getTraineeProfile(@PathVariable String username) {

        Trainee trainee = traineeService.selectTraineeByUserName(username);
        if (trainee != null) {
            return ResponseEntity.ok(trainee.toString());

        } else {
            throw new EntityNotFoundException("Not found");
        }

    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateTraineeProfile(@RequestBody Trainee trainee) {
        Trainee updatedTrainee = traineeService.updateTrainee(trainee.getUser().getUserName(), trainee);
        return ResponseEntity.ok(updatedTrainee.toString());
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<Void> deleteTraineeProfile(@PathVariable String username) {
        traineeService.deleteTraineeByUserName(username);
        return ResponseEntity.ok().build();
    }

//    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<String> updateTraineeTrainersList(@RequestBody Map<String, Object> requestBody) {
//        String username = (String) requestBody.get("username");
//        Set<Map<String, Object>> trainersList = (Set<Map<String, Object>>) requestBody.get("trainers");
//
//
//        Trainee updatedTrainee = traineeService.updateTraineeTrainersList(username, trainersList);
//        return ResponseEntity.ok(updatedTrainee.toString());
//    }

}
