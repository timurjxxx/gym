package org.gym;

import org.gym.config.AppConfig;
import org.gym.dao.TraineeDAO;
import org.gym.dao.TrainerDAO;
import org.gym.dao.UserDAO;
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
            // Получаем бин из контекста (в данном случае, AppConfig)
            // Можете добавить другие бины, если необходимо
            AppConfig appConfig = context.getBean(AppConfig.class);
            UserDAO userDAO = context.getBean(UserDAO.class);
            UserService userService = context.getBean(UserService.class);
            TrainerDAO trainerDAO = context.getBean(TrainerDAO.class);
            TrainerService trainerService = new TrainerService(trainerDAO, userService);
            TraineeDAO traineeDAO = context.getBean(TraineeDAO.class);
            TraineeService traineeService = new TraineeService(traineeDAO, userService);

            // Дополнительно, вы можете добавить код для проверки других параметров конфигурации
        }
    }
}
