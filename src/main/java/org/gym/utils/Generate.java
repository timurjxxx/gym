package org.gym.utils;

import org.gym.memory.InMemoryStorage;
import org.gym.model.Identifiable;
import org.gym.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Stream;

@Component
public class Generate {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);


    private final InMemoryStorage storage;

    @Autowired
    public Generate(InMemoryStorage storage) {
        this.storage = storage;
    }

    public String generatePassword() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder(10);
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            int index = random.nextInt(chars.length());
            sb.append(chars.charAt(index));
        }
        LOGGER.info("Generated unique password");
        LOGGER.debug("Generated password: {}", sb.toString());


        return sb.toString();
    }

    public Long generateUniqueId(String name) {
        return Stream.iterate(1L, id -> id + 1)
                .filter(id -> isUniqueId(name, id))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Unable to generate unique ID."));
    }

    private boolean isUniqueId(String name, Long id) {
        List<Identifiable> objects = storage.getStorageMap().getOrDefault(name, Collections.emptyList());
        LOGGER.debug("Checking uniqueness for ID {} in collection {}", id, name);

        return objects.stream().noneMatch(obj -> obj instanceof Identifiable && Objects.equals(((Identifiable) obj).getId(), id));
    }


}
