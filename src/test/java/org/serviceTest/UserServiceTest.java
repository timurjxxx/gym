package org.serviceTest;


import org.gym.dao.UserDAO;
import org.gym.model.User;
import org.gym.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserDAO userDAO;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSelectUser() {
        Long userId = 1L;
        when(userDAO.get(userId)).thenReturn(new User());

        User result = userService.selectUser(userId);

        assertNotNull(result);
        verify(userDAO, times(1)).get(userId);
    }

    @Test
    void testCreateUser() {
        User newUser = new User();
        when(userDAO.save(any(User.class))).thenAnswer(invocation -> {
            User savedUser = invocation.getArgument(0);
            savedUser.setId(1L);
            savedUser.setUserName("testUsername");
            savedUser.setPassword("testPassword");
            return savedUser;
        });

        User result = userService.createUser(newUser);

        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getUserName());
        assertNotNull(result.getPassword());
        verify(userDAO, times(1)).save(any(User.class));
    }

    @Test
    void testUpdateUser() {
        Long userId = 1L;
        User updatedUser = new User();
        when(userDAO.get(userId)).thenReturn(new User());
        when(userDAO.save(any(User.class))).thenReturn(new User());

        User result = userService.updateUser(userId, updatedUser);

        assertNotNull(result);
        verify(userDAO, times(1)).get(userId);
        verify(userDAO, times(1)).save(any(User.class));
    }

    @Test
    void testDeleteUser() {
        Long userId = 1L;

        userService.deletetUser(userId);

        verify(userDAO, times(1)).delete(userId);
    }

    @Test
    void testSelectAllUsers() {
        Map<Long, Object> userMap = new HashMap<>();
        when(userDAO.getAll()).thenReturn(userMap);

        Map<Long, Object> result = userService.selectAllUsers();

        assertNotNull(result);
        verify(userDAO, times(1)).getAll();
    }

    @Test
    public void testGenerateUsername() {
        // Arrange
        String firstName = "John";
        String lastName = "Doe";
        String username1 = firstName + "." + lastName;

        when(userDAO.findByUsername(username1)).thenReturn(null);

        String generatedUsername = userService.generateUsername(username1);

        assertEquals(username1, generatedUsername);

        verify(userDAO, times(1)).findByUsername(username1);
    }


    @Test
    public void testGenerateUsernameWithExistingUsername() {
        // Arrange
        String firstName = "John";
        String lastName = "Doe";
        String username1 = firstName + "." + lastName;

        when(userDAO.findByUsername(username1)).thenReturn(new User());

        String generatedUsername = userService.generateUsername(username1);

        assertEquals(username1 + ".2", generatedUsername);

        verify(userDAO, times(1)).findByUsername(username1);
        verify(userDAO, times(1)).findByUsername(username1 + ".2");
    }

    @Test
    public void testGeneratePassword() {
        // Arrange
        Set<String> generatedPasswords = new HashSet<>();

        // Act & Assert
        for (int i = 0; i < 1000; i++) {
            String generatedPassword = userService.generatePassword();

            assertNotNull(generatedPassword);
            assertFalse(generatedPassword.isEmpty());

            assertFalse(generatedPasswords.contains(generatedPassword));
            generatedPasswords.add(generatedPassword);

            assertEquals(10, generatedPassword.length());

            assertTrue(generatedPassword.matches("[a-zA-Z0-9]+"));
        }
    }
}