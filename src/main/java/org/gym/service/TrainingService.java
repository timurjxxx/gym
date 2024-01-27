package org.gym.service;

import org.gym.dao.TraineeDAO;
import org.gym.dao.TrainerDAO;
import org.gym.dao.TrainingDAO;
import org.gym.model.Trainee;
import org.gym.model.Trainer;
import org.gym.model.Training;
import org.gym.model.TrainingSearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.Predicate;
import javax.validation.Valid;
import java.util.*;


@Service
public class TrainingService {


    private final TrainingDAO trainingDAO;
    private final TrainerDAO trainerDAO;
    private final TraineeDAO traineeDAO;

    @Autowired
    public TrainingService(TrainingDAO trainingDAO, TrainerDAO trainerDAO, TraineeDAO traineeDAO) {
        this.trainingDAO = trainingDAO;
        this.trainerDAO = trainerDAO;
        this.traineeDAO = traineeDAO;
    }

    @Transactional
    public Training addTraining( Training training, Long trainerId, Long traineeId) {
        training.setTrainer(trainerDAO.findById(trainerId).orElseThrow(EntityNotFoundException::new));
        training.setTrainee(traineeDAO.findById(traineeId).orElseThrow(EntityNotFoundException::new));
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
                .orElseThrow(() -> new EntityNotFoundException("Trainer not found"));

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