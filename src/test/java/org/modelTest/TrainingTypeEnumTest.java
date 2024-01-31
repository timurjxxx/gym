package org.modelTest;

import org.gym.model.TrainingTypeEnum;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class TrainingTypeEnumTest {

    @Test
    void testEnumValues() {
        assertEquals(TrainingTypeEnum.values().length, 5);
        assertEquals(TrainingTypeEnum.valueOf("Flexibility"), TrainingTypeEnum.Flexibility);
        assertEquals(TrainingTypeEnum.valueOf("Cardio"), TrainingTypeEnum.Cardio);
        assertEquals(TrainingTypeEnum.valueOf("Aerobic"), TrainingTypeEnum.Aerobic);
        assertEquals(TrainingTypeEnum.valueOf("YOGA"), TrainingTypeEnum.YOGA);
        assertEquals(TrainingTypeEnum.valueOf("TECHNICAL"), TrainingTypeEnum.TECHNICAL);
    }

    @Test
    void testEqualsAndHashCode() {
        assertEquals(TrainingTypeEnum.Flexibility, TrainingTypeEnum.Flexibility);
        assertNotEquals(TrainingTypeEnum.Flexibility, TrainingTypeEnum.Cardio);
        assertNotEquals(TrainingTypeEnum.Flexibility, null);
        assertNotEquals(TrainingTypeEnum.Flexibility, "Flexibility");
        assertEquals(TrainingTypeEnum.Flexibility.hashCode(), TrainingTypeEnum.Flexibility.hashCode());
        assertNotEquals(TrainingTypeEnum.Flexibility.hashCode(), TrainingTypeEnum.Cardio.hashCode());
    }
}
