package com.fdmgroup.QuizSystem.repository;

import com.fdmgroup.QuizSystem.model.UnauthorisedTrainer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UnauthorisedTrainerRepository extends JpaRepository<UnauthorisedTrainer, Long> {
    Optional<UnauthorisedTrainer> findByUsername(String username);
}
