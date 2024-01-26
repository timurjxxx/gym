package org.service;
import org.gym.dao.TrainerDAO;
import org.gym.dao.UserDAO;
import org.gym.model.Trainee;
import org.gym.model.Trainer;
import org.gym.model.Training;
import org.gym.service.TraineeService;
import org.gym.service.TrainerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
public class TrainerServiceTest {
    @Mock
    private TrainerDAO trainerDAO;

    @Mock
    private TraineeService traineeService;
    @Mock
    private UserDAO userDAO;


    @Mock
    private Trainer mockedTrainer;

    @InjectMocks
    private TrainerService trainerService;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

    }
    @Test
    void testCreateTrainer() {
        // Mocking
        Long userId = 1L;

        // Set values using setters on the mockedTrainer object
        mockedTrainer.setSpecialization("Fitness Trainer");
        mockedTrainer.setId(1L);  // Set other fields as needed

        when(userDAO.findById(userId)).thenReturn(Optional.ofNullable(mockedTrainer.getUser()));
        when(trainerDAO.save(any())).thenReturn(mockedTrainer);

        // Test
        Trainer createdTrainer = trainerService.createTrainer(mockedTrainer, userId);

        // Assertions
        assertNotNull(createdTrainer);
        assertEquals(mockedTrainer, createdTrainer);
        verify(userDAO, times(1)).findById(userId);
        verify(trainerDAO, times(1)).save(any());
    }
    @Test
    void testSelectTrainerByUserName() {
        // Mocking
        String username = "testUser";
        String password = "testPassword";
        when(trainerDAO.findTrainerByUserUserName(username)).thenReturn(Optional.of(mockedTrainer));

        // Test
        Trainer selectedTrainer = trainerService.selectTrainerByUserName(username, password);

        // Assertions
        assertNotNull(selectedTrainer);
        assertEquals(mockedTrainer, selectedTrainer);
    }



}
