package org.daoTest;

import org.gym.dao.TraineeDAO;
import org.gym.memory.InMemoryStorage;
import org.gym.model.Trainee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TraineeDAOTest {

    @Mock
    private InMemoryStorage storageMock;

    @InjectMocks
    private TraineeDAO traineeDAO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveTrainee() {
        String nameSpace = "Trainee";
        Trainee newTrainee = new Trainee();
        when(storageMock.save(nameSpace, newTrainee)).thenReturn(newTrainee);
        Trainee result = traineeDAO.save(nameSpace, newTrainee);
        assertNotNull(result);
        assertEquals(newTrainee, result);
        verify(storageMock).save(nameSpace, newTrainee);
    }

    @Test
    void testGetTrainee() {
        String nameSpace = "Trainee";
        Long traineeId = 1L;
        Trainee storedTrainee = new Trainee();
        when(storageMock.get(nameSpace, traineeId)).thenReturn(storedTrainee);
        Trainee result = traineeDAO.get(nameSpace, traineeId);
        assertNotNull(result);
        assertEquals(storedTrainee, result);
        verify(storageMock).get(nameSpace, traineeId);
    }

    @Test
    void testDeleteTrainee() {
        String nameSpace = "Trainee";
        Long traineeId = 1L;
        traineeDAO.delete(nameSpace, traineeId);
        verify(storageMock).deleteById(nameSpace, traineeId);
    }

    @Test
    void testUpdateTrainee() {
        String nameSpace = "Trainee";
        Long traineeId = 1L;
        Trainee newTrainee = new Trainee();
        when(storageMock.update(nameSpace, traineeId, newTrainee)).thenReturn(newTrainee);
        Trainee result = traineeDAO.update(nameSpace, traineeId, newTrainee);
        assertNotNull(result);
        assertEquals(newTrainee, result);
        verify(storageMock).update(nameSpace, traineeId, newTrainee);
    }
}