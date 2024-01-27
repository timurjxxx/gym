package org.gym.service;


import org.gym.aspect.Authenticated;
import org.gym.dao.UserDAO;
import org.gym.model.User;
import org.modelmapper.ModelMapper;
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


    public User selectUser(@NotBlank Long userId) {
        return userDAO.findById(userId).orElseThrow(EntityNotFoundException::new);
    }

    @Authenticated
    @Transactional(readOnly = true)
    public User findUserByUserName(String username) {
        return userDAO.findUserByUserName(username).orElseThrow(EntityNotFoundException::new);
    }

    @Transactional

    public User createUser(@Valid User newUser) {
        newUser.setPassword(generatePassword());
        newUser.setUserName(generateUsername(newUser.getFirstName() + "." + newUser.getLastName()));

        return userDAO.save(newUser);
    }


    @Transactional
    public User updateUser(@NotNull Long userId, @Valid User updatedUser) {

        User existingUser = userDAO.findById(userId).orElseThrow(EntityNotFoundException::new);

        if (existingUser != null) {
            existingUser.setId(userId);
            return userDAO.save(existingUser);
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



    public User findUserById(@NotNull Long id) {
        LOGGER.info("Finding user by ID: {}", id);
        return userDAO.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public String changePassword(@NotBlank String username, @NotBlank String newPassword) {
        LOGGER.info("Changing password for user: {}", username);

        User user = userDAO.findUserByUserName(username).orElseThrow(EntityNotFoundException::new);
        if (user != null) {
            user.setPassword(newPassword);
            userDAO.save(user);
            return newPassword;
        } else {
            LOGGER.warn("Authentication failed for user: {}", username);
            throw new EntityNotFoundException();
        }
    }

    @Transactional
    public boolean changeStatus(@NotBlank String username) {
        LOGGER.info("Deactivating user: {}", username);

        User user = userDAO.findUserByUserName(username).orElseThrow(EntityNotFoundException::new);
        if (user.getIsActive()) {
            user.setIsActive(false);
        } else {
            user.setIsActive(true);
        }
        userDAO.save(user);
        return user.getIsActive();
    }


     public String generateUsername(String username1) {
        LOGGER.info("Generating username");
        LOGGER.debug("Base username: {}", username1);


        return IntStream.iterate(1, i -> i + 1)
                .mapToObj(serialNumber -> username1 + ((serialNumber == 1) ? "" : "." + serialNumber))
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
        LOGGER.info("Generating unique password");
        LOGGER.debug("Generated password: {}", sb.toString());


        return sb.toString();
    }



}
