package com.fdmgroup.QuizSystem.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fdmgroup.QuizSystem.exception.QuizNotFoundException;
import com.fdmgroup.QuizSystem.model.Quiz;
import com.fdmgroup.QuizSystem.model.QuizCategory;
import com.fdmgroup.QuizSystem.repository.QuizRepository;

@Service
@Transactional
public class QuizService {

	@Autowired
	private QuizRepository quizRepository;

	public Quiz saveQuiz(Quiz quizToSave) {
		return quizRepository.save(quizToSave);
	}

	public Quiz updateQuiz(long id, Quiz quizToUpdate) {
		Optional<Quiz> quizInRepo = quizRepository.findById(id);
		if (quizInRepo.isEmpty()) {
			throw new QuizNotFoundException();
		}

		// Update quiz with new attributes
		Quiz managedQuiz = quizInRepo.get();
		managedQuiz.setQuizCategory(quizToUpdate.getQuizCategory());
		return managedQuiz;
	}

	public void deleteQuizById(long id) {
		if (quizRepository.existsById(id)) {
			quizRepository.deleteById(id);
		} else {
			throw new QuizNotFoundException();
		}
	}

	public List<Quiz> getAllQuizzes() {
		return quizRepository.findAll();
	}

	public Quiz getQuizById(long id) {

		Optional<Quiz> quizInRepo = quizRepository.findById(id);
		if (quizInRepo.isEmpty()) {
			throw new QuizNotFoundException();
		}
		return quizInRepo.get();
	}


	
	public List<Quiz> getQuizzesByQuizCategory(QuizCategory quizCategory) {
		return quizRepository.findByQuizCategory(quizCategory);
		
	}

//	public Quiz getQuizByQuizCategory(QuizCategory quizCategory) {
//	Optional<Quiz> quizInRepo = quizRepository.findByQuizCategory(quizCategory);
//	if (quizInRepo.isEmpty()) {
//		throw new QuizNotFoundException();
//	}
//	return quizInRepo.get();
//}
	
}
