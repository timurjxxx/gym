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

import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.Predicate;
import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;


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
    public Training addTraining(@Valid Training training, Long trainerId, Long traineeId) {
        training.setTrainer(trainerDAO.findById(trainerId).orElseThrow(EntityNotFoundException::new));
        training.setTrainee(traineeDAO.findById(traineeId).orElseThrow(EntityNotFoundException::new));
        return trainingDAO.save(training);
    }

    @Transactional(readOnly = true)
    public List<Training> getTrainerTrainingsByCriteria(String trainerUsername, TrainingSearchCriteria criteria) {
        Trainer trainer = trainerDAO.findTrainerByUserUserName(trainerUsername)
                .orElseThrow(() -> new EntityNotFoundException("Trainee not found"));

        return trainingDAO.findAll((root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(cb.equal(root.get("trainer"), trainer));

            if (criteria != null) {
                if (criteria.getTrainingName() != null) {
                    predicates.add(trainingDAO.hasTrainingName(criteria.getTrainingName()).toPredicate(root, query, cb));
                }

                if (criteria.getTrainingStartDate() != null && criteria.getTrainingEndDate() != null) {
                    predicates.add(trainingDAO.isBetweenTrainingDates(criteria.getTrainingStartDate(), criteria.getTrainingEndDate()).toPredicate(root, query, cb));
                }

                if (criteria.getTrainingDuration() != null) {
                    predicates.add(trainingDAO.hasTrainingDuration((Integer) criteria.getTrainingDuration()).toPredicate(root, query, cb));
                }
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        });
    }
    @Transactional(readOnly = true)
    public List<Training> getTraineeTrainingsByCriteria(String traineeUsername, TrainingSearchCriteria criteria) {
        Trainee trainee = traineeDAO.findTraineeByUserUserName(traineeUsername)
                .orElseThrow(() -> new EntityNotFoundException("Trainee not found"));

        return trainingDAO.findAll((root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(cb.equal(root.get("trainee"), trainee));

            if (criteria != null) {
                if (criteria.getTrainingName() != null) {
                    predicates.add(trainingDAO.hasTrainingName(criteria.getTrainingName()).toPredicate(root, query, cb));
                }

                if (criteria.getTrainingStartDate() != null && criteria.getTrainingEndDate() != null) {
                    predicates.add(trainingDAO.isBetweenTrainingDates(criteria.getTrainingStartDate(), criteria.getTrainingEndDate()).toPredicate(root, query, cb));
                }

                if (criteria.getTrainingDuration() != null) {
                    predicates.add(trainingDAO.hasTrainingDuration((Integer) criteria.getTrainingDuration()).toPredicate(root, query, cb));
                }
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        });
    }
}