package org.gym;

import org.gym.config.AppConfig;
import org.gym.dao.TraineeDAO;
import org.gym.dao.TrainerDAO;
import org.gym.dao.UserDAO;
import org.gym.memory.InMemoryStorage;
import org.gym.memory.StorageInitialization;
import org.gym.model.Trainee;
import org.gym.model.Trainer;
import org.gym.service.TraineeService;
import org.gym.service.TrainerService;
import org.gym.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class, InMemoryStorage.class, StorageInitialization.class);

        InMemoryStorage inMemoryStorage = context.getBean(InMemoryStorage.class);
        StorageInitialization storageInitialization = context.getBean(StorageInitialization.class);

        storageInitialization.initializeStorageWithData();
        UserDAO userDAO = new UserDAO(inMemoryStorage);
        UserService userService = new UserService(userDAO);
        TraineeDAO traineeDAO = new TraineeDAO(inMemoryStorage);
        TrainerDAO trainerDAO = new TrainerDAO(inMemoryStorage);
        TrainerService trainerService = new TrainerService(trainerDAO,userService);
        TraineeService traineeService = new TraineeService(traineeDAO, userService);
        Trainee trainee = new Trainee();
        trainee.setFirstName("Tom");
        trainee.setLastName("Hue");
        traineeService.createTrainee(trainee);
        Trainer trainer = new Trainer();
        trainer.setFirstName("Tom");
        trainer.setLastName("Hue");
        trainer.setSpecialization("qwe");
        trainerService.createTrainer(trainer);
        System.out.println(inMemoryStorage.getStorageMap().toString());

        context.close();
    }
}
