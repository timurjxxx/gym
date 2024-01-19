package org.gym.dao;

import org.gym.model.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Repository
public interface TrainerDAO extends JpaRepository<Trainer, Long> {
    @Transactional(readOnly = true)
    Optional<Trainer> findTrainerByUserUserName(String username);
}