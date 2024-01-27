package org.gym;

import org.gym.config.AppConfig;
import org.gym.dao.TraineeDAO;
import org.gym.dao.TrainerDAO;
import org.gym.dao.TrainingDAO;
import org.gym.dao.UserDAO;
import org.gym.model.*;
import org.gym.service.TraineeService;
import org.gym.service.TrainerService;
import org.gym.service.TrainingService;
import org.gym.service.UserService;
import org.mapstruct.control.MappingControl;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.persistence.EntityNotFoundException;
import java.util.*;

public class Test {

    public static void main(String[] args) {
        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class)) {
            UserDAO userDAO = context.getBean(UserDAO.class);
            UserService userService = context.getBean(UserService.class);
            TrainerDAO trainerDAO = context.getBean(TrainerDAO.class);
            TrainerService trainerService = context.getBean(TrainerService.class);
            TraineeDAO traineeDAO = context.getBean(TraineeDAO.class);
            TraineeService traineeService = context.getBean(TraineeService.class);
            TrainingDAO trainingDAO = context.getBean(TrainingDAO.class);
            TrainingService trainingService = context.getBean(TrainingService.class);
            TrainingSearchCriteria criteria = new TrainingSearchCriteria();

            Training training = new Training();
            training.setTrainingDate(new Date());
            training.setTrainingName("Taining");
            training.setTrainingDuration(2);

            Trainee trainee = traineeService.selectTraineeByUserName("Trainee.777");
            System.out.println(trainee + "HEEEEEEEEEEEEEEEEEEERE");

        }
    }
}

