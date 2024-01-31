package org.modelTest;

import org.gym.model.Trainee;
import org.gym.model.Trainer;
import org.gym.model.Training;
import org.gym.model.User;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class TrainerTest {

    @Test
    void testEqualsAndHashCode() {
        // Create trainers with the same ID
        Trainer trainer1 = new Trainer();
        trainer1.setId(1L);
        Trainer trainer2 = new Trainer();
        trainer2.setId(1L);
        // Create trainers with different ID
        Trainer trainer3 = new Trainer();
        trainer3.setId(2L);

        // Test equals
        assertEquals(trainer1, trainer2);
        assertNotEquals(trainer1, trainer3);
        assertNotEquals(trainer2, trainer3);

        // Test hashCode
        assertEquals(trainer1.hashCode(), trainer2.hashCode());
        assertNotEquals(trainer1.hashCode(), trainer3.hashCode());
        assertNotEquals(trainer2.hashCode(), trainer3.hashCode());
    }

    @Test
    void testGettersAndSetters() {
        // Create a trainer
        Trainer trainer = new Trainer();

        // Set values using setters
        trainer.setId(1L);
        trainer.setSpecialization("Test Specialization");

        // Create a user
        User user = new User();
        trainer.setUser(user);

        // Create a set of trainees
        Set<Trainee> trainees = new HashSet<>();
        trainees.add(new Trainee());
        trainees.add(new Trainee());
        trainer.setTrainees(trainees);

        // Create a list of trainings
        List<Training> trainings = List.of(new Training(), new Training());
        trainer.setTraineeTrainings(trainings);

        // Test getters
        assertEquals(1L, trainer.getId());
        assertEquals("Test Specialization", trainer.getSpecialization());
        assertEquals(user, trainer.getUser());
        assertEquals(trainees, trainer.getTrainees());
        assertEquals(trainings, trainer.getTraineeTrainings());
    }
}
