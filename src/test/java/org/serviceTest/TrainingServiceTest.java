package org.serviceTest;

import org.gym.dao.TraineeDAO;
import org.gym.dao.TrainerDAO;
import org.gym.dao.TrainingDAO;
import org.gym.model.Trainee;
import org.gym.model.Trainer;
import org.gym.model.Training;
import org.gym.model.TrainingSearchCriteria;
import org.gym.service.TrainingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class TrainingServiceTest {

    @Mock
    private TrainingDAO trainingDAO;

    @Mock
    private TrainerDAO trainerDAO;

    @Mock
    private TraineeDAO traineeDAO;

    @InjectMocks
    private TrainingService trainingService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddTraining_Success() {
        Training training = new Training();
        Long trainerId = 1L;
        Long traineeId = 2L;
        Trainer trainer = new Trainer();
        Trainee trainee = new Trainee();

        when(trainerDAO.findById(trainerId)).thenReturn(Optional.of(trainer));
        when(traineeDAO.findById(traineeId)).thenReturn(Optional.of(trainee));
        when(trainingDAO.save(training)).thenReturn(training);

        Training result = trainingService.addTraining(training, trainerId, traineeId);

        assertEquals(training, result);
        verify(trainerDAO, times(1)).findById(trainerId);
        verify(traineeDAO, times(1)).findById(traineeId);
        verify(trainingDAO, times(1)).save(training);
    }

    @Test
    void testGetTrainerTrainingsByCriteria_Success() {
        String trainerUsername = "testTrainer";
        TrainingSearchCriteria criteria = new TrainingSearchCriteria();
        Trainer trainer = new Trainer();
        List<Training> trainingList = new ArrayList<>();

        when(trainerDAO.findTrainerByUserUserName(trainerUsername)).thenReturn(Optional.of(trainer));
        when(trainingDAO.findAll((Specification<Training>) any())).thenReturn(trainingList);

        List<Training> result = trainingService.getTrainerTrainingsByCriteria(trainerUsername, criteria);
        assertEquals(trainingList, result);
        verify(trainerDAO, times(1)).findTrainerByUserUserName(trainerUsername);
        verify(trainingDAO, times(1)).findAll((Specification<Training>) any());
    }

    @Test
    void testGetTraineeTrainingsByCriteria_Success() {
        String traineeUsername = "testTrainee";
        TrainingSearchCriteria criteria = new TrainingSearchCriteria();
        Trainee trainee = new Trainee();
        List<Training> trainingList = new ArrayList<>();

        when(traineeDAO.findTraineeByUserUserName(traineeUsername)).thenReturn(Optional.of(trainee));
        when(trainingDAO.findAll((Specification<Training>) any())).thenReturn(trainingList);

        List<Training> result = trainingService.getTraineeTrainingsByCriteria(traineeUsername, criteria);

        assertEquals(trainingList, result);
        verify(traineeDAO, times(1)).findTraineeByUserUserName(traineeUsername);
        verify(trainingDAO, times(1)).findAll((Specification<Training>) any());
    }
}
