package com.fdmgroup.QuizSystem.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fdmgroup.QuizSystem.dto.QuizDto;
import com.fdmgroup.QuizSystem.exception.QuizNotFoundException;
import com.fdmgroup.QuizSystem.model.Question;
import com.fdmgroup.QuizSystem.model.Quiz;
import com.fdmgroup.QuizSystem.model.QuizCategory;
import com.fdmgroup.QuizSystem.repository.QuizRepository;
import com.fdmgroup.QuizSystem.repository.UserRepository;

import lombok.AllArgsConstructor;

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
		quizDto.setName(quiz.getName());
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

	public void updateQuiz(long id, QuizDto quizDto){
		
		// Check if quiz exists
		Optional<Quiz> optionalQuiz = quizRepository.findById(id);
		if (optionalQuiz.isEmpty()) {
			 throw new QuizNotFoundException();
		}

		Quiz quiz = optionalQuiz.get();
		quiz.setName(quizDto.getName());
		quiz.setQuizCategory(quizDto.getQuizCategory());
		quiz.setQuestions(quizDto.getQuestions());
		quiz.setCreator(userRepository.findById(quizDto.getCreatorId()).get());
		quizRepository.save(quiz);
	}
	

	public void deleteQuizById(long id) {
		
		Optional<Quiz> optionalQuiz = quizRepository.findById(id);
		if(optionalQuiz.isEmpty()) {
			throw new QuizNotFoundException();
		}
		Quiz managedQuiz = optionalQuiz.get();
		
	// TODO is it necessary to do these steps?	
//		// let user know about quiz removal
//		User managedUser = managedQuiz.getCreator();
//		managedUser.removeQuiz(managedQuiz);
//		userRepo.save(managedUser);
//		
//		// let questions know about quiz removal
//		List<Question> managedQuestions = managedQuiz.getQuestions();
//		for(Question managedQuestion: managedQuestions) {
//			managedQuestion.removeQuiz(managedQuiz);
//			questionRepository.save(managedQuestion);
//		}
		
		quizRepository.delete(managedQuiz);
		
		// TODO or can it be simplify in this way?
//		if (quizRepository.existsById(id)) {
//			quizRepository.deleteById(id);
//		} else {
//			throw new QuizNotFoundException();
//		}
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
