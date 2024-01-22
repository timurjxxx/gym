package org.util;
import org.gym.memory.InMemoryStorage;
import org.gym.model.Identifiable;
import org.gym.utils.Generate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GenerateTest {


    @Mock
    private InMemoryStorage storage;

    @InjectMocks
    private Generate generate;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGeneratePassword() {
        String generatedPassword = generate.generatePassword();

        assertNotNull(generatedPassword);
        assertEquals(10, generatedPassword.length());
    }

    @Test
    void testGenerateUniqueId() {
        String name = "test";
        Identifiable existingObject1 = mock(Identifiable.class);
        Identifiable existingObject2 = mock(Identifiable.class);
        when(existingObject1.getId()).thenReturn(1L);
        when(existingObject2.getId()).thenReturn(2L);

        List<Identifiable> existingObjects = Stream.of(existingObject1, existingObject2).collect(Collectors.toList());

        when(storage.getStorageMap()).thenReturn(Collections.singletonMap(name, existingObjects));

        Long generatedId = generate.generateUniqueId(name);

        assertEquals(3L, generatedId);
    }
    @Test
    void testGenerateUniqueIdNoAvailableId() {
        String name = "test";
        Identifiable existingObject1 = mock(Identifiable.class);
        Identifiable existingObject2 = mock(Identifiable.class);
        when(existingObject1.getId()).thenReturn(1L);
        when(existingObject2.getId()).thenReturn(2L);

        List<Identifiable> existingObjects = Stream.of(existingObject1, existingObject2).collect(Collectors.toList());

        when(storage.getStorageMap()).thenReturn(Collections.singletonMap(name, existingObjects));

        try {
            generate.generateUniqueId(name);
        } catch (IllegalStateException e) {
        }

    }

}
