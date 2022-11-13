package com.fdmgroup.QuizSystem.repository;

import com.fdmgroup.QuizSystem.model.QuizQuestionMCQAttempt;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizQuestionMCQAttemptRepository extends JpaRepository<QuizQuestionMCQAttempt, Long> {
}
