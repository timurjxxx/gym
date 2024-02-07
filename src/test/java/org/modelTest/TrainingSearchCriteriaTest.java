package org.modelTest;

import org.gym.model.TrainingSearchCriteria;
import org.gym.model.TrainingType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


import java.time.LocalDate;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class TrainingSearchCriteriaTest {


    @Test
    public void testGetterAndSetterForTrainingName() {
        TrainingSearchCriteria criteria = new TrainingSearchCriteria();
        String trainingName = "TestTraining";
        criteria.setTrainingName(trainingName);
        assertEquals(trainingName, criteria.getTrainingName());
    }

    @Test
    public void testGetterAndSetterForTrainingStartDate() {
        TrainingSearchCriteria criteria = new TrainingSearchCriteria();
        LocalDate startDate = LocalDate.now();
        criteria.setTrainingStartDate(startDate);
        assertEquals(startDate, criteria.getTrainingStartDate());
    }

    @Test
    public void testGetterAndSetterForTrainingEndDate() {
        TrainingSearchCriteria criteria = new TrainingSearchCriteria();
        LocalDate endDate = LocalDate.now().plusDays(7);
        criteria.setTrainingEndDate(endDate);
        assertEquals(endDate, criteria.getTrainingEndDate());
    }

    @Test
    public void testGetterAndSetterForTrainingDuration() {
        TrainingSearchCriteria criteria = new TrainingSearchCriteria();
        Integer duration = 10;
        criteria.setTrainingDuration(duration);
        assertEquals(duration, criteria.getTrainingDuration());
    }

    @Test
    public void testGetterAndSetterForTrainingTypes() {
        TrainingSearchCriteria criteria = new TrainingSearchCriteria();
        TrainingType trainingType = new TrainingType();
        criteria.setTrainingTypes(trainingType);
        assertEquals(trainingType, criteria.getTrainingTypes());
    }

    @Test
    public void testAllArgsConstructor() {
        String trainingName = "TestTraining";
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.now().plusDays(7);
        Integer duration = 10;
        TrainingType trainingType = new TrainingType();

        TrainingSearchCriteria criteria = new TrainingSearchCriteria(trainingName, startDate, endDate, duration, trainingType);

        assertEquals(trainingName, criteria.getTrainingName());
        assertEquals(startDate, criteria.getTrainingStartDate());
        assertEquals(endDate, criteria.getTrainingEndDate());
        assertEquals(duration, criteria.getTrainingDuration());
        assertEquals(trainingType, criteria.getTrainingTypes());
    }

}
