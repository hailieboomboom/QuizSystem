package com.fdmgroup.QuizSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fdmgroup.QuizSystem.model.Quiz;

public interface QuizRepository extends JpaRepository<Quiz, Long> {
    
}
