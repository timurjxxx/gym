package org.gym.service;

import org.gym.dao.TrainingDAO;
import org.gym.dto.TrainingDTO;
import org.gym.mapper.TrainingMapper;
import org.gym.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import java.util.*;


@Service
public class TrainingService {


    private final TrainingDAO trainingDAO;
    private final TrainerService trainerService;
    private final TraineeService traineeService;

    private final TrainingMapper mapper = TrainingMapper.INSTANCE;
    private final TrainingTypeService trainingTypeService;

    private static final Logger LOGGER = LoggerFactory.getLogger(TrainingService.class);

    @Autowired
    public TrainingService(TrainingDAO trainingDAO, TrainerService trainerService, TraineeService traineeService, TrainingTypeService trainingTypeService) {
        this.trainingDAO = trainingDAO;
        this.trainerService = trainerService;
        this.traineeService = traineeService;
        this.trainingTypeService = trainingTypeService;
    }

    @Transactional
    public Training addTraining(Training training, String trainerName, String traineeName, String trainingTypeName) {
        training.setTrainer(trainerService.selectTrainerByUserName(trainerName));
        training.setTrainee(traineeService.selectTraineeByUserName(traineeName));
        training.setTrainingTypes(trainingTypeService.findByTrainingName(trainingTypeName));
        LOGGER.info("Added training with trainer name {}, and trainee name {} and trainingtype {}", trainerName, traineeName, trainingTypeName);
        LOGGER.debug("Added training details: {}", training);

        return trainingDAO.save(training);
    }

    public List<Training> getTrainerTrainingsByCriteria(String trainerUsername, TrainingSearchCriteria criteria) {
        Trainer trainer = trainerService.selectTrainerByUserName(trainerUsername);
        LOGGER.info("Get trainer with username: {}", trainerUsername);
        LOGGER.debug("Trainer details: {}", trainer);
        LOGGER.debug("Criteria details: {}", criteria);

        return trainingDAO.findAll((root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            Optional.ofNullable(trainer).ifPresent(t -> predicates.add(cb.equal(root.get("trainer"), t)));

            Optional.ofNullable(criteria).ifPresent(c -> {
                Optional.ofNullable(c.getTrainingName()).ifPresent(v -> predicates.add(cb.equal(root.get("trainingName"), v)));
                if (c.getTrainingStartDate() != null && c.getTrainingEndDate() != null) {
                    predicates.add(cb.between(root.get("trainingDate"), c.getTrainingStartDate(), c.getTrainingEndDate()));
                }
                Optional.ofNullable(c.getTrainingDuration()).ifPresent(v -> predicates.add(cb.equal(root.get("trainingDuration"), v)));

                Optional.ofNullable(c.getTrainingTypes()).ifPresent(trainingType -> {
                    Join<Training, TrainingType> trainingTypeJoin = root.join("trainingType");
                    predicates.add(cb.equal(trainingTypeJoin.get("trainingTypeName"), trainingType.getTrainingTypeName()));
                });
            });

            return cb.and(predicates.toArray(new Predicate[0]));
        });
    }


    public List<Training> getTraineeTrainingsByCriteria(String traineeUsername, TrainingSearchCriteria criteria) {
        Trainee trainee = traineeService.selectTraineeByUserName(traineeUsername);

        LOGGER.info("Get trainee with username: {}", traineeUsername);
        LOGGER.debug("Trainee details: {}", trainee);
        LOGGER.debug("Criterie details: {}", criteria);

        return trainingDAO.findAll((root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            Optional.ofNullable(trainee).ifPresent(t -> predicates.add(cb.equal(root.get("trainee"), t)));

            Optional.ofNullable(criteria).ifPresent(c -> {
                Optional.ofNullable(c.getTrainingName()).ifPresent(v -> predicates.add(cb.equal(root.get("trainingName"), v)));
                if (c.getTrainingStartDate() != null && c.getTrainingEndDate() != null) {
                    predicates.add(cb.between(root.get("trainingDate"), c.getTrainingStartDate(), c.getTrainingEndDate()));
                }
                Optional.ofNullable(c.getTrainingDuration()).ifPresent(v -> predicates.add(cb.equal(root.get("trainingDuration"), v)));

                Optional.ofNullable(c.getTrainingTypes()).ifPresent(trainingType -> {
                    Join<Training, TrainingType> trainingTypeJoin = root.join("trainingType", JoinType.LEFT);
                    predicates.add(cb.equal(trainingTypeJoin.get("trainingTypeName"), trainingType.getTrainingTypeName()));
                });
            });
            return cb.and(predicates.toArray(new Predicate[0]));
        });
    }

    public List<Training> findall() {
        return trainingDAO.findAll();
    }


}