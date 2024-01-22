package org.storageTest;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.gym.memory.InMemoryStorage;
import org.gym.model.Identifiable;
import org.gym.model.Trainer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.slf4j.Logger;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class InMemoryStorageTest {


    private InMemoryStorage inMemoryStorage;

    @BeforeEach
    public void setUp() {
        inMemoryStorage = new InMemoryStorage();
    }

    @Test
    public void saveAndGet() {
        Trainer trainer = new Trainer();
        trainer.setId(1L);
        trainer.setSpecialization("Fitness");

        inMemoryStorage.save("Trainer", trainer);

        Identifiable retrievedTrainer = (Identifiable) inMemoryStorage.get("Trainer", 1L);

        assertNotNull(retrievedTrainer);
        assertEquals(trainer.getId(), retrievedTrainer.getId());
        assertEquals(trainer.getSpecialization(), ((Trainer) retrievedTrainer).getSpecialization());
    }

    @Test
    public void update() {
        Trainer trainer = new Trainer();
        trainer.setId(1L);
        trainer.setSpecialization("Fitness");

        inMemoryStorage.save("Trainer", trainer);

        Trainer updatedTrainer = new Trainer();
        updatedTrainer.setId(1L);
        updatedTrainer.setSpecialization("Yoga");

        inMemoryStorage.update("Trainer", 1L, updatedTrainer);

        Identifiable retrievedTrainer = (Identifiable) inMemoryStorage.get("Trainer", 1L);

        assertNotNull(retrievedTrainer);
        assertEquals(updatedTrainer.getId(), retrievedTrainer.getId());
        assertEquals(updatedTrainer.getSpecialization(), ((Trainer) retrievedTrainer).getSpecialization());
    }

    @Test
    public void delete() {
        Trainer trainer = new Trainer();
        trainer.setId(1L);
        trainer.setSpecialization("Fitness");

        inMemoryStorage.save("Trainer", trainer);

        inMemoryStorage.deleteById("Trainer", 1L);

        Identifiable retrievedTrainer = (Identifiable) inMemoryStorage.get("Trainer", 1L);

        assertNull(retrievedTrainer);
    }

    @Test
    public void getAll() {
        Trainer trainer1 = new Trainer();
        trainer1.setId(1L);
        trainer1.setSpecialization("Fitness");

        Trainer trainer2 = new Trainer();
        trainer2.setId(2L);
        trainer2.setSpecialization("Yoga");

        inMemoryStorage.save("Trainer", trainer1);
        inMemoryStorage.save("Trainer", trainer2);

        List<Identifiable> trainers = inMemoryStorage.getAll("Trainer");

        assertNotNull(trainers);
        assertEquals(2, trainers.size());
    }

    @Test
    public void writeAndReadFromJsonFile() {
        Trainer trainer = new Trainer();
        trainer.setId(1L);
        trainer.setSpecialization("Fitness");

        inMemoryStorage.save("Trainer", trainer);

        inMemoryStorage.writeToJsonFile("test.json");

        InMemoryStorage newStorage = new InMemoryStorage();
        newStorage.readFromJsonFile("test.json");

        Identifiable retrievedTrainer = (Identifiable) newStorage.get("Trainer", 1L);

        assertNotNull(retrievedTrainer);
        assertEquals(trainer.getId(), retrievedTrainer.getId());
        assertEquals(trainer.getSpecialization(), ((Trainer) retrievedTrainer).getSpecialization());
    }

    @Test
    public void isUniqueId() {
        Trainer trainer = new Trainer();
        trainer.setId(1L);
        trainer.setSpecialization("Fitness");

        inMemoryStorage.save("Trainer", trainer);

        boolean isUniqueId = inMemoryStorage.isUniqueId("Trainer", 2L);
        assertTrue(isUniqueId);

        isUniqueId = inMemoryStorage.isUniqueId("Trainer", 1L);
        assertFalse(isUniqueId);
    }

    @Test
    public void deleteNonExistingObject() {
        inMemoryStorage.deleteById("Trainer", 123L); // Non-existing ID, nothing should happen
        List<Identifiable> trainers = inMemoryStorage.getAll("Trainer");
        assertNotNull(trainers);
        assertTrue(trainers.isEmpty());
    }

    @Test
    public void updateNonExistingObject() {
        Trainer updatedTrainer = new Trainer();
        updatedTrainer.setId(123L);
        updatedTrainer.setSpecialization("UpdatedSpecialization");

        Identifiable result = (Identifiable) inMemoryStorage.update("Trainer", 123L, updatedTrainer);
        assertNull(result);
    }
    @Test
    public void readFromJsonFile() {
        Trainer trainer1 = new Trainer();
        trainer1.setId(1L);
        trainer1.setSpecialization("Fitness");

        Trainer trainer2 = new Trainer();
        trainer2.setId(2L);
        trainer2.setSpecialization("Yoga");

        inMemoryStorage.save("Trainer", trainer1);
        inMemoryStorage.save("Trainer", trainer2);

        inMemoryStorage.writeToJsonFile("test.json");

        InMemoryStorage newStorage = new InMemoryStorage();

        newStorage.readFromJsonFile("test.json");

        List<Identifiable> trainers = newStorage.getAll("Trainer");

        assertNotNull(trainers);
        assertEquals(2, trainers.size());

        Identifiable retrievedTrainer1 = (Identifiable) newStorage.get("Trainer", 1L);
        assertNotNull(retrievedTrainer1);
        assertEquals(trainer1.getId(), retrievedTrainer1.getId());
        assertEquals(trainer1.getSpecialization(), ((Trainer) retrievedTrainer1).getSpecialization());

        Identifiable retrievedTrainer2 = (Identifiable) newStorage.get("Trainer", 2L);
        assertNotNull(retrievedTrainer2);
        assertEquals(trainer2.getId(), retrievedTrainer2.getId());
        assertEquals(trainer2.getSpecialization(), ((Trainer) retrievedTrainer2).getSpecialization());
    }

}