package org.serviceTest;

import org.gym.dao.TraineeDAO;
import org.gym.dao.TrainerDAO;
import org.gym.memory.InMemoryStorage;
import org.gym.model.User;
import org.gym.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class UserServiceTest {


    @Mock
    private InMemoryStorage storage;

    @Mock
    private TraineeDAO traineeDAO;

    @Mock
    private TrainerDAO trainerDAO;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void generateUsernameForTraineeTest() {
        String nameSpace = "Trainee";
        String baseUsername = "testUser";

        when(traineeDAO.findByUsername(eq(nameSpace), anyString())).thenReturn(Optional.empty());

        String username = userService.generateUsernameFor(nameSpace, baseUsername);

        assertNotNull(username);
        assertTrue(username.startsWith(baseUsername));
        verify(traineeDAO, atLeastOnce()).findByUsername(eq(nameSpace), anyString());
    }

    @Test
    void generateUsernameForTrainerTest() {
        String nameSpace = "Trainer";
        String baseUsername = "testUser";

        when(trainerDAO.findByUsername(eq(nameSpace), anyString())).thenReturn(Optional.empty());

        String username = userService.generateUsernameFor(nameSpace, baseUsername);

        assertNotNull(username);
        assertTrue(username.startsWith(baseUsername));
        verify(trainerDAO, atLeastOnce()).findByUsername(eq(nameSpace), anyString());
    }


    @Test
    void generatePasswordTest() {
        String password = userService.generatePassword();

        assertNotNull(password);
        assertEquals(10, password.length());
        assertTrue(password.matches("[A-Za-z0-9]+"));
    }
}
