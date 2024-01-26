package org.service;

import org.gym.dao.UserDAO;
import org.gym.model.User;
import org.gym.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private UserDAO userDAO;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

    }
    @Test
    void testSelectUser() {
        // Mocking
        Long userId = 1L;
        User sampleUser = new User();
        sampleUser.setId(userId);
        sampleUser.setFirstName("John");
        sampleUser.setLastName("Doe");
        sampleUser.setUserName("johndoe");
        sampleUser.setPassword("password");
        sampleUser.setIsActive(true);

        when(userDAO.findById(anyLong())).thenReturn(Optional.of(sampleUser));

        User user = userService.selectUser(userId);

        assertNotNull(user);
        assertEquals(userId, user.getId());
        verify(userDAO, times(1)).findById(anyLong());
    }
    @Test
    void testCreateUser() {


        User newUser = new User();
        newUser.setFirstName("John");
        newUser.setLastName("Doe");
        newUser.setUserName("John.Doe");
        newUser.setIsActive(true);

        when(userDAO.save(any())).thenReturn(newUser);

        User createdUser = userService.createUser(newUser);

        assertNotNull(createdUser);
        assertEquals("John", createdUser.getFirstName());
        assertEquals("Doe", createdUser.getLastName());
        assertEquals("John.Doe", createdUser.getUserName());
        assertTrue(createdUser.getIsActive());
    }

    @Test
    void testUpdateUser() {


        Long userId = 1L;
        User existingUser = new User();
        existingUser.setId(userId);
        existingUser.setFirstName("OldFirstName");
        existingUser.setLastName("OldLastName");
        existingUser.setUserName("OldUserName");
        existingUser.setIsActive(true);

        User updatedUser = new User();
        updatedUser.setFirstName("NewFirstName");
        updatedUser.setLastName("NewLastName");
        updatedUser.setUserName("NewUserName");
        updatedUser.setIsActive(false);

        when(userDAO.findById(anyLong())).thenReturn(Optional.of(existingUser));
        when(userDAO.save(any())).thenReturn(updatedUser);

        User result = userService.updateUser(userId, updatedUser);

        assertNotNull(result);
        assertEquals("NewFirstName", result.getFirstName());
        assertEquals("NewLastName", result.getLastName());
        assertEquals("NewUserName", result.getUserName());
        assertFalse(result.getIsActive());
        verify(userDAO, times(1)).findById(anyLong());
        verify(userDAO, times(1)).save(any());
    }

    @Test
    void testFindUserByUserName() {
        String username = "testUser";
        User sampleUser = new User();
        sampleUser.setId(1L);
        sampleUser.setFirstName("John");
        sampleUser.setLastName("Doe");
        sampleUser.setUserName(username);
        sampleUser.setPassword("password");
        sampleUser.setIsActive(true);

        when(userDAO.findUserByUserName(anyString())).thenReturn(Optional.of(sampleUser));

        User user = userService.findUserByUserName(username, "password");

        assertNotNull(user);
        assertEquals(username, user.getUserName());
        verify(userDAO, times(1)).findUserByUserName(anyString());
    }

    @Test
    void testDeleteUserByUserName() {


        String userNameToDelete = "ToDelete";
        when(userDAO.findUserByUserName(anyString())).thenReturn(Optional.of(new User()));

        assertDoesNotThrow(() -> userService.deleteUserByUserName(userNameToDelete));

        verify(userDAO, times(1)).findUserByUserName(eq(userNameToDelete));
        verify(userDAO, times(1)).deleteUserByUserName(eq(userNameToDelete));
    }

    @Test
    void testChangePassword() {

        String username = "testUser";
        String newPassword = "newPassword";

        User existingUser = new User();
        existingUser.setUserName(username);

        when(userDAO.findUserByUserName(eq(username))).thenReturn(Optional.of(existingUser));
        when(userDAO.save(any())).thenReturn(existingUser);

        String changedPassword = userService.changePassword(username, newPassword);

        assertNotNull(changedPassword);
        assertEquals(newPassword, changedPassword);
        verify(userDAO, times(1)).findUserByUserName(eq(username));
        verify(userDAO, times(1)).save(any());
    }

    @Test
    void testChangeStatus() {
        UserDAO userDAO = mock(UserDAO.class);
        ModelMapper modelMapper = mock(ModelMapper.class);
        UserService userService = new UserService(userDAO, modelMapper);

        String username = "testUser";

        User existingUser = new User();
        existingUser.setUserName(username);
        existingUser.setIsActive(true);

        when(userDAO.findUserByUserName(eq(username))).thenReturn(Optional.of(existingUser));
        when(userDAO.save(any())).thenReturn(existingUser);

        boolean newStatus = userService.changeStatus(username);

        assertFalse(newStatus);
        verify(userDAO, times(1)).findUserByUserName(eq(username));
        verify(userDAO, times(1)).save(any());
    }

    @Test
    void testGenerateUsername() {
        String baseUsername = "testUser";
        when(userDAO.findUserByUserName(anyString())).thenReturn(Optional.empty());

        String generatedUsername = userService.generateUsername(baseUsername);

        assertNotNull(generatedUsername);
        assertTrue(generatedUsername.startsWith(baseUsername));
        verify(userDAO, atLeastOnce()).findUserByUserName(anyString());
    }


    @Test
    void testGeneratePassword() {
        String generatedPassword = userService.generatePassword();

        assertNotNull(generatedPassword);
        assertEquals(10, generatedPassword.length());
        assertTrue(generatedPassword.matches("[A-Za-z0-9]{10}"));
    }
}
