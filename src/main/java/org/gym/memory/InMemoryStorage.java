package org.gym.memory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.MapType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import lombok.Getter;
import lombok.Setter;
import org.gym.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Component
@Scope("singleton")
public class InMemoryStorage {

    private final ObjectMapper objectMapper;
    @Getter
    @Setter
    public final Map<String, List<Object>> storageMap = new HashMap<>();
    private static final Logger LOGGER = LoggerFactory.getLogger(InMemoryStorage.class);

    @Autowired
    public InMemoryStorage(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public Object save(String name, Object entity) {
        storageMap.computeIfAbsent(name, k -> new ArrayList<>()).add(entity);
        LOGGER.info("Saved object to collection {} - {}", name, entity);
        return entity;
    }

    public Object deleteById(String name, Long id) {
        LOGGER.debug("Deleting object with ID {} from collection {}", id, name);

        storageMap.computeIfPresent(name, (key, objects) -> {
            boolean removed = objects.removeIf(obj -> {
                if (obj instanceof Training) {
                    return Objects.equals(((Training) obj).getId(), id);
                } else if (obj instanceof Trainer) {
                    return Objects.equals(((Trainer) obj).getId(), id);
                } else if (obj instanceof Trainee) {
                    return Objects.equals(((Trainee) obj).getId(), id);
                }
                return false;
            });

            if (removed) {
                LOGGER.info("Object with ID {} successfully deleted from collection {}", id, name);
            } else {
                LOGGER.warn("Object with ID {} not found in collection {}", id, name);
            }

            return objects;
        });

        if (!storageMap.containsKey(name)) {
            LOGGER.warn("Collection {} does not exist", name);
        }

        return null;
    }

    public List<Object> findAll(String name) {
        LOGGER.debug("Retrieving all objects from collection {}", name);

        return storageMap.getOrDefault(name, Collections.emptyList())
                .stream()
                .filter(obj -> obj instanceof Training || obj instanceof Trainer || obj instanceof Trainee)
                .collect(Collectors.toList());
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
            MapType mapType = typeFactory.constructMapType(
                    HashMap.class,
                    typeFactory.constructType(String.class),
                    typeFactory.constructCollectionType(List.class, LinkedHashMap.class)
            );
            Map<String, List<LinkedHashMap>> userMap = objectMapper.readValue(new File(filePath), mapType);
            storageMap.clear();

            userMap.forEach((namespace, linkedHashMapList) -> {
                List<Object> objectList = storageMap.computeIfAbsent(namespace, k -> new ArrayList<>());

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


    private Class<?> getClassForNamespace(String namespace) {
        switch (namespace) {
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


    public Object findById(String name, Long id) {
        LOGGER.debug("Searching for object with ID {} in collection {}", id, name);

        return storageMap.getOrDefault(name, Collections.emptyList())
                .stream()
                .filter(obj -> obj instanceof Training || obj instanceof Trainer || obj instanceof Trainee)
                .filter(obj -> {
                    if (obj instanceof Training) {
                        return Objects.equals(((Training) obj).getId(), id);
                    } else if (obj instanceof Trainer) {
                        return Objects.equals(((Trainer) obj).getId(), id);
                    } else if (obj instanceof Trainee) {
                        return Objects.equals(((Trainee) obj).getId(), id);
                    }
                    return false;
                })
                .findFirst()
                .orElse(null);
    }

    public void update(String name, Long id, Object updatedEntity) {
        LOGGER.debug("Обновление объекта с ID {} в коллекции {}", id, name);

        storageMap.computeIfPresent(name, (key, entities) -> {
            for (int i = 0; i < entities.size(); i++) {
                Object obj = entities.get(i);
                if (obj instanceof Trainer && Objects.equals(((Trainer) obj).getId(), id)) {
                    entities.set(i, updatedEntity);
                } else if (obj instanceof Trainee && Objects.equals(((Trainee) obj).getId(), id)) {
                    entities.set(i, updatedEntity);
                    break;
                }
            }
            return entities;
        });
    }


}
