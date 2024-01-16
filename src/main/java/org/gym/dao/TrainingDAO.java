package org.gym.dao;

import org.gym.memory.InMemoryStorage;
import org.gym.model.Training;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class TrainingDAO {


    private final InMemoryStorage storage;
    private final String nameSpace = "Training";


    @Autowired
    public TrainingDAO(InMemoryStorage storage) {
        this.storage = storage;
    }

    public Training save(Training training) {
        return (Training) storage.save(nameSpace, training.getId(), training);
    }


    public Training get(Long id) {

        return (Training) storage.get(nameSpace, id);
    }


    public Map<Long, Object> getAll() {
        return storage.getAll(nameSpace);

    }

}