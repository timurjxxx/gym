package org.util;
import org.gym.memory.InMemoryStorage;
import org.gym.model.Trainee;
import org.gym.model.Trainer;
import org.gym.model.Training;
import org.gym.utils.GenerateId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GenerateTest {



    @Mock
    private InMemoryStorage storage;

    @InjectMocks
    private GenerateId generateId;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void generateUniqueIdTrainerNamespaceTest() {
        String namespace = "Trainer";
        List<Object> trainers = new ArrayList<>();
        Trainer trainer1 = new Trainer();
        trainer1.setId(1L);
        trainers.add(trainer1);
        when(storage.getStorageMap()).thenReturn(Collections.singletonMap(namespace, trainers));

        Long generatedId = generateId.generateUniqueId(namespace);

        assertEquals(2L, generatedId);
    }

    @Test
    void generateUniqueIdTraineeNamespaceTest() {
        // Arrange
        String namespace = "Trainee";
        List<Object> trainees = new ArrayList<>();
        Trainee trainee1 = new Trainee();
        trainee1.setId(1L);
        trainees.add(trainee1);
        when(storage.getStorageMap()).thenReturn(Collections.singletonMap(namespace, trainees));

        // Act
        Long generatedId = generateId.generateUniqueId(namespace);

        // Assert
        assertEquals(2L, generatedId);
    }

    @Test
    void generateUniqueIdTrainingNamespaceTest() {
        // Arrange
        String namespace = "Training";
        List<Object> trainings = new ArrayList<>();
        Training training1 = new Training();
        training1.setId(1L);
        trainings.add(training1);
        when(storage.getStorageMap()).thenReturn(Collections.singletonMap(namespace, trainings));

        // Act
        Long generatedId = generateId.generateUniqueId(namespace);

        // Assert
        assertEquals(2L, generatedId);
    }

    @Test
    void generateUniqueIdEmptyNamespace() {
        String namespace = "EmptyNamespace";
        when(storage.getStorageMap()).thenReturn(Collections.emptyMap());

        Long generatedId = generateId.generateUniqueId(namespace);

        assertEquals(1L, generatedId);
    }

}
