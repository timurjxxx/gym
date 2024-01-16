package org.gym.config;

import org.gym.memory.InMemoryStorage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;

@Configuration
@ComponentScan
public class AppConfig {

    @Bean
    public ResourceLoader resourceLoader() {
        return new DefaultResourceLoader();
    }
    @Bean
    public InMemoryStorage inMemoryStorage(){
        return new InMemoryStorage();
    }


}