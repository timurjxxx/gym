package org.serviceTest;


import org.gym.dao.TrainerDAO;
import org.gym.model.Trainer;
import org.gym.service.TrainerService;
import org.gym.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TrainerServiceTest {

    @Mock
    private TrainerDAO trainerDAO;

    @Mock
    private UserService userService;
    @InjectMocks
    private TrainerService trainerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateTrainer() {
        Trainer newTrainer = new Trainer();
        when(trainerDAO.save(any(Trainer.class))).thenAnswer(invocation -> {
            Trainer savedTrainer = invocation.getArgument(0);
            savedTrainer.setUserName("testUsername");
            savedTrainer.setPassword("testPassword");
            return savedTrainer;
        });

        Trainer result = trainerService.createTrainer(newTrainer);

        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getUserName());
        assertNotNull(result.getPassword());
        verify(trainerDAO, times(1)).save(any(Trainer.class));
    }

    @Test
    void testSelectTrainer() {
        Long trainerId = 1L;
        when(trainerDAO.get(trainerId)).thenReturn(new Trainer());

        Trainer result = trainerService.selectTrainer(trainerId);

        assertNotNull(result);
        verify(trainerDAO, times(1)).get(trainerId);
    }

    @Test
    void testUpdateTrainer() {
        Long trainerId = 1L;
        Trainer updatedTrainer = new Trainer();
        when(trainerDAO.get(trainerId)).thenReturn(new Trainer());
        when(trainerDAO.save(any(Trainer.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Trainer result = trainerService.updateTrainer(trainerId, updatedTrainer);

        assertNotNull(result);
        verify(trainerDAO, times(1)).get(trainerId);
        verify(trainerDAO, times(1)).save(any(Trainer.class));
    }

    @Test
    void testSelectAllTrainers() {
        Map<Long, Object> trainerMap = new HashMap<>();
        when(trainerDAO.getAll()).thenReturn(trainerMap);

        Map<Long, Object> result = trainerService.selectAllTrainers();

        assertNotNull(result);
        verify(trainerDAO, times(1)).getAll();
    }
}