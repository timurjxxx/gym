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
        List<Training> trainings = trainerDAO.findTrainerByUserUserName(trainerUsername)
                .orElseThrow(() -> new EntityNotFoundException("Trainer not found")).getTraineeTrainings();

        if (criteria != null) {
            if (criteria.getTrainingName() != null) {
                trainings = trainings.stream()
                        .filter(training -> criteria.getTrainingName().equals(training.getTrainingName()))
                        .collect(Collectors.toList());
            }

            if (criteria.getTrainingStartDate() != null && criteria.getTrainingEndDate() != null) {
                trainings = trainings.stream()
                        .filter(training ->
                                training.getTrainingDate().after(criteria.getTrainingStartDate())
                                        && training.getTrainingDate().before(criteria.getTrainingEndDate())
                        )
                        .collect(Collectors.toList());
            }

            if (criteria.getTrainingDuration() != null) {
                trainings = trainings.stream()
                        .filter(training -> criteria.getTrainingDuration().equals(training.getTrainingDuration()))
                        .collect(Collectors.toList());
            }
        }

        return trainings;
    }
    @Transactional(readOnly = true)
    public List<Training> getTraineeTrainingsByCriteria(String traineeUsername, TrainingSearchCriteria criteria) {
        List<Training> trainings = traineeDAO.findTraineeByUserUserName(traineeUsername)
                .orElseThrow(() -> new EntityNotFoundException("Trainee not found")).getTraineeTrainings();


        if (criteria != null) {
            if (criteria.getTrainingName() != null) {
                trainings = trainings.stream()
                        .filter(training -> criteria.getTrainingName().equals(training.getTrainingName()))
                        .collect(Collectors.toList());
            }

            if (criteria.getTrainingStartDate() != null && criteria.getTrainingEndDate() != null) {
                trainings = trainings.stream()
                        .filter(training ->
                                training.getTrainingDate().after(criteria.getTrainingStartDate())
                                        && training.getTrainingDate().before(criteria.getTrainingEndDate())
                        )
                        .collect(Collectors.toList());
            }

            if (criteria.getTrainingDuration() != null) {
                trainings = trainings.stream()
                        .filter(training -> criteria.getTrainingDuration().equals(training.getTrainingDuration()))
                        .collect(Collectors.toList());
            }
        }

        return trainings;
    }
}