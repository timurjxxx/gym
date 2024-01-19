package org.gym.service;

import org.gym.dao.TrainerDAO;
import org.gym.model.Trainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.IntStream;


@Component
public class TrainerService {

    private final TrainerDAO trainerDAO;
    private final UserService userService;
    private static final Logger LOGGER = LoggerFactory.getLogger(TrainerService.class);
    private final AtomicLong idCounter = new AtomicLong(0);

    @Autowired
    public TrainerService(TrainerDAO trainerDAO, UserService userService) {
        this.trainerDAO = trainerDAO;
        this.userService = userService;
    }

    public Trainer createTrainer(Trainer newTrainer) {
        LOGGER.debug("Creating new trainer");
        LOGGER.debug("New trainer details: {}", newTrainer);

        newTrainer.setId(generateUniqueId());
        newTrainer.setUserName(userService.generateUsername(newTrainer.getFirstName() + "." + newTrainer.getLastName()));
        newTrainer.setPassword(userService.generatePassword());

        Trainer savedTrainer = trainerDAO.save(newTrainer);
        LOGGER.info("Trainer created: {}", savedTrainer);

        return savedTrainer;
    }

    private synchronized Long generateUniqueId() {
        LOGGER.info("Generating unique ID for trainer");
        return idCounter.incrementAndGet();
    }

    public Trainer selectTrainer(Long trainerId) {
        LOGGER.debug("Selecting trainer by ID: {}", trainerId);
        return trainerDAO.get(trainerId);
    }

    public Trainer updateTrainer(Long trainerId, Trainer updatedTrainer) {
        LOGGER.debug("Updating trainer by ID: {}", trainerId);
        LOGGER.debug("Updated trainer details: {}", updatedTrainer);

        Trainer trainer = trainerDAO.get(trainerId);

        if (trainer != null) {
            updatedTrainer.setId(trainerId);
            Trainer savedTrainer = trainerDAO.save(updatedTrainer);
            LOGGER.info("Trainer updated: {}", savedTrainer);
            return savedTrainer;
        } else {
            LOGGER.warn("Trainer with ID {} not found", trainerId);
            throw new RuntimeException();
        }
    }

    public Map<Long, Object> selectAllTrainers() {
        LOGGER.debug("Selecting all trainers");
        return trainerDAO.getAll();
    }
}
