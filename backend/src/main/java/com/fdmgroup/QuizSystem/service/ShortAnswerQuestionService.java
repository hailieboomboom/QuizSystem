package com.fdmgroup.QuizSystem.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fdmgroup.QuizSystem.model.ShortAnswerQuestion;
import com.fdmgroup.QuizSystem.repository.ShortAnswerQuestionRepository;


@Service
@Transactional
public class ShortAnswerQuestionService {
	
	@Autowired
	ShortAnswerQuestionRepository saqRepo;
	
	public ShortAnswerQuestion findById(Long id) {
		Optional<ShortAnswerQuestion> opSaq = saqRepo.findById(id);
		if(opSaq.isEmpty()) {
			return null;
		}
		return opSaq.get();
	}

	public List<ShortAnswerQuestion> findAll() {
		
		return saqRepo.findAll();
	}

}
