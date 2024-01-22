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
public class TrainerDAO {

    private final InMemoryStorage storage;

    @Autowired
    public TrainerDAO(InMemoryStorage storage) {
        this.storage = storage;
    }

    public Trainer save(String nameSpace, Trainer newTrainer) {
        return (Trainer) storage.save(nameSpace, newTrainer);
    }


    public Trainer get(String nameSpace, Long trainerId) {
        return (Trainer) storage.findById(nameSpace, trainerId);
    }

    public Optional<Trainer> findByUsername(String nameSpace, String username) {
        List<Trainer> trainers = storage.findAll(nameSpace)
                .stream()
                .filter(trainer -> trainer instanceof Trainer)
                .map(trainer -> (Trainer) trainer)
                .filter(trainer -> trainer.getUserName().equals(username))
                .collect(Collectors.toList());

        if (trainers.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(trainers.get(0));
        }
    }

    public void delete(String namespcae, Long id) {
        storage.deleteById(namespcae, id);
    }

    public void update(String namespace, Long id , Trainer updatedTrainer){
         storage.update(namespace,id,updatedTrainer);
    }
}