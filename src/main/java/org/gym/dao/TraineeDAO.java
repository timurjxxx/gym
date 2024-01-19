package org.gym.dao;

import org.gym.model.Trainee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Repository
public interface TraineeDAO extends JpaRepository<Trainee, Long> {
    @Transactional(readOnly = true)
    Optional<Trainee> findTraineeByUserUserName(String username);
}