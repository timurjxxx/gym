package org.gym.memory;

import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@Component
@Scope("singleton")
public class InMemoryStorage {

    private static final Logger LOGGER = LoggerFactory.getLogger(InMemoryStorage.class);

    @Getter
    @Setter
    private Map<String, Map<Long, Object>> storageMap = new HashMap<>();

    public Object save(String namespace, Long key, Object entity) {
        LOGGER.info("Saving entity by namespace into memory");
        LOGGER.debug("Namespace: {}, Key: {}, Entity: {}", namespace, key, entity);

        return storageMap.computeIfAbsent(namespace, k -> new HashMap<>()).put(key, entity);
    }

    public Object get(String namespace, Long key) {
        LOGGER.info("Finding entity by ID");
        LOGGER.debug("Namespace: {}, Key: {}", namespace, key);

        return storageMap.getOrDefault(namespace, new HashMap<>()).get(key);
    }

    public Map<Long, Object> getAll(String namespace) {
        LOGGER.info("Getting all entities by namespace");
        LOGGER.debug("Namespace: {}", namespace);

        return storageMap.getOrDefault(namespace, new HashMap<>());
    }

    public void delete(String namespace, Long key) {
        LOGGER.info("Deleting entity by ID");
        LOGGER.debug("Namespace: {}, Key: {}", namespace, key);

        if (storageMap.containsKey(namespace)) {
            storageMap.get(namespace).remove(key);
        }
    }

    public void initializeWithDataFromFile(String filePath) {
        LOGGER.info("Parsing data from file and saving into memory");
        LOGGER.debug("File Path: {}", filePath);

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String namespace = parts[0];
                    Long key = Long.valueOf(parts[1]);
                    Object value = parseValue(parts[2]);
                    save(namespace, key, value);
                }
            }
        } catch (IOException e) {
            LOGGER.error("Error while parsing data from file", e);
        }
    }

    public Object parseValue(String value) {
        LOGGER.debug("Parsing value: {}", value);
        return value;
    }
}
