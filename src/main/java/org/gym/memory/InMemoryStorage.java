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

        LOGGER.info("Save entity by namespace into memory");
        return storageMap.computeIfAbsent(namespace, k -> new HashMap<>()).put(key, entity);
    }

    public Object get(String namespace, Long key) {
        LOGGER.info("Find entity by id ");

        return storageMap.getOrDefault(namespace, new HashMap<>()).get(key);
    }

    public Map<Long, Object> getAll(String namespace) {

        return storageMap.getOrDefault(namespace, new HashMap<>());
    }

    public void delete(String namespace, Long key) {
        LOGGER.info("Delete entity by id ");

        if (storageMap.containsKey(namespace)) {
            storageMap.get(namespace).remove(key);
        }
    }




    public void initializeWithDataFromFile(String filePath) {
        LOGGER.info("Parsing data from file and save into memory ");

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
            e.printStackTrace();

        }
    }

    public Object parseValue(String namespace) {
        return namespace;
    }
}