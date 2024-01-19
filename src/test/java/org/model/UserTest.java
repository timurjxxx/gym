package org.model;

import org.gym.model.User;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class UserTest {

    @Test
    public void testNoArgsConstructor() {
        User user = new User();
        assertEquals(null, user.getId());
    }

    @Test
    public void testAllArgsConstructor() {
        User user = new User(1L, "John", "Doe", "johndoe", "password123", true);
        assertEquals(1L, user.getId());
    }

    @Test
    public void testBuilder() {
        User user = User.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .userName("johndoe")
                .password("password123")
                .isActive(true)
                .build();
        assertEquals(1L, user.getId());
    }

    @Test
    public void testEqualsAndHashCode() {
        User user1 = new User(1L, "John", "Doe", "johndoe", "password123", true);
        User user2 = new User(1L, "John", "Doe", "johndoe", "password123", true);

        assertEquals(user1, user2);
        assertEquals(user1.hashCode(), user2.hashCode());

        user2.setId(2L);
        assertNotEquals(user1, user2);
        assertNotEquals(user1.hashCode(), user2.hashCode());
    }

    @Test
    public void testToString() {
        User user = User.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .userName("johndoe")
                .password("password123")
                .isActive(true)
                .build();

        String expectedToString = "User{id=1, firstName='John', lastName='Doe', userName='johndoe', password='password123', isActive=true}";
        assertEquals(expectedToString, user.toString());
    }

}
