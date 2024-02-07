package org.modelTest;

import org.gym.model.Trainee;
import org.gym.model.Trainer;
import org.gym.model.Training;
import org.gym.model.User;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TraineeTest {

    @Test
    public void testGetterAndSetterForDateOfBirth() {
        Trainee trainee = new Trainee();
        LocalDate dateOfBirth = LocalDate.now().minusYears(25);
        trainee.setDateOfBirth(dateOfBirth);
        assertEquals(dateOfBirth, trainee.getDateOfBirth());
    }

    @Test
    public void testGetterAndSetterForAddress() {
        Trainee trainee = new Trainee();
        String address = "123 Main Street";
        trainee.setAddress(address);
        assertEquals(address, trainee.getAddress());
    }

    @Test
    public void testGetterAndSetterForUser() {
        Trainee trainee = new Trainee();
        User user = new User();
        trainee.setUser(user);
        assertEquals(user, trainee.getUser());
    }

    @Test
    public void testGetterAndSetterForTrainers() {
        Trainee trainee = new Trainee();
        Set<Trainer> trainers = new HashSet<>();
        trainee.setTrainers(trainers);
        assertEquals(trainers, trainee.getTrainers());
    }

    @Test
    public void testGetterAndSetterForTraineeTrainings() {
        Trainee trainee = new Trainee();
        List<Training> traineeTrainings = List.of(new Training(), new Training());
        trainee.setTraineeTrainings(traineeTrainings);
        assertEquals(traineeTrainings, trainee.getTraineeTrainings());
    }

    @Test
    public void testEqualsAndHashCode() {
        Trainee trainee1 = new Trainee();
        trainee1.setId(1L);

        Trainee trainee2 = new Trainee();
        trainee2.setId(1L);

        assertEquals(trainee1, trainee2);
        assertEquals(trainee1.hashCode(), trainee2.hashCode());
    }

    @Test
    public void testToString() {
        Trainee trainee = new Trainee();
        User user = new User();
        user.setFirstName("Alice");
        user.setLastName("Doe");
        trainee.setUser(user);

        trainee.setDateOfBirth(LocalDate.now().minusYears(30));
        trainee.setAddress("456 Oak Street");

        String expectedToString = "Trainee{" + user +
                ", dateOfBirth=" + trainee.getDateOfBirth() +
                ", address='" + trainee.getAddress() + '\'' +
                '}';
        assertEquals(expectedToString, trainee.toString());
    }
}
