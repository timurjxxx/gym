package org.gym.service;

import org.gym.dao.TraineeDAO;
import org.gym.model.Trainee;
import org.gym.utils.Generate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class TraineeService {

    private final TraineeDAO traineeDAO;
    private final UserService userService;
    private final String nameSpace = "Trainee";

    private final Generate generate;

    private static final Logger LOGGER = LoggerFactory.getLogger(TraineeService.class);

    @Autowired
    public TraineeService(TraineeDAO traineeDAO, UserService userService, Generate generate) {
        this.traineeDAO = traineeDAO;
        this.userService = userService;
        this.generate = generate;
    }

    public Trainee selectTrainee(Long traineeId) {
        LOGGER.debug("Selecting trainee by ID: {}", traineeId);
        LOGGER.debug("Retrieving trainee details from the database");

        return traineeDAO.get(nameSpace, traineeId);
    }

    public Trainee createTrainee(Trainee newTrainee, Long userId) {
        LOGGER.debug("Creating new trainee");

        newTrainee.setId(generate.generateUniqueId(nameSpace));
        LOGGER.debug("Selecting User with ID: {}", userId);

        newTrainee.setUser(userService.selectUser(userId));
        LOGGER.debug("Saving trainee details to the database");
        return traineeDAO.save(nameSpace, newTrainee);
    }

    public Trainee updateTrainee(Long id, Trainee newTrainee) {
        LOGGER.debug("Updating trainee by ID: {}", id);
        LOGGER.debug("Updated trainee details: {}", newTrainee);
        return traineeDAO.update(nameSpace, id, newTrainee);
    }

    public void deleteTrainee(Long id) {
        LOGGER.warn("Deleting trainee with ID: {}", id);
        traineeDAO.delete(nameSpace, id);
    }


}
