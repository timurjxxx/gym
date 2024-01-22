package org.gym.utils;

import org.gym.memory.InMemoryStorage;
import org.gym.model.Trainee;
import org.gym.model.Trainer;
import org.gym.model.Training;
import org.gym.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;


@Component
public class GenerateId {


    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);
    private final InMemoryStorage storage;

    @Autowired
    public GenerateId(InMemoryStorage storage) {
        this.storage = storage;
    }


    public Long generateUniqueId(String name) {
        return Stream.iterate(1L, id -> id + 1)
                .filter(id -> isUniqueId(name, id))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Unable to generate unique ID."));
    }

    private boolean isUniqueId(String namespace, Long id) {
        List<Object> objects = storage.getStorageMap().getOrDefault(namespace, Collections.emptyList());
        LOGGER.debug("Checking uniqueness for ID {} in collection under namespace {}", id, namespace);

        return objects.stream()
                .filter(obj -> obj != null)
                .noneMatch(obj -> {
                    if (obj instanceof Trainer && "Trainer".equals(namespace)) {
                        return Objects.equals(((Trainer) obj).getId(), id);
                    } else if (obj instanceof Trainee && "Trainee".equals(namespace)) {
                        return Objects.equals(((Trainee) obj).getId(), id);
                    } else if (obj instanceof Training && "Training".equals(namespace)) {
                        return Objects.equals(((Training) obj).getId(), id);
                    }
                    return false;
                });
    }

}
