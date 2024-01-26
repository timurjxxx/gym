package org.service;
import org.gym.dao.TraineeDAO;
import org.gym.dao.UserDAO;
import org.gym.model.Trainee;
import org.gym.model.Trainer;
import org.gym.model.User;
import org.gym.service.TraineeService;
import org.gym.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;


public class TraineeServiceTest {
    @Mock
    private TraineeDAO traineeDAO;

    @Mock
    private UserService userService;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private TraineeService traineeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

    }

    @Test
    void testSelectTraineeByUsername() {
        String username = "testUser";
        String password = "testPassword";

        Trainee mockedTrainee = new Trainee();
        mockedTrainee.setId(1L);
        mockedTrainee.setUser(new User());

        when(traineeDAO.findTraineeByUserUserName(anyString())).thenReturn(Optional.of(mockedTrainee));

        Trainee selectedTrainee = traineeService.selectTraineeByUsername(username, password);

        assertEquals(mockedTrainee, selectedTrainee);
    }
    @Test
    void testCreateTrainee() {
        // Mocking
        Long userId = 1L;
        Date dateOfBirth = new Date();
        String address = "123 Main Street";

        Trainee trainee = new Trainee();
        trainee.setDateOfBirth(dateOfBirth);
        trainee.setAddress(address);

        when(userService.findUserById(anyLong())).thenReturn(new User());
        when(traineeDAO.save(any())).thenReturn(trainee);

        Trainee createdTrainee = traineeService.createTrainee(trainee, userId);

        assertNotNull(createdTrainee);
        assertEquals(dateOfBirth, createdTrainee.getDateOfBirth());
        assertEquals(address, createdTrainee.getAddress());
        verify(userService, times(1)).findUserById(anyLong());
        verify(traineeDAO, times(1)).save(any());
    }


    @Test
    void testDeleteTraineeByUserName() {
        String username = "testUser";
        when(traineeDAO.findTraineeByUserUserName(anyString())).thenReturn(Optional.of(new Trainee()));

        assertDoesNotThrow(() -> traineeService.deleteTraineeByUserName(username, "password"));

        verify(traineeDAO, times(1)).findTraineeByUserUserName(anyString());
        verify(traineeDAO, times(1)).deleteById(any());
    }

    @Test
    void testUpdateTraineeTrainerList() {
        String username = "testUser";
        Set<Trainer> updatedList = new HashSet<>();

        Trainee trainee = new Trainee();
        when(traineeDAO.findTraineeByUserUserName(anyString())).thenReturn(Optional.of(trainee));
        when(traineeDAO.save(any())).thenReturn(trainee);

        Set<Trainer> result = traineeService.updateTraineeTrainerList(username, "password", updatedList);

        assertNotNull(result);
        assertEquals(updatedList, result);
        verify(traineeDAO, times(1)).findTraineeByUserUserName(anyString());
        verify(traineeDAO, times(1)).save(any());
    }



}
