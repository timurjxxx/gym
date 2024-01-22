package org.gym;

import org.gym.config.AppConfig;
import org.gym.config.ApplicationShutdownListener;
import org.gym.dao.TraineeDAO;
import org.gym.dao.TrainerDAO;
import org.gym.dao.TrainingDAO;
import org.gym.memory.InMemoryStorage;
import org.gym.model.Trainee;
import org.gym.model.Trainer;
import org.gym.model.Training;
import org.gym.model.User;
import org.gym.service.TraineeService;
import org.gym.service.TrainerService;
import org.gym.service.TrainingService;
import org.gym.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class Main {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        InMemoryStorage storage = context.getBean(InMemoryStorage.class);
        ApplicationShutdownListener applicationShutdownListener = context.getBean(ApplicationShutdownListener.class);
        TrainerDAO trainerDAO = context.getBean(TrainerDAO.class);
        TrainerService trainerService = context.getBean(TrainerService.class);
        TraineeDAO traineeDAO = context.getBean(TraineeDAO.class);
        UserService userService = context.getBean(UserService.class);
        TraineeService traineeService = context.getBean(TraineeService.class);
        TrainingDAO trainingDAO = context.getBean(TrainingDAO.class);
        TrainingService trainingService = context.getBean(TrainingService.class);

        Trainee trainee = new Trainee();
        trainee.setFirstName("Trainee");
        trainee.setLastName("Trainee");
        traineeService.createTrainee(trainee);


        Trainee trainee1 = new Trainee();
        trainee1.setFirstName("Trainee");
        trainee1.setLastName("s");
        traineeService.createTrainee(trainee1);

        Trainee trainee2 = new Trainee();
        trainee2.setFirstName("Trainee");
        trainee2.setLastName("s");
        traineeService.createTrainee(trainee2);

//
//        Trainer trainer = new Trainer();
//        trainer.setFirstName("Trainer");
//        trainer.setLastName("Trainer");
//        trainerService.createTrainer(trainer);
//
//
//        Trainer trainer1 = new Trainer();
//        trainer1.setFirstName("Trainer");
//        trainer1.setLastName("Trainer");
//        trainerService.createTrainer(trainer1);
//
//
//        Trainer trainer2 = new Trainer();
//        trainer2.setFirstName("Trainer");
//        trainer2.setLastName("Trainer");
//        trainerService.createTrainer(trainer2);
        System.out.println(storage.getStorageMap().toString());

    }
}