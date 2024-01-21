package org.gym.memory;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@Component
@Scope("singleton")
public class InMemoryStorage {

    private static final Logger LOGGER = LoggerFactory.getLogger(InMemoryStorage.class);
    ObjectMapper objectMapper = new ObjectMapper();

    @Getter
    @Setter
    private Map<String, Map<Long, Object>> storageMap = new HashMap<>();
    public InMemoryStorage(){
    }
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

    public void writeToJsonFile(String filePath) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(new File(filePath), storageMap);
            System.out.println("Data has been written to " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void readFromJsonFile(String filePath) {
        try {
            TypeReference<Map<String, Map<Long, Object>>> typeRef = new TypeReference<Map<String, Map<Long, Object>>>() {};
            Map<String, Map<Long, Object>> userMap = objectMapper.readValue(new File(filePath), typeRef);

            // Convert userMap to the type Map<String, Map<Long, Object>>
            storageMap = new HashMap<>(userMap);

            System.out.println("Данные успешно загружены из файла " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}