package org.gym.service;

import org.gym.dao.TrainingDAO;
import org.gym.model.Training;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;


@Service
public class TrainingService {

    private final TrainingDAO trainingDAO;

    @Autowired
    public TrainingService(TrainingDAO trainingDAO) {
        this.trainingDAO = trainingDAO;
    }
    @Transactional
    public Training addTraining(@Valid Training training) {
        return trainingDAO.save(training);
    }

}