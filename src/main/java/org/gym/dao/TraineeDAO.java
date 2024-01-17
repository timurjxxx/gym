package org.gym.dao;

import org.gym.model.Trainee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface TraineeDAO extends JpaRepository<Trainee, Long> {

    Optional<Trainee> findTraineeByUserName(String username);

    void deleteTraineeByUserName(String username);

}