package com.fdmgroup.QuizSystem.service;

import com.fdmgroup.QuizSystem.exception.UserNotFoundException;
import com.fdmgroup.QuizSystem.model.Role;
import com.fdmgroup.QuizSystem.model.Trainer;
import com.fdmgroup.QuizSystem.model.User;
import com.fdmgroup.QuizSystem.repository.TrainerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class TrainerServiceTest {

    private TrainerService trainerService;

    @Mock
    private TrainerRepository mockTrainerRepo;

    @Mock
    private Trainer mockTrainer;

    @BeforeEach
    void setup(){
        trainerService = new TrainerService(mockTrainerRepo);
    }

    @Test
    void testGetTrainerById(){
        long id = 1;
        when(mockTrainerRepo.findById(id)).thenReturn(Optional.of(mockTrainer));

        Trainer result = trainerService.getTrainerById(id);
        assertEquals(mockTrainer, result);
    }

    @Test
    void testGetTrainerById_ThrowUserNotFoundException(){
        long id = 1;
        when(mockTrainerRepo.findById(id)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, ()-> trainerService.getTrainerById(id));
    }

    @Test
    void testGetTrainerByUsername(){
        String username = "test";
        when(mockTrainerRepo.findByUsername(username)).thenReturn(Optional.of(mockTrainer));

        User result = trainerService.findByUsername(username);

        assertEquals(mockTrainer, result);
    }

    @Test
    void testGetUserByUsername_ThrowUserNotFoundException(){
        String username = "test";
        when(mockTrainerRepo.findByUsername(username)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> trainerService.findByUsername(username));
    }

    @Test
    void testAuthoriseTrainerByUsername(){
        String username = "test";
        Trainer trainer = new Trainer();
        when(mockTrainerRepo.findByUsername(username)).thenReturn(Optional.of(trainer));
        when(mockTrainerRepo.save(trainer)).thenReturn(trainer);

        Trainer result = trainerService.authoriseTrainer(username);

        assertEquals(Role.AUTHORISED_TRAINER, result.getRole());
    }

    @Test
    void testGetAllUnauthorisedTrainers(){
        Trainer trainer1 = new Trainer();
        trainer1.setUsername("trainer1");
        Trainer trainer2 = new Trainer();
        trainer2.setUsername("trainer2");
        Trainer trainer3 = new Trainer();
        trainer3.setUsername("trainer3");
        Trainer trainer4 = new Trainer();
        trainer4.setUsername("trainer4");
        trainer4.setRole(Role.AUTHORISED_TRAINER);
        List<Trainer> trainers = new ArrayList<>(List.of(trainer1, trainer2, trainer3, trainer4));

        when(mockTrainerRepo.findAll()).thenReturn(trainers);

        List<Trainer> result = trainerService.getAllUnauthorisedTrainers();
        assertEquals(3, result.size());
        assertEquals("trainer1", result.get(0).getUsername());
        assertEquals("trainer2", result.get(1).getUsername());
        assertEquals("trainer3", result.get(2).getUsername());
    }

    @Test
    void testSaveTrainer(){
        when(mockTrainerRepo.save(mockTrainer)).thenReturn(mockTrainer);

        assertEquals(mockTrainer, trainerService.save(mockTrainer));
    }

}
