package org.model;

import org.gym.model.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void testGetterAndSetter() {
        User user = new User();

        user.setId(1L);
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setUserName("john.doe");
        user.setPassword("password123");
        user.setIsActive(true);

        assertEquals(1L, user.getId());
        assertEquals("John", user.getFirstName());
        assertEquals("Doe", user.getLastName());
        assertEquals("john.doe", user.getUserName());
        assertEquals("password123", user.getPassword());
        assertTrue(user.getIsActive());
    }

    @Test
    void testAllArgsConstructor() {
        User user = new User(1L, "John", "Doe", "john.doe", "password123", true);

        assertEquals(1L, user.getId());
        assertEquals("John", user.getFirstName());
        assertEquals("Doe", user.getLastName());
        assertEquals("john.doe", user.getUserName());
        assertEquals("password123", user.getPassword());
        assertTrue(user.getIsActive());
    }

    @Test
    void testToString() {
        User user = new User(1L, "John", "Doe", "john.doe", "password123", true);

        assertEquals("id=1, firstName='John', lastName='Doe', userName='john.doe', password='password123', isActive=true", user.toString());
    }

    @Test
    void testEqualsAndHashCode() {
        User user1 = new User(1L, "John", "Doe", "john.doe", "password123", true);
        User user2 = new User(1L, "John", "Doe", "john.doe", "password123", true);

        assertEquals(user1, user2);
        assertEquals(user1.hashCode(), user2.hashCode());
    }

    @Test
    void testNotEquals() {
        User user1 = new User(1L, "John", "Doe", "john.doe", "password123", true);
        User user2 = new User(2L, "Jane", "Doe", "jane.doe", "password456", false);

        assertNotEquals(user1, user2);
        assertNotEquals(user1.hashCode(), user2.hashCode());
    }
}
