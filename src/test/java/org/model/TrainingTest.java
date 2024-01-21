//package org.model;
//
//import org.gym.model.Trainee;
//import org.gym.model.Trainer;
//import org.gym.model.Training;
//import org.gym.model.TrainingType;
//import org.junit.jupiter.api.Test;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotEquals;
//
//import java.util.*;
//
//public class TrainingTest {
//    @Test
//    void testGettersAndSetters() {
//        Trainee trainee = new Trainee();
//        Trainer trainer = new Trainer();
//        Date trainingDate = new Date();
//        List<TrainingType> trainingTypes = new ArrayList<>();
//
//        Training training = new Training();
//
//        // Test setter and getter for id
//        training.setId(1L);
//        assertEquals(1L, training.getId());
//
//        // Test setter and getter for traineeId
//        training.setTraineeId(trainee);
//        assertEquals(trainee, training.getTraineeId());
//
//        // Test setter and getter for trainerId
//        training.setTrainerId(trainer);
//        assertEquals(trainer, training.getTrainerId());
//
//        // Test setter and getter for trainingName
//        training.setTrainingName("Fitness Training");
//        assertEquals("Fitness Training", training.getTrainingName());
//
//        // Test setter and getter for trainingDate
//        training.setTrainingDate(trainingDate);
//        assertEquals(trainingDate, training.getTrainingDate());
//
//        // Test setter and getter for trainingDuration
//        training.setTrainingDuration(60);
//        assertEquals(60, training.getTrainingDuration());
//
//        // Test setter and getter for trainingType
//        training.setTrainingType(trainingTypes);
//        assertEquals(trainingTypes, training.getTrainingType());
//    }
//
//
//    @Test
//    public void testEqualsAndHashCode() {
//        Trainee trainee1 = new Trainee(1L, "TraineeFirstName", "TraineeLastName", "traineeUser", "traineePass", true, new Date(), "TraineeAddress");
//        Trainee trainee2 = new Trainee(1L, "TraineeFirstName", "TraineeLastName", "traineeUser", "traineePass", true, new Date(), "TraineeAddress");
//
//        Trainer trainer1 = new Trainer(2L, "TrainerFirstName", "TrainerLastName", "trainerUser", "trainerPass", false, "Fitness");
//        Trainer trainer2 = new Trainer(2L, "TrainerFirstName", "TrainerLastName", "trainerUser", "trainerPass", false, "Fitness");
//
//        List<TrainingType> trainingTypes1 = Arrays.asList(new TrainingType(1L, "Type1"), new TrainingType(2L, "Type2"));
//        List<TrainingType> trainingTypes2 = Arrays.asList(new TrainingType(1L, "Type1"), new TrainingType(2L, "Type2"));
//
//        Training training1 = new Training(1L, trainee1, trainer1, "Training1", new Date(), 60, trainingTypes1);
//        Training training2 = new Training(1L, trainee2, trainer2, "Training1", new Date(), 60, trainingTypes2);
//
//        assertEquals(training1, training2);
//        assertEquals(training1.hashCode(), training2.hashCode());
//
//        // Modify one of the fields
//        training2.setTrainingName("ModifiedTrainingName");
//
//        assertNotEquals(training1, training2);
//        assertNotEquals(training1.hashCode(), training2.hashCode());
//    }
//
//
//
//    @Test
//    public void testAllArgsConstructor() {
//        Trainee trainee = new Trainee(1L, "TraineeFirstName", "TraineeLastName", "traineeUser", "traineePass", true, new Date(), "TraineeAddress");
//        Trainer trainer = new Trainer(2L, "TrainerFirstName", "TrainerLastName", "trainerUser", "trainerPass", false, "Fitness");
//        List<TrainingType> trainingTypes = Arrays.asList(new TrainingType(1L, "Type1"), new TrainingType(2L, "Type2"));
//
//        Date trainingDate = new Date();
//        Number trainingDuration = 60;
//
//        Training training = new Training(1L, trainee, trainer, "Training1", trainingDate, trainingDuration, trainingTypes);
//
//        assertEquals(1L, training.getId());
//        assertEquals(trainee, training.getTraineeId());
//        assertEquals(trainer, training.getTrainerId());
//        assertEquals("Training1", training.getTrainingName());
//        assertEquals(trainingDate, training.getTrainingDate());
//        assertEquals(trainingDuration, training.getTrainingDuration());
//        assertEquals(trainingTypes, training.getTrainingType());
//    }
//
//}
