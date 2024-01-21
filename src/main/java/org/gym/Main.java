package org.gym;

import org.gym.config.AppConfig;
import org.gym.config.ApplicationShutdownListener;
import org.gym.dao.UserDAO;
import org.gym.memory.InMemoryStorage;
import org.gym.memory.StorageInitialization;
import org.gym.model.User;
import org.gym.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        StorageInitialization storageInitialization = context.getBean(StorageInitialization.class);
        InMemoryStorage storage = context.getBean(InMemoryStorage.class);
        UserDAO userDAO = context.getBean(UserDAO.class);
        UserService userService = context.getBean(UserService.class);
        ApplicationShutdownListener listener = context.getBean(ApplicationShutdownListener.class);
        User user = new User();
        user.setFirstName("qwe");
        user.setLastName("llll");
        User user1 = new User();
        user1.setFirstName("ssssssss");
        user1.setLastName("gggggggggg");
        User user2 = new User();
        user2.setFirstName("zzzzzzzzzz");
        user2.setLastName("xxxxxxxxxxxx");
        userService.createUser(user);
        userService.createUser(user1);
        userService.createUser(user2);
        context.close();
    }
}
