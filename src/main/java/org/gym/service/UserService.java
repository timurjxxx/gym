package org.gym.service;


import org.gym.dao.UserDAO;
import org.gym.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

@Service
public class UserService {
    private UserDAO userDAO;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);


    @Autowired
    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public User selectUser(Long userId) {
        return userDAO.findById(userId).orElseThrow(EntityNotFoundException::new);
    }
    public User findUserByUserName(String username) {
        return userDAO.findUserByUserName(username).orElseThrow(EntityNotFoundException::new);
    }
    public User createtUser(@Validated User newUser) {
        LOGGER.info("create new user");
        newUser.setPassword(generatePassword());
        newUser.setUserName(generateUsername(newUser.getFirstName(), newUser.getLastName()));
        return userDAO.save(newUser);
    }
    public String generateUsername(String firstName, String lastName) {
        String baseUsername = firstName + "." + lastName;
        LOGGER.info("Generate username");

        return IntStream.iterate(1, i -> i + 1)
                .mapToObj(serialNumber -> baseUsername + ((serialNumber == 1) ? "" : "." + serialNumber))
                .filter(username -> !userDAO.findUserByUserName(username).isPresent())
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

        return sb.toString();
    }

    public User updateUser(Long userId, User updatedUser) {
        LOGGER.info("update user");
        User existingUser = userDAO.findById(userId).orElseThrow(EntityNotFoundException::new);

        if (existingUser != null) {
            updatedUser.setId(userId);
            return userDAO.save(updatedUser);
        } else {
            throw new RuntimeException("User with ID " + userId + " not found");
        }
    }

    public List<User> selectAllUsers() {
        return userDAO.findAll();
    }

    public String changePassword(String username, String newPassword) {
        User user = userDAO.findUserByUserName(username).orElseThrow(EntityNotFoundException::new);
        if (user != null && authenticate(user.getUserName(), user.getPassword())) {
            user.setPassword(newPassword);
            userDAO.save(user);
            return newPassword;
        } else throw new EntityNotFoundException();
    }


    public boolean active(String username) {
        User user = userDAO.findUserByUserName(username).orElseThrow(EntityNotFoundException::new);

        if (!user.getIsActive()) {
            user.setIsActive(true);
            userDAO.save(user);
            return user.getIsActive();
        } else throw new EntityNotFoundException();
    }

    public boolean deActive(String username) {
        User user = userDAO.findUserByUserName(username).orElseThrow(EntityNotFoundException::new);


        if (user.getIsActive()) {
            user.setIsActive(false);
            userDAO.save(user);
            return user.getIsActive();
        } else throw new EntityNotFoundException();
    }

    public boolean authenticate(String username, String password) {
        User user = userDAO.findUserByUserName(username).orElseThrow(EntityNotFoundException::new);

        return user != null && user.getPassword().equals(password);
    }
}