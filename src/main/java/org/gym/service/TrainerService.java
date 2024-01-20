package org.gym.service;

import org.gym.aspect.Authenticated;
import org.gym.dao.TrainerDAO;
import org.gym.model.Trainer;
import org.gym.model.Training;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Authenticated
    @Transactional(readOnly = true)
    public Trainer selectTrainerByUserName(String username, String password) {
        LOGGER.info("Selecting trainer by username: {}", username);

        LOGGER.warn("Authentication failed for trainer with username: {}", username);
        return trainerDAO.findTrainerByUserUserName(username).orElseThrow(EntityNotFoundException::new);


    }

    @Authenticated
    @Transactional
    public Trainer updateTrainer(@NotBlank String username, @NotBlank String password, @Valid Trainer updatedTrainer) {
        LOGGER.info("Updating trainer with username: {}", username);
        LOGGER.debug("Updated trainer details: {}", updatedTrainer);
        Trainer trainer = trainerDAO.findTrainerByUserUserName(username).orElseThrow(EntityNotFoundException::new);

        updatedTrainer.setId(trainer.getId());
        return trainerDAO.save(updatedTrainer);
    }

    @Authenticated
    @Transactional(readOnly = true)
    public List<Training> getTrainerTrainingList(@NotBlank String username, @NotBlank String password) {
        LOGGER.info("Getting training list for trainer with username: {}", username);

        Trainer trainer = trainerDAO.findTrainerByUserUserName(username).orElseThrow(EntityNotFoundException::new);
        return trainer.getTraineeTrainings();
    }


    @Authenticated
    public String changePassword(@NotBlank String username, @NotBlank String password, @NotBlank String newPassword) {
        LOGGER.info("Changing password for trainer with username: {}", username);

        Trainer trainer = trainerDAO.findTrainerByUserUserName(username).orElseThrow(EntityNotFoundException::new);
        trainer.getUser().setPassword(userService.changePassword(username, newPassword));
        return trainer.getUser().getPassword();
    }

    @Authenticated
    @Transactional
    public String changeStatus(@NotBlank String username, String password) {
        LOGGER.info("Activating trainer with username: {}", username);

        Trainer trainer = trainerDAO.findTrainerByUserUserName(username).orElseThrow(EntityNotFoundException::new);
        trainer.getUser().setIsActive(userService.changeStatus(username));
        return "Activated";
    }


}