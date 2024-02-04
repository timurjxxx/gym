//package org.serviceTest;
//
//import org.gym.dao.TrainingTypeDAO;
//import org.gym.model.TrainingType;
//import org.gym.service.TrainingTypeService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import javax.persistence.EntityNotFoundException;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//public class TrainingTypeServiceTest {
//
//    @Mock
//    private TrainingTypeDAO trainingTypeDAO;
//
//    @InjectMocks
//    private TrainingTypeService trainingTypeService;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.initMocks(this);
//    }
//
//    @Test
//    void testCreateTrainingType() {
//        TrainingType trainingTypeToSave = new TrainingType();
//        when(trainingTypeDAO.save(any(TrainingType.class))).thenReturn(trainingTypeToSave);
//
//        TrainingType savedTrainingType = trainingTypeService.createTrainingType(new TrainingType());
//
//        assertNotNull(savedTrainingType);
//        verify(trainingTypeDAO, times(1)).save(any(TrainingType.class));
//    }
//
//    @Test
//    void testGetTrainingType() {
//        Long trainingTypeId = 1L;
//        TrainingType trainingType = new TrainingType();
//        when(trainingTypeDAO.findById(trainingTypeId)).thenReturn(Optional.of(trainingType));
//
//        TrainingType retrievedTrainingType = trainingTypeService.getTrainingType(trainingTypeId);
//
//        assertNotNull(retrievedTrainingType);
//        assertEquals(trainingType, retrievedTrainingType);
//        verify(trainingTypeDAO, times(1)).findById(trainingTypeId);
//    }
//
//    @Test
//    void testGetTrainingTypeNotFound() {
//        Long nonExistentTrainingTypeId = 999L;
//        when(trainingTypeDAO.findById(nonExistentTrainingTypeId)).thenReturn(Optional.empty());
//
//        assertThrows(EntityNotFoundException.class, () -> trainingTypeService.getTrainingType(nonExistentTrainingTypeId));
//
//        verify(trainingTypeDAO, times(1)).findById(nonExistentTrainingTypeId);
//    }
//}
