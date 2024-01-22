package org.serviceTest;


import org.gym.dao.TrainingDAO;
import org.gym.model.Trainee;
import org.gym.model.Trainer;
import org.gym.model.Training;
import org.gym.service.TraineeService;
import org.gym.service.TrainerService;
import org.gym.service.TrainingService;
import org.gym.utils.GenerateId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TrainingServiceTest {
    @Mock
    private TrainingDAO trainingDAOMock;

    @Mock
    private TrainerService trainerServiceMock;

    @Mock
    private TraineeService traineeServiceMock;

    @Mock
    private GenerateId generateIdMock;

    @InjectMocks
    private TrainingService trainingService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void createTrainingTest() {
        // Arrange
        Long trainerId = 1L;
        Long traineeId = 2L;

        Trainer trainer = new Trainer();
        trainer.setId(trainerId);
        trainer.setFirstName("John");
        trainer.setLastName("Doe");
        trainer.setPassword("password123");
        trainer.setUserName("john.doe");
        trainer.setSpecialization("Runner");
        when(trainerServiceMock.selectTrainer(trainerId)).thenReturn(trainer);

        Trainee trainee = new Trainee();
        trainee.setId(traineeId);
        trainee.setFirstName("John");
        trainee.setLastName("Doe");
        trainee.setAddress("123 Main St");
        trainee.setPassword("password123");
        trainee.setUserName("john.doe");
        trainee.setDateOfBirth(new Date());
        trainee.setIsActive(true);
        when(traineeServiceMock.selectTrainee(traineeId)).thenReturn(trainee);

        Training inputTraining = new Training();
        inputTraining.setTrainingName("Running");

        Training expectedTraining = new Training();
        expectedTraining.setId(1L); // You should mock the generated ID accordingly
        expectedTraining.setTrainingName("Running");
        expectedTraining.setTrainerId(trainer);
        expectedTraining.setTraineeId(trainee);

        when(generateIdMock.generateUniqueId("Training")).thenReturn(1L);
        when(trainingDAOMock.save("Training", inputTraining)).thenReturn(expectedTraining);

        // Act
        Training result = trainingService.createTraining(inputTraining, trainerId, traineeId);

        // Assert
        verify(trainerServiceMock).selectTrainer(trainerId);
        verify(traineeServiceMock).selectTrainee(traineeId);
        verify(generateIdMock).generateUniqueId("Training");
        verify(trainingDAOMock).save("Training", inputTraining);

        // Additional assertions based on your implementation
        assertNotNull(result);
        assertEquals(expectedTraining.getId(), result.getId());
        assertEquals(expectedTraining.getTrainingName(), result.getTrainingName());
        assertEquals(expectedTraining.getTrainerId(), result.getTrainerId());
        assertEquals(expectedTraining.getTraineeId(), result.getTraineeId());
    }

    @Test
    void selectTrainingTest() {
        Long trainingId = 1L;
        Training expectedTraining = new Training();
        expectedTraining.setId(trainingId);
        expectedTraining.setTrainingName("Running");

        when(trainingDAOMock.get("Training", trainingId)).thenReturn(expectedTraining);

        Training result = trainingService.selectTraining(trainingId);

        verify(trainingDAOMock).get("Training", trainingId);

        assertNotNull(result);
        assertEquals(expectedTraining.getId(), result.getId());
        assertEquals(expectedTraining.getTrainingName(), result.getTrainingName());
    }

}