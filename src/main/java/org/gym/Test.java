package org.gym;

import org.gym.config.AppConfig;
import org.gym.dao.*;
import org.gym.model.*;
import org.gym.service.*;
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
            TrainingTypeDAO trainingTypeDAO = context.getBean(TrainingTypeDAO.class);
            TrainingTypeService trainingTypeService = context.getBean(TrainingTypeService.class);
            ///////////////////////////////////////////////////////////////////////////////////
            User user = new User();
            user.setFirstName("Traiienr");
            user.setLastName("tt");
            user.setIsActive(true);
//            userService.createUser(user);
            /////////////////////////////////////////////////////////////////////////////////
            User user1 = new User();
            user1.setFirstName("Mason");
            user1.setLastName("Jackson");
            user1.setIsActive(true);
//            userService.createUser(user1);

            /////////////////////////////////////////////////////////////////////////////////
            Trainee trainee = new Trainee();
            trainee.setDateOfBirth(new Date());
            trainee.setAddress("Grow Street");
//            traineeService.createTrainee(trainee, 1L);
            /////////////////////////////////////////////////////////////////////////////////
            Trainer trainer = new Trainer();
            trainer.setSpecialization("Walk");
//            trainerService.createTrainer(trainer, 2L);
            /////////////////////////////////////////////////////////////////////////////////
            Trainer trainer1 = new Trainer();

            trainer1.setSpecialization("Jumper");

            /////////////////////////////////////////////////////////////////////////////////
            Trainer trainer2 = new Trainer();
            trainer2.setSpecialization("Pull up");
            /////////////////////////////////////////////////////////////////////////////////

            TrainingType trainingType = new TrainingType();
            trainingType.setTrainingTypeName(TrainingTypeEnum.YOGA);
//            trainingTypeService.createTrainingType(trainingType);
            /////////////////////////////////////////////////////////////////////////////////
            Training training = new Training();
            training.setTrainingName("Walk");
            training.setTrainingDate(new Date());
            training.setTrainingDuration(5);
//            trainingService.addTraining(training, 1L, 1L, 3L);



            TrainingSearchCriteria criteria = new TrainingSearchCriteria();
            criteria.setTrainingDuration(9);

            List<Training> trainings = trainingService.getTraineeTrainingsByCriteria("Traiienr.tt",criteria );
            System.out.println(trainings + "HEEEEEEEEEEER");


            /////////////////////////////////////////////////////////////////////////////////


            ////////////////////////////////////////////////////////////////////////////////


        }
    }
}

