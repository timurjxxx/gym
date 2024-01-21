package org.gym.service;


import org.gym.dao.UserDAO;
import org.gym.model.User;
import org.gym.utils.Generate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.IntStream;

@Component
public class UserService {
    private final UserDAO userDAO;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);
    private final String nameSpace = "User";
    private final Generate generate;


    @Autowired
    public UserService(UserDAO userDAO, Generate generate) {
        this.userDAO = userDAO;
        this.generate = generate;
    }

    public User selectUser(Long userId) {
        LOGGER.debug("Selecting user with ID: {}", userId);

        return userDAO.get(nameSpace,userId);
    }

    public User createUser(User newUser) {
        LOGGER.info("Creating a new user");

        Long userId = generate.generateUniqueId("User");
        LOGGER.debug("Generated user ID: {}", userId);


        newUser.setId(userId);
        newUser.setPassword(generate.generatePassword());
        newUser.setUserName(generateUsername(newUser.getFirstName() + "." + newUser.getLastName()));
        LOGGER.debug("Saving the new user to the database");

        return userDAO.save(nameSpace,newUser);

    }

    public String generateUsername(String username1) {
        LOGGER.info("Generating username");
        LOGGER.debug("Base username: {}", username1);
        return IntStream.iterate(1, i -> i + 1)
                .mapToObj(serialNumber -> username1 + ((serialNumber == 1) ? "" : "." + serialNumber))
                .filter(username -> !userDAO.findByUserName(nameSpace,username).isPresent())
                .findFirst()
                .orElseThrow(RuntimeException::new);
    }

    public User updateUser(Long id, User updatedUser) {
        LOGGER.info("Updating user with ID: {}", id);

        updatedUser.setId(id);
        LOGGER.debug("Updating user details: {}", updatedUser);

        return userDAO.update(nameSpace,id, updatedUser);
    }

    public void deleteUser(Long id) {
        LOGGER.debug("Retrieving training details from the database");

        userDAO.delete(nameSpace,id);
    }




}