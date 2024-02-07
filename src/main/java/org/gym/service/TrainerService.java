package org.gym.service;

import org.gym.dao.TrainerDAO;
import org.gym.exception.UserNotFoundException;
import org.gym.model.Trainer;
import org.gym.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public Trainer createTrainer(@Valid Trainer trainer, @Valid User user, @NotBlank String trainingTypeName) {
        trainer.setUser(userService.createUser(user));
        trainer.setSpecialization(trainingTypeService.findByTrainingName(trainingTypeName));
        return trainerDAO.save(trainer);
    }

    @Transactional(readOnly = true)
    public Trainer selectTrainerByUserName(@NotBlank String username) throws UserNotFoundException {
        LOGGER.info("Find trainer with username: {}", username);
        LOGGER.debug("Trainer username: {}", username);
        return trainerDAO.findTrainerByUserUserName(username).orElseThrow(() -> new UserNotFoundException("Trainer with username " + username + " is not found"));
    }

    @Transactional
    public Trainer updateTrainer(@NotBlank String username, @Valid Trainer updatedTrainer) throws UserNotFoundException {
        Trainer trainer = trainerDAO.findTrainerByUserUserName(username).orElseThrow(() -> new UserNotFoundException("Trainer with username " + username + " is not found"));
        trainer.setUser(userService.updateUser(updatedTrainer.getUser().getUserName(), updatedTrainer.getUser()));
        LOGGER.info("Updated trainer with username: {}", username);
        LOGGER.debug("Updated trainer details: {}", trainer);
        return trainerDAO.save(trainer);
    }

    @Transactional(readOnly = true)
    public List<Trainer> getNotAssignedActiveTrainers(@NotBlank String username) throws UserNotFoundException {
        LOGGER.info("Find trainee with username: {}", username);
        return trainerDAO.getNotAssignedActiveTrainers(username);
    }

    @Transactional
    public void changeStatus(@NotBlank String username) throws UserNotFoundException {
        Trainer trainer = trainerDAO.findTrainerByUserUserName(username).orElseThrow(() -> new UserNotFoundException("Trainer with username " + username + " is not found"));
        trainer.getUser().setIsActive(userService.changeStatus(username));
        LOGGER.info("Changed status for trainer username: {}", username);
    }

    @Transactional
    public void changePassword(@NotBlank String userName, @NotBlank String newPassword) throws UserNotFoundException {
        Trainer trainer = trainerDAO.findTrainerByUserUserName(userName)
                .orElseThrow(() -> new UserNotFoundException("Trainer with username " + userName + " is not found"));
        trainer.getUser().setPassword(userService.changePassword(userName, newPassword));
        trainerDAO.save(trainer);

        LOGGER.info("Changed password for trainer with username: {}", userName);
        LOGGER.debug("Updated trainer details: new password is {}", newPassword);
    }
}