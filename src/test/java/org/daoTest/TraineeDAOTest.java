package org.daoTest;

import org.gym.dao.TraineeDAO;
import org.gym.memory.InMemoryStorage;
import org.gym.model.Trainee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import java.util.Collections;
import java.util.Date;
import java.util.Optional;

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
    void findByIdTest() {
        Long traineeId = 1L;

        Trainee expectedTrainee = new Trainee();
        expectedTrainee.setId(traineeId);
        expectedTrainee.setFirstName("John");
        expectedTrainee.setLastName("Doe");
        expectedTrainee.setAddress("123 Main St");
        expectedTrainee.setPassword("password123");
        expectedTrainee.setUserName("john.doe");
        expectedTrainee.setDateOfBirth(new Date());
        expectedTrainee.setIsActive(true);

        when(storage.findById("Trainee", traineeId)).thenReturn(expectedTrainee);

        Trainee result = traineeDAO.get("Trainee", traineeId);

        assertEquals(expectedTrainee, result);
        assertEquals("john.doe", result.getUserName());
        assertEquals("password123", result.getPassword());
        verify(storage).findById("Trainee", traineeId);
    }

    @Test
    void saveTest() {
        Trainee newTrainee = new Trainee();
        newTrainee.setId(1L);
        newTrainee.setFirstName("John");
        newTrainee.setLastName("Doe");
        newTrainee.setAddress("123 Main St");
        newTrainee.setPassword("password123");
        newTrainee.setUserName("john.doe");
        newTrainee.setIsActive(true);

        when(storage.save("Trainee", newTrainee)).thenReturn(newTrainee);

        Trainee result = traineeDAO.save("Trainee", newTrainee);

        assertEquals(newTrainee, result);
        assertEquals(1L, result.getId());
        assertEquals("John", result.getFirstName());
        assertEquals("Doe", result.getLastName());
        assertEquals("123 Main St", result.getAddress());
        assertEquals("password123", result.getPassword());
        assertEquals("john.doe", result.getUserName());
        assertTrue(result.getIsActive());
        verify(storage).save("Trainee", newTrainee);
    }

    @Test
    void deleteTest() {
        Long traineeId = 1L;

        traineeDAO.delete("Trainee", traineeId);

        verify(storage).deleteById("Trainee", traineeId);
    }

    @Test
    void findByUsernameTest() {
        String username = "testUser";

        Trainee trainee = new Trainee();
        trainee.setId(1L);
        trainee.setFirstName("John");
        trainee.setLastName("Doe");
        trainee.setAddress("123 Main St");
        trainee.setPassword("password123");
        trainee.setUserName(username);
        trainee.setIsActive(true);

        when(storage.findAll("Trainee")).thenReturn(Collections.singletonList(trainee));

        Optional<Trainee> result = traineeDAO.findByUsername("Trainee", username);

        assertTrue(result.isPresent());
        assertEquals(trainee, result.get());
        assertEquals(1L, result.get().getId());
        assertEquals("John", result.get().getFirstName());
        assertEquals("Doe", result.get().getLastName());
        assertEquals("123 Main St", result.get().getAddress());
        assertEquals("password123", result.get().getPassword());
        assertEquals(username, result.get().getUserName());
        assertTrue(result.get().getIsActive());
        verify(storage).findAll("Trainee");
    }

    @Test
    void findByUsernameThrowException() {
        String username = "nonExistentUser";
        when(storage.findAll("Trainee")).thenReturn(Collections.emptyList());

        Optional<Trainee> result = traineeDAO.findByUsername("Trainee", username);

        assertTrue(result.isEmpty());
        verify(storage).findAll("Trainee");
    }

}