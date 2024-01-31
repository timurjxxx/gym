package org.gym.service;

import org.gym.dao.TraineeDAO;
import org.gym.dao.TrainerDAO;
import org.gym.dao.TrainingDAO;
import org.gym.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.Predicate;
import java.util.*;


@Service
public class TrainingService {


    private final TrainingDAO trainingDAO;
    private final TrainerDAO trainerDAO;
    private final TraineeDAO traineeDAO;

    private final TrainingTypeService trainingTypeService;

    private static final Logger LOGGER = LoggerFactory.getLogger(TrainingService.class);

    @Autowired
    public TrainingService(TrainingDAO trainingDAO, TrainerDAO trainerDAO, TraineeDAO traineeDAO, TrainingTypeService trainingTypeService) {
        this.trainingDAO = trainingDAO;
        this.trainerDAO = trainerDAO;
        this.traineeDAO = traineeDAO;
        this.trainingTypeService = trainingTypeService;
    }

    @Transactional
    public Training addTraining(Training training, Long trainerId, Long traineeId , Long trainingTypeId) {
        training.setTrainer(trainerDAO.findById(trainerId).orElseThrow(EntityNotFoundException::new));
        training.setTrainee(traineeDAO.findById(traineeId).orElseThrow(EntityNotFoundException::new));
        training.setTrainingTypes(trainingTypeService.getTrainingType(trainingTypeId));
        LOGGER.info("Added training");
        LOGGER.debug("Added training details: ");

        return trainingDAO.save(training);
    }

    public List<Training> getTrainerTrainingsByCriteria(String trainerUsername, TrainingSearchCriteria criteria) {
        Trainer trainer = trainerDAO.findTrainerByUserUserName(trainerUsername)
                .orElseThrow(() -> new EntityNotFoundException("Trainer not found"));

        return trainingDAO.findAll((root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            Optional.ofNullable(trainer).ifPresent(t -> predicates.add(cb.equal(root.get("trainer"), t)));

            Optional.ofNullable(criteria).ifPresent(c -> {
                Optional.ofNullable(c.getTrainingName()).ifPresent(v -> predicates.add(cb.equal(root.get("trainingName"), v)));
                if (c.getTrainingStartDate() != null && c.getTrainingEndDate() != null) {
                    predicates.add(cb.between(root.get("trainingDate"), c.getTrainingStartDate(), c.getTrainingEndDate()));
                }
                Optional.ofNullable(c.getTrainingDuration()).ifPresent(v -> predicates.add(cb.equal(root.get("trainingDuration"), v)));
            });

            return cb.and(predicates.toArray(new Predicate[0]));
        });
    }

    public List<Training> getTraineeTrainingsByCriteria(String traineeUsername, TrainingSearchCriteria criteria) {
        Trainee trainee = traineeDAO.findTraineeByUserUserName(traineeUsername)
                .orElseThrow(() -> new EntityNotFoundException("Trainee not found"));

        return trainingDAO.findAll((root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            Optional.ofNullable(trainee).ifPresent(t -> predicates.add(cb.equal(root.get("trainee"), t)));

            Optional.ofNullable(criteria).ifPresent(c -> {
                Optional.ofNullable(c.getTrainingName()).ifPresent(v -> predicates.add(cb.equal(root.get("trainingName"), v)));
                if (c.getTrainingStartDate() != null && c.getTrainingEndDate() != null) {
                    predicates.add(cb.between(root.get("trainingDate"), c.getTrainingStartDate(), c.getTrainingEndDate()));
                }
                Optional.ofNullable(c.getTrainingDuration()).ifPresent(v -> predicates.add(cb.equal(root.get("trainingDuration"), v)));
            });

            return cb.and(predicates.toArray(new Predicate[0]));
        });
    }



}