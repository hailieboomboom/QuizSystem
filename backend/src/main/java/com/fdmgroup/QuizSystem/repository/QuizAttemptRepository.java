package com.fdmgroup.QuizSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fdmgroup.QuizSystem.model.QuizAttempt;

public interface QuizAttemptRepository extends JpaRepository<QuizAttempt, Long> {
}
