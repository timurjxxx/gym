package org.gym.service;


import org.gym.dao.TraineeDAO;
import org.gym.dao.TrainerDAO;
import org.gym.memory.InMemoryStorage;
import org.gym.model.Trainee;
import org.gym.model.Trainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Function;
import java.util.function.IntUnaryOperator;
import java.util.function.Predicate;
import java.util.function.ToIntFunction;
import java.util.stream.IntStream;

@Component
public class UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    private final InMemoryStorage storage;
    private final TraineeDAO traineeDAO;
    private final TrainerDAO trainerDAO;

    @Autowired
    public UserService(InMemoryStorage storage, TraineeDAO traineeDAO, TrainerDAO trainerDAO) {
        this.storage = storage;
        this.traineeDAO = traineeDAO;
        this.trainerDAO = trainerDAO;
    }


    public String generateUsernameFor(String nameSpace, String baseUsername) {
        LOGGER.info("Generating trainer username");
        LOGGER.debug("Base username: {}", baseUsername);
        if ("Trainer".equals(nameSpace)) {
            return IntStream.iterate(1, i -> i + 1)
                    .mapToObj(serialNumber -> baseUsername + ((serialNumber == 1) ? "" : "." + serialNumber))
                    .filter(username -> !trainerDAO.findByUsername(nameSpace, username).isPresent())
                    .findFirst()
                    .orElseThrow(RuntimeException::new);
        }if ("Trainee".equals(nameSpace)){

            return IntStream.iterate(1, i -> i + 1)
                    .mapToObj(serialNumber -> baseUsername + ((serialNumber == 1) ? "" : "." + serialNumber))
                    .filter(username -> !traineeDAO.findByUsername(nameSpace, username).isPresent())
                    .findFirst()
                    .orElseThrow(RuntimeException::new);
        }
        return null;
    }



    public String generatePassword() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder(10);
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            int index = random.nextInt(chars.length());
            sb.append(chars.charAt(index));
        }
        LOGGER.info("Generated unique password");
        LOGGER.debug("Generated password: {}", sb.toString());


        return sb.toString();
    }




}