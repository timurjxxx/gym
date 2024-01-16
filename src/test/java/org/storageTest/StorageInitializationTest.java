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

import static org.mockito.Mockito.*;

class StorageInitializationTest {

    @Mock
    private ResourceLoader resourceLoader;

    @Mock
    private InMemoryStorage inMemoryStorage;

    @Mock
    private Resource resource;

    @InjectMocks
    private StorageInitialization storageInitialization;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testInitializeStorageWithData() throws IOException {
        String filePath = "dummyFilePath";
        File dummyFile = mock(File.class);

        when(resourceLoader.getResource("classpath:application.properties")).thenReturn(resource);
        when(resource.getFile()).thenReturn(dummyFile);
        when(dummyFile.getPath()).thenReturn(filePath);

        storageInitialization.initializeStorageWithData();

        verify(resourceLoader).getResource("classpath:application.properties");
        verify(inMemoryStorage).initializeWithDataFromFile(filePath);
    }
}