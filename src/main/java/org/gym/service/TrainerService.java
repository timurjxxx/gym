package org.gym.service;

import org.gym.aspect.Authenticated;
import org.gym.dao.TraineeDAO;
import org.gym.dao.TrainerDAO;
import org.gym.dao.UserDAO;
import org.gym.model.Trainee;
import org.gym.model.Trainer;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;


@Service
public class TrainerService {

    private final TrainerDAO trainerDAO;
    private final UserService userService;
    private static final Logger LOGGER = LoggerFactory.getLogger(TrainerService.class);
    private final ModelMapper mapper;
    private final EntityManager entityManager;

    private final TraineeDAO traineeDAO;
    private final UserDAO userDAO;

    @Autowired
    public TrainerService(TrainerDAO trainerDAO, UserService userService, ModelMapper mapper, EntityManager entityManager, TraineeDAO traineeDAO, UserDAO userDAO) {
        this.trainerDAO = trainerDAO;
        this.userService = userService;
        this.mapper = mapper;
        this.entityManager = entityManager;
        this.traineeDAO = traineeDAO;
        this.userDAO = userDAO;
    }

    @Transactional
    public Trainer createTrainer(@Valid Trainer trainer, @NotNull Long userId) {
        LOGGER.info("Creating trainer with user ID: {}", userId);
        LOGGER.debug("Trainer details: {}", trainer);
        if (trainerDAO.existsTrainerByUser_Id(userId) || traineeDAO.existsTraineeByUser_Id(userId)) {
            throw new EntityExistsException("Trainer or Trainee with this user already exists");
        }
        trainer.setUser(userDAO.findById(userId).orElseThrow(EntityNotFoundException::new));
        return trainerDAO.save(trainer);
    }


    @Authenticated
    @Transactional(readOnly = true)
    public Trainer selectTrainerByUserName(@NotBlank String username, @NotBlank String password) {
        LOGGER.info("Selecting trainer by username: {}", username);

        LOGGER.warn("Authentication failed for trainer with username: {}", username);
        return trainerDAO.findTrainerByUserUserName(username).orElseThrow(EntityNotFoundException::new);

    }

    @Authenticated
    @Transactional
    public Trainer updateTrainer(@NotBlank String username, @NotBlank String password,  @Valid Trainer updatedTrainer) {
        LOGGER.info("Updating trainer with username: {}", username);
        LOGGER.debug("Updated trainer details: {}", updatedTrainer);

        Long id = trainerDAO.findTrainerByUserUserName(username).get().getId();
        // Получаем CriteriaBuilder из EntityManager


        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaUpdate<Trainer> criteriaUpdate = criteriaBuilder.createCriteriaUpdate(Trainer.class);

        Root<Trainer> root = criteriaUpdate.from(Trainer.class);

        if (Objects.nonNull(updatedTrainer.getSpecialization())) {
            criteriaUpdate.set(root.get("specialization"), updatedTrainer.getSpecialization());
        }

        criteriaUpdate.where(criteriaBuilder.equal(root.get("id"), id));

        entityManager.createQuery(criteriaUpdate).executeUpdate();

        return trainerDAO.findById(id).orElseThrow(EntityNotFoundException::new);
    }


    @Authenticated
    @Transactional(readOnly = true)
    public List<Trainer> getNotAssignedActiveTrainers(String traineeUsername) {
        Trainee trainee = traineeDAO.findTraineeByUserUserName(traineeUsername).orElseThrow(EntityNotFoundException::new);
        if (trainee != null) {
            List<Trainer> assignedTrainers = new ArrayList<>(trainee.getTrainers());
            List<Trainer> allActiveTrainers = trainerDAO.findTrainerByUserIsActive(true);

            allActiveTrainers.removeAll(assignedTrainers);
            return allActiveTrainers;
        }
        return Collections.emptyList();
    }

    @Authenticated
    @Transactional
    public String changePassword(@NotBlank String username, @NotBlank String password, @NotBlank String newPassword) {
        LOGGER.info("Changing password for trainer with username: {}", username);

        Trainer trainer = trainerDAO.findTrainerByUserUserName(username).orElseThrow(EntityNotFoundException::new);
        trainer.getUser().setPassword(userService.changePassword(username, newPassword));
        return trainer.getUser().getPassword();
    }

    @Authenticated
    @Transactional
    public String changeStatus(@NotBlank String username, @NotBlank String password) {
        LOGGER.info("Activating trainer with username: {}", username);

        Trainer trainer = trainerDAO.findTrainerByUserUserName(username).orElseThrow(EntityNotFoundException::new);
        trainer.getUser().setIsActive(userService.changeStatus(username));
        return "Activated";
    }


}