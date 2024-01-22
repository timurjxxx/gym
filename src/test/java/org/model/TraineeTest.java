package org.model;

import org.gym.model.Trainee;
import org.gym.model.User;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class TraineeTest {

    @Test
    void testTraineeBuilder() {
        Long traineeId = 1L;
        Date dateOfBirth = new Date();
        String address = "123 Main St";
        User user = new User();

        Trainee trainee = Trainee.builder()
                .id(traineeId)
                .dateOfBirth(dateOfBirth)
                .address(address)
                .user(user)
                .build();

        assertNotNull(trainee);
        assertEquals(traineeId, trainee.getId());
        assertEquals(dateOfBirth, trainee.getDateOfBirth());
        assertEquals(address, trainee.getAddress());
        assertEquals(user, trainee.getUser());
    }

    @Test
    void testTraineeToString() {
        Trainee trainee = new Trainee(1L, new Date(), "123 Main St", new User());

        String result = trainee.toString();

        assertNotNull(result);
        assertTrue(result.contains("id=" + trainee.getId()));
        assertTrue(result.contains("dateOfBirth=" + trainee.getDateOfBirth()));
        assertTrue(result.contains("address='" + trainee.getAddress() + "'"));
        assertTrue(result.contains("user=" + trainee.getUser()));
    }

    @Test
    void testTraineeEqualsAndHashCode() {
        Trainee trainee1 = new Trainee(1L, new Date(), "123 Main St", new User());
        Trainee trainee2 = new Trainee(1L, new Date(), "123 Main St", new User());
        Trainee trainee3 = new Trainee(2L, new Date(), "456 Side St", new User());

        assertEquals(trainee1, trainee2);
        assertNotEquals(trainee1, trainee3);
        assertNotEquals(trainee2, trainee3);

        assertEquals(trainee1.hashCode(), trainee2.hashCode());
        assertNotEquals(trainee1.hashCode(), trainee3.hashCode());
        assertNotEquals(trainee2.hashCode(), trainee3.hashCode());
    }

    @Test
    void testTraineeGettersAndSetters() {
        Trainee trainee = new Trainee();

        Long traineeId = 1L;
        Date dateOfBirth = new Date();
        String address = "123 Main St";
        User user = new User();

        trainee.setId(traineeId);
        trainee.setDateOfBirth(dateOfBirth);
        trainee.setAddress(address);
        trainee.setUser(user);

        assertEquals(traineeId, trainee.getId());
        assertEquals(dateOfBirth, trainee.getDateOfBirth());
        assertEquals(address, trainee.getAddress());
        assertEquals(user, trainee.getUser());
    }
    @Test
    void testTraineeAllArgsConstructor() {
        Long traineeId = 1L;
        Date dateOfBirth = new Date();
        String address = "123 Main St";
        User user = new User();

        Trainee trainee = new Trainee(traineeId, dateOfBirth, address, user);

        assertNotNull(trainee);
        assertEquals(traineeId, trainee.getId());
        assertEquals(dateOfBirth, trainee.getDateOfBirth());
        assertEquals(address, trainee.getAddress());
        assertEquals(user, trainee.getUser());
    }

}

