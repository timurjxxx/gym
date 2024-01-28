package org.gym.service;

import org.gym.aspect.Authenticated;
import org.gym.dao.TraineeDAO;
import org.gym.dao.TrainerDAO;
import org.gym.dao.UserDAO;
import org.gym.model.Trainee;
import org.gym.model.Trainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Service
public class TraineeService {

    private final UserService userService;
    private final UserDAO userDAO;

    private final TraineeDAO traineeDAO;
    private final TrainerDAO trainerDAO;

    private static final Logger LOGGER = LoggerFactory.getLogger(TraineeService.class);

    @Autowired
    public TraineeService(TraineeDAO traineeDAO, UserService userService, UserDAO userDAO, TrainerDAO trainerDAO) {
        this.traineeDAO = traineeDAO;
        this.userService = userService;
        this.userDAO = userDAO;
        this.trainerDAO = trainerDAO;
    }

    @Transactional
    public Trainee createTrainee(@Valid Trainee trainee, @NotNull Long userId) {
        if (trainerDAO.existsTrainerByUser_Id(userId) || traineeDAO.existsTraineeByUser_Id(userId)) {
            throw new EntityExistsException("Trainer or Trainee with this user already exists");
        }
        trainee.setUser(userDAO.findById(userId).orElseThrow(EntityNotFoundException::new));

        LOGGER.info("Created new trainee : ");
        LOGGER.debug("Created trainee details:");

        return traineeDAO.save(trainee);
    }

    @Authenticated
    @Transactional(readOnly = true)
    public Trainee selectTraineeByUserName(@NotBlank String username, @NotBlank String password) {
        return traineeDAO.findTraineeByUserUserName(username).orElseThrow(EntityNotFoundException::new);
    }

    @Authenticated
    @Transactional
    public Trainee updateTrainee(@NotBlank String username, @NotBlank String password, @Valid Trainee updatedTrainee) {
        Trainee trainee = traineeDAO.findTraineeByUserUserName(username).orElseThrow(EntityNotFoundException::new);
        trainee.setDateOfBirth(updatedTrainee.getDateOfBirth());
        trainee.setAddress(updatedTrainee.getAddress());

        LOGGER.info("Updated trainee : ");
        LOGGER.debug("Updated trainee details: ");
        return traineeDAO.save(trainee);
    }

    @Authenticated
    @Transactional
    public void deleteTraineeByUserName(@NotBlank String username, @NotBlank String password) {
        traineeDAO.findTraineeByUserUserName(username)
                .orElseThrow(EntityNotFoundException::new);

        traineeDAO.deleteTraineeByUserUserName(username);
        LOGGER.info("Deleted trainee ");
        LOGGER.debug("Deleted trainee details:");
    }

    @Authenticated
    @Transactional
    public Trainee updateTraineeTrainersList(@NotBlank String username, @NotBlank String password, @NotBlank Set<Trainer> updatedList) {
        Trainee trainee = traineeDAO.findTraineeByUserUserName(username).orElseThrow(EntityNotFoundException::new);

        trainee.setTrainers(updatedList);
        LOGGER.info("Updated trainers list for trainee ");
        LOGGER.debug("Updated trainee details: ");

        return traineeDAO.save(trainee);

    }

    @Authenticated
    @Transactional
    public void changePassword(@NotBlank String username, @NotBlank String password, @NotBlank String newPassword) {
        Trainee trainee = traineeDAO.findTraineeByUserUserName(username).orElseThrow(EntityNotFoundException::new);
        trainee.getUser().setPassword(userService.changePassword(username, newPassword));

        LOGGER.info("Changed password for trainee with ID: {}", trainee.getId());
        LOGGER.debug("Updated trainee details: ");
    }

    @Transactional
    public void changeStatus(@NotBlank String username, @NotBlank String password) {

        Trainee trainee = traineeDAO.findTraineeByUserUserName(username).orElseThrow(EntityNotFoundException::new);
        trainee.getUser().setIsActive(userService.changeStatus(username));
        LOGGER.info("Changed status for trainee with ID: {}", trainee.getId());
        LOGGER.debug("Updated trainee details: ");
    }

}