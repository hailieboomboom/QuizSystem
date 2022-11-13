package com.fdmgroup.QuizSystem.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fdmgroup.QuizSystem.model.Question;
import com.fdmgroup.QuizSystem.model.User;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
	
	List<Question> findByCreator(User creator);
	List<Question> findAllByCreatorId(Long id);
	@Query(value="select q.creator_id from Question q where q.id = ?1", nativeQuery = true)
	Optional<Long> findCreatorIdOfQuestion(Long id);
    
}
