package org.serviceTest;


import org.gym.dao.TrainingDAO;
import org.gym.model.Trainee;
import org.gym.model.Trainer;
import org.gym.model.Training;
import org.gym.service.TraineeService;
import org.gym.service.TrainerService;
import org.gym.service.TrainingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TrainingServiceTest {

    @Mock
    private TrainingDAO trainingDAO;

    @Mock
    private TrainerService trainerService;

    @Mock
    private TraineeService traineeService;

    @InjectMocks
    private TrainingService trainingService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testCreateTrainingWithIds() {
        Training training = new Training();
        Long trainerId = 1L;
        Long traineeId = 2L;

        when(trainerService.selectTrainer(trainerId)).thenReturn(new Trainer());
        when(traineeService.selectTrainee(traineeId)).thenReturn(new Trainee());
        when(trainingDAO.save(any(Training.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Training result = trainingService.createTraining(training, trainerId, traineeId);

        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getTrainerId());
        assertNotNull(result.getTraineeId());
        verify(trainerService, times(1)).selectTrainer(trainerId);
        verify(traineeService, times(1)).selectTrainee(traineeId);
        verify(trainingDAO, times(1)).save(any(Training.class));
    }

    @Test
    void testCreateTrainingWithObjects() {
        Training training = new Training();
        Trainee trainee = new Trainee();
        Trainer trainer = new Trainer();

        when(trainerService.createTrainer(trainer)).thenReturn(new Trainer());
        when(traineeService.createTrainee(trainee)).thenReturn(new Trainee());
        when(trainingDAO.save(any(Training.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Training result = trainingService.createTraining(training, trainee, trainer);

        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getTrainerId());
        assertNotNull(result.getTraineeId());
        verify(trainerService, times(1)).createTrainer(trainer);
        verify(traineeService, times(1)).createTrainee(trainee);
        verify(trainingDAO, times(1)).save(any(Training.class));
    }

    @Test
    void testSelectTraining() {
        Long trainingId = 1L;
        when(trainingDAO.get(trainingId)).thenReturn(new Training());

        Training result = trainingService.selectTraining(trainingId);

        assertNotNull(result);
        verify(trainingDAO, times(1)).get(trainingId);
    }

    @Test
    void testSelectAllTrainings() {
        Map<Long, Object> trainingMap = new HashMap<>();
        when(trainingDAO.getAll()).thenReturn(trainingMap);

        Map<Long, Object> result = trainingService.selectAllTrainings();

        assertNotNull(result);
        verify(trainingDAO, times(1)).getAll();
    }
}