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
    void toStringTest() {
        Long trainingId = 1L;
        Trainee trainee = new Trainee();
        trainee.setId(2L);
        trainee.setFirstName("John");
        trainee.setLastName("Doe");
        trainee.setUserName("john.doe");
        trainee.setPassword("password123");
        trainee.setIsActive(true);
        trainee.setDateOfBirth(new Date());
        trainee.setAddress("123 Main St");

        Trainer trainer = new Trainer();
        trainer.setId(3L);
        trainer.setFirstName("Jane");
        trainer.setLastName("Doe");
        trainer.setUserName("jane.doe");
        trainer.setPassword("password456");
        trainer.setIsActive(false);
        trainer.setSpecialization("Yoga");

        String trainingName = "Running";
        Date trainingDate = new Date();
        Number trainingDuration = 60;
        List<TrainingType> trainingType = List.of(new TrainingType(1L, "Cardio"));

        Training training = new Training();
        training.setId(trainingId);
        training.setTraineeId(trainee);
        training.setTrainerId(trainer);
        training.setTrainingName(trainingName);
        training.setTrainingDate(trainingDate);
        training.setTrainingDuration(trainingDuration);
        training.setTrainingType(trainingType);

        String expectedToString = "Training{" +
                "id=" + trainingId +
                ", traineeId=" + trainee +
                ", trainerId=" + trainer +
                ", trainingName='" + trainingName + '\'' +
                ", trainingDate=" + trainingDate +
                ", trainingDuration=" + trainingDuration +
                '}';

        assertEquals(expectedToString, training.toString());
    }
    @Test
    void equalsAndHashCodeTest() {
        Long trainingId = 1L;
        Trainee trainee = new Trainee();
        trainee.setId(2L);
        trainee.setFirstName("John");
        trainee.setLastName("Doe");
        trainee.setUserName("john.doe");
        trainee.setPassword("password123");
        trainee.setIsActive(true);
        trainee.setDateOfBirth(new Date());
        trainee.setAddress("123 Main St");

        Trainer trainer = new Trainer();
        trainer.setId(3L);
        trainer.setFirstName("Jane");
        trainer.setLastName("Doe");
        trainer.setUserName("jane.doe");
        trainer.setPassword("password456");
        trainer.setIsActive(false);
        trainer.setSpecialization("Yoga");

        String trainingName = "Running";
        Date trainingDate = new Date();
        Number trainingDuration = 60;
        List<TrainingType> trainingType = List.of(new TrainingType(1L, "Cardio"));

        Training training1 = new Training();
        training1.setId(trainingId);
        training1.setTraineeId(trainee);
        training1.setTrainerId(trainer);
        training1.setTrainingName(trainingName);
        training1.setTrainingDate(trainingDate);
        training1.setTrainingDuration(trainingDuration);
        training1.setTrainingType(trainingType);

        Training training2 = new Training();
        training2.setId(trainingId);
        training2.setTraineeId(trainee);
        training2.setTrainerId(trainer);
        training2.setTrainingName(trainingName);
        training2.setTrainingDate(trainingDate);
        training2.setTrainingDuration(trainingDuration);
        training2.setTrainingType(trainingType);

        assertEquals(training1, training2);
        assertEquals(training1.hashCode(), training2.hashCode());

        training2.setTrainingName("Swimming");

        assertNotEquals(training1, training2);
        assertNotEquals(training1.hashCode(), training2.hashCode());
    }

    @Test
    void getterSetterTest() {
        Training training = new Training();

        Long trainingId = 1L;
        Trainee trainee = new Trainee();
        trainee.setId(2L);
        trainee.setFirstName("John");
        trainee.setLastName("Doe");
        trainee.setUserName("john.doe");
        trainee.setPassword("password123");
        trainee.setIsActive(true);
        trainee.setDateOfBirth(new Date());
        trainee.setAddress("123 Main St");

        Trainer trainer = new Trainer();
        trainer.setId(3L);
        trainer.setFirstName("Jane");
        trainer.setLastName("Doe");
        trainer.setUserName("jane.doe");
        trainer.setPassword("password456");
        trainer.setIsActive(false);
        trainer.setSpecialization("Yoga");

        String trainingName = "Running";
        Date trainingDate = new Date();
        Number trainingDuration = 60;
        List<TrainingType> trainingType = List.of(new TrainingType(1L, "Cardio"));

        training.setId(trainingId);
        training.setTraineeId(trainee);
        training.setTrainerId(trainer);
        training.setTrainingName(trainingName);
        training.setTrainingDate(trainingDate);
        training.setTrainingDuration(trainingDuration);
        training.setTrainingType(trainingType);

        assertEquals(trainingId, training.getId());
        assertEquals(trainee, training.getTraineeId());
        assertEquals(trainer, training.getTrainerId());
        assertEquals(trainingName, training.getTrainingName());
        assertEquals(trainingDate, training.getTrainingDate());
        assertEquals(trainingDuration, training.getTrainingDuration());
        assertEquals(trainingType, training.getTrainingType());
    }

    @Test
    void allArgsConstructorTest() {
        Long trainingId = 1L;
        Trainee trainee = new Trainee();
        trainee.setId(2L);
        trainee.setFirstName("John");
        trainee.setLastName("Doe");
        trainee.setUserName("john.doe");
        trainee.setPassword("password123");
        trainee.setIsActive(true);
        trainee.setDateOfBirth(new Date());
        trainee.setAddress("123 Main St");

        Trainer trainer = new Trainer();
        trainer.setId(3L);
        trainer.setFirstName("Jane");
        trainer.setLastName("Doe");
        trainer.setUserName("jane.doe");
        trainer.setPassword("password456");
        trainer.setIsActive(false);
        trainer.setSpecialization("Yoga");

        String trainingName = "Running";
        Date trainingDate = new Date();
        Number trainingDuration = 60;
        List<TrainingType> trainingType = List.of(new TrainingType(1L, "Cardio"));

        Training training = new Training(trainingId, trainee, trainer, trainingName, trainingDate, trainingDuration, trainingType);

        assertEquals(trainingId, training.getId());
        assertEquals(trainee, training.getTraineeId());
        assertEquals(trainer, training.getTrainerId());
        assertEquals(trainingName, training.getTrainingName());
        assertEquals(trainingDate, training.getTrainingDate());
        assertEquals(trainingDuration, training.getTrainingDuration());
        assertEquals(trainingType, training.getTrainingType());
    }
}
