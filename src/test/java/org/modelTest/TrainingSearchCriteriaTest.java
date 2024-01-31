package org.modelTest;

import org.gym.model.TrainingSearchCriteria;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class TrainingSearchCriteriaTest {

    @Test
    void testGettersAndSetters() {
        TrainingSearchCriteria criteria = new TrainingSearchCriteria();

        criteria.setTrainingName("Yoga");
        criteria.setTrainingStartDate(new Date());
        criteria.setTrainingEndDate(new Date());
        criteria.setTrainingDuration(60);

        assertEquals("Yoga", criteria.getTrainingName());
        assertNotNull(criteria.getTrainingStartDate());
        assertNotNull(criteria.getTrainingEndDate());
        assertEquals(60, criteria.getTrainingDuration());
    }

    @Test
    void testEqualsAndHashCode() {
        TrainingSearchCriteria criteria1 = new TrainingSearchCriteria();
        criteria1.setTrainingName("Yoga");
        criteria1.setTrainingStartDate(new Date());
        criteria1.setTrainingEndDate(new Date());
        criteria1.setTrainingDuration(60);

        TrainingSearchCriteria criteria2 = new TrainingSearchCriteria();
        criteria2.setTrainingName("Yoga");
        criteria2.setTrainingStartDate(new Date());
        criteria2.setTrainingEndDate(new Date());
        criteria2.setTrainingDuration(60);

        assertEquals(criteria1, criteria2);
        assertEquals(criteria1.hashCode(), criteria2.hashCode());

        TrainingSearchCriteria criteria3 = new TrainingSearchCriteria();
        criteria3.setTrainingName("Pilates");
        criteria3.setTrainingStartDate(new Date());
        criteria3.setTrainingEndDate(new Date());
        criteria3.setTrainingDuration(45);

        assertNotEquals(criteria1, criteria3);
        assertNotEquals(criteria1.hashCode(), criteria3.hashCode());
    }

    @Test
    void testToString() {
        TrainingSearchCriteria criteria = new TrainingSearchCriteria();
        criteria.setTrainingName("Yoga");
        criteria.setTrainingStartDate(new Date());
        criteria.setTrainingEndDate(new Date());
        criteria.setTrainingDuration(60);

        String expectedToString = "TrainingSearchCriteria{" +
                "trainingName='Yoga', " +
                "trainingStartDate=" + criteria.getTrainingStartDate() + ", " +
                "trainingEndDate=" + criteria.getTrainingEndDate() + ", " +
                "trainingDuration=60" +
                "}";

        assertEquals(expectedToString, criteria.toString());
    }
    @Test
    void testEquals() {
        TrainingSearchCriteria criteria1 = new TrainingSearchCriteria();
        TrainingSearchCriteria criteria2 = new TrainingSearchCriteria();

        // Проверка на равенство с самим собой
        assertEquals(criteria1, criteria1);

        // Проверка на равенство с другим пустым объектом
        assertEquals(criteria1, criteria2);

        // Изменение одного поля и проверка на неравенство
        criteria1.setTrainingName("Training1");
        assertNotEquals(criteria1, criteria2);
    }

    @Test
    void testHashCode() {
        TrainingSearchCriteria criteria1 = new TrainingSearchCriteria();
        TrainingSearchCriteria criteria2 = new TrainingSearchCriteria();

        // Проверка на равенство хэш-кодов у пустых объектов
        assertEquals(criteria1.hashCode(), criteria2.hashCode());

        // Изменение одного поля и проверка на неравенство хэш-кодов
        criteria1.setTrainingName("Training1");
        assertNotEquals(criteria1.hashCode(), criteria2.hashCode());
    }

    @Test
    void testEqualsAndHashCodee() {
        TrainingSearchCriteria criteria1 = new TrainingSearchCriteria();
        criteria1.setTrainingName("Training1");
        criteria1.setTrainingStartDate(new Date(2022, 1, 1));
        criteria1.setTrainingEndDate(new Date(2022, 1, 10));
        criteria1.setTrainingDuration(5);

        TrainingSearchCriteria criteria2 = new TrainingSearchCriteria();
        criteria2.setTrainingName("Training1");
        criteria2.setTrainingStartDate(new Date(2022, 1, 1));
        criteria2.setTrainingEndDate(new Date(2022, 1, 10));
        criteria2.setTrainingDuration(5);

        // Проверка равенства
        Assertions.assertEquals(criteria1, criteria2);

        // Проверка hashCode
        Assertions.assertEquals(criteria1.hashCode(), criteria2.hashCode());
    }
}
