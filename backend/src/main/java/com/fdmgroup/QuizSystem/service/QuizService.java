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

	public void createQuiz(QuizDto quizDto) {
		Quiz quizEntity = new Quiz();
		quizEntity.setQuizCategory(quizDto.getQuizCategory());
		quizEntity.setQuestions(quizDto.getQuestions());
		quizEntity.setCreator(userRepository.findById(quizDto.getCreatorId()).get());
		quizRepository.save(quizEntity);
	}

	public QuizDto getQuizDto(Quiz quiz) {
		QuizDto quizDto = new QuizDto();
		quizDto.setQuizCategory(quiz.getQuizCategory());
		quizDto.setQuestions(quiz.getQuestions());
		quizDto.setCreatorId(quiz.getCreator().getId());
		return quizDto;
	}

	
	public List<QuizDto> getAllQuizzes() {
		List<Quiz> allQuizzes = quizRepository.findAll();
		
		List<QuizDto> quizDtos = new ArrayList<>();
		for (Quiz quiz : allQuizzes) {
			quizDtos.add(getQuizDto(quiz));
		}
		return quizDtos;
	}

	public void updateQuiz(long id, QuizDto quizDto) throws Exception {
		
		Optional<Quiz> optionalQuiz = quizRepository.findById(id);
		
		// Check if quiz exists
		if (optionalQuiz.isEmpty()) {
			// TODO: to fix it
			// throw new Exception("Quiz not found");
			 throw new QuizNotFoundException();
		}

		// Update quiz
		Quiz quiz = optionalQuiz.get();
		quiz.setQuizCategory(quizDto.getQuizCategory());
		quiz.setQuestions(quizDto.getQuestions());
		quiz.setCreator(userRepository.findById(quizDto.getCreatorId()).get());
		quizRepository.save(quiz);
	}
	

	
	public void deleteQuizById(long id) {
		
		if (quizRepository.existsById(id)) {
			quizRepository.deleteById(id);
		} else {
			throw new QuizNotFoundException();
		}
	}
	

	public QuizDto getQuizById(long id) {

		Optional<Quiz> optionalQuiz = quizRepository.findById(id);
		if (optionalQuiz.isEmpty()) {
			throw new QuizNotFoundException();
		}
		return getQuizDto(optionalQuiz.get());
	}
	

	public List<Quiz> getQuizzesByQuizCategory(QuizCategory quizCategory) {
		return quizRepository.findByQuizCategory(quizCategory);

	}

	public List<QuizDto> getContentQuizzes() {
		
		List<Quiz> contentQuizzes = quizRepository.findByQuizCategory(QuizCategory.COURSE_QUIZ);
		
		List<QuizDto> contentQuizDtos = new ArrayList<>();
		for (Quiz quiz : contentQuizzes) {
			contentQuizDtos.add(getQuizDto(quiz));
		}
		return contentQuizDtos;
	}
	


}
