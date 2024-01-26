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
import java.util.Objects;
import java.util.Set;

@Service
public class TraineeService {

    private final UserService userService;
    private final ModelMapper mapper;
    private final UserDAO userDAO;

    private final TraineeDAO traineeDAO;
    private final TrainerDAO trainerDAO;
    private final EntityManager entityManager;

    private static final Logger LOGGER = LoggerFactory.getLogger(TraineeService.class);

    @Autowired
    public TraineeService(TraineeDAO traineeDAO, UserService userService, ModelMapper mapper, UserDAO userDAO, TrainerDAO trainerDAO, EntityManager entityManager) {
        this.traineeDAO = traineeDAO;
        this.userService = userService;
        this.mapper = mapper;
        this.userDAO = userDAO;
        this.trainerDAO = trainerDAO;
        this.entityManager = entityManager;
    }

    @Transactional
    public Trainee createTrainee(@Valid Trainee trainee, @NotNull Long userId) {
        LOGGER.info("Creating trainee with user ID: {}", userId);
        LOGGER.debug("Trainee details: {}", trainee);
        if (trainerDAO.existsTrainerByUser_Id(userId) || traineeDAO.existsTraineeByUser_Id(userId)) {
            throw new EntityExistsException("Trainer or Trainee with this user already exists");
        }
        trainee.setUser(userDAO.findById(userId).orElseThrow(EntityNotFoundException::new));
        return traineeDAO.save(trainee);
    }

    @Authenticated
    @Transactional(readOnly = true)
    public Trainee selectTraineeByUsername(@NotBlank String username, @NotBlank String password) {
        LOGGER.info("Selecting trainee by username: {}", username);
        return traineeDAO.findTraineeByUserUserName(username).orElseThrow(EntityNotFoundException::new);
    }


    @Authenticated
    @Transactional
    public Trainee updateTrainee(@NotBlank String username, @NotBlank String password,  @Valid Trainee updatedTrainee) {
        LOGGER.info("Updating trainee with username: {}", username);
        LOGGER.debug("Updated trainee details: {}", updatedTrainee);
        Long id = traineeDAO.findTraineeByUserUserName(username).get().getId();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaUpdate<Trainee> criteriaUpdate = criteriaBuilder.createCriteriaUpdate(Trainee.class);

        Root<Trainee> root = criteriaUpdate.from(Trainee.class);

        if (Objects.nonNull(updatedTrainee.getDateOfBirth())) {
            criteriaUpdate.set(root.get("dateOfBirth"), updatedTrainee.getDateOfBirth());
        }
        if (Objects.nonNull(updatedTrainee.getAddress())) {
            criteriaUpdate.set(root.get("address"), updatedTrainee.getAddress());
        }

        criteriaUpdate.where(criteriaBuilder.equal(root.get("id"), id));

        entityManager.createQuery(criteriaUpdate).executeUpdate();

        return traineeDAO.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Authenticated
    @Transactional
    public void deleteTraineeByUserName(@NotBlank String username, @NotBlank String password) {
        LOGGER.info("Deleting trainee by username: {}", username);

        Trainee trainee = traineeDAO.findTraineeByUserUserName(username).orElseThrow(EntityNotFoundException::new);
        traineeDAO.deleteById(trainee.getId());
    }


    @Authenticated
    @Transactional
    public Set<Trainer> updateTraineeTrainerList(@NotBlank String username, @NotBlank String password, @Valid Set<Trainer> updatedList) {
        LOGGER.info("Updating training list for trainee with username: {}", username);

        Trainee trainee = traineeDAO.findTraineeByUserUserName(username).orElseThrow(EntityNotFoundException::new);
        trainee.setTrainers(updatedList);
        traineeDAO.save(trainee);
        return trainee.getTrainers();

    }

    @Authenticated
    @Transactional
    public String changePassword(@NotBlank String username, String password,  @NotBlank String newPassword) {
        LOGGER.info("Activating trainee with username: {}", username);

        Trainee trainee = traineeDAO.findTraineeByUserUserName(username).orElseThrow(EntityNotFoundException::new);
        trainee.getUser().setPassword(userService.changePassword(username, newPassword));
        return trainee.getUser().getPassword();
    }

    @Authenticated
    @Transactional
    public String changeStatus(@NotBlank String username, String password) {
        LOGGER.info("Activating trainee with username: {}", username);

        Trainee trainee = traineeDAO.findTraineeByUserUserName(username).orElseThrow(EntityNotFoundException::new);
        trainee.getUser().setIsActive(userService.changeStatus(username));
        return "Activated";
    }


}