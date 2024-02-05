package org.gym.controller;

import org.gym.model.Trainer;
import org.gym.service.TrainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/trainer", produces = MediaType.APPLICATION_JSON_VALUE)
public class TrainerController {


    private final TrainerService trainerService;

    @Autowired
    public TrainerController(TrainerService trainerService) {
        this.trainerService = trainerService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createTrainer(@RequestBody Trainer trainer) {

        Trainer createdTrainee = trainerService.createTrainer(trainer, trainer.getUser(), trainer.getSpecialization());
        return ResponseEntity.ok("Username :" + createdTrainee.getUser().getUserName() + " Password :" + createdTrainee.getUser().getPassword());

    }


    @GetMapping("/{username}")
    public ResponseEntity<String> getTrainerProfile(@PathVariable String username) {

        Trainer trainer = trainerService.selectTrainerByUserName(username);
        if (trainer != null) {
            return ResponseEntity.ok(trainer.toString());

        } else {
            throw new EntityNotFoundException("Not found");
        }

    }


    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateTrainerProfile(@RequestBody Trainer trainer) {
        Trainer updatedTrainee = trainerService.updateTrainer(trainer.getUser().getUserName(), trainer);
        return ResponseEntity.ok(updatedTrainee.toString());
    }

    @GetMapping("/list/{userName}")
    public ResponseEntity<String> getNotAssignedActiveTrainers(@PathVariable String userName) {

        List<Trainer> trainer = trainerService.getNotAssignedActiveTrainers(userName);
        if (trainer != null) {
            return ResponseEntity.ok(trainer.toString());

        } else {
            throw new EntityNotFoundException("Not found");
        }

    }

    @PatchMapping(value = "/change_status", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> activateDeactivateTrainee(@RequestBody Map<String, String> jsonData) {
        trainerService.changeStatus(jsonData.get("username"));
        return ResponseEntity.ok().build();
    }

}
