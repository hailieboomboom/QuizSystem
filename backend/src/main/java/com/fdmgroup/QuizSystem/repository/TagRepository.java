package com.fdmgroup.QuizSystem.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


import com.fdmgroup.QuizSystem.model.Tag;

public interface TagRepository extends JpaRepository<Tag, Long>{
	
	Optional<Tag> findByTagName(String tagName);

}
