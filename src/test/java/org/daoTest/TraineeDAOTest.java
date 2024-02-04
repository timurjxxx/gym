//package org.daoTest;
//
//import org.gym.model.User;
//import org.gym.service.TraineeService;
//import org.junit.jupiter.api.BeforeEach;
//import org.mockito.MockitoAnnotations;
//import org.gym.dao.TraineeDAO;
//import org.gym.model.Trainee;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//
//import java.util.Date;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.when;
//
//public class TraineeDAOTest {
//
//    @Mock
//    private TraineeDAO traineeDAO;
//
//    @InjectMocks
//    private TraineeService traineeService;
//
//    @BeforeEach
//    public void setUp(){
//        MockitoAnnotations.openMocks(this);
//    }
//    @Test
//    public void testFindTraineeByUserUserName() {
//        Trainee trainee = new Trainee();
//        trainee.setDateOfBirth(new Date());
//
//        User user = new User();
//        user.setUserName("testUsername");
//
//        trainee.setUser(user);
//
//        when(traineeDAO.findTraineeByUserUserName(Mockito.anyString())).thenReturn(Optional.of(trainee));
//
//        Optional<Trainee> foundTrainee = Optional.ofNullable(traineeService.selectTraineeByUserName("testUsername", "password"));
//        assertTrue(foundTrainee.isPresent());
//        assertEquals("testUsername", foundTrainee.get().getUser().getUserName());
//    }
//
//
//    @Test
//    public void testDeleteTraineeByUserUserName() {
//        when(traineeDAO.findTraineeByUserUserName(Mockito.anyString())).thenReturn(Optional.of(new Trainee()));
//
//        traineeService.deleteTraineeByUserName("testUsername", "password");
//
//        Mockito.verify(traineeDAO, Mockito.times(1)).deleteTraineeByUserUserName("testUsername");
//    }
//
//
//}
