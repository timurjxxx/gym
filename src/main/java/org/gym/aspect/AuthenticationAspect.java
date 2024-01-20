package org.gym.aspect;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.gym.dao.UserDAO;
import org.gym.model.Trainer;
import org.gym.model.User;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;

@Aspect
@Component
public class AuthenticationAspect {

    private final UserDAO userDAO;
    public AuthenticationAspect(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Before("@within(org.gym.aspect.Authenticated) || @annotation(org.gym.aspect.Authenticated)")
    public void authenticate(JoinPoint joinPoint) {

        System.out.println("Authentication aspect is invoked.");

        // Получите аргументы метода
        Object[] args = joinPoint.getArgs();
        if (args.length >= 2 &&
                args[0] instanceof String &&   // проверка типа параметра username
                args[1] instanceof String) {  // проверка типа параметра password

            String username = (String) args[0];
            String password = (String) args[1];

            User user = userDAO.findUserByUserName(username).orElseThrow(EntityNotFoundException::new);

            if (user == null || !user.getPassword().equals(password)) {
                throw new SecurityException("Authentication failed");
            }
        } else {
            throw new IllegalArgumentException("Method requires at least two String parameters for authentication.");
        }
    }

}
