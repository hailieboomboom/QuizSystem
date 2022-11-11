package com.fdmgroup.QuizSystem.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fdmgroup.QuizSystem.model.QuizQuestionGrade;
import com.fdmgroup.QuizSystem.model.QuizQuestionGradeKey;

public interface QuizQuestionGradeRepository extends JpaRepository<QuizQuestionGrade, Long>{
	
	Optional<QuizQuestionGrade> findByKey(QuizQuestionGradeKey key);
}
