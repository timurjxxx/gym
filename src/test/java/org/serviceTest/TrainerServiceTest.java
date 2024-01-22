package org.serviceTest;


import org.gym.dao.TrainerDAO;
import org.gym.model.Trainer;
import org.gym.service.TrainerService;
import org.gym.service.UserService;
import org.gym.utils.Generate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

class TrainerServiceTest {
    @Mock
    private TrainerDAO trainerDAO;

    @Mock
    private UserService userService;

    @Mock
    private Generate generate;

    @InjectMocks
    private TrainerService trainerService;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        trainerService = new TrainerService(trainerDAO, userService, generate);


    }
    @Test
    public void testCreateTrainer() {
        // Arrange
        assertNotNull(trainerService);
        assertNotNull(trainerDAO);
        assertNotNull(userService);
        assertNotNull(generate);
    }

    @Test
    public void testSelectTrainer() {
        Long trainerId = 1L;
        Trainer expectedTrainer = new Trainer();

        when(trainerDAO.get("Trainer", trainerId)).thenReturn(expectedTrainer);

        Trainer result = trainerService.selectTrainer(trainerId);

        assertEquals(expectedTrainer, result);
    }

    @Test
    void testUpdateTrainer() {
        Long trainerId = 1L;
        Trainer updatedTrainer = new Trainer();

        when(trainerDAO.update("Trainer", trainerId, updatedTrainer)).thenReturn(updatedTrainer);

        Trainer result = trainerService.updateTrainer(trainerId, updatedTrainer);

        assertEquals(trainerId, result.getId());
        verify(trainerDAO).update("Trainer", trainerId, updatedTrainer);
    }

    @Test
    public void testDeleteTrainer() {
        Long trainerId = 1L;

        trainerService.deleteTrainer(trainerId);

        verify(trainerDAO).delete("Trainer", trainerId);
    }
}