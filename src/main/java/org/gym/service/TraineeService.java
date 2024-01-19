package org.gym.service;

import lombok.AllArgsConstructor;
import org.gym.dao.TraineeDAO;
import org.gym.model.Trainee;
import org.gym.model.Trainer;
import org.gym.model.Training;
import org.gym.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Service
public class TraineeService {

    private final UserService userService;
    private final TraineeDAO traineeDAO;

    private static final Logger LOGGER = LoggerFactory.getLogger(TraineeService.class);

    @Autowired
    public TraineeService(TraineeDAO traineeDAO, UserService userService) {
        this.traineeDAO = traineeDAO;
        this.userService = userService;
    }
    @Transactional
    public Trainee createTrainee(@Valid Trainee trainee, @NotNull Long userId) {
        LOGGER.info("Creating trainee with user ID: {}", userId);
        LOGGER.debug("Trainee details: {}", trainee);
        trainee.setUser(userService.findUserById(userId));
        return traineeDAO.save(trainee);
    }
    @Transactional
    public Trainee createTrainee(@Valid Trainee trainee, @Valid User user) {
        LOGGER.info("Creating trainee with user details: {}", user);
        LOGGER.debug("Trainee details: {}", trainee);
        trainee.setUser(userService.createUser(user));
        return traineeDAO.save(trainee);

    }
    @Transactional(readOnly = true)
    public Trainee selectTraineeByUsername(@NotBlank String username) {
        LOGGER.info("Selecting trainee by username: {}", username);
        return traineeDAO.findTraineeByUserUserName(username).orElseThrow(EntityNotFoundException::new);

    }
    @Transactional
    public Trainee updateTrainee(@NotBlank String username, @Valid Trainee updatedTrainee) {
        LOGGER.info("Updating trainee with username: {}", username);
        LOGGER.debug("Updated trainee details: {}", updatedTrainee);
        Trainee trainee = selectTraineeByUsername(username);
        updatedTrainee.setId(trainee.getId());
        return traineeDAO.save(updatedTrainee);
    }

    @Transactional
    public void deleteTraineeByUserName(@NotBlank String username) {
        LOGGER.info("Deleting trainee by username: {}", username);

        Trainee trainee = selectTraineeByUsername(username);
        traineeDAO.deleteById(trainee.getId());
    }
    @Transactional(readOnly = true)
    public List<Training> getTraineeTrainingList(@NotBlank String username) {
        LOGGER.info("Getting training list for trainee with username: {}", username);

        Trainee trainee = selectTraineeByUsername(username);
        return trainee.getTraineeTrainings();
    }
    @Transactional
    public List<Training> updateTraineeTrainingList(@NotBlank String username, @Valid List<Training> updatedList) {
        LOGGER.info("Updating training list for trainee with username: {}", username);

        Trainee trainee = selectTraineeByUsername(username);
        trainee.setTraineeTrainings(updatedList);
        traineeDAO.save(trainee);
        return trainee.getTraineeTrainings();

    }

    public String changePassword(@NotBlank String username, @NotBlank String newPassword) {
        LOGGER.info("Activating trainee with username: {}", username);

        Trainee trainee = selectTraineeByUsername(username);
        trainee.getUser().setPassword(userService.changePassword(username, newPassword));
        return trainee.getUser().getPassword();
    }

    public String activeTrainee(@NotBlank String username) {
        LOGGER.info("Activating trainee with username: {}", username);

        Trainee trainee = selectTraineeByUsername(username);
        trainee.getUser().setIsActive(userService.active(username));
        return "Activated";
    }

    public String deActiveTrainee(@NotBlank String username) {
        LOGGER.info("Deactivating trainee with username: {}", username);
        Trainee trainee = selectTraineeByUsername(username);
        trainee.getUser().setIsActive(userService.deActive(username));
        return "deActivated";
    }
}