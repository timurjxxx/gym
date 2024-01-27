package org.gym.service;

import lombok.val;
import org.gym.aspect.Authenticated;
import org.gym.dao.TraineeDAO;
import org.gym.dao.TrainerDAO;
import org.gym.dao.UserDAO;
import org.gym.model.Trainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import java.util.*;


@Service
public class TrainerService {

    private final TrainerDAO trainerDAO;
    private final UserService userService;
    private static final Logger LOGGER = LoggerFactory.getLogger(TrainerService.class);


    private final TraineeDAO traineeDAO;
    private final UserDAO userDAO;

    @Autowired
    public TrainerService(TrainerDAO trainerDAO, UserService userService, TraineeDAO traineeDAO, UserDAO userDAO) {
        this.trainerDAO = trainerDAO;
        this.userService = userService;
        this.traineeDAO = traineeDAO;
        this.userDAO = userDAO;
    }

    public Trainer createTrainer(Trainer trainer, Long userId) {
        if (trainerDAO.existsTrainerByUser_Id(userId) || traineeDAO.existsTraineeByUser_Id(userId)) {
            throw new EntityExistsException("Trainer or Trainee with this user already exists");
        }
        trainer.setUser(userDAO.findById(userId).orElseThrow(EntityNotFoundException::new));
        return trainerDAO.save(trainer);
    }

    public Trainer selectTrainerByUserName(String username) {


        return trainerDAO.findTrainerByUserUserName(username).orElseThrow(EntityNotFoundException::new);
    }

    public void updateTrainer(Long id, Trainer updatedTrainer) {

    }


    public List<Trainer> getNotAssignedActiveTrainers(String username) {
        return trainerDAO.getNotAssignedActiveTrainers(username);
    }


    @Transactional
    public void deleteTrainerByUserName(String username) {

        trainerDAO.findTrainerByUserUserName(username)
                .orElseThrow(EntityNotFoundException::new);

        trainerDAO.deleteTrainerByUserUserName(username);

    }

    public void changeStatus(String username) {

        Trainer trainer = trainerDAO.findTrainerByUserUserName(username).orElseThrow(EntityNotFoundException::new);
        trainer.getUser().setIsActive(userService.changeStatus(username));
    }

    public void changePassword(String userName, String newPassword) {
        Trainer trainer = trainerDAO.findTrainerByUserUserName(userName)
                .orElseThrow(EntityNotFoundException::new);
        trainer.getUser().setPassword(userService.changePassword(userName, newPassword));
        trainerDAO.save(trainer);
    }

}