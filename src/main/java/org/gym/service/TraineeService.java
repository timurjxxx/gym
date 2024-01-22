package org.gym.service;

import org.gym.dao.TraineeDAO;
import org.gym.model.Trainee;
import org.gym.utils.GenerateId;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class TraineeService {

    private final TraineeDAO traineeDAO;
    private final UserService userService;
    private final String nameSpace = "Trainee";

    private final GenerateId generateId;

    private final ModelMapper modelMapper;

    private static final Logger LOGGER = LoggerFactory.getLogger(TraineeService.class);

    @Autowired
    public TraineeService(TraineeDAO traineeDAO, UserService userService, GenerateId generateId, ModelMapper modelMapper) {
        this.traineeDAO = traineeDAO;
        this.userService = userService;
        this.generateId = generateId;
        this.modelMapper = modelMapper;
    }

    public Trainee selectTrainee(Long traineeId) {
        LOGGER.debug("Selecting trainee by ID: {}", traineeId);
        LOGGER.debug("Retrieving trainee details from the database");

        return traineeDAO.get(nameSpace, traineeId);
    }

    public Trainee createTrainee(Trainee newTrainee) {
        LOGGER.debug("Creating new trainee");

        newTrainee.setId(generateId.generateUniqueId(nameSpace));
        newTrainee.setUserName(userService.generateUsernameFor(nameSpace, newTrainee.getFirstName()) + "." + newTrainee.getLastName());
        newTrainee.setPassword(userService.generatePassword());
        return traineeDAO.save(nameSpace, newTrainee);
    }

    public Trainee updateTrainee(Long trainerId, Trainee updatedTrainee) {
        LOGGER.debug("Updating trainee by ID: {}", trainerId);
        LOGGER.debug("Updated trainee details: {}", updatedTrainee);

        Trainee existingTrainee = traineeDAO.get(nameSpace, trainerId);
        if (existingTrainee == null) {
            throw new RuntimeException("Trainee with ID " + trainerId + " not found");
        }

        // Use ModelMapper for mapping properties
        modelMapper.map(updatedTrainee, existingTrainee);
        LOGGER.debug("Updating trainee details in the database");
        traineeDAO.update(nameSpace, trainerId, existingTrainee);
        return existingTrainee;

    }

    public void deleteTrainee(Long id) {
        LOGGER.warn("Deleting trainee with ID: {}", id);
        traineeDAO.delete(nameSpace, id);
    }


}
