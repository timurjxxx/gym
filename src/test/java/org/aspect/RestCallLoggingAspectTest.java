//package org.aspect;
//
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.Signature;
//import org.gym.aspect.RestCallLoggingAspect;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.slf4j.Logger;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//
//import javax.servlet.http.HttpServletRequest;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//
//class RestCallLoggingAspectTest {
//
//    @Mock
//    private Logger loggerMock;
//    @Mock
//    private ServletRequestAttributes attributesMock;
//    @Mock
//    private JoinPoint joinPointMock;
//    @Mock
//    Signature signatureMock;
//
//    @Mock
//    private HttpServletRequest requestMock;
//    @InjectMocks
//    private RestCallLoggingAspect aspect;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//        RequestContextHolder.resetRequestAttributes();
//    }
//
//
//    @Test
//    void logRestCallDetails() {
//        // Set up mocks
//        when(requestMock.getRequestURI()).thenReturn("/test");
//        when(requestMock.getMethod()).thenReturn("GET");
//
//        when(attributesMock.getRequest()).thenReturn(requestMock);
//        RequestContextHolder.setRequestAttributes(attributesMock);
//
//        when(signatureMock.getName()).thenReturn("testMethod");
//        when(joinPointMock.getSignature()).thenReturn(signatureMock);
//        when(joinPointMock.getTarget()).thenReturn(new TestController());
//
//        // Call the method under test
//        aspect.logRestCallDetails(joinPointMock);
//
//        // Verify that the logger is called with the expected messages
//        verify(loggerMock).info("REST Call: /test [GET]");
//        verify(loggerMock).info("Class: TestController, Method: testMethod");
//        verify(loggerMock).info("Request Parameters: ");
//
//        // Add more verifications if needed based on your requirements
//    }
//
//
//    @Test
//    void logRestCallResult() {
//        // Mocking a ResponseEntity for testing
//        ResponseEntity<String> responseEntityMock = ResponseEntity.ok("Test response");
//        aspect.logRestCallResult(responseEntityMock);
//
//        // Verify that the logger is called with the expected message
//        verify(loggerMock).info("REST Call Result: Status: 200, Response Body: Test response");
//
//        // Add more verifications if needed based on your requirements
//    }
//
//    // Additional tests can be added for other methods or edge cases
//
//    // Example controller for testing purposes
//    private static class TestController {
//        // Define a method here if needed for testing
//    }
//}
