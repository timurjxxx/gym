package org.gym.service;

import org.gym.dao.TrainerDAO;
import org.gym.model.Trainee;
import org.gym.model.Trainer;
import org.gym.model.Training;
import org.gym.model.User;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.List;


@Service
public class TrainerService {

    private final TrainerDAO trainerDAO;
    private final UserService userService;

    @Autowired
    public TrainerService(TrainerDAO trainerDAO, UserService userService) {
        this.trainerDAO = trainerDAO;
        this.userService = userService;
    }

    public Trainer createTrainer(Trainer trainer) {
        trainer.setPassword(userService.generatePassword());
        trainer.setUserName(userService.generateUsername(trainer.getFirstName(), trainer.getLastName()));
        return trainerDAO.save(trainer);
    }

    public Trainer selectTrainerByUserName(String username) {
        return trainerDAO.findTrainerByUserName(username).orElseThrow(EntityNotFoundException::new);
    }

    public Trainer updateTrainer(String username, Trainer updatedTrainer) {
        Trainer trainer = selectTrainerByUserName(username);
        updatedTrainer.setId(trainer.getId());
        return trainerDAO.save(updatedTrainer);
    }
    public List<Training> getTrainerTrainingList(String username) {
        Trainer trainer = selectTrainerByUserName(username);
        return trainer.getTraineeTrainings();
    }


}