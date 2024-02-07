package org.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.gym.aspect.RestCallLoggingAspect;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

import static org.mockito.Mockito.*;

class RestCallLoggingAspectTest {

    @Mock
    private Logger logger;

    @Mock
    private ProceedingJoinPoint joinPoint;

    @InjectMocks
    private RestCallLoggingAspect restCallLoggingAspect;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void logRestCallDetails() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest()).thenReturn(request);

        when(joinPoint.getSignature().getName()).thenReturn("testMethod");
        when(joinPoint.getTarget().getClass().getSimpleName()).thenReturn("TestController");

        restCallLoggingAspect.logRestCallDetails(joinPoint);

        verify(logger).info("REST Call: {} [{}]", request.getRequestURI(), request.getMethod());
        verify(logger).info("Class: {}, Method: {}", "TestController", "testMethod");
        verify(logger).info("Request Parameters: {}", "");
    }

    @Test
    void logRestCallResult() {
        Object result = mock(Object.class);
        restCallLoggingAspect.logRestCallResult(result);

        verify(logger).info("REST Call Result: {}", "Invalid response type");
    }
}
