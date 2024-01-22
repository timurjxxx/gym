package org.gym.dao;

import org.gym.memory.InMemoryStorage;
import org.gym.model.Trainer;
import org.springframework.stereotype.Component;


@Component
public class TrainerDAO {

    private final InMemoryStorage storage;

    public TrainerDAO(InMemoryStorage storage) {
        this.storage = storage;
    }

    public Trainer save(String nameSpace, Trainer newTrainer) {
        return (Trainer) storage.save(nameSpace, newTrainer);
    }


    public Trainer get(String nameSpace,Long trainerId) {
        return (Trainer) storage.get(nameSpace, trainerId);
    }

    public void delete(String nameSpace,Long id) {
        storage.deleteById(nameSpace, id);
    }

    public Trainer update(String nameSpace,Long id, Trainer updatedTrainer) {
     return (Trainer) storage.update(nameSpace, id, updatedTrainer);
    }

}