package org.daoTest;

import org.gym.dao.TrainingTypeDAO;
import org.gym.model.TrainingType;
import org.gym.service.TrainingTypeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class TrainingTypeDAOTest {


    @Mock
    private TrainingTypeDAO trainingTypeDAO;

    @InjectMocks
    private TrainingTypeService trainingTypeService;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindTrainingTypeByTrainingTypeName() {
        // Arrange
        String typeName = "TestType";
        TrainingType trainingType = new TrainingType();
        trainingType.setId(1L);
        trainingType.setTrainingTypeName(typeName);

        when(trainingTypeDAO.findTrainingTypeByTrainingTypeName(typeName)).thenReturn(trainingType);

        // Act
        TrainingType result = trainingTypeService.findByTrainingName(typeName);

        // Assert
        assertNotNull(result);
        assertEquals(typeName, result.getTrainingTypeName());
        verify(trainingTypeDAO, times(1)).findTrainingTypeByTrainingTypeName(typeName);
    }
}
