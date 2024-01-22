package org.gym.dao;

import org.gym.memory.InMemoryStorage;
import org.gym.model.Trainee;
import org.gym.model.Trainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Component
public class TraineeDAO {

    private final InMemoryStorage storage;


    @Autowired
    public TraineeDAO(InMemoryStorage storage) {
        this.storage = storage;
    }


    public Trainee get(String nameSpace, Long traineeId) {
        return (Trainee) storage.findById(nameSpace, traineeId);
    }

    public Trainee save(String nameSpace, Trainee newTrainee) {

        return (Trainee) storage.save(nameSpace, newTrainee);
    }

    public void delete(String nameSpace, Long id) {
        storage.deleteById(nameSpace, id);
    }

    public Optional<Trainee> findByUsername(String nameSpace, String username) {
        List<Trainee> trainers = storage.findAll(nameSpace)
                .stream()
                .filter(trainer -> trainer instanceof Trainee)
                .map(trainer -> (Trainee) trainer)
                .filter(trainer -> trainer.getUserName().equals(username))
                .collect(Collectors.toList());

        if (trainers.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(trainers.get(0));
        }
    }
    public void update(String nameSpace, Long id, Trainee trainee) {
        storage.update(nameSpace, id, trainee);
    }
}