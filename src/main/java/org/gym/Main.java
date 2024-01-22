package org.gym;

import org.gym.config.AppConfig;
import org.gym.config.ApplicationShutdownListener;
import org.gym.dao.TraineeDAO;
import org.gym.dao.TrainerDAO;
import org.gym.dao.TrainingDAO;
import org.gym.dao.UserDAO;
import org.gym.memory.InMemoryStorage;
import org.gym.model.Trainer;
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
        UserDAO userDAO = context.getBean(UserDAO.class);
        UserService userService = context.getBean(UserService.class);
        TrainerDAO trainerDAO = context.getBean(TrainerDAO.class);
        TrainerService trainerService = context.getBean(TrainerService.class);
        TraineeDAO traineeDAO = context.getBean(TraineeDAO.class);
        TraineeService traineeService = context.getBean(TraineeService.class);
        TrainingDAO trainingDAO = context.getBean(TrainingDAO.class);
        TrainingService trainingService = context.getBean(TrainingService.class);
        ApplicationShutdownListener applicationShutdownListener = context.getBean(ApplicationShutdownListener.class);
        User user = new User();
        user.setFirstName("USer");
        user.setLastName("JJ");
        userService.createUser(user);
        Trainer trainer = new Trainer();
        trainerService.createTrainer(trainer,1L);
        System.out.println(trainerService.selectTrainer(1L) +"HHHHHHHHHHEEEEEEEEEEEERRRRRRRRR");

//        System.out.println(storage.getStorageMap().toString());
//        User user1 = new User();
//        user1.setFirstName("USer");
//        user1.setLastName("JJ");
//        userService.createUser(user1);
//        Trainer trainer = new Trainer();
//        trainerService.createTrainer(trainer, 1L);
//        Trainer trainer2 = new Trainer();
//        trainerService.createTrainer(trainer2, 1L);
//        Trainee trainee = new Trainee();
//        trainee.setAddress("trainer");
//        traineeService.createTrainee(trainee, 2L);
//        Training training = new Training();
//        trainingService.createTraining(training, 2L, 1L);
//        User user2 =new User();

        System.out.println(storage.getStorageMap().toString());
        context.close();
    }
}
