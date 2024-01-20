package org.gym;

import org.gym.config.AppConfig;
import org.gym.dao.UserDAO;
import org.gym.memory.InMemoryStorage;
import org.gym.model.User;
import org.gym.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        InMemoryStorage storage = context.getBean(InMemoryStorage.class);
        UserDAO userDAO = context.getBean(UserDAO.class);
        UserService userService = context.getBean(UserService.class);
        User user = new User();
        user.setFirstName("Tim");
        user.setLastName("Rj");
        User user1 = new User();
        User user2 = userService.selectUser(1L);
        System.out.println(user2);
    }
}
