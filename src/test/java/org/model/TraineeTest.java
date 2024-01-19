package org.model;

import org.gym.model.Trainee;
import org.gym.model.User;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.Date;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.Date;

import org.junit.jupiter.api.Test;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class TraineeTest {
    @Test
    void testToString() {
        Trainee trainee = new Trainee();
        trainee.setId(1L);
        trainee.setFirstName("Alice");
        trainee.setLastName("Smith");
        trainee.setUserName("alice123");
        trainee.setPassword("password456");
        trainee.setIsActive(true);
        trainee.setDateOfBirth(new Date());
        trainee.setAddress("123 Main Street");

        String expectedToString = "User{id=1, firstName='Alice', lastName='Smith', userName='alice123', password='password456', isActive=true}, dateOfBirth=" + trainee.getDateOfBirth() + ", address='123 Main Street'";

        assertEquals(expectedToString, trainee.toString());
    }

    @Test
    void testEqualsAndHashCode() {
        Date dateOfBirth = new Date();
        Trainee trainee1 = new Trainee(1L, "Alice", "Smith", "alice123", "password456", true, dateOfBirth, "123 Main Street");
        Trainee trainee2 = new Trainee(1L, "Alice", "Smith", "alice123", "password456", true, dateOfBirth, "123 Main Street");
        Trainee trainee3 = new Trainee(2L, "Bob", "Johnson", "bob123", "password789", false, new Date(), "456 Oak Street");

        // Test equals
        assertEquals(trainee1, trainee2);
        assertNotEquals(trainee1, trainee3);

        // Test hashCode
        assertEquals(trainee1.hashCode(), trainee2.hashCode());
        assertNotEquals(trainee1.hashCode(), trainee3.hashCode());
    }

    @Test
    void testGettersAndSetters() {
        Date dateOfBirth = new Date();
        Trainee trainee = new Trainee();

        // Test setter and getter for dateOfBirth
        trainee.setDateOfBirth(dateOfBirth);
        assertEquals(dateOfBirth, trainee.getDateOfBirth());

        // Test setter and getter for address
        trainee.setAddress("123 Main Street");
        assertEquals("123 Main Street", trainee.getAddress());
    }
}

