package com.fdmgroup.QuizSystem.repository;

import com.fdmgroup.QuizSystem.model.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Trainer repository.
 */
public interface TrainerRepository extends JpaRepository<Trainer, Long> {

    Optional<Trainer> findByUsername(String username);


}
