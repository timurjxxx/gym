package org.applicationStartTest;

import org.gym.GymApplication;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
@ContextConfiguration
@ExtendWith(OutputCaptureExtension.class)
class GymApplicationTests {

    @Test
    void contextLoads() {
        // Basic test to ensure that the application context loads successfully
    }

    @Test
    void mainTest(CapturedOutput output) {
        GymApplication.main(new String[]{});

        // Assert that the output contains some information about the application start
        assertTrue(output.getAll().contains("Started GymApplication in"));
    }
}
