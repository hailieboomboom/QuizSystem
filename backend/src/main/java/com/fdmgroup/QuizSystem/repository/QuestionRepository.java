package com.fdmgroup.QuizSystem.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.fdmgroup.QuizSystem.model.Question;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    
}
