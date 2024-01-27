package org.serviceTest;

import org.gym.dao.TraineeDAO;
import org.gym.dao.TrainerDAO;
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

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TraineeServiceTest {

    @Mock
    private UserService userService;


    @Mock
    private UserDAO userDAO;

    @Mock
    private TraineeDAO traineeDAO;

    @Mock
    private TrainerDAO trainerDAO;


    @InjectMocks
    private TraineeService traineeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateTrainee() {
        Trainee trainee = new Trainee();
        trainee.setId(1L);
        trainee.setUser(new User());
        Long userId = 2L;

        when(trainerDAO.existsTrainerByUser_Id(userId)).thenReturn(false);
        when(traineeDAO.existsTraineeByUser_Id(userId)).thenReturn(false);
        when(userDAO.findById(userId)).thenReturn(Optional.of(new User()));
        when(traineeDAO.save(any(Trainee.class))).thenReturn(trainee);

        Trainee createdTrainee = traineeService.createTrainee(trainee, userId);

        assertNotNull(createdTrainee);
        assertEquals(trainee.getId(), createdTrainee.getId());

        verify(trainerDAO, times(1)).existsTrainerByUser_Id(userId);
        verify(traineeDAO, times(1)).existsTraineeByUser_Id(userId);
        verify(userDAO, times(1)).findById(userId);
        verify(traineeDAO, times(1)).save(any(Trainee.class));
    }

    @Test
    void testCreateTrainee_TrainerExists() {
        Long userId = 2L;

        when(trainerDAO.existsTrainerByUser_Id(userId)).thenReturn(true);

        assertThrows(EntityExistsException.class, () -> traineeService.createTrainee(new Trainee(), userId));

        verify(trainerDAO, times(1)).existsTrainerByUser_Id(userId);
        verify(traineeDAO, never()).existsTraineeByUser_Id(userId);
        verify(userDAO, never()).findById(userId);
        verify(traineeDAO, never()).save(any(Trainee.class));
    }

    @Test
    void testCreateTrainee_TraineeExists() {
        Long userId = 2L;

        when(trainerDAO.existsTrainerByUser_Id(userId)).thenReturn(false);
        when(traineeDAO.existsTraineeByUser_Id(userId)).thenReturn(true);

        assertThrows(EntityExistsException.class, () -> traineeService.createTrainee(new Trainee(), userId));

        verify(trainerDAO, times(1)).existsTrainerByUser_Id(userId);
        verify(traineeDAO, times(1)).existsTraineeByUser_Id(userId);
        verify(userDAO, never()).findById(userId);
        verify(traineeDAO, never()).save(any(Trainee.class));
    }

    @Test
    void testSelectTraineeByUserName() {
        String username = "testTrainee";
        when(traineeDAO.findTraineeByUserUserName(username)).thenReturn(Optional.of(new Trainee()));

        Trainee selectedTrainee = traineeService.selectTraineeByUserName(username);

        assertNotNull(selectedTrainee);

        verify(traineeDAO, times(1)).findTraineeByUserUserName(username);
    }
    @Test
    void testDeleteTraineeByUserName_Success() {
        String username = "testUser";
        Trainee trainee = new Trainee();
        when(traineeDAO.findTraineeByUserUserName(username)).thenReturn(Optional.of(trainee));

        traineeService.deleteTraineeByUserName(username);

        verify(traineeDAO, times(1)).deleteTraineeByUserUserName(username);
    }
    @Test
    void testUpdateTraineeTrainersList_Success() {
        Long traineeId = 1L;
        Trainee trainee = new Trainee();
        trainee.setId(traineeId);

        Set<Trainer> updatedTrainers = new HashSet<>();
        Trainer trainer1 = new Trainer();
        Trainer trainer2 = new Trainer();
        updatedTrainers.add(trainer1);
        updatedTrainers.add(trainer2);

        when(traineeDAO.findById(traineeId)).thenReturn(Optional.of(trainee));
        when(traineeDAO.save(trainee)).thenReturn(trainee); // mock the save method
        Trainee result = traineeService.updateTraineeTrainersList(traineeId, updatedTrainers);

        assertNotNull(result);
        assertEquals(updatedTrainers, result.getTrainers());
        verify(traineeDAO, times(1)).findById(traineeId);
        verify(traineeDAO, times(1)).save(trainee);
    }


    @Test
    void testChangePassword_Success() {
        String username = "testUser";
        String newPassword = "newPassword";
        Trainee trainee = new Trainee();
        trainee.setUser(new User());
        when(traineeDAO.findTraineeByUserUserName(username)).thenReturn(Optional.of(trainee));
        when(userService.changePassword(username, newPassword)).thenReturn("encryptedPassword");

        traineeService.changePassword(username, newPassword);

        assertEquals("encryptedPassword", trainee.getUser().getPassword());
        verify(traineeDAO, times(1)).findTraineeByUserUserName(username);
        verify(userService, times(1)).changePassword(username, newPassword);
    }

    @Test
    void testChangeStatus_Success() {
        String username = "testUser";
        Trainee trainee = new Trainee();
        trainee.setUser(new User());
        when(traineeDAO.findTraineeByUserUserName(username)).thenReturn(Optional.of(trainee));
        when(userService.changeStatus(username)).thenReturn(true);

        traineeService.changeStatus(username);

        assertTrue(trainee.getUser().getIsActive());
        verify(traineeDAO, times(1)).findTraineeByUserUserName(username);
        verify(userService, times(1)).changeStatus(username);
    }

}