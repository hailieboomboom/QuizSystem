package com.fdmgroup.QuizSystem.repository;

import com.fdmgroup.QuizSystem.model.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TrainerRepository extends JpaRepository<Trainer, Long> {

    Optional<Trainer> findByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

}
