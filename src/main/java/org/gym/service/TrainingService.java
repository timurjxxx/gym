package org.gym.service;

import org.gym.dao.TrainingDAO;
import org.gym.model.Trainee;
import org.gym.model.Trainer;
import org.gym.model.Training;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class TrainingService {

    private final TrainingDAO trainingDAO;
    private final TrainerService trainerService;
    private final TraineeService traineeService;

    private static final Logger LOGGER = LoggerFactory.getLogger(TrainingService.class);
    @Autowired
    public TrainingService(TrainingDAO trainingDAO, TrainerService trainerService, TraineeService traineeService) {
        LOGGER.debug("Initializing TrainingService");
        this.trainingDAO = trainingDAO;
        this.trainerService = trainerService;
        this.traineeService = traineeService;
    }

    public Training createTraining(Training training, Long trainerId, Long traineeId) {
        LOGGER.debug("Creating training with trainer ID {} and trainee ID {}", trainerId, traineeId);

        training.setId(generateUniqueId());
        training.setTraineeId(traineeService.selectTrainee(traineeId));
        training.setTrainerId(trainerService.selectTrainer(trainerId));

        LOGGER.info("Creating training with trainer and trainee IDs");

        return trainingDAO.save(training);
    }

    public Training createTraining(Training training, Trainee trainee, Trainer trainer) {

        LOGGER.debug("Creating training with trainer and trainee objects");
        training.setId(generateUniqueId());
        training.setTraineeId(traineeService.createTrainee(trainee));
        training.setTrainerId(trainerService.createTrainer(trainer));
        LOGGER.info("Creating training with trainer and trainee objects");
        return trainingDAO.save(training);
    }

    public Training selectTraining(Long id) {
        LOGGER.debug("Selecting training by ID: {}", id);
        return trainingDAO.get(id);
    }

    public Map<Long, Object> selectAllTrainings() {
        LOGGER.debug("Selecting all trainings");
        return trainingDAO.getAll();
    }

    public synchronized Long generateUniqueId() {
        LOGGER.info("Generating unique ID for training");
        return idCounter.incrementAndGet();
    }

    private final AtomicLong idCounter = new AtomicLong(0);


}