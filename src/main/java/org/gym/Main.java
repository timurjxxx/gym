package org.gym;

import org.gym.config.AppConfig;
import org.gym.model.*;
import org.gym.service.TraineeService;
import org.gym.service.TrainerService;
import org.gym.service.TrainingService;
import org.gym.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class)) {
            UserService userService = context.getBean(UserService.class);
            TrainerService trainerService = context.getBean(TrainerService.class);
            TraineeService traineeService = context.getBean(TraineeService.class);
            TrainingService trainingService = context.getBean(TrainingService.class);
            Trainer trainer = new Trainer();
            trainer.setFirstName("Trainer");
            trainer.setLastName("T");
            trainerService.createTrainer(trainer);
            Trainee trainee = new Trainee();
            trainee.setFirstName("Trainee");
            trainee.setLastName("Tra");
            traineeService.createTrainee(trainee);
        }
    }
}
