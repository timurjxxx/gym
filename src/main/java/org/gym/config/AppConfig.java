package org.gym.config;

import org.gym.memory.InMemoryStorage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;

@Configuration
@ComponentScan
@PropertySource("classpath:application.properties")

public class AppConfig {

    @Value("${file.path}")
    private String filePath;

    @Bean
    public String getFilePath() {
        return filePath;
    }

    @Value("${password.length}")
    private int passwordLength;

    @Bean
    public int getPasswordLength() {
        return passwordLength;
    }


}