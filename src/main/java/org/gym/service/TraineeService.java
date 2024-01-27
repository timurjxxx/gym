package org.gym.service;

import org.gym.aspect.Authenticated;
import org.gym.dao.TraineeDAO;
import org.gym.dao.TrainerDAO;
import org.gym.dao.UserDAO;
import org.gym.model.Trainee;
import org.gym.model.Trainer;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
public class TraineeService {

    private final UserService userService;
    private final UserDAO userDAO;

    private final TraineeDAO traineeDAO;
    private final TrainerDAO trainerDAO;

    private static final Logger LOGGER = LoggerFactory.getLogger(TraineeService.class);

    @Autowired
    public TraineeService(TraineeDAO traineeDAO, UserService userService,  UserDAO userDAO, TrainerDAO trainerDAO) {
        this.traineeDAO = traineeDAO;
        this.userService = userService;
        this.userDAO = userDAO;
        this.trainerDAO = trainerDAO;
    }

    public Trainee createTrainee(Trainee trainee, Long userId) {
        if (trainerDAO.existsTrainerByUser_Id(userId) || traineeDAO.existsTraineeByUser_Id(userId)) {
            throw new EntityExistsException("Trainer or Trainee with this user already exists");
        }
        trainee.setUser(userDAO.findById(userId).orElseThrow(EntityNotFoundException::new));
        return traineeDAO.save(trainee);
    }

    public Trainee selectTraineeByUserName(String username) {
        return traineeDAO.findTraineeByUserUserName(username).orElseThrow(EntityNotFoundException::new);
    }

    public void updateTrainee(Long id, Trainee updatedTrainee) {

    }

    @Transactional
    public void deleteTraineeByUserName(String username) {
        traineeDAO.findTraineeByUserUserName(username)
                .orElseThrow(EntityNotFoundException::new);

        traineeDAO.deleteTraineeByUserUserName(username);

    }


    public Trainee updateTraineeTrainersList(Long id, Set<Trainer> updatedList) {
        Trainee trainee = traineeDAO.findById(id).orElseThrow(EntityNotFoundException::new);
        trainee.setTrainers(updatedList);
        return traineeDAO.save(trainee);

    }

    public void changePassword(String username, String newPassword) {
        Trainee trainee = traineeDAO.findTraineeByUserUserName(username).orElseThrow(EntityNotFoundException::new);
        trainee.getUser().setPassword(userService.changePassword(username, newPassword));
    }


    public void changeStatus(String username) {

        Trainee trainee = traineeDAO.findTraineeByUserUserName(username).orElseThrow(EntityNotFoundException::new);
        trainee.getUser().setIsActive(userService.changeStatus(username));
    }

}