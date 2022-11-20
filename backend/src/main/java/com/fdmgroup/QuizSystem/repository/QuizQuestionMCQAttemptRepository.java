package com.fdmgroup.QuizSystem.repository;

import com.fdmgroup.QuizSystem.model.QuizAttempt;
import com.fdmgroup.QuizSystem.model.QuizQuestionMCQAttempt;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Repository for QuizQuestionMCQAttempt
 * @author Yutta
 *
 */
public interface QuizQuestionMCQAttemptRepository extends JpaRepository<QuizQuestionMCQAttempt, Long> {

	List<QuizQuestionMCQAttempt> findByQuizAttemptId(Long quizAttemptId);
	List<QuizQuestionMCQAttempt> findByQuizAttempt(QuizAttempt quizAttempt);

	@Query(value="select q.quiz_attempt_id from quiz_question_mcq_attempt q where q.mcq_id = ?1", nativeQuery = true)
	List<Long> findByMcqId(Long id);

}
