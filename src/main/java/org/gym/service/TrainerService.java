package org.gym.service;

import org.gym.dao.TrainerDAO;
import org.gym.model.Trainer;
import org.gym.utils.Generate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Component
public class TrainerService {

    private final TrainerDAO trainerDAO;
    private final UserService userService;
    private final Generate generate;
    private final String nameSpace = "Trainer";

    private static final Logger LOGGER = LoggerFactory.getLogger(TrainerService.class);

    @Autowired
    public TrainerService(TrainerDAO trainerDAO, UserService userService, Generate generate) {
        this.trainerDAO = trainerDAO;
        this.userService = userService;
        this.generate = generate;
    }

    public Trainer createTrainer(Trainer newTrainer, Long userId) {
        LOGGER.debug("Creating new trainer");
        LOGGER.debug("New trainer details: {}", newTrainer);
        newTrainer.setId(generate.generateUniqueId(nameSpace));
        LOGGER.debug("Selecting User with ID: {}", userId);
        newTrainer.setUser(userService.selectUser(userId));
        LOGGER.debug("Saving trainer details to the database");

        return trainerDAO.save(nameSpace,newTrainer);
    }

    public Trainer selectTrainer(Long trainerId) {
        LOGGER.debug("Selecting trainer by ID: {}", trainerId);
        LOGGER.debug("Retrieving trainer details from the database");

        return trainerDAO.get(nameSpace,trainerId);
    }

    public Trainer updateTrainer(Long trainerId, Trainer updatedTrainer) {
        LOGGER.debug("Updating trainer by ID: {}", trainerId);
        LOGGER.debug("Updated trainer details: {}", updatedTrainer);
        updatedTrainer.setId(trainerId);
        LOGGER.debug("Updating trainer details in the database");

        return trainerDAO.update(nameSpace,trainerId, updatedTrainer);
    }

    public void deleteTrainer(Long id) {
        LOGGER.warn("Deleting trainer with ID: {}", id);

        trainerDAO.delete(nameSpace,id);
    }

}
