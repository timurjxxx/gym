package org.serviceTest;


import org.gym.dao.TrainerDAO;
import org.gym.model.Trainer;
import org.gym.service.TrainerService;
import org.gym.service.UserService;
import org.gym.utils.GenerateId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

class TrainerServiceTest {
    @Mock
    private TrainerDAO trainerDAOMock;

    @Mock
    private UserService userServiceMock;

    @Mock
    private GenerateId generateIdMock;

    @Mock
    private ModelMapper modelMapperMock;

    @InjectMocks
    private TrainerService trainerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createTrainerTest() {
        Trainer newTrainer = new Trainer();
        newTrainer.setId(1L);
        newTrainer.setFirstName("John");
        newTrainer.setLastName("Doe");
        newTrainer.setPassword("password123");
        newTrainer.setUserName("john.doe");
        newTrainer.setSpecialization("Runner");

        when(userServiceMock.generatePassword()).thenReturn("generatedPassword");
        when(userServiceMock.generateUsernameFor(anyString(), anyString())).thenReturn("generatedUsername");
        when(generateIdMock.generateUniqueId(anyString())).thenReturn(1L);
        when(trainerDAOMock.save(anyString(), any())).thenReturn(newTrainer);  // Fix here

        Trainer result = trainerService.createTrainer(newTrainer);
        verify(userServiceMock).generatePassword();
        verify(userServiceMock).generateUsernameFor("Trainer", "John.Doe");
        verify(generateIdMock).generateUniqueId("Trainer");
        verify(trainerDAOMock).save("Trainer", newTrainer);

        assertNotNull(result);
        assertEquals("generatedPassword", result.getPassword());
        assertEquals("generatedUsername", result.getUserName());
        assertEquals(1L, result.getId());
    }

    @Test
    void selectTrainerTest() {
        Long trainerId = 1L;
        Trainer expectedTrainer = new Trainer();
        expectedTrainer.setId(trainerId);
        expectedTrainer.setFirstName("John");
        expectedTrainer.setLastName("Doe");
        expectedTrainer.setPassword("password123");
        expectedTrainer.setUserName("john.doe");
        expectedTrainer.setSpecialization("Runner");

        when(trainerDAOMock.get("Trainer", trainerId)).thenReturn(expectedTrainer);

        Trainer result = trainerService.selectTrainer(trainerId);

        verify(trainerDAOMock).get("Trainer", trainerId);

        assertNotNull(result);
        assertEquals(expectedTrainer.getId(), result.getId());
        assertEquals(expectedTrainer.getFirstName(), result.getFirstName());
        assertEquals(expectedTrainer.getLastName(), result.getLastName());
        assertEquals(expectedTrainer.getPassword(), result.getPassword());
        assertEquals(expectedTrainer.getUserName(), result.getUserName());
        assertEquals(expectedTrainer.getSpecialization(), result.getSpecialization());
    }

    @Test
    void updateTrainerTest() {
        // Arrange
        Long trainerId = 1L;
        Trainer existingTrainer = new Trainer();
        existingTrainer.setId(trainerId);
        existingTrainer.setFirstName("John");
        existingTrainer.setLastName("Doe");
        existingTrainer.setPassword("password123");
        existingTrainer.setUserName("john.doe");
        existingTrainer.setSpecialization("Runner");

        Trainer updatedTrainer = new Trainer();
        updatedTrainer.setFirstName("UpdatedFirstName");
        updatedTrainer.setLastName("UpdatedLastName");
        updatedTrainer.setPassword("updatedPassword123");
        updatedTrainer.setUserName("updated.john.doe");
        updatedTrainer.setSpecialization("UpdatedSpecialization");

        when(trainerDAOMock.get("Trainer", trainerId)).thenReturn(existingTrainer);

        // Act
        Trainer result = trainerService.updateTrainer(trainerId, updatedTrainer);

        // Assert
        assertNotNull(result);
        assertEquals(existingTrainer.getId(), result.getId());
        assertEquals(updatedTrainer.getFirstName(), result.getFirstName());
        assertEquals(updatedTrainer.getLastName(), result.getLastName());
        assertEquals(updatedTrainer.getPassword(), result.getPassword());
        assertEquals(updatedTrainer.getUserName(), result.getUserName());
        assertEquals(updatedTrainer.getSpecialization(), result.getSpecialization());

        // Verify
        verify(trainerDAOMock).get("Trainer", trainerId);
        verify(modelMapperMock).map(updatedTrainer, existingTrainer);
        verify(trainerDAOMock).delete("Trainer", trainerId);
        verify(trainerDAOMock).save("Trainer", existingTrainer);
    }

}