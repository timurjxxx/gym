package org.model;

import org.gym.model.Trainer;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class TrainerTest {

    @Test
    void testSpecializationGetterSetter() {
        Trainer trainer = new Trainer();
        assertNull(trainer.getSpecialization());

        trainer.setSpecialization("Fitness");
        assertEquals("Fitness", trainer.getSpecialization());
    }

    @Test
    void testEqualsAndHashCode() {
        Trainer trainer1 = new Trainer(1L, "John", "Doe", "johndoe", "password123", true, "Fitness");
        Trainer trainer2 = new Trainer(1L, "John", "Doe", "johndoe", "password123", true, "Fitness");
        Trainer trainer3 = new Trainer(2L, "Jane", "Doe", "janedoe", "password456", true, "Yoga");

        // Test equals
        assertEquals(trainer1, trainer2);
        assertNotEquals(trainer1, trainer3);

        // Test hashCode
        assertEquals(trainer1.hashCode(), trainer2.hashCode());
        assertNotEquals(trainer1.hashCode(), trainer3.hashCode());
    }

    @Test
    void testToString() {
        Trainer trainer = new Trainer(1L, "John", "Doe", "johndoe", "password123", true, "Fitness");

        String expectedToString = "Trainer{" +
                "id=1, " +
                "firstName='John', " +
                "lastName='Doe', " +
                "userName='johndoe', " +
                "password='password123', " +
                "isActive=true, " +
                "specialization='Fitness'}";

        assertEquals(expectedToString, trainer.toString());
    }


    @Test
    void testSetSpecialization() {
        Trainer trainer = new Trainer();

        assertNull(trainer.getSpecialization());

        // Set specialization
        String specialization = "Fitness";
        trainer.setSpecialization(specialization);

        // Verify that the specialization has been set
        assertEquals(specialization, trainer.getSpecialization());
    }

    @Test
    void testGetSpecialization() {
        // Create a Trainer with a known specialization
        Trainer trainer = new Trainer();
        String specialization = "Fitness";
        trainer.setSpecialization(specialization);

        // Verify that getSpecialization returns the expected value
        assertEquals(specialization, trainer.getSpecialization());
    }

}
