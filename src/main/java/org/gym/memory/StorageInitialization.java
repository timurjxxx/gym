package org.gym.memory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;
import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

@Component
public class StorageInitialization {

    private final InMemoryStorage inMemoryStorage;
    private final String filePath;

    private static final Logger LOGGER = LoggerFactory.getLogger(StorageInitialization.class);

    @Autowired
    public StorageInitialization(InMemoryStorage inMemoryStorage, String filePath) {
        this.inMemoryStorage = inMemoryStorage;
        this.filePath = filePath;
    }

}
