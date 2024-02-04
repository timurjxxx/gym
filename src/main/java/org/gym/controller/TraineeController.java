package org.gym.controller;

import org.gym.aspect.Authenticated;
import org.gym.dao.TraineeDAO;
import org.gym.model.Trainee;
import org.gym.model.User;
import org.gym.service.TraineeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(value = "/trainee", produces = MediaType.APPLICATION_JSON_VALUE)
public class TraineeController {

    private final TraineeService traineeService;

    @Autowired
    public TraineeController(TraineeService traineeService) {
        this.traineeService = traineeService;
    }


    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createTrainee(@RequestBody Map<String, String> jsonData) {
        Trainee trainee = new Trainee();
        User user = new User();
        user.setFirstName(jsonData.get("firstName"));
        user.setLastName(jsonData.get("lastName"));
        trainee.setAddress(jsonData.get("address"));
        user.setIsActive(Boolean.valueOf(jsonData.get("isActive")));

        LocalDate date = LocalDate.parse(jsonData.get("dateOfBirth"));
        trainee.setDateOfBirth(date);

        Trainee createdTrainee = traineeService.createTrainee(trainee, user);
        return ResponseEntity.ok("Username :" + createdTrainee.getUser().getUserName() + " Password :" + createdTrainee.getUser().getPassword());
    }

    @Authenticated
    @GetMapping("/login/{username}/{password}")
    public ResponseEntity<Void> login(@PathVariable String username, @PathVariable String password) {

        Trainee trainee = traineeService.selectTraineeByUserName(username);
        if (trainee != null) {
            return ResponseEntity.ok().build();

        } else {
            throw new EntityNotFoundException("Not found");
        }

    }

}
