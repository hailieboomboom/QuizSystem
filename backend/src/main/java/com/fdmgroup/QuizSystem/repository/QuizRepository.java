package com.fdmgroup.QuizSystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fdmgroup.QuizSystem.model.Quiz;
import com.fdmgroup.QuizSystem.model.QuizCategory;
import com.fdmgroup.QuizSystem.model.User;

/**
 * Repository for quiz
 */
@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {

	List<Quiz> findByQuizCategory(QuizCategory quizCategory);

	List<Quiz> findByCreator(User creator);
	
//	Optional<Quiz> findByQuizCategory(QuizCategory quizCategory);
}
