package com.fdmgroup.QuizSystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fdmgroup.QuizSystem.model.Quiz;
import com.fdmgroup.QuizSystem.model.QuizCategory;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {

	List<Quiz> findByQuizCategory(QuizCategory quizCategory);
	
//	Optional<Quiz> findByQuizCategory(QuizCategory quizCategory);
}
