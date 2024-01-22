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
    void testSave() {
        String nameSpace = "Trainer";
        Trainer newTrainer = new Trainer();

        when(storage.save(eq(nameSpace), eq(newTrainer))).thenReturn(newTrainer);

        Trainer result = trainerDAO.save(nameSpace, newTrainer);

        assertEquals(newTrainer, result);
        verify(storage, times(1)).save(eq(nameSpace), eq(newTrainer));
    }

    @Test
    void testGet() {
        String nameSpace = "Trainer";
        Long trainerId = 1L;
        Trainer expectedTrainer = new Trainer();

        when(storage.get(eq(nameSpace), eq(trainerId))).thenReturn(expectedTrainer);

        Trainer result = trainerDAO.get(nameSpace, trainerId);

        assertEquals(expectedTrainer, result);
        verify(storage, times(1)).get(eq(nameSpace), eq(trainerId));
    }

    @Test
    void testDelete() {
        String nameSpace = "Trainer";
        Long trainerId = 1L;

        trainerDAO.delete(nameSpace, trainerId);

        verify(storage, times(1)).deleteById(eq(nameSpace), eq(trainerId));
    }

    @Test
    void testUpdate() {
        String nameSpace = "Trainer";
        Long trainerId = 1L;
        Trainer updatedTrainer = new Trainer();

        when(storage.update(eq(nameSpace), eq(trainerId), eq(updatedTrainer))).thenReturn(updatedTrainer);

        Trainer result = trainerDAO.update(nameSpace, trainerId, updatedTrainer);

        assertEquals(updatedTrainer, result);
        verify(storage, times(1)).update(eq(nameSpace), eq(trainerId), eq(updatedTrainer));
    }


}