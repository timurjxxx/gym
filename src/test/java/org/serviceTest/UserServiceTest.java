package org.serviceTest;


import org.gym.dao.UserDAO;
import org.gym.model.User;
import org.gym.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;



import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
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

        User result = userService.createtUser(newUser);

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
}