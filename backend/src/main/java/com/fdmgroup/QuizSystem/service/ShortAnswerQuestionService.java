package com.fdmgroup.QuizSystem.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fdmgroup.QuizSystem.model.Question;
import com.fdmgroup.QuizSystem.model.ShortAnswerQuestion;
import com.fdmgroup.QuizSystem.repository.ShortAnswerQuestionRepository;


@Service
@Transactional
public class ShortAnswerQuestionService {
	
	@Autowired
	ShortAnswerQuestionRepository saqRepo;
	
	
	public ShortAnswerQuestionService(ShortAnswerQuestionRepository saqRepo) {
		super();
		this.saqRepo = saqRepo;
	}

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
	
	// check if short answer question exists by question details
	public boolean ifShortAnswerQuestionExists(String questionDetails) {
		Optional<Question> foundQ = saqRepo.findByQuestionDetails(questionDetails);
		if(foundQ.isEmpty()) {
			return false;
		}else {
			return true;
		}
	}
	
	public Question getByQuestionDetails(String questionDetails) {
		Optional<Question> foundQ = saqRepo.findByQuestionDetails(questionDetails);
		if(foundQ.isEmpty()) {
			return null;
		}
		return foundQ.get();
	}
	
	public List<Long> getAllSaqIds(){
		List<Long> saqIds = new ArrayList<Long>();
		List<ShortAnswerQuestion> saqs = findAll();
		for(ShortAnswerQuestion saq : saqs) {
			Long id = saq.getId();
			saqIds.add(id);
		}
		return saqIds;
	}

}
