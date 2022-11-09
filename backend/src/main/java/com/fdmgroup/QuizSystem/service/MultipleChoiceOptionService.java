package com.fdmgroup.QuizSystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fdmgroup.QuizSystem.model.MultipleChoiceOption;
import com.fdmgroup.QuizSystem.repository.MultipleChoiceOptionRepository;


@Service
@Transactional
public class MultipleChoiceOptionService {

	@Autowired
	private MultipleChoiceOptionRepository mcoRepo;
	
	public MultipleChoiceOption save(MultipleChoiceOption mco) {
		return this.mcoRepo.save(mco);
	}
}
