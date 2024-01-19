package org.model;

import org.gym.model.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {
    @Test
    public void testUserBuilder() {
        // Arrange
        Long userId = 1L;
        String firstName = "John";
        String lastName = "Doe";
        String userName = "john.doe";
        String password = "password123";
        Boolean isActive = true;

        // Act
        User user = User.builder()
                .id(userId)
                .firstName(firstName)
                .lastName(lastName)
                .userName(userName)
                .password(password)
                .isActive(isActive)
                .build();

        // Assert
        assertNotNull(user);
        assertEquals(userId, user.getId());
        assertEquals(firstName, user.getFirstName());
        assertEquals(lastName, user.getLastName());
        assertEquals(userName, user.getUserName());
        assertEquals(password, user.getPassword());
        assertEquals(isActive, user.getIsActive());
    }

    @Test
    public void testToString() {
        // Arrange
        User user = new User(1L, "John", "Doe", "john.doe", "password123", true);

        // Act
        String userToString = user.toString();

        // Assert
        assertNotNull(userToString);
        assertTrue(userToString.contains("User"));
        assertTrue(userToString.contains("id=1"));
        assertTrue(userToString.contains("firstName='John'"));
        assertTrue(userToString.contains("lastName='Doe'"));
        assertTrue(userToString.contains("userName='john.doe'"));
        assertTrue(userToString.contains("password='password123'"));
        assertTrue(userToString.contains("isActive=true"));
    }
}
