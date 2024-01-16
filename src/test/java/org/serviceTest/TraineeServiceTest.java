package org.serviceTest;

import org.gym.dao.TraineeDAO;
import org.gym.model.Trainee;
import org.gym.service.TraineeService;
import org.gym.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TraineeServiceTest {

    @Mock
    private TraineeDAO traineeDAO;

    @Mock
    private UserService userService;

    @InjectMocks
    private TraineeService traineeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSelectTrainee() {
        Long traineeId = 1L;
        when(traineeDAO.get(traineeId)).thenReturn(new Trainee());

        Trainee result = traineeService.selectTrainee(traineeId);

        assertNotNull(result);
        verify(traineeDAO, times(1)).get(traineeId);
    }

    @Test
    void testCreateTrainee() {
        Trainee newTrainee = new Trainee();
        when(traineeDAO.save(any(Trainee.class))).thenAnswer(invocation -> {
            Trainee savedTrainee = invocation.getArgument(0);
            savedTrainee.setUserName("testUsername");
            savedTrainee.setPassword("testPassword");
            return savedTrainee;
        });

        Trainee result = traineeService.createTrainee(newTrainee);

        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getUserName());
        assertNotNull(result.getPassword());
        verify(traineeDAO, times(1)).save(any(Trainee.class));
    }

    @Test
    void testUpdateTrainee() {
        Long traineeId = 1L;
        Trainee newTrainee = new Trainee();
        when(traineeDAO.get(traineeId)).thenReturn(new Trainee());
        when(traineeDAO.save(any(Trainee.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Trainee result = traineeService.updateTrainee(traineeId, newTrainee);

        assertNotNull(result);
        verify(traineeDAO, times(1)).get(traineeId);
        verify(traineeDAO, times(1)).save(any(Trainee.class));
    }

    @Test
    void testDeleteTrainee() {
        Long traineeId = 1L;

        traineeService.deleteTrainee(traineeId);

        verify(traineeDAO, times(1)).delete(traineeId);
    }

    @Test
    void testSelectAllTrainees() {
        Map<Long, Object> traineeMap = new HashMap<>();
        when(traineeDAO.getAll()).thenReturn(traineeMap);

        Map<Long, Object> result = traineeService.selectAllTrainees();

        assertNotNull(result);
        verify(traineeDAO, times(1)).getAll();
    }
}