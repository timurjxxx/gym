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

        // Check if the constructor and getters work correctly
        assertEquals(id, trainingType.getId());
        assertEquals(trainingTypeName, trainingType.getTrainingTypeName());
    }

    @Test
    public void testToString() {
        Long id = 1L;
        String trainingTypeName = "Test Training Type";

        TrainingType trainingType = new TrainingType(id, trainingTypeName);

        // Check if toString generates the expected output
        String expectedToString = "TrainingType{id=" + id + ", trainingTypeName='" + trainingTypeName + "'}";
        assertEquals(expectedToString, trainingType.toString());
    }

    @Test
    public void testEqualsAndHashCode() {
        // Test the equals and hashCode methods

        Long id1 = 1L;
        String trainingTypeName1 = "Test Training Type 1";

        Long id2 = 1L;  // Same id as id1
        String trainingTypeName2 = "Test Training Type 1";

        TrainingType trainingType1 = new TrainingType(id1, trainingTypeName1);
        TrainingType trainingType2 = new TrainingType(id2, trainingTypeName2);

        // Test equality based on id
        assertEquals(trainingType1, trainingType2);
        assertEquals(trainingType1.hashCode(), trainingType2.hashCode());

        // Test inequality with different ids
        TrainingType differentTrainingType = new TrainingType(2L, "Different Training Type");
        assertNotEquals(trainingType1, differentTrainingType);
        assertNotEquals(trainingType1.hashCode(), differentTrainingType.hashCode());
    }
}
