package org.gym.dao;

import org.gym.model.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface TrainerDAO extends JpaRepository<Trainer, Long> {
    Optional<Trainer> findTrainerByUserName(String username);
}