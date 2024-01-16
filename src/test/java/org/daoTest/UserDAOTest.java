package org.daoTest;


import org.gym.dao.UserDAO;
import org.gym.memory.InMemoryStorage;
import org.gym.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserDAOTest {

    @Mock
    private InMemoryStorage storage;

    @InjectMocks
    private UserDAO userDAO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetUser() {
        Long userId = 1L;
        when(storage.get("User", userId)).thenReturn(new User());
        User result = userDAO.get(userId);

        assertNotNull(result);
        verify(storage, times(1)).get("User", userId);
    }

    @Test
    void testSaveUser() {
        User newUser = new User();
        when(storage.save("User", newUser.getId(), newUser)).thenReturn(newUser);

        User result = userDAO.save(newUser);

        assertNotNull(result);
        verify(storage, times(1)).save("User", newUser.getId(), newUser);
    }

    @Test
    void testDeleteUser() {
        Long userId = 1L;

        userDAO.delete(userId);

        verify(storage, times(1)).delete("User", userId);
    }

    @Test
    void testGetAllUsers() {
        Map<Long, Object> userMap = new HashMap<>();
        when(storage.getAll("User")).thenReturn(userMap);

        Map<Long, Object> result = userDAO.getAll();
        assertNotNull(result);
        verify(storage, times(1)).getAll("User");
    }

    @Test
    void testFindByUsername() {
        User user = new User();
        user.setUserName("testUsername");
        when(storage.getStorageMap()).thenReturn(getTestStorageMap());

        User result = userDAO.findByUsername(user.getUserName());

        assertEquals(user, result);
    }

    private Map<String, Map<Long, Object>> getTestStorageMap() {
        Map<String, Map<Long, Object>> storageMap = new HashMap<>();
        Map<Long, Object> userMap = new HashMap<>();
        User user = new User();
        user.setUserName("testUsername");
        userMap.put(1L, user);
        storageMap.put("User", userMap);
        return storageMap;
    }
}