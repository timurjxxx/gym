package org.modelTest;

import org.gym.model.TrainingType;
import org.gym.model.TrainingTypeEnum;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class TrainingTypeTest {

    @Test
    public void testGetterAndSetterForId() {
        TrainingType trainingType = new TrainingType();
        Long id = 1L;
        trainingType.setId(id);
        assertEquals(id, trainingType.getId());
    }

    @Test
    public void testGetterAndSetterForTrainingTypeName() {
        TrainingType trainingType = new TrainingType();
        TrainingTypeEnum trainingTypeName = TrainingTypeEnum.YOGA;
        trainingType.setTrainingTypeName(trainingTypeName);
        assertEquals(trainingTypeName, trainingType.getTrainingTypeName());
    }

    @Test
    void testEqualsAndHashCode() {
        TrainingType type1 = new TrainingType();
        type1.setId(1L);
        type1.setTrainingTypeName(TrainingTypeEnum.YOGA);

        TrainingType type2 = new TrainingType();
        type2.setId(1L);
        type2.setTrainingTypeName(TrainingTypeEnum.YOGA);

        assertEquals(type1, type2);
        assertEquals(type1.hashCode(), type2.hashCode());

        TrainingType type3 = new TrainingType();
        type3.setId(2L);
        type3.setTrainingTypeName(TrainingTypeEnum.YOGA);

        assertNotEquals(type1, type3);
        assertNotEquals(type1.hashCode(), type3.hashCode());
    }

}
