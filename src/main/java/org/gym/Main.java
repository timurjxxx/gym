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
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class)) {
            // Получаем бин из контекста (в данном случае, AppConfig)
            // Можете добавить другие бины, если необходимо
            UserDAO userDAO = context.getBean(UserDAO.class);
            UserService userService = context.getBean(UserService.class);
            TrainerDAO trainerDAO = context.getBean(TrainerDAO.class);
            TrainerService trainerService = context.getBean(TrainerService.class);
            TraineeDAO traineeDAO = context.getBean(TraineeDAO.class);
            TraineeService traineeService = context.getBean(TraineeService.class);
            TrainingDAO trainingDAO = context.getBean(TrainingDAO.class);
            TrainingService trainingService = context.getBean(TrainingService.class);
            User user = new User();
            user.setFirstName("Tim");
            user.setLastName("Rj");
            user.setUserName("UserName");
            user.setPassword("gamer120");
            user.setIsActive(true);
            User user1 = new User();
            user.setLastName("qweqweq");
            Trainee trainee = new Trainee();
            trainee.setAddress("USA");
            trainee.setDateOfBirth(new Date());
            Training training = new Training();
            training.setTrainingName("pullup");
            training.setTrainingDate(new Date());
            training.setTrainingDuration(1);
            trainingService.addTraining(training);
        }
    }
}
