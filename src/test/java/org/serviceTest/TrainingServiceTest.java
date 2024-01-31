package org.serviceTest;

import org.gym.dao.TraineeDAO;
import org.gym.dao.TrainerDAO;
import org.gym.dao.TrainingDAO;
import org.gym.model.*;
import org.gym.service.TrainingService;
import org.gym.service.TrainingTypeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import javax.persistence.criteria.Path;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class TrainingServiceTest {

    @Mock
    private TrainingDAO trainingDAO;

    @Captor
    private ArgumentCaptor<Specification<Training>> specificationCaptor;

    @Mock
    private TrainerDAO trainerDAO;

    @Mock
    private TraineeDAO traineeDAO;

    @Mock
    private TrainingTypeService trainingTypeService;

    @InjectMocks
    private TrainingService trainingService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddTraining() {
        Long trainerId = 1L;
        Long traineeId = 2L;
        Long trainingTypeId = 3L;

        Training training = new Training();
        training.setTrainingName("Sample Training");

        when(trainerDAO.findById(trainerId)).thenReturn(Optional.of(new Trainer()));
        when(traineeDAO.findById(traineeId)).thenReturn(Optional.of(new Trainee()));
        when(trainingTypeService.getTrainingType(trainingTypeId)).thenReturn(new TrainingType());

        when(trainingDAO.save(any(Training.class))).thenAnswer(invocation -> {
            Training savedTraining = invocation.getArgument(0);
            savedTraining.setId(1L);
            return savedTraining;
        });

        Training result = trainingService.addTraining(training, trainerId, traineeId, trainingTypeId);

        assertNotNull(result.getId());
        assertEquals(training.getTrainingName(), result.getTrainingName());

        verify(trainerDAO, times(1)).findById(trainerId);
        verify(traineeDAO, times(1)).findById(traineeId);
        verify(trainingTypeService, times(1)).getTrainingType(trainingTypeId);
        verify(trainingDAO, times(1)).save(any(Training.class));
    }

    @Test
    void testGetTrainerTrainingsByCriteria_Success() {
        String trainerUsername = "testTrainer";
        TrainingSearchCriteria criteria = new TrainingSearchCriteria();
        Trainer trainer = new Trainer();
        List<Training> trainingList = new ArrayList<>();

        when(trainerDAO.findTrainerByUserUserName(trainerUsername)).thenReturn(Optional.of(trainer));
        when(trainingDAO.findAll((Specification<Training>) any())).thenReturn(trainingList);

        List<Training> result = trainingService.getTrainerTrainingsByCriteria(trainerUsername, criteria);
        assertEquals(trainingList, result);
        verify(trainerDAO, times(1)).findTrainerByUserUserName(trainerUsername);
        verify(trainingDAO, times(1)).findAll((Specification<Training>) any());
    }

    @Test
    void testGetTraineeTrainingsByCriteria_Success() {
        String traineeUsername = "testTrainee";
        TrainingSearchCriteria criteria = new TrainingSearchCriteria();
        Trainee trainee = new Trainee();
        List<Training> trainingList = new ArrayList<>();

        when(traineeDAO.findTraineeByUserUserName(traineeUsername)).thenReturn(Optional.of(trainee));
        when(trainingDAO.findAll((Specification<Training>) any())).thenReturn(trainingList);

        List<Training> result = trainingService.getTraineeTrainingsByCriteria(traineeUsername, criteria);

        assertEquals(trainingList, result);
        verify(traineeDAO, times(1)).findTraineeByUserUserName(traineeUsername);
        verify(trainingDAO, times(1)).findAll((Specification<Training>) any());
    }


    @Test
    public void testGetTrainerTrainingsByCriteria() {
        String trainerUsername = "testTrainer";
        TrainingSearchCriteria criteria = new TrainingSearchCriteria();
        criteria.setTrainingName("Test Training");
        criteria.setTrainingStartDate(Date.valueOf("2022-01-01"));
        criteria.setTrainingEndDate(Date.valueOf("2022-12-31"));
        criteria.setTrainingDuration(60);

        Trainer trainer = new Trainer();

        when(trainerDAO.findTrainerByUserUserName(trainerUsername)).thenReturn(Optional.of(trainer));

        List<Training> expectedTrainings = Arrays.asList(new Training(), new Training());
        when(trainingDAO.findAll((Specification<Training>) any())).thenReturn(expectedTrainings);

        List<Training> result = trainingService.getTrainerTrainingsByCriteria(trainerUsername, criteria);

        assertEquals(expectedTrainings, result);

        verify(trainerDAO).findTrainerByUserUserName(trainerUsername);
        verify(trainingDAO).findAll(specificationCaptor.capture());
        Specification<Training> capturedSpecification = specificationCaptor.getValue();

        assertNotNull(capturedSpecification);
    }


    @Test
    public void testGetTraineeTrainingsByCriteriaa() {
        String traineeUsername = "testTrainee";
        TrainingSearchCriteria criteria = new TrainingSearchCriteria();
        criteria.setTrainingName("Test Training");
        criteria.setTrainingStartDate(Date.valueOf("2022-01-01"));
        criteria.setTrainingEndDate(Date.valueOf("2022-12-31"));
        criteria.setTrainingDuration(60);

        Trainee trainee = new Trainee();

        when(traineeDAO.findTraineeByUserUserName(traineeUsername)).thenReturn(Optional.of(trainee));

        List<Training> expectedTrainings = Arrays.asList(new Training(), new Training());
        when(trainingDAO.findAll((Specification<Training>) any())).thenReturn(expectedTrainings);

        List<Training> result = trainingService.getTraineeTrainingsByCriteria(traineeUsername, criteria);

        assertEquals(expectedTrainings, result);

        verify(traineeDAO).findTraineeByUserUserName(traineeUsername);
        verify(trainingDAO).findAll(specificationCaptor.capture());
        Specification<Training> capturedSpecification = specificationCaptor.getValue();

        assertNotNull(capturedSpecification);
    }

    @Test
    void testGetTraineeTrainingsByCriteria() {
        Trainee testTrainee = new Trainee();

        TrainingSearchCriteria testCriteria = new TrainingSearchCriteria();
        testCriteria.setTrainingName("Test Training");
        testCriteria.setTrainingStartDate(Date.valueOf("2022-01-01"));
        testCriteria.setTrainingEndDate(Date.valueOf("2022-12-31"));
        testCriteria.setTrainingDuration(60);

        List<Training> testTrainings = new ArrayList<>();
        Training testTraining1 = new Training();
        testTraining1.setTrainingName("Test Training 1");
        testTrainings.add(testTraining1);

        Training testTraining2 = new Training();
        testTraining2.setTrainingName("Test Training 2");
        testTrainings.add(testTraining2);

        when(traineeDAO.findTraineeByUserUserName("testTrainee")).thenReturn(Optional.of(testTrainee));
        when(trainingDAO.findAll((Specification<Training>) any())).thenReturn(testTrainings);

        List<Training> resultTrainings = trainingService.getTraineeTrainingsByCriteria("testTrainee", testCriteria);

        assertEquals(2, resultTrainings.size());


        CriteriaBuilder criteriaBuilder = org.mockito.Mockito.mock(CriteriaBuilder.class);
        CriteriaQuery<Training> criteriaQuery = org.mockito.Mockito.mock(CriteriaQuery.class);
        Root<Training> root = org.mockito.Mockito.mock(Root.class);
        Path<Integer> path = org.mockito.Mockito.mock(Path.class);

        when(criteriaBuilder.equal(root.get("trainee"), testTrainee)).thenReturn(any(Predicate.class));
        when(criteriaBuilder.equal(root.get("trainingName"), "Test Training")).thenReturn(any(Predicate.class));
        when(criteriaBuilder.between(root.get("trainingDate"), testCriteria.getTrainingStartDate(), testCriteria.getTrainingEndDate())).thenReturn(any(Predicate.class));
        when(criteriaBuilder.equal(root.get("trainingDuration"), 60)).thenReturn(any(Predicate.class));

        List<Training> resultTrainingsWithCriteria = trainingService.getTraineeTrainingsByCriteria("testTrainee", testCriteria);

        assertEquals(2, resultTrainingsWithCriteria.size());
    }




}

