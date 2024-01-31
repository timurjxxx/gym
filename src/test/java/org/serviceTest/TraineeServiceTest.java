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
import java.util.Date;
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
        String  password = "testpassword";
        when(traineeDAO.findTraineeByUserUserName(username)).thenReturn(Optional.of(new Trainee()));

        Trainee selectedTrainee = traineeService.selectTraineeByUserName(username, password);

        assertNotNull(selectedTrainee);

        verify(traineeDAO, times(1)).findTraineeByUserUserName(username);
    }



    @Test
    void testUpdateTrainee() {
        String username = "john.doe";
        String password = "password";

        Trainee existingTrainee = new Trainee();
        existingTrainee.setId(1L);
        existingTrainee.setDateOfBirth(new Date());
        existingTrainee.setAddress("123 Main St");

        Trainee updatedTrainee = new Trainee();
        updatedTrainee.setDateOfBirth(new Date());
        updatedTrainee.setAddress("456 Oak St");

        when(traineeDAO.findTraineeByUserUserName(username)).thenReturn(Optional.of(existingTrainee));
        when(traineeDAO.save(any(Trainee.class))).thenAnswer(invocation -> {
            Trainee savedTrainee = invocation.getArgument(0);
            return savedTrainee;
        });

        Trainee result = traineeService.updateTrainee(username, password, updatedTrainee);

        assertNotNull(result);
        assertEquals(updatedTrainee.getDateOfBirth(), result.getDateOfBirth());
        assertEquals(updatedTrainee.getAddress(), result.getAddress());

        verify(traineeDAO, times(1)).findTraineeByUserUserName(username);
        verify(traineeDAO, times(1)).save(any(Trainee.class));
    }
    @Test
    void testUpdateTraineeTrainersList() {
        String username = "john.doe";
        String password = "password";

        Trainee existingTrainee = new Trainee();
        existingTrainee.setId(1L);

        Set<Trainer> updatedList = new HashSet<>();
        Trainer trainer1 = new Trainer();
        trainer1.setId(1L);
        Trainer trainer2 = new Trainer();
        trainer2.setId(2L);
        updatedList.add(trainer1);
        updatedList.add(trainer2);

        when(traineeDAO.findTraineeByUserUserName(username)).thenReturn(Optional.of(existingTrainee));
        when(traineeDAO.save(any(Trainee.class))).thenAnswer(invocation -> {
            Trainee savedTrainee = invocation.getArgument(0);
            return savedTrainee;
        });

        Trainee result = traineeService.updateTraineeTrainersList(username, password, updatedList);

        assertNotNull(result);
        assertEquals(updatedList, result.getTrainers());

        verify(traineeDAO, times(1)).findTraineeByUserUserName(username);
        verify(traineeDAO, times(1)).save(any(Trainee.class));
    }

    @Test
    void testChangePassword_Success() {
        String username = "testUser";
        String newPassword = "newPassword";
        String  existpassword = "existpassword";
        Trainee trainee = new Trainee();
        trainee.setUser(new User());
        when(traineeDAO.findTraineeByUserUserName(username)).thenReturn(Optional.of(trainee));
        when(userService.changePassword(username, newPassword)).thenReturn("encryptedPassword");

        traineeService.changePassword(username, existpassword, newPassword);

        assertEquals("encryptedPassword", trainee.getUser().getPassword());
        verify(traineeDAO, times(1)).findTraineeByUserUserName(username);
        verify(userService, times(1)).changePassword(username, newPassword);
    }

    @Test
    void testChangeStatus_Success() {
        String username = "testUser";
        String password = "password";
        Trainee trainee = new Trainee();
        trainee.setUser(new User());
        when(traineeDAO.findTraineeByUserUserName(username)).thenReturn(Optional.of(trainee));
        when(userService.changeStatus(username)).thenReturn(true);

        traineeService.changeStatus(username , password);

        assertTrue(trainee.getUser().getIsActive());
        verify(traineeDAO, times(1)).findTraineeByUserUserName(username);
        verify(userService, times(1)).changeStatus(username);
    }

}