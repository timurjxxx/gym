package org.daoTest;


import org.gym.dao.UserDAO;
import org.gym.memory.InMemoryStorage;
import org.gym.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class UserDAOTest {

    @Mock
    private InMemoryStorage storage;

    @InjectMocks
    private UserDAO userDAO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetUser() {
        Long userId = 1L;
        User user = new User(userId, "John", "Doe", "john.doe", "password", true);
        when(storage.get(anyString(), anyLong())).thenReturn(user);

        User result = userDAO.get("User", userId);

        assertEquals(user, result);
        verify(storage).get("User", userId);
    }

    @Test
    void testSaveUser() {
        User user = new User(1L, "John", "Doe", "john.doe", "password", true);
        when(storage.save(anyString(), any(User.class))).thenReturn(user);

        User result = userDAO.save("User", user);

        assertEquals(user, result);
        verify(storage).save("User", user);
    }

    @Test
    void testDeleteUser() {
        Long userId = 1L;

        userDAO.delete("User", userId);

        verify(storage).deleteById("User", userId);
    }

    @Test
    void testFindByUserName() {
        String userName = "john.doe";
        User user = new User(1L, "John", "Doe", userName, "password", true);
        when(storage.getAll(anyString())).thenReturn(Collections.singletonList(user));

        Optional<User> result = userDAO.findByUserName("User", userName);

        assertTrue(result.isPresent());
        assertEquals(user, result.get());
        verify(storage).getAll("User");
    }

    @Test
    void testFindByUserName_NotFound() {
        String userName = "nonexistentUser";
        when(storage.getAll(anyString())).thenReturn(Collections.emptyList());

        Optional<User> result = userDAO.findByUserName("User", userName);

        assertTrue(result.isEmpty());
        verify(storage).getAll("User");
    }

    @Test
    void testUpdateUser() {
        Long userId = 1L;
        User updatedUser = new User(userId, "UpdatedFirstName", "UpdatedLastName", "updated.user", "newpassword", false);
        when(storage.update(anyString(), anyLong(), any(User.class))).thenReturn(updatedUser);

        User result = userDAO.update("User", userId, updatedUser);

        assertEquals(updatedUser, result);
        verify(storage).update("User", userId, updatedUser);
    }
}
