package org.model;

import org.gym.model.Trainee;
import org.gym.model.User;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class TraineeTest {

    @Test
    void toStringTest() {
        Long traineeId = 1L;
        String firstName = "John";
        String lastName = "Doe";
        String address = "123 Main St";
        String password = "password123";
        String userName = "john.doe";
        Date dateOfBirth = new Date();
        Boolean isActive = true;

        Trainee trainee = new Trainee();
        trainee.setId(traineeId);
        trainee.setFirstName(firstName);
        trainee.setLastName(lastName);
        trainee.setAddress(address);
        trainee.setPassword(password);
        trainee.setUserName(userName);
        trainee.setDateOfBirth(dateOfBirth);
        trainee.setIsActive(isActive);

        String expectedToString = "Trainee{" +
                "id=" + traineeId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", isActive=" + isActive +
                ", dateOfBirth=" + dateOfBirth +
                ", address='" + address + '\'' +
                '}';

        assertEquals(expectedToString, trainee.toString());
    }

    @Test
    void hashCodeAndEqualsTest() {
        Long traineeId = 1L;
        String firstName = "John";
        String lastName = "Doe";
        String address = "123 Main St";
        String password = "password123";
        String userName = "john.doe";
        Date dateOfBirth = new Date();
        Boolean isActive = true;

        Trainee trainee1 = new Trainee();
        trainee1.setId(traineeId);
        trainee1.setFirstName(firstName);
        trainee1.setLastName(lastName);
        trainee1.setAddress(address);
        trainee1.setPassword(password);
        trainee1.setUserName(userName);
        trainee1.setDateOfBirth(dateOfBirth);
        trainee1.setIsActive(isActive);

        Trainee trainee2 = new Trainee();
        trainee2.setId(traineeId);
        trainee2.setFirstName(firstName);
        trainee2.setLastName(lastName);
        trainee2.setAddress(address);
        trainee2.setPassword(password);
        trainee2.setUserName(userName);
        trainee2.setDateOfBirth(dateOfBirth);
        trainee2.setIsActive(isActive);

        Trainee trainee3 = new Trainee();
        trainee3.setId(2L);
        trainee3.setFirstName("Jane");
        trainee3.setLastName("Doe");
        trainee3.setAddress("456 Oak St");
        trainee3.setPassword("password456");
        trainee3.setUserName("jane.doe");
        trainee3.setDateOfBirth(new Date());
        trainee3.setIsActive(false);

        int hashCode1 = trainee1.hashCode();
        int hashCode2 = trainee2.hashCode();

        assertEquals(trainee1, trainee2);
        assertNotEquals(trainee1, trainee3);
        assertEquals(hashCode1, hashCode2);
    }

    @Test
    void allArgsConstructorTest() {
        Long traineeId = 1L;
        String firstName = "John";
        String lastName = "Doe";
        String address = "123 Main St";
        String password = "password123";
        String userName = "john.doe";
        Date dateOfBirth = new Date();
        Boolean isActive = true;

        Trainee trainee = new Trainee();
        trainee.setId(traineeId);
        trainee.setFirstName(firstName);
        trainee.setLastName(lastName);
        trainee.setAddress(address);
        trainee.setPassword(password);
        trainee.setUserName(userName);
        trainee.setDateOfBirth(dateOfBirth);
        trainee.setIsActive(isActive);

        assertEquals(traineeId, trainee.getId());
        assertEquals(firstName, trainee.getFirstName());
        assertEquals(lastName, trainee.getLastName());
        assertEquals(address, trainee.getAddress());
        assertEquals(password, trainee.getPassword());
        assertEquals(userName, trainee.getUserName());
        assertEquals(dateOfBirth, trainee.getDateOfBirth());
        assertEquals(isActive, trainee.getIsActive());
    }

    @Test
    void getterSetterTest() {
        // Arrange
        Long traineeId = 1L;
        String firstName = "John";
        String lastName = "Doe";
        String address = "123 Main St";
        String password = "password123";
        String userName = "john.doe";
        Date dateOfBirth = new Date();
        Boolean isActive = true;

        Trainee trainee = new Trainee();
        trainee.setId(traineeId);
        trainee.setFirstName(firstName);
        trainee.setLastName(lastName);
        trainee.setAddress(address);
        trainee.setPassword(password);
        trainee.setUserName(userName);
        trainee.setDateOfBirth(dateOfBirth);
        trainee.setIsActive(isActive);

        assertEquals(traineeId, trainee.getId());
        assertEquals(firstName, trainee.getFirstName());
        assertEquals(lastName, trainee.getLastName());
        assertEquals(address, trainee.getAddress());
        assertEquals(password, trainee.getPassword());
        assertEquals(userName, trainee.getUserName());
        assertEquals(dateOfBirth, trainee.getDateOfBirth());
        assertEquals(isActive, trainee.getIsActive());
    }
}

