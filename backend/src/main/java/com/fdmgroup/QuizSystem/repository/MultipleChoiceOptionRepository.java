package com.fdmgroup.QuizSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fdmgroup.QuizSystem.model.MultipleChoiceOption;

import java.util.List;


public interface MultipleChoiceOptionRepository extends JpaRepository<MultipleChoiceOption, Long>{
    List<MultipleChoiceOption> findAllByMcqId(Long id);
}
