package org.gym.service;

import org.gym.dao.TrainerDAO;
import org.gym.model.Trainer;
import org.gym.utils.GenerateId;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Component
public class TrainerService {

    private final TrainerDAO trainerDAO;
    private final UserService userService;
    private final String nameSpace = "Trainer";
    private final ModelMapper modelMapper;

    private final GenerateId generateId;

    private static final Logger LOGGER = LoggerFactory.getLogger(TrainerService.class);

    @Autowired
    public TrainerService(TrainerDAO trainerDAO, UserService userService, ModelMapper modelMapper, GenerateId generateId) {
        this.trainerDAO = trainerDAO;
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.generateId = generateId;
    }

    public Trainer createTrainer(Trainer newTrainer) {
        newTrainer.setPassword(userService.generatePassword());
        newTrainer.setUserName(userService.generateUsernameFor(nameSpace, newTrainer.getFirstName() + "." + newTrainer.getLastName()));
        newTrainer.setId(generateId.generateUniqueId(nameSpace));
        return trainerDAO.save(nameSpace, newTrainer);
    }

    public Trainer selectTrainer(Long trainerId) {
        LOGGER.debug("Selecting trainer by ID: {}", trainerId);
        LOGGER.debug("Retrieving trainer details from the database");

        return trainerDAO.get(nameSpace, trainerId);
    }

    public Trainer updateTrainer(Long trainerId, Trainer updatedTrainer) {
        LOGGER.debug("Updating trainer by ID: {}", trainerId);
        LOGGER.debug("Updated trainer details: {}", updatedTrainer);

        Trainer existingTrainer = trainerDAO.get(nameSpace, trainerId);
        if (existingTrainer == null) {
            throw new RuntimeException("Trainer with ID " + trainerId + " not found");
        }

        modelMapper.map(updatedTrainer, existingTrainer);
        LOGGER.debug("Updating trainer details in the database");
        trainerDAO.update(nameSpace, trainerId, existingTrainer);
        return existingTrainer;
    }

}
