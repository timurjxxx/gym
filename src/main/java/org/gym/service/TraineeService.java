package org.gym.service;

import lombok.AllArgsConstructor;
import org.gym.dao.TraineeDAO;
import org.gym.model.Trainee;
import org.gym.model.Training;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class TraineeService {

    private final UserService userService;
    private final TraineeDAO traineeDAO;

    @Autowired
    public TraineeService(UserService userService, TraineeDAO traineeDAO) {
        this.userService = userService;
        this.traineeDAO = traineeDAO;
    }

    public Trainee createTrainee(Trainee trainee) {
        trainee.setPassword(userService.generatePassword());
        trainee.setUserName(userService.generateUsername(trainee.getFirstName(), trainee.getLastName()));
        return traineeDAO.save(trainee);
    }

    public Trainee selectTraineeByUsername(String username) {

        Trainee trainee = traineeDAO.findTraineeByUserName(username).orElseThrow(EntityNotFoundException::new);
        if (userService.authenticate(trainee.getUserName(), trainee.getPassword())) {
            return trainee;
        } else throw new RuntimeException();
    }

    public Trainee updateTrainee(String username, Trainee updatedTrainee) {
        Trainee trainee = selectTraineeByUsername(username);
        updatedTrainee.setId(trainee.getId());
        return traineeDAO.save(updatedTrainee);
    }

    public void deleteTraineeByUserName(String username) {
        try {
            traineeDAO.deleteTraineeByUserName(username);
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
        }
    }

    public List<Training> getTraineeTrainingList(String username) {
        Trainee trainee = selectTraineeByUsername(username);
        return trainee.getTraineeTrainings();
    }

    public List<Training> updateTraineeTrainingList(String username, List<Training> updatedList) {
        Trainee trainee = selectTraineeByUsername(username);
        trainee.setTraineeTrainings(updatedList);
        traineeDAO.save(trainee);
        return trainee.getTraineeTrainings();
    }
}