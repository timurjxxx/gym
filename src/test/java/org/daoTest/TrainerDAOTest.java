package org.daoTest;

import org.gym.dao.TrainerDAO;
import org.gym.memory.InMemoryStorage;
import org.gym.model.Trainer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TrainerDAOTest {

    @Mock
    private InMemoryStorage storage;

    @InjectMocks
    private TrainerDAO trainerDAO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveTrainer() {
        Trainer newTrainer = new Trainer();
        when(storage.save("Trainer", newTrainer.getId(), newTrainer)).thenReturn(newTrainer);

        Trainer result = trainerDAO.save(newTrainer);

        assertNotNull(result);
        verify(storage, times(1)).save("Trainer", newTrainer.getId(), newTrainer);
    }

    @Test
    void testGetTrainer() {
        Long trainerId = 1L;
        when(storage.get("Trainer", trainerId)).thenReturn(new Trainer());

        Trainer result = trainerDAO.get(trainerId);

        assertNotNull(result);
        verify(storage, times(1)).get("Trainer", trainerId);
    }

    @Test
    void testGetAllTrainers() {
        Map<Long, Object> trainerMap = new HashMap<>();
        when(storage.getAll("Trainer")).thenReturn(trainerMap);

        Map<Long, Object> result = trainerDAO.getAll();

        assertNotNull(result);
        verify(storage, times(1)).getAll("Trainer");
    }

    @Test
    void testFindByUsername() {
        String username = "testUsername";
        when(storage.getStorageMap()).thenReturn(getTestStorageMap());

        String result = trainerDAO.findByUsername(username);

        assertEquals(username, result);
    }

    private Map<String, Map<Long, Object>> getTestStorageMap() {
        Map<String, Map<Long, Object>> storageMap = new HashMap<>();
        Map<Long, Object> trainerMap = new HashMap<>();
        Trainer trainer = new Trainer();
        trainer.setUserName("testUsername");
        trainerMap.put(1L, trainer);
        storageMap.put("Trainer", trainerMap);
        return storageMap;
    }
}