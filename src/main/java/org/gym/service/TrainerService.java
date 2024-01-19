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
        LOGGER.debug("");

        newTrainer.setId(generateUniqueId());
        newTrainer.setUserName(userService.generateUsername(newTrainer.getFirstName() + "." + newTrainer.getLastName()));
        newTrainer.setPassword(userService.generatePassword());
        LOGGER.info("create new trainer ");
        return trainerDAO.save(newTrainer);
    }

    private synchronized Long generateUniqueId() {
        LOGGER.info("Generate unique Id for entity ");
        return idCounter.incrementAndGet();

    }


    public Trainer selectTrainer(Long trainerId) {
        return trainerDAO.get(trainerId);
    }

    public Trainer updateTrainer(Long trainerId, Trainer updatedTrainer) {
        LOGGER.debug("");

        Trainer trainer = trainerDAO.get(trainerId);
        LOGGER.info("Update trainer by Id");

        if (trainer != null) {
            updatedTrainer.setId(trainerId);
            return trainerDAO.save(updatedTrainer);
        } else throw new RuntimeException();

    }

    public Map<Long, Object> selectAllTrainers() {
        return trainerDAO.getAll();
    }


}