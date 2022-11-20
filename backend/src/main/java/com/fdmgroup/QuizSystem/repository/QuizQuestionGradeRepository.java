package com.fdmgroup.QuizSystem.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fdmgroup.QuizSystem.model.QuizQuestionGrade;
import com.fdmgroup.QuizSystem.model.QuizQuestionGradeKey;

/**
 * Repository for QuizQuestionGrade
 */
public interface QuizQuestionGradeRepository extends JpaRepository<QuizQuestionGrade, Long>{
	
	Optional<QuizQuestionGrade> findByKey(QuizQuestionGradeKey key);
	List<QuizQuestionGrade> findAllByQuizId(long quiz_id);

}
