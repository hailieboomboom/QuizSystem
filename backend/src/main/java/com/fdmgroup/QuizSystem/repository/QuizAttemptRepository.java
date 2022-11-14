package com.fdmgroup.QuizSystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fdmgroup.QuizSystem.model.QuizAttempt;
import com.fdmgroup.QuizSystem.model.User;

public interface QuizAttemptRepository extends JpaRepository<QuizAttempt, Long> {

	List<QuizAttempt> findByQuizIdAndUserId(long quizId, long userId);

	List<QuizAttempt> findByQuizId(long quizId);

	List<QuizAttempt> findByUser(User user);
}
