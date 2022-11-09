package com.fdmgroup.QuizSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fdmgroup.QuizSystem.model.Quiz;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {
    
//	Optional<Quiz> findByCategory(String category);
}
