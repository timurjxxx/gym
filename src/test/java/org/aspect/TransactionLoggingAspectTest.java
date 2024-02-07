package org.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.gym.aspect.TransactionLoggingAspect;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ContextConfiguration
@ComponentScan(basePackages = "org.gym.service") // Specify the package of your service classes
public class TransactionLoggingAspectTest {

    @InjectMocks
    private TransactionLoggingAspect transactionLoggingAspect;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testLogTransaction() {
        Object dummyResult = new Object();
        JoinPoint joinPoint = createJoinPointMock(dummyResult);

        transactionLoggingAspect.logTransaction(joinPoint, dummyResult);

    }

    private JoinPoint createJoinPointMock(Object result) {
        JoinPoint joinPoint = mock(JoinPoint.class);
        Signature signature = mock(Signature.class);

        when(joinPoint.getSignature()).thenReturn(signature);
        when(signature.toShortString()).thenReturn("mockedMethod");
        when(joinPoint.getTarget()).thenReturn(new Object());
        when(joinPoint.getArgs()).thenReturn(new Object[]{});
        return joinPoint;
    }
}
