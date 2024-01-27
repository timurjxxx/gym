package org.gym.dao;

import org.gym.model.Trainer;
import org.gym.model.Training;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Repository
public interface TrainerDAO extends JpaRepository<Trainer, Long> {


    @EntityGraph(value = "Trainer.fullGraph", type = EntityGraph.EntityGraphType.FETCH)
    Optional<Trainer> findTrainerByUserUserName(String username);


    void deleteTrainerByUserUserName(String username);

    List<Trainer> findTrainerByUserIsActive (Boolean isActive);

    boolean existsTrainerByUser_Id(Long id);

    @EntityGraph(value = "Trainer.fullGraph", type = EntityGraph.EntityGraphType.FETCH)
    @Query("SELECT t FROM Trainer t WHERE t.user.isActive = true AND t NOT IN (SELECT tt FROM Trainee tr JOIN tr.trainers tt WHERE tr.user.userName = :username)")
    List<Trainer> getNotAssignedActiveTrainers(@Param("username") String username);

}