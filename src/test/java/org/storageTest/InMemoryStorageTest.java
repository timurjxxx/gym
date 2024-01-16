package org.storageTest;


import org.gym.memory.InMemoryStorage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class InMemoryStorageTest {

    @InjectMocks
    private InMemoryStorage inMemoryStorage;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveAndGet() {
        String namespace = "TestNamespace";
        Long key = 1L;
        Object entity = new Object();

        inMemoryStorage.save(namespace, key, entity);
        Object actualEntity = inMemoryStorage.get(namespace, key);

        assertEquals(entity, actualEntity);
    }

    @Test
    public void testGetAll() {
        String namespace = "TestNamespace";
        Long key1 = 1L;
        Long key2 = 2L;
        Object entity1 = new Object();
        Object entity2 = new Object();

        inMemoryStorage.save(namespace, key1, entity1);
        inMemoryStorage.save(namespace, key2, entity2);
        Map<Long, Object> allEntities = inMemoryStorage.getAll(namespace);

        assertEquals(2, allEntities.size());
        assertTrue(allEntities.containsKey(key1));
        assertTrue(allEntities.containsKey(key2));
    }

    @Test
    public void testDelete() {
        String namespace = "TestNamespace";
        Long key = 1L;
        Object entity = new Object();

        inMemoryStorage.save(namespace, key, entity);
        inMemoryStorage.delete(namespace, key);
        Object obj = inMemoryStorage.get(namespace, key);
        assertNull(obj);
    }


    @Test
    public void testInitializeWithDataFromFile() {
        String filePath = "test_data.txt";

        inMemoryStorage.initializeWithDataFromFile(filePath);
        Map<Long, Object> allEntities = inMemoryStorage.getAll("TestDataNamespace");

        assertNotNull(allEntities);
    }
}