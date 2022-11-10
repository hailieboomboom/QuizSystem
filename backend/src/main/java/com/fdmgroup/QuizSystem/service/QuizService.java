package com.fdmgroup.QuizSystem.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.fdmgroup.QuizSystem.dto.QuizDto;
import com.fdmgroup.QuizSystem.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fdmgroup.QuizSystem.exception.QuizNotFoundException;
import com.fdmgroup.QuizSystem.model.Quiz;
import com.fdmgroup.QuizSystem.model.QuizCategory;
import com.fdmgroup.QuizSystem.repository.QuizRepository;

@Service
@AllArgsConstructor
@Transactional
public class QuizService {

	private QuizRepository quizRepository;
	private UserRepository userRepository;

	public void createQuiz(QuizDto quizDto){

		Quiz quizEntity = new Quiz();
		quizEntity.setQuizCategory(quizDto.getQuizCategory());
		quizEntity.setQuestions(quizDto.getQuestions());
		quizEntity.setCreator(userRepository.findById(quizDto.getCreatorId()).get());
		quizRepository.save(quizEntity);
	}

	public QuizDto getQuizDto(Quiz quiz){
		QuizDto quizDto = new QuizDto();
		quizDto.setQuizCategory(quiz.getQuizCategory());
		quizDto.setQuestions(quiz.getQuestions());
		quizDto.setCreatorId(quiz.getCreator().getId());
		return quizDto;
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

	public List<QuizDto> getAllQuizzes() {
		List<Quiz> allQuizzes = quizRepository.findAll();
		List<QuizDto> quizDtos = new ArrayList<>();
		for(Quiz quiz: allQuizzes){
			quizDtos.add(quiz);
		}
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
