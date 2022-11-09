package com.fdmgroup.QuizSystem.service;
import com.fdmgroup.QuizSystem.exception.UserNotFoundException;
import com.fdmgroup.QuizSystem.model.Trainer;
import com.fdmgroup.QuizSystem.model.User;
import com.fdmgroup.QuizSystem.repository.TrainerRepository;
import com.fdmgroup.QuizSystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.swing.text.html.Option;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class TrainerService {

    private final TrainerRepository trainerRepository;


    private final UserRepository userRepository;

    public Trainer findTrainerByUsername(String username){
//        Optional<User> maybeUser = userRepository.findUserByUsername(username);
//        if(maybeUser.isEmpty()){
//            throw new UserNotFoundException("Trainer is not found!");
//        }

        Optional<Trainer> maybeTrainer = trainerRepository.findByUsername(username);
        if (maybeTrainer.isEmpty()) {
            throw new UserNotFoundException("Trainer is not found!");
        }
        return maybeTrainer.get();
    }

    public Trainer updateTrainer(Trainer modifiedTrainer) {
        Trainer trainer = findTrainerByUsername(modifiedTrainer.getUsername());
        modifiedTrainer.setId(trainer.getId());
        return trainerRepository.save(modifiedTrainer);
    }


    public Trainer save(Trainer trainer){
       return trainerRepository.save(trainer);
    }

}
