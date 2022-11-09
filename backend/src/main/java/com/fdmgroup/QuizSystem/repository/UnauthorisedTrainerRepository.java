package com.fdmgroup.QuizSystem.repository;

import com.fdmgroup.QuizSystem.model.UnauthorisedTrainer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UnauthorisedTrainerRepository extends JpaRepository<UnauthorisedTrainer, Long> {
}
