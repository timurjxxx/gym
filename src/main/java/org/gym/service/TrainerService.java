package org.gym.service;

import org.gym.dao.TrainerDAO;
import org.gym.model.Trainee;
import org.gym.model.Trainer;
import org.gym.model.Training;
import org.gym.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;


@Service
public class TrainerService {

    private final TrainerDAO trainerDAO;
    private final UserService userService;
    private static final Logger LOGGER = LoggerFactory.getLogger(TrainerService.class);

    @Autowired
    public TrainerService(TrainerDAO trainerDAO, UserService userService) {
        this.trainerDAO = trainerDAO;
        this.userService = userService;
    }
    @Transactional
    public Trainer createTrainer(@Valid Trainer trainer, @NotNull Long userId) {
        LOGGER.info("Creating trainer with user ID: {}", userId);
        LOGGER.debug("Trainer details: {}", trainer);
        trainer.setUser(userService.findUserById(userId));
        return trainerDAO.save(trainer);
    }
    @Transactional
    public Trainer createTrainer(@Valid Trainer trainer,@Valid User user) {
        LOGGER.info("Creating trainer with user details: {}", user);
        LOGGER.debug("Trainer details: {}", trainer);
        trainer.setUser(userService.createUser(user));
        return trainerDAO.save(trainer);

    }
    @Transactional(readOnly = true)
    public Trainer selectTrainerByUserName(@NotBlank String username) {
        LOGGER.info("Selecting trainer by username: {}", username);

        Trainer trainer = trainerDAO.findTrainerByUserUserName(username).orElseThrow(EntityNotFoundException::new);
        if (userService.authenticate(trainer.getUser().getUserName(), trainer.getUser().getPassword())) {
            return trainer;

        } else {
            LOGGER.warn("Authentication failed for trainer with username: {}", username);

            throw new RuntimeException();
        }


    }


    @Transactional
    public Trainer updateTrainer(@NotBlank String username, @Valid Trainer updatedTrainer) {
        LOGGER.info("Updating trainer with username: {}", username);
        LOGGER.debug("Updated trainer details: {}", updatedTrainer);
        Trainer trainer = selectTrainerByUserName(username);

        updatedTrainer.setId(trainer.getId());
        return trainerDAO.save(updatedTrainer);
    }
    @Transactional(readOnly = true)
    public List<Training> getTrainerTrainingList(@NotBlank String username) {
        LOGGER.info("Getting training list for trainer with username: {}", username);

        Trainer trainer = selectTrainerByUserName(username);
        return trainer.getTraineeTrainings();
    }

    public String changePassword( @NotBlank String username, @NotBlank String newPassword) {
        LOGGER.info("Changing password for trainer with username: {}", username);

        Trainer trainer = selectTrainerByUserName(username);
        trainer.getUser().setPassword(userService.changePassword(username, newPassword));
        return trainer.getUser().getPassword();
    }

    public String activeTrainer(@NotBlank String username) {
        LOGGER.info("Activating trainer with username: {}", username);

        Trainer trainer = selectTrainerByUserName(username);
        trainer.getUser().setIsActive(userService.active(username));
        return "Activated";
    }

    public String deActiveTrainer(@NotBlank String username) {
        LOGGER.info("Deactivating trainer with username: {}", username);
        Trainer trainer = selectTrainerByUserName(username);
        trainer.getUser().setIsActive(userService.deActive(username));
        return "deActivated";
    }

}