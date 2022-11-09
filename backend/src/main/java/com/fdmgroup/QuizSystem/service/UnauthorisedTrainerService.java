package com.fdmgroup.QuizSystem.service;

import com.fdmgroup.QuizSystem.exception.UserNotFoundException;
import com.fdmgroup.QuizSystem.model.Trainer;
import com.fdmgroup.QuizSystem.model.UnauthorisedTrainer;
import com.fdmgroup.QuizSystem.repository.TrainerRepository;
import com.fdmgroup.QuizSystem.repository.UnauthorisedTrainerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UnauthorisedTrainerService {

    private final UnauthorisedTrainerRepository unauthorisedTrainerRepository;

    private final TrainerRepository trainerRepository;



    public UnauthorisedTrainer findByUsername(String username) {
        Optional<UnauthorisedTrainer> maybeTrainer = unauthorisedTrainerRepository.findByUsername(username);
        if (maybeTrainer.isEmpty()) {
            throw new UserNotFoundException("Trainer is not found!");
        }
        return maybeTrainer.get();
    }

    public UnauthorisedTrainer save(UnauthorisedTrainer unauthorisedTrainer) {
        return unauthorisedTrainerRepository.save(unauthorisedTrainer);
    }

    public void delete(String username){
        UnauthorisedTrainer unauthorisedTrainer = findByUsername(username);
        unauthorisedTrainerRepository.delete(unauthorisedTrainer);
    }

    public Trainer authoriseTrainer(String username){
        UnauthorisedTrainer unauthorisedTrainer = findByUsername(username);
        Trainer trainer = new Trainer();
        trainer.setUsername(unauthorisedTrainer.getUsername());
        trainer.setEmail(unauthorisedTrainer.getEmail());
        trainer.setPassword(unauthorisedTrainer.getPassword());
        trainer.setFirstName(unauthorisedTrainer.getFirstName());
        trainer.setLastName(unauthorisedTrainer.getLastName());
        unauthorisedTrainerRepository.delete(unauthorisedTrainer);
        return trainerRepository.save(trainer);
    }



}
