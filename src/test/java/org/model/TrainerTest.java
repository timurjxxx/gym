package org.model;

import org.gym.model.Trainer;
import org.gym.model.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class TrainerTest {


    @Test
    void toStringTest() {
        Long trainerId = 1L;
        String firstName = "John";
        String lastName = "Doe";
        String userName = "john.doe";
        String password = "password123";
        Boolean isActive = true;
        String specialization = "Runner";

        Trainer trainer = new Trainer();
        trainer.setId(trainerId);
        trainer.setFirstName(firstName);
        trainer.setLastName(lastName);
        trainer.setUserName(userName);
        trainer.setPassword(password);
        trainer.setIsActive(isActive);
        trainer.setSpecialization(specialization);

        String expectedToString = "Trainer{" +
                "id=" + trainerId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", isActive=" + isActive +
                ", specialization='" + specialization + '\'' +
                '}';

        assertEquals(expectedToString, trainer.toString());
    }

    @Test
    void hashCodeAndEqualsTest() {
        Long trainerId = 1L;
        String firstName = "John";
        String lastName = "Doe";
        String userName = "john.doe";
        String password = "password123";
        Boolean isActive = true;
        String specialization = "Runner";

        Trainer trainer1 = new Trainer();
        trainer1.setId(trainerId);
        trainer1.setFirstName(firstName);
        trainer1.setLastName(lastName);
        trainer1.setUserName(userName);
        trainer1.setPassword(password);
        trainer1.setIsActive(isActive);
        trainer1.setSpecialization(specialization);

        Trainer trainer2 = new Trainer();
        trainer2.setId(trainerId);
        trainer2.setFirstName(firstName);
        trainer2.setLastName(lastName);
        trainer2.setUserName(userName);
        trainer2.setPassword(password);
        trainer2.setIsActive(isActive);
        trainer2.setSpecialization(specialization);

        Trainer trainer3 = new Trainer();
        trainer3.setId(2L);
        trainer3.setFirstName("Jane");
        trainer3.setLastName("Doe");
        trainer3.setUserName("jane.doe");
        trainer3.setPassword("password456");
        trainer3.setIsActive(false);
        trainer3.setSpecialization("Yoga");

        int hashCode1 = trainer1.hashCode();
        int hashCode2 = trainer2.hashCode();

        assertEquals(trainer1, trainer2);
        assertNotEquals(trainer1, trainer3);
        assertEquals(hashCode1, hashCode2);
    }

    @Test
    void getterSetterTest() {
        Long trainerId = 1L;
        String firstName = "John";
        String lastName = "Doe";
        String userName = "john.doe";
        String password = "password123";
        Boolean isActive = true;
        String specialization = "Runner";

        Trainer trainer = new Trainer();
        trainer.setId(trainerId);
        trainer.setFirstName(firstName);
        trainer.setLastName(lastName);
        trainer.setUserName(userName);
        trainer.setPassword(password);
        trainer.setIsActive(isActive);
        trainer.setSpecialization(specialization);

        assertEquals(trainerId, trainer.getId());
        assertEquals(firstName, trainer.getFirstName());
        assertEquals(lastName, trainer.getLastName());
        assertEquals(userName, trainer.getUserName());
        assertEquals(password, trainer.getPassword());
        assertEquals(isActive, trainer.getIsActive());
        assertEquals(specialization, trainer.getSpecialization());
    }
}