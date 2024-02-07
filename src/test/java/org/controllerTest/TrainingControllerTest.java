package org.controllerTest;

import org.gym.controller.TrainingController;
import org.gym.model.*;
import org.gym.service.TrainingService;
import org.gym.service.TrainingTypeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class TrainingControllerTest {

    @Mock
    private TrainingService trainingService;

    @Mock
    private TrainingTypeService trainingTypeService;

    @InjectMocks
    private TrainingController trainingController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createTraining() {
        Training training = new Training();
        Trainer trainer = new Trainer();
        trainer.setUser(new User());
        trainer.setSpecialization(new TrainingType());
        Trainee trainee = new Trainee();
        trainee.setUser(new User());
        training.setTrainer(trainer);
        training.setTrainee(trainee);
        training.setTrainingTypes(new TrainingType());

        ResponseEntity<Void> response = trainingController.createTraining(training);

        assertEquals(ResponseEntity.ok().build(), response);
        verify(trainingService, times(1)).addTraining(any(), any(), any(), any());
    }

    @Test
    void getTraineeTrainingsByCriteria() {
        String username = "trainee";
        TrainingSearchCriteria criteria = new TrainingSearchCriteria();
        List<Training> trainings = Arrays.asList(new Training(), new Training());

        when(trainingService.getTraineeTrainingsByCriteria(username, criteria)).thenReturn(trainings);

        ResponseEntity<String> response = trainingController.getTraineeTrainingsByCriteria(username, criteria);

        assertEquals(ResponseEntity.ok(trainings.toString()), response);
        verify(trainingService, times(1)).getTraineeTrainingsByCriteria(username, criteria);
    }

    @Test
    void getTrainerTrainingsByCriteria() {
        String username = "trainer";
        TrainingSearchCriteria criteria = new TrainingSearchCriteria();
        List<Training> trainings = Arrays.asList(new Training(), new Training());

        when(trainingService.getTrainerTrainingsByCriteria(username, criteria)).thenReturn(trainings);

        ResponseEntity<String> response = trainingController.getTrainerTrainingsByCriteria(username, criteria);

        assertEquals(ResponseEntity.ok(trainings.toString()), response);
        verify(trainingService, times(1)).getTrainerTrainingsByCriteria(username, criteria);
    }
}
