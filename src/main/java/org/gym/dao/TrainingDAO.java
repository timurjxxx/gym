package org.gym.dao;

import org.gym.memory.InMemoryStorage;
import org.gym.model.Training;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class TrainingDAO {


    private final InMemoryStorage storage;


    @Autowired
    public TrainingDAO(InMemoryStorage storage) {
        this.storage = storage;
    }

    public Training save(String nameSpace, Training training) {
        return (Training) storage.save( nameSpace,  training);
    }

    public Training get(String nameSpace,Long id) {

        return (Training) storage.get(nameSpace, id);
    }


}