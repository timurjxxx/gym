package org.storageTest;

import org.gym.memory.InMemoryStorage;
import org.gym.memory.StorageInitialization;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import static org.mockito.Mockito.*;

class StorageInitializationTest {

    @InjectMocks
    private StorageInitialization storageInitialization;

    @Mock
    private ResourceLoader resourceLoader;

    @Mock
    private InMemoryStorage inMemoryStorage;

    @Mock
    private Resource resource;

    @Mock
    private InputStream inputStream;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testInitializeStorageWithData() throws IOException {
        // Arrange
        String filePath = "test_data.txt";

        when(resourceLoader.getResource("classpath:application.properties")).thenReturn(resource);
        when(resource.getFile()).thenReturn(new File(filePath));  // Use File to represent an actual file
        when(resource.getInputStream()).thenReturn(inputStream);

        // Act
        storageInitialization.initializeStorageWithData();

        // Assert
        verify(inMemoryStorage).initializeWithDataFromFile(filePath);
    }

    @Test
    public void testInitializeStorageWithDataIOException() throws IOException {
        when(resourceLoader.getResource("classpath:application.properties")).thenReturn(resource);
        when(resource.getFile()).thenThrow(new IOException("Mocked IOException"));

        storageInitialization.initializeStorageWithData();

    }
}