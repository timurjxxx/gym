package org.serviceTest;

import org.gym.dao.TraineeDAO;
import org.gym.model.Trainee;
import org.gym.model.User;
import org.gym.service.TraineeService;
import org.gym.service.UserService;
import org.gym.utils.GenerateId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;


import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TraineeServiceTest {
    @Mock
    private TraineeDAO traineeDAO;

    @Mock
    private UserService userService;

    @Mock
    private GenerateId generateId;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private TraineeService traineeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void selectTraineeTest() {
        Long traineeId = 1L;
        Trainee expectedTrainee = new Trainee();
        expectedTrainee.setId(traineeId);
        expectedTrainee.setFirstName("John");
        expectedTrainee.setLastName("Doe");
        expectedTrainee.setAddress("123 Main St");
        expectedTrainee.setPassword("password123");
        expectedTrainee.setUserName("john.doe");
        expectedTrainee.setDateOfBirth(new Date());
        expectedTrainee.setIsActive(true);

        when(traineeDAO.get("Trainee", traineeId)).thenReturn(expectedTrainee);

        Trainee result = traineeService.selectTrainee(traineeId);

        assertNotNull(result);
        assertEquals(expectedTrainee.getId(), result.getId());
        assertEquals(expectedTrainee.getFirstName(), result.getFirstName());
        assertEquals(expectedTrainee.getLastName(), result.getLastName());
        assertEquals(expectedTrainee.getAddress(), result.getAddress());
        assertEquals(expectedTrainee.getPassword(), result.getPassword());
        assertEquals(expectedTrainee.getUserName(), result.getUserName());
        assertEquals(expectedTrainee.getDateOfBirth(), result.getDateOfBirth());
        assertEquals(expectedTrainee.getIsActive(), result.getIsActive());

        verify(traineeDAO).get("Trainee", traineeId);
    }


    @Test
    void createTraineeTest() {
        // Arrange
        Trainee newTrainee = new Trainee();
        newTrainee.setFirstName("John");
        newTrainee.setLastName("Doe");
        newTrainee.setAddress("123 Main St");
        newTrainee.setDateOfBirth(new Date());
        newTrainee.setIsActive(true);

        // Замокаем поведение UserService
        when(generateId.generateUniqueId("Trainee")).thenReturn(1L);
        when(userService.generateUsernameFor("Trainee", newTrainee.getFirstName())).thenReturn("john");
        when(userService.generatePassword()).thenReturn("password123");
        when(traineeDAO.save("Trainee", newTrainee)).thenReturn(newTrainee);

        // Act
        Trainee result = traineeService.createTrainee(newTrainee);

        // Assert
        assertNotNull(result);
        assertEquals(newTrainee.getFirstName(), result.getFirstName());
        assertEquals(newTrainee.getLastName(), result.getLastName());
        assertEquals(newTrainee.getAddress(), result.getAddress());
        assertEquals(newTrainee.getPassword(), result.getPassword());
        assertEquals("john.Doe", result.getUserName());  // Обновлено для проверки корректного имени пользователя
        assertEquals(newTrainee.getDateOfBirth(), result.getDateOfBirth());
        assertEquals(newTrainee.getIsActive(), result.getIsActive());

        // Verify
        verify(generateId).generateUniqueId("Trainee");
        verify(userService).generateUsernameFor("Trainee", newTrainee.getFirstName());
        verify(userService).generatePassword();
        verify(traineeDAO).save("Trainee", newTrainee);
    }


    @Test
    void updateTraineeTest() {
        // Arrange
        Long traineeId = 1L;
        Trainee existingTrainee = new Trainee();
        existingTrainee.setId(traineeId);
        existingTrainee.setFirstName("John");
        existingTrainee.setLastName("Doe");
        existingTrainee.setAddress("123 Main St");
        existingTrainee.setPassword("password123");
        existingTrainee.setUserName("john.doe");
        existingTrainee.setDateOfBirth(new Date());
        existingTrainee.setIsActive(true);

        Trainee updatedTrainee = new Trainee();
        updatedTrainee.setFirstName("UpdatedFirstName");
        updatedTrainee.setLastName("UpdatedLastName");
        updatedTrainee.setAddress("UpdatedAddress");
        updatedTrainee.setPassword("updatedPassword123");
        updatedTrainee.setUserName("updated.john.doe");
        updatedTrainee.setDateOfBirth(new Date());
        updatedTrainee.setIsActive(false);

        when(traineeDAO.get("Trainee", traineeId)).thenReturn(existingTrainee);

        // Act
        Trainee result = traineeService.updateTrainee(traineeId, updatedTrainee);

        // Assert
        assertNotNull(result);
        assertEquals(existingTrainee.getId(), result.getId());
        assertEquals(updatedTrainee.getFirstName(), result.getFirstName());
        assertEquals(updatedTrainee.getLastName(), result.getLastName());
        assertEquals(updatedTrainee.getAddress(), result.getAddress());
        assertEquals(updatedTrainee.getPassword(), result.getPassword());
        assertEquals(updatedTrainee.getUserName(), result.getUserName());
        assertEquals(updatedTrainee.getDateOfBirth(), result.getDateOfBirth());
        assertEquals(updatedTrainee.getIsActive(), result.getIsActive());

        // Verify
        verify(traineeDAO).get("Trainee", traineeId);
        verify(modelMapper).map(updatedTrainee, existingTrainee);
        verify(traineeDAO).delete("Trainee", traineeId);
        verify(traineeDAO).save("Trainee", existingTrainee);
    }





    @Test
    void deleteTraineeTest() {
        // Arrange
        Long traineeId = 1L;

        // Act
        traineeService.deleteTrainee(traineeId);

        // Assert
        verify(traineeDAO).delete("Trainee", traineeId);
    }
}