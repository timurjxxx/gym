package org.gym.service;

import org.gym.dao.TraineeDAO;
import org.gym.model.Trainee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class TraineeService {

    private final TraineeDAO traineeDAO;
    private final UserService userService;
    private final AtomicLong idCounter = new AtomicLong(0);

    private static final Logger LOGGER = LoggerFactory.getLogger(TraineeService.class);

    @Autowired
    public TraineeService(TraineeDAO traineeDAO, UserService userService) {
        this.traineeDAO = traineeDAO;
        this.userService = userService;
    }

    public Trainee selectTrainee(Long traineeId) {
        LOGGER.debug("Selecting trainee by ID: {}", traineeId);
        return traineeDAO.get(traineeId);
    }

    public Trainee createTrainee(Trainee newTrainee) {
        LOGGER.debug("Creating new trainee");
        LOGGER.debug("New trainee details: {}", newTrainee);

        newTrainee.setId(generateUniqueId());
        newTrainee.setUserName(userService.generateUsername(newTrainee.getFirstName() + "." + newTrainee.getLastName()));
        newTrainee.setPassword(userService.generatePassword());

        Trainee savedTrainee = traineeDAO.save(newTrainee);
        LOGGER.info("Trainee created: {}", savedTrainee);

        return savedTrainee;
    }

    public synchronized Long generateUniqueId() {
        LOGGER.info("Generating unique ID for trainee");
        return idCounter.incrementAndGet();
    }

    public void deleteTrainee(Long traineeId) {
        LOGGER.info("Deleting trainee by ID: {}", traineeId);
        traineeDAO.delete(traineeId);
    }

    public Trainee updateTrainee(Long traineeId, Trainee newTrainee) {
        LOGGER.info("Updating trainee by ID: {}", traineeId);
        LOGGER.debug("Updated trainee details: {}", newTrainee);

        Trainee trainee = traineeDAO.get(traineeId);

        if (trainee != null) {
            newTrainee.setId(traineeId);
            Trainee savedTrainee = traineeDAO.save(newTrainee);
            LOGGER.info("Trainee updated: {}", savedTrainee);
            return savedTrainee;
        } else {
            LOGGER.warn("Trainee with ID {} not found", traineeId);
            throw new RuntimeException();
        }
    }

    public Map<Long, Object> selectAllTrainees() {
        LOGGER.debug("Selecting all trainees");
        return traineeDAO.getAll();
    }
}
