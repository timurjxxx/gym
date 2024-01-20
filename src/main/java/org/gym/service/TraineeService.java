package org.gym.service;

import org.gym.aspect.Authenticated;
import org.gym.dao.TraineeDAO;
import org.gym.model.Trainee;
import org.gym.model.Training;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Authenticated
    @Transactional(readOnly = true)
    public Trainee selectTraineeByUsername(@NotBlank String username, @NotBlank String password) {
        LOGGER.info("Selecting trainee by username: {}", username);
        return traineeDAO.findTraineeByUserUserName(username).orElseThrow(EntityNotFoundException::new);
    }

    @Authenticated
    @Transactional
    public Trainee updateTrainee(@NotBlank String username, @NotBlank String password, @Valid Trainee updatedTrainee) {
        LOGGER.info("Updating trainee with username: {}", username);
        LOGGER.debug("Updated trainee details: {}", updatedTrainee);
        Trainee trainee = traineeDAO.findTraineeByUserUserName(username).orElseThrow(EntityNotFoundException::new);
        updatedTrainee.setId(trainee.getId());
        return traineeDAO.save(updatedTrainee);
    }

    @Authenticated
    @Transactional
    public void deleteTraineeByUserName(@NotBlank String username, @NotBlank String password) {
        LOGGER.info("Deleting trainee by username: {}", username);

        Trainee trainee = traineeDAO.findTraineeByUserUserName(username).orElseThrow(EntityNotFoundException::new);
        traineeDAO.deleteById(trainee.getId());
    }
    @Authenticated

    @Transactional(readOnly = true)
    public List<Training> getTraineeTrainingList(@NotBlank String username, @NotBlank String password) {
        LOGGER.info("Getting training list for trainee with username: {}", username);

        Trainee trainee = traineeDAO.findTraineeByUserUserName(username).orElseThrow(EntityNotFoundException::new);
        return trainee.getTraineeTrainings();
    }
    @Authenticated
    @Transactional
    public List<Training> updateTraineeTrainingList(@NotBlank String username,@NotBlank String password, @Valid List<Training> updatedList) {
        LOGGER.info("Updating training list for trainee with username: {}", username);

        Trainee trainee = traineeDAO.findTraineeByUserUserName(username).orElseThrow(EntityNotFoundException::new);
        trainee.setTraineeTrainings(updatedList);
        traineeDAO.save(trainee);
        return trainee.getTraineeTrainings();

    }
    @Authenticated

    public String changePassword(@NotBlank String username,String password, @NotBlank String newPassword) {
        LOGGER.info("Activating trainee with username: {}", username);

        Trainee trainee = traineeDAO.findTraineeByUserUserName(username).orElseThrow(EntityNotFoundException::new);
        trainee.getUser().setPassword(userService.changePassword(username, newPassword));
        return trainee.getUser().getPassword();
    }

    @Authenticated
    public String changeStatus(@NotBlank String username, String password) {
        LOGGER.info("Activating trainee with username: {}", username);

        Trainee trainee = traineeDAO.findTraineeByUserUserName(username).orElseThrow(EntityNotFoundException::new);
        trainee.getUser().setIsActive(userService.changeStatus(username));
        return "Activated";
    }


}