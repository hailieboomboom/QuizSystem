package com.fdmgroup.QuizSystem.service;
import com.fdmgroup.QuizSystem.exception.UserNotFoundException;
import com.fdmgroup.QuizSystem.model.Role;
import com.fdmgroup.QuizSystem.model.Trainer;
import com.fdmgroup.QuizSystem.repository.TrainerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * Trainer service for CRUD and authorisation actions on trainers.
 *
 * @author Jason Liu
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
@Transactional
public class TrainerService {

    private final TrainerRepository trainerRepository;

    /**
     * Get trainer by user id.
     * @param id User id.
     * @return   Trainer.
     */
    public Trainer getTrainerById(long id) {
        Optional<Trainer> maybeTrainer = trainerRepository.findById(id);
        if (maybeTrainer.isEmpty()) {
            throw new UserNotFoundException("Trainer is not found!");
        }
        return maybeTrainer.get();
    }

    /**
     * Get trainer by username.
     * @param username Username.
     * @return         Trainer.
     */
    public Trainer findByUsername(String username){

        Optional<Trainer> maybeTrainer = trainerRepository.findByUsername(username);
        if (maybeTrainer.isEmpty()) {
            throw new UserNotFoundException("Trainer is not found!");
        }
        return maybeTrainer.get();
    }

    /**
     * Authorise trainer.
     * @param username Username.
     * @return         Trainer.
     */
    public Trainer authoriseTrainer(String username) {
        Trainer trainer = findByUsername(username);
        trainer.setRole(Role.AUTHORISED_TRAINER);
        return trainerRepository.save(trainer);
    }

    /**
     * Get all unauthorised trainers.
     * @return A list of unauthorised trainers.
     */
    public List<Trainer> getAllUnauthorisedTrainers(){
       return trainerRepository.findAll().stream().filter(trainer -> trainer.getRole() == Role.UNAUTHORISED_TRAINER).toList();
    }

    /**
     * Persist trainer into database.
     * @param trainer Trainer.
     * @return        Persisted trainer.
     */
    public Trainer save(Trainer trainer){
       return trainerRepository.save(trainer);
    }

}
