//package org.daoTest;
//
//import org.gym.service.TrainerService;
//import org.junit.jupiter.api.BeforeEach;
//import org.mockito.MockitoAnnotations;
//import org.gym.dao.TrainerDAO;
//import org.gym.model.Trainer;
//import org.gym.model.User;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.when;
//public class TrainerDAOTest {
//
//    @Mock
//    private TrainerDAO trainerDAO;
//
//    @InjectMocks
//    private TrainerService trainerService;
//
//    @BeforeEach
//    public void setUp(){
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    public void testFindTrainerByUserUserName() {
//        Trainer trainer = new Trainer();
//        User user = new User();
//        user.setUserName("testUsername");
//        trainer.setUser(user);
//
//        when(trainerDAO.findTrainerByUserUserName(Mockito.anyString())).thenReturn(Optional.of(trainer));
//
//        Optional<Trainer> foundTrainer = Optional.ofNullable(trainerService.selectTrainerByUserName("testUsername", "password"));
//        assertTrue(foundTrainer.isPresent());
//        assertEquals("testUsername", foundTrainer.get().getUser().getUserName());
//    }
//
//    @Test
//    public void testDeleteTrainerByUserUserName() {
//        when(trainerDAO.findTrainerByUserUserName(Mockito.anyString())).thenReturn(Optional.of(new Trainer()));
//
//        trainerService.deleteTrainerByUserName("testUsername", "password");
//
//        Mockito.verify(trainerDAO, Mockito.times(1)).deleteTrainerByUserUserName("testUsername");
//    }
//
//
//    @Test
//    public void testGetNotAssignedActiveTrainers() {
//        Trainer trainer = new Trainer();
//        User user = new User();
//        user.setUserName("testUsername");
//        trainer.setUser(user);
//
//        when(trainerDAO.getNotAssignedActiveTrainers(Mockito.anyString())).thenReturn(List.of(trainer));
//        List<Trainer> notAssignedActiveTrainers = trainerService.getNotAssignedActiveTrainers("testUsername");
//
//        assertNotNull(notAssignedActiveTrainers);
//    }
//}
