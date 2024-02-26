package org.controllerTest;

import org.gym.controller.AuthenticationController;
import org.gym.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
    void login_ShouldReturnOkStatus_WhenAuthenticationSucceeds() {
        // Arrange
        Map<String, String> credentials = new HashMap<>();
        credentials.put("username", "sampleUser");
        credentials.put("password", "samplePassword");

        // Mocking the authentication to succeed
        doNothing().when(userService).authenticated("sampleUser", "samplePassword");

        // Act
        ResponseEntity<?> response = authenticationController.login(credentials);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }


    @Test
    public void testChangeLogin() {
        // Arrange
        Map<String, String> cred = new HashMap<>();
        cred.put("username", "oldUsername");
        cred.put("password", "oldPassword");
        cred.put("newPassword", "newPassword");


        // Act
        ResponseEntity<?> responseEntity = authenticationController.changeLogin(cred);

        // Assert
        assertEquals(200, responseEntity.getStatusCodeValue());
        verify(userService, times(1)).authenticated("oldUsername", "oldPassword");
        verify(userService, times(1)).changePassword("oldUsername", "newPassword");
    }
}
