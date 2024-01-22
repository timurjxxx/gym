package org.config;
import org.gym.config.ApplicationShutdownListener;
import org.gym.memory.InMemoryStorage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.event.ContextClosedEvent;

import static org.mockito.Mockito.*;
public class ApplicationShutdownListenerTest {
    @Mock
    private InMemoryStorage inMemoryStorage;

    @InjectMocks
    private ApplicationShutdownListener shutdownListener;

    private final String filePath = "/path/to/file.json";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        shutdownListener = new ApplicationShutdownListener(filePath, inMemoryStorage);
    }

    @Test
    void testOnApplicationEvent() {
        ContextClosedEvent event = mock(ContextClosedEvent.class);
        shutdownListener.onApplicationEvent(event);
        verify(inMemoryStorage, times(1)).writeToJsonFile(filePath);
    }

    @Test
    void testInitializeStorageWithData() {
        shutdownListener.initializeStorageWithData();
        verify(inMemoryStorage, times(1)).readFromJsonFile(filePath);
    }
}
