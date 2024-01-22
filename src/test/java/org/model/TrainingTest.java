package org.model;
import org.gym.model.Trainee;
import org.gym.model.Trainer;
import org.gym.model.Training;
import org.gym.model.TrainingType;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TrainingTest {

    @Test
    void testEquals() {
        Trainee trainee1 = new Trainee(1L, new Date(), "Address1", null);
        Trainer trainer1 = new Trainer(1L, "Specialization1", null);

        List<TrainingType> trainingTypes1 = new ArrayList<>();
        trainingTypes1.add(new TrainingType(1L, "Type1"));

        Training training1 = new Training(1L, trainee1, trainer1, "Training1", new Date(), 10, trainingTypes1);
        Training training2 = new Training(1L, trainee1, trainer1, "Training1", new Date(), 10, trainingTypes1);
        Training training3 = new Training(2L, trainee1, trainer1, "Training1", new Date(), 10, trainingTypes1);

        assertEquals(training1, training2);
        assertNotEquals(training1, training3);
    }

    @Test
    void testHashCode() {
        Trainee trainee1 = new Trainee(1L, new Date(), "Address1", null);
        Trainer trainer1 = new Trainer(1L, "Specialization1", null);

        List<TrainingType> trainingTypes1 = new ArrayList<>();
        trainingTypes1.add(new TrainingType(1L, "Type1"));

        Training training1 = new Training(1L, trainee1, trainer1, "Training1", new Date(), 10, trainingTypes1);
        Training training2 = new Training(1L, trainee1, trainer1, "Training1", new Date(), 10, trainingTypes1);
        Training training3 = new Training(2L, trainee1, trainer1, "Training1", new Date(), 10, trainingTypes1);

        assertEquals(training1.hashCode(), training2.hashCode());
        assertNotEquals(training1.hashCode(), training3.hashCode());
    }

    @Test
    void testBuilder() {
        Trainee trainee = new Trainee(1L, new Date(), "Address1", null);
        Trainer trainer = new Trainer(1L, "Specialization1", null);

        List<TrainingType> trainingTypes = new ArrayList<>();
        trainingTypes.add(new TrainingType(1L, "Type1"));

        Training training = Training.builder()
                .id(1L)
                .traineeId(trainee)
                .trainerId(trainer)
                .trainingName("Training1")
                .trainingDate(new Date())
                .trainingDuration(10)
                .trainingType(trainingTypes)
                .build();

        assertNotNull(training);
        assertEquals(1L, training.getId());
        assertEquals(trainee, training.getTraineeId());
        assertEquals(trainer, training.getTrainerId());
        assertEquals("Training1", training.getTrainingName());
        assertNotNull(training.getTrainingDate());
        assertEquals(10, training.getTrainingDuration());
        assertEquals(trainingTypes, training.getTrainingType());
    }

    @Test
    void testGetterSetterAllArgsConstructor() {
        Trainee trainee = new Trainee(1L, new Date(), "Address1", null);
        Trainer trainer = new Trainer(1L, "Specialization1", null);

        List<TrainingType> trainingTypes = new ArrayList<>();
        trainingTypes.add(new TrainingType(1L, "Type1"));

        Training training = new Training(1L, trainee, trainer, "Training1", new Date(), 10, trainingTypes);

        assertEquals(1L, training.getId());
        assertEquals(trainee, training.getTraineeId());
        assertEquals(trainer, training.getTrainerId());
        assertEquals("Training1", training.getTrainingName());
        assertNotNull(training.getTrainingDate());
        assertEquals(10, training.getTrainingDuration());
        assertEquals(trainingTypes, training.getTrainingType());

        Trainee newTrainee = new Trainee(2L, new Date(), "Address2", null);
        Trainer newTrainer = new Trainer(2L, "Specialization2", null);

        List<TrainingType> newTrainingTypes = new ArrayList<>();
        newTrainingTypes.add(new TrainingType(2L, "Type2"));

        training.setId(2L);
        training.setTraineeId(newTrainee);
        training.setTrainerId(newTrainer);
        training.setTrainingName("Training2");
        training.setTrainingDate(new Date());
        training.setTrainingDuration(20);
        training.setTrainingType(newTrainingTypes);

        assertEquals(2L, training.getId());
        assertEquals(newTrainee, training.getTraineeId());
        assertEquals(newTrainer, training.getTrainerId());
        assertEquals("Training2", training.getTrainingName());
        assertNotNull(training.getTrainingDate());
        assertEquals(20, training.getTrainingDuration());
        assertEquals(newTrainingTypes, training.getTrainingType());
    }
    @Test
    void testAllArgsConstructor() {
        Trainee trainee = new Trainee(1L, new Date(), "Address1", null);
        Trainer trainer = new Trainer(1L, "Specialization1", null);

        List<TrainingType> trainingTypes = new ArrayList<>();
        trainingTypes.add(new TrainingType(1L, "Type1"));

        Training training = new Training(1L, trainee, trainer, "Training1", new Date(), 10, trainingTypes);

        assertEquals(1L, training.getId());
        assertEquals(trainee, training.getTraineeId());
        assertEquals(trainer, training.getTrainerId());
        assertEquals("Training1", training.getTrainingName());
        assertNotNull(training.getTrainingDate());
        assertEquals(10, training.getTrainingDuration());
        assertEquals(trainingTypes, training.getTrainingType());
    }
}
