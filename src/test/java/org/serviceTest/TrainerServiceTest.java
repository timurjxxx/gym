package org.serviceTest;

import org.gym.dao.TraineeDAO;
import org.gym.dao.TrainerDAO;
import org.gym.dao.UserDAO;
import org.gym.model.Trainee;
import org.gym.model.Trainer;
import org.gym.model.User;
import org.gym.service.TrainerService;
import org.gym.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.persistence.EntityExistsException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TrainerServiceTest {

    @Mock
    private TrainerDAO trainerDAO;

    @Mock
    private UserService userService;


    @Mock
    private TraineeDAO traineeDAO;

    @Mock
    private UserDAO userDAO;

    @InjectMocks
    private TrainerService trainerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateTrainer_Success() {
        // Arrange
        Trainer trainer = new Trainer();
        trainer.setId(1L);
        trainer.setUser(new User());
        Long userId = 2L;

        when(trainerDAO.existsTrainerByUser_Id(userId)).thenReturn(false);
        when(traineeDAO.existsTraineeByUser_Id(userId)).thenReturn(false);
        when(userDAO.findById(userId)).thenReturn(Optional.of(new User()));
        when(trainerDAO.save(any(Trainer.class))).thenReturn(trainer);

        Trainer createdTrainer = trainerService.createTrainer(trainer, userId);

        assertNotNull(createdTrainer);
        assertEquals(trainer.getId(), createdTrainer.getId());

        verify(trainerDAO, times(1)).existsTrainerByUser_Id(userId);
        verify(traineeDAO, times(1)).existsTraineeByUser_Id(userId);
        verify(userDAO, times(1)).findById(userId);
        verify(trainerDAO, times(1)).save(any(Trainer.class));

        assertDoesNotThrow(() -> {
            Trainer result = trainerService.createTrainer(trainer, userId);
            assertNotNull(result);
        });
    }
    @Test
    void testSelectTrainerByUserName() {
        String username = "testTrainer";
        String password = "password";
        when(trainerDAO.findTrainerByUserUserName(username)).thenReturn(Optional.of(new Trainer()));

        Trainer selectedTrainer = trainerService.selectTrainerByUserName(username, password);

        assertNotNull(selectedTrainer);

        verify(trainerDAO, times(1)).findTrainerByUserUserName(username);
    }

    @Test
    void testGetNotAssignedActiveTrainers() {
        String username = "testUser";
        List<Trainer> trainers = Arrays.asList(new Trainer(), new Trainer(), new Trainer());

        when(trainerDAO.getNotAssignedActiveTrainers(username)).thenReturn(trainers);

        List<Trainer> result = trainerService.getNotAssignedActiveTrainers(username);

        assertNotNull(result);
        assertEquals(trainers.size(), result.size());
        verify(trainerDAO, times(1)).getNotAssignedActiveTrainers(username);
    }
    @Test
    public void testUpdateTrainer() {
        Trainer existingTrainer = new Trainer();
        User existingUser = new User();
        existingUser.setUserName("existingUsername");
        existingUser.setPassword("existingPassword");
        existingTrainer.setUser(existingUser);
        existingTrainer.setSpecialization("Existing Specialization");

        Trainer updatedTrainer = new Trainer();
        updatedTrainer.setSpecialization("Updated Specialization");

        when(trainerDAO.findTrainerByUserUserName("existingUsername")).thenReturn(Optional.of(existingTrainer));
        when(trainerDAO.save(existingTrainer)).thenReturn(existingTrainer);

        Trainer resultTrainer = trainerService.updateTrainer("existingUsername", "existingPassword", updatedTrainer);

        assertNotNull(resultTrainer);
        assertEquals("Updated Specialization", resultTrainer.getSpecialization());
        verify(trainerDAO, times(1)).findTrainerByUserUserName("existingUsername");
        verify(trainerDAO, times(1)).save(existingTrainer);
    }

    @Test
    void testChangePassword_Success() {
        String username = "testUser";
        String password = "password";

        String newPassword = "newPassword";
        Trainer trainer = new Trainer();
        trainer.setUser(new User());
        when(trainerDAO.findTrainerByUserUserName(username)).thenReturn(Optional.of(trainer));
        when(userService.changePassword(username, newPassword)).thenReturn("encryptedPassword");

        trainerService.changePassword(username,password, newPassword);

        assertEquals("encryptedPassword", trainer.getUser().getPassword());
        verify(trainerDAO, times(1)).findTrainerByUserUserName(username);
        verify(userService, times(1)).changePassword(username, newPassword);
    }

    @Test
    void testChangeStatus_Success() {
        String username = "testUser";
        String password = "password";
        Trainer trainer = new Trainer();
        trainer.setUser(new User());
        when(trainerDAO.findTrainerByUserUserName(username)).thenReturn(Optional.of(trainer));
        when(userService.changeStatus(username)).thenReturn(true);

        trainerService.changeStatus(username, password);

        assertTrue(trainer.getUser().getIsActive());
        verify(trainerDAO, times(1)).findTrainerByUserUserName(username);
        verify(userService, times(1)).changeStatus(username);
    }


}
