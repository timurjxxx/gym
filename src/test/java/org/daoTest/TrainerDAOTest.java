package org.daoTest;

import org.gym.dao.TrainerDAO;
import org.gym.memory.InMemoryStorage;
import org.gym.model.Trainer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import java.util.Collections;
import java.util.Optional;

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
    void saveTest() {
        Long trainerId = 1L;
        Trainer newTrainer = new Trainer();
        newTrainer.setId(trainerId);
        newTrainer.setFirstName("John");
        newTrainer.setLastName("Doe");
        newTrainer.setPassword("password123");
        newTrainer.setUserName("john.doe");
        newTrainer.setSpecialization("Runner");

        when(storage.save("Trainer", newTrainer)).thenReturn(newTrainer);

        Trainer result = trainerDAO.save("Trainer", newTrainer);

        assertEquals(newTrainer, result);
        assertEquals(trainerId, result.getId());
        assertEquals("John", result.getFirstName());
        assertEquals("Doe", result.getLastName());
        assertEquals("password123", result.getPassword());
        assertEquals("john.doe", result.getUserName());
        assertEquals("Runner", result.getSpecialization());
        verify(storage).save("Trainer", newTrainer);
    }

    @Test
    void getTest() {
        Long trainerId = 1L;
        Trainer expectedTrainer = new Trainer();
        expectedTrainer.setId(trainerId);
        expectedTrainer.setFirstName("John");
        expectedTrainer.setLastName("Doe");
        expectedTrainer.setPassword("password123");
        expectedTrainer.setUserName("john.doe");
        expectedTrainer.setSpecialization("Runner");

        when(storage.findById("Trainer", trainerId)).thenReturn(expectedTrainer);

        Trainer result = trainerDAO.get("Trainer", trainerId);

        assertEquals(expectedTrainer, result);
        assertEquals(trainerId, result.getId());
        assertEquals("John", result.getFirstName());
        assertEquals("Doe", result.getLastName());
        assertEquals("password123", result.getPassword());
        assertEquals("john.doe", result.getUserName());
        assertEquals("Runner", result.getSpecialization());

        verify(storage).findById("Trainer", trainerId);
    }

    @Test
    void findByUsernameTest() {
        // Arrange
        String username = "testUser";
        Trainer trainer = new Trainer();
        trainer.setUserName(username);
        trainer.setFirstName("John");
        trainer.setLastName("Doe");
        trainer.setPassword("password123");
        trainer.setSpecialization("Runner");

        when(storage.findAll("Trainer")).thenReturn(Collections.singletonList(trainer));

        Optional<Trainer> result = trainerDAO.findByUsername("Trainer", username);

        assertTrue(result.isPresent());
        assertEquals(trainer, result.get());
        assertEquals("testUser", result.get().getUserName());
        assertEquals("John", result.get().getFirstName());
        assertEquals("Doe", result.get().getLastName());
        assertEquals("password123", result.get().getPassword());
        assertEquals("Runner", result.get().getSpecialization());

        verify(storage).findAll("Trainer");
    }

    @Test
    void deleteTest() {
        Long trainerId = 1L;

        trainerDAO.delete("Trainer", trainerId);

        verify(storage).deleteById("Trainer", trainerId);
    }


}