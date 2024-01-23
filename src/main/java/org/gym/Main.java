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
        TraineeDAO traineeDAO = context.getBean(TraineeDAO.class);
        TrainerDAO trainerDAO = context.getBean(TrainerDAO.class);
        TraineeService traineeService = context.getBean(TraineeService.class);
        TrainerService trainerService = context.getBean(TrainerService.class);
        TrainingDAO trainingDAO = context.getBean(TrainingDAO.class);
        TrainingService trainingService = context.getBean(TrainingService.class);
        UserService userService = context.getBean(UserService.class);


        Training training = new Training();
        training.setTrainingName("Training");
        trainingService.createTraining(training,trainerService.selectTrainer(1L).getId(),traineeService.selectTrainee(1L).getId());





        context.close();


    }
}