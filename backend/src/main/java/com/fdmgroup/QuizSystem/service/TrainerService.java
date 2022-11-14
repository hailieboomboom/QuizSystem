package com.fdmgroup.QuizSystem.service;
import com.fdmgroup.QuizSystem.dto.UserUpdateDTO;
import com.fdmgroup.QuizSystem.exception.UserAlreadyExistsException;
import com.fdmgroup.QuizSystem.exception.UserNotFoundException;
import com.fdmgroup.QuizSystem.model.Role;
import com.fdmgroup.QuizSystem.model.Student;
import com.fdmgroup.QuizSystem.model.Trainer;
import com.fdmgroup.QuizSystem.model.User;
import com.fdmgroup.QuizSystem.repository.TrainerRepository;
import com.fdmgroup.QuizSystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class TrainerService {

    private final TrainerRepository trainerRepository;

    public Trainer getTrainerById(long id) {
        Optional<Trainer> maybeTrainer = trainerRepository.findById(id);
        if (maybeTrainer.isEmpty()) {
            throw new UserNotFoundException("Trainer is not found!");
        }
        return maybeTrainer.get();
    }

    public Trainer findByUsername(String username){

        Optional<Trainer> maybeTrainer = trainerRepository.findByUsername(username);
        if (maybeTrainer.isEmpty()) {
            throw new UserNotFoundException("Trainer is not found!");
        }
        return maybeTrainer.get();
    }

    public Trainer authoriseTrainer(String username) {
        Trainer trainer = findByUsername(username);
        trainer.setRole(Role.AUTHORISED_TRAINER);
        return trainerRepository.save(trainer);
    }

    public List<Trainer> getAllUnauthorisedTrainers(){
       return trainerRepository.findAll().stream().filter(trainer -> trainer.getRole() == Role.UNAUTHORISED_TRAINER).toList();
    }

    public List<Trainer> getAllTrainers(){
        return trainerRepository.findAll();
    }


    public Trainer save(Trainer trainer){
       return trainerRepository.save(trainer);
    }

}
