package com.fdmgroup.QuizSystem.repository;

import com.fdmgroup.QuizSystem.model.Quiz;
import com.fdmgroup.QuizSystem.model.QuizAttempt;
import com.fdmgroup.QuizSystem.model.QuizQuestionMCQAttempt;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizQuestionMCQAttemptRepository extends JpaRepository<QuizQuestionMCQAttempt, Long> {

	List<QuizQuestionMCQAttempt> findByQuizAttemptId(Long quizAttemptId);
	List<QuizQuestionMCQAttempt> findByQuizAttempt(QuizAttempt quizAttempt);
}
