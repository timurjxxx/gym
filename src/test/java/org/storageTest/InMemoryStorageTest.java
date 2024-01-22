package org.storageTest;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.MapType;
import org.gym.memory.InMemoryStorage;
import org.gym.model.Trainee;
import org.gym.model.Trainer;
import org.gym.model.Training;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.MapType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.File;
import java.io.IOException;
import java.util.*;
class InMemoryStorageTest {



    @Mock
    private ObjectMapper objectMapper;


    @InjectMocks
    private InMemoryStorage inMemoryStorage;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveTest() {
        String trainerNamespace = "Trainer";
        Trainer trainer = new Trainer();
        trainer.setId(1L);
        trainer.setFirstName("John");
        trainer.setLastName("Doe");
        trainer.setUserName("john.doe");
        trainer.setPassword("password123");
        trainer.setIsActive(true);
        trainer.setSpecialization("Running");

        inMemoryStorage.save(trainerNamespace, trainer);

        assertTrue(inMemoryStorage.getStorageMap().containsKey(trainerNamespace));
        assertEquals(1, inMemoryStorage.getStorageMap().get(trainerNamespace).size());
        assertEquals(trainer, inMemoryStorage.getStorageMap().get(trainerNamespace).get(0));

        String traineeNamespace = "Trainee";
        Trainee trainee = new Trainee();
        trainee.setId(2L);
        trainee.setFirstName("Alice");
        trainee.setLastName("Smith");
        trainee.setUserName("alice.smith");
        trainee.setPassword("password456");
        trainee.setIsActive(true);
        trainee.setDateOfBirth(new Date());
        trainee.setAddress("123 Main St");

        inMemoryStorage.save(traineeNamespace, trainee);

        assertTrue(inMemoryStorage.getStorageMap().containsKey(traineeNamespace));
        assertEquals(1, inMemoryStorage.getStorageMap().get(traineeNamespace).size());
        assertEquals(trainee, inMemoryStorage.getStorageMap().get(traineeNamespace).get(0));

        String trainingNamespace = "Training";
        Training training = new Training();
        training.setId(1L);
        training.setTrainerId(trainer);
        training.setTraineeId(trainee);
        training.setTrainingName("Running");

        inMemoryStorage.save(trainingNamespace, training);

        assertTrue(inMemoryStorage.getStorageMap().containsKey(trainingNamespace));
        assertEquals(1, inMemoryStorage.getStorageMap().get(trainingNamespace).size());
        assertEquals(training, inMemoryStorage.getStorageMap().get(trainingNamespace).get(0));
    }



