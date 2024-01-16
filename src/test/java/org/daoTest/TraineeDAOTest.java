package org.daoTest;

import org.gym.dao.TraineeDAO;
import org.gym.memory.InMemoryStorage;
import org.gym.model.Trainee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TraineeDAOTest {

    @Mock
    private InMemoryStorage storage;

    @InjectMocks
    private TraineeDAO traineeDAO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetTrainee() {
        Long traineeId = 1L;
        when(storage.get("Trainee", traineeId)).thenReturn(new Trainee());

        Trainee result = traineeDAO.get(traineeId);

        assertNotNull(result);
        verify(storage, times(1)).get("Trainee", traineeId);
    }

    @Test
    void testSaveTrainee() {
        Trainee newTrainee = new Trainee();

        when(storage.save("Trainee", newTrainee.getId(), newTrainee)).thenReturn(newTrainee);
        Trainee result = traineeDAO.save(newTrainee);

        assertNotNull(result);
        verify(storage, times(1)).save("Trainee", newTrainee.getId(), newTrainee);
    }

    @Test
    void testDeleteTrainee() {
        Long traineeId = 1L;

        traineeDAO.delete(traineeId);

        verify(storage, times(1)).delete("Trainee", traineeId);
    }

    @Test
    void testGetAllTrainees() {
        Map<Long, Object> traineeMap = new HashMap<>();
        when(storage.getAll("Trainee")).thenReturn(traineeMap);

        Map<Long, Object> result = traineeDAO.getAll();

        assertNotNull(result);
        verify(storage, times(1)).getAll("Trainee");
    }

    @Test
    void testFindByUsername() {
        String username = "testUsername";
        when(storage.getStorageMap()).thenReturn(getTestStorageMap());

        String result = traineeDAO.findByUsername(username);

        assertEquals(username, result);
    }

    private Map<String, Map<Long, Object>> getTestStorageMap() {
        Map<String, Map<Long, Object>> storageMap = new HashMap<>();
        Map<Long, Object> traineeMap = new HashMap<>();
        Trainee trainee = new Trainee();
        trainee.setUserName("testUsername");
        traineeMap.put(1L, trainee);
        storageMap.put("Trainee", traineeMap);
        return storageMap;
    }
}