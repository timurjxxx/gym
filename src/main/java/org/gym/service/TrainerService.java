package org.gym.service;

import org.gym.aspect.Authenticated;
import org.gym.dao.TraineeDAO;
import org.gym.dao.TrainerDAO;
import org.gym.dao.UserDAO;
import org.gym.model.Trainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.*;


@Service
public class TrainerService {

    private final TrainerDAO trainerDAO;
    private final UserService userService;
    private static final Logger LOGGER = LoggerFactory.getLogger(TrainerService.class);

    private final TraineeDAO traineeDAO;
    private final UserDAO userDAO;

    @Autowired
    public TrainerService(TrainerDAO trainerDAO, UserService userService, TraineeDAO traineeDAO, UserDAO userDAO) {
        this.trainerDAO = trainerDAO;
        this.userService = userService;
        this.traineeDAO = traineeDAO;
        this.userDAO = userDAO;
    }

    @Transactional
    public Trainer createTrainer(Trainer trainer, Long userId) {
        if (trainerDAO.existsTrainerByUser_Id(userId) || traineeDAO.existsTraineeByUser_Id(userId)) {
            throw new EntityExistsException("Trainer or Trainee with this user already exists");
        }
        trainer.setUser(userDAO.findById(userId).orElseThrow(EntityNotFoundException::new));
        LOGGER.info("Created new trainer ");
        return trainerDAO.save(trainer);
    }

    @Authenticated
    @Transactional(readOnly = true)
    public Trainer selectTrainerByUserName(String username, String password) {
        return trainerDAO.findTrainerByUserUserName(username).orElseThrow(EntityNotFoundException::new);
    }

    @Authenticated
    @Transactional
    public Trainer updateTrainer(@NotBlank String usernmae, @NotBlank String password, @Valid Trainer updatedTrainer) {
        Trainer trainer = trainerDAO.findTrainerByUserUserName(usernmae).orElseThrow(EntityNotFoundException::new);

        trainer.setSpecialization(updatedTrainer.getSpecialization());
        LOGGER.info("Updated trainer");
        return trainerDAO.save(trainer);
    }

    @Transactional(readOnly = true)
    public List<Trainer> getNotAssignedActiveTrainers(@NotBlank String username) {
        return trainerDAO.getNotAssignedActiveTrainers(username);
    }

    @Authenticated
    @Transactional
    public void deleteTrainerByUserName(@NotBlank String username, @NotBlank String password) {

        trainerDAO.findTrainerByUserUserName(username)
                .orElseThrow(EntityNotFoundException::new);
        LOGGER.info("Deleted trainer");
        trainerDAO.deleteTrainerByUserUserName(username);

    }


    @Authenticated
    @Transactional
    public void changeStatus(@NotBlank String username, @NotBlank String password) {

        Trainer trainer = trainerDAO.findTrainerByUserUserName(username).orElseThrow(EntityNotFoundException::new);
        trainer.getUser().setIsActive(userService.changeStatus(username));
        LOGGER.info("Changed status for trainer with ID: ");
        LOGGER.debug("Updated trainer details: ");
    }


    @Authenticated
    @Transactional
    public void changePassword(@NotBlank String userName, @NotBlank String password, @NotBlank String newPassword) {
        Trainer trainer = trainerDAO.findTrainerByUserUserName(userName)
                .orElseThrow(EntityNotFoundException::new);
        trainer.getUser().setPassword(userService.changePassword(userName, newPassword));
        trainerDAO.save(trainer);

        LOGGER.info("Changed password for trainer ");
        LOGGER.debug("Updated trainer details: ");

    }

}