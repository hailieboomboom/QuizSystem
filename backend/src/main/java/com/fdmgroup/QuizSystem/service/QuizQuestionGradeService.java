package com.fdmgroup.QuizSystem.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fdmgroup.QuizSystem.model.QuizQuestionGrade;
import com.fdmgroup.QuizSystem.model.QuizQuestionGradeKey;
import com.fdmgroup.QuizSystem.model.User;
import com.fdmgroup.QuizSystem.repository.QuizQuestionGradeRepository;

@Service
@Transactional
public class QuizQuestionGradeService {
	
	@Autowired
	private QuizQuestionGradeRepository qqgRepo;
	
	public QuizQuestionGrade save(QuizQuestionGrade qqg) {
		return qqgRepo.save(qqg);
	}
	
	public QuizQuestionGrade findById(QuizQuestionGradeKey qqgKey) {
		Optional<QuizQuestionGrade> foundQqg = qqgRepo.findByKey(qqgKey);
		if(foundQqg.isEmpty()) {
			return null;
		}
		return foundQqg.get();
	}
	
	public void remove(QuizQuestionGrade qqg) {
		qqgRepo.delete(qqg);
	}
 
}
