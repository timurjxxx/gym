package org.gym.controller;

import org.gym.aspect.Authenticated;
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

        Trainer createdTrainee = trainerService.createTrainer(trainer, trainer.getUser(), trainer.getSpecialization().getTrainingTypeName());
        return ResponseEntity.ok("Username :" + createdTrainee.getUser().getUserName() + " Password :" + createdTrainee.getUser().getPassword());

    }

    @Authenticated
    @GetMapping("/get_Trainer/{username}")
    public ResponseEntity<String> getTrainerProfile(@PathVariable String username,@RequestHeader("password") String password) {

        Trainer trainer = trainerService.selectTrainerByUserName(username);
        if (trainer != null) {
            return ResponseEntity.ok(trainer.toString());

        } else {
            throw new EntityNotFoundException("Not found");
        }

    }

    @Authenticated
    @PutMapping(value = "/update_Trainer/{username}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateTrainerProfile(@PathVariable String username, @RequestHeader("password") String password, @RequestBody Trainer trainer) {
        Trainer updatedTrainee = trainerService.updateTrainer(trainer.getUser().getUserName(), trainer);
        return ResponseEntity.ok(updatedTrainee.toString());
    }
    @Authenticated
    @GetMapping("/get_Trainers/{userName}")
    public ResponseEntity<String> getNotAssignedActiveTrainers(@PathVariable String userName,@RequestHeader("password") String password) {

        List<Trainer> trainer = trainerService.getNotAssignedActiveTrainers(userName);
        if (trainer != null) {
            return ResponseEntity.ok(trainer.toString());

        } else {
            throw new EntityNotFoundException("Not found");
        }

    }

    @Authenticated
    @PatchMapping(value = "/change_status/{username}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> activateDeactivateTrainer(@PathVariable String username,@RequestHeader("password") String password, @RequestBody Map<String, String> jsonData) {
        trainerService.changeStatus(jsonData.get("username"));
        return ResponseEntity.ok().build();
    }

}