    @Test
    void testFindAll() {
        // Test for Trainer
        String trainerName = "Trainer";
        Trainer trainer1 = new Trainer();
        trainer1.setId(1L);
        trainer1.setFirstName("John");
        trainer1.setLastName("Doe");
        trainer1.setUserName("john.doe");
        trainer1.setPassword("password123");
        trainer1.setIsActive(true);
        trainer1.setSpecialization("Running");

        Trainer trainer2 = new Trainer();
        trainer2.setId(2L);
        trainer2.setFirstName("Alice");
        trainer2.setLastName("Smith");
        trainer2.setUserName("alice.smith");
        trainer2.setPassword("password456");
        trainer2.setIsActive(true);
        trainer2.setSpecialization("Cycling");

        List<Object> trainerEntities = Arrays.asList(trainer1, trainer2);

        inMemoryStorage.getStorageMap().put(trainerName, trainerEntities);

        List<Object> allTrainers = inMemoryStorage.findAll(trainerName);

        assertEquals(trainerEntities, allTrainers);


        // Test for Trainee
        String traineeName = "Trainee";
        Trainee trainee1 = new Trainee();
        trainee1.setId(1L);
        trainee1.setFirstName("Bob");
        trainee1.setLastName("Johnson");
        trainee1.setUserName("bob.johnson");
        trainee1.setPassword("password789");
        trainee1.setIsActive(true);
        trainee1.setDateOfBirth(new Date());
        trainee1.setAddress("456 Oak St");

        Trainee trainee2 = new Trainee();
        trainee2.setId(2L);
        trainee2.setFirstName("Eva");
        trainee2.setLastName("Brown");
        trainee2.setUserName("eva.brown");
        trainee2.setPassword("passwordabc");
        trainee2.setIsActive(true);
        trainee2.setDateOfBirth(new Date());
        trainee2.setAddress("789 Pine St");

        List<Object> traineeEntities = Arrays.asList(trainee1, trainee2);

        inMemoryStorage.getStorageMap().put(traineeName, traineeEntities);

        List<Object> allTrainees = inMemoryStorage.findAll(traineeName);

        assertEquals(traineeEntities, allTrainees);
    }
    @Test
    void testWriteToJsonFile() throws IOException {
        String filePath = "test.json";
        doNothing().when(objectMapper).writeValue(any(File.class), eq(inMemoryStorage.getStorageMap()));

        inMemoryStorage.writeToJsonFile(filePath);

        verify(objectMapper, times(1)).writeValue(any(File.class), eq(inMemoryStorage.getStorageMap()));
    }
    @Test
    void findByIdTest() {
        String trainerNamespace = "Trainer";
        Long trainerId = 1L;
        Trainer trainer = new Trainer();
        trainer.setId(trainerId);
        List<Object> trainerEntities = Arrays.asList(trainer);

        inMemoryStorage.getStorageMap().put(trainerNamespace, trainerEntities);

        Object foundTrainer = inMemoryStorage.findById(trainerNamespace, trainerId);

        assertEquals(trainer, foundTrainer);

        String traineeNamespace = "Trainee";
        Long traineeId = 2L;
        Trainee trainee = new Trainee();
        trainee.setId(traineeId);
        List<Object> traineeEntities = Arrays.asList(trainee);

        inMemoryStorage.getStorageMap().put(traineeNamespace, traineeEntities);

        Object foundTrainee = inMemoryStorage.findById(traineeNamespace, traineeId);

        assertEquals(trainee, foundTrainee);

        String trainingNamespace = "Training";
        Long trainingId = 1L;
        Training training = new Training();
        training.setId(trainingId);
        List<Object> trainingEntities = Arrays.asList(training);

        inMemoryStorage.getStorageMap().put(trainingNamespace, trainingEntities);

        Object foundTraining = inMemoryStorage.findById(trainingNamespace, trainingId);

        assertEquals(training, foundTraining);
    }


    @Test
    void testUpdate() {
        String trainerName = "Trainer";
        Long trainerId = 1L;

        Trainer updatedTrainer = new Trainer();
        updatedTrainer.setId(trainerId);
        updatedTrainer.setFirstName("Updated");
        updatedTrainer.setLastName("Trainer");
        updatedTrainer.setUserName("updated.trainer");
        updatedTrainer.setPassword("newpassword");
        updatedTrainer.setIsActive(true);
        updatedTrainer.setSpecialization("Updated Specialization");

        Trainer originalTrainer = new Trainer();
        originalTrainer.setId(trainerId);
        originalTrainer.setFirstName("John");
        originalTrainer.setLastName("Doe");
        originalTrainer.setUserName("john.doe");
        originalTrainer.setPassword("password123");
        originalTrainer.setIsActive(true);
        originalTrainer.setSpecialization("Running");

        List<Object> trainerEntities = Arrays.asList(originalTrainer);

        inMemoryStorage.getStorageMap().put(trainerName, trainerEntities);

        inMemoryStorage.update(trainerName, trainerId, updatedTrainer);

        assertEquals(updatedTrainer, inMemoryStorage.getStorageMap().get(trainerName).get(0));


        String traineeName = "Trainee";
        Long traineeId = 2L;

        Trainee updatedTrainee = new Trainee();
        updatedTrainee.setId(traineeId);
        updatedTrainee.setFirstName("Updated");
        updatedTrainee.setLastName("Trainee");
        updatedTrainee.setUserName("updated.trainee");
        updatedTrainee.setPassword("newpassword");
        updatedTrainee.setIsActive(true);
        updatedTrainee.setDateOfBirth(new Date());
        updatedTrainee.setAddress("Updated Address");

        Trainee originalTrainee = new Trainee();
        originalTrainee.setId(traineeId);
        originalTrainee.setFirstName("Alice");
        originalTrainee.setLastName("Smith");
        originalTrainee.setUserName("alice.smith");
        originalTrainee.setPassword("password456");
        originalTrainee.setIsActive(true);
        originalTrainee.setDateOfBirth(new Date());
        originalTrainee.setAddress("123 Main St");

        List<Object> traineeEntities = Arrays.asList(originalTrainee);

        inMemoryStorage.getStorageMap().put(traineeName, traineeEntities);

        inMemoryStorage.update(traineeName, traineeId, updatedTrainee);

        assertEquals(updatedTrainee, inMemoryStorage.getStorageMap().get(traineeName).get(0));


    }


}