package org.gym.config;

import org.gym.memory.InMemoryStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;

@Component
public class ApplicationShutdownListener implements ApplicationListener<ContextClosedEvent> {
    private static final Logger LOGGER = LoggerFactory.getLogger(InMemoryStorage.class);
    private final String filePath;
    private final InMemoryStorage inMemoryStorage;

    @Autowired
    public ApplicationShutdownListener(String filePath, InMemoryStorage inMemoryStorage) {
        this.filePath = filePath;
        this.inMemoryStorage = inMemoryStorage;
    }


    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        LOGGER.info("Save in file after shutdown");

        inMemoryStorage.writeToJsonFile(filePath);

    }
}
