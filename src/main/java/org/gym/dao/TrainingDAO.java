package org.gym.dao;

import org.gym.model.Training;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TrainingDAO extends JpaRepository<Training, Long> {


}