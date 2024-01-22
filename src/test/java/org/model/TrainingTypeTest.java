package org.model;

import org.gym.model.TrainingType;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TrainingTypeTest {

    @Test
    public void testConstructorAndGetters() {
        Long id = 1L;
        String trainingTypeName = "Test Training Type";

        TrainingType trainingType = new TrainingType(id, trainingTypeName);

        assertEquals(id, trainingType.getId());
        assertEquals(trainingTypeName, trainingType.getTrainingTypeName());
    }

    @Test
    public void testToString() {
        Long id = 1L;
        String trainingTypeName = "Test Training Type";

        TrainingType trainingType = new TrainingType(id, trainingTypeName);

        String expectedToString = "TrainingType{id=" + id + ", trainingTypeName='" + trainingTypeName + "'}";
        assertEquals(expectedToString, trainingType.toString());
    }

    @Test
    public void testEqualsAndHashCode() {

        Long id1 = 1L;
        String trainingTypeName1 = "Test Training Type 1";

        Long id2 = 1L;
        String trainingTypeName2 = "Test Training Type 1";

        TrainingType trainingType1 = new TrainingType(id1, trainingTypeName1);
        TrainingType trainingType2 = new TrainingType(id2, trainingTypeName2);

        assertEquals(trainingType1, trainingType2);
        assertEquals(trainingType1.hashCode(), trainingType2.hashCode());

        TrainingType differentTrainingType = new TrainingType(2L, "Different Training Type");
        assertNotEquals(trainingType1, differentTrainingType);
        assertNotEquals(trainingType1.hashCode(), differentTrainingType.hashCode());
    }
}
