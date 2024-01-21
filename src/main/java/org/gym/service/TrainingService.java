package org.gym.service;

import org.gym.dao.TrainingDAO;
import org.gym.model.Training;
import org.gym.utils.Generate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class TrainingService {

    private final TrainingDAO trainingDAO;
    private final TrainerService trainerService;
    private final TraineeService traineeService;
    private final String nameSpace = "Training";

    private final Generate generate;

    private static final Logger LOGGER = LoggerFactory.getLogger(TrainingService.class);

    @Autowired
    public TrainingService(TrainingDAO trainingDAO, TrainerService trainerService, TraineeService traineeService, Generate generate) {
        this.generate = generate;
        LOGGER.debug("Initializing TrainingService");
        this.trainingDAO = trainingDAO;
        this.trainerService = trainerService;
        this.traineeService = traineeService;
    }

    public Training createTraining(Training training, Long trainerId, Long traineeId) {
        LOGGER.debug("Creating training with trainer ID {} and trainee ID {}", trainerId, traineeId);

        LOGGER.info("Creating training with trainer and trainee IDs");
        training.setId(generate.generateUniqueId(nameSpace));
        LOGGER.debug("Selecting Trainer with ID: {}", trainerId);

        training.setTrainerId(trainerService.selectTrainer(trainerId));
        LOGGER.debug("Selecting Trainee with ID: {}", traineeId);

        training.setTraineeId(traineeService.selectTrainee(traineeId));
        LOGGER.debug("Saving training details to the database");

        return trainingDAO.save(nameSpace,training);
    }

    public Training selectTraining(Long id) {

        LOGGER.debug("Selecting training by ID: {}", id);
        LOGGER.debug("Retrieving training details from the database");

        return trainingDAO.get(nameSpace,id);
    }

}