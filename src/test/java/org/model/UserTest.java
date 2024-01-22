package org.model;

import org.gym.model.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void testUserBuilder() {
        Long userId = 1L;
        String firstName = "John";
        String lastName = "Doe";
        String userName = "john.doe";
        String password = "password";
        Boolean isActive = true;

        User user = User.builder()
                .id(userId)
                .firstName(firstName)
                .lastName(lastName)
                .userName(userName)
                .password(password)
                .isActive(isActive)
                .build();

        assertEquals(userId, user.getId());
        assertEquals(firstName, user.getFirstName());
        assertEquals(lastName, user.getLastName());
        assertEquals(userName, user.getUserName());
        assertEquals(password, user.getPassword());
        assertEquals(isActive, user.getIsActive());
    }

    @Test
    void testToString() {
        // Given
        Long userId = 1L;
        String firstName = "John";
        String lastName = "Doe";
        String userName = "john.doe";
        String password = "password";
        Boolean isActive = true;

        User user = new User(userId, firstName, lastName, userName, password, isActive);
        String toStringResult = user.toString();

        assertTrue(toStringResult.contains("id=" + userId));
        assertTrue(toStringResult.contains("firstName='" + firstName + "'"));
        assertTrue(toStringResult.contains("lastName='" + lastName + "'"));
        assertTrue(toStringResult.contains("userName='" + userName + "'"));
        assertTrue(toStringResult.contains("password='" + password + "'"));
        assertTrue(toStringResult.contains("isActive=" + isActive));
    }

    @Test
    void testGettersAndSetters() {
        User user = new User();

        Long userId = 2L;
        String firstName = "Jane";
        String lastName = "Smith";
        String userName = "jane.smith";
        String password = "newpassword";
        Boolean isActive = false;

        user.setId(userId);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setUserName(userName);
        user.setPassword(password);
        user.setIsActive(isActive);

        assertEquals(userId, user.getId());
        assertEquals(firstName, user.getFirstName());
        assertEquals(lastName, user.getLastName());
        assertEquals(userName, user.getUserName());
        assertEquals(password, user.getPassword());
        assertEquals(isActive, user.getIsActive());
    }

    @Test
    void testEqualsAndHashCode() {
        User user1 = new User(1L, "John", "Doe", "john.doe", "password", true);
        User user2 = new User(1L, "John", "Doe", "john.doe", "password", true);

        assertEquals(user1, user2);
        assertEquals(user1.hashCode(), user2.hashCode());
    }
    @Test
    void testAllArgsConstructor() {
        Long userId = 3L;
        String firstName = "Alice";
        String lastName = "Wonderland";
        String userName = "alice.wonderland";
        String password = "alicepassword";
        Boolean isActive = true;

        User user = new User(userId, firstName, lastName, userName, password, isActive);

        assertEquals(userId, user.getId());
        assertEquals(firstName, user.getFirstName());
        assertEquals(lastName, user.getLastName());
        assertEquals(userName, user.getUserName());
        assertEquals(password, user.getPassword());
        assertEquals(isActive, user.getIsActive());
    }
}
