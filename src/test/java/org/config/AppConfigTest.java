package org.config;


import org.gym.config.AppConfig;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class AppConfigTest {

    @Test
    public void testFilePathBean() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        AppConfig appConfig = context.getBean(AppConfig.class);

        // Assuming your_expected_file_path is present in your application.properties
        assertEquals("D://test.txt", appConfig.getFilePath());

        context.close();
    }

    @Test
    public void testPasswordLengthBean() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        AppConfig appConfig = context.getBean(AppConfig.class);

        // Assuming password.length is present in your application.properties
        assertEquals(10, appConfig.getPasswordLength()); // Adjust 10 to your expected password length

        context.close();
    }
}