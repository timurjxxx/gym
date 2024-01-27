package org.gym.dao;

import org.gym.model.Trainee;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Repository
public interface TraineeDAO extends JpaRepository<Trainee, Long> {

    @EntityGraph(value = "Trainee.fullGraph", type = EntityGraph.EntityGraphType.FETCH)
    Optional<Trainee> findTraineeByUserUserName(String username);

    void deleteTraineeByUserUserName(String username);

    boolean existsTraineeByUser_Id(Long id);
}