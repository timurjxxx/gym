package org.gym.service;

import org.gym.aspect.Authenticated;
import org.gym.dao.TraineeDAO;
import org.gym.model.Trainee;
import org.gym.model.Trainer;
import org.gym.model.Training;
import org.modelmapper.ModelMapper;
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
import java.util.Set;

@Service
public class TraineeService {

    private final UserService userService;
    private final ModelMapper mapper;

    private final TraineeDAO traineeDAO;

    private static final Logger LOGGER = LoggerFactory.getLogger(TraineeService.class);

    @Autowired
    public TraineeService(TraineeDAO traineeDAO, UserService userService, ModelMapper mapper) {
        this.traineeDAO = traineeDAO;
        this.userService = userService;
        this.mapper = mapper;
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
    public Trainee updateTrainee(@NotBlank String username, @NotBlank String password, Long id, @Valid Trainee updatedTrainee) {
        LOGGER.info("Updating trainee with username: {}", username);
        LOGGER.debug("Updated trainee details: {}", updatedTrainee);
        Trainee trainee = traineeDAO.findById(id).orElseThrow(EntityNotFoundException::new);
        if (trainee != null) {

            mapper.map(updatedTrainee, trainee);
            trainee.setId(id);
            return traineeDAO.save(updatedTrainee);
        } else {
            throw new EntityNotFoundException();
        }
    }

    @Authenticated
    @Transactional
    public void deleteTraineeByUserName(@NotBlank String username, @NotBlank String password) {
        LOGGER.info("Deleting trainee by username: {}", username);

        Trainee trainee = traineeDAO.findTraineeByUserUserName(username).orElseThrow(EntityNotFoundException::new);
        traineeDAO.deleteById(trainee.getId());
    }


    @Authenticated
    @Transactional
    public Set<Trainer> updateTraineeTrainerList(@NotBlank String username, @NotBlank String password, @Valid Set<Trainer> updatedList) {
        LOGGER.info("Updating training list for trainee with username: {}", username);

        Trainee trainee = traineeDAO.findTraineeByUserUserName(username).orElseThrow(EntityNotFoundException::new);
        trainee.setTrainers(updatedList);
        traineeDAO.save(trainee);
        return trainee.getTrainers();

    }

    @Authenticated
    @Transactional
    public String changePassword(@NotBlank String username, String password, @NotNull Long id, @NotBlank String newPassword) {
        LOGGER.info("Activating trainee with username: {}", username);

        Trainee trainee = traineeDAO.findById(id).orElseThrow(EntityNotFoundException::new);
        trainee.getUser().setPassword(userService.changePassword(username, newPassword));
        return trainee.getUser().getPassword();
    }

    @Authenticated
    @Transactional
    public String changeStatus(@NotBlank String username, String password) {
        LOGGER.info("Activating trainee with username: {}", username);

        Trainee trainee = traineeDAO.findTraineeByUserUserName(username).orElseThrow(EntityNotFoundException::new);
        trainee.getUser().setIsActive(userService.changeStatus(username));
        return "Activated";
    }


}