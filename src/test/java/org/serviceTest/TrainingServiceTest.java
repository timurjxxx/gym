package org.serviceTest;


import org.gym.dao.TrainingDAO;
import org.gym.model.Trainee;
import org.gym.model.Trainer;
import org.gym.model.Training;
import org.gym.service.TraineeService;
import org.gym.service.TrainerService;
import org.gym.service.TrainingService;
import org.gym.utils.Generate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


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

    @Mock
    private Generate generate;

    @InjectMocks
    private TrainingService trainingService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateTraining() {
        Long trainerId = 1L;
        Long traineeId = 2L;
        Training training = new Training();

        when(generate.generateUniqueId(anyString())).thenReturn(42L);
        when(trainerService.selectTrainer(eq(trainerId))).thenReturn(new Trainer());
        when(traineeService.selectTrainee(eq(traineeId))).thenReturn(new Trainee());
        when(trainingDAO.save(anyString(), any())).thenReturn(training);

        Training result = trainingService.createTraining(training, trainerId, traineeId);

        assertEquals(training, result);
        verify(generate, times(1)).generateUniqueId(anyString());
        verify(trainerService, times(1)).selectTrainer(eq(trainerId));
        verify(traineeService, times(1)).selectTrainee(eq(traineeId));
        verify(trainingDAO, times(1)).save(anyString(), eq(training));
    }

    @Test
    void testSelectTraining() {
        Long trainingId = 42L;
        Training expectedTraining = new Training();

        when(trainingDAO.get(anyString(), eq(trainingId))).thenReturn(expectedTraining);

        Training result = trainingService.selectTraining(trainingId);

        assertEquals(expectedTraining, result);
        verify(trainingDAO, times(1)).get(anyString(), eq(trainingId));
    }
}