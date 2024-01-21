package org.gym.dao;

import org.gym.memory.InMemoryStorage;
import org.gym.model.Trainee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Component
public class TraineeDAO {

    private final InMemoryStorage storage;

    private static final Logger LOGGER = LoggerFactory.getLogger(TraineeDAO.class);


    public TraineeDAO(InMemoryStorage storage) {
        this.storage = storage;
    }


    public Trainee get(String nameSpace, Long traineeId) {
        return (Trainee) storage.get(nameSpace, traineeId);
    }

    public Trainee save(String nameSpace,Trainee newTrainee) {

        return (Trainee) storage.save(nameSpace, newTrainee);
    }

    public void delete(String nameSpace,Long id) {
        storage.deleteById(nameSpace, id);
    }

    public Trainee update(String nameSpace,Long id, Trainee newTrainee) {
        return (Trainee) storage.update(nameSpace, id, newTrainee);
    }


}