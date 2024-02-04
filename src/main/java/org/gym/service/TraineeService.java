package org.gym.service;

import org.gym.aspect.Authenticated;
import org.gym.dao.TraineeDAO;
import org.gym.dao.TrainerDAO;
import org.gym.dao.UserDAO;
import org.gym.model.Trainee;
import org.gym.model.Trainer;
import org.gym.model.User;
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
public class    TraineeService {

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
    public Trainee createTrainee(@Valid Trainee trainee, @NotNull User user) {
        trainee.setUser(userService.createUser(user));

        return traineeDAO.save(trainee);
    }

    @Transactional(readOnly = true)
    public Trainee selectTraineeByUserName(@NotBlank String username) {
        return traineeDAO.findTraineeByUserUserName(username).orElseThrow(() -> new EntityNotFoundException("Trainee with username:" + username + "is not found"));
    }

    @Transactional
    public Trainee updateTrainee(@NotBlank String username, @Valid Trainee updatedTrainee) {
        Trainee trainee = traineeDAO.findTraineeByUserUserName(username).orElseThrow(() -> new EntityNotFoundException("Trainee with username:" + username + "is not found"));
        trainee.setDateOfBirth(updatedTrainee.getDateOfBirth());
        trainee.setAddress(updatedTrainee.getAddress());
        return traineeDAO.save(trainee);
    }

    @Transactional
    public void deleteTraineeByUserName(@NotBlank String username) {
        traineeDAO.findTraineeByUserUserName(username)
                .orElseThrow(() -> new EntityNotFoundException("Trainee with username:" + username + "is not found"));

        traineeDAO.deleteTraineeByUserUserName(username);
        LOGGER.info("Deleted trainee with username: {}", username);
        LOGGER.debug("Deleted trainee with username {}  ", username);
    }

    @Transactional
    public Trainee updateTraineeTrainersList(@NotBlank String username,  @NotBlank Set<Trainer> updatedList) {
        Trainee trainee = traineeDAO.findTraineeByUserUserName(username).orElseThrow(() -> new EntityNotFoundException("Trainee with username:" + username + "is not found"));

        trainee.setTrainers(updatedList);
        LOGGER.info("Updated trainers list for trainee with username {}", username);
        LOGGER.debug("Updated trainee details: new trainers list {}", updatedList);

        return traineeDAO.save(trainee);

    }

    @Transactional
    public void changePassword(@NotBlank String username, @NotBlank String newPassword) {
        Trainee trainee = traineeDAO.findTraineeByUserUserName(username).orElseThrow(() -> new EntityNotFoundException("Trainee with username:" + username + "is not found"));
        trainee.getUser().setPassword(userService.changePassword(username, newPassword));

        LOGGER.info("Changed password for trainee with username: {}", username);
    }

    @Transactional
    public void changeStatus(@NotBlank String username, @NotBlank String password) {

        Trainee trainee = traineeDAO.findTraineeByUserUserName(username).orElseThrow(() -> new EntityNotFoundException("Trainee with username:" + username + "is not found"));
        trainee.getUser().setIsActive(userService.changeStatus(username));
        LOGGER.info("Changed status for trainee with ID: {}", trainee.getId());
    }

}