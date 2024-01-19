package org.gym.service;


import org.gym.dao.UserDAO;
import org.gym.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
    @Transactional(readOnly = true)
    public User selectUser(@NotBlank Long userId) {
        LOGGER.info("Selecting user by ID: {}", userId);
        return userDAO.findById(userId).orElseThrow(EntityNotFoundException::new);
    }
    @Transactional(readOnly = true)
    public User findUserByUserName(@NotBlank String username) {
        LOGGER.info("Finding user by username: {}", username);
        return userDAO.findUserByUserName(username).orElseThrow(EntityNotFoundException::new);
    }
    @Transactional

    public User createUser(@Valid User newUser) {
        LOGGER.info("Creating new user");
        LOGGER.debug("New user details: {}", newUser);
        newUser.setPassword(generatePassword());
        newUser.setUserName(generateUsername(newUser.getFirstName()+ "." + newUser.getLastName()));

        return userDAO.save(newUser);
    }
    @Transactional

    public User updateUser(@NotNull Long userId, @Valid User updatedUser) {
        LOGGER.info("Updating user with ID: {}", userId);
        LOGGER.debug("Updated user details: {}", updatedUser);

        User existingUser = userDAO.findById(userId).orElseThrow(EntityNotFoundException::new);

        if (existingUser != null) {
            updatedUser.setId(userId);
            return userDAO.save(updatedUser);
        } else {
            LOGGER.warn("User with ID {} not found", userId);
            throw new RuntimeException("User with ID " + userId + " not found");
        }
    }
    @Transactional(readOnly = true)
    public List<User> selectAllUsers() {
        LOGGER.info("Selecting all users");
        return userDAO.findAll();
    }
    @Transactional
    public void deleteUserByUserName(@NotBlank String userName) {
        LOGGER.info("Deleting user by username: {}", userName);
        findUserByUserName(userName);
        userDAO.deleteUserByUserName(userName);
    }

    public User findUserById(@NotNull Long id) {
        LOGGER.info("Finding user by ID: {}", id);
        return userDAO.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public String changePassword(@NotBlank String username, @NotBlank String newPassword) {
        LOGGER.info("Changing password for user: {}", username);

        User user = userDAO.findUserByUserName(username).orElseThrow(EntityNotFoundException::new);
        if (user != null && authenticate(user.getUserName(), user.getPassword())) {
            user.setPassword(newPassword);
            userDAO.save(user);
            return newPassword;
        } else {
            LOGGER.warn("Authentication failed for user: {}", username);
            throw new EntityNotFoundException();
        }
    }

    public boolean active(@NotBlank String username) {
        LOGGER.info("Activating user: {}", username);
        User user = userDAO.findUserByUserName(username).orElseThrow(EntityNotFoundException::new);

        if (!user.getIsActive()) {
            user.setIsActive(true);
            userDAO.save(user);
            return user.getIsActive();
        } else {
            LOGGER.warn("User {} is already active", username);
            throw new EntityNotFoundException();
        }
    }

    public boolean deActive(@NotBlank String username) {
        LOGGER.info("Deactivating user: {}", username);

        User user = userDAO.findUserByUserName(username).orElseThrow(EntityNotFoundException::new);

        if (user.getIsActive()) {
            user.setIsActive(false);
            userDAO.save(user);
            return user.getIsActive();
        } else {
            LOGGER.warn("User {} is already deactivated", username);
            throw new EntityNotFoundException();
        }
    }

    public boolean authenticate(@NotBlank String username, @NotBlank String password) {
        LOGGER.info("Authenticating user: {}", username);
        User user = userDAO.findUserByUserName(username).orElseThrow(EntityNotFoundException::new);

        return user != null && user.getPassword().equals(password);
    }

    private String generateUsername(String username1) {
        LOGGER.info("Generating username");
        LOGGER.debug("Base username: {}", username1);


        return IntStream.iterate(1, i -> i + 1)
                .mapToObj(serialNumber -> username1 + ((serialNumber == 1) ? "" : "." + serialNumber))
                .filter(username -> !userDAO.findUserByUserName(username).isPresent())
                .findFirst()
                .orElseThrow(RuntimeException::new);
    }

    private String generatePassword() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder(10);
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            int index = random.nextInt(chars.length());
            sb.append(chars.charAt(index));
        }
        LOGGER.info("Generating unique password");
        LOGGER.debug("Generated password: {}", sb.toString());


        return sb.toString();
    }
}
