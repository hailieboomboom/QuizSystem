package com.fdmgroup.QuizSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fdmgroup.QuizSystem.model.Question;
import com.fdmgroup.QuizSystem.model.ShortAnswerQuestion;

@Repository
public interface ShortAnswerQuestionRepository extends JpaRepository<ShortAnswerQuestion, Long>{

}
