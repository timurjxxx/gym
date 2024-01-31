package org.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.gym.aspect.AuthenticationAspect;
import org.gym.config.AppConfig;
import org.gym.dao.UserDAO;
import org.gym.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.persistence.EntityNotFoundException;
import javax.sql.DataSource;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class AuthenticationAspectTest {

    @Mock
    private UserDAO userDAO;

    @InjectMocks
    private AuthenticationAspect authenticationAspect;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAuthenticate_ValidCredentials() {
        JoinPoint joinPoint = mock(JoinPoint.class);
        Signature signature = mock(Signature.class);
        when(joinPoint.getSignature()).thenReturn(signature);
        when(signature.getName()).thenReturn("methodName");
        when(joinPoint.getArgs()).thenReturn(new Object[]{"validUsername", "validPassword"});

        User user = new User();
        user.setPassword("validPassword");

        when(userDAO.findUserByUserName("validUsername")).thenReturn(java.util.Optional.of(user));

        authenticationAspect.authenticate(joinPoint);

        verify(userDAO, times(1)).findUserByUserName("validUsername");
    }

    @Test
    public void testAuthenticate_InvalidCredentials() {
        JoinPoint joinPoint = mock(JoinPoint.class);
        Signature signature = mock(Signature.class);
        when(joinPoint.getSignature()).thenReturn(signature);
        when(signature.getName()).thenReturn("methodName");
        when(joinPoint.getArgs()).thenReturn(new Object[]{"invalidUsername", "invalidPassword"});

        when(userDAO.findUserByUserName("invalidUsername")).thenReturn(java.util.Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> authenticationAspect.authenticate(joinPoint));

        verify(userDAO, times(1)).findUserByUserName("invalidUsername");
    }

    @Test
    public void testAuthenticate_InvalidMethodArguments() {
        JoinPoint joinPoint = mock(JoinPoint.class);
        Signature signature = mock(Signature.class);
        when(joinPoint.getSignature()).thenReturn(signature);
        when(signature.getName()).thenReturn("methodName");
        when(joinPoint.getArgs()).thenReturn(new Object[]{});

        assertThrows(IllegalArgumentException.class, () -> authenticationAspect.authenticate(joinPoint));

        verify(userDAO, never()).findUserByUserName(anyString());
    }

    @Test
    public void testEntityManagerFactory() throws SQLException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        AppConfig appConfig = context.getBean(AppConfig.class);
        DataSource mockDataSource = mock(DataSource.class);

        when(mockDataSource.getConnection()).thenReturn(null);

        assertNotNull(appConfig.entityManagerFactory(mockDataSource));

    }
}
