package org.gym.dao;

import org.gym.memory.InMemoryStorage;
import org.gym.model.Trainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class TrainerDAO {
    private static final Logger LOGGER = LoggerFactory.getLogger(TrainerDAO.class);

    private final String nameSpace = "Trainer";
    private final InMemoryStorage storage;

    public TrainerDAO(InMemoryStorage storage) {
        this.storage = storage;
    }

    public Trainer save(Trainer newTrainer) {
        return (Trainer) storage.save(nameSpace, newTrainer.getId(), newTrainer);
    }


    public Trainer get(Long trainerId) {
        return (Trainer) storage.get(nameSpace, trainerId);
    }

    public Map<Long, Object> getAll() {
        return storage.getAll(nameSpace);

    }






}