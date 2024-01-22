package org.model;

import org.gym.model.Trainer;
import org.gym.model.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class TrainerTest {

    @Test
    void testTrainerBuilder() {
        Long trainerId = 1L;
        String specialization = "Fitness";
        User user = new User(101L, "John", "Doe", "john.doe", "password", true);

        Trainer trainer = Trainer.builder()
                .id(trainerId)
                .specialization(specialization)
                .user(user)
                .build();

        assertEquals(trainerId, trainer.getId());
        assertEquals(specialization, trainer.getSpecialization());
        assertEquals(user, trainer.getUser());
    }

    @Test
    void testGettersAndSetters() {
        Trainer trainer = new Trainer();
        Long trainerId = 2L;
        String specialization = "Strength Training";
        User user = new User(102L, "Jane", "Smith", "jane.smith", "newpassword", false);

        trainer.setId(trainerId);
        trainer.setSpecialization(specialization);
        trainer.setUser(user);

        assertEquals(trainerId, trainer.getId());
        assertEquals(specialization, trainer.getSpecialization());
        assertEquals(user, trainer.getUser());
    }

    @Test
    void testEqualsAndHashCode() {
        Trainer trainer1 = new Trainer(1L, "Fitness", new User(201L, "Trainer1", "Trainer1", "trainer1", "password1", true));
        Trainer trainer2 = new Trainer(1L, "Fitness", new User(201L, "Trainer1", "Trainer1", "trainer1", "password1", true));
        Trainer trainer3 = new Trainer(2L, "Strength Training", new User(202L, "Trainer2", "Trainer2", "trainer2", "password2", false));

        assertEquals(trainer1, trainer2);
        assertEquals(trainer1.hashCode(), trainer2.hashCode());

        assertNotEquals(trainer1, trainer3);
        assertNotEquals(trainer1.hashCode(), trainer3.hashCode());
    }

    @Test
    void testToString() {
        Long trainerId = 1L;
        String specialization = "Fitness";
        User user = new User(201L, "Trainer1", "Trainer1", "trainer1", "password1", true);

        Trainer trainer = new Trainer(trainerId, specialization, user);

        String toStringResult = trainer.toString();

        assertEquals("Trainer{" +
                "id=" + trainerId +
                ", specialization='" + specialization + '\'' +
                ", user=" + user +
                '}', toStringResult);
    }

    @Test
    void testAllArgsConstructor() {
        Long trainerId = 3L;
        String specialization = "Yoga";
        User user = new User(301L, "YogaTrainer", "YogaTrainer", "yogatrainer", "yogapassword", true);

        Trainer trainer = new Trainer(trainerId, specialization, user);

        assertEquals(trainerId, trainer.getId());
        assertEquals(specialization, trainer.getSpecialization());
        assertEquals(user, trainer.getUser());
    }
}