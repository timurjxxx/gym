package org.gym.memory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;
import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

@Component
public class StorageInitialization {
    private ResourceLoader resourceLoader;
    private final InMemoryStorage inMemoryStorage;
    private static final Logger LOGGER = LoggerFactory.getLogger(StorageInitialization.class);

    public StorageInitialization(ResourceLoader resourceLoader, InMemoryStorage inMemoryStorage) {
        this.resourceLoader = resourceLoader;
        this.inMemoryStorage = inMemoryStorage;
    }


    @PostConstruct
    public void initializeStorageWithData() {
        LOGGER.info("Initialize bean after creating with file ");
        Resource resource = resourceLoader.getResource("classpath:application.properties");
        try {
            String filePath = resource.getFile().getPath();
            inMemoryStorage.initializeWithDataFromFile(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}