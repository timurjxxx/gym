package org.controllerTest;

import org.gym.controller.AuthenticationController;
import org.gym.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
public class AuthenticationControllerTest {
    @Mock
    private UserService userService;

    @InjectMocks
    private AuthenticationController authenticationController;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLogin() {
        String username = "testUser";
        String password = "testPassword";

        ResponseEntity<Void> response = authenticationController.login(username, password);

        verify(userService, times(0)).changePassword(anyString(), anyString());
        verify(userService, times(0)).changePassword(eq(username), eq(password));
    }

    @Test
    void testChangeLogin() {
        // Arrange
        String username = "testUser";
        String password = "testPassword";
        String newPassword = "newTestPassword";
        Map<String, String> newPasswordMap = new HashMap<>();
        newPasswordMap.put("newPassword", newPassword);

        ResponseEntity<Void> response = authenticationController.changeLogin(username, password, newPasswordMap);

        verify(userService, times(1)).changePassword(eq(username), eq(newPassword)); // Verify that userService.changePassword was called with specific arguments
    }
}
