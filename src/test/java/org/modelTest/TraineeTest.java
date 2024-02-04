//package org.modelTest;
//
//import org.gym.model.Trainee;
//import org.gym.model.Trainer;
//import org.gym.model.Training;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//
//import java.time.LocalDate;
//import java.util.Date;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//public class TraineeTest {
//
//    @Test
//    public void testGetterAndSetterForTraineeTrainings() {
//        Trainee trainee = new Trainee();
//        List<Training> traineeTrainings = Mockito.mock(List.class);
//        trainee.setTraineeTrainings(traineeTrainings);
//        assertEquals(traineeTrainings, trainee.getTraineeTrainings());
//    }
//
//    @Test
//    public void testGetterAndSetterForAddress() {
//        Trainee trainee = new Trainee();
//        String address = "123 Main St";
//        trainee.setAddress(address);
//        assertEquals(address, trainee.getAddress());
//    }
//
//    @Test
//    public void testGetterAndSetterForDateOfBirth() {
//        Trainee trainee = new Trainee();
//        LocalDate dateOfBirth = new LocalDate();
//        trainee.setDateOfBirth(dateOfBirth);
//        assertEquals(dateOfBirth, trainee.getDateOfBirth());
//    }
//
//    @Test
//    public void testGetterAndSetterForTrainers() {
//        Trainee trainee = new Trainee();
//        Set<Trainer> trainers = Mockito.mock(HashSet.class);
//        trainee.setTrainers(trainers);
//        assertEquals(trainers, trainee.getTrainers());
//    }
//}
