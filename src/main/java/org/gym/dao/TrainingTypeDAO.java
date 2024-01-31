package org.gym.dao;

import org.gym.model.TrainingType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainingTypeDAO extends JpaRepository<TrainingType, Long> {


}
