package org.daoTest;

import org.gym.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.MockitoAnnotations;
import org.gym.dao.UserDAO;
import org.gym.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserDAOTest {

    @Mock
    private UserDAO userDAO;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindUserByUserName() {
        User user = new User();
        user.setUserName("testUsername");

        when(userDAO.findUserByUserName("testUsername")).thenReturn(Optional.of(user));

        Optional<User> foundUser = Optional.ofNullable(userService.findUserByUserName("testUsername"));

        assertTrue(foundUser.isPresent());
        assertEquals("testUsername", foundUser.get().getUserName());
    }


}
