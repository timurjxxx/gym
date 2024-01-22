package org.serviceTest;

import org.gym.dao.TraineeDAO;
import org.gym.model.Trainee;
import org.gym.model.User;
import org.gym.service.TraineeService;
import org.gym.service.UserService;
import org.gym.utils.Generate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TraineeServiceTest {

    @Mock
    private TraineeDAO traineeDAOMock;

    @Mock
    private UserService userServiceMock;

    @Mock
    private Generate generateMock;

    @InjectMocks
    private TraineeService traineeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSelectTrainee() {
        Long traineeId = 1L;
        Trainee expectedTrainee = new Trainee();
        when(traineeDAOMock.get("Trainee", traineeId)).thenReturn(expectedTrainee);

        Trainee result = traineeService.selectTrainee(traineeId);

        assertEquals(expectedTrainee, result);
        verify(traineeDAOMock).get("Trainee", traineeId);
    }

    @Test
    void testCreateTrainee() {
        Long userId = 1L;
        Trainee newTrainee = new Trainee();
        when(generateMock.generateUniqueId("Trainee")).thenReturn(123L);
        when(userServiceMock.selectUser(userId)).thenReturn(new User());
        when(traineeDAOMock.save("Trainee", newTrainee)).thenReturn(newTrainee);

        Trainee result = traineeService.createTrainee(newTrainee, userId);

        assertEquals(newTrainee, result);
        assertEquals(123L, result.getId());
        verify(generateMock).generateUniqueId("Trainee");
        verify(userServiceMock).selectUser(userId);
        verify(traineeDAOMock).save("Trainee", newTrainee);
    }

    @Test
    void testUpdateTrainee() {
        Long traineeId = 1L;
        Trainee updatedTrainee = new Trainee();
        when(traineeDAOMock.update("Trainee", traineeId, updatedTrainee)).thenReturn(updatedTrainee);

        Trainee result = traineeService.updateTrainee(traineeId, updatedTrainee);

        assertEquals(updatedTrainee, result);
        verify(traineeDAOMock).update("Trainee", traineeId, updatedTrainee);
    }

    @Test
    void testDeleteTrainee() {
        Long traineeId = 1L;

        traineeService.deleteTrainee(traineeId);

        verify(traineeDAOMock).delete("Trainee", traineeId);
    }
}