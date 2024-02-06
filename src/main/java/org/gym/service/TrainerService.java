package org.gym.service;

import org.gym.dao.TrainerDAO;
import org.gym.model.Trainer;
import org.gym.model.TrainingType;
import org.gym.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.*;


@Service
public class TrainerService {

    private final TrainerDAO trainerDAO;
    private final UserService userService;
    private static final Logger LOGGER = LoggerFactory.getLogger(TrainerService.class);

    private final TrainingTypeService trainingTypeService;

    @Autowired
    public TrainerService(TrainerDAO trainerDAO, UserService userService, TrainingTypeService trainingTypeService) {
        this.trainerDAO = trainerDAO;
        this.userService = userService;
        this.trainingTypeService = trainingTypeService;
    }

    @Transactional
    public Trainer createTrainer(Trainer trainer, User user, String  trainingTypeName) {
        trainer.setUser(userService.createUser(user));
        trainer.setSpecialization(trainingTypeService.findByTrainingName(trainingTypeName));
        return trainerDAO.save(trainer);
    }

    @Transactional(readOnly = true)
    public Trainer selectTrainerByUserName(String username) {
        LOGGER.info("Find trainer with username:{}", username);
        LOGGER.debug("Trainer username: {}", username);
        return trainerDAO.findTrainerByUserUserName(username).orElseThrow(() -> new EntityNotFoundException("Trainer with username " + username + "is not found "));
    }

    @Transactional
    public Trainer updateTrainer(@NotBlank String username, @Valid Trainer updatedTrainer) {
        Trainer trainer = trainerDAO.findTrainerByUserUserName(username).orElseThrow(() -> new EntityNotFoundException("Trainer with username " + username + "is not found "));
        trainer.setUser(userService.updateUser(updatedTrainer.getUser().getUserName(), updatedTrainer.getUser()));
        LOGGER.info("Updated trainer with username: {}", username);
//        LOGGER.debug("Updated trainer details: {}", updatedTrainer);
        return trainerDAO.save(trainer);
    }

    @Transactional(readOnly = true)
    public List<Trainer> getNotAssignedActiveTrainers(@NotBlank String username) {
        LOGGER.info("Find trainee with username: {}", username);
        return trainerDAO.getNotAssignedActiveTrainers(username);
    }




    @Transactional
    public void changeStatus(@NotBlank String username) {

        Trainer trainer = trainerDAO.findTrainerByUserUserName(username).orElseThrow(() -> new EntityNotFoundException("Trainer with username " + username + "is not found "));
        trainer.getUser().setIsActive(userService.changeStatus(username));
        LOGGER.info("Changed status for trainer username : {}", username);
    }


    @Transactional
    public void changePassword(@NotBlank String userName, @NotBlank String password, @NotBlank String newPassword) {
        Trainer trainer = trainerDAO.findTrainerByUserUserName(userName)
                .orElseThrow(() -> new EntityNotFoundException("Trainer with username " + userName + "is not found "));
        trainer.getUser().setPassword(userService.changePassword(userName, newPassword));
        trainerDAO.save(trainer);

        LOGGER.info("Changed password for trainer with username: {}", userName);
        LOGGER.debug("Updated trainer details: old password is {}, new password is {} ", password, newPassword);

    }

}