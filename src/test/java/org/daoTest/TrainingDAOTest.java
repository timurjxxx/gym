package org.daoTest;

import org.gym.dao.TrainingDAO;
import org.gym.memory.InMemoryStorage;
import org.gym.model.Trainee;
import org.gym.model.Trainer;
import org.gym.model.Training;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TrainingDAOTest {

    @Mock
    private InMemoryStorage storage;

    @InjectMocks
    private TrainingDAO trainingDAO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveTest() {
        String nameSpace = "Training";
        Training training = new Training();
        Long trainingId = 1L;
        Long trainerId = 10L;
        Long traineeId = 20L;

        Trainer trainer = new Trainer();
        trainer.setId(trainerId);
        trainer.setFirstName("John");
        trainer.setLastName("Doe");
        trainer.setPassword("password123");
        trainer.setUserName("john.doe");
        trainer.setSpecialization("Runner");

        Trainee trainee = new Trainee();
        trainee.setId(traineeId);
        trainee.setFirstName("Alice");
        trainee.setLastName("Smith");
        trainee.setPassword("password456");
        trainee.setUserName("alice.smith");
        trainee.setIsActive(true);

        training.setId(trainingId);
        training.setTrainerId(trainer);
        training.setTraineeId(trainee);

        when(storage.save(nameSpace, training)).thenReturn(training);

        Training result = trainingDAO.save(nameSpace, training);

        // Assert
        assertEquals(training, result);
        assertEquals(trainingId, result.getId());
        assertEquals(trainer, result.getTrainerId());
        assertEquals(trainee, result.getTraineeId());

        verify(storage).save(nameSpace, training);
    }

    @Test
    void getTest() {
        // Arrange
        String nameSpace = "Training";
        Long trainingId = 1L;
        Long trainerId = 10L;
        Long traineeId = 20L;

        Trainer trainer = new Trainer();
        trainer.setId(trainerId);
        trainer.setFirstName("John");
        trainer.setLastName("Doe");
        trainer.setPassword("password123");
        trainer.setUserName("john.doe");
        trainer.setSpecialization("Runner");

        Trainee trainee = new Trainee();
        trainee.setId(traineeId);
        trainee.setFirstName("Alice");
        trainee.setLastName("Smith");
        trainee.setPassword("password456");
        trainee.setUserName("alice.smith");
        trainee.setIsActive(true);

        Training expectedTraining = new Training();
        expectedTraining.setId(trainingId);
        expectedTraining.setTrainerId(trainer);
        expectedTraining.setTraineeId(trainee);

        when(storage.findById(nameSpace, trainingId)).thenReturn(expectedTraining);

        Training result = trainingDAO.get(nameSpace, trainingId);

        assertEquals(expectedTraining, result);
        assertEquals(trainingId, result.getId());
        assertEquals(trainer, result.getTrainerId());
        assertEquals(trainee, result.getTraineeId());

        verify(storage).findById(nameSpace, trainingId);
    }


}