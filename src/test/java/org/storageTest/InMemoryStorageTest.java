package org.storageTest;

import org.gym.memory.InMemoryStorage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.slf4j.Logger;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class InMemoryStorageTest {

    @Spy
    private InMemoryStorage inMemoryStorage;

    @Mock
    private ResourceLoader resourceLoader;

    @Mock
    private Resource resource;

    @Mock
    private Logger logger;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveAndGetEntity() {
        String namespace = "testNamespace";
        Long key = 1L;
        Object entity = "testEntity";

        Object result = inMemoryStorage.save(namespace, key, entity);
        assertNull(result);

        Object retrievedEntity = inMemoryStorage.get(namespace, key);
        assertEquals(entity, retrievedEntity);
    }

    @Test
    void getAllEntities() {
        String namespace = "testNamespace";
        Long key1 = 1L;
        Object entity1 = "testEntity1";
        Long key2 = 2L;
        Object entity2 = "testEntity2";

        inMemoryStorage.save(namespace, key1, entity1);
        inMemoryStorage.save(namespace, key2, entity2);

        Map<Long, Object> allEntities = inMemoryStorage.getAll(namespace);
        assertEquals(2, allEntities.size());
        assertEquals(entity1, allEntities.get(key1));
        assertEquals(entity2, allEntities.get(key2));
    }

    @Test
    void deleteEntity() {
        String namespace = "testNamespace";
        Long key = 1L;
        Object entity = "testEntity";

        inMemoryStorage.save(namespace, key, entity);
        inMemoryStorage.delete(namespace, key);

        assertNull(inMemoryStorage.get(namespace, key));
    }

    @Test
    void initializeWithDataFromFile() throws IOException {
        String fileContent = "namespace1,1,value1\nnamespace2,2,value2";
        BufferedReader bufferedReader = new BufferedReader(new StringReader(fileContent));

        when(resourceLoader.getResource(anyString())).thenReturn(resource);
        when(resource.getFile()).thenReturn(null);
        when(inMemoryStorage.parseValue(anyString())).thenCallRealMethod();

        inMemoryStorage.initializeWithDataFromFile("D://test.txt");

        verify(inMemoryStorage, times(1)).save(eq("namespace1"), eq(1L), eq("value1"));
        verify(inMemoryStorage, times(1)).save(eq("namespace2"), eq(2L), eq("value2"));
    }

    @Test
    void parseValue() {
        String value = "testValue";
        assertEquals(value, inMemoryStorage.parseValue(value));
    }
}