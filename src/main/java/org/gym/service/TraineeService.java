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
    public TraineeService(TraineeDAO trainingDAO, UserService userService) {
        this.traineeDAO = trainingDAO;
        this.userService = userService;
    }


    public Trainee selectTrainee(Long traineeId) {
        return traineeDAO.get(traineeId);

    }

    public Trainee createTrainee(Trainee newTrainee) {

        newTrainee.setId(generateUniqueId());
        newTrainee.setUserName(userService.generateUsername(newTrainee.getFirstName(), newTrainee.getLastName()));
        newTrainee.setPassword(userService.generatePassword());
        LOGGER.info("Create new trainee ");
        return traineeDAO.save(newTrainee);
    }

    public synchronized Long generateUniqueId() {
        LOGGER.info("Generate unique id  ");

        return idCounter.incrementAndGet();
    }

    public void deleteTrainee(Long traineeId) {
        traineeDAO.delete(traineeId);
    }

    public Trainee updateTrainee(Long traineeId, Trainee newTrainee) {
        Trainee trainee = traineeDAO.get(traineeId);
        LOGGER.info("Update trainee  ");

        if (trainee != null) {
            newTrainee.setId(traineeId);
            return traineeDAO.save(newTrainee);
        } else throw new RuntimeException();
    }

    public Map<Long, Object> selectAllTrainees() {
        return traineeDAO.getAll();
    }

}