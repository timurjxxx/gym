package org.gym.service;


import org.gym.dao.UserDAO;
import org.gym.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.IntStream;

@Component
public class UserService {
    private final UserDAO userDAO;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    private final AtomicLong idCounter = new AtomicLong(0);


    @Autowired
    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public User selectUser(Long userId) {
        return userDAO.get(userId);
    }

    public User createUser(User newUser) {
        LOGGER.info("create new user");
        LOGGER.debug("New user details: {}", newUser);

        newUser.setId(generateUniqueId());
        newUser.setUserName(generateUsername(newUser.getFirstName() + "." + newUser.getLastName()));
        newUser.setPassword(generatePassword());
        LOGGER.debug("User created: {}", newUser);
        return userDAO.save(newUser);
    }

    public String generateUsername(String username1) {
        LOGGER.info("Generate username");
        LOGGER.debug("Base username: {}", username1);

        return IntStream.iterate(1, i -> i + 1)
                .mapToObj(serialNumber -> username1 + ((serialNumber == 1) ? "" : "." + serialNumber))
                .filter(username -> userDAO.findByUsername(username) == null)
                .findFirst()
                .orElseThrow(RuntimeException::new);
    }


    public String generatePassword() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder(10);
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            int index = random.nextInt(chars.length());
            sb.append(chars.charAt(index));
        }
        LOGGER.info("Generate unique password");
        LOGGER.debug("Generated password: {}", sb.toString());


        return sb.toString();
    }


    public synchronized Long generateUniqueId() {
        LOGGER.info("Generate unique id for entity ");
        return idCounter.incrementAndGet();

    }

    public User updateUser(Long userId, User updatedUser) {
        LOGGER.info("update user");
        LOGGER.debug("Updated user details: {}", updatedUser);

        User existingUser = userDAO.get(userId);

        if (existingUser != null) {
            updatedUser.setId(userId);
            return userDAO.save(updatedUser);
        } else {
            LOGGER.warn("User with ID {} not found", userId);
            throw new RuntimeException("User with ID " + userId + " not found");
        }
    }


    public void deletetUser(Long userId) {
        userDAO.delete(userId);
    }

    public Map<Long, Object> selectAllUsers() {
        return userDAO.getAll();
    }
}