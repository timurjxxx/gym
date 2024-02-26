package org.controllerTest;

import org.gym.controller.TrainingController;
import org.gym.dao.TrainingDAO;
import org.gym.model.*;
import org.gym.service.TraineeService;
import org.gym.service.TrainerService;
import org.gym.service.TrainingService;
import org.gym.service.TrainingTypeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

import static antlr.build.ANTLR.root;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class TrainingControllerTest {

    @Mock
    private TrainingService trainingService;

    @Mock
    private TrainingTypeService trainingTypeService;

    @Mock
    private TrainingDAO trainingDAO;
    @Mock
    private TrainerService trainerService;
    @Mock
    private TraineeService traineeService;
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
        String password = "test";
        TrainingSearchCriteria criteria = new TrainingSearchCriteria();
        List<Training> trainings = Arrays.asList(new Training(), new Training());

        when(trainingService.getTraineeTrainingsByCriteria(username, criteria)).thenReturn(trainings);

        ResponseEntity<String> response = trainingController.getTraineeTrainingsByCriteria(username, password, criteria);

        assertEquals(ResponseEntity.ok(trainings.toString()), response);
        verify(trainingService, times(1)).getTraineeTrainingsByCriteria(username, criteria);
    }

    @Test
    void getTrainerTrainingsByCriteria() {
        String username = "trainer";
        String password = "test";
        TrainingSearchCriteria criteria = new TrainingSearchCriteria();
        List<Training> trainings = Arrays.asList(new Training(), new Training());

        when(trainingService.getTrainerTrainingsByCriteria(username, criteria)).thenReturn(trainings);

        ResponseEntity<String> response = trainingController.getTrainerTrainingsByCriteria(username, password, criteria);

        assertEquals(ResponseEntity.ok(trainings.toString()), response);
        verify(trainingService, times(1)).getTrainerTrainingsByCriteria(username, criteria);
    }

    @Test
    void getTrainerTrainingsByCriteria_ShouldReturnEmptyList_WhenTrainerNotFound() {
        // Arrange
        String trainerUsername = "nonExistentTrainer";

        when(trainerService.selectTrainerByUserName(trainerUsername)).thenReturn(null);

        // Act
        List<Training> result = trainingService.getTrainerTrainingsByCriteria(trainerUsername, null);

        // Assert
        assertEquals(0, result.size());
    }

//    @Test
//    void getTrainerTrainingsByCriteria_ShouldFilterByTrainingTypes_WhenProvided() {
//        // Arrange
//        String trainerUsername = "sampleTrainer";
//        Trainer mockTrainer = new Trainer();
//        mockTrainer.setUsername(trainerUsername);
//
//        TrainingSearchCriteria criteria = new TrainingSearchCriteria();
//        criteria.setTrainingTypes(new TrainingType("Type1"));
//
//        List<Training> mockTrainings = new ArrayList<>();
//        Training mockTraining1 = new Training();
//        mockTraining1.setTrainingName("Training1");
//        mockTraining1.setTrainingType(new TrainingType("Type1"));
//        mockTrainings.add(mockTraining1);
//
//        Training mockTraining2 = new Training();
//        mockTraining2.setTrainingName("Training2");
//        mockTraining2.setTrainingType(new TrainingType("Type2"));
//        mockTrainings.add(mockTraining2);
//
//        when(trainerService.selectTrainerByUserName(trainerUsername)).thenReturn(mockTrainer);
//        when(trainingDAO.findAll(any())).thenReturn(mockTrainings);
//
//        // Act
//        List<Training> result = trainingService.getTrainerTrainingsByCriteria(trainerUsername, criteria);
//
//        // Assert
//        assertEquals(1, result.size());
//        assertEquals("Training1", result.get(0).getTrainingName());
//    }

//    @Test
//    public void testGetTraineeTrainingsByCriteria_WithCompleteCriteria() {
//        // Arrange
//        String username = "test_user";
//        Trainee trainee = new Trainee();
//        TrainingSearchCriteria criteria = new TrainingSearchCriteria();
//        criteria.setTrainingName("Test Training");
//        criteria.setTrainingStartDate(LocalDate.now().minusDays(7));
//        criteria.setTrainingEndDate(LocalDate.now().plusDays(7));
//        criteria.setTrainingDuration(30);
//        TrainingType trainingType = new TrainingType();
//        trainingType.setTrainingTypeName("name");
//        criteria.setTrainingTypes(trainingType);
//
//        Mockito.when(traineeService.selectTraineeByUserName(username)).thenReturn(trainee);
//
//        // Act
//        List<Training> trainings = trainingDAO.findAll((root, query, cb) -> );
//
//        // Assert
//        // Verify the trainee is retrieved and used in the predicate
//        Mockito.verify(traineeService, times(1)).selectTraineeByUserName(username);
//
//        // Assert expected number of trainings retrieved based on mock criteria
//        // (You may need to adjust assertions based on your actual implementation)
//        // assertEquals(expectedTrainingCount, trainings.size());
//    }


}
