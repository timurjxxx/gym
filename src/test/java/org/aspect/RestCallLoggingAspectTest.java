//package org.aspect;
//
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.Signature;
//import org.gym.aspect.RestCallLoggingAspect;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.slf4j.Logger;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//
//import javax.servlet.http.HttpServletRequest;
//
//import static org.mockito.Mockito.*;
//
//class RestCallLoggingAspectTest {
//
//    @Mock
//    private Logger loggerMock;
//
//    @Mock
//    private JoinPoint joinPointMock;
//
//    @Mock
//    private Signature signatureMock;
//
//    @Mock
//    private HttpServletRequest requestMock;
//
//    @InjectMocks
//    private RestCallLoggingAspect restCallLoggingAspect;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);  // Initialize annotated fields
//        reset(loggerMock, joinPointMock, signatureMock, requestMock);
//    }
//
//    @Test
//    void logRestCallDetails() {
//        // Mock HttpServletRequest and JoinPoint
//        when(requestMock.getRequestURI()).thenReturn("/example");
//        when(requestMock.getMethod()).thenReturn("GET");
//
//        when(signatureMock.getName()).thenReturn("testMethod");
//        when(joinPointMock.getSignature()).thenReturn(signatureMock);
//        when(joinPointMock.getTarget()).thenReturn(new Object()); // Mocking the target object
//
//        // Mock RequestContextHolder to return the mocked HttpServletRequest
//        ServletRequestAttributes servletRequestAttributesMock = mock(ServletRequestAttributes.class);
//        RequestContextHolder.setRequestAttributes(servletRequestAttributesMock);
//        when(servletRequestAttributesMock.getRequest()).thenReturn(requestMock);
//
//        // Call the method to be tested
//        restCallLoggingAspect.logRestCallDetails(joinPointMock);
//
//        // Verify the log statements
//        verify(loggerMock).info("REST Call: {} [{}]", "/example", "GET");
//        verify(loggerMock).info("Class: {}, Method: {}", "Object", "testMethod");
//        verify(loggerMock).info("Request Parameters: ");
//
//        // No need for additional verification as the aspect method doesn't return anything
//    }
//
//    // Additional tests can be added based on the requirements
//}