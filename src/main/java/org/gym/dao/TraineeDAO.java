package org.gym.dao;

import org.gym.memory.InMemoryStorage;
import org.gym.model.Trainee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class TraineeDAO {

    private final InMemoryStorage storage;
    private final String nameSpace = "Trainee";

    private static final Logger LOGGER = LoggerFactory.getLogger(TraineeDAO.class);


    public TraineeDAO(InMemoryStorage storage) {
        this.storage = storage;
    }


    public Trainee get(Long traineeId) {
        return (Trainee) storage.get(nameSpace, traineeId);
    }

    public Trainee save(Trainee newTrainee) {

        return (Trainee) storage.save(nameSpace, newTrainee.getId(), newTrainee);
    }

    public void delete(Long traineeId) {
        storage.delete(nameSpace, traineeId);
    }


    public Map<Long, Object> getAll() {
        return storage.getAll(nameSpace);

    }

    public String findByUsername(String username) {
        LOGGER.info("Find trainer by uer name");

        for (Map<Long, Object> innerMap : storage.getStorageMap().values()) {
            for (Object obj : innerMap.values()) {
                if (obj instanceof Trainee) {
                    Trainee trainee = (Trainee) obj;
                    String traineeUsername = trainee.getUserName();
                    if (traineeUsername != null && traineeUsername.equals(username)) {
                        return username = traineeUsername;
                    }
                }
            }
        }
        return null;
    }

}