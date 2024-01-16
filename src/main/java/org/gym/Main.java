package org.gym;


import org.gym.dao.TraineeDAO;
import org.gym.dao.TrainerDAO;
import org.gym.dao.UserDAO;
import org.gym.memory.InMemoryStorage;
import org.gym.model.Trainee;
import org.gym.model.Trainer;
import org.gym.service.TraineeService;
import org.gym.service.TrainerService;
import org.gym.service.UserService;

public class Main {
    public static void main(String[] args) {

        InMemoryStorage inMemoryStorage = new InMemoryStorage();
        UserDAO userDAO = new UserDAO(inMemoryStorage);
        UserService userService = new UserService(userDAO);
        TraineeDAO dao = new TraineeDAO(inMemoryStorage);
        TraineeService traineeService = new TraineeService(dao,userService);
        Trainee trainee = new Trainee();
        trainee.setFirstName("Tim");
        trainee.setLastName("Rj");
        traineeService.createTrainee(trainee);
        Trainee trainee1 = new Trainee();
        trainee1.setFirstName("Tim");
        trainee1.setLastName("Rj");
        traineeService.createTrainee(trainee1);
        TrainerDAO trainerDAO = new TrainerDAO(inMemoryStorage);
        TrainerService trainerService = new TrainerService(trainerDAO,userService);
        Trainer trainer = new Trainer();
        trainer.setFirstName("Tom");
        trainer.setLastName("Name");
        trainerService.createTrainer(trainer);
        Trainer trainer1 = new Trainer();
        trainer1.setFirstName("Tom");
        trainer1.setLastName("Name");
        trainerService.createTrainer(trainer1);




        System.out.println(inMemoryStorage.getStorageMap().toString());

    }
}