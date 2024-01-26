package org.gym.dao;

import org.gym.model.Trainer;
import org.gym.model.Training;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;


@Repository
public interface TrainingDAO extends JpaRepository<Training, Long> , JpaSpecificationExecutor<Training> {

    List<Training> findAll(Specification<Training> spec);

    default Specification<Training> hasTraineeUsername(String username) {
        return (root, query, cb) -> cb.equal(root.get("trainee").get("user").get("userName"), username);
    }
    default Specification<Training> hasTrainerUsername(String username) {
        return (root, query, cb) -> cb.equal(root.get("trainer").get("user").get("userName"), username);
    }
    default Specification<Training> hasTrainingName(String trainingName) {
        return (root, query, cb) -> cb.equal(root.get("trainingName"), trainingName);
    }

    default Specification<Training> isBetweenTrainingDates(Date startDate, Date endDate) {
        return (root, query, cb) -> cb.between(root.get("trainingDate"), startDate, endDate);
    }

    default Specification<Training> hasTrainingDuration(Integer duration) {
        return (root, query, cb) -> cb.equal(root.get("trainingDuration"), duration);
    }



}