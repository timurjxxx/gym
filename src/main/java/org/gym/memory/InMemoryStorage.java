package org.gym.memory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.MapType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import lombok.Getter;
import org.gym.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;


@Component
@Scope("singleton")
public class InMemoryStorage {

    ObjectMapper objectMapper = new ObjectMapper();
    @Getter
    public final Map<String, List<Identifiable>> storageMap = new HashMap<>();
    private static final Logger LOGGER = LoggerFactory.getLogger(InMemoryStorage.class);


    public Object save(String name, Identifiable entity) {
        storageMap.computeIfAbsent(name, k -> new ArrayList<>()).add(entity);
        LOGGER.info("Saved object to collection {} - {}", name, entity);

        return entity;
    }

    public Object get(String name, Long id) {
        LOGGER.debug("Retrieved object with ID {} from collection {}", id, name);

        return storageMap.getOrDefault(name, Collections.emptyList())
                .stream()
                .filter(obj -> obj instanceof Identifiable
                        && Objects.equals(((Identifiable) obj).getId(), id))
                .findFirst()
                .orElse(null);
    }


    public void deleteById(String name, Long id) {
        storageMap.computeIfPresent(name, (key, objects) -> {
            boolean removed = objects.removeIf(obj -> obj instanceof Identifiable
                    && Objects.equals(((Identifiable) obj).getId(), id));

            if (removed) {
                LOGGER.info("Object with id {} successfully deleted from collection {}", id, name);

            } else {
                LOGGER.warn("Object with id {} not found in collection {}", id, name);

            }

            return objects;
        });

        if (!storageMap.containsKey(name)) {
            LOGGER.warn("Collection {} does not exist", name);
        }
    }

    public List<Identifiable> getAll(String namespace) {
        LOGGER.debug("Retrieved all objects from collection {}", namespace);

        return storageMap.getOrDefault(namespace, Collections.emptyList())
                .stream()
                .filter(obj -> obj instanceof Identifiable)
                .collect(Collectors.toList());
    }


    public Object update(String name, Long id, Identifiable updatedObj) {
        if (id == null) {
            throw new IllegalArgumentException("Object ID cannot be null for update.");
        }

        Identifiable existingObj = (Identifiable) get(name, id);
        if (existingObj != null) {
            List<Identifiable> objects = storageMap.get(name);
            objects.set(objects.indexOf(existingObj), updatedObj);
            LOGGER.info("Object with id {} successfully updated in collection {}", id, name);
            return updatedObj;
        } else {
            LOGGER.warn("Object with id {} not found in collection {}", id, name);
            return null;
        }
    }

    public void writeToJsonFile(String filePath) {
        try {
            objectMapper.writeValue(new File(filePath), storageMap);
            LOGGER.info("Data successfully written to file {}", filePath);
        } catch (IOException e) {
            LOGGER.error("Error writing data to file {}", filePath, e);

            e.printStackTrace();
        }
    }


    public void readFromJsonFile(String filePath) {
        try {
            TypeFactory typeFactory = objectMapper.getTypeFactory();
            MapType mapType = typeFactory.constructMapType(HashMap.class, typeFactory.constructType(String.class),
                    typeFactory.constructCollectionType(List.class, LinkedHashMap.class));
            Map<String, List<LinkedHashMap>> userMap = objectMapper.readValue(new File(filePath), mapType);
            storageMap.clear();

            userMap.forEach((namespace, linkedHashMapList) -> {
                List<Identifiable> objectList = storageMap.computeIfAbsent(namespace, k -> new ArrayList<>());

                linkedHashMapList.stream()
                        .map(linkedHashMap -> objectMapper.convertValue(linkedHashMap, getClassForNamespace(namespace)))
                        .filter(Objects::nonNull)
                        .forEach(objectList::add);
            });

            LOGGER.info("Data successfully loaded from file {}", filePath);

        } catch (IOException e) {
            LOGGER.error("Error loading data from file {}", filePath, e);

            e.printStackTrace();
        }
    }


    private Class<? extends Identifiable> getClassForNamespace(String namespace) {
        switch (namespace) {
            case "User":
                return User.class;
            case "Training":
                return Training.class;
            case "Trainer":
                return Trainer.class;
            case "Trainee":
                return Trainee.class;
            default:
                return null;
        }
    }


    public boolean isUniqueId(String name, Long id) {
        LOGGER.debug("Checking uniqueness for ID {} in collection {}", id, name);

        return storageMap.getOrDefault(name, Collections.emptyList())
                .stream()
                .noneMatch(obj -> obj instanceof Identifiable && Objects.equals(((Identifiable) obj).getId(), id));

    }

}