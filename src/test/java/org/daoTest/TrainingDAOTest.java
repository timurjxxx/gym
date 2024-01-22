package org.daoTest;

import org.gym.dao.TrainingDAO;
import org.gym.memory.InMemoryStorage;
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
    void testSaveTraining() {
        String nameSpace = "Training";
        Training training = new Training();
        when(storage.save(anyString(), eq(training))).thenReturn(training);

        Training result = trainingDAO.save(nameSpace, training);

        assertEquals(training, result);
        verify(storage, times(1)).save(anyString(), eq(training));
    }

    @Test
    void testGetTraining() {
        String nameSpace = "Training";
        Long trainingId = 1L;
        Training expectedTraining = new Training();
        when(storage.get(anyString(), eq(trainingId))).thenReturn(expectedTraining);

        Training result = trainingDAO.get(nameSpace, trainingId);

        assertEquals(expectedTraining, result);
        verify(storage, times(1)).get(anyString(), eq(trainingId));
    }

}