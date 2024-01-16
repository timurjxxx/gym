package org.daoTest;

import org.gym.dao.TrainingDAO;
import org.gym.memory.InMemoryStorage;
import org.gym.model.Training;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;
import java.util.Map;

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
        Training training = new Training();
        when(storage.save("Training", training.getId(), training)).thenReturn(training);

        Training result = trainingDAO.save(training);

        assertNotNull(result);
        verify(storage, times(1)).save("Training", training.getId(), training);
    }

    @Test
    void testGetTraining() {
        Long trainingId = 1L;
        when(storage.get("Training", trainingId)).thenReturn(new Training());

        Training result = trainingDAO.get(trainingId);

        assertNotNull(result);
        verify(storage, times(1)).get("Training", trainingId);
    }

    @Test
    void testGetAllTrainings() {
        Map<Long, Object> trainingMap = new HashMap<>();
        when(storage.getAll("Training")).thenReturn(trainingMap);

        Map<Long, Object> result = trainingDAO.getAll();

    }
}