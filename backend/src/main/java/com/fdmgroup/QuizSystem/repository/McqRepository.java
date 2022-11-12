package com.fdmgroup.QuizSystem.repository;


import com.fdmgroup.QuizSystem.model.MultipleChoiceQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface McqRepository extends JpaRepository<MultipleChoiceQuestion, Long> {
}
