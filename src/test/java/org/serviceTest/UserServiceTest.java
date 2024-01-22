package org.serviceTest;

import org.gym.dao.UserDAO;
import org.gym.model.User;
import org.gym.service.UserService;
import org.gym.utils.Generate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserDAO userDAO;

    @Mock
    private Generate generate;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        when(generate.generateUniqueId(anyString())).thenReturn(1L);
        when(generate.generatePassword()).thenReturn("mockedPassword");
        when(userDAO.findByUserName(anyString(), anyString())).thenReturn(Optional.empty());
    }

    @Test
    void testCreateUser() {
        // Given
        User newUser = User.builder()
                .firstName("John")
                .lastName("Doe")
                .build();

        when(generate.generateUniqueId(anyString())).thenReturn(1L);
        when(generate.generatePassword()).thenReturn("mockedPassword");
        when(userDAO.save(anyString(), any(User.class))).thenReturn(newUser);

        User createdUser = userService.createUser(newUser);

        assertNotNull(createdUser.getId());
        assertEquals("mockedPassword", createdUser.getPassword());
        assertNotNull(createdUser.getUserName());
        verify(userDAO).save("User", newUser);
    }

    @Test
    void testSelectUser() {
        Long userId = 1L;
        User mockedUser = User.builder()
                .id(userId)
                .firstName("John")
                .lastName("Doe")
                .build();

        when(userDAO.get(anyString(), anyLong())).thenReturn(mockedUser);

        User selectedUser = userService.selectUser(userId);

        assertEquals(mockedUser, selectedUser);
        verify(userDAO).get("User", userId);
    }

    @Test
    void testUpdateUser() {
        // Given
        Long userId = 1L;
        User updatedUser = User.builder()
                .id(userId)
                .firstName("UpdatedFirstName")
                .lastName("UpdatedLastName")
                .build();

        when(userDAO.update(anyString(), anyLong(), any(User.class))).thenReturn(updatedUser);

        User result = userService.updateUser(userId, updatedUser);

        assertEquals(updatedUser, result);
        verify(userDAO).update("User", userId, updatedUser);
    }

    @Test
    void testDeleteUser() {
        Long userId = 1L;

        userService.deleteUser(userId);

        verify(userDAO).delete("User", userId);
    }
}
