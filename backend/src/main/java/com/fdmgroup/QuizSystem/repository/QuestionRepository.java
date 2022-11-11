package com.fdmgroup.QuizSystem.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fdmgroup.QuizSystem.model.Question;
import com.fdmgroup.QuizSystem.model.User;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
	
	List<Question> findByCreator(User creator);
    
}
